/*
 * ParallelIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ParallelIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_PARALLEL;
        return super.create();
    }

    public override function canAttach(parent:GraphicalElement) : Boolean {
        var ret = super.canAttach(parent);

        if (parent instanceof Activity) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError39"));
        }
        return ret;
    }
}
