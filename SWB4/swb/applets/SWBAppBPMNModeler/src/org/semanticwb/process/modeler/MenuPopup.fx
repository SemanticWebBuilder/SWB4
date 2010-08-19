package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.input.*;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class MenuPopup extends CustomNode {
public var x: Number = 0;
    public var y: Number = 0;
    public var optionsMargin: Number = 2;
    public var optionsPadding: Number = 1;

    var maxH: Number = 0;
    var maxW: Number = 0;
    var os: MenuItem[];

    public var content:Action[] = null on replace {
        buildOptions(content);
    }

    override public function create(): Node {
        blocksMouse = true;
        this.visible = false;

        var r = Rectangle {
            x: bind x
            y: bind y
            styleClass: "menu"
            width: bind maxW + optionsMargin * 2
            height: bind maxH + optionsMargin * 2 - optionsPadding * 2
        }

        return Group {
            cache: true;
            content: bind [
                r,
                os
            ]
            onMouseExited: function (e: MouseEvent) {
                visible = false;
            }
            onMouseClicked: function (e: MouseEvent) {
                visible = false;
            }
        }
    }

    function buildOptions(options: Action[]) {
        delete os;
        maxH = optionsMargin;
        maxW = 0;

        for (option in options) {
            option.offsety = maxH;

            var o = MenuItem {
                x: bind x + optionsMargin
                y: bind y + option.offsety
                caption: bind option.label
                action: option.action
                textOffsetX: bind x + optionsMargin * 3
                isSeparator: bind option.isSeparator;
            }

            if (o.getWidth() > maxW) {
                maxW = o.getWidth();
            }

            maxH += o.getHeight() + optionsPadding;
            insert o into os;
        }

        for (o in os) {
            o.sizeToText = false;
            o.w = maxW;
        }
    }

    public function show (e: MouseEvent): Void {
        this.x = e.sceneX - 10;
        this.y = e.sceneY - 10;
        this.visible = (e.button == e.button.SECONDARY);
    }

    public function hide() {
        this.visible = false;
    }

    public function setOptions(acts: Action[]) {
        if (acts != null and acts.size() > 0) {
            this.content = acts;
        }
    }
}
