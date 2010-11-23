/*
 * TimerStartEvent.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class TimerStartEvent extends StartEvent
{
    override public function create(): Node
    {
        type=CATCH_TIMER;
        return super.create();
    }

    override public function canAddToDiagram(): Boolean {
        var ret = true;
        var c = 0;

        if (modeler.containerElement != null) {
            for (child in modeler.containerElement.containerChilds) {
                if (child instanceof StartEvent) {
                    c++;
                }
            }

            if (modeler.containerElement instanceof AdhocSubProcess) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError48");
            } else if (modeler.containerElement instanceof EventSubProcess) {
                ret = false;
                if (c != 0) {
                    ModelerUtils.setErrorMessage(##"msgError44");
                } else {
                    ModelerUtils.setErrorMessage(##"msgError43");
                }
            } else if (c != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError44");
            } else {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError50");
            }
        }
        return ret;
    }
}