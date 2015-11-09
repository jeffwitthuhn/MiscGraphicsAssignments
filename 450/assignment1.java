import java.awt.*;
class myframe extends Frame //myframe is a subclass of Frame
	{
		public myFrame(string s){
			Super(s);
			add WindowListener(new WindowAdapter());
			{
				public void WindowClosing(WindowEvent e){
					System.exit(0);
				}
				SetSize(200,200);

			}
		}


	}
class myCanvas extends Canvas{
	//dont need another constructor
	public void paint(Graphics g){
		g.drawline(20,20, 60,60);

		}
}

public class mywindow{
	
	public static void main(string[] args){
		Canvas c = new myCanvas();
		Frame f= new myFrame();
		f.add(c);
		f.show(); 
	}



}
