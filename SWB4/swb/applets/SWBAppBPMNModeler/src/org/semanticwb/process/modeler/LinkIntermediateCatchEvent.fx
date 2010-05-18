/*
 * LinkIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class LinkIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_LINK;
        return super.create();
    }

    public override function canEndLink(link:ConnectionObject) {
        //El evento intermedio de enlace tipo catch no puede tener flujos de entrada
        return false;
    }

    public override function canStartLink(link:ConnectionObject) {
        //El evento intermedio de enlace tipo catch no puede tener más de un flujo de
        //secuencia de salida y no puede tener flujos de mensaje de salida
        var ret = super.canStartLink(link);
        if (link instanceof MessageFlow) {
            ret = false;
        }

        return false;
    }
}
