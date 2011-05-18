/*
 * Point.fx
 *
 * Created on 13/02/2010, 10:48:49 AM
 */

package org.semanticwb.process.modeler;

/**
 * Clase que representa un punto en un espacio bidimensional.
 * @author javier.solis
 */
public class Point
{
    public var x : Number;
    public var y : Number;

    /**Indica si un punto p se encuentra después en el plano*/
    public function isAfter (p: Point) : Boolean {
        var ret = false;

        if (x > p.x and y > p.y) {
            ret = true;
        } else if (x > p.x) {
            ret = true;
        } else if (y > p.y) {
            ret = true;
        }
        return ret;
    }

    /**Indica si un punto p se encuentra antes en el plano*/
    public function isBefore (p: Point) : Boolean {
        var ret = false;
        if (x < p.x and y < p.y) {
            ret = true;
        } else if (x < p.x) {
            ret = true;
        } else if (y < p.y) {
            ret = true;
        }
        return ret;
    }

    /**Indica si un punto p es idéntico al punto actual*/
    public function equalsTo(p: Point) : Boolean {
        return (x == p.x and y == p.y)
    }

    /**Indica si un punto p pertenece al segmento de recta delimitado por los puntos ini y end.*/
    public function belongsToLineSegment(ini: Point, end: Point) : Boolean {
        if (ini.equalsTo(end)) return false;
        var ret = false;
        var pini = ini;
        var pend = end;

        if (ini.isAfter(end)) {
            pini = end;
            pend = ini;
        }

        if (not isAfter(pend) and not isBefore(pini)) { // pertenecen al segmento
            if (pini.x == pend.x) { // es una línea vertical con pendiente infinita
                if (x == pini.x and (y > pini.y and y < pend.y)) { //revisar respecto a y
                    ret = true;
                }
            } else { //es una línea con alguna pendiente
                var m = (pend.y - pini.y) / (pend.x - pini.x);
                var h = pini.y - m * pini.x;
                if (y == (m * x + h) + (y - (m*x+h))) {
                    ret = true;
                }
            }
        }
        return ret;
    }    
}

/**Crea un punto a partir de una cadena en formato ([0-9]+[.[0-9]*]?),([0-9]+[.[0-9]*]?)*/
public function fromString(p: String) : Point {
    if (p != null and not (p.trim().equals(""))) {
        var s: String[] = p.trim().split(",");
        if (s != null and s.size() == 2) {
            return Point {
                x: Float.parseFloat(s[0].trim());
                y: Float.parseFloat(s[1].trim())
            }
        }
    }
    return null;
}
