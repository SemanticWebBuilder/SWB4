/*
 * StartEvent.fx
 *
 * Created on 26/02/2010, 12:30:43 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * @author victor.lorenzana
 */

public class StartEvent extends Event
{
    
    public var alert:ImageView;
    public var viewalert:Boolean=bind busyoutputPoints==0;
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
         stroke=Color.web(Styles.color_iniEvent);
         text.visible=false;
         return ret;
    }

    bound public function getTextAlert() : String
    {
       if(busyoutputPoints==0)
       {
           "1"
       }
       else
       {
           ""
       }


    }

    override public function canIniLink(link: ConnectionObject): Boolean {
        if(link instanceof AuthorizeLink or link instanceof NoAuthorizeLink)
        {
            return false;
        }
        if(getBusyPoints()==1)
        {
            return false;
        }

        return true;
    }
    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        return false;
    }
    public function getXml() : String
    {
            modeler.organizeMap();
        return "";
    }

}
