/*
 * Activity.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import org.semanticwb.process.modeler.Artifact;

/**
 * @author javier.solis
 */

public class Activity extends FlowNode
{
    public override function create(): Node
    {
        var ret=super.create();
        return ret;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canStartLink(link);
        if (link instanceof SequenceFlow) {
            if (getContainer() != null and getContainer() instanceof AdhocSubProcess) {
                ret = false;
                ModelerUtils.setErrorMessage("SequenceFlow is not allowed in AdHoc Subprocess");
            }
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof DirectionalAssociation and link.ini instanceof CompensationIntermediateCatchEvent) {
            ret = true;            
        }
        return ret;
    }

}
