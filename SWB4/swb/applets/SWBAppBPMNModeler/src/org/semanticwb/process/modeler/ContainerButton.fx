/*
 * ContainerPath.fx
 *
 * Created on 13/04/2010, 11:37:17 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import org.semanticwb.process.modeler.Styles;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

public class ContainerButton extends CustomNode
{
    public var container:GraphicalElement;
    public var modeler:Modeler;

    public override function create(): Node
    {
        cache=true;
        var t:Text;
        var r:Rectangle;
        var root = Group {
            content: [
                Rectangle {
                    style:Styles.style_containerButton
                    width: bind t.boundsInParent.width+5
                    height: bind t.boundsInParent.height+5
                },
                t = Text {
                    x:bind r.x+4;
                    y:bind r.y+12;
                    content:bind if(container!=null)container.title else ModelerUtils.getLocalizedString("root")
                    font: Font {size:10 name:"Verdana"}
                    fill: bind if(container!=modeler.containerElement)Color.BLUE else Color.BLACK
                    //underline:bind if(container!=modeler.containerElement)true else false
                    smooth:true
                }
            ]
            cursor:bind if(container!=modeler.containerElement)Cursor.HAND else Cursor.DEFAULT;
            blocksMouse:true
            
            onMouseClicked: function ( e: MouseEvent ) : Void
            {
                modeler.containerElement=container;
            }
        }
        return root;
    }
}