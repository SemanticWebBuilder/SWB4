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
import javafx.scene.shape.PathElement;

/**
 * @author victor.lorenzana
 */
public class ConnectionObject extends CustomNode {

    public var modeler: Modeler;
    public var ini: FlowObject;
    public var end: FlowObject;
    public var title: String;
    public var action: String = bind title;
    public var uri: String;
    public var text: EditableText;
    public var color = Styles.style_connection;
    public var color_row = Styles.style_connection_row;
    
    var o: Number = 0.8;                   //opacity
    public var pini: Point = bind getIniConnection(ini) on replace olvalue {
                replaceIni(olvalue);
            };
    public var pend: Point = bind getEndConnection(end) on replace olvalue {
                replaceEnd(olvalue);
            };
    var pinter1: Point = bind getInterFirstConnection(pini,pend);
    var pinter2: Point = bind getInterLastConnection(pini,pend);

    var elements:PathElement[] =bind getPoints(pini,pend,pinter1,pinter2);
    
    var path: Path= Path {
            elements: bind {elements}
            style: bind color with inverse;
            smooth: true;
            strokeLineCap: StrokeLineCap.ROUND
            strokeLineJoin: StrokeLineJoin.ROUND
        };
    public function replaceIni(olvalue: Point): Void {

        if (olvalue == pini) {
            if (pini instanceof ConnectionPoint) {
                var cp: ConnectionPoint = pini as ConnectionPoint;
                if (cp.connectionObject == null) {
                    var index: Integer = javafx.util.Sequences.indexOf(modeler.contents, this);
                    if (index >= 0) {
                        cp.connectionObject = this;
                    }
                }

            }

        } else {
            if (olvalue instanceof ConnectionPoint) {
                var con: ConnectionPoint = olvalue as ConnectionPoint;
                if (con.connectionObject != null) {
                    con.connectionObject = null;
                }
            }
            if (pini instanceof ConnectionPoint) {
                var cp: ConnectionPoint = pini as ConnectionPoint;
                if (cp.connectionObject == null) {
                    var index: Integer = javafx.util.Sequences.indexOf(modeler.contents, this);
                    if (index >= 0) {
                        cp.connectionObject = this;
                    }
                }
            }
        }
    }

    public function replaceEnd(olvalue: Point): Void {
        if (olvalue == pend) {
            if (pend instanceof ConnectionPoint) {
                var cp: ConnectionPoint = pend as ConnectionPoint;
                if (cp.connectionObject == null) {
                    var index: Integer = javafx.util.Sequences.indexOf(modeler.contents, this);
                    if (index >= 0) {
                        cp.connectionObject = this;
                    }
                }

            }

        } else {
            if (olvalue instanceof ConnectionPoint) {
                var con: ConnectionPoint = olvalue as ConnectionPoint;
                if (con.connectionObject != null) {
                    con.connectionObject = null;
                }
            }
            if (pend instanceof ConnectionPoint) {
                var cp: ConnectionPoint = pend as ConnectionPoint;
                if (cp.connectionObject == null) {
                    var index: Integer = javafx.util.Sequences.indexOf(modeler.contents, this);
                    if (index >= 0) {
                        cp.connectionObject = this;
                    }
                }
            }
        }
    }

    public function getPoints(pini: Point,pend: Point,pinter1:Point,pinter2:Point) : PathElement[]
    {
        var pathElements:PathElement[];        
        var mt=MoveTo{x:bind pini.x,y:bind pini.y}
        insert mt into pathElements;
        var lt=LineTo { x: bind pinter1.x, y: bind pinter1.y };
        insert lt into pathElements;
        
        
        
        
        if(Math.abs(pinter1.y-pinter2.y)==0 or Math.abs(pinter1.x-pinter2.x)==0) 
        {
            if(Math.abs(pinter1.y-pinter2.y)==0 and Math.abs(pinter1.x-pinter2.x)!=0)
            {
                    var landa:Number=(ini.x-pinter1.x)/(pinter2.x-pinter1.x);                    
                    if(landa>0 and landa<1) // a traviesa en centro de inicio
                    {
                        //println("landa y: a traviesa en centro de inicio");
                        lt=LineTo { x: bind pinter1.x, y: bind pinter1.y+20 };
                        insert lt into pathElements;


                        lt=LineTo { x: bind pinter2.x, y: bind pinter1.y+20 };
                        insert lt into pathElements;

                        lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                        insert lt into pathElements;
                    }
                    else
                    {
                        lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                        insert lt into pathElements;
                    }


                
            }
            else if( Math.abs(pinter1.x-pinter2.x)==0 and Math.abs(pinter1.y-pinter2.y)!=0)
            {
                var landa:Number=(ini.y-pinter1.y)/(pinter2.y-pinter1.y);
                //println("landa x: {landa}");
                if(landa>0 and landa<1) // a traviesa en centro de inicio
                {
                    //println("landa x: a traviesa en centro de inicio");
                    lt=LineTo { x: bind pinter1.x+20, y: bind pinter1.y };
                    insert lt into pathElements;

                    lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                    insert lt into pathElements;
                }
                else
                {
                    lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                    insert lt into pathElements;
                }                
            }
            else
            {
                 lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                 insert lt into pathElements;
            }



            
        }
        else
        {
            // estan desalineados
            if(pini.x==pinter1.x) 
            {
                lt=LineTo { x: bind pinter2.x, y: bind pinter1.y };
                insert lt into pathElements;
                lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                insert lt into pathElements;
            }
            else
            {
                lt=LineTo { x: bind pinter1.x, y: bind pinter2.y };
                insert lt into pathElements;
                lt=LineTo { x: bind pinter2.x, y: bind pinter2.y };
                insert lt into pathElements;
            }

        }
        lt=LineTo { x: bind pend.x, y: bind pend.y };
        insert lt into pathElements;
        return pathElements;
    }


    

