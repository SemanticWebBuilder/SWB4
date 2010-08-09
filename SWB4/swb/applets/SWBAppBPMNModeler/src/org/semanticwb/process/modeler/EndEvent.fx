/*
 * EndEvent.fx
 *
 * Created on 13/02/2010, 11:32:09 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class EndEvent extends ThrowEvent
{
    public override function create(): Node
    {
         var ret=super.create();
         stroke=Color.web(Styles.color_endEvent);
         shape.strokeWidth=4;
         stkw=4;
         stkwo=5;

         colorAdjust.hue=-0.03;
         colorAdjust.brightness=-0.33;
         colorAdjust.contrast=0.25;
         colorAdjust.saturation=1;

         return ret;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        return false;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean {
        var ret = true;
        if (not(link instanceof SequenceFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError8"));
        } else if (link instanceof SequenceFlow and link.ini instanceof ExclusiveIntermediateEventGateway) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError9"));
        }
        return ret;
    }

}

