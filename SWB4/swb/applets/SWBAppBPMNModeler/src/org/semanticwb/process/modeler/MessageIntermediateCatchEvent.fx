/*
 * MessageIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class MessageIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_MESSAGE;
        return super.create();
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        var c = 0;

        for(ele in getInputConnectionObjects()) {
            if(ele instanceof MessageFlow) {
                c++;
            }
        }

        if (link instanceof MessageFlow and c != 0) {
            ret = false;
            ModelerUtils.setErrorMessage("MessageIntermediateCatchEvent can have only one incoming MessageFlow");
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);

        if(link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage("MessageIntermediateCatchEvent cannot have outgoing MessageFlow");
        }
        return ret;
    }

}
