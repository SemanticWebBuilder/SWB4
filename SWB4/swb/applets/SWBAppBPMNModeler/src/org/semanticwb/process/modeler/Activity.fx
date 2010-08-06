/*
 * Activity.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public def TYPE_ADHOC="adhoc";
public def TYPE_LOOP="loop";
public def TYPE_COMPENSATION="compensation";
public def TYPE_MULTIPLE="multiple";
public def TYPE_TRANSACTION="transaction";

public class Activity extends FlowNode
{
    public override function create(): Node
    {
        var ret=super.create();
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof DirectionalAssociation and link.ini instanceof CompensationIntermediateCatchEvent) {
            ret = true;            
        } else if (link.ini instanceof ExclusiveIntermediateEventGateway) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError1"));
        }
        return ret;
    }

}
