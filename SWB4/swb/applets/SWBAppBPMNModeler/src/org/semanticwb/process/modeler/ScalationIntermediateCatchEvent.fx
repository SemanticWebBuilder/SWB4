/*
 * ScalationIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ScalationIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_SCALATION;
        return super.create();
    }

    public override function canAttach(parent:GraphicalElement) : Boolean {
        var ret=false;
        if(parent instanceof Activity or parent instanceof Pool or parent instanceof Lane) {
            ret=true;
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof SequenceFlow and link.ini instanceof EventBasedGateway) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError40"));
        }
        return ret;
    }
}
