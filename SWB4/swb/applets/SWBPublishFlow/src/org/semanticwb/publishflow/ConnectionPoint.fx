/*
 * ConnectionPoint.fx
 *
 * Created on 1/03/2010, 05:58:41 PM
 */

package org.semanticwb.publishflow;
import javafx.util.Math;

/**
 * @author victor.lorenzana
 */

public class ConnectionPoint extends Point {
    public var connectionObject:ConnectionObject;
    public function getDistance(point : FlowObject) : Number
    {
        return Math.sqrt(Math.pow(point.x-x, 2)+Math.pow(point.y-y, 2));
    }
}
