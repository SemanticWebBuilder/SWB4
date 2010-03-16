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
    public var connectionObject:ConnectionObject on replace oldValue
    {
            if(oldValue!=null)
            {
                if(oldValue.ini!=null)
                {
                    oldValue.ini.onConected();
                }
                if(oldValue.end!=null)
                {
                    oldValue.ini.onConected();
                }
            }
            if(connectionObject!=null)
            {
                if(connectionObject.ini!=null)
                {
                    connectionObject.ini.onConected();
                }
                if(connectionObject.end!=null)
                {
                    connectionObject.ini.onConected();
                }
            }
    };
    public var id:String;
    public function getDistance(point : FlowObject) : Number
    {
        return Math.sqrt(Math.pow(point.x-x, 2)+Math.pow(point.y-y, 2));
    }
}
