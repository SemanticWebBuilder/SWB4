/*
 * IntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:30:48 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import org.semanticwb.process.modeler.SequenceFlow;

/**
 * @author javier.solis
 */

public class IntermediateCatchEvent extends CatchEvent
{
    public override function create(): Node
    {
        initializeCustomNode();
        stroke=Color.web(Styles.color_interEvent);
        cursor=Cursor.HAND;
        w=30;
        h=30;
        stkw=2;
        stkwo=2;
        scaleOff=-0.1;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y + 30
            width: bind w + 60
            height: bind h
        }
        colorAdjust.hue=-0.83;
        colorAdjust.brightness=-0.07;
        colorAdjust.contrast=0.25;
        colorAdjust.saturation=1;

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            //styleClass: "event"
            style: Styles.style_event
            //smooth:true;
        };

        setType(type);

        return Group
        {
            content: [
                shape,
                //text,
                Circle
                {
                    centerX: bind x
                    centerY: bind y
                    radius: bind w/2-3
                    stroke: bind shape.stroke
                    //styleClass: "event"
                    style: Styles.style_event
                    //smooth:true;
                },
                message, text
            ]
            scaleX: bind s;
            scaleY: bind s;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }

    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret = super.canAttach(parent);
        if(ret or parent instanceof Activity) {
            ret = true;
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        var c = 0;

        for(ele in getInputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                c++;
            }
        }

        if (link instanceof SequenceFlow) {
            if (this.getGraphParent() instanceof Activity) {
                ret = false;
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError20"));
            } else if (c != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError21"));
            }
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        var c = 0;

        for(ele in getOutputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                c++;
            }
        }
        
        if (link instanceof SequenceFlow and c != 0) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError22"));
        }
        return ret;
    }
}



