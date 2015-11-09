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
 public static void main(String[] args){
 	int width=700;
 	int height=400; 
 	int margin=15; 
 	new h1q1();
 }
 h1q1(){
 	super ("h1q2 Triangles!");
 	adddWindowListener(new WindowAdapter(){
 		public void windowClosing(WindowEvent e){System.exit(0);}});
 		pack();
 		Insets boarder = getInsets();
 		/*
 		System.out.println(boarder.top);
 		System.out.println(boarder.bottom);
 		System.out.println(boarder.left);
 		System.out.println(boarder.right);
		*/

 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
		add("Center", new Triangles(margin));
		show();
	}

 }
 class Triangles extends Canvas{
 
 	int margin;

 	Triangles(int _margin){
 		margin=_margin;
 	}

 	public void drawTriangles(Graphics g, int x, int y, int w){
	//w=size of triangles
	//xy topleft

 	double height = sqrt((1.25*pow(w, 2)));
		 for (int i=0; i<8; i++)
			for (int j=0; j<8; j++){
				g.fillRect(x + i * w, y + j * w, w, w);
			}
		}
 	public void paint(Graphics g){
 		Dimension d=getSize();
 		drawTriangles(g, margin, margin, d.height-2*margin, (d.height-2*margin)/8);

 	}
 }
