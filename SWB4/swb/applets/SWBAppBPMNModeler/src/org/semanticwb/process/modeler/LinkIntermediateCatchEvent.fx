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

}
