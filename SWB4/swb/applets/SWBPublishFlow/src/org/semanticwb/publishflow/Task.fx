/*
 * Task.fx
 *
 * Created on 26/02/2010, 12:48:58 PM
 */
package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import org.semanticwb.publishflow.DialogEditActivity;
import org.semanticwb.publishflow.AuthorizeLink;
import org.semanticwb.publishflow.NoAuthorizeLink;
import org.semanticwb.publishflow.StartEvent;
import org.semanticwb.publishflow.EndEvent;
import org.semanticwb.publishflow.ConnectionPoint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.lang.Void;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.lang.Void;




/**
 * @author victor.lorenzana
 */
public class Task extends FlowObject {

    public var users: String[];
    public var roles: String[];
    public var description:String="Sin descripción";
    public var days:Integer;
    public var hours:Integer;
    public var button:ImgButton;
    public var alert:ImageView;
    public var textAlert:Text;
    public var stextAlert:String;
    public var viewalert:Boolean=bind hasErros(users, roles) or busyPoints<2;
    public override function create(): Node {
            super.create();
            
        cursor = Cursor.HAND;
        w = 100;
        h = 60;
        tooltip=title;

        textAlert=Text
        {
            content:bind stextAlert;
            visible:bind viewalert
            x:bind {x}-(w/2)
            y:bind {y}-(h/2)+5
            font: Font
            {
                size:10
            }
            fill: Color.WHITE

        }

        text = EditableText {
            text: bind title with inverse
            x: bind x
            y: bind y
            width: bind w
            height: bind h
        }
        shape = Rectangle {
            x: bind x - w / 2
            y: bind y - h / 2
            width: w
            height: h
            //effect: lighting
            //styleClass: "task"
            style: Styles.style_task
            smooth: true;
        };
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

        button=ImgButton
        {
            text: "Edit"
            image: "images/edit_task_1.png"
            imageOver: "images/edit_task_1.png"
            scaleX:0.5
            scaleY:0.5
            translateX:bind x+12
            translateY:bind y-5
            opacity:0.8
            action:function(): Void
            {
                editTask();
                onConected();
            }

        }

        // add connectionPoints

        /*var cp:ConnectionPoint=ConnectionPoint
        {
            id:"5"
            x:bind {x}-w/2;
            y:bind {y}-h/2;
        };*/
        //insert cp into connectionPoints;


        var cp:ConnectionPoint=ConnectionPoint
        {
            id:"6"
            x:bind {x}+w/2;
            y:bind {y}-h/2;
        };
        insert cp into connectionPoints;

        cp=ConnectionPoint
        {
            id:"7"
            x:bind {x}-w/2;
            y:bind {y}+h/2;
        };
        insert cp into connectionPoints;


        cp=ConnectionPoint
        {
            id:"8"
            x:bind {x}+w/2;
            y:bind {y}+h/2;
        };
        insert cp into connectionPoints;
        onConected();
        return Group {
                    content: [
                        shape, text,button,alert,textAlert
                    ]
                    scaleX: bind s;
                    scaleY: bind s;
                    opacity: bind o;
                    effect: Styles.dropShadow;
                };
    }
    

    public function getTextAlert() : String
    {
        var value:Integer=0;
        var nusers:Integer=sizeof users;
        var nroles:Integer=sizeof roles;
        if(nusers==0 and nroles==0)
        {
            value++;
            if(getBusyOutputPoints()<2)
            {
                value+=2-getBusyOutputPoints();
            }                      
        }
        else
        {
            
        }
        stextAlert=String.valueOf(value);
    }

   

    bound public function hasErros(users: String[],roles: String[]) :Boolean
    {
        var nusers:Integer=sizeof users;
        var nroles:Integer=sizeof roles;
        if(nusers==0 and nroles==0)
        {
            true
        }
        else
        {
            false
        }


    }
    public function hasErros() :Boolean
    {
        var nusers:Integer=sizeof users;
        var nroles:Integer=sizeof roles;
        if(nusers==0 and nroles==0)
        {
            true
        }
        else
        {

            if(getBusyPoints()<2)
            {
                true
            }
            else
            {
                false
            }
        }
    }

    public override function getSequence() : ConnectionObject
    {
        if(not hasIniAuthorizeLink())
        {
            AuthorizeLink
            {
                modeler:modeler;
                uri:"new:flowlink:{ToolBar.counter++}"
            }

        }
        else
        {
            NoAuthorizeLink
            {
                modeler:modeler;
                uri:"new:flowlink:{ToolBar.counter++}"
            }
        }


    }

    public function editTask() : Void
    {
        var dialog: DialogEditActivity;
        dialog = new DialogEditActivity();
        dialog.users=users;
        dialog.tm=modeler.tm;
        dialog.locale=modeler.locale;
        dialog.description=description;
        dialog.roles=roles;
        dialog.name=title;
        dialog.con=ToolBar.conn;
        dialog.init();
        dialog.setVisible(true);
        if(not dialog.cancel)
        {
            title=dialog.name;
            description=dialog.description;
            users=dialog.users;
            roles=dialog.roles;
        }
    }
    public override function onConected() : Void
    {
        super.onConected();
        stextAlert=getTextAlert();
    }

    public override function mouseClicked(e: MouseEvent)
      {
        super.mouseClicked(e);
        if (e.clickCount == 2 and e.button == MouseButton.PRIMARY) {
            //editTask();

        }
    }

    public function hasIniAuthorizeLink() : Boolean
    {
        for(point in connectionPoints)
        {
            if(point.connectionObject!=null and point.connectionObject instanceof AuthorizeLink and point.connectionObject.ini==this)
            {
                return true;
            }
        }
        return false;
    }
    public function hasIniNoAuthorizeLink() : Boolean
    {
        for(point in connectionPoints)
        {
            if(point.connectionObject!=null and point.connectionObject instanceof NoAuthorizeLink and point.connectionObject.ini==this)
            {
                return true;
            }
        }
        return false;
    }


    override public function canIniLink(link: ConnectionObject): Boolean {
        if(not super.canIniLink(link))
        {
            return false;
        }

        if(link instanceof AuthorizeLink and  hasIniAuthorizeLink())
        {
            return false;
        }
        if(link instanceof NoAuthorizeLink and  hasIniNoAuthorizeLink())
        {
            return false;
        }


        if(link instanceof AuthorizeLink or link instanceof NoAuthorizeLink)
        {
            return true;
        }
        else
        {
            if(link.end==null)
            {
                return true;
            }

            if(link.end instanceof EndEvent)
            {
                return true;
            }
            else
            {
                return false;
            }


        }        
    }

    override public function canEndLink(link: ConnectionObject): Boolean {
        if(link instanceof AuthorizeLink or link instanceof NoAuthorizeLink)
        {
            return true;
        }
        else
        {
            if(link.ini instanceof StartEvent)
            {
                return true;
            }
            else
            {
                return false;
            }


        }

    }

    


}
