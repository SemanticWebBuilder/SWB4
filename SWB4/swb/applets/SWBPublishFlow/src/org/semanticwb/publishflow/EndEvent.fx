/*
 * EndEvent.fx
 *
 * Created on 26/02/2010, 12:50:06 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.semanticwb.publishflow.AuthorizeLink;
import org.semanticwb.publishflow.NoAuthorizeLink;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * @author victor.lorenzana
 */

public class EndEvent extends Event
{
    public var alert:ImageView;
    public var viewalert:Boolean=bind busyPoints==0;
    public override function create(): Node
    {
         alert=ImageView
        {
                image:Image
                {
                    url: "{__DIR__}images/alerta.png"
                }
                visible:bind viewalert;
                x:bind {x}-(w/2)-7
                y:bind {y}-(h/2)-7

        }
         var ret:Group=super.create() as Group;
         insert alert into ret.content;
         title="Terminar flujo";
         stroke=Color.web(Styles.color_endEvent);
         shape.strokeWidth=4;
         stkw=4;
         stkwo=5;
         return ret;
    }

    override public function canEndLink(link: ConnectionObject): Boolean {
        if(link instanceof AuthorizeLink or link instanceof NoAuthorizeLink)
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
