/*
 * DefaultFlow.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * @author javier.solis
 */

public class DefaultFlow extends SequenceFlow
{
    public override function create(): Node
    {
        arrowType=ARROW_TYPE_SEQUENCE;
        notGroup=true;  //No agrega los elementos path y arrow al grupo

        var difx=bind (points[1].x-points[0].x)*.2;
        var dify=bind (points[1].y-points[0].y)*.2;

        var line=Line
        {
            startX:bind points[0].x+difx-5
            startY:bind points[0].y+dify-5
            endX:bind points[0].x+difx+5
            endY:bind points[0].y+dify+5
            styleClass: "connObject"
            stroke:bind path.stroke
        }
        super.create();
        
        return Group
        {
            content: [
                Group
                {
                    content: [
                        path,
                        line,
                        arrow
                    ]
                },
                text
            ]
            visible: bind canView()
        };
    }
}