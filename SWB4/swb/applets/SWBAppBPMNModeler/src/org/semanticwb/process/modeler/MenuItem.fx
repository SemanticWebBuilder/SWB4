package org.semanticwb.process.modeler;

import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextOrigin;
import javafx.scene.CustomNode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public def STATUS_ENABLED = "enabled";
public def STATUS_DISABLED = "disabled";
public def STATUS_SELECTED = "selected";
public class MenuItem extends CustomNode {
    public var caption: String;
    public var action: function(e: MouseEvent);
    public var status = STATUS_ENABLED;
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
            font: bind if (status.equals(STATUS_SELECTED)) Font.font("Verdana", FontWeight.BOLD, 11) else Font.font("Verdana", 11)
        }

        var t2: Text;
        if (isSeparator) {
            r = Rectangle {
                x: bind x
                y: bind y
                height: 1
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
                font: bind if (status.equals(STATUS_SELECTED)) Font.font("Verdana", FontWeight.BOLD, 11) else Font.font("Verdana", 11)
                fill: bind if (hover) Color.WHITE else if (status.equals(STATUS_ENABLED) or status.equals(STATUS_SELECTED)) Color.BLACK else Color.GRAY
            }
        }

        return Group {
            content: [
                r,
                t2
            ]
            onMouseClicked: bind if (not status.equals(STATUS_DISABLED)) action else function(e: MouseEvent){}
        }
    }

    public function getWidth(): Number {
        return r.width;
    }

    public function getHeight(): Number {
        return r.height;
    }
};