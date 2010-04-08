/*
 * Point.fx
 *
 * Created on 26/02/2010, 12:31:53 PM
 */

package org.semanticwb.publishflow;
import javafx.util.Math;

/**
 * @author victor.lorenzana
 */

public class Point
{
    public var x : Number;
    public var y : Number;
    public function getDistance(point : Point) : Number
    {
        return Math.sqrt(Math.pow(point.x-x, 2)+Math.pow(point.y-y, 2));
    }

}
