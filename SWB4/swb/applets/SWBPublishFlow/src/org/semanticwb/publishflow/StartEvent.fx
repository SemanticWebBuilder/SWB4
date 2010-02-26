/*
 * StartEvent.fx
 *
 * Created on 26/02/2010, 12:30:43 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.paint.Color;
/**
 * @author victor.lorenzana
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
