/*
 * InclusiveStartEventGateway.fx
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
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
/**
 * @author javier.solis
 */

public class ExclusiveStartEventGateway extends EventBasedGateway
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

        var colorAdjust=ColorAdjust
        {
            hue:0.25
            brightness:0.28
            contrast:0.25
            saturation:1
        }

        var message=ImageView
        {
            image:Styles.ICO_EVENT_W_MULTIPLE
            x: 14
            y: 13
            scaleX: 1
            scaleY: 1
            effect: colorAdjust
        };

        return Group
        {
            content: [
                shape, Circle
                {
                    centerX: w/2
                    centerY: h/2
                    radius: w/3.5
                    style: Styles.style_simbol2
                    //smooth: true;
                },
                message
            ]
            translateX: bind x - w/2
            translateY: bind y - w/2
            scaleX: bind s;
            scaleY: bind s;
            effect: Styles.dropShadow
            visible:bind canView()
        };
    }
}

