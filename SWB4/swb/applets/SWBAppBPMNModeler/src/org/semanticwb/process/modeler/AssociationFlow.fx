/*
 * AssociationFlow.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class AssociationFlow extends ConnectionObject
{
    public override function create(): Node
    {
        strokeDash=[2,5];
        cubicCurve=true;
        var ret=super.create();
        return ret;
    }
}
