/*
 * FlowObject.fx
 *
 * Created on 26/02/2010, 12:28:31 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
/**
 * @author victor.lorenzana
 */

public class FlowObject extends GraphElement
{
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
        return text;
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
}
