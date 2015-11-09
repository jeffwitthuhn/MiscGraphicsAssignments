//CSCI 450-1 
//Jeff Witthuhn
//Assignment 2.2
//line thing
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class h2q2 extends Frame{
static float s1, s2, i1, i2;

public static void main(String[] args){
		parseArgs(args);
		new h2q2(s1,i1,s2,i2);
		}
 public int width=500;
 public int height=500; 
 public int margin=20; 


 h2q2(float slope1, float intercept1, float slope2, float intercept2){
 	super ("h2q2 graph mapping");
 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
 		pack();
 		Insets boarder = getInsets();
 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
		add("Center", new Graph(slope1, intercept1, slope2, intercept2, boarder, margin, 10, 10, 0, 0));
		show();
	}
public static void parseArgs(String[] args){
	try {
		s1=Float.parseFloat(args[0]);
		i1=Float.parseFloat(args[1]);
		s2=Float.parseFloat(args[2]);
		i2=Float.parseFloat(args[3]);
	}
	catch(Exception e) {
		System.out.println("Usage: h1q1 [slope1] [intercept1] [slope2] [intercept2]");
	}
}
 }


 class Graph extends Canvas{
 	Insets boarder;
 	float slope1, slope2;
 	float intercept1, intercept2; 
 	float centerX;
 	float centerY;
 	float maxY;
 	float minY;
 	float maxX;
 	float minX;

 	int margin; 
 	Dimension d;
 	//int ppuY;
 	//int ppuX;
 	float ppu;
 	float unitsInX;
 	float unitsInY;
 	int centerXpx;
 	int centerYpx;
 	int maxXpx;
 	int minXpx;
 	int maxYpx;
 	int minYpx; 



 	Graph(float _slope1, float _intercept1, float _slope2, float _intercept2, Insets _boarder, int _margin, float _unitsInX, float _unitsInY, float _centerX, float _centerY){
 		boarder=_boarder;
 		centerX=_centerX;
 		centerY=_centerY;
 		unitsInX=_unitsInX;
 		unitsInY=_unitsInY;
 		slope1=_slope1;
 		slope2=_slope2;
 		intercept1=_intercept1;
 		intercept2=_intercept2;

 		
 		margin=_margin;

 		addMouseListener(new MouseAdapter(){
	 		public void mousePressed(MouseEvent evt){
						calcNewCenter(evt.getX(), evt.getY());
						repaint();
					}
			});
 	}
 	public int calculateRealValues(float slope,float intercept, float[][] intercepts){
 		float y1, y2, x1, x2;
 		y1=slope*minX+intercept;
 		y2=slope*maxX+intercept;
 		x1=(maxY-intercept)/slope;
 		x2=(minY-intercept)/slope;
 		int index=0;
 		if(maxY>y1&&minY<y1){
 			intercepts[index][0]=minX;
 			intercepts[index][1]=y1;
 			index++;
 		}
 		if(maxY>y2&&minY<y2){
 			intercepts[index][0]=maxX;
 			intercepts[index][1]=y2;
 			index++;
 		}
 		if(maxX>x1&&minX<x1){
 			intercepts[index][0]=maxY;
 			intercepts[index][1]=x1;
 			index++;
 		}
 		if(maxX>x2&&minX<x2){
 			intercepts[index][0]=minY;
 			intercepts[index][1]=x2;
 			index++;
 		}

 		return index;


 	}
   	public int calculateYPixel(float y){
   		int deltaY=(int)(y-centerY+unitsInY/2)*((int)ppu);
   		int yPixel=maxYpx-deltaY;
   		return yPixel;
   	}
   	public int calculateXPixel(float x){
   		int deltaX=(int)(x-centerX+unitsInX/2)*((int)ppu);
   		int xPixel=minXpx+deltaX;
   		return xPixel;
   	}
   	public void calcNewCenter(int clickX, int clickY){
   		int deltaX=clickX-margin-boarder.left;
   		int deltaY=clickY-margin-boarder.top;
   		centerX=centerX+(deltaX/ppu);
   		centerY=centerY-(deltaY/ppu);

   	}
   	public void drawOrigin(){
   		
   	}
 	public void drawGraph(Graphics g){
 		float windowInterceptsL1[][]=new float[2][2];
 		float windowInterceptsL2[][]=new float[2][2];
 		float x1L1, y1L1, x2L1, y2L1;
 		float x1L2, y1L2, x2L2, y2L2;
 		if(calculateRealValues(slope1, intercept1, windowInterceptsL1)>1)
 			{
 				x1L1=windowInterceptsL1[0][0];
 				y1L1=windowInterceptsL1[0][1];
 				x2L1=windowInterceptsL1[1][0];
 				y2L1=windowInterceptsL1[1][1];
 				g.drawLine(calculateXPixel(x1L1), calculateYPixel(y1L1), calculateXPixel(x2L1), calculateYPixel(y2L1));

 				
 				
 			}
 		if(calculateRealValues(slope2, intercept2, windowInterceptsL2)>1)
 			{
				x1L2= windowInterceptsL1[0][0];
 				y1L2=windowInterceptsL1[0][1];
 				x2L2=windowInterceptsL1[1][0];
 				y2L2=windowInterceptsL1[1][1];
 				g.drawLine(calculateXPixel(x1L2), calculateYPixel(y1L2), calculateXPixel(x2L2), calculateYPixel(y2L2));

 			}
 		}
 	public void paint(Graphics g){
 		d=getSize();
 		centerYpx=d.height/2+boarder.top+margin;
 		centerXpx=d.width/2+boarder.left+margin;
 		maxX=centerX+5;
 		minX=centerX-5;
 		maxY=centerY+5;
 		minY=centerY-5;
 		if(d.height-boarder.top-boarder.bottom>d.width-boarder.left-boarder.right){
 			maxXpx=d.width-margin-boarder.right;
 			minXpx=margin+boarder.left;
 			maxYpx=d.width+boarder.top+margin;
 			minYpx=margin+boarder.top;
 			ppu=((float)(d.width-boarder.left-boarder.right))/unitsInX;
 		}
 		else{
 			maxXpx=d.height-boarder.top+boarder.left+margin;
 			minXpx=margin+boarder.left;
 			maxYpx=d.height-boarder.bottom-margin;
 			minYpx=margin+boarder.top;
 			ppu=((float)(d.height-boarder.top-margin))/unitsInX;

 		}


	 	drawGraph(g);

	 	

 	}

 }