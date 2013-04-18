/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.utils;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author hasdai
 */
public class Point {
    private double x;
    private double y;
    private static final Logger log = SWBUtils.getLogger(Point.class);

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setX(double val) {
        x = val;
    }
    
    public void setY(double val) {
        y = val;
    }
    
    public static Point fromString(String val) {
        Point ret = null;
        if (val != null && val.trim().length() > 0) {
            String [] points = val.split(",");
            if (points != null && points.length == 2) {
                try {
                    double _x = Double.parseDouble(points[0].trim());
                    double _y = Double.parseDouble(points[1].trim());
                    ret = new Point(_x, _y);
                } catch (NumberFormatException ex) {
                    log.error("Point - error al convertir coordenadas", ex);
                    ret = null;
                }
            }
        }
        return ret;
    }
    
    public boolean equalsTo(Point p) {
        return x == p.getX() && y == p.getY();
    }
}
