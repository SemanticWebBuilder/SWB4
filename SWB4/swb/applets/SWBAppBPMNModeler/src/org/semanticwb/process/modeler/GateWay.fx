/*
 * GateWay.fx
 *
 * Created on 13/02/2010, 11:23:01 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class GateWay extends FlowObject
{
    public override function create(): Node
    {
        initializeCustomNode();
        stroke=Color.web(Styles.color_gateway);
        cursor=Cursor.HAND;
        w=50;
        h=50;
        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            style: Styles.style_gateway
            smooth: true;
        };

        return Group
        {
            content: [
                Group
                {
                    content: [
                        shape
                    ]
                    translateX: bind x - w/2
                    translateY: bind y - w/2
                    scaleX: bind s;
                    scaleY: bind s;
                    opacity: bind o;
                    effect: Styles.dropShadow
                }
            ]
        };
    }
}

