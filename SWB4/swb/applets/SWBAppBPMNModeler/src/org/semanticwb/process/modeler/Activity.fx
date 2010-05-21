/*
 * FlowLink.fx
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
        var ret = true;        
        //No se puede iniciar un flujo de secuencia si la actividad está
        //dentro de un subproceso adhoc
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

        //No se puede terminar un flujo de mensaje si están en el mismo pool
        if (link instanceof MessageFlow) {
            if (link.ini.getPool() == getPool()) {
                ret = false;
                ModelerUtils.setErrorMessage("MessageFlow must cross pool boundary");
            }
        }

        //No se puede terminar una asociación si no viene de un artefacto
        if (link instanceof AssociationFlow) {
            if (not(link.ini instanceof Artifact)) {
                ret = false;
                ModelerUtils.setErrorMessage("Association cannot link activities");
            }
        }
        return ret;
    }
}
