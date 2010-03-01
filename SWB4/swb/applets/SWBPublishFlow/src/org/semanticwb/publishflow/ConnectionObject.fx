/*
 * ConnectionObject.fx
 *
 * Created on 26/02/2010, 12:31:15 PM
 */
package org.semanticwb.publishflow;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import java.lang.Math;
import javafx.scene.Cursor;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.input.MouseButton;
import org.semanticwb.publishflow.Styles;
import org.semanticwb.publishflow.DialogChangeTransition;

/**
 * @author victor.lorenzana
 */
public class ConnectionObject extends CustomNode {

    public var authorize: Boolean;
    public var modeler: Modeler;
    public var ini: GraphElement;
    public var end: GraphElement;
    public var title: String;
    public var action: String = bind title;
    public var uri: String;
    public var text: EditableText;
    var color = Styles.style_connection_authorize;
    var color_row = Styles.style_connection_arrow_authorize;
    public var points: Point[];
    var path: Path;
    var o: Number = 0.8;                   //opacity

    public override function create(): Node {
        authorize = true;
        cursor = Cursor.HAND;
        //                HLineTo { x: 70 },
        //                QuadCurveTo { x: 120  y: 60  controlX: 100  controlY: 0 },
        //                ArcTo { x: 10  y: 50  radiusX: 100  radiusY: 100  sweepFlag: true },

        var pini = Point { x: bind getConnectionX(ini, end) y: bind getConnectionY(ini, end) };
        var pend = Point { x: bind getConnectionX(end, ini) y: bind getConnectionY(end, ini) };
        var pinter1 = Point { x: bind getInter1ConnectionX(ini, end, pini, pend) y: bind getInter1ConnectionY(ini, end, pini, pend) };
        var pinter2 = Point { x: bind getInter2ConnectionX(ini, end, pini, pend) y: bind getInter2ConnectionY(ini, end, pini, pend) };
        points = [pini, pinter1, pinter2, pend];
        text = EditableText {
            text: bind title with inverse
            x: bind pini.x + (pend.x - pini.x) / 2
            y: bind pini.y + (pend.y - pini.y) / 2
            width: 80
            height: 20
        }
        path = Path {
            elements: [
                MoveTo { x: bind pini.x, y: bind pini.y },
                LineTo { x: bind pinter1.x, y: bind pinter1.y },
                LineTo { x: bind pinter2.x, y: bind pinter2.y },
                LineTo { x: bind pend.x, y: bind pend.y }
            ]
            style: bind color with inverse;
            smooth: true;
            strokeLineCap: StrokeLineCap.ROUND
            strokeLineJoin: StrokeLineJoin.ROUND
        };
        return Group {
                    content: [
                        path, text,
                        Line {
                            startX: bind pend.x;
                            startY: bind pend.y;
                            endX: bind pend.x + 6 * Math.cos(getArrow(points, -45));
                            endY: bind pend.y - 6 * Math.sin(getArrow(points, -45));
                            style: bind color_row with inverse;
                            stroke: bind path.stroke;
                            strokeLineCap: StrokeLineCap.ROUND
                            smooth: true;
                        },
                        Line {
                            startX: bind pend.x;
                            startY: bind pend.y;
                            endX: bind pend.x + 6 * Math.cos(getArrow(points, 45));
                            endY: bind pend.y - 6 * Math.sin(getArrow(points, 45));
                            style: bind color_row with inverse;
                            stroke: bind path.stroke;
                            strokeLineCap: StrokeLineCap.ROUND
                            smooth: true;
                        }
                    ]
                    opacity: bind o;
                    effect: Styles.dropShadow
                };
    }

    public function remove() {
        modeler.remove(this);
    }

//    override var onMouseDragged = function ( e: MouseEvent ) : Void
//    {
//        if(modeler.clickedNode==this)
//        {
////            x=dx+e.sceneX;
////            y=dy+e.sceneY;
//        }
//    }
    public function mousePressed(e: MouseEvent): Void {
        if (modeler.clickedNode == null) {
            modeler.clickedNode = this;
            modeler.focusedNode = this;
        }
    }

    override var onMousePressed = function (e: MouseEvent): Void {
                mousePressed(e);
            }
    override var onMouseReleased = function (e: MouseEvent): Void {
                if (modeler.clickedNode == this) {
                    modeler.clickedNode = null;
                }
                if (e.clickCount == 2 and e.button == MouseButton.PRIMARY) {
                    if (ini instanceof Task and end instanceof Task) {
                        var dialog: DialogChangeTransition;
                        dialog = new DialogChangeTransition(authorize);
                        dialog.setVisible(true);
                        authorize = dialog.mode;
                        if (authorize) {
                            color = Styles.style_connection_authorize;
                            color_row = Styles.style_connection_arrow_authorize;
                        } else {
                            color = Styles.style_connection_not_authorize;
                            color_row = Styles.style_connection_arrow_not_authorize
                        }
                    }


                }
            }
    override var onMouseEntered = function (e) {
                if (modeler.tempNode == null and modeler.clickedNode == null) modeler.disablePannable = true;
                //path.stroke=Color.web(Styles.color_over);
                color = Styles.style_connection_over;
                color_row = Styles.style_connection_row_over;
                path.strokeWidth = 3;
            }
    override var onMouseExited = function (e) {
                if (modeler.tempNode == null and modeler.clickedNode == null) modeler.disablePannable = false;
                //path.stroke=Color.web(Styles.color);
                if (authorize) {
                    color = Styles.style_connection_authorize;
                    color_row = Styles.style_connection_arrow_authorize;
                } else {
                    color = Styles.style_connection_not_authorize;
                    color_row = Styles.style_connection_arrow_not_authorize
                }
                path.strokeWidth = 2;
            }































































































































bound function getConnectionX(ini: GraphElement, end: GraphElement): Number
    {
        if(ini!=null)
        {
            if(end!=null)
            {
                var dx=end.x-ini.x;
                var dy=end.y-ini.y;
                if(Math.abs(dx)>=Math.abs(dy))
                {
                    if(dx>0)
                    {
                        ini.x+ini.w/2+2;
                    }else
                    {
                        ini.x-ini.w/2-2;
                    }
                }else
                {
                    ini.x;
                }
            }else
            {
                var dx=modeler.mousex-ini.x;
                var dy=modeler.mousey-ini.y;
                if(Math.abs(dx)>=Math.abs(dy))
                {
                    if(dx>0)
                    {
                        ini.x+ini.w/2+2;
                    }else
                    {
                        ini.x-ini.w/2-2;
                    }
                }else
                {
                    ini.x;
                }
            }
        }else
        {
            var dx=end.x-modeler.mousex;
            var dy=end.y-modeler.mousey;
            if(Math.abs(dx)>=Math.abs(dy))
            {
                if(dx>0)
                {
                    modeler.mousex+10/2+2;
                }else
                {
                    modeler.mousex-10/2-2;
                }
            }else
            {
                modeler.mousex;
            }
        }
    }

    bound function getConnectionY(ini: GraphElement, end: GraphElement): Number
    {
        if(ini!=null)
        {
            if(end!=null)
            {
                var dx=end.x-ini.x;
                var dy=end.y-ini.y;
                if(Math.abs(dy)>Math.abs(dx))
                {
                    if(dy>0)
                    {
                        ini.y+ini.h/2+2;
                    }else
                    {
                        ini.y-ini.h/2-2;
                    }
                }else
                {
                    ini.y;
                }
            }else
            {
                var dx=modeler.mousex-ini.x;
                var dy=modeler.mousey-ini.y;
                if(Math.abs(dy)>Math.abs(dx))
                {
                    if(dy>0)
                    {
                        ini.y+ini.h/2+2;
                    }else
                    {
                        ini.y-ini.h/2-2;
                    }
                }else
                {
                    ini.y;
                }
            }
        }else
        {
            var dx=end.x-modeler.mousex;
            var dy=end.y-modeler.mousey;
            if(Math.abs(dy)>Math.abs(dx))
            {
                if(dy>0)
                {
                    modeler.mousey+10/2+2;
                }else
                {
                    modeler.mousey-10/2-2;
                }
            }else
            {
                modeler.mousey;
            }
        }
    }

    bound function getInter1ConnectionX(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
    {
        if(end!=null)
        {
            if(ini.y!=pini.y)
            {
                pini.x
            }else
            {
                pini.x+(pend.x-pini.x)/2;
            }
        }else
        {
            pini.x;
        }

    }

    bound function getInter1ConnectionY(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
    {
        if(end!=null)
        {
            if(ini.y!=pini.y)
            {
                pini.y+(pend.y-pini.y)/2;
            }else
            {
                pini.y
            }
        }else
        {
            pini.y;
        }
    }

    bound function getInter2ConnectionX(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
    {
        if(end!=null)
        {
            if(end.y!=pend.y)
            {
                pend.x
            }else
            {
                pini.x+(pend.x-pini.x)/2;
            }
        }else
        {
            getInter1ConnectionX(ini, end, pini, pend);
        }
    }

    bound function getInter2ConnectionY(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
    {
        if(end!=null)
        {
            if(end.y!=pend.y)
            {
                pini.y+(pend.y-pini.y)/2;
            }else
            {
                pend.y
            }
        }else
        {
            getInter1ConnectionY(ini, end, pini, pend);
        }
    }

    bound function getArrow(points:Point[], grad: Number) : Number
    {
        var pini:Point=points[(sizeof points)-2];
        var pend:Point=points[(sizeof points)-1];

        if(pend.x >= pini.x)
        {
            Math.PI-Math.atan((pend.y-pini.y)/(pend.x-pini.x))+(grad*Math.PI)/180;
        }else
        {
            2*Math.PI-Math.atan((pend.y-pini.y)/(pend.x-pini.x))+(grad*Math.PI)/180;
        }
    }
}
