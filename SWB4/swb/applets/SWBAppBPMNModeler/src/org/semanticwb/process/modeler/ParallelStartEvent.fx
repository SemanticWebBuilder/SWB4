/*
 * ParallelStartEvent.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ParallelStartEvent extends StartEvent
{
    override public function create(): Node
    {
        type=CATCH_PARALLEL;
        return super.create();
    }

    override public function canAddToDiagram(): Boolean {
        var ret = true;
        if (modeler.containerElement != null and modeler.containerElement instanceof EventSubProcess) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError43"));
        }
        return ret;
    }
}