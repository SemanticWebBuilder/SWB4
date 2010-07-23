/*
 * ReceiveTask.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import org.semanticwb.process.modeler.ExclusiveIntermediateEventGateway;

/**
 * @author javier.solis
 */

public class ReceiveTask extends Task
{
    public override function create(): Node
    {
        type=TYPE_RECEIVE;
        return super.create();
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof SequenceFlow and link.ini instanceof ExclusiveIntermediateEventGateway) {
            ret = true;
        }
        return ret;
    }
}
