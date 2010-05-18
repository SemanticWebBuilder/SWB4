/*
 * MessageIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class RuleIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_RULE;
        return super.create();
    }

    public override function canAttach(parent:GraphicalElement) : Boolean {
        var ret=false;
        if(parent instanceof Activity or parent instanceof Pool or parent instanceof Lane) {
            ret=true;
        }
        return ret;
    }
}
