/*
 * MessageIntermediateCatchEvent.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class MessageIntermediateCatchEvent extends IntermediateCatchEvent
{
    public override function create(): Node
    {
        type=CATCH_MESSAGE;
        return super.create();
    }

    public override function canAttach(parent:GraphicalElement) : Boolean {
        var ret=false;
        if(parent instanceof Activity or parent instanceof Pool or parent instanceof Lane) {
            ret=true;
        }
        return ret;
    }    

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = false;
        var c = sizeof getInputConnectionObjects();

        //Un evento intermedio de mensaje puede tener sólo un flujo de mensaje de entrada
        //TODO: Debe haber una forma de checar qué tipo de conexion es la entrante
        if (link instanceof MessageFlow and c == 0) {
            ret = true;
        } else {
            ret = super.canEndLink(link);
        }
        return ret;
    }
}
