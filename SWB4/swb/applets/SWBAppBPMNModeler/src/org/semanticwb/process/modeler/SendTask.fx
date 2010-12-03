/*
 * SendTask.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class SendTask extends Task
{
    public override function create(): Node
    {
        type=TYPE_SEND;
        return super.create();
    }

    public override function canEndLink(link: ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError57");
        }
        return ret;
    }
}
