/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class CallUserTask extends CallTask {
    public override function create(): Node {
        type = TYPE_USER;
        return super.create();
    }
}
