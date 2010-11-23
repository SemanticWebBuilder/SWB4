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

/**
 * @author javier.solis
 */

public class ConditionalFlow extends SequenceFlow
{
    public override function create(): Node
    {
        title= ##"condition";
        arrowType=ARROW_TYPE_SEQUENCE;
        notGroup=true;  //No agrega los elementos path y arrow al grupo
        
        //Se reemplaza
        var poly=Polygon
        {
            points:[0, -6, 6, 0, 0, 6, -6, 0]
            translateX:bind points[0].x
            translateY:bind points[0].y
            styleClass: "connObject"
            id: "diamond"
            stroke:bind path.stroke
            visible:bind not(ini instanceof Gateway)
        };
        super.create();

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
                },
                text
            ]
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
                text.startEditing();
            }
        }
    }
}