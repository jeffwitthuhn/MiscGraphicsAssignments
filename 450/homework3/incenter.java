//CSCI 450-1 
//Jeff Witthuhn
//homework3 incenters
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.abs;
 

public class incenter extends Frame{
	public static void main(String[] args){
		new incenter();
	}
	 public int width=700;
	 public int height=500; 
	 public int margin=20; 
	 incenter(){
	 	super ("incenters!");
	 	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
	 		pack();
	 		Insets boarder = getInsets();
	 		setSize (width-boarder.top-boarder.bottom, height-boarder.left-boarder.right);
			add("Center", new incenterGraphics(boarder));
			show();
		}
 }




 class incenterGraphics extends Canvas{
 	Insets boarder; 
 	double lenAB, lenBC, lenCA;
 	double d1Bisector, d2Bisector, d1Sides, d2Sides; 
 	double distanceFromSides, sidesRadiusdouble;
 	double area;
 	boolean clockwise; 
 	int bisectorRadius, sidesRadius; 
 	Vector<Integer> pointA;//= new Vector<Integer>(2);
 	Vector<Integer> pointB;//= new Vector<Integer>(2);
 	Vector<Integer> pointC;//= new Vector<Integer>(2);
 	Vector<Double> pointIncenterBisector;
 	Vector<Double> pointIncenterSides;
 	Vector<Double> dirAB;//= new Vector<Integer>(2);
 	Vector<Double> dirAC;//= new Vector<Integer>(2);
 	Vector<Double> dirBC;//= new Vector<Integer>(2);
 	Vector<Double> dirBA;//= new Vector<Integer>(2);
 	Vector<Double> dirAngleA;
 	Vector<Double> dirAngleB;


 	int clickCount;
 	incenterGraphics(Insets _boarder){
 		boarder=_boarder;
 		clickCount=0; 
 		 addMouseListener(new MouseAdapter(){
 		  public void mousePressed(MouseEvent evt){
					if(clickCount%3==0){
						pointIncenterBisector= new Vector<Double>(2);
						pointIncenterSides= new Vector<Double>(2);
						pointA= new Vector<Integer>(2);
						pointB= new Vector<Integer>(2);
						pointC= new Vector<Integer>(2);
						dirAB= new Vector<Double>(2);
						dirAC= new Vector<Double>(2);
						dirBC= new Vector<Double>(2);
						dirBA= new Vector<Double>(2);
						dirAngleA=new Vector<Double>(2);
						dirAngleB= new Vector<Double>(2);


						pointA.add(evt.getX());
						pointA.add(evt.getY());
						//System.out.println("click 1: ("+evt.getX() +","+evt.getY()+")");
						System.out.println("pointA: ("+pointA.get(0) +","+pointA.get(1)+")");
						repaint();
						clickCount++;
					}
					else if(clickCount%3==1){
						pointB.add(evt.getX());
						pointB.add(evt.getY());
						System.out.println("pointB: ("+pointB.get(0) +","+pointB.get(1)+")");
						clickCount++;
						repaint();

					}
					else if(clickCount%3==2){
						pointC.add(evt.getX());
						pointC.add(evt.getY());
						System.out.println("pointC: ("+pointC.get(0) +","+pointC.get(1)+")");
						clickCount++;
						repaint();
					}
				}
			});
 		}
				
		
 	public void calculateIncenters(){

 		lenAB=sqrt(pow((pointA.get(0)-pointB.get(0)),2)+pow((pointA.get(1)-pointB.get(1)),2));
 		lenBC=sqrt(pow((pointB.get(0)-pointC.get(0)),2)+pow((pointB.get(1)-pointC.get(1)),2));
 		lenCA=sqrt(pow((pointC.get(0)-pointA.get(0)),2)+pow((pointC.get(1)-pointA.get(1)),2));

 		area = ( (pointB.get(0)-pointA.get(0))*(pointB.get(1)+pointA.get(1))
 				+(pointC.get(0)-pointB.get(0))*(pointC.get(1)+pointB.get(1))
 				+(pointA.get(0)-pointC.get(0))*(pointA.get(1)+pointC.get(1))
 				)/2;
 		if(area>0)
 			clockwise=false;
 		else 
 			clockwise=true; 
 		area=abs(area);
 		sidesRadiusdouble=(area/((lenAB+lenCA+lenBC)/2));
 		sidesRadius=(int)sidesRadiusdouble;
 		System.out.println("sidesRadius is: ("+sidesRadius+")");

 		dirAB.add((double)(pointA.get(0)-pointB.get(0))/lenAB);
 		dirAB.add((double)(pointA.get(1)-pointB.get(1))/lenAB);
		dirAC.add((double)(pointA.get(0)-pointC.get(0))/lenCA);
		dirAC.add((double)(pointA.get(1)-pointC.get(1))/lenCA);
		dirBC.add((double)(pointB.get(0)-pointC.get(0))/lenBC);
		dirBC.add((double)(pointB.get(1)-pointC.get(1))/lenBC);
		dirBA.add((double)(pointB.get(0)-pointA.get(0))/lenAB);
		dirBA.add((double)(pointB.get(1)-pointA.get(1))/lenAB);
		dirAngleA.add((dirAC.get(0)+dirAB.get(0))/2);
		dirAngleA.add((dirAC.get(1)+dirAB.get(1))/2);
		dirAngleB.add((dirBC.get(0)+dirBA.get(0))/2);
		dirAngleB.add((dirBC.get(1)+dirBA.get(1))/2);
		if(!clockwise){
			d2Sides=((pointB.get(0))*(dirAC.get(1))-(pointB.get(1)*dirAC.get(0))
				+(pointA.get(1))*(dirAC.get(0))-(pointA.get(0))*(dirAC.get(1))
				+(sidesRadiusdouble)*(dirAC.get(1))*((-1)*dirBC.get(1)-dirAC.get(1))
				-(sidesRadiusdouble)*(dirAC.get(0))*(dirBC.get(0)-(-1)*dirAC.get(0)))
					/((dirBC.get(1)*dirAC.get(0))-(dirBC.get(0)*dirAC.get(1)));
			pointIncenterSides.add((pointB.get(0))+(d2Sides)*(dirBC.get(0))
										+(-1)*(sidesRadiusdouble)*(dirBC.get(1)));
			pointIncenterSides.add((pointB.get(1))+(d2Sides)*(dirBC.get(1))
										+(sidesRadiusdouble)*(dirBC.get(0)));
		}
		else {
			d2Sides=((pointB.get(0))*(dirAC.get(1))-(pointB.get(1)*dirAC.get(0))
				+(pointA.get(1))*(dirAC.get(0))-(pointA.get(0))*(dirAC.get(1))
				+(sidesRadiusdouble)*(dirAC.get(1))*(dirBC.get(1)-(-1)*dirAC.get(1))
				-(sidesRadiusdouble)*(dirAC.get(0))*((-1)*dirBC.get(0)-dirAC.get(0)))
					/((dirBC.get(1)*dirAC.get(0))-(dirBC.get(0)*dirAC.get(1)));
			pointIncenterSides.add((pointB.get(0))+(d2Sides)*(dirBC.get(0))
										+(sidesRadiusdouble)*(dirBC.get(1)));
			pointIncenterSides.add((pointB.get(1))+(d2Sides)*(dirBC.get(1))
										+(-1)*(sidesRadiusdouble)*(dirBC.get(0)));

		}
		System.out.println("incenter from sides method is: ("+pointIncenterSides.get(0)+","+pointIncenterSides.get(1)+")");

		distanceFromSides= abs((pointB.get(0)-pointA.get(0))*(pointA.get(1)-pointIncenterSides.get(1))
								-(pointA.get(0)-pointIncenterSides.get(0))*(pointB.get(1)-pointA.get(1)))
							/sqrt(pow((pointB.get(0)-pointA.get(0)),2)+ pow((pointB.get(1)-pointA.get(1)),2));
		System.out.println("incenterSides distance from AB ("+distanceFromSides+")");

		distanceFromSides= abs((pointC.get(0)-pointA.get(0))*(pointA.get(1)-pointIncenterSides.get(1))
								-(pointA.get(0)-pointIncenterSides.get(0))*(pointC.get(1)-pointA.get(1)))
							/sqrt(pow((pointC.get(0)-pointA.get(0)),2)+ pow((pointC.get(1)-pointA.get(1)),2));
		System.out.println("incenterSides distance from AC ("+distanceFromSides+")");

		distanceFromSides= abs((pointB.get(0)-pointC.get(0))*(pointC.get(1)-pointIncenterSides.get(1))
								-(pointC.get(0)-pointIncenterSides.get(0))*(pointB.get(1)-pointC.get(1)))
							/sqrt(pow((pointB.get(0)-pointC.get(0)),2)+ pow((pointB.get(1)-pointC.get(1)),2));
		System.out.println("incenterSides distance from CB ("+distanceFromSides+")");


		d2Bisector = ((dirAngleA.get(0))*(pointB.get(1)-pointA.get(1))+(dirAngleA.get(1))*(pointA.get(0)-pointB.get(0)))
						/((dirAngleB.get(0))*(dirAngleA.get(1))-(dirAngleB.get(1))*(dirAngleA.get(0)));
		pointIncenterBisector.add((pointB.get(0))+(d2Bisector)*(dirAngleB.get(0)));
		pointIncenterBisector.add((pointB.get(1))+(d2Bisector)*(dirAngleB.get(1)));
		System.out.println("incenter from bisector method is: ("+pointIncenterBisector.get(0)+","+pointIncenterBisector.get(1)+")");

		distanceFromSides= abs((pointB.get(0)-pointA.get(0))*(pointA.get(1)-pointIncenterBisector.get(1))
								-(pointA.get(0)-pointIncenterBisector.get(0))*(pointB.get(1)-pointA.get(1)))
							/sqrt(pow((pointB.get(0)-pointA.get(0)),2)+ pow((pointB.get(1)-pointA.get(1)),2));
		System.out.println("incenterBisector distance from AB ("+distanceFromSides+")");

		distanceFromSides= abs((pointC.get(0)-pointA.get(0))*(pointA.get(1)-pointIncenterBisector.get(1))
								-(pointA.get(0)-pointIncenterBisector.get(0))*(pointC.get(1)-pointA.get(1)))
							/sqrt(pow((pointC.get(0)-pointA.get(0)),2)+ pow((pointC.get(1)-pointA.get(1)),2));
		System.out.println("incenterBisector distance from AC ("+distanceFromSides+")");

		distanceFromSides= abs((pointB.get(0)-pointC.get(0))*(pointC.get(1)-pointIncenterBisector.get(1))
								-(pointC.get(0)-pointIncenterBisector.get(0))*(pointB.get(1)-pointC.get(1)))
							/sqrt(pow((pointB.get(0)-pointC.get(0)),2)+ pow((pointB.get(1)-pointC.get(1)),2));
		System.out.println("incenterBisector distance from CB ("+distanceFromSides+")");

		bisectorRadius=(int)(distanceFromSides);







 	}
 	public void drawStuff(Graphics g){
 		g.setColor(Color.white);
 		g.drawLine(pointA.get(0), pointA.get(1), pointB.get(0), pointB.get(1));
 		g.drawLine(pointA.get(0), pointA.get(1), pointC.get(0), pointC.get(1));
 		g.drawLine(pointC.get(0), pointC.get(1), pointB.get(0), pointB.get(1));
 		g.setColor(Color.green);
 		g.drawOval((int)round(pointIncenterBisector.get(0))-bisectorRadius, (int)round(pointIncenterBisector.get(1))-bisectorRadius,2*bisectorRadius,2*bisectorRadius);
 		g.setColor(Color.red);
 		g.drawOval((int)round(pointIncenterSides.get(0))-sidesRadius, (int)round(pointIncenterSides.get(1))-sidesRadius,2*sidesRadius,2*sidesRadius);
 	}	
	public void paint(Graphics g){
	 	Dimension d=getSize();
	 	g.setColor(Color.black);
	 	g.fillRect(0,0,d.width,d.height);
	 	if(clickCount%3==0&&clickCount>0){
	 		calculateIncenters();
	 		drawStuff(g);
	 		//draw incenter circles!
	 	}
	 	

 	}

}