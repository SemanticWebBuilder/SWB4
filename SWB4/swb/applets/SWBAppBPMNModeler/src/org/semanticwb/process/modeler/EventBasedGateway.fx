/*
 * EventBasedGateway.fx
 *
 * Created on 13/02/2010, 11:28:15 AM
 */

package org.semanticwb.process.modeler;

/**
 * @author javier.solis
 */

public class EventBasedGateway extends Gateway
{
    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);

        if (link instanceof DefaultFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError12"));
        } else if (link instanceof ConditionalFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError13"));
        }
        return ret;
    }
}
