/*
 * FlowNode.fx
 *
 * Created on 13/02/2010, 10:59:22 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import org.semanticwb.process.modeler.CompensationIntermediateCatchEvent;

/**
 * @author javier.solis
 */

public class FlowNode extends GraphicalElement
{
    override public function create(): Node
    {
        zindex=2;
        return super.create();
    }

    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret=false;
        if(parent instanceof Pool or parent instanceof Lane)
        {
            ret=true;
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof SequenceFlow) {
            if (not(link.ini.getPool() == getPool())) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError14");
            }
        }

        if (link instanceof AssociationFlow) {
            if (not(link.ini instanceof Artifact) and not(link.ini instanceof CompensationIntermediateCatchEvent and link.ini.getGraphParent() instanceof Activity)) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError15");
            }
        }

        if (link instanceof MessageFlow) {
            if (link.ini.getPool() == getPool()) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError16");
            }
        }
        return ret;
    }
}