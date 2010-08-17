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
import java.lang.Math;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.input.KeyEvent;

/**
 * @author javier.solis
 */

public def ARROW_TYPE_SEQUENCE="sequence";
public def ARROW_TYPE_MESSAGE="mesage";
public def ARROW_TYPE_ASSOCIATION="association";
public def ARROW_TYPE_NONE="none";

public class ConnectionObject  extends CustomNode
{
    public var modeler:Modeler;
    public var ini : GraphicalElement;
    public var end : GraphicalElement;
    public var cubicCurve : Boolean;
    public var title : String;
    public var action : String=bind title;
    public var uri : String;
    public var text : EditableText;
    public var points : Point[];
    public var arrowType : String;

    protected var path : Path;
    protected var arrow : Path;
    protected var strokeDash : Float[];
    protected var notGroup : Boolean;               //No agrega los elementos path y arrow al grupo

    var pini: Point;
    var pend: Point;
    public override function create(): Node
    {
        pini=Point{ x: bind getConnectionX(ini,end) y: bind getConnectionY(ini,end) };
        pend=Point{ x: bind getConnectionX(end,ini) y: bind getConnectionY(end,ini) };
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

        setType(arrowType);
        
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
                styleClass: "connObject"
                strokeDashArray: strokeDash
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
                styleClass: "connObject"
                strokeDashArray: strokeDash
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
                    },
                    text
                ]
                visible: bind canView()
            };
        }else
        {
            ret=Group{};
        }
        return ret;
    }
    
    function setType(type: String): Void {
        if (type.equals(ARROW_TYPE_SEQUENCE) or type.equals(ARROW_TYPE_ASSOCIATION)) {
            arrow = Path {
                elements: [
                    MoveTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(-45.0))
                        y:bind pend.y-8.0*Math.sin(getArrow(-45.0))
                    },
                    LineTo{
                        x:bind pend.x
                        y:bind pend.y
                    },
                    LineTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(45.0))
                        y:bind pend.y-8.0*Math.sin(getArrow(45.0))
                    },
                ]
                strokeWidth: bind path.strokeWidth
                stroke: bind path.stroke
            };
        } else if (type.equals(ARROW_TYPE_MESSAGE)) {
            arrow = Path {
                elements: [
                    MoveTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(-45.0))
                        y:bind pend.y-8.0*Math.sin(getArrow(-45.0))
                    },
                    LineTo{
                        x:bind pend.x
                        y:bind pend.y
                    },
                    LineTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(45.0))
                        y:bind pend.y-8.0*Math.sin(getArrow(45.0))
                    },
                    ClosePath{}
                ]
                strokeWidth: bind path.strokeWidth
                stroke: bind path.stroke
                styleClass: "arrowMessage"
            };
        }
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
            modeler.setFocusedNode(this);
            path.requestFocus();
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
        
    }

    override var onMouseExited = function(e)
    {
        if(modeler.tempNode==null and ModelerUtils.clickedNode==null)modeler.disablePannable=false        
    }

    override var onKeyPressed = function( e: KeyEvent )
    {
        keyPressed(e);
    }

    public function keyPressed( e: KeyEvent )
    {
        if(e.code==e.code.VK_DELETE)
        {
            remove();
        }
    }

    protected bound function getConnectionX(ini: GraphicalElement, end: GraphicalElement): Number
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

    protected bound function getConnectionY(ini: GraphicalElement, end: GraphicalElement): Number
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

    protected bound function getInter1ConnectionX(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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

    protected bound function getInter1ConnectionY(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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

    protected bound function getInter2ConnectionX(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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
            //1.0;
            var ret=getInter1ConnectionX(ini, end, pini, pend);
            ret;
        }
    }

    protected bound function getInter2ConnectionY(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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
            var ret=getInter1ConnectionY(ini, end, pini, pend);
            ret;
        }
    }

    protected bound function getArrow(grad: Number) : Number
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

    public bound function canView():Boolean
    {
        return modeler.containerElement==ini.container or modeler.containerElement==end.container;
    }
}