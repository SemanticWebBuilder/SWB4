/*
 * EndEvent.fx
 *
 * Created on 13/02/2010, 11:32:09 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class EndEvent extends Event
{
    public override function create(): Node
    {
         var ret=super.create();
         stroke=Color.web(Styles.color_endEvent);
         shape.strokeWidth=4;
         stkw=4;
         stkwo=5;
         return ret;
    }

    override public function canIniLink(link:ConnectionObject) : Boolean
    {
        return false;
    }
}

