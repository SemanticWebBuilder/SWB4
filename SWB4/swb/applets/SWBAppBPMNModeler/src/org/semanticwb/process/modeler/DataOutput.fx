/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;

/**
 * @author hasdai
 */

public class DataOutput extends DataObject {
    override public function create(): Node
    {
        type=TYPE_OUTPUT;
        return super.create();
    }
}
