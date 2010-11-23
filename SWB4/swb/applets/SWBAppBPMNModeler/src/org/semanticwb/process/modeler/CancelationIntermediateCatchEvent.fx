/*
 * CancelationIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class CancelationIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_CANCELATION;
        return super.create();
    }

    public override function canAttach(parent:GraphicalElement) : Boolean {
        var ret = false;
        
        if(parent instanceof Pool or parent instanceof Lane or parent instanceof TransactionSubProcess) {
            ret = true;
        } else {
            ModelerUtils.setErrorMessage(##"msgError6");
        }
        return ret;
    }
}
