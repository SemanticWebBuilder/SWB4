/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class EventSubProcess extends SubProcess {
    public override function create(): Node {
        type = TYPE_EVENT;
        return super.create();
    }

    override public function canStartLink(link: ConnectionObject) {
        var ret = super.canStartLink(link);

        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError47");
        }
        return ret;
    }

    override public function canEndLink(link: ConnectionObject) {
        var ret = super.canStartLink(link);

        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError47");
        }
        return ret;
    }
}