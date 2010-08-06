/*
 * FlowNode.fx
 *
 * Created on 13/02/2010, 10:59:22 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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

    override public function mousePressed( e: MouseEvent )
    {
        super.mousePressed(e);
    }

    override public function mouseReleased( e: MouseEvent )
    {
        super.mouseReleased(e);
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
                ModelerUtils.setErrorMessage("SequenceFlow cannot cross pool boundary");
            }
        }

        if (link instanceof AssociationFlow) {
            if (not(link.ini instanceof Artifact) and not(link.ini instanceof CompensationIntermediateCatchEvent and link.ini.getGraphParent() instanceof Activity)) {
                ret = false;
                ModelerUtils.setErrorMessage("Association cannot link FlowNodes");
            }
        }

        if (link instanceof MessageFlow) {
            if (link.ini.getPool() == getPool()) {
                ret = false;
                ModelerUtils.setErrorMessage("MessageFlow must cross pool boundary");
            }
        }
        return ret;
    }
}
