/*
 * ParallelGateway.fx
 *
 * Created on 13/02/2010, 11:26:19 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
/**
 * @author javier.solis
 */

public class ParallelGateway extends Gateway
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
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        return Group
        {
            content: [
                shape,
                Line{
                    startX: w/2-w/4
                    startY: h/2
                    endX: w/2+w/4
                    endY: h/2
                    styleClass: "modifierGateway"
                }, Line{
                    startX: w/2
                    startY: h/2-h/4
                    endX: w/2
                    endY: h/2+h/4
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