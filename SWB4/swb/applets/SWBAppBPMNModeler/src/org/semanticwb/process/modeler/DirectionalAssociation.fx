/*
 * DirectionalAssociation.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class DirectionalAssociation extends AssociationFlow
{
    public override function create(): Node
    {
        arrowType=ARROW_TYPE_ASSOCIATION;
        strokeDash=[2,5];
        cubicCurve=true;
        var ret=super.create();
        return ret;
    }
}
