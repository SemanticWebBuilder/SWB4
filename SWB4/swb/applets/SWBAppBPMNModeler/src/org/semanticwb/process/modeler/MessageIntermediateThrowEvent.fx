/*
 * MessageIntermediateThrowEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class MessageIntermediateThrowEvent extends IntermediateThrowEvent
{
    public override function create(): Node
    {
        type=THROW_MESSAGE;
        return super.create();
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = false;

        //Un evento intermedio de mensaje (throw) sólo puede tener flujos de mensaje de salida
        if (link instanceof MessageFlow) {
            ret = true;
        } else {
            ret = super.canStartLink(link);
        }

        return ret;
    }

}
