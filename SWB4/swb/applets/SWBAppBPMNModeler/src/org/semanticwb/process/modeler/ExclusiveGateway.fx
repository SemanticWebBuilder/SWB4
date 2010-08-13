/*
 * ExclusiveGateway.fx
 *
 * Created on 13/02/2010, 11:26:19 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
/**
 * @author javier.solis
 */

public class ExclusiveGateway extends Gateway
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
                shape,
                Line{
                    startX: w/2-w/6
                    startY: h/2-h/6
                    endX: w/2+w/6
                    endY: h/2+h/6
                    style: Styles.style_simbol
                    //smooth: true;
                    strokeLineCap: StrokeLineCap.ROUND
                }, Line{
                    startX: w/2+w/6
                    startY: h/2-h/6
                    endX: w/2-w/6
                    endY: h/2+h/6
                    style: Styles.style_simbol
                    //smooth: true;
                    strokeLineCap: StrokeLineCap.ROUND
                }
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

