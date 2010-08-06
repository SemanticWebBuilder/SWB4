/*
 * ConditionalFlow.fx
 *
 * Created on 17/02/2010, 12:56:12 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class ConditionalFlow extends SequenceFlow
{
    public override function create(): Node
    {
        title= ModelerUtils.getLocalizedString("condition");
        arrowType=ARROW_TYPE_SEQUENCE;
        notGroup=true;  //No agrega los elementos path y arrow al grupo
        super.create();
        //Se reemplaza
        var poly=Polygon
        {
            points:[
                 0,-6,
                 6, 0,
                 0, 6,
                -6, 0
            ]
            translateX:bind points[0].x
            translateY:bind points[0].y
            style:Styles.style_flow
            stroke:bind path.stroke
            fill:Color.WHITE
            visible:bind not(ini instanceof Gateway)
        };

        var ret=Group
        {
            content: [
                Group
                {
                    content: [
                        path,
                        poly,
                        arrow
                    ]
                    effect: Styles.dropShadow
                },
                text
            ]
            opacity: bind o;
            visible: bind canView()
        };
        return ret;
    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
        if(ModelerUtils.clickedNode==null)
        {
            ModelerUtils.clickedNode=this;
            modeler.setFocusedNode(this);
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
