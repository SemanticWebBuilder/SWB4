/*
 * InclusiveGateway.fx
 *
 * Created on 13/02/2010, 11:24:44 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
/**
 * @author javier.solis
 */

public class InclusiveGateway extends Gateway
{
    public override function create(): Node
    {
        initializeCustomNode();
        w=50;
        h=50;
        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            styleClass: "gateway"
        };

        return Group
        {
            content: [
                shape, Circle
                {
                    centerX: w/2
                    centerY: h/2
                    radius: w/4
                    styleClass: "modifierGateway"
                }
            ]
            translateX: bind x - w/2
            translateY: bind y - w/2
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }
}