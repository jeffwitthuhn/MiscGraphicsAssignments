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
 	Vector<Float> lengths= new Vector<Float> (1);
 	Vector<Integer> click1= new Vector<Integer>(2);
 	Vector<Integer> click2= new Vector<Integer>(2);
 	int dashL, gapL;
 	int clickCount;
 	Lines(Insets _boarder, int _dashL, int _gapL){
 		boarder=_boarder;
 		dashL= _dashL; 
 		gapL= _gapL; 
 		clickCount=0; 
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
					if(clickCount%2==0){
						click1.add(evt.getX());
						click1.add(evt.getY());
						//System.out.println("click 1: ("+evt.getX() +","+evt.getY()+")");
						System.out.println("click 1: ("+click1.get(clickCount) +","+click1.get(clickCount+1)+")");
						repaint();
						clickCount++;
					}
					else if(clickCount%2==1){
						click2.add(evt.getX());
						click2.add(evt.getY());
						System.out.println("click 2: ("+click2.get(clickCount-1) +","+click2.get(clickCount)+")");
						clickCount++;
						lengths.add((float)sqrt(pow(click2.get(clickCount-2)-click1.get(clickCount-2),2)+pow(click2.get(clickCount-1)-click1.get(clickCount-1),2)));
						System.out.println("approximate distance: "+lengths.get(clickCount/2-1));
						repaint();


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
 		//	System.out.println("enter j for loop");
 		//	System.out.println("clickCount is: "+clickCount);

 			for(int j=0, k=0; j+1<clickCount; j+=2, k++){
 				 //	System.out.println("j="+j);
		 			direction.set(0, (float)(click2.get(j)-click1.get(j))/lengths.get(k));
		 			direction.set(1, (float)(click2.get(j+1)-click1.get(j+1))/lengths.get(k));
		 			//length-(gapL+2*dashL) = a(gapL+dashL)+b
		 			int a=(int) (lengths.get(k)-(gapL+2*dashL))/(gapL+dashL);
		 			int b=(int) (lengths.get(k)-(gapL+2*dashL))%(gapL+dashL);
		 			int adjustment=b/(3+2*a);
		 			int remaining=b%(3+2*a);
		 			int adjustedGapL=gapL+adjustment;
		 			int adjustedDashL=dashL+adjustment;
		 			int intervalcount= 3+2*a; 
		 			int intervalLength=adjustedDashL;
		 			position1.set (0,(float)click1.get(j));
		 			position1.set (1,(float)click1.get(j+1));

		 			position2.set(0,position1.get(0)+(intervalLength*direction.get(0)));
		 			position2.set(1,position1.get(1)+(intervalLength*direction.get(1)));	
		 			//System.out.println("direction: ("+direction.get(0) +","+direction.get(1)+")");
		 			//System.out.println("intervallength:"+ (intervalLength));
		 			//System.out.println("enter i for loop:");

		 			for(int i=0; i<intervalcount; i++){
		 				// System.out.println("i="+i);

		 				//System.out.println("POS1: ("+position1.get(0) +","+position1.get(1)+")");
						//System.out.println("POS2: ("+position2.get(0) +","+position2.get(1)+")");

						
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



 	}
	public void paint(Graphics g){
	 	Dimension d=getSize();
		//System.out.println("click 1: ("+click1.get(clickCount-2) +","+click1.get(clickCount-1)+")");

	 	if(clickCount%2==1){
	 		 		g.fillRect((int)(click1.get(clickCount-1))-2, (int)(click1.get(clickCount))-2, 4, 4);
	 		 	}
	 	if(clickCount>=2){
	 		drawDashedLine(g);
	 	}

	 	

 	}

}