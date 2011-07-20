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
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.ModelerUtils;

/**
 * @author javier.solis
 */

public class AnnotationArtifact extends Artifact
{
    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "artifactHover";
        } else if (not selected) {
            shape.styleClass = "artifact";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "artifactFocused";
        } else {
            shape.styleClass = "artifact";
        }
    }

    override public function create(): Node
    {
        blocksMouse = true;
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
            onKeyReleased: onKeyReleased
        }

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actCopy"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = copy();
                    modeler.setCopyNode(t);
                }
            },
            MenuItem {
                caption: ##"actCut"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = cut();
                    modeler.setCopyNode(t);
                    ModelerUtils.setResizeNode(null);
                }
            }
        ];
        insert actions before menuOptions[0];

        setType(TYPE_ANNOTATION);

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
            ModelerUtils.setErrorMessage(##"msgError2");
        }        
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link.ini instanceof Artifact) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError5");
        } else if (link instanceof DirectionalAssociation) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError2");
        }
        return ret;
    }

    override public function getDefaultFlow() : ConnectionObject {
        var link = AssociationFlow {
            modeler:modeler
            uri:"new:associationflow:{modeler.toolBar.counter++}"
        }
        return link;
    }

    override public function copy() : GraphicalElement {
        var t = AnnotationArtifact {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            isLoop: this.isLoop
            isForCompensation: this.isForCompensation
            isMultiInstance: this.isMultiInstance
            container: this.container
            uri: "new:{type}:{modeler.toolBar.counter++}"
            x: this.x + 10
            y: this.y + 10
        }
        return t;
    }
}