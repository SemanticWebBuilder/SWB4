/*
 * StartEvent.fx
 *
 * Created on 13/02/2010, 11:29:53 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import org.semanticwb.process.modeler.AdhocSubProcess;

/**
 * @author javier.solis
 */

public class StartEvent extends CatchEvent
{
    public override function create(): Node
    {
         var ret=super.create();
         colorAdjust.hue=0.52;
         colorAdjust.brightness=-0.19;
         colorAdjust.contrast=0.25;
         colorAdjust.saturation=1;
         shape.styleClass = "startEvent";
         return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canEndLink(link);

        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError33"));
        }

        if (link instanceof MessageFlow) {
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError34"));
            ret = false;
        }
        return ret;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        var ret = super.canStartLink(link);
        
        if(link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError35"));
        }
        return ret;
    }

    override public function canAddToDiagram(): Boolean {
        var ret = super.canAddToDiagram();
        if (modeler.containerElement != null) {
            if (modeler.containerElement instanceof EventSubProcess) {
                ret = false;
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError41"));
            }
            if (modeler.containerElement instanceof AdhocSubProcess) {
                ret = false;
                ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError48"));
            }
        }
        return ret;
    }
}