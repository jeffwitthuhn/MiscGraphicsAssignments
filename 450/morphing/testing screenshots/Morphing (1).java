import java.util.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import java.lang.Math.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.abs;
public class Morphing {
	public static HashMap<Point2D, Point2D> calcMorph(List<Point2D> src, List<Point2D> trgt, Point2D centerSource, Point2D centerTarget){
		HashMap<Point2D,Point2D> lineSegments = new HashMap<Point2D,Point2D> (); 
		int i=0;
		int j=0;
		double anglei;
		double anglej; 
		Point2D sourcePt;
		Point2D targetPt;
		List<Point2D> source=src,target=trgt;
		double sourcePtX, sourcePtY, targetPtX, targetPtY; 
		int sizeS=source.size(); 
		int sizeT=target.size();
		double distanceFromi, distanceFromj;
		Vector<java.lang.Double> dirOrigin, dirSide;
		double lenOrigin, lenSide;
		for(int z=0; z<sizeS;z++)
			source.set(z, new Point2D.Double(src.get(z).getX()-centerSource.getX(), src.get(z).getY()-centerSource.getY()));
		for(int z=0; z<sizeT;z++)
			target.set(z, new Point2D.Double(trgt.get(z).getX()-centerTarget.getX(), trgt.get(z).getY()-centerTarget.getY()));


		while(i<sizeS||j<sizeT){
			System.out.println("(i j): "+ i +":::"+ j);

			anglei=calcAngle(source.get(i%sizeS).getX(), source.get(i%sizeS).getY());
			anglej=calcAngle(target.get(j%sizeT).getX(), target.get(j%sizeT).getY());
			dirOrigin = new Vector<java.lang.Double> (2);
			dirSide = new Vector<java.lang.Double> (2);
			if(anglei<anglej&&i<sizeS||j>=sizeT){
				//find the intersection of the line source[i],O and the line between  target[j] and target[((j-1+sizeT)%sizeT)]
				//calc distance
				lenOrigin=sqrt(pow((source.get(i%sizeS).getX()),2)+pow((source.get(i%sizeS).getY()),2));
				lenSide=sqrt(pow((target.get(j%sizeT).getY()-target.get((j-1+sizeT)%sizeT).getY()),2)+pow((target.get(j%sizeT).getX()-target.get((j-1+sizeT)%sizeT).getX()),2));
				//calc directions
				dirOrigin.add(((source.get(i%sizeS).getX())/lenOrigin));
				dirOrigin.add(((source.get(i%sizeS).getY())/lenOrigin));
				dirSide.add(((target.get(j%sizeT).getX()-target.get((j-1+sizeT)%sizeT).getX())/lenSide));
				dirSide.add(((target.get(j%sizeT).getY()-target.get((j-1+sizeT)%sizeT).getY())/lenSide));

				//calc distance to go from j to find target in he PjPj-1+sizeT direction 
				distanceFromj=(dirOrigin.get(0)*(source.get(i%sizeS).getY()-target.get(j%sizeT).getY())+dirOrigin.get(1)*(target.get(j%sizeT).getX()-source.get(i%sizeS).getX()))
							/(dirOrigin.get(0)*dirSide.get(1)-dirSide.get(0)*dirOrigin.get(1));
				//set source point and target points
				sourcePtX=source.get(i%sizeS).getX()+centerSource.getX();
				sourcePtY=source.get(i%sizeS).getY()+centerSource.getY();
				sourcePt= new Point2D.Double(sourcePtX, sourcePtY);

				targetPtX=target.get(j%sizeT).getX()+distanceFromj*dirSide.get(0)+centerTarget.getX();
				targetPtY=target.get(j%sizeT).getY()+distanceFromj*dirSide.get(1)+centerTarget.getY();
				targetPt= new Point2D.Double(targetPtX, targetPtY);
				i++;
			}
			else if(anglei>anglej&&j<sizeT||i>=sizeS){
				//find the intersection of the line target[j],O  and the line between  source[i] and source[(i-1+sizeS)%sizeS]
				//calc distance
				lenOrigin=sqrt(pow((target.get(j%sizeT).getX()),2)+pow((target.get(j%sizeT).getY()),2));
				lenSide=sqrt(pow((source.get(i%sizeS).getY()-source.get((i-1+sizeS)%sizeS).getY()),2)+pow((source.get(i%sizeS).getX()-source.get((i-1+sizeS)%sizeS).getX()),2));
				//calc directions
				dirOrigin.add(((target.get(j%sizeT).getX())/lenOrigin));
				dirOrigin.add(((target.get(j%sizeT).getY())/lenOrigin));
				dirSide.add(((source.get(i%sizeS).getX()-source.get((i-1+sizeS)%sizeS).getX())/lenSide));
				dirSide.add(((source.get(i%sizeS).getY()-source.get((i-1+sizeS)%sizeS).getY())/lenSide));

				//calc distance to go from j to find target in he PjPj-1+sizeT direction 
				//distanceFromj=(dirOrigin.get(0)*(source.get(i%sizeS).getY()-target.get(j%sizeT).getY())+dirOrigin.get(1)*(target.get(j%sizeT).getX()-source.get(i%sizeS).getX()))
				//			/(dirOrigin.get(0)*dirSide.get(1)-dirSide.get(0));
				distanceFromi=(dirOrigin.get(0)*(target.get(j%sizeT).getY()-source.get(i%sizeS).getY())+dirOrigin.get(1)*(source.get(i%sizeS).getX()-target.get(j%sizeT).getX()))
							/(dirOrigin.get(0)*dirSide.get(1)-dirSide.get(0)*dirOrigin.get(1));
				//set source point and target points
				targetPtX=target.get(j%sizeT).getX()+centerTarget.getX();
				targetPtY=target.get(j%sizeT).getY()+centerTarget.getY();
				targetPt= new Point2D.Double(targetPtX, targetPtY);

				sourcePtX=source.get(i%sizeS).getX()+distanceFromi*dirSide.get(0)+centerSource.getX();
				sourcePtY=source.get(i%sizeS).getY()+distanceFromi*dirSide.get(1)+centerSource.getY();
				sourcePt= new Point2D.Double(sourcePtX, sourcePtY);
				j++;
			}
			else{//(anglei==anglej)
				//no calculations needed, source[i] is to move towards target [j]
				sourcePtX=source.get(i%sizeS).getX()+centerSource.getX();
				sourcePtY=source.get(i%sizeS).getY()+centerSource.getY();
				sourcePt= new Point2D.Double(sourcePtX, sourcePtY);

				targetPtX=target.get(j%sizeT).getX()+centerTarget.getX();
				targetPtY=target.get(j%sizeT).getY()+centerTarget.getY();
				targetPt= new Point2D.Double(targetPtX, targetPtY);

				i++;
				j++;

			}
			//add sourcePt and targetPt to HashMap
			lineSegments.put(sourcePt,targetPt);
		}



		return lineSegments;
	}
	public static double calcAngle(double x, double y){
		//returns a value 0-2pi, assumed the points x,y lie on a circle centered at origin. 
		double angle;
		angle=Math.atan2(y,x);
		if(angle<0)
			angle=Math.PI + Math.PI + angle; 
		return angle;
	}

}

	/*TESTS*/
