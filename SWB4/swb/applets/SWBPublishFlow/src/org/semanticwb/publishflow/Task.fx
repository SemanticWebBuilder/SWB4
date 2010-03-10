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
import java.lang.Void;
import java.lang.Void;


/**
 * @author victor.lorenzana
 */
public class Task extends FlowObject {

    public var users: String[];
    public var roles: String[];
    public var description:String="";
    public var days:Integer;
    public var hours:Integer;
    public var button:ImgButton;
    
    public override function create(): Node {
            super.create();
            
        cursor = Cursor.HAND;
        w = 100;
        h = 60;
        tooltip=title;
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
            /*onMouseEntered:function(e:MouseEvent):Void
            {
                opacity=1
            }
            onMouseExited:function(e:MouseEvent):Void
            {
                opacity=0.5
            }*/

            action:function(): Void
            {
                editTask();
            }

        }

        // add connectionPoints

        var cp:ConnectionPoint=ConnectionPoint
        {
            id:"5"
            x:bind {x}-w/2;
            y:bind {y}-h/2;
        };
        insert cp into connectionPoints;


        cp=ConnectionPoint
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

        return Group {
                    content: [
                        shape, text,button
                    ]
                    scaleX: bind s;
                    scaleY: bind s;
                    opacity: bind o;
                    effect: Styles.dropShadow;
                };
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
        dialog.locale=modeler.locale;
        dialog.roles=roles;
        dialog.name=title;
        dialog.con=ToolBar.conn;
        dialog.setVisible(true);
        if(not dialog.cancel)
        {
            title=dialog.name;
            description=dialog.description;
            users=dialog.users;
            roles=dialog.roles;
        }
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
