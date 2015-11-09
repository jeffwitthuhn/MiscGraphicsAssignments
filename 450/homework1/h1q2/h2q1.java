//CSCI 450-1 
//Jeff Witthuhn
//Assignment 2.1
//draw the new triangle thing
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class h2q1 extends Frame{
public static void main(String[] args){new h2q1();}
 public int width=700;
 public int height=500; 
 public int margin=50; 
 h1q2(){
 	super ("h2q1 Triangles!");
 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
 		pack();
 		Insets boarder = getInsets();
 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
		add("Center", new Triangles(margin, boarder));
		show();
	}

 }


}

 class Triangles extends Canvas{
 	int margin;
 	Insets boarder; 
 	int height; 
 	bool firstclick=false; 
 	bool secondclick=false; 
 	int click1X, click1Y, click2X, click2Y; 

 	Triangles(int _margin, Insets _boarder){
 		margin=_margin;
 		boarder=_boarder;
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
					if(firstclick=false){
						float click1X = fx(evt.getX());
						float click1Y = fy(evt.getY());
						System.out.println("click 1: ("+click1X +","+click1Y+")");

						firstclick=true;
					}
					else if(secondclick=false){
						float click2X = fx(evt.getX());
						float click2Y = fy(evt.getY());
						System.out.println("click 2: ("+click2X +","+click2Y+")");
						secondclick=true;
						height=(int)sqrt(pow(click2X-click1X,2)+pow(click2Y-click1Y,2));
						System.out.println("approximate distance: "+height);

					}
					repaint();
					}
				}
			}

 	
 	public void drawSingleTriangle(Graphics g, int x, int y, int w, int h){
 			g.drawLine(x,y,x+w, y);
	 		g.drawLine(x,y, x+w/2, y+h);
	 		g.drawLine(x+w, y, x+w/2, y+h);

 	}
 	public void drawTriangles(Graphics g, int x, int y, int w, Dimension d){
 		int numberOfHorizontalTriangles=(int)((d.width-boarder.left-boarder.right-margin-margin)/w);
 		int numberOfVerticalTriangles=(int)((d.height-boarder.top-boarder.bottom-margin-margin)/w);
 		boolean middleRow=false; 
 		for(int j=0; j<numberofVerticalTriangles){
 			if(middleRow){
 				for(int i=0; i<(numberofHorizontalTriangles-1); i++)
 					drawSingleTriangle(g,x+i*w+w/2, y+j*height,w,height);
 				middleRow=false;
 			}
 			else{
 				for(int i=0; i<(numberofHorizontalTriangles); i++)
 					drawSingleTriangle(g,x+i*w, y+j*height,w,height);
 				middleRow=true;
 			}

 		}

	}
	public void paint(Graphics g){
	 	Dimension d=getSize();
	 	if(firstclick && !secondclick)
	 		g.drawRect(new int(click1X), new int(click1Y), 4, 4);
	 	else if(secondclick)
 			drawTriangles(g, margin, margin, height, d);

 	}

}