/*
 * Tooltip.fx
 *
 * Created on 5/03/2010, 01:46:17 PM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

/**
 * @author victor.lorenzana
 */

public class ToolTip extends CustomNode {
    public var x:Number;
    public var y:Number;
    public var text:String;
    var _root: Group;
    
    function initializeCustomNode():Void 
    {
        var t:Text;
        var r:Rectangle;

        _root = Group {
            content: [
                r=Rectangle {
                    style:Styles.style_tooltip
                    x: bind x
                    y: bind y
                //Bind the rectangle width with the Text width.
                    width: bind t.boundsInParent.width+5
                    height: bind t.boundsInParent.height+5
                },
                t = Text {
                    x:bind r.x+4;
                    y:bind r.y+12;
                    content:bind text
                    font: Font {size:10 name:"Verdana"}
                }
            ]
        }

    }
    protected override function create() : Node {
        if (_root == null) {
            initializeCustomNode();
        }
        return _root;
    }
}
