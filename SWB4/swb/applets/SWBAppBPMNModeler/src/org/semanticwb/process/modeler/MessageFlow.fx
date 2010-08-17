/*
 * FlowLink.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class MessageFlow extends ConnectionObject
{
    public override function create(): Node
    {
        title=ModelerUtils.getLocalizedString("Message");
        arrowType=ARROW_TYPE_MESSAGE;
        strokeDash=[2,5];
        notGroup=true;  //No agrega los elementos path y arrow al grupo
        cubicCurve=true;
        super.create();
        return Group
        {
            content: [
                Group
                {
                    content: [
                        path,
                        Circle{
                            radius: 5
                            centerX:bind points[0].x
                            centerY:bind points[0].y
                            styleClass: "connObject"
                            id: "tail"
                            stroke:bind path.stroke
                        },
                        arrow
                    ]
                },
                text
            ]
            visible: bind canView()
        };
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

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(ModelerUtils.clickedNode==this)
        {
            ModelerUtils.clickedNode=null;
        }
    }
}