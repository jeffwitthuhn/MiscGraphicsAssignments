//CSCI 450-1 
//Jeff Witthuhn
//Dashed Line Extra Credit
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;

public class dasher extends Frame{
static int dL, gL; 
public static void main(String[] args){
	parseArgs(args);
	new dasher(dL, gL);
}
 public int width=700;
 public int height=500; 
 public int margin=20; 
 dasher(int _dashL_, int _gapL_){
 	super ("DASHER");
 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
 		pack();
 		Insets boarder = getInsets();
 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
		add("Center", new Lines(boarder,_dashL_, _gapL_));
		show();
	}
 public static void parseArgs(String[] args){
		try {
			dL=Integer.parseInt(args[0]);
			gL=Integer.parseInt(args[1]);
		}
		catch(Exception e) {
			System.out.println("Usage: java dasher <ideal dash length> <ideal gap length>");
		}
	}
 }




 class Lines extends Canvas{
 	Insets boarder; 
 	float length; 
 	boolean firstclick=false; 
 	boolean secondclick=false; 
 	Vector<Integer> click1= new Vector<Integer>(2);
 	Vector<Integer> click2= new Vector<Integer>(2);
 	int dashL, gapL;

 	Lines(Insets _boarder, int _dashL, int _gapL){
 		boarder=_boarder;
 		dashL= _dashL; 
 		gapL= _gapL; 
 		click1.add(-1);
		click1.add(-1);
		click2.add(-1);
 		click2.add(-1);
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
					if(firstclick==false){
						click1.set(0, evt.getX());
						click1.set(1, evt.getY());
						System.out.println("click 1: ("+evt.getX() +","+evt.getY()+")");
						System.out.println("click 1: ("+click1.get(0) +","+click1.get(1)+")");

						firstclick=true;
						repaint();
					}
					else if(secondclick==false){
						click2.set(0, (evt.getX()));
						click2.set(1, (evt.getY()));
						System.out.println("click 2: ("+click2.get(0) +","+click2.get(1)+")");
						secondclick=true;
						length=(float)sqrt(pow(click2.get(0)-click1.get(0),2)+pow(click2.get(1)-click1.get(1),2));
						System.out.println("approximate distance: "+length);
						repaint();


					}
					else if(secondclick==true){
						secondclick=firstclick=false;
					}
				}
			});
 		}
				
		
 	public void drawDashedLine(Graphics g){
 			//draw dashed line from click1 to click2.
 			//assume length >= 2*dashL+gapL
 			Vector<Float> position1 = new Vector<Float>(2);
 			Vector<Float> position2 = new Vector<Float>(2);
 			position1.add((float)-1.0);
 			position2.add((float)-1.0);
			position1.add((float)-1.0);
 			position2.add((float)-1.0);
 			Vector<Float> direction = new Vector<Float>(2);
 			direction.add((float) -1.0);
 			direction.add((float) -1.0);
 			direction.set(0, (float)(click2.get(0)-click1.get(0))/length);
 			direction.set(1, (float)(click2.get(1)-click1.get(1))/length);
 			//length-(gapL+2*dashL) = a(gapL+dashL)+b
 			int a=(int) (length-(gapL+2*dashL))/(gapL+dashL);
 			int b=(int) (length-(gapL+2*dashL))%(gapL+dashL);


 			int adjustment=b/(3+2*a);
 			int remaining=b%(3+2*a);
 			int adjustedGapL=gapL+adjustment;
 			int adjustedDashL=dashL+adjustment;

 			int intervalcount= 3+2*a; 


 			int intervalLength=adjustedDashL;
 			position1.set (0,(float)click1.get(0));
 			position1.set (1,(float)click1.get(1));

 			position2.set(0,position1.get(0)+(intervalLength*direction.get(0)));
 			position2.set(1,position1.get(1)+(intervalLength*direction.get(1)));	
 			System.out.println("direction: ("+direction.get(0) +","+direction.get(1)+")");
 			System.out.println("intervallength:"+ (intervalLength));

 			for(int i=0; i<intervalcount; i++){
 				System.out.println("POS1: ("+position1.get(0) +","+position1.get(1)+")");
				System.out.println("POS2: ("+position2.get(0) +","+position2.get(1)+")");

				
				if(i%2==0){
					g.drawLine(round(position1.get(0)), round(position1.get(1)), round(position2.get(0)), round(position2.get(1)));
					intervalLength=adjustedGapL;
				}
				else 
					intervalLength=adjustedDashL;
				if(remaining>0){
					intervalLength++;
					remaining--;
				}
				position1.set(0, position2.get(0));
				position1.set(1, position2.get(1));
 				position2.set(0,position1.get(0)+(intervalLength*direction.get(0)));
 				position2.set(1,position1.get(1)+(intervalLength*direction.get(1)));

 			}




 	}
	public void paint(Graphics g){
	 	Dimension d=getSize();

	 	if(firstclick && !secondclick){
	 		 		System.out.println("draw rect at: ("+click1.get(0) +","+click1.get(1)+")");
	 		 		g.fillRect((int)(click1.get(0))-2, (int)(click1.get(1))-2, 4, 4);
	 		 	}
	 	else if(secondclick&&firstclick)
	 		drawDashedLine(g);

	 	

 	}

}