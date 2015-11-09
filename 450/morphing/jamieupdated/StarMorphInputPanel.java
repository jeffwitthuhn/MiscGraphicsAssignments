import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StarMorphInputPanel extends JPanel {
  private class InputMouseAdapter extends MouseAdapter {
    private static final int CLICK_DISTANCE_THRESHOLD = 5;
    
    @Override
    public void mousePressed(MouseEvent e) {
      if(originalCenter == null) {
        originalCenter = new Point2D.Double(
          coordinateMap.getXFromPixel(e.getX()),
          coordinateMap.getYFromPixel(e.getY())
        );
      }
      else if(!isOriginalShapeClosed) {
        isOriginalShapeClosed = addPointIfApplicable(e.getPoint(), originalCenter, originalShape);
      }
      else if(morphCenter == null) {
        morphCenter = new Point2D.Double(
          coordinateMap.getXFromPixel(e.getX()),
          coordinateMap.getYFromPixel(e.getY())
        );
      }
      else if(!isMorphShapeClosed) {
        isMorphShapeClosed = addPointIfApplicable(e.getPoint(), morphCenter, morphShape);
      }
      
      if(isMorphShapeClosed) {
        reorderShapes();
      }
      
      repaint();
    }
    
    private boolean addPointIfApplicable(Point click, Point2D center, List<Point2D> shape) {
      Point2D converted = new Point2D.Double(
        coordinateMap.getXFromPixel(click.x),
        coordinateMap.getYFromPixel(click.y)
      );
	  final Point2D compCenter = center;
      
      boolean isAdded = false;
      if(shape.size() > 0) {
        if(shape.size() > 2 && isClickInRange(click, shape.get(0))) {
	      double x1, y1, x2, y2, eval;
		  for(int i = 1; i < shape.size(); i++) {
			  x1 = shape.get(i-1).getX();
			  y1 = shape.get(i-1).getY();  
			  x2 = shape.get(i).getX();
			  y2 = shape.get(i).getY();
			  eval = (Morphing.calcAngle(x2-compCenter.getX(), y2-compCenter.getY()) - Morphing.calcAngle(x1-compCenter.getX(), y1-compCenter.getY()));
			  if(eval < Math.PI)
				  isAdded = true;
				  
			  else
				  System.out.println("eval: "+eval);
				  isAdded = false;
				  break;
		  }
		  if(isAdded)
			return true;
		  
		  JOptionPane.showMessageDialog(
			(JFrame) SwingUtilities.getWindowAncestor(StarMorphInputPanel.this),
			"Can not close polygon until there all points have less than pi angular from each other.",
			"Warning",
			JOptionPane.WARNING_MESSAGE
			);
		  return false;
        }
        else {
			shape.add(converted);
			Collections.sort(shape, new Comparator<Point2D>() {
				@Override
				public int compare(Point2D one, Point2D two) {
					// calcAngle returns atan2 angle based on center.
					double a = Morphing.calcAngle(one.getX()-compCenter.getX(), one.getY()-compCenter.getY());
					double b = Morphing.calcAngle(two.getX()-compCenter.getX(), two.getY()-compCenter.getY());
					if (a < b)
						return -1;
					else if (a > b)
						return 1;
					else 
						return 0;
					
				}
		    });
		  isAdded = true;  
        }
      }
      else {
        shape.add(converted);
        isAdded = true;
      }
      
      if(!isAdded) {
        JOptionPane.showMessageDialog(
          (JFrame) SwingUtilities.getWindowAncestor(StarMorphInputPanel.this),
          "The selected point is not between the last point and first point in counter-clockwise orientation",
          "Warning",
          JOptionPane.WARNING_MESSAGE
        );
      }
      
      return false;
    }
    
    private boolean isClickInRange(Point click, Point2D point) {
      Point compareTo = coordinateMap.getPointPixelFromXY(point);
      return (
        Math.abs(click.x - compareTo.x) < CLICK_DISTANCE_THRESHOLD && 
        Math.abs(click.y - compareTo.y) < CLICK_DISTANCE_THRESHOLD
      );
    }
  }
  
  private static final long serialVersionUID = 1L;
  private static final Color COLOR_ORIGINAL_SHAPE = Color.RED;
  private static final Color COLOR_MORPH_SHAPE = Color.BLUE;
  private static final int POINT_SIZE = 2;
  
  private CoordinateMap2D coordinateMap;
  private IObjectListener readyListener;
  private List<Point2D> originalShape;
  private List<Point2D> morphShape;
  private Point2D originalCenter;
  private Point2D morphCenter;
  private boolean isOriginalShapeClosed = false;
  private boolean isMorphShapeClosed = false;

  public StarMorphInputPanel(CoordinateMap2D map) {
    originalShape = new LinkedList<Point2D>();
    morphShape = new LinkedList<Point2D>();
    
    this.addMouseListener(new InputMouseAdapter());
    
    coordinateMap = map;
    this.addComponentListener(coordinateMap);
  }
  
  public List<Point2D> getOriginalShape() {
    return originalShape;
  }
  
  public List<Point2D> getMorphShape() {
    return morphShape;
  }
  
  public Point2D getOriginalCenter() {
    return originalCenter;
  }
  
  public Point2D getMorphCenter() {
    return morphCenter;
  }
  
  public void setReadyListener(IObjectListener value) {
    readyListener = value;
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(COLOR_ORIGINAL_SHAPE);
    drawShape(g, originalShape, isOriginalShapeClosed);
    g.setColor(COLOR_MORPH_SHAPE);
    drawShape(g, morphShape, isMorphShapeClosed);
    g.setColor(Color.BLACK);
    drawReferenceLines(g);
  }
  
  protected void drawReferenceLines(Graphics g) {
    Point2D referenceLast = null;
    Point2D referenceFirst = null;
    Point2D referenceCenter = null;
    
    if(originalShape.size() > 0 && !isOriginalShapeClosed) {
      referenceFirst = originalShape.get(0);
      referenceLast = originalShape.get(originalShape.size() - 1);
      referenceCenter = originalCenter;
    }
    else if(morphShape.size() > 0 && !isMorphShapeClosed) {
      referenceFirst = morphShape.get(0);
      referenceLast = morphShape.get(morphShape.size() - 1);
      referenceCenter = morphCenter;
    }
    
    if(referenceFirst != null) {
      int centerX = coordinateMap.getPixelFromX(referenceCenter.getX());
      int centerY = coordinateMap.getPixelFromY(referenceCenter.getY());
      int firstX = coordinateMap.getPixelFromX(referenceFirst.getX());
      int firstY = coordinateMap.getPixelFromY(referenceFirst.getY());
      int lastX = coordinateMap.getPixelFromX(referenceLast.getX());
      int lastY = coordinateMap.getPixelFromY(referenceLast.getY());
      g.drawLine(centerX, centerY, firstX, firstY);
      g.drawLine(centerX, centerY, lastX, lastY);
    }
    
    if(originalCenter != null) {
      drawPoint(g, originalCenter);
    }
    
    if(morphCenter != null) {
      drawPoint(g, morphCenter);
    }
  }
  
  protected void drawShape(Graphics g, List<Point2D> shape, boolean isClosed) {
    if(shape.size() > 1) {
      ListIterator<Point2D> it = shape.listIterator();
      Point2D start  = it.next();
      Point2D end;
      drawPoint(g, start);
      while(it.hasNext()) {
        end = it.next();
        g.drawLine(
          coordinateMap.getPixelFromX(start.getX()), coordinateMap.getPixelFromY(start.getY()),
          coordinateMap.getPixelFromX(end.getX()), coordinateMap.getPixelFromY(end.getY())
        );
        drawPoint(g, end);
        start = end;
      }
      
      if(isClosed) {
        end = shape.get(0);
        g.drawLine(
          coordinateMap.getPixelFromX(start.getX()), coordinateMap.getPixelFromY(start.getY()),
          coordinateMap.getPixelFromX(end.getX()), coordinateMap.getPixelFromY(end.getY())
        );
      }
    }
    else if(shape.size() == 1) {
      drawPoint(g, shape.get(0));
    }
  }
  
  protected void drawPoint(Graphics g, Point2D p) {
    int pX = coordinateMap.getPixelFromX(p.getX());
    int pY = coordinateMap.getPixelFromY(p.getY());
    g.fillRect(pX - POINT_SIZE, pY - POINT_SIZE, 2 * POINT_SIZE, 2 * POINT_SIZE);
  }
  
  protected void reorderShapes() {
    ArrayList<Point2D> originalCopy = new ArrayList<Point2D>(originalShape.size());
    ArrayList<Point2D> morphCopy = new ArrayList<Point2D>(morphShape.size());
    
    for(Point2D original : originalShape) {
      originalCopy.add(original);
    }
    
    boolean isOrderFound = false;
    int firstIndex = 0;
    Point2D original0 = originalShape.get(0);
    Point2D original1 = originalShape.get(1);
    for(Point2D morph : morphShape) {
      if(!isOrderFound) {
        if(MathHelpers.isPointBetweenAngularly(morphCenter, morph, original0, original1)) {
          isOrderFound = true;
        }
        firstIndex++;
      }
      else {
        morphCopy.add(morph);
      }
    }
    
    int index = 0;
    for(Point2D morph : morphShape) {
      if(index < firstIndex) {
        morphCopy.add(morph);
        index++;
      }
      else {
        break;
      }
    }
    
    //originalShape = originalCopy;
    //morphShape = morphCopy;
    readyListener.update(this);
  }
}
