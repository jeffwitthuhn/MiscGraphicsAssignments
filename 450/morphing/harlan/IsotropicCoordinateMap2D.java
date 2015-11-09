import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * This implements the interface that uses an isotropic mapping.
 * Each logical coordinate has the same pixel size in either the x or y
 * direction with a linear scale. The viewport has a fixed aspect ratio.
 * @author Harlan Sang
 */
public class IsotropicCoordinateMap2D extends CoordinateMap2D {
  private double pixelsPerUnit;
  private int minPixelsX;
  private int maxPixelsX;
  private int minPixelsY;
  private int maxPixelsY;
  
  public IsotropicCoordinateMap2D(double rangeMinX, double rangeMaxX,
      double rangeMinY, double rangeMaxY) {
    super(rangeMinX, rangeMaxX, rangeMinY, rangeMaxY);
  }
  
  /**
   * @return The first pixel horizontally within the viewport that can be drawn to
   */
  public int getMinPixelsX() {
    return minPixelsX;
  }
  
  /**
   * @return The last pixel horizontally within the viewport that can be drawn to
   */
  public int getMaxPixelsX() {
    return maxPixelsX;
  }
  
  /**
   * @return The first pixel vertically within the viewport that can be drawn to
   */
  public int getMinPixelsY() {
    return minPixelsY;
  }
  
  /**
   * @return The first pixel vertically within the viewport that can be drawn to
   */
  public int getMaxPixelsY() {
    return maxPixelsY;
  }

  /**
   * @return The number of pixels for every logical unit in either X or Y
   */
  public double getPixelsPerUnit() {
    return pixelsPerUnit;
  }
  
  @Override
  public double getXFromPixel(int x) {
    int offsetPixels = x - minPixelsX;
    double units = offsetPixels / pixelsPerUnit;
    return units + getRangeMinX();
  }

  @Override
  public double getYFromPixel(int y) {
    int offsetPixels = maxPixelsY - y;
    double units = offsetPixels / pixelsPerUnit;
    return units + getRangeMinY();
  }

  @Override
  public int getPixelFromX(double x) {
    double offsetX = x - getRangeMinX();
    int pixels = (int) Math.round(offsetX * pixelsPerUnit);
    return pixels + minPixelsX;
  }

  @Override
  public int getPixelFromY(double y) {
    double offsetY = y - getRangeMinY();
    int pixels = (int) Math.round(offsetY * pixelsPerUnit);
    return maxPixelsY - pixels;
  }
  
  @Override
  public Point getPointPixelFromXY(Point2D point) {
    return new Point(
      getPixelFromX(point.getX()),
      getPixelFromY(point.getY())
    );
  }

  @Override
  public Point2D getPointXYFromPixel(Point point) {
    return new Point2D.Double(
      getXFromPixel(point.x),
      getXFromPixel(point.y)
    );
  }

  @Override
  protected void initializeCoordinateMap() {
    double maxPixelsPerUnitX = getTotalPixelsX() / getRangeX();
    double maxPixelsPerUnitY = getTotalPixelsY() / getRangeY();
    
    int edgeOffset;
    if(maxPixelsPerUnitX >  maxPixelsPerUnitY) {
      pixelsPerUnit = maxPixelsPerUnitY;
      minPixelsY = 0;
      maxPixelsY = getTotalPixelsY();
      edgeOffset = (int) ((getTotalPixelsX() - pixelsPerUnit * getRangeX()) / 2);
      minPixelsX = edgeOffset;
      maxPixelsX = getTotalPixelsX() - edgeOffset;
    }
    else {
      pixelsPerUnit = maxPixelsPerUnitX;
      minPixelsX = 0;
      maxPixelsX = getTotalPixelsX();
      edgeOffset = (int) ((getTotalPixelsY() - pixelsPerUnit * getRangeY()) / 2);
      minPixelsY = edgeOffset;
      maxPixelsY = getTotalPixelsY() - edgeOffset;
    }
  }
}

