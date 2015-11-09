/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;

/**
 * This creates a logical region in the Cartesian coordinate system
 * for mapping (x, y) coordinates to physical pixels on the screen.
 * It provides an abstract interface for different types of mappings
 * (isotropic, anisotropic, etc.)
 * @author Harlan Sang
 */
abstract public class CoordinateMap2D implements ComponentListener {
  public static final Point2D ORIGIN = new Point2D.Double(0, 0);
  
  private double rangeMinX;
  private double rangeMaxX;
  private double rangeMinY;
  private double rangeMaxY;
  private int totalPixelsX;
  private int totalPixelsY;
  
  /**
   * @param rangeMinX The minimum logical value of X that will be allowed within the viewport
   * @param rangeMaxX The maximum logical value of X that will be allowed within the viewport
   * @param rangeMinY The minimum logical value of Y that will be allowed within the viewport
   * @param rangeMaxY The maximum logical value of Y that will be allowed within the viewport
   */
  public CoordinateMap2D(double rangeMinX, double rangeMaxX, double rangeMinY, double rangeMaxY) {
    this.rangeMinX = rangeMinX;
    this.rangeMaxX = rangeMaxX;
    this.rangeMinY = rangeMinY;
    this.rangeMaxY = rangeMaxY;
  }
  
  /**
   * @return The total number number of pixels in the horizontal direction of the 
   *         attached Dimension object (via IDimensionObserver)
   */
  public int getTotalPixelsX() {
    return totalPixelsX;
  }
  
  /**
   * @return The total number number of pixels in the vertical direction of the 
   *         attached Dimension object (via IDimensionObserver)
   */
  public int getTotalPixelsY() {
    return totalPixelsY;
  }
  
  /**
   * @return The minimum logical value of X that will be allowed within the viewport
   */
  public double getRangeMinX() {
    return rangeMinX;
  }
  
  /**
   * @return The maximum logical value of X that will be allowed within the viewport
   */
  public double getRangeMaxX() {
    return rangeMaxX;
  }
  
  /**
   * @return The number of logical units that encompass the viewport horizontally
   */
  public double getRangeX() {
    return rangeMaxX - rangeMinX;
  }
  
  /**
   * @return The minimum logical value of Y that will be allowed within the viewport
   */
  public double getRangeMinY() {
    return rangeMinY;
  }
  
  /**
   * @return The maximum logical value of Y that will be allowed within the viewport
   */
  public double getRangeMaxY() {
    return rangeMaxY;
  }
  
  /**
   * @return The number of logical units that encompass the viewport vertically
   */
  public double getRangeY() {
    return rangeMaxY - rangeMinY;
  }
  
  /**
   * @param min The minimum logical value of X that will be allowed within the viewport
   * @param max The maximum logical value of X that will be allowed within the viewport
   */
  public void setRangeX(double min, double max) {
    this.rangeMinX = min;
    this.rangeMaxX = max;
  }
  
  /**
   * @param min The minimum logical value of Y that will be allowed within the viewport
   * @param max The maximum logical value of Y that will be allowed within the viewport
   */
  public void setRangeY(double min, double max) {
    this.rangeMinY = min;
    this.rangeMaxY = max;
  }
  
  @Override
  public void componentResized(ComponentEvent e) {
    totalPixelsX = e.getComponent().getSize().width - 1;
    totalPixelsY = e.getComponent().getSize().height - 1;
    initializeCoordinateMap();
  }
  
  @Override
  public void componentHidden(ComponentEvent arg0) {}

  @Override
  public void componentMoved(ComponentEvent arg0) {}

  @Override
  public void componentShown(ComponentEvent arg0) {}
  
  /**
   * @param x The pixel that will be mapped to a logical X
   * @return The X value the matches the input pixel within the current Dimension context
   */
  public abstract double getXFromPixel(int x);
  
  /**
   * @param x The pixel that will be mapped to a logical Y
   * @return The Y value the matches the input pixel within the current Dimension context
   */
  public abstract double getYFromPixel(int y);
  
  /**
   * @param x The logical X that will be mapped to a pixel
   * @return The pixel that matches the input X within the current Dimension context
   */
  public abstract int getPixelFromX(double x);
  
  /**
   * @param x The logical Y that will be mapped to a pixel
   * @return The pixel that matches the input Y within the current Dimension context
   */
  public abstract int getPixelFromY(double y);
  
  public abstract Point getPointPixelFromXY(Point2D point);
  public abstract Point2D getPointXYFromPixel(Point point);
  
  /**
   * Sets up the mapping parameters whenever the observed Dimension object changes
   * @see update(Dimension dimensions)
   */
  protected abstract void initializeCoordinateMap();
}
