package org.semanticwb.process.modeler;

import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextOrigin;
import javafx.scene.CustomNode;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class MenuItem extends CustomNode {

    public var caption: String;
    public var action: function(e: MouseEvent);
    public var x: Number;
    public var y: Number;
    public var w: Number;
    public var h: Number;
    public var textOffsetX: Number;
    public var sizeToText: Boolean = true;
    public var isSeparator: Boolean = false;
    var r: Rectangle;

    override public function create(): Node {
        var t:Text = Text {
            content: bind caption
            textOrigin: TextOrigin.TOP
            //styleClass: "menuItem"
            id: "caption"
        }

        var t2: Text;
        if (isSeparator) {
            r = Rectangle {
                x: bind x
                y: bind y
                height: 2
                width: bind w
                styleClass: "menuItemSeparator"
            }
        } else {
            r = Rectangle {
                x: bind x
                y: bind y
                width: bind if (sizeToText) t.boundsInLocal.width + 7 else w
                height: bind t.boundsInLocal.height + 7
                styleClass: "menuItem"
            }
            t2 = Text {
                textOrigin: TextOrigin.TOP
                x: bind if (sizeToText) r.boundsInParent.minX + (r.width - t.boundsInLocal.width) / 2 else textOffsetX
                y: bind r.boundsInParent.minY + (r.height - t.boundsInLocal.height) / 2
                content: bind caption
                //styleClass: "menuItem"
                id: "caption"
                fill: bind if (hover) Color.WHITE else Color.BLACK
            }
        }

        return Group {
            content: [
                r,
                t2
            ]
            onMouseClicked: action
        }
    }

    public function getWidth(): Number {
        return r.width;
    }

    public function getHeight(): Number {
        return r.height;
    }
};