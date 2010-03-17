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
import javafx.scene.text.Text;
import javafx.scene.text.Font;
/**
 * @author victor.lorenzana
 */

public class EndEvent extends Event
{
    public var alert:ImageView;
    public var viewalert:Boolean=bind busyPoints==0;
    public var textAlert:Text;
    public override function create(): Node
    {
        textAlert=Text
        {
            content:bind getTextAlert();
            visible:bind viewalert
            x:bind {x}-(w/2)
            y:bind {y}-(h/2)+5
            font: Font
            {
                size:10
            }
            fill: Color.WHITE

        }
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
         insert textAlert into ret.content;
         title="Terminar flujo";
         text.visible=false;
         stroke=Color.web(Styles.color_endEvent);
         shape.strokeWidth=4;
         stkw=4;
         stkwo=5;
         return ret;
    }

    bound public function getTextAlert() : String
    {
       if(busyPoints==0)
       {
           "1"
       }
       else
       {
           ""
       }


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
