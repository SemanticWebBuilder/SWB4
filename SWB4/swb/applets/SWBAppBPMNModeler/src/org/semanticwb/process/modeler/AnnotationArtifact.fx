/*
 * AnnotationArtifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Rectangle;

/**
 * @author javier.solis
 */

public class AnnotationArtifact extends Artifact
{
    override public function create(): Node
    {
        setCommonMenuOptions();
        resizeable=true;
        w=80;
        h=60;
        minW = w;
        minH = h;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y
            width: bind w-6
            height: bind h
            styleClass: "artifact"
            id: "text"
        }

        shape=Path
        {
            elements: [
                MoveTo { x: bind w/4  y: 0 },
                LineTo { x: 0  y: 0 },
                LineTo { x: 0  y: bind h },
                LineTo { x: bind w/4  y: bind h },
            ]
            translateX:bind x-w/2
            translateY:bind y-h/2
            styleClass: "artifact"
            onKeyPressed: onKeyPressed
        }

        setType(type);

        return Group
        {
            content: [
                Rectangle{
                    x:bind x-w/2
                    y:bind y-h/2
                    width:bind w
                    height:bind h
                    styleClass: "artifact"
                    id: "textbox"
                }
                ,shape,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);

        if (link instanceof DirectionalAssociation) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError2"));
        }        
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link.ini instanceof Artifact) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError5"));
        } else if (link instanceof DirectionalAssociation) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError2"));
        }
        return ret;
    }
}