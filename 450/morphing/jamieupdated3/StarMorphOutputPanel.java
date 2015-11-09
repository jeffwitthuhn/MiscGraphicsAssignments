/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    HashMap<Point2D, Point2D> polygonTransformations;
    Vector<Point2D> sortedList;
    CoordinateMap2D map;
    Point2D sourceCenter;
    int totalSteps, currentStep;
    javax.swing.Timer t;

    public StarMorphOutputPanel(HashMap<Point2D, Point2D> conv,
            Point2D sc, int ts, CoordinateMap2D m) {
        super();

        map = m;
        sourceCenter = sc;
        totalSteps = ts;
        currentStep = 0;
        polygonTransformations = new HashMap<Point2D, Point2D>(conv);
        sortedList = new Vector<Point2D>(polygonTransformations.keySet().size());
        sortList();

        t = new javax.swing.Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep++;
                repaint();
                if (currentStep >= totalSteps) {
                    t.stop();
                }
            }
        });
        t.setInitialDelay(10000);
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
        Polygon poly2 = new Polygon();

        // Draw source and target polygons
        for (Point2D p : sortedList) {
            poly.addPoint(map.getPixelFromX(p.getX()), map.getPixelFromY(p.getY()));
            poly2.addPoint(
                    map.getPixelFromX(polygonTransformations.get(p).getX()), 
                    map.getPixelFromY(polygonTransformations.get(p).getY()));
        }
        g.setColor(Color.RED);
        g.drawPolygon(poly);
        g.setColor((currentStep < totalSteps ? Color.BLUE : Color.MAGENTA));
        g.drawPolygon(poly2);        

        // Draw transformed polygon
        if (currentStep < totalSteps) {
            poly = new Polygon();
            for (Point2D p : sortedList)
                poly.addPoint(calculatePolygonPoint(p, polygonTransformations.get(p)).x,
                        calculatePolygonPoint(p, polygonTransformations.get(p)).y);

            g.setColor(Color.MAGENTA);
            g.drawPolygon(poly);
        }
    }

    // Calculate step distance from source to target point.
    private Point calculatePolygonPoint(Point2D a, Point2D b) {
        return new Point(
                map.getPixelFromX(a.getX() + currentStep * (b.getX() - a.getX()) / totalSteps),
                map.getPixelFromY(a.getY() + currentStep * (b.getY() - a.getY()) / totalSteps));
    }
    
    private void sortList() {
        for (Point2D p : polygonTransformations.keySet())
            sortedList.add(p);

        Collections.sort(sortedList, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D one, Point2D two) {
                // calcAngle returns atan2 angle based on center.
                double a = Morphing.calcAngle(one.getX()-sourceCenter.getX(), one.getY()-sourceCenter.getY());
                double b = Morphing.calcAngle(two.getX()-sourceCenter.getX(), two.getY()-sourceCenter.getY());
                if (a < b) {
                    return -1;
                } else if (a > b) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }
}
