/*
 * Task.fx
 *
 * Created on 26/02/2010, 12:48:58 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Cursor;
/**
 * @author victor.lorenzana
 */

public class Task extends FlowObject
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
            y:bind y
            width: bind w
            height: bind h
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
                shape,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: Styles.dropShadow;
        };
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        var ret=super.canEndLink(link);
        return ret;
    }
}
