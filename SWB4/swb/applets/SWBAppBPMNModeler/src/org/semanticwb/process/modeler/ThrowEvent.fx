/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class ThrowEvent extends Event
{
    public override function create(): Node
    {
        return super.create();
    }

    public override function canEndLink(link:ConnectionObject) : Boolean{
        var ret = super.canEndLink(link);
        
        if (link.ini instanceof EventBasedGateway) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError38"));
        }
        return ret;
    }
}