/*
 * MessageEndEvent.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class MessageEndEvent extends EndEvent
{
    override public function create(): Node
    {
        type=THROW_MESSAGE;
        return super.create();
    }

    override public function canStartLink(link:ConnectionObject) : Boolean {
        var ret = false;
        //De un evento final de mensaje sólo pueden salir flujos de mensaje
        if (link instanceof MessageFlow) {
            ret = true;
        } else {
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError28"));
        }
        return ret;
    }
}