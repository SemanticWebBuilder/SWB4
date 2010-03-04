/*
 * AuthorActivity.fx
 *
 * Created on 4/03/2010, 03:52:00 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author victor.lorenzana
 */

public class AuthorActivity extends Event{

    public override function create(): Node
    {
         var ret=super.create();
         stroke=Color.web(Styles.color_authorEvent);
         shape.strokeWidth=4;
         stkw=4;
         stkwo=5;
         return ret;
    }
    override public function canEndLink(link: ConnectionObject): Boolean {
        if(link instanceof NoAuthorizeLink)
        {
            return true;
        }
        return false;

    }

    override public function canIniLink(link:ConnectionObject) : Boolean
    {
        return false;
    }
}
