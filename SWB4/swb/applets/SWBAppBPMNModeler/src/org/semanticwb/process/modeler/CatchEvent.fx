/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class CatchEvent extends Event
{
    public override function create(): Node
    {
        var ret = super.create();

        if (isInterrupting) {
            var actions: Action[] = [
                Action {
                    label: bind if (this.cancelActivity) "Interruptor" else "No Interruptor"
                    action: function (e: MouseEvent) {
                        this.cancelActivity = not this.cancelActivity;
                    }
                }, Action {isSeparator: true}
            ];
            insert actions before menuOptions[0];
        }
        return ret;
    }
}
