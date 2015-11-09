//CSCI 450-1 
//Jeff Witthuhn
//homework3 incenters
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.abs;
//import java.awt.geom.Point2D;
//import java.awt.geom.Point2D.Double;
//import java.lang.Math.*;
 

public class incenter extends Frame{
	 public int width=700;
	 public int height=500; 
	 public int margin=20; 
	 incenter(java.util.List<java.awt.geom.Point2D> src, java.util.List<java.awt.geom.Point2D> trgt, java.awt.geom.Point2D centerSource, java.awt.geom.Point2D centerTarget){
	 	super ("incenters!");
	 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
	 		pack();
	 		Insets boarder = getInsets();
	 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
			add("Center", new incenterGraphics(src,trgt,centerSource,centerTarget,boarder));
			show();
		}
 }




 class incenterGraphics extends Canvas{
 	Insets boarder;
 	java.util.List<java.awt.geom.Point2D> source, target; 
 	HashMap<java.awt.geom.Point2D, java.awt.geom.Point2D> lineSegments;
 	int clickCount;
 	java.awt.geom.Point2D centerSource, centerTarget;//= new java.awt.geom.Point2D.Double(0.0, 0.0);

 	incenterGraphics(java.util.List<java.awt.geom.Point2D> src, java.util.List<java.awt.geom.Point2D> trgt, java.awt.geom.Point2D centerS, java.awt.geom.Point2D centerT,Insets _boarder){
 		boarder=_boarder;
 		source=src;
 		target=trgt;
 		centerSource=centerS;
 		centerTarget=centerT;
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
				repaint();
			}});
 		}
 	
 	public java.awt.geom.Point2D randomizeStarPoly(java.util.List<java.awt.geom.Point2D> poly){
 		double angle=0;
 		double maxrand=(2*Math.PI)/3; // at least 3 steps;?
 		double minrand = (Math.PI/16);//maximum of 32 points; ?
 		double random; 
 		double distance; 
 		System.out.println("min is  "+ minrand);
 		System.out.println("max is  "+ maxrand);
 		random=new Random().nextDouble(); // number between 0 and 1
 		double centerX=-200+400*random;
 		double centerY=-200+400*random;;


 		while(1==1){
 			random=new Random().nextDouble(); // number between 0 and 1
 			angle = angle + (minrand + (maxrand-minrand)*random);
 			random=new Random().nextDouble(); // number between 0 and 1
 			distance = 10 + (190*random);
 			if(angle>2*Math.PI)
 				break;
 			System.out.println("angle is "+ angle);

 			//x=distance*cos(angle)
 			//y=distance*sin(angle)
 
 			poly.add(new java.awt.geom.Point2D.Double(distance*Math.cos(angle)+centerX, distance*Math.sin(angle)+centerY));






 		}
 		return new java.awt.geom.Point2D.Double(centerX,centerY);

 	}
 	public void drawStuff(Graphics g, Dimension d){
 		System.out.println("size of source is: "+ source.size());
 		System.out.println("size of target is:  "+ target.size());

 		g.setColor(Color.green);
 		for(int i=0; i<source.size(); i++)
 			g.drawLine((int)source.get(i).getX(), (int)source.get(i).getY(),
 			 (int)source.get((i+1)%source.size()).getX(),(int)source.get((i+1)%source.size()).getY());
 		
 		g.setColor(Color.blue);
 		for(int i=0; i<target.size(); i++)
 			g.drawLine((int)target.get(i).getX(), (int)target.get(i).getY(),
 			 (int)target.get((i+1)%target.size()).getX(),(int)target.get((i+1)%target.size()).getY());

 		 System.out.println("source center is  ("+ centerSource.getX()+", "+centerSource.getY()+")");
 		 for(int i=0; i<source.size(); i++)
 		 	 System.out.println("source["+ i+"i] is: ("+source.get(i).getX()+", "+source.get(i).getY()+")");
 		 System.out.println("source target is  ("+ centerTarget.getX()+", "+centerTarget.getY()+")");
 		 for(int i=0; i<target.size(); i++)
 		 	 System.out.println("target["+ i+"i] is: ("+target.get(i).getX()+", "+target.get(i).getY()+")");


 		lineSegments=Morphing.calcMorph(source,target,centerSource,centerTarget);

 		g.setColor(Color.red);
        for (Map.Entry<java.awt.geom.Point2D, java.awt.geom.Point2D> entry : lineSegments.entrySet())
        {

            java.awt.geom.Point2D key = entry.getKey();
            java.awt.geom.Point2D value = entry.getValue();
            System.out.println("key["+ "] is: ("+key.getX()+", "+key.getY()+")");
            System.out.println("value["+ "] is: ("+value.getX()+", "+value.getY()+")");
            g.drawLine((int)key.getX(),(int)key.getY(), (int)value.getX(),(int)value.getY());
        }
 		
 	}
	public void paint(Graphics g){
	 	Dimension d=getSize();
	 	g.setColor(Color.black);
	 	g.fillRect(0,0,d.width,d.height);
	 	drawStuff(g,d);

 	}

}