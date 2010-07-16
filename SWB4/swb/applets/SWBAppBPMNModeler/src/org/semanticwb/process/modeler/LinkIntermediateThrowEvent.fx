/*
 * LinkIntermediateThrowEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class LinkIntermediateThrowEvent extends IntermediateThrowEvent
{
    public override function create(): Node
    {
        type=THROW_LINK;
        return super.create();
    }

    public override function canStartLink(link:ConnectionObject) {
        //Un evento intermedio de enlace tipo throw no puede tener flujos de salida
        return false;
    }

    public override function canEndLink(link:ConnectionObject) {
        //El evento intermedio de enlace tipo throw no puede tener más de un flujo de
        //secuencia de entrada y no puede tener flujos de mensaje de entrada
        var ret = super.canEndLink(link);
        if (link instanceof MessageFlow) {
            ret = false;
        }

        return ret;
    }

}
