/*
 * ConditionalFlow.fx
 *
 * Created on 17/02/2010, 12:56:12 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class ConditionalFlow extends ConnectionObject
{
    public override function create(): Node
    {
        title="Condition";
        var ret=super.create();
        return ret;
    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
        if(modeler.clickedNode==null)
        {
            modeler.clickedNode=this;
            modeler.focusedNode=this;
            if(e.clickCount >= 2)
            {
                //println("starEditing");
                text.startEditing();
            }
        }
    }

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(modeler.clickedNode==this)
        {
            modeler.clickedNode=null;
        }
    }


}
