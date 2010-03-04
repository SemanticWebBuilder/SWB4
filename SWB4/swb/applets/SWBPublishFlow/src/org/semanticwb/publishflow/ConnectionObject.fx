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
import org.semanticwb.publishflow.Styles;
import javafx.scene.paint.Color;
import org.semanticwb.publishflow.ConnectionPoint;

/**
 * @author victor.lorenzana
 */
public class ConnectionObject extends CustomNode {

    public var modeler: Modeler;
    public var ini: FlowObject;
    public var end: FlowObject;
    public  var title: String;
    public  var action: String = bind title;
    public var uri: String;
    public var text: EditableText;
    public var color = Styles.style_connection;
    public var color_row = Styles.style_connection_row;
    public var points: Point[];    
    var path: Path;
    var o: Number = 0.8;                   //opacity
    var pini:Point=bind getIniConnection(ini) on replace
    {
        replaceIni();
    };
    var pend:Point= bind getEndConnection(end) on replace
    {
        replaceEnd();
    };

    var pinter1 : Point= bind getInter1Connection(ini,end,pini,pend);
    var pinter2 : Point=bind getInter2Connection(ini,end,pini,pend);
    public function replaceIni() : Void
    {
       if(pini instanceof ConnectionPoint)
        {            
            var con:ConnectionPoint=pini as ConnectionPoint;
            con.connectionObject=this;

        }
    }
    public function replaceEnd() : Void
    {
        if(pend instanceof ConnectionPoint)
        {            
            var con:ConnectionPoint=pend as ConnectionPoint;
            con.connectionObject=this;
        }

    }

    

    public override function create(): Node {
        cursor = Cursor.HAND;

       points=[pini,pinter1,pinter2,pend];
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
                        path
                    ,Line {
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
            }

    public function mouseExited(e: MouseEvent): Void {
        if (modeler.tempNode == null and modeler.clickedNode == null) modeler.disablePannable = false;
        path.stroke = Color.web(Styles.color);
        path.strokeWidth = 2;
    }

    override var onMouseEntered = function (e) {
                if (modeler.tempNode == null and modeler.clickedNode == null) modeler.disablePannable = true;
                path.stroke = Color.web(Styles.color_over);
                color = Styles.style_connection_over;
                color_row = Styles.style_connection_row_over;
                path.strokeWidth = 3;
            }
    override var onMouseExited = function (e) {
                mouseExited(e);
            }







bound function getEndConnection(end: FlowObject): Point
{
    if(end==null)
    {
        Point
                {
                   x: modeler.mousex;
                   y: modeler.mousey;
                }
    }
    else
    {
        end.getConnectionPoint(Point
                {
                   x: modeler.mousex;
                   y: modeler.mousey;
                }, this)
    }


}

    bound function getIniConnection(ini: FlowObject): Point
    {
        if(ini==null)
        {
              Point
                {
                   x: modeler.mousex;
                   y: modeler.mousey;
                }
        }
        else
        {
            
            var cp: ConnectionPoint=ini.getConnectionPoint(Point
                {
                    x: modeler.mousex;
                    y: modeler.mousey;
                },this);
                if(cp==null)
                {
                    Point
                    {
                       x: modeler.mousex;
                       y: modeler.mousey;
                    }
                }
                else
                {
                    cp
                }


        }
    }

    

    bound function getInter1Connection(ini: FlowObject, end: FlowObject, pini: Point,pend: Point): Point
    {

        if(end!=null)
        {
            if(ini.x==pini.x and id.equals("1"))
            {
                Point{
                        x:pini.x;
                        y:pini.y-20;
                    }
            }
            else if(ini.x==pini.x and id.equals("3"))
            {
                Point{
                        x:pini.x;
                        y:pini.y+20;
                    }
            }
            else if(ini.y!=pini.y)
            {
                    Point{
                        x:pini.x;
                        y:pini.y+(pend.y-pini.y)/2;
                    }
            }else
            {
                    Point{
                        x:pini.x+(pend.x-pini.x)/2;
                        y:pini.y;
                    }
                
            }
        }
        else
        {
            Point{
            x:pini.x;
            y:pini.y;
            }
        }
    }

bound function getInter2Connection(ini: FlowObject, end: FlowObject, pini: Point,pend: Point): Point
    {
        if(end!=null)
        {
            if(end.x==pend.x and id.equals("1"))
            {
                Point{
                        x:pend.x;
                        y:pend.y-20;
                    }
            }
            else if(end.x==pend.x and id.equals("3"))
            {
                Point{
                        x:pend.x;
                        y:pend.y+20;
                    }
            }
            else if(end.y!=pend.y)
            {
                    Point
                    {
                        x:pend.x;
                        y:pini.y+(pend.y-pini.y)/2;
                        }
            }else
            {
                    Point
                    {
                    x:pini.x+(pend.x-pini.x)/2;
                    y:pini.y+(pend.y-pini.y)/2;
                    }
            }
        }else
        {
            getInter1Connection(ini, end, pini, pend);
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