/*	
	System.out.println("angle of (1,0) is: "+ calcAngle(1.0,0.0));
	System.out.println("angle of (1,1) is: "+ calcAngle(1.0,1.0));
	System.out.println("angle of (1,2) is: "+ calcAngle(1.0,2.0));
	System.out.println("angle of (1,3) is: "+ calcAngle(1.0,3.0));
	System.out.println("angle of (1,4) is: "+ calcAngle(1.0,4.0));
	System.out.println("angle of (1,5) is: "+ calcAngle(1.0,5.0));
	System.out.println("angle of (1,6) is: "+ calcAngle(1.0,6.0));	
	System.out.println("angle of (0,1) is: "+ calcAngle(0.0,1.0));
	System.out.println("angle of (-1,0) is: "+ calcAngle(-1.0,0.0));
	System.out.println("angle of (0,-1) is: "+ calcAngle(0.0,-1.0));
	System.out.println("angle of (1,-0.1) is: "+ calcAngle(1.0,-0.1));
	System.out.println("angle of (0,0) is: "+ calcAngle(0.0,0.0));
*/

	/*
 			System.out.println("cos pi/4 is "+ Math.cos(Math.PI/4));
 			System.out.println("cos pi/2 is "+ Math.cos(Math.PI/2));
 			System.out.println("cos 3*pi/4 is "+ Math.cos(3*Math.PI/4));
 			System.out.println("cos pi is "+ Math.cos(Math.PI));
 			System.out.println("cos pi*5/4 is "+ Math.cos(5*Math.PI/4));
 			System.out.println("cos 6*pi/4 is "+ Math.cos(6*Math.PI/4));
 			System.out.println("cos 7*pi/4 is "+ Math.cos(7*Math.PI/4));
 			System.out.println("cos 2pi "+ Math.cos(2*Math.PI));
 			System.out.println("sin pi/4 is "+ Math.sin(Math.PI/4));
 			System.out.println("sin pi/2 is "+ Math.sin(Math.PI/2));
 			System.out.println("sin 3*pi/4 is "+ Math.sin(3*Math.PI/4));
 			System.out.println("sin pi is "+ Math.sin(Math.PI));
 			System.out.println("sin pi*5/4 is "+ Math.sin(5*Math.PI/4));
 			System.out.println("sin 6*pi/4 is "+ Math.sin(6*Math.PI/4));
 			System.out.println("sin 7*pi/4 is "+ Math.sin(7*Math.PI/4));
 			System.out.println("sin 2pi "+ Math.sin(2*Math.PI));
 			*/

 			/*
				/*
 		int sizeT = target.size();
 		int j=0;
 		System.out.println("size of target is:  "+ target.size());
 		System.out.println("j-1%size is "+ (j-1+sizeT)%sizeT);


 		double lenSide= sqrt(pow((target.get(j).getY()-target.get((j-1+sizeT)%sizeT).getY()),2)+pow((target.get(j).getX()-target.get((j-1+sizeT)%sizeT).getX()),2));
		*/
		/*
		g.setColor(Color.yellow);
 		for(int i=0; i<source.size(); i++)
 			g.drawLine((int)source.get(i).getX()+d.width/2, (int)source.get(i).getY()+d.height/2,
 			 d.width/2,d.height/2);
 		for(int i=0; i<target.size(); i++)
 			g.drawLine((int)target.get(i).getX()+d.width/2, (int)target.get(i).getY()+d.height/2,
 			 d.width/2,d.height/2);
 		*/

 			
