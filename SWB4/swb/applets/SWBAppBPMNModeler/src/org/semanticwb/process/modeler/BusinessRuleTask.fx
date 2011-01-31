/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;

/**
 * @author Hasdai Pacheco
 */

public class BusinessRuleTask extends Task {
    public override function create(): Node {
        type = TYPE_RULE;
        return super.create();
    }
}
