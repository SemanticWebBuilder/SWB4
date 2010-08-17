/*
 * EditableText.fx
 *
 * Created on 13/02/2010, 10:31:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.TextOrigin;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.scene.input.MouseEvent;

/**
 * @author javier.solis
 */

public class EditableText extends CustomNode
{
    public var x : Number;
    public var y : Number;
    public var width : Number;
    public var height : Number;
    public var text : String;
    public var fill : Boolean;

    var textb : TextBox;
    var textl : Text;
    var first : Boolean =true;

    var f=bind textb.focused on replace
    {
        if(not f)
        {
            if(first)
            {
                //textb.text=text;
                first=false;
            }else
            {
                stopEditing();
            }
        }
    }

    public function stopEditing() :Void
    {
        text=textb.text;
        cancelEditing();
    }

    public function cancelEditing() :Void
    {
        textb.visible=false;
        textl.visible=true;
    }

    public function startEditing() :Void
    {
        textb.text=text;
        textl.visible=false;
        textb.visible=true;
        textb.selectAll();
        textb.requestFocus();
    }

    public override function create(): Node
    {
        textl= Text
        {
             content: bind "{text}\r"
             textOrigin: TextOrigin.TOP
             textAlignment: TextAlignment.CENTER
             wrappingWidth: bind width
             translateX:bind x-(textl.boundsInLocal.width)/2+2
             translateY:bind y-(textl.boundsInLocal.height)/2
             boundsType:TextBoundsType.VISUAL
             visible: true
        };

        var back;
        if(text!=null and fill)
        {
            back=Rectangle
            {
                x:bind textl.translateX-2
                y:bind textl.translateY-2
                width:bind textl.boundsInLocal.width
                height:bind textl.boundsInLocal.height
                styleClass: "editableText"
            };
        }

        textb= TextBox
        {
             text: text
             styleClass: "editableTextBox"
             translateX:bind x - width/2
             translateY:bind y -10
             width:bind width
             height: 20
             visible: false
             selectOnFocus:true
             onKeyTyped:function(k:KeyEvent)
             {
                 var c=0+k.char.charAt(0);
                 if(c==27)cancelEditing();
             }
             action: function() {
                stopEditing();
             }
        };
        return Group
        {
            content: [
               back,textl,textb
            ]
            
            onMouseClicked: function (e: MouseEvent): Void {
                if (e.clickCount >= 2) {
                    startEditing()
                }
            }
        };
    }
}