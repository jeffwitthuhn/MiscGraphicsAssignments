import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author jsart
 */
public class StarMorphOutputPanel extends JPanel {

    HashMap<Point, Point> polygonTransformations;
    CoordinateMap2D map;
    int totalSteps, currentStep;
    javax.swing.Timer t;

    public StarMorphOutputPanel(HashMap<Point2D, Point2D> conv, 
            Point2D sc, Point2D tc, int ts, CoordinateMap2D m) {
        super();
        
        map = m;
        totalSteps = ts;
        currentStep = 0;
        polygonTransformations = convertPoints(conv, sc, tc);
        
        t = new javax.swing.Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep++;
                repaint();
                if(currentStep >= totalSteps)
                    t.stop();
            }
        });
        t.setInitialDelay(5000);
        t.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentStep++;
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Polygon poly = new Polygon();
        
        // Draw source polygon
        for (Point p : polygonTransformations.keySet())
            poly.addPoint(p.x, p.y);
        g.setColor(Color.RED);
        g.drawPolygon(poly);
        
        // Draw target polygon.
        poly = new Polygon();
        for (Point p : polygonTransformations.values())
            poly.addPoint(p.x, p.y);
        g.setColor((currentStep < totalSteps ? Color.BLUE : Color.MAGENTA));
        g.drawPolygon(poly);
              
        // Draw transformed polygon
        if (currentStep < totalSteps) {
            poly = new Polygon();  
            for (Point p : polygonTransformations.keySet())
                poly.addPoint(calculatePolygonPoint(p, polygonTransformations.get(p)).x, 
                        calculatePolygonPoint(p, polygonTransformations.get(p)).y);
        
            g.setColor(Color.MAGENTA);
            g.drawPolygon(poly);
        }
    }

    // Calculate step distance from source to target point.
    private Point calculatePolygonPoint(Point a, Point b) {
        return new Point(a.x + (int) Math.round(currentStep * (b.x-a.x) / totalSteps),
                a.y + (int) Math.round(currentStep * (b.y-a.y) / totalSteps));
    }
    
    // Group partners used Point2D, convert to Point since x/y are integers.
    private HashMap<Point, Point> convertPoints(HashMap<Point2D, Point2D> conv, 
            Point2D sourceCenter, Point2D targetCenter) {
        HashMap<Point, Point> temp = new HashMap<Point, Point>();
        Point source, target;
        
        for(Point2D p : conv.keySet()) {
            source = new Point(map.getPixelFromX(p.getX()+sourceCenter.getX()), 
                    map.getPixelFromY(sourceCenter.getY()+p.getY()));
            target = new Point(map.getPixelFromX(conv.get(p).getX()+targetCenter.getX()), 
                    map.getPixelFromX(this.getHeight()-targetCenter.getY()+conv.get(p).getY()));
            temp.put(source, target);
        }
        
        return temp;
    }
}
