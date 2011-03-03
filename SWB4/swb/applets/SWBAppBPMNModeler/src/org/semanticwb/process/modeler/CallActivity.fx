/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Node;

/**
 * @author hasdai
 */

public class CallActivity extends Activity {
    public override var over on replace {
        if (over and not selected) {
            shape.styleClass = "globalTaskHover";
        } else if (not selected) {
            shape.styleClass = "globalTask";
        }
    }

    public override var selected on replace {
        if (selected) {
            shape.styleClass = "globalTaskFocused";
        } else {
            shape.styleClass = "globalTask";
        }
    }

    public override function create(): Node
    {
        var ret=super.create();
        return ret;
    }
}
