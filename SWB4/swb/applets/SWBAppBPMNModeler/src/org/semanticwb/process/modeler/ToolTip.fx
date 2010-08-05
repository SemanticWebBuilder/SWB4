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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;

/**
 * @author victor.lorenzana
 * @modified by Hasdai Pacheco {haxdai@gmail.com}
 */

public def TOOLTIP_ERROR = "error";
public def TOOLTIP_WARNING = "warning";
public def TOOLTIP_NORMAL = "normal";

public class ToolTip extends CustomNode {
    public var x:Number;
    public var y:Number;
    public var text:String;
    public var error:Boolean;
    var _root: Group;
    var t:Text;
    var r:Rectangle;
    var p: Polygon;
    
    override var opacity = 0.0;
    def timeLine: Timeline = Timeline {
        keyFrames: [
            at (0s) {
                opacity => 0.0;
            },
            at (800ms) {
                opacity => 1.0 tween Interpolator.EASEBOTH;
            }
        ]
    }

    def grad = LinearGradient {
        startX: 0.0
        startY: 0.0
        endX: 0.0
        endY:1.0
        stops: [
            Stop {
                offset: 0.0
                color: Color.LIGHTGOLDENRODYELLOW
            }
            Stop {
                offset: 1.0
                color: Color.GOLDENROD
            }
        ]
    }

    def egrad = LinearGradient {
        startX: 0.0
        startY: 0.0
        endX: 0.0
        endY:1.0
        stops: [
            Stop {
                offset: 0.0
                color: Color.ANTIQUEWHITE
            }
            Stop {
                offset: 1.0
                color: Color.INDIANRED
            }
        ]
    }


    function initializeCustomNode(): Void
    {
        _root = Group {
            content: [
                r = Rectangle {
                    //style:Styles.style_tooltip                    
                    x: bind x
                    y: bind y
                    arcWidth: 10
                    arcHeight: 10
                //Bind the rectangle width with the Text width.
                    width: bind t.boundsInParent.width+5
                    height: bind t.boundsInParent.height+5
                    //fill: bind if (error) egrad else grad
                    stroke: Color.GRAY
                },
                p = Polygon {
                    points: bind [r.x + 10, r.y+2, r.x + 15, r.y - 8, r.x+20, r.y+2]
                    id: "triangle"
                    //style:Styles.style_tooltip
                    fill: Color.LIGHTGOLDENRODYELLOW
                    visible: bind (not error)
                },
                Polyline {
                    points: bind [r.x + 10, r.y, r.x + 16, r.y - 7, r.x+20, r.y]
                    id: "triangleBorder"
                    //style:Styles.style_tooltip
                    visible: bind (not error)
                    stroke: Color.GRAY
                },
                t = Text {
                    x:bind r.x+4;
                    y:bind r.y+12;
                    content:bind text
                    font: Font {
                        size:10
                        name:"Verdana"
                    }
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

    public function show(): Void {
        this.visible = true;
        timeLine.playFromStart();
    }

    public function hide(): Void {
        if (timeLine.running) {
            timeLine.stop();
        }
        this.visible = false;
    }

    public function setType(type: String): Void {
        if (type == TOOLTIP_ERROR) {
            r.fill = egrad;
            error = true;
        } else if (type == TOOLTIP_WARNING or type == TOOLTIP_NORMAL) {
            r.fill = grad;
            error = false;
        }
    }
}
