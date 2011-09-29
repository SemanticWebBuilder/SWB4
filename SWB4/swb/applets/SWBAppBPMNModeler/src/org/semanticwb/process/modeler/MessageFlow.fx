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
 * Clase que representa un flujo de mensaje en un diagrama BPMN 2.0
 * @author javier.solis
 */

public class MessageFlow extends ConnectionObject
{
    public override function create(): Node
    {
        blocksMouse = true;
        title=##"message";
        arrowType=ARROW_TYPE_MESSAGE;
        strokeDash=[2,5];
        notGroup=true;  //No agrega los elementos path y arrow al grupo
        //cubicCurve=true;
        super.create();
        return Group
        {
            content: bind [
                Group
                {
                    content: [
                        path,
                        Circle{
                            radius: 5
                            centerX:bind pini.x
                            centerY:bind pini.y
                            styleClass: bind path.styleClass
                            id: "tail"
                        },
                        arrow
                    ]
                },
                text,
                handles
            ]
            visible: bind canView()
        };
    }

    public override var onMouseClicked = function (e: MouseEvent) {
        if(e.clickCount >= 2)
        {
            if(text != null)
            {
                text.startEditing();
            }
        }
        if (e.button == e.button.SECONDARY) {
            var p = Point {
                x: e.sceneX
                y: e.sceneY
            };
            if (handles.isEmpty()) {
                buildDefaultHandlers();
            } else {
                addLineHandler(p, false);
            }
        }
    }

    public override function copy() : ConnectionObject {
        var t = MessageFlow {
            ini: this.ini
            end: this.end
            modeler: this.modeler
            title:this.title
            uri:"new:messageflow:{modeler.toolBar.counter++}"
        }

        for (handle in handles) {
            t.addLineHandler(handle.getPoint(), false);
        }
        return t;
    }
}