//CSCI 450-1 
//Jeff Witthuhn
//Assignment 1.2
//draw the triangle thing
import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

//import java.util.Scanner;

public class h1q2 extends Frame{
 public static void main(String[] args){new h1q2();}
 public int width=700;
 public int height=500; 
 public int margin=50; 
 h1q2(){
 	super ("h1q2 Triangles!");
 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
 		pack();
 		Insets boarder = getInsets();
 		/*
 		System.out.println(boarder.top);
 		System.out.println(boarder.bottom);
 		System.out.println(boarder.left);
 		System.out.println(boarder.right);
		*/

 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
		add("Center", new Triangles(margin, boarder));
		show();
	}

 }
 class Triangles extends Canvas{
 
 	int margin;
 	Insets boarder; 

 	Triangles(int _margin, Insets _boarder){
 		margin=_margin;
 		boarder=_boarder;
 	}
 	public void drawSingleTriangle(Graphics g, int x, int y, int w, int h, boolean flip){
 		//draws an upside down triangle, top left is (x,y) 
 		//flip==true will just draw straight lines, finishing most rightside up triangles
 		//w is the length of a side of the equliateral triangle
 		//h is height of the equlateral triangle
 		if (flip==false){
	 		g.drawLine(x,y,x+w, y);
	 		g.drawLine(x,y, x+w/2, y+h);
	 		g.drawLine(x+w, y, x+w/2, y+h);
	 	}
	 	else {
	 		//g.drawLine(x,y, x+w/2, y-h);
	 		g.drawLine(x,y, x+w, y);
	 		//g.drawLine(x+w,y,x+w/2,y-h);
	 	}

 	}
 	public void drawTriangles(Graphics g, int x, int y, int w, Dimension d){
	//w=size of triangles
	//xy topleft
 	double dheight = sqrt((1.25*pow(w, 2)));
 	int height = (int) dheight;/
 	 /*
		Upside down triangles
 	 */
		for (int i=0; i<7; i++){
			drawSingleTriangle(g,x+i*w, y+0*height, w, height, false);
		}
		for (int i=0; i<6; i++){
			drawSingleTriangle(g,x+i*w+w/2, y+1*height, w, height, false);

		}
		for (int i=0; i<7; i++){
			drawSingleTriangle(g,x+i*w, y+2*height, w, height, false);

		}
	 /*
	  fill last two side lines and bottom parts of te bottom triangles
	 */
	 	for (int i=0; i<6; i++){
			//drawSingleTriangle(g,x+i*w+w/2, y+1*height, w, height, true);

	 	}
	 	g.drawLine(x,y+2*height, x+w/2, y+height);
	 	g.drawLine(x+7*w, y+2*height, x+7*w-w/2, y+height );
	 	
	 	for (int i=0; i<6; i++){
	 		drawSingleTriangle(g,x+i*w+w/2, y+3*height, w, height, true);

	 	}
	}
 	public void paint(Graphics g){
 		Dimension d=getSize();
 		drawTriangles(g, margin, margin, (d.width-2*margin)/7, d);

 	}
 }
