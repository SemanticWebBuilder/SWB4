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

/**
 * @author victor.lorenzana
 */
public class Task extends FlowObject {

    var users: String[];
    var roles: String[];
    public override function create(): Node {
            super.create();
        cursor = Cursor.HAND;
        w = 100;
        h = 60;
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
        return Group {
                    content: [
                        shape, text
                    ]
                    scaleX: bind s;
                    scaleY: bind s;
                    opacity: bind o;
                    effect: Styles.dropShadow;
                };
    }

    
    public override function mouseClicked(e: MouseEvent)
      {
        super.mouseClicked(e);
        if (e.clickCount == 2 and e.button == MouseButton.PRIMARY) {
            var dialog: DialogEditActivity;            
            dialog = new DialogEditActivity(title,ToolBar.conn);
            dialog.setVisible(true);

        }
    }

    override public function canIniLink(link: ConnectionObject): Boolean {
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
