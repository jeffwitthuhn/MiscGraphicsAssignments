import java.util.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.List;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.abs;

public class Reflect extends Frame{
	public static void main(String[] args){
		new Reflect();
	}
	 public int width=1000;
	 public int height=1000; 
	 Reflect(){
	 	super ("Reflect!");
	 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
	 		pack();
	 		Insets boarder = getInsets();

	 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
			add("Center", new ReflectGraphics());
			show();
		}
}

class ReflectGraphics extends Canvas implements Runnable{
	boolean moving; 
	boolean closed; 
	boolean pt1set;
	boolean pt2set;
	boolean ready;
	double theta;
	Point2D pointToO;
	float speed;
	float alpha=0;
    int w,h;
    Thread thr = new Thread(this);
    Dimension d;
    Graphics gImage;
    Image image;
	List<Point2D> polygon;
	List<Point2D> reflectedpolygon;
	int clickCount;
	Point2D first;
	Point2D ptLine1, ptLine2;
	public void run(){
    		try{ 
    		 	for (;;){
	    		    if(moving){	
	    		 	  alpha += speed;
	    		 	  repaint();
    		 		}
    		 	    Thread.sleep (2);

				}
			}
			catch (InterruptedException e){}
		}
	ReflectGraphics(){
 		moving = false;
 		closed=false;
 		pt1set=false;
 		pt2set=false;
 		ready=false; 
 		clickCount=0;
 		polygon = new ArrayList<Point2D>();
 		reflectedpolygon = new ArrayList<Point2D>();
 		thr.start();
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
 		  			if(clickCount==0){
 		  				first= new Point2D.Double(evt.getX(), evt.getY());
 		  				polygon.add(new Point2D.Double(evt.getX(), evt.getY()));
 		  			}
 		  			else if(sqrt(abs(pow(first.getX()-evt.getX(),2)+abs(pow(first.getY()-evt.getY(),2))))<5){
 		  				closed=true; 
 		  			}
 		  			else if(closed==false){
 		  				polygon.add(new Point2D.Double(evt.getX(), evt.getY()));
 		  			}
 		  			else if(pt1set==false){
 		  				pt1set=true;
 		  				ptLine1=new Point2D.Double(evt.getX(), evt.getY());
 
 		  			}
 		  			else if(pt2set==false){
 		  				pt2set=true;
 		  				ptLine2=new Point2D.Double(evt.getX(), evt.getY());
 		  				ready=true;
 		  			}
 		  			else if (ready){
 		  				first= new Point2D.Double(evt.getX(), evt.getY());

 		  				closed=false;
				 		pt1set=false;
				 		pt2set=false;
				 		ready=false; 
				 		clickCount=0;
				 		polygon = new ArrayList<Point2D>();
				 		reflectedpolygon = new ArrayList<Point2D>();
				 		polygon.add(new Point2D.Double(evt.getX(), evt.getY()));

 		  			}
 		  			clickCount++;
 		  			repaint();
				}
			});
 		}
 	public void update(Graphics g){
 		paint(g);

 	}
 	public void paint(Graphics g){
 		d=getSize();
 		if (w != d.width || h != d.height){
 			w = d.width; h = d.height;
			image = createImage(w, h);
			gImage = image.getGraphics();
		}
 	    gImage.clearRect(0,0,w,h);
 	   	gImage.setColor(Color.black);
 	    gImage.fillRect(0,0,d.width,d.height);
 		if(clickCount>0){
 			gImage.setColor(Color.yellow);
 			gImage.fillRect((int)first.getX()-2, (int)first.getY()-2, 4,4);
 		 	gImage.setColor(Color.green);
 			for (int i=1; i<polygon.size(); i++){
 				gImage.drawLine((int)polygon.get(i-1).getX(), (int)polygon.get(i-1).getY(),
 					(int)polygon.get(i%polygon.size()).getX(),(int) polygon.get(i%polygon.size()).getY());
 			}
 			if(closed){
				gImage.drawLine((int)polygon.get(0).getX(), (int)polygon.get(0).getY(),
 					(int)polygon.get(polygon.size()-1).getX(),(int) polygon.get(polygon.size()-1).getY());
 			 }
 		 if(pt1set){
 		 	gImage.setColor(Color.yellow);
 			gImage.fillRect((int)ptLine1.getX()-2, (int)ptLine1.getY()-2, 4,4);
 		 }
 		 if(pt2set){
 		 	gImage.setColor(Color.yellow);
 			gImage.fillRect((int)ptLine2.getX()-2, (int)ptLine2.getY()-2, 4,4);
 			gImage.setColor(Color.blue);

 			gImage.drawLine((int)ptLine1.getX(), (int)ptLine1.getY(),
 					(int)ptLine2.getX(),(int) ptLine2.getY());
 			 

 		 }
 			
 		if(ready){
	 		if(ptLine2.getY()>ptLine1.getY()){
	 			pointToO=ptLine1;
	 			theta = Math.atan2(ptLine2.getX()-ptLine1.getX(),(ptLine2.getY()-ptLine1.getY()));

	 			//theta=calcAngle(ptLine2.getX()-ptLine1.getX(),(ptLine2.getY()-ptLine1.getY()));
	 			//theta=Math.atan(((double)(ptLine2.getY()-ptLine1.getY())/(double)(ptLine2.getX()-ptLine1.getX())));
	 			//if ((ptLine2.getX()-ptLine1.getX())<0)
	 				//theta+=3.14/2;

	 		}
	 		else {
	 			pointToO=ptLine2;
	 			theta = Math.atan2(ptLine1.getX()-ptLine2.getX(),(ptLine1.getY()-ptLine2.getY()));

	 			//theta=calcAngle(ptLine1.getX()-ptLine2.getX(),(ptLine1.getY()-ptLine2.getY()));

	 			//theta=Math.atan((double)((ptLine1.getY()-ptLine2.getY())/(ptLine1.getX()-ptLine2.getX())));
	 			//if ((ptLine1.getX()-ptLine2.getX())<0)
	 			//	theta+=3.14/2;
	 			 
	 		}

	 		
	 		for(int i=0; i<polygon.size(); i++){
	 			System.out.println("theta is: " +theta);

	 			reflectedpolygon.add(transform (theta,pointToO, 
	 				polygon.get(i)));
	 			}
			gImage.setColor(Color.red);

	 		for (int i=1; i<reflectedpolygon.size(); i++){

 				gImage.drawLine((int)reflectedpolygon.get(i-1).getX(), (int)reflectedpolygon.get(i-1).getY(),
 					(int)reflectedpolygon.get(i%reflectedpolygon.size()).getX(),(int) reflectedpolygon.get(i%reflectedpolygon.size()).getY());
 			}
 			gImage.drawLine((int)reflectedpolygon.get(0).getX(), (int)reflectedpolygon.get(0).getY(),
 					(int)reflectedpolygon.get(reflectedpolygon.size()-1).getX(),(int) reflectedpolygon.get(reflectedpolygon.size()-1).getY());
 			gImage.drawLine((int)transform(theta, pointToO, ptLine2).getX(),(int)transform(theta, pointToO, ptLine2).getY()
 				,(int)transform(theta, pointToO, ptLine1).getX(),(int)transform(theta, pointToO, ptLine1).getY() );
 		}
 	}
 		g.drawImage(image, 0, 0, null);


 	}
 	Point2D.Double transform(double theta, Point2D ab, Point2D start){
 		
 		double a=ab.getX();
 		double b=ab.getY();
 		double x=start.getX();
 		double y=start.getY();
 		double sin=Math.sin(theta);
 		double cos= Math.cos(theta);
 		double sin2=pow(sin,2);
 		double cos2= pow(cos,2);
 		double newX, newY;
 		/*
 		//
 		//			[sin^2(theta)-cos^2(theta)                              2cossin                              0]
 		// [x y 1]  [2cossin                                                  - sin^2(theta)+cos^2(theta)        0]
 		//			[a+sin(-b*cos-a*sin)+cos(a*cos-b*sin)   b+cos(-b*cos-a*sin)-sin(a*cos-b*sin)   1]
 		//
 		*/
 		///*
 		newX= x*(sin2-cos2)+y*(2*cos*sin)+(a+sin*(-b*cos-a*sin))+cos*(a*cos-b*sin);
 		newY=x*2*cos*sin+y*(cos2-sin2)+b+cos*(-b*cos-a*sin)-sin*(a*cos-b*sin);
 		x=newX;
 		y=newY;
 		//*/

 		//just try to rotate, get this right first..
 		/*
 		x=x-a;
 		y=y-b;

 		newX=x*cos+y*sin;
 		newY=-1*x*sin+y*cos;

 		x=newX+a;
 		y=newY+b;
 		*/



 		return new Point2D.Double(x,y);
 	}
 	  public static double calcAngle(double x, double y) {
        //returns a value 0-2pi, assumed the points x,y lie on a circle centered at origin. 
        double angle;
        angle = Math.atan2(y, x);
        if (angle < 0) {
            angle = Math.PI + Math.PI + angle;
        }
        return angle;
    }

}