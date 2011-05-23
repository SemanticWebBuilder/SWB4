/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class CallProcess extends CallActivity {
    protected var strokeDash : Float[];
    var icons: ImageView[];
    var adjust: ColorAdjust = ColorAdjust {
        hue: -0.83
        brightness: -0.07
        contrast: 0.25
        saturation: 1
    }

    def imgCollapsed = ImageView {
        image: Image {
            url: "{__DIR__}images/n_collapsed.png"
        }
        effect: adjust
    }

    var modifiers: HBox = HBox {
        nodeVPos: VPos.CENTER
        translateX: bind shape.boundsInLocal.minX + (shape.boundsInLocal.width - modifiers.boundsInLocal.width) / 2
        translateY: bind shape.boundsInLocal.minY + shape.boundsInLocal.height - (modifiers.boundsInLocal.height + 6)
        spacing: 5
        content: bind icons
    }

    public override function create(): Node
    {
        resizeable=true;
        containerable=true;
        blocksMouse = true;
        w=100;
        h=60;
        initializeCustomNode();

        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y - h/6
            width: bind w
            height: bind h/2
        }

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            strokeDashArray: bind strokeDash
            styleClass: "globalTask"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        setType(type);
        getMarkers();

        var actions: MenuItem[] = [
            MenuItem {isSeparator: true},
             MenuItem {
                caption: ##"actCopy"
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = copy();
                    modeler.setCopyNode(t);
                }
             },
             MenuItem {
                caption: ##"actCut"
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = cut();
                    modeler.setCopyNode(t);
                    ModelerUtils.setResizeNode(null);
                }
             }
         ];
         insert actions before menuOptions[0];

        return Group
        {
            content: [
                shape, text, modifiers
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible:bind canView()
        };
    }

    override var onMouseClicked = function (e: MouseEvent): Void {
        if (e.button == e.button.PRIMARY) {
            if (e.clickCount >= 2) {
                if (not (modeler.containerElement == this)) {
                    text.startEditing()
                }
            }
        } else if (e.button == e.button.SECONDARY) {
            if (modeler.getFocusedNode() == this) {
                ModelerUtils.stopToolTip();
                ModelerUtils.popup.setOptions(menuOptions);
                ModelerUtils.popup.show(e);
            }
        }
    }

    function getMarkers() : Void {
        delete icons;
        insert imgCollapsed into icons;
    }

    override public function copy() : GraphicalElement {
        var t = CallProcess {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            container: this.container
            x: this.x + 10
            y: this.y + 10
            uri: "new:callprocess:{modeler.toolBar.counter++}"
        }
        return t;
    }
}