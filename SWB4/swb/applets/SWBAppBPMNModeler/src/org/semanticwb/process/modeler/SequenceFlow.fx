/*
 * SequenceFlow.fx
 *
 * Created on 13/02/2010, 10:51:55 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;

/**
 * @author javier.solis
 */

public class SequenceFlow extends ConnectionObject
{
    public override function create(): Node
    {
        arrowType=ARROW_TYPE_SEQUENCE;
        var ret=super.create();
        return ret;
    }
}
