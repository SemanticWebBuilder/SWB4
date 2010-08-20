/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.input.MouseEvent;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class Action {
    public var label: String;
    public var action: function(e: MouseEvent): Void;
    public var offsety: Number;
    public var isSeparator: Boolean = false;
    public var status;
}
