/*
 * FlowObject.fx
 *
 * Created on 26/02/2010, 12:28:31 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import java.lang.Math;
/**
 * @author victor.lorenzana
 */

public class FlowObject extends GraphElement
{
    protected   var connectionPoints : ConnectionPoint[];
    
    //public var pool : Pool;
    public var dpx : Number;                        //diferencia de pool
    public var dpy : Number;                        //diferencia de pool

    override public function create(): Node
    {
        zindex=2;
        text=EditableText
        {
            text: bind title with inverse
            width: bind w;
            height: bind h;
        }
        connectionPoints=[
                ConnectionPoint
                {
                    x:bind {x};
                    y:bind {y}-h/2;
                    id:"1";
                }
                ,
                ConnectionPoint
                {
                    x: bind {x}+w/2;
                    y:bind {y};
                    id:"2";
                },
                ConnectionPoint
                {
                    x:bind {x}-w/2;
                    y:bind {y};
                    id:"4";
                    
                },
                ConnectionPoint
                {
                    x: bind {x};
                    y:bind {y}+h/2;
                    id:"3";
                }]
                ;
        return text;

    }
    
    public function getAvailablePoints() : Integer
    {        
        var count:Integer=0;
        for(cpoint in connectionPoints)
        {
            if(cpoint.connectionObject==null)
            {
                count++;
            }
        }
        
        return count;

    }
    public function getBusyPoints() : Integer
    {
        var count:Integer=0;
        for(cpoint in connectionPoints)
        {
            if(cpoint.connectionObject!=null)
            {
                count++;
            }
        }
        println("getBusyPoints: {count}");
        return count;

    }


    public function getConnectionObject(index:Integer) : ConnectionObject
    {        
        var i:Integer=0;
        for(cpoint in connectionPoints)
        {
            if(cpoint.connectionObject!=null)
            {
                if(i==index)
                {
                    return cpoint.connectionObject;
                }

                i++;
            }
        }
        return null;

    }


    public function getAvailablePoint(point : Point) : ConnectionPoint
    {
        var pointToReturn : ConnectionPoint;
        for(cpoint in connectionPoints)
        {
            if(cpoint.connectionObject==null)
            {
                if(pointToReturn==null)
                {
                    pointToReturn=cpoint;
                }
                else
                {
                    var d1:Number;
                    var d2:Number;
                    d1=Math.sqrt(Math.pow(point.x-cpoint.x, 2)+Math.pow(point.y-cpoint.y, 2));
                    d2=Math.sqrt(Math.pow(point.x-pointToReturn.x, 2)+Math.pow(point.y-pointToReturn.y, 2));
                    if(d1<d2)
                    {
                        pointToReturn=cpoint;
                    }
                }
            }
        }
        
        return pointToReturn;
    }

    public function getConnectionPoint(point : Point,connectionObject : ConnectionObject) : ConnectionPoint
    {        
        for(cpoint in connectionPoints)
        {
            if(cpoint.connectionObject==connectionObject)
            {                
                return cpoint;
            }
        }        
        return getAvailablePoint(point);       
    }

    

    

    /*var px = bind pool.x on replace
    {
        if(pool!=null)x=px+dpx;
    }
    var py = bind pool.y on replace
    {
        if(pool!=null)y=py+dpy;
    }*/


    override public function mousePressed( e: MouseEvent )
    {
        super.mousePressed(e);
    }

    override public function mouseReleased( e: MouseEvent )
    {
        //println("FlowObject mouseReleased:{e} {modeler.overNode}");
        super.mouseReleased(e);
        /*if(modeler.overPool instanceof Pool)
        {
            dpx=x-modeler.overPool.x;
            dpy=y-modeler.overPool.y;
            pool=modeler.overPool as Pool;
        }else
        {
            pool=null;
        }*/
        super.mouseReleased(e);
//        if(pool!=null)
//        {
//        }
    }

    override public function remove()
    {
        super.remove();
    }
    override public function canEndLink(link: ConnectionObject): Boolean {
        if(this==link.ini and this==link.end)
        {
            return false;
        }
        return true;

    }

    override public function canIniLink(link: ConnectionObject): Boolean {
        if(getAvailablePoints()==0)
        {
            return false;
        }
        return super.canIniLink(link);
    }

}
