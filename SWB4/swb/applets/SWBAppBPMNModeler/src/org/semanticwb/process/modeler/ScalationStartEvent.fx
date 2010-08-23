/*
 * ScalationStartEvent.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ScalationStartEvent extends StartEvent
{
    override public function create(): Node
    {
        type=CATCH_SCALATION;
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
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError48"));
            } else if (modeler.containerElement instanceof EventSubProcess) {
                if (c != 0) {
                    ret = false;
                    ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError44"));
                }
            } else if (c != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError44"));
            } else {
                ret = false;
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError50"));
            }
        }
        return ret;
    }
}