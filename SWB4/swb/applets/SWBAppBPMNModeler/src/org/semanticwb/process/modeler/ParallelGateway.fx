/*
 * ParallelGateway.fx
 *
 * Created on 13/02/2010, 11:26:19 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.input.MouseEvent;
/**
 * @author javier.solis
 */

public class ParallelGateway extends Gateway
{
    public override function create(): Node
    {
        blocksMouse = true;
        initializeCustomNode();
        w=50;
        h=50;
        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            styleClass: "gateway"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        var actions: MenuItem[] = [
             MenuItem {
                caption: ##"actCopy"
                action: function (e: MouseEvent) {
                    var t = copy();
                    modeler.setCopyNode(t);
                    ModelerUtils.popup.hide();
                }
             },
         ];
         insert actions before menuOptions[0];

        return Group
        {
            content: [
                shape,
                Group {
                    content: [
                        Line{
                            startX: w/2-w/4
                            startY: h/2
                            endX: w/2+w/4
                            endY: h/2
                            styleClass: "modifierGateway"
                        }, Line{
                            startX: w/2
                            startY: h/2-h/4
                            endX: w/2
                            endY: h/2+h/4
                            styleClass: "modifierGateway"
                        }
                    ]
                }
            ]
            translateX: bind x - w/2
            translateY: bind y - w/2
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    override public function canStartLink(link: ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);

        if (link instanceof ConditionalFlow or link instanceof DefaultFlow) {
            ModelerUtils.setErrorMessage(##"msgError1");
            ret = false;
        }
        return ret;
    }

    override public function copy() : GraphicalElement {
        var t = ParallelGateway {
            title: this.title
            type: this.type
            modeler: this.modeler
            container: this.container
        }
        t.uri = "new:parallelgateway:{modeler.toolBar.counter++}";
        return t;
    }
}