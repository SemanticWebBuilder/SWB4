/*
 * SubProcess.fx
 *
 * Created on 13/02/2010, 11:21:05 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Line;

/**
 * @author javier.solis
 */

public class SubProcess extends FlowObject
{
    public override function create(): Node
    {
        cursor=Cursor.HAND;
        w=100;
        h=60;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y - h/5
            width: bind w
            height: bind h/2
        }

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: w
            height: h
            //effect: lighting
            //styleClass: "task"
            style: Styles.style_task
            smooth:true;
        };

        return Group
        {
            content: [
                shape,text, Rectangle
                {
                    x: bind x-10
                    y: bind y+2
                    width: 20
                    height: 20
                    style: Styles.style_message
                    smooth:true;
                }, Line{
                    startX: bind x-6
                    startY: bind y+12
                    endX: bind x+6
                    endY: bind y+12
                    style: Styles.style_message
                    smooth:true;
                }, Line{
                    startX: bind x
                    startY: bind y+12-6
                    endX: bind x
                    endY: bind y+12+6
                    style: Styles.style_message
                    smooth:true;
                }
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: Styles.dropShadow
        };
    }
}