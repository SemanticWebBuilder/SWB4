/*
 * ManualTask.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ManualTask extends Task
{
    public override function create(): Node
    {
        type=TYPE_MANUAL;
        return super.create();
    }

    override public function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError57");
        }
        return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError57");
        }
        return ret;
    }
}
