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
                message
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }

    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret=false;
        if(parent instanceof Pool or parent instanceof Lane)
        {
            ret=true;
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = false;
        var c = sizeof getInputConnectionObjects();

        //Un evento intermedio sólo puede tener un flujo de secuencia de entrada
        if (link instanceof SequenceFlow and c == 0) {
            //Siempre y cuando no esté adherido a una actividad
            if (not(this.getGraphParent() instanceof Activity)) {
                ret = true;
            }
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = false;
        var c = sizeof getOutputConnectionObjects();

        //Un evento intermedio sólo puede tener un flujo de secuencia de salida
        if (link instanceof SequenceFlow and c == 0) {
            ret = true;
        }
        return ret;
    }
}



