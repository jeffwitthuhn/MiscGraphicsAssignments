// RedRect.java: The largest possible rectangle in red.
import java.awt.*;
import java.awt.event.*;

public class RedRect extends Frame{
 public static void main(String[] args){new RedRect();}
	RedRect(){
		super("RedRect");
		addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){System.exit(0);}});
				setSize (200, 100);
				add("Center", new CvRedRect());
				show();
			}
			}
class CvRedRect extends Canvas{ 
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
	Dimension d = getSize();
	int maxX = d.width - 1, maxY = d.height - 1;
	g.drawString("d.width ="+d.width, 10, 30);
	g.drawString("d.height = " + d.height, 10, 60);
	g.setColor(Color.red);
	g.drawRect(0, 0, maxX, maxY);
	}
}