    public override function create(): Node {
        cursor = Cursor.HAND;        
        return Group {
                    content: [
                        path, Line {
                            startX: bind pend.x;
                            startY: bind pend.y;
                            endX: bind pend.x + 6 * Math.cos(getArrow(-45));
                            endY: bind pend.y - 6 * Math.sin(getArrow(-45));
                            style: bind color_row with inverse;
                            stroke: bind path.stroke;
                            strokeLineCap: StrokeLineCap.ROUND
                            smooth: true;
                        },
                        Line {
                            startX: bind pend.x;
                            startY: bind pend.y;
                            endX: bind pend.x + 6 * Math.cos(getArrow(45));
                            endY: bind pend.y - 6 * Math.sin(getArrow(45));
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
            };
    override var onMouseExited = function (e) {
                mouseExited(e);
            };













public bound function getEndConnection(end: FlowObject): Point
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

    public bound function getIniConnection(ini: FlowObject): Point
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

    

    bound function getInterFirstConnection(pini: Point,pend: Point): Point
    {

        if(end!=null)
        {
                if(pini.x>ini.x) // el pini esta a la derecha del centro
                {
                    Point
                    {
                        x: pini.x+20;
                        y: pini.y
                    }
                }
                else if(pini.x==ini.x) // el ini esta al centro
                {
                        if(pini.y>ini.y) // punto 3
                        {
                            Point
                            {
                                x: pini.x;
                                y: pini.y+20
                            }
                        }
                        else // punto 1
                        {
                            Point
                            {
                                x: pini.x;
                                y: pini.y-20
                            }
                        }


                }
                else // el pini esta a la izquierda del centro
                {
                    Point
                    {
                        x: pini.x-20;
                        y: pini.y
                    }
                }


                /*if(pend.y==pini.y and pend.y>end.y)
                {
                    Point{
                            x:pini.x;
                            y:pini.y+20;
                        }
                }
                else if(pend.y==pini.y and pend.y<end.y)
                {
                    Point{
                            x:pini.x;
                            y:pini.y-20;
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

                }*/
        }
        else
        {
            Point{
            x:pini.x;
            y:pini.y;
            }
        }
    }

    bound function getInterLastConnection(pini: Point,pend: Point): Point
    {
        if(end!=null)
        {
                if(pend.x>end.x) // el pini esta a la derecha del centro
                {
                    Point
                    {
                        x: pend.x+20;
                        y: pend.y
                    }
                }
                else if(pend.x==end.x) // el ini esta al centro
                {
                        if(pend.y>end.y) // punto 3
                        {
                            Point
                            {
                                x: pend.x;
                                y: pend.y+20
                            }
                        }
                        else // punto 1
                        {
                            Point
                            {
                                x: pend.x;
                                y: pend.y-20
                            }
                        }


                }
                else // el pini esta a la izquierda del centro
                {
                    Point
                    {
                        x: pend.x-20;
                        y: pend.y
                    }
                }            
        }else
        {
            getInterFirstConnection(pini,pend);
        }
    }

    

    

    bound function getArrow(grad: Number) : Number
    {        

        if(pend.x >= pinter2.x)
        {
            Math.PI-Math.atan((pend.y-pinter2.y)/(pend.x-pinter2.x))+(grad*Math.PI)/180;
        }else
        {
            2*Math.PI-Math.atan((pend.y-pinter2.y)/(pend.x-pinter2.x))+(grad*Math.PI)/180;
        }
    }
    override var onMouseClicked = function ( e: MouseEvent ) : Void
    {
        if(modeler.focusedNode==this)
        {
            mouseClicked(e);
        }
    }
    public function mouseClicked( e: MouseEvent )
    {

    }
}
