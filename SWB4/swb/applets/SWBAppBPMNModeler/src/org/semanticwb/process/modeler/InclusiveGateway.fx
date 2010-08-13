/*
 * InclusiveGateway.fx
 *
 * Created on 13/02/2010, 11:24:44 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
/**
 * @author javier.solis
 */

public class InclusiveGateway extends Gateway
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
            //smooth: true;
        };

        return Group
        {
            content: [
                shape, Circle
                {
                    centerX: w/2
                    centerY: h/2
                    radius: w/4
                    style: Styles.style_simbol
                    //smooth: true;
                }
            ]
            translateX: bind x - w/2
            translateY: bind y - w/2
            scaleX: bind s;
            scaleY: bind s;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }
}

