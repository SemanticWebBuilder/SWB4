/*
 * FlowLink.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

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
        var ret = true;
        if (getContainer() != null and getContainer() instanceof AdhocSubProcess) {
            if (link instanceof SequenceFlow) {
                ret = false;
            }
        } else {
            ret = super.canStartLink(link);
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = true;
        if (link.ini.getPool() != getPool()) {
            if (link instanceof SequenceFlow) {
                ret = false;
            }
        } else {
            if (link instanceof MessageFlow) {
                ret= false;
            }
            if (getContainer() != null and getContainer() instanceof AdhocSubProcess) {
                if (link instanceof SequenceFlow) {
                    ret = false;
                }
            }
        }
        return ret;
    }
}
