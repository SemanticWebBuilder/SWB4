/*
 * GroupArtifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;

/**
 * @author javier.solis
 */

public class GroupArtifact extends Artifact
{
    override public function create(): Node
    {
        setCommonMenuOptions();
        resizeable=true;
        w=150;
        h=150;
        
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
            styleClass: "connObject"
            translateX:bind x-w/2
            translateY:bind y-h/2
            strokeDashArray: [20,5,3,5]
            onKeyPressed: onKeyPressed
        };

        return Group
        {
            content: [
                shape,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        return false;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        return false;
    }
}