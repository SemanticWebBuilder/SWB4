/*
 * ConditionalFlow.fx
 *
 * Created on 17/02/2010, 12:56:12 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Math;

/**
 * Clase que representa un flujo condicional en un diagrama BPMN 2.0
 * @author javier.solis
 */

public class ConditionalFlow extends SequenceFlow
{
    var poly: Polygon;
    public override function create(): Node
    {
        blocksMouse = true;
        title= ##"condition";
        arrowType=ARROW_TYPE_SEQUENCE;
        notGroup=true;  //No agrega los elementos path y arrow al grupo
        
        var lnode = pinter1;
        if (not handles.isEmpty()) {
            lnode = Point {
                x: handles[0].x
                y: handles[0].y
            }
        }
        var m = bind (lnode.y - pini.y) / (lnode.x - pini.x);

        poly=Polygon
        {
            points:[0, -6, 6, 0, 0, 6, -6, 0]
            translateX:bind pini.x
            translateY:bind pini.y
            styleClass: "connObject"
            id: "diamond"
            stroke:bind path.stroke
            visible:bind not(ini instanceof Gateway)
            transforms: bind if (m != 0 and not Number.isInfinite(m)) Rotate {angle: Math.toDegrees(Math.atan(m))} else null;
        };
        super.create();

        var ret=Group
        {
            content: bind [
                Group
                {
                    content: [
                        path,
                        poly,
                        arrow
                    ]
                },
                text,
                handles
            ]
            visible: bind canView()
        };
        return ret;
    }

    override public function createPath() : Void {
        super.createPath();
        var lnode = pinter1;
        if (not handles.isEmpty()) {
            lnode = Point {
                x: handles[0].x
                y: handles[0].y
            }
        }

        var m = bind (lnode.y - pini.y) / (lnode.x - pini.x);
        poly=Polygon
        {
            points:[0, -6, 6, 0, 0, 6, -6, 0]
            translateX:bind pini.x
            translateY:bind pini.y
            styleClass: "connObject"
            id: "diamond"
            stroke:bind path.stroke
            visible:bind not(ini instanceof Gateway)
            transforms: bind if (m != 0 and not Number.isInfinite(m)) Rotate {angle: Math.toDegrees(Math.atan(m))} else null;
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
        var t = ConditionalFlow {
            ini: this.ini
            end: this.end
            modeler: this.modeler
            title:this.title
            uri:"new:associationflow:{modeler.toolBar.counter++}"
        }

        for (handle in handles) {
            t.addLineHandler(handle.getPoint(), false);
        }
        return t;
    }
}