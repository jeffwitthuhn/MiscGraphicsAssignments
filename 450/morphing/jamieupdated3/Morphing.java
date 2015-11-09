import java.util.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import java.lang.Math.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.round;
import static java.lang.Math.abs;

public class Morphing {
    private HashMap<Point2D, Point2D> morph; 
    private List<Point2D> source;
    private List<Point2D> target;
    private Point2D sourceCenter;
    private Point2D targetCenter;
    private boolean sourceSet, targetSet,sourceCenterSet, targetCenterSet;
    Morphing(){
        calculated=sourceSet=targetSet=sourceCenterSet=targetCenterSet=false;
    } 
    void setSourcePolygon(List<Point2D> poly){
        source=poly;
        sourceSet=true; 
        calculated=false; 
    }
    void setTargetPolygon(List<Point2D> poly){
        target=poly;
        targetSet=true; 
        calculated=false; 
    }
    void setSourceCenter(Point2D center){
        sourceCenter=center;
        sourceCenterSet=true; 
        calculated=false; 
    }
    void setTargetCenter(Point2D center){
        targetCenter=center;
        targetCenterSet=true; 
        calculated=false; 
    }
    List<Point2D> getSourcePolygon(){
        return source;
    }
    List<Point2D> getTargetPolygon(){
        return target;
    }
    Point2D getTargetCenter(){
        return targetCenter;
    }
    Point2D getSourceCenter(){
        return sourceCenter;
    }
    HashMap<Point2D, Point2D> getMorph(){
        if(!calculated){
            morph=calcMorph(source,target,sourceCenter,targetCenter);
            calculated=true;
        }
       
        return morph; 
    }

    



