/*
 * ErrorIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ErrorIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_ERROR;
        return super.create();
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        ModelerUtils.setErrorMessage(##"msgError54");
        return false;
    }
}
