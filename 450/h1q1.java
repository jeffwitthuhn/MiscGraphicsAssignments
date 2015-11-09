//CSCI 450-1 
//Jeff Witthuhn
//Assignment 1.1
//draw a red and yellow checker board
//specify the size of the square window and the margin.
import java.awt.*;
import java.awt.event.*;
//import java.util.Scanner;

public class h1q1 extends Frame{
 public static void main(String[] args){
 	int size = Integer.parseInt(args[0]);
 	int margin=Integer.parseInt(args[1]);

 	new h1q1(size, margin);
 }
 h1q1(int size, int margin){
 	super ("h1q1 Checker Board!");
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

 		setSize (size-boarder.top-boarder.bottom, size-boarder.left-boarder.right);
		add("Center", new CheckerBoard(size, margin));
		show();
	}

 }
 class CheckerBoard extends Canvas{
 	int size; 
 	int margin;

 	CheckerBoard(int _size, int _margin){
 		size=_size;
 		margin=_margin;
 	}

 	public void checker(Graphics g, int x, int y, int n, int w){
	//w=size of squares
	//n=size of board
	//xy topleft
		 for (int i=0; i<8; i++)
			for (int j=0; j<8; j++){
				g.setColor((i + n - j) % 2 == 0?
				Color.yellow : Color.red);
				g.fillRect(x + i * w, y + j * w, w, w);
			}
		}
 	public void paint(Graphics g){
  		Dimension d=getSize();
  		if(d.height>d.width)
  			

 		checker(g, margin, margin, d.height-2*margin, (d.height-2*margin)/8);

 	}
 }
