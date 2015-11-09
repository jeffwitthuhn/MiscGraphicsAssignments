/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.geom.Point2D;


public final class MathHelpers {
  private MathHelpers () {}

  public static double crossProduct2D(Point2D vec1, Point2D vec2) {
    return (
      (vec1.getX() * vec2.getY()) -
      (vec1.getY() * vec2.getX())
    );
  }

  public static boolean isPointAfterCCWAngularly(Point2D center, Point2D direction, Point2D compareTo) {
    Point2D vec1 = new Point2D.Double(
      compareTo.getX() - center.getX(),
      compareTo.getY() - center.getY()
    );
    Point2D vec2 = new Point2D.Double(
      direction.getX() - center.getX(),
      direction.getY() - center.getY()
    );
    
    double cross = crossProduct2D(vec1, vec2);
    return (cross > 0);
  }
  
  public static boolean isPointBetweenAngularly(Point2D center, Point2D direction, Point2D ref1, Point2D ref2) {
    Point2D vec1 = new Point2D.Double(
      ref1.getX() - center.getX(),
      ref1.getY() - center.getY()
    );
    Point2D vec2 = new Point2D.Double(
      ref2.getX() - center.getX(),
      ref2.getY() - center.getY()
    );
    Point2D dirVec = new Point2D.Double(
      direction.getX() - center.getX(),
      direction.getY() - center.getY()
    );
    
    double kDirection = crossProduct2D(vec1, vec2);
    double direction1 = crossProduct2D(vec1, dirVec);
    double direction2 = crossProduct2D(dirVec, vec2);

    if (kDirection > 0) {
      return (direction1 > 0 && direction2 > 0);
    }
    else if (kDirection < 0) {
      return (direction1 < 0 && direction2 < 0);
    }
    else {
      return false;
    }
  }
}

