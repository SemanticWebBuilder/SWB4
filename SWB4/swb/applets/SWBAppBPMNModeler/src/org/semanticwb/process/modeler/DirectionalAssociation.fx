/*
 * DirectionalAssociation.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * Clase que representa un flujo de asociación direccional en un diagrama BPMN 2.0
 * @author javier.solis
 */

public class DirectionalAssociation extends AssociationFlow
{
    public override function create(): Node
    {
        arrowType=ARROW_TYPE_ASSOCIATION;
        strokeDash=[2,5];
        //cubicCurve=true;
        var ret=super.create();
        return ret;
    }

    public override function copy() : ConnectionObject {
        var t = DirectionalAssociation {
            ini: this.ini
            end: this.end
            arrowType: this.arrowType
            modeler: this.modeler
            strokeDash: this.strokeDash
            //cubicCurve: this.cubicCurve
            uri:"new:dirassociationflow:{modeler.toolBar.counter++}"
        }

        for (handle in handles) {
            t.addLineHandler(handle.getPoint(), false);
        }
        return t;
    }
}
