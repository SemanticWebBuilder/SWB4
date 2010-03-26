/*
 * ConditionalFlow.fx
 *
 * Created on 26/02/2010, 12:47:12 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
/**
 * @author victor.lorenzana
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
        if(ModelerUtils.clickedNode==null)
        {
            ModelerUtils.clickedNode=this;
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
        if(ModelerUtils.clickedNode==this)
        {
            ModelerUtils.clickedNode=null;
        }
    }


}
