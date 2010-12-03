/*
 * ReceiveTask.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import org.semanticwb.process.modeler.ExclusiveIntermediateEventGateway;
import org.semanticwb.process.modeler.MessageIntermediateCatchEvent;

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
            for(ele in link.ini.getOutputConnectionObjects()) {
                if(ele instanceof SequenceFlow and ele.end instanceof MessageIntermediateCatchEvent) {
                    ret = false;
                    ModelerUtils.setErrorMessage(##"msgError52");
                }
            }
        }
        return ret;
    }

    public override function canStartLink(link: ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError56");
        }
        return ret;
    }
}
