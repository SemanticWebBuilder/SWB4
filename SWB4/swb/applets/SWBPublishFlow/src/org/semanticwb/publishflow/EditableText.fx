/*
 * EditableText.fx
 *
 * Created on 26/02/2010, 12:32:54 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.scene.text.TextOrigin;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextBox;
/**
 * @author victor.lorenzana
 */

public class EditableText extends CustomNode
{
    public var x : Number;
    public var y : Number;
    public var width : Number;
    public var height : Number;

    public var text : String;

    var textb : TextBox;
    var textl : Text;

    public function stopEditing() :Void
    {
        //textb.unselect();
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
             content: bind text
             style: Styles.style_task_text
             textOrigin: TextOrigin.TOP
             wrappingWidth: bind width
             transforms: [
                 Translate{
                     x: bind x-(textl.boundsInLocal.width)/2+2
                     y: bind y-(textl.boundsInLocal.height)/2
                 }
             ]
             //smooth:true;
             visible: true
        };
        textb= TextBox
        {
             text: text
             style: Styles.style_task_textbox
             translateX:bind x - width/2
             translateY:bind y -10
             width:bind width
             height: 20
             visible: false
             selectOnFocus:true
             onKeyTyped:function(k:KeyEvent)
             {
                 //if(k.char=="\n")stopEditing();
                 var c=0+k.char.charAt(0);
                 if(c==27)cancelEditing();
                 //println(c);
             }
             action: function() {
                stopEditing();
             }
        };
        return Group
        {
            content: [
               textl,textb
            ]
        };
    }
}
