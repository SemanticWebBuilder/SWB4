/*
 * GroupArtifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;

/**
 * @author javier.solis
 */

public-read var TYPE_MULTIPLE="multiple";
public-read var TYPE_INPUT="input";
public-read var TYPE_OUTPUT="output";


public class GroupArtifact extends GraphElement
{
    override public function create(): Node
    {
        resizeable=true;
        cursor=Cursor.HAND;
        w=150;
        h=150;
        stkw=2;
        stkwo=2;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y
            width: bind w-6
            height: bind h
        }

        shape=Path
        {
            elements: [
                MoveTo { x: 0  y: 0 },
                LineTo { x: 0  y: bind h },
                LineTo { x: bind w y: bind h },
                LineTo { x: bind w  y: 0 },
                LineTo { x: 0  y: 0 }
            ]
            style:Styles.style_connection
            translateX:bind x-w/2
            translateY:bind y-h/2
            strokeDashArray: [20,5,3,5]
        };

        setType(type);

        return Group
        {
            content: [
                shape,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }

    override public function mouseEntered( e: MouseEvent )
    {
        super.mouseEntered(e);
    }

    override public function mouseExited( e: MouseEvent )
    {
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

    public override function canAttach(parent:GraphElement):Boolean
    {
        var ret=false;
        if(parent instanceof Pool or parent instanceof Lane)
        {
            ret=true;
        }
        return ret;
    }
}