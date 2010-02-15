/*
 * StartEvent.fx
 *
 * Created on 13/02/2010, 11:29:53 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class StartEvent extends Event
{
    public override function create(): Node
    {
         var ret=super.create();
         stroke=Color.web(Styles.color_iniEvent);
         return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        return false;
    }
}
