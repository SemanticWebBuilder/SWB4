/*
 * DefaultFlow.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Math;

/**
 * Clase que representa un flujo por defecto en un diagrama BPMN 2.0
 * @author javier.solis
 */

public class DefaultFlow extends SequenceFlow
{
    var line: Line;
    public override function create(): Node {
        blocksMouse = true;
        title= ##"default";
        notGroup=true;  //No agrega los elementos path y arrow al grupo

        var difx=bind (pend.x-pini.x)*.2;
        var dify=bind (pend.y-pini.y)*.2;
        var m= bind (pend.y - pini.y) / (pend.x - pini.x);

        line=Line
        {
            startX:bind pini.x+difx-5
            startY:bind pini.y+dify-5
            endX:bind pini.x+difx+5
            endY:bind pini.y+dify+5
            styleClass: bind path.styleClass
            transforms: bind if (m != 0) Rotate {
                pivotX: bind pini.x+difx
                pivotY: bind pini.y+dify
                angle: 30
            } else null
        }
        super.create();
        
        return Group
        {
            content: bind [
                Group
                {
                    content: [
                        path,
                        line,
                        arrow
                    ]
                },
                handles
            ]
            visible: bind canView()
        }
    }

    override public function createPath() : Void {
        super.createPath();

        var lnode = pinter1;
        if (not handles.isEmpty()) {
            var han = handles[0];
            lnode = Point {
                x: han.x
                y: han.y
            }
        }

        var difx=bind (lnode.x-pini.x)*.2;
        var dify=bind (lnode.y-pini.y)*.2;
        var m= bind (lnode.y - pini.y) / (lnode.x - pini.x);

        line=Line
        {
            startX:bind pini.x+difx-5
            startY:bind pini.y+dify-5
            endX:bind pini.x+difx+5
            endY:bind pini.y+dify+5
            styleClass: bind path.styleClass
            stroke:bind path.stroke
            transforms: bind if (m != 0 and not Number.isInfinite(m)) Rotate {angle: Math.toDegrees(Math.atan(m)), pivotX: bind pini.x+difx, pivotY: bind pini.y+dify} else null;
        }
    }

    public override function copy() : ConnectionObject {
        var t = DefaultFlow {
            ini: this.ini
            end: this.end
            modeler: this.modeler
            uri:"new:defaultflow:{modeler.toolBar.counter++}"
        }
        
        for (handle in handles) {
            t.addLineHandler(handle.getPoint(), false);
        }
        return t;
    }
}