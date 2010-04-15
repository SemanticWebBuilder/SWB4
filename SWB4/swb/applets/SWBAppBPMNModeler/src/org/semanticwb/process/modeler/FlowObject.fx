/*
 * FlowObject.fx
 *
 * Created on 13/02/2010, 10:59:22 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class FlowObject extends GraphElement
{
    override public function create(): Node
    {
        zindex=2;
        return super.create();
    }

    override public function mousePressed( e: MouseEvent )
    {
        super.mousePressed(e);
    }

    override public function mouseReleased( e: MouseEvent )
    {
        super.mouseReleased(e);
    }

    override public function remove()
    {
        super.remove();
    }

    public override function canAttach(parent:GraphElement):Boolean
    {
        var ret=false;
        if(parent instanceof Pool or parent instanceof Lane)
        {
            ret=true;
        }
        return ret;
    }
}
