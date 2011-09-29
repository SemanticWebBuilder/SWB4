/*
 * AssociationFlow.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * Clase que representa un flujo de asociación en un diagrama BPMN 2.0
 * @author javier.solis
 */

public class AssociationFlow extends ConnectionObject
{
    public override function create(): Node
    {
        strokeDash=[1,5];
        //cubicCurve=true;
        var ret=super.create();
        return ret;
    }    

    public override function copy() : ConnectionObject {
        var t = AssociationFlow {
            ini: this.ini
            end: this.end
            modeler: this.modeler
            strokeDash: this.strokeDash
            //cubicCurve: this.cubicCurve
            uri:"new:associationflow:{modeler.toolBar.counter++}"
        }

        for (handle in handles) {
            t.addLineHandler(handle.getPoint(), false);
        }
        return t;
    }
}
