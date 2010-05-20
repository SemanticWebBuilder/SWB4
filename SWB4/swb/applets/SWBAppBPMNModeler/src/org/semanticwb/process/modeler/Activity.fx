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

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = false;
        if (link.ini.getPool() == getPool()) {
            if (link instanceof SequenceFlow) {
                ret = true;
            }
        } else if (link instanceof MessageFlow) {
            ret = true;
        }
        return ret;
    }
}
