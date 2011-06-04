/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.geometry.VPos;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextBox;
import javafx.scene.text.TextAlignment;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.text.TextOrigin;
import javafx.scene.text.TextBoundsType;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.BlurType;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public class ModalDialog extends CustomNode {
    public var x: Number;
    public var y: Number;
    public var w: Number;
    public var h: Number;
    public var title: String;
    public var borderSize: Number = 2;
    public var modeler: Modeler;

    var titleBar : Rectangle;
    var titleText : Text;
    var contentArea: Rectangle;
    var borderRect : Rectangle;
    var boundNode: GraphicalElement;
    var tit: TextBox;
    var desc: TextBox;
    var over: Boolean = false;
    
    def closeButton: ImageView = ImageView {
        x: bind titleBar.x + titleBar.width - 25
        y: bind titleBar.y + 5
        image: Image {
            url: "{__DIR__}images/btn_close.png"
        }
    }
    
    def closeButtonOver: ImageView = ImageView {
        x: bind titleBar.x + titleBar.width - 25
        y: bind titleBar.y + 5
        image: Image {
            url: "{__DIR__}images/btn_close2.png"
        }
    }

    var buttonGroup: Group = Group {
        content: bind if (over) [closeButtonOver] else [closeButton]
        onMouseClicked: function (e: MouseEvent) {
            hide();
        }
        onMouseEntered: function(e: MouseEvent) {
            over = true;
        }
        onMouseExited: function(e: MouseEvent) {
            over = false;
        }
    }

    public override function create() : Node {
        titleText = Text {
            content: bind title
            textOrigin: TextOrigin.TOP;
            boundsType: TextBoundsType.VISUAL
            font: Font {
                name: "Verdana"
                embolden: true
                size: 14
            }
        }

        return Group {
            content: [
                Rectangle {
                    width: bind scene.width
                    height: bind scene.height
                    x: 0
                    y: 0
                    fill: Color.rgb(255, 255, 255, 0.0);
                },
                borderRect = Rectangle {
                    width: bind w
                    height: bind h
                    fill: Color.BLACK
                    x: bind (scene.width - w) / 2
                    y: bind (scene.height - h) / 2
                    effect: DropShadow {
                        offsetX: 2
                        offsetY: 2
                        color: Color.rgb(136, 136, 136);
                        blurType: BlurType.GAUSSIAN
                    }
                },
                titleBar = Rectangle {
                    width: bind w - borderSize * 2
                    height: 30
                    fill: LinearGradient {
                        startX: 0.0
                        startY: 1.0
                        endX: 0.0
                        endY: 0.0
                        proportional: true
                        stops: [
                            Stop {
                                offset: 0.0
                                color: Color.rgb(221, 221, 221);
                            },
                            Stop {
                                offset: 1.0
                                color: Color.rgb(239, 239, 239);
                            }
                        ]
                    }
                    x: bind borderRect.x + borderSize
                    y: bind borderRect.y + borderSize
                },
                Text {
                    content: bind title
                    textOrigin: TextOrigin.TOP
                    boundsType: TextBoundsType.VISUAL
                    font: Font {
                        name: "Verdana"
                        embolden: true
                        size: 14
                    }
                    x: bind titleBar.x + (titleBar.boundsInLocal.width - titleText.boundsInLocal.width) / 2
                    y: bind titleBar.y + (titleBar.boundsInLocal.height - titleText.boundsInLocal.height) / 2
                },
                buttonGroup,
                contentArea = Rectangle {
                    width: bind w - borderSize * 2
                    height: bind h - titleBar.boundsInLocal.height - borderSize * 3
                    fill: Color.rgb(242, 243, 242);
                    x: bind borderRect.x + borderSize
                    y: bind titleBar.y + titleBar.boundsInLocal.height + borderSize
                },
                HBox {
                    nodeVPos: VPos.CENTER
                    spacing: 10
                    layoutX: bind contentArea.x + 12
                    layoutY: bind contentArea.y + 12
                    content: [
                        VBox {
                            nodeVPos: VPos.CENTER
                            nodeHPos: HPos.RIGHT
                            spacing: 8
                            content: [
                                Text {
                                    textAlignment: TextAlignment.RIGHT
                                    content: "Título:"
                                },
                                Text {
                                    textAlignment: TextAlignment.RIGHT
                                    content: "Descripción:"
                                }
                            ]
                        },
                        VBox {
                            nodeVPos: VPos.CENTER
                            nodeHPos: HPos.LEFT
                            spacing: 8
                            content: [
                                tit = TextBox {
                                    multiline: false
                                    columns: 30
                                },
                                desc = TextBox {
                                    multiline: true
                                    columns: 30
                                }
                            ]
                        },
                        VBox {
                            nodeVPos: VPos.CENTER
                            nodeHPos: HPos.CENTER
                            spacing: 10
                            content: [
                                Button {
                                    text: "Cancelar"
                                    action: function () {
                                        hide();
                                    }
                                },
                                Button {
                                    text: "Guardar"
                                    action: function () {
                                        hide();
                                        if (boundNode != null) {
                                            tit.commit();
                                            desc.commit();
                                            boundNode.title = tit.text;
                                            boundNode.description = desc.text;
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
            blocksMouse:true
        };
    }

    public function show() : Void {
        this.visible = true;
    }

    public function hide() : Void {
        this.visible = false;
    }

    public function setBoundNode(node: GraphicalElement) {
        this.boundNode = node;
        tit.text = boundNode.title;
        desc.text = boundNode.description;
    }
}
