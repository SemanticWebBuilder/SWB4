/*
 * FlowLink.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

/**
 * @author javier.solis
 */

public class MessageFlow extends ConnectionObject
{
    public override function create(): Node
    {
        arrowType=ARROW_TYPE_MESSAGE;
        strokeDash=[2,5];
        strokeWidth=1;
        notGroup=true;  //No agrega los elementos path y arrow al grupo
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
                            style:Styles.style_flow
                            stroke:bind path.stroke
                        },
                        arrow
                    ]
                    effect: Styles.dropShadow
                },
                text
            ]
            opacity: bind o;
        };
    }
}
