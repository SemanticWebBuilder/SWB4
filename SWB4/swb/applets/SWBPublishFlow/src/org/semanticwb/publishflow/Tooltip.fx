/*
 * Tooltip.fx
 *
 * Created on 5/03/2010, 01:46:17 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author victor.lorenzana
 */

public class Tooltip extends CustomNode {
    public var x:Number;
    public var y:Number;
    public-init var text:String;
    var _root: Group;
    var t:Text;
    var r:Rectangle;    
    function initializeCustomNode():Void {
        _root = Group {
            content: [
                r=Rectangle {
                    style:Styles.style_tooltip
                    x: bind x+13
                    y: bind y+6
                //Bind the rectangle width with the Text width.
                    width: bind t.boundsInParent.width+5
                    height: bind t.boundsInParent.height+5
                },
                t = Text {
                    x:bind r.x+3;
                    y:bind r.y+10;
                    content:text
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
