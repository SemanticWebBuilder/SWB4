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

public class ComplexGateway extends Gateway
{
    public override function create(): Node
    {
        initializeCustomNode();
        w=50;
        h=50;        
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y: bind y + 40
            width: bind w + 60
            height: bind h
        }
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
                    modeler.copyNode = t;
                    ModelerUtils.popup.hide();
                }
             },
         ];
         insert actions before menuOptions[0];

        return Group
        {
            content: [
                Group {
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
                                }, Line{
                                    startX: w/2-w/6
                                    startY: h/2-h/6
                                    endX: w/2+w/6
                                    endY: h/2+h/6
                                    styleClass: "modifierGateway"
                                }, Line{
                                    startX: w/2+w/6
                                    startY: h/2-h/6
                                    endX: w/2-w/6
                                    endY: h/2+h/6
                                    styleClass: "modifierGateway"
                                }
                            ]
                        }
                    ]
                    translateX: bind x - w/2
                    translateY: bind y - w/2
                    scaleX: bind s;
                    scaleY: bind s;
                }, text
            ]
            visible: bind canView()
        };
    }

    override public function copy() : GraphicalElement {
        var t = ComplexGateway {
            title: this.title
            type: this.type
            modeler: this.modeler
            container: this.container
        }
        t.uri = "new:complexgateway:{modeler.toolBar.counter++}";
        return t;
    }
}