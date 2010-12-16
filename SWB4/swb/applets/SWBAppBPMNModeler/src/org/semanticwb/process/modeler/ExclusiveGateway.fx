/*
 * ExclusiveGateway.fx
 *
 * Created on 13/02/2010, 11:26:19 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.semanticwb.process.modeler.ModelerUtils;
import org.semanticwb.process.modeler.DefaultFlow;
/**
 * @author javier.solis
 */

public class ExclusiveGateway extends Gateway
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

        return Group
        {
            content: [
                Group {
                    content: [
                        shape,
                        Line {
                            startX: w / 2 - w / 6
                            startY: h / 2 - h / 6
                            endX: w / 2 + w / 6
                            endY: h / 2 + h / 6
                            styleClass: "modifierGateway"
                        }, Line {
                            startX: w / 2 + w / 6
                            startY: h / 2 - h / 6
                            endX: w / 2 - w / 6
                            endY: h / 2 + h / 6
                            styleClass: "modifierGateway"
                        }
                    ]
                    translateX: bind x - w / 2
                    translateY: bind y - w / 2
                    scaleX: bind s;
                    scaleY: bind s;
                }, text
            ]
            visible:bind canView()
        };
    }

    override public function canStartLink(link: ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        var count = 0;

        for (ele in getOutputConnectionObjects()) {
            if (ele instanceof DefaultFlow) {
                count++;
            }
        }

        if (not (link instanceof ConditionalFlow or link instanceof DefaultFlow)) {
            ModelerUtils.setErrorMessage(##"msgError1");
            ret = false;
        }

        if (link instanceof DefaultFlow and count > 0) {
            ModelerUtils.setErrorMessage(##"msgError2");
            ret = false;
        }
        return ret;
    }

}