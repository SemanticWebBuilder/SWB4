package org.semanticwb.process.modeler;

import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextOrigin;
import javafx.scene.CustomNode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.CacheHint;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

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
    public var items: MenuItem[];
    public var owner: MenuPopup;
    public var mchild: MenuPopup = null on replace {drawTriangle();};
    public var offsety: Number;
    public var x: Number;
    public var y: Number;
    public var w: Number;
    public var h: Number;
    public var textOffsetX: Number;
    public var sizeToText: Boolean = true;
    public var isSeparator: Boolean = false;
    var r: Rectangle;
    var p: Path;
    var t2: Text;
    var tsize = 8;

    override public function create(): Node {
        var t:Text = Text {
            content: bind caption
            textOrigin: TextOrigin.TOP
            font: bind if (status.equals(STATUS_SELECTED)) Font.font("Verdana", FontWeight.BOLD, 11) else Font.font("Verdana", 11)
            id: "caption"
        }

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
                styleClass: bind
                    if (status.equals(STATUS_DISABLED)) {
                        "menuItemCaptionDisabled"
                    } else if (r.hover) {
                        "menuItemCaptionOver"
                    } else {
                        "menuItemCaptionNormal"
                    }
                textOrigin: TextOrigin.TOP
                x: bind if (sizeToText) r.x + (r.width - t.boundsInLocal.width) / 2 else textOffsetX
                y: bind r.y + (r.height - t.boundsInLocal.height) / 2
                content: bind caption
                font: bind if (status.equals(STATUS_SELECTED)) Font.font("Verdana", FontWeight.BOLD, 11) else Font.font("Verdana", 11)
                //fill: bind if (hover) Color.WHITE else if (status.equals(STATUS_ENABLED) or status.equals(STATUS_SELECTED)) Color.BLACK else Color.GRAY
            }
        }

        return Group {
            cache: true;
            cacheHint:CacheHint.SPEED;
            content: bind [
                r, t2, p, mchild
            ]
            onMouseClicked: bind
                if (not status.equals(STATUS_DISABLED) and action != null) {
                    action
                } else {
                    function(e: MouseEvent){closeAll()}
                }
                onMouseEntered: function (e: MouseEvent) {
                    if (mchild != null and status.equals(STATUS_ENABLED)) {
                        showChild(x + w + 4, y + h - 2);
                    }
                    owner.closeMenus(this);
                }
        }
    }

    public function getWidth(): Number {
        return r.width + tsize;
    }

    public function getHeight(): Number {
        return r.height;
    }

    public function hide () : Void {
        this.visible = false;
        this.mchild.hide();
    }

    public function show () : Void  {
        this.visible = true;
        this.mchild.hide();
    }

    public function showChild(cx: Float, cy: Float) : Void {
        this.mchild.show(cx, cy);
        this.visible = true;
    }

    public function hideChild() : Void {
        this.visible = false;
        this.mchild.hide();
    }

    public function closeAll() {
        var p = owner;
        while (p != null) {
            p.hide();
            p = p.miParent.owner;
        }
    }

    function drawTriangle() {
        if (mchild != null and mchild.items.size() > 0) {
            p = Path {
                styleClass: bind t2.styleClass
                stroke: bind t2.fill
                elements: [
                    MoveTo {
                        x: bind x + r.width - 6
                        y: bind y + (r.height/2)
                    },
                    LineTo {
                        x: bind x + r.width - 6 - tsize / 2
                        y: bind y + (r.height - tsize) / 2
                    },
                    LineTo {
                        x: bind x + r.width - 6 - tsize / 2
                        y: bind y + (r.height - tsize) / 2 + tsize
                    }
                    ClosePath{}
                ]
            }
        }
    }
}