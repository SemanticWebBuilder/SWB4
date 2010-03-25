/*
 * ConnectionObject.fx
 *
 * Created on 13/02/2010, 10:49:51 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.lang.Math;
import javafx.scene.Cursor;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.ClosePath;

/**
 * @author javier.solis
 */

public-read var ARROW_TYPE_SEQUENCE="sequence";
public-read var ARROW_TYPE_MESSAGE="mesage";
public-read var ARROW_TYPE_ASSOCIATION="association";
public-read var ARROW_TYPE_NONE="none";

public class ConnectionObject  extends CustomNode
{
    public var modeler:Modeler;
    public var ini : GraphElement;
    public var end : GraphElement;
    public var cubicCurve : Boolean;

    public var title : String;
    public var action : String=bind title;
    public var uri : String;

    public var text : EditableText;

    public var points : Point[];
    public var strokeWidth : Float=2;
    public var arrowType : String;

    protected var path : Path;
    protected var arrow : Path;
    protected var o : Number = 0.8;                   //opacity
    protected var strokeDash : Float[];

    protected var notGroup : Boolean;                 //No agrega los elementos path y arrow al grupo

    public override function create(): Node
    {
        cursor=Cursor.HAND;

        var pini=Point{ x: bind getConnectionX(ini,end) y: bind getConnectionY(ini,end) };
        var pend=Point{ x: bind getConnectionX(end,ini) y: bind getConnectionY(end,ini) };
        var pinter1=Point{ x: bind getInter1ConnectionX(ini,end,pini,pend) y: bind getInter1ConnectionY(ini,end,pini,pend) };
        var pinter2=Point{ x: bind getInter2ConnectionX(ini,end,pini,pend) y: bind getInter2ConnectionY(ini,end,pini,pend) };
        points=[pini,pinter1,pinter2,pend];

        text=EditableText
        {
            text: bind title with inverse
            x: bind pini.x + (pend.x - pini.x) / 2
            y: bind pini.y + (pend.y - pini.y) / 2
            width: 80
            height: 20
            fill: true
        }

        if(not(arrowType.equals(ARROW_TYPE_NONE)))
        {
            var close:ClosePath;
            if(arrowType.equals(ARROW_TYPE_MESSAGE))
            {
                close=ClosePath{};
            }
            arrow=Path {
                elements: [
                    MoveTo{
                        x:bind pend.x+8*Math.cos(getArrow(points, -45))
                        y:bind pend.y-8*Math.sin(getArrow(points, -45))
                    },
                    LineTo{
                        x:bind pend.x
                        y:bind pend.y
                    },
                    LineTo{
                        x:bind pend.x+8*Math.cos(getArrow(points, 45))
                        y:bind pend.y-8*Math.sin(getArrow(points, 45))
                    },close
                ]
                //style: Styles.style_connection
                stroke: Color.web(Styles.color);
            };
            if(arrowType.equals(ARROW_TYPE_MESSAGE))
            {
                arrow.fill=Color.WHITE;
                arrow.strokeWidth=1;
            }else if(arrowType.equals(ARROW_TYPE_SEQUENCE))
            {
                arrow.fill=Color.web(Styles.color);
                arrow.strokeWidth=1;
            }else
            {
                arrow.strokeWidth=2;
            }
        }
        
        if(cubicCurve)
        {
            path=Path {
                elements: [
                    MoveTo{x:bind pini.x,y:bind pini.y},
                    CubicCurveTo {
                        controlX1: bind pinter1.x
                        controlY1: bind pinter1.y
                        controlX2: bind pinter2.x
                        controlY2: bind pinter2.y
                        x: bind pend.x
                        y: bind pend.y
                    }
                ]
                style: Styles.style_connection
                strokeDashArray: strokeDash
                strokeWidth: strokeWidth;
                //smooth:true;
            };
        }else
        {
            path=Path {
                elements: [
                    MoveTo{x:bind pini.x,y:bind pini.y},
                    LineTo{x:bind pinter1.x,y:bind pinter1.y},
                    LineTo{x:bind pinter2.x,y:bind pinter2.y},
                    LineTo{x:bind pend.x,y:bind pend.y}
                ]
                style: Styles.style_connection
                strokeDashArray: strokeDash
                strokeWidth: strokeWidth;
                //smooth:true;
                //strokeLineCap: StrokeLineCap.ROUND
                //strokeLineJoin: StrokeLineJoin.ROUND
            };
        }

        var ret;
        if(not notGroup)
        {
            ret=Group
            {
                content: [
                    Group
                    {
                        content: [
                            path,
                            arrow
                        ]
                        effect: Styles.dropShadow
                    },
                    text
                ]
                opacity: bind o;
            };
        }else
        {
            ret=Group{};
        }
        return ret;
    }

    public function remove()
    {
        modeler.remove(this);
    }

//    override var onMouseDragged = function ( e: MouseEvent ) : Void
//    {
//        if(ModelerUtils.clickedNode==this)
//        {
////            x=dx+e.sceneX;
////            y=dy+e.sceneY;
//        }
//    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
        if(ModelerUtils.clickedNode==null)
        {
            ModelerUtils.clickedNode=this;
            modeler.focusedNode=this;
        }
    }

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(ModelerUtils.clickedNode==this)
        {
            ModelerUtils.clickedNode=null;
        }
    }

    override var onMouseEntered = function(e)
    {
        if(modeler.tempNode==null and ModelerUtils.clickedNode==null)modeler.disablePannable=true;
        path.stroke=Color.web(Styles.color_over);
        arrow.stroke=Color.web(Styles.color_over);
        path.strokeWidth=strokeWidth+1;
    }

    override var onMouseExited = function(e)
    {
        if(modeler.tempNode==null and ModelerUtils.clickedNode==null)modeler.disablePannable=false;
        path.stroke=Color.web(Styles.color);
        arrow.stroke=Color.web(Styles.color);
        path.strokeWidth=strokeWidth;
    }

    protected bound function getConnectionX(ini: GraphElement, end: GraphElement): Number
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

    protected bound function getConnectionY(ini: GraphElement, end: GraphElement): Number
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

    protected bound function getInter1ConnectionX(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
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

    protected bound function getInter1ConnectionY(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
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

    protected bound function getInter2ConnectionX(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
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

    protected bound function getInter2ConnectionY(ini: GraphElement, end: GraphElement, pini: Point,pend: Point): Number
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

    protected bound function getArrow(points:Point[], grad: Number) : Number
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