    public static HashMap<Point2D, Point2D> calcMorph(List<Point2D> src, List<Point2D> trgt, Point2D centerSource, Point2D centerTarget) {
        HashMap<Point2D, Point2D> lineSegments = new HashMap<Point2D, Point2D>();
        int i = 0;
        int j = 0;
        double anglei;
        double anglej;
        Point2D sourcePt;
        Point2D targetPt;
		//maybe pointer to same object??
        //List<Point2D> source=src,target=trgt;
        List<Point2D> source = new ArrayList<Point2D>(src.size()), target = new ArrayList<Point2D>(trgt.size());

        double sourcePtX, sourcePtY, targetPtX, targetPtY;

        double distanceFromi, distanceFromj;
        Vector<java.lang.Double> dirOrigin, dirSide;
        double lenOrigin, lenSide;
        for (int z = 0; z < src.size(); z++) {
            source.add(new Point2D.Double(src.get(z).getX() - centerSource.getX(), src.get(z).getY() - centerSource.getY()));
        }
        for (int z = 0; z < trgt.size(); z++) {
            target.add(new Point2D.Double(trgt.get(z).getX() - centerTarget.getX(), trgt.get(z).getY() - centerTarget.getY()));
        }

        int sizeS = source.size();
        int sizeT = target.size();
        Collections.sort(source, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D one, Point2D two) {
        // assume positive y is up and vertices centered around origin
                //a and b will range from 0 to 2*PI
                //not tested but calcAngle is tested.
                double a = calcAngle(one.getX(), one.getY());
                double b = calcAngle(two.getX(), two.getY());
                if (a < b) {
                    return -1;
                } else if (a > b) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Collections.sort(target, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D one, Point2D two) {
        // assume positive y is up and vertices centered around origin
                //a and b will range from 0 to 2*PI
                //not tested but calcAngle is tested.
                double a = calcAngle(one.getX(), one.getY());
                double b = calcAngle(two.getX(), two.getY());
                if (a < b) {
                    return -1;
                } else if (a > b) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        while (i < sizeS || j < sizeT) {

            anglei = calcAngle(source.get(i % sizeS).getX(), source.get(i % sizeS).getY());
            anglej = calcAngle(target.get(j % sizeT).getX(), target.get(j % sizeT).getY());
            dirOrigin = new Vector<java.lang.Double>(2);
            dirSide = new Vector<java.lang.Double>(2);
            if (anglei < anglej && i < sizeS || j >= sizeT) {
				//find the intersection of the line source[i],O and the line between  target[j] and target[((j-1+sizeT)%sizeT)]
                //calc distance
                lenOrigin = sqrt(pow((source.get(i % sizeS).getX()), 2) + pow((source.get(i % sizeS).getY()), 2));
                lenSide = sqrt(pow((target.get(j % sizeT).getY() - target.get((j - 1 + sizeT) % sizeT).getY()), 2) + pow((target.get(j % sizeT).getX() - target.get((j - 1 + sizeT) % sizeT).getX()), 2));
                //calc directions
                dirOrigin.add(((source.get(i % sizeS).getX()) / lenOrigin));
                dirOrigin.add(((source.get(i % sizeS).getY()) / lenOrigin));
                dirSide.add(((target.get(j % sizeT).getX() - target.get((j - 1 + sizeT) % sizeT).getX()) / lenSide));
                dirSide.add(((target.get(j % sizeT).getY() - target.get((j - 1 + sizeT) % sizeT).getY()) / lenSide));

                //calc distance to go from j to find target in he PjPj-1+sizeT direction 
                distanceFromj = (dirOrigin.get(0) * (source.get(i % sizeS).getY() - target.get(j % sizeT).getY()) + dirOrigin.get(1) * (target.get(j % sizeT).getX() - source.get(i % sizeS).getX()))
                        / (dirOrigin.get(0) * dirSide.get(1) - dirSide.get(0) * dirOrigin.get(1));
                //set source point and target points
                sourcePtX = source.get(i % sizeS).getX() + centerSource.getX();
                sourcePtY = source.get(i % sizeS).getY() + centerSource.getY();
                sourcePt = new Point2D.Double(sourcePtX, sourcePtY);

                targetPtX = target.get(j % sizeT).getX() + distanceFromj * dirSide.get(0) + centerTarget.getX();
                targetPtY = target.get(j % sizeT).getY() + distanceFromj * dirSide.get(1) + centerTarget.getY();
                targetPt = new Point2D.Double(targetPtX, targetPtY);
                i++;
            } else if (anglei > anglej && j < sizeT || i >= sizeS) {
				//find the intersection of the line target[j],O  and the line between  source[i] and source[(i-1+sizeS)%sizeS]
                //calc distance
                lenOrigin = sqrt(pow((target.get(j % sizeT).getX()), 2) + pow((target.get(j % sizeT).getY()), 2));
                lenSide = sqrt(pow((source.get(i % sizeS).getY() - source.get((i - 1 + sizeS) % sizeS).getY()), 2) + pow((source.get(i % sizeS).getX() - source.get((i - 1 + sizeS) % sizeS).getX()), 2));
                //calc directions
                dirOrigin.add(((target.get(j % sizeT).getX()) / lenOrigin));
                dirOrigin.add(((target.get(j % sizeT).getY()) / lenOrigin));
                dirSide.add(((source.get(i % sizeS).getX() - source.get((i - 1 + sizeS) % sizeS).getX()) / lenSide));
                dirSide.add(((source.get(i % sizeS).getY() - source.get((i - 1 + sizeS) % sizeS).getY()) / lenSide));

				//calc distance to go from j to find target in he PjPj-1+sizeT direction 
                //distanceFromj=(dirOrigin.get(0)*(source.get(i%sizeS).getY()-target.get(j%sizeT).getY())+dirOrigin.get(1)*(target.get(j%sizeT).getX()-source.get(i%sizeS).getX()))
                //			/(dirOrigin.get(0)*dirSide.get(1)-dirSide.get(0));
                distanceFromi = (dirOrigin.get(0) * (target.get(j % sizeT).getY() - source.get(i % sizeS).getY()) + dirOrigin.get(1) * (source.get(i % sizeS).getX() - target.get(j % sizeT).getX()))
                        / (dirOrigin.get(0) * dirSide.get(1) - dirSide.get(0) * dirOrigin.get(1));
                //set source point and target points
                targetPtX = target.get(j % sizeT).getX() + centerTarget.getX();
                targetPtY = target.get(j % sizeT).getY() + centerTarget.getY();
                targetPt = new Point2D.Double(targetPtX, targetPtY);

                sourcePtX = source.get(i % sizeS).getX() + distanceFromi * dirSide.get(0) + centerSource.getX();
                sourcePtY = source.get(i % sizeS).getY() + distanceFromi * dirSide.get(1) + centerSource.getY();
                sourcePt = new Point2D.Double(sourcePtX, sourcePtY);
                j++;
            } else {//(anglei==anglej)
                //no calculations needed, source[i] is to move towards target [j]
                sourcePtX = source.get(i % sizeS).getX() + centerSource.getX();
                sourcePtY = source.get(i % sizeS).getY() + centerSource.getY();
                sourcePt = new Point2D.Double(sourcePtX, sourcePtY);

                targetPtX = target.get(j % sizeT).getX() + centerTarget.getX();
                targetPtY = target.get(j % sizeT).getY() + centerTarget.getY();
                targetPt = new Point2D.Double(targetPtX, targetPtY);

                i++;
                j++;

            }
            //add sourcePt and targetPt to HashMap
            lineSegments.put(sourcePt, targetPt);
        }

        return lineSegments;
    }

    public static double calcAngle(double x, double y) {
        //returns a value 0-2pi, assumed the points x,y lie on a circle centered at origin. 
        double angle;
        angle = Math.atan2(y, x);
        if (angle < 0) {
            angle = Math.PI + Math.PI + angle;
        }
        return angle;
    }

}
