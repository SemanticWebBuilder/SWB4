/*
 * Pool.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Cursor;

/**
 * @author javier.solis
 */

public class Pool extends GraphElement
{
    override public function create(): Node
    {
        cursor=Cursor.HAND;
        w=400;
        h=100;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x-w/2+10
            y:bind y
            width: bind h
            height: 20
            rotate: -90
        }

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: w
            height: h
            style: Styles.style_pool
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
            effect: Styles.dropShadow
        };
    }

    override public function mouseEntered( e: MouseEvent )
    {
        modeler.overPool=this;
        super.mouseEntered(e);
    }

    override public function mouseExited( e: MouseEvent )
    {
        modeler.overPool = null;
        super.mouseExited(e);
    }

    override public function mousePressed( e: MouseEvent )
    {
       super.mousePressed(e);
    }

    override public function remove()
    {
       super.remove();
    }
}