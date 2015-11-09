import java.util.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.abs;
public class Wheel extends Frame{
	public static void main(String[] args){
		new Wheel();
	}
	 public int width=1000;
	 public int height=500; 
	 public int margin=20; 
	 Wheel(){
	 	super ("Wheel!");
	 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
	 		pack();
	 		Insets boarder = getInsets();
	 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
			add("Center", new WheelGraphics(boarder));
			show();
		}

}
class WheelGraphics extends Canvas implements Runnable{
	Insets boarder; 
    boolean moving; 
    int w,h;
    Thread thr = new Thread(this);
    Dimension d;
    Graphics gImage;
    Image image;
    double speed=0.2;
    float alpha = 0;
    float hubRad;
    float innerRad;
    float outerRad;
    float stickLength;
    float stickWidth;
    float startMidx,startMidy;
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
	WheelGraphics(Insets _boarder){
 		boarder=_boarder;
 		moving = false;
 		outerRad=150;
 		innerRad=130;
 		thr.start();
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
					moving = true; 		
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
 	    if(!moving)
	 		startMidx=outerRad;
	 	else
	 		startMidx=0-outerRad;

	 	startMidy=d.height/2;
	 	float wheel1alpha= alpha % (d.width*2);
	 	float wheel2alpha= (alpha-d.width) % (d.width *2);
	 	//drawWheel(alpha*2%(d.width*2));
	 	//rim
	 		gImage.setColor(Color.white);
	 		gImage.fillOval((int)(startMidx+wheel1alpha-outerRad),(int)(startMidy-outerRad),(int)outerRad*2,(int)outerRad*2);
	 		gImage.setColor(Color.red);
	 		gImage.drawOval((int)(startMidx+wheel1alpha-outerRad),(int)(startMidy-outerRad),(int)outerRad*2,(int)outerRad*2);
	 		gImage.setColor(Color.black);
	 		gImage.fillOval((int)(startMidx+wheel1alpha-innerRad),(int)(startMidy-innerRad),(int)innerRad*2,(int)innerRad*2);

	 		gImage.setColor(Color.white);
	 		gImage.fillOval((int)(startMidx+wheel2alpha-outerRad),(int)(startMidy-outerRad),(int)outerRad*2,(int)outerRad*2);
	 		gImage.setColor(Color.red);
	 		gImage.drawOval((int)(startMidx+wheel2alpha-outerRad),(int)(startMidy-outerRad),(int)outerRad*2,(int)outerRad*2);
	 		gImage.setColor(Color.black);
	 		gImage.fillOval((int)(startMidx+wheel2alpha-innerRad),(int)(startMidy-innerRad),(int)innerRad*2,(int)innerRad*2);
	 	//spokes
	 		gImage.setColor(Color.yellow);
	 		double spokex=innerRad;
	 		double spokey=0;
	 		double x=spokex;
	 		double y=spokey;
	 		double theta = speed/outerRad; 
			int increments = (int)(alpha/(speed));
			for (int i=0; i<increments; i++){
				x=spokex;
	 			y=spokey;
	 			spokex=Math.cos(theta)*x-Math.sin(theta)*y;
	 			spokey=Math.sin(theta)*x+Math.cos(theta)*y;
			}
	 		for (int i=0; i<6; i++){
	 			gImage.drawLine((int)(startMidx+wheel1alpha),(int)startMidy,(int)(spokex+wheel1alpha+startMidx), (int)(spokey+startMidy));
	 			x=spokex;
	 			y=spokey;
	 			spokex=(x- sqrt(3)*y)/2;
	 			spokey= (x*sqrt(3)+y)/2;
	 		}

	 		gImage.setColor(Color.yellow);
	 		spokex=innerRad;
	 		spokey=0;
	 		x=spokex;
	 		y=spokey;
	 		theta = speed/outerRad; 
		    increments = (int)(alpha/(speed));
			for (int i=0; i<increments; i++){
				x=spokex;
	 			y=spokey;
	 			spokex=Math.cos(theta)*x-Math.sin(theta)*y;
	 			spokey=Math.sin(theta)*x+Math.cos(theta)*y;
			}
	 		for (int i=0; i<6; i++){
	 			gImage.drawLine((int)(startMidx+wheel2alpha),(int)startMidy,(int)(spokex+wheel2alpha+startMidx), (int)(spokey+startMidy));
	 			x=spokex;
	 			y=spokey;
	 			spokex=(x- sqrt(3)*y)/2;
	 			spokey= (x*sqrt(3)+y)/2;
	 		}


	 	//hub
	 		gImage.setColor(Color.green);
	 		gImage.fillOval((int)(startMidx+wheel1alpha-10),(int)(startMidy-10),20,20);
	 		gImage.setColor(Color.green);
	 		gImage.fillOval((int)(startMidx+wheel2alpha-10),(int)(startMidy-10),20,20);
	 	
	 	//ground
		 	gImage.setColor(Color.white);
		 	gImage.drawLine(0, (int)(startMidy+outerRad), d.width,(int)(startMidy+outerRad));
	 	    g.drawImage(image, 0, 0, null);
	 		
 	}
 	void drawWheel(float wheelalpha){
 		//rim
	 		gImage.setColor(Color.white);
	 		gImage.fillOval((int)(startMidx+wheelalpha-outerRad),(int)(startMidy-outerRad),(int)outerRad*2,(int)outerRad*2);
	 		gImage.setColor(Color.red);
	 		gImage.drawOval((int)(startMidx+wheelalpha-outerRad),(int)(startMidy-outerRad),(int)outerRad*2,(int)outerRad*2);
	 		gImage.setColor(Color.black);
	 		gImage.fillOval((int)(startMidx+wheelalpha-innerRad),(int)(startMidy-innerRad),(int)innerRad*2,(int)innerRad*2);
	 		//SPOKES
	 		gImage.setColor(Color.yellow);
	 		double spokex=innerRad;
	 		double spokey=0;
	 		double x=spokex;
	 		double y=spokey;
	 		double x1;
	 		double y1;
	 		double x2=outerRad;
	 		double y2=0;
	 		double theta = speed/outerRad; 
			int increments = (int)(wheelalpha/(speed));
			for (int i=0; i<increments; i++){
				x=spokex;
	 			y=spokey;
	 			spokex=Math.cos(theta)*x-Math.sin(theta)*y;
	 			spokey=Math.sin(theta)*x+Math.cos(theta)*y;
	 			x=x2;
	 			y=y2;
	 			x2=Math.cos(theta)*x-Math.sin(theta)*y;
	 			y2=Math.sin(theta)*x+Math.cos(theta)*y;
			}
			x=spokex;
			y=spokey;
			for(int i=0; i<90; i++){
				gImage.setColor(new Color(200,200,200));

				gImage.drawLine((int)(startMidx+x+wheelalpha),(int)(startMidy+y),(int)(startMidx+x2+wheelalpha),(int)(startMidy+y));
				x1=x;
				y1=y;
				x=Math.cos(2*Math.PI/90)*x1-Math.sin(2*Math.PI/90)*y1;
	 			y=Math.sin(2*Math.PI/90)*x1+Math.cos(2*Math.PI/90)*y1;
	 			x1=x2;
				y1=y2;
				x2=Math.cos(2*Math.PI/90)*x1-Math.sin(2*Math.PI/90)*y1;
	 			y2=Math.sin(2*Math.PI/90)*x1+Math.cos(2*Math.PI/90)*y1;


			}
	 		for (int i=0; i<6; i++){
	 			gImage.drawLine((int)(startMidx+wheelalpha),(int)startMidy,(int)(spokex+wheelalpha+startMidx), (int)(spokey+startMidy));
	 			x=spokex;
	 			y=spokey;
	 			spokex=(x- sqrt(3)*y)/2;
	 			spokey= (x*sqrt(3)+y)/2;
	 		}
 	
 	//hub
	 	gImage.setColor(Color.green);
	 	gImage.fillOval((int)(startMidx+wheelalpha-10),(int)(startMidy-10),20,20);

	 }
}


