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
import javafx.scene.shape.ClosePath;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.PathElement;
import javafx.util.Sequences;

/**
 * Clase que representa un objeto de conexión. Es la superclase de todas las
 * conexiones en un diagrama BPMN 2.0
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
    //public var cubicCurve : Boolean;
    public var title : String;
    public var action : String=bind title;
    public var uri : String;
    public var text : EditableText;
    //public var points : Point[];
    public var arrowType : String;
    public var handles: LineHandle[];    
    public var over:Boolean on replace {
        if (over and not selected) {
            path.styleClass = "connObjectHover";
        } else if (not selected) {
            path.styleClass = "connObject";
        }
    }
    public var selected:Boolean on replace {
        if (selected) {
            path.styleClass = "connObjectFocused";
        } else {
            path.styleClass = "connObject";
        }
    }
    protected var path : Path;
    protected var arrow : Path;
    protected var strokeDash : Float[];
    protected var notGroup : Boolean;               //No agrega los elementos path y arrow al grupo
    protected var pini: Point;
    protected var pend: Point;
    protected var pinter1: Point;
    protected var pinter2: Point;
    var elements: PathElement[];
    var labelAnchorStart: Point;
    var labelAnchorEnd: Point;
    
    public override function create(): Node
    {
        blocksMouse = true;
        pini=Point{ x: getStartConnectionX(ini,end) y: getStartConnectionY(ini,end) };
        pend=Point{ x: getEndConnectionX(end,ini) y: getEndConnectionY(end,ini) };
        pinter1=Point{ x: getInter1ConnectionX(ini, end, pini, pend), y: getInter1ConnectionY(ini, end, pini, pend)};
        pinter2=Point{ x: getInter2ConnectionX(ini, end, pini, pend), y: getInter2ConnectionY(ini, end, pini, pend)};
        labelAnchorStart = pini;
        labelAnchorEnd = pend;

        text=EditableText
        {
            text: bind title with inverse
            x: bind labelAnchorStart.x + (labelAnchorEnd.x - labelAnchorStart.x) / 2
            y: bind labelAnchorStart.y + (labelAnchorEnd.y - labelAnchorStart.y) / 2
            width: 80
            height: 20
            fill: true
        }

        createPath();
        
//        if(cubicCurve)
//        {
//            path=Path {
//                elements: [
//                    MoveTo{x:bind pini.x,y:bind pini.y},
//                    CubicCurveTo {
//                        controlX1: bind pinter1.x
//                        controlY1: bind pinter1.y
//                        controlX2: bind pinter2.x
//                        controlY2: bind pinter2.y
//                        x: bind pend.x
//                        y: bind pend.y
//                    }
//                ]
//                styleClass: "connObject"
//                strokeDashArray: strokeDash
//                onKeyPressed: onKeyPressed
//                onKeyReleased: onKeyReleased
//            };
//        }else
//        {
//            path=Path {
//                elements: [
//                    MoveTo{x:bind pini.x,y:bind pini.y},
//                    LineTo{x:bind pinter1.x,y:bind pinter1.y},
//                    LineTo{x:bind pinter2.x,y:bind pinter2.y},
//                    LineTo{x:bind pend.x,y:bind pend.y}
//                ]
//                styleClass: "connObject"
//                strokeDashArray: strokeDash
//                onKeyPressed: onKeyPressed
//                onKeyReleased: onKeyReleased
//            };
//        }

        var ret;
        if(not notGroup)
        {
            ret=Group
            {
                content: bind [
                    Group
                    {
                        content: [
                            path,
                            arrow
                        ]
                    },
                    text,
                    handles
                ]
                visible: bind canView()
            };
        }else
        {
            ret=Group{};
        }
        return ret;
    }

    /** Dibuja el trayecto del objeto de conexión.*/
    public function createPath() : Void {
        //println("Creando linea");
        delete elements;
        insert MoveTo{x:bind pini.x,y:bind pini.y} into elements;
        if (not handles.isEmpty()) {
            for (p in handles) {
                insert LineTo{x: bind p.x,y: bind p.y} into elements;
            }
        } else {
            if (end != null) {
                insert LineTo{x: bind pinter1.x,y: bind pinter1.y} into elements;
                insert LineTo{x: bind pinter2.x,y: bind pinter2.y} into elements;
            }
        }
        insert LineTo{x:bind pend.x,y:bind pend.y} into elements;

        path=Path {
            elements: bind elements
            styleClass: "connObject"
            strokeDashArray: strokeDash
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        updateLabelAnchors();
        setType(arrowType);
    }

    /**Crea la flecha correspondiente al tipo de objeto de conexión.*/
    function setType(type: String): Void {
        var lnode = pini;

        if (not handles.isEmpty()) {
            if (handles.size()>=1) {
                var han = handles[handles.size()-1];
                lnode = Point {
                    x: han.x
                    y: han.y
                }
            }
        } else {
            lnode = pinter2;
        }

        if (type.equals(ARROW_TYPE_SEQUENCE) or type.equals(ARROW_TYPE_ASSOCIATION)) {
            var closePath = null;
            if (type.equals(ARROW_TYPE_SEQUENCE)) {
                closePath = ClosePath{};
            }
            arrow = Path {
                elements: [
                    MoveTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(-45.0, lnode))
                        y:bind pend.y-8.0*Math.sin(getArrow(-45.0, lnode))
                    },
                    LineTo{
                        x:bind pend.x
                        y:bind pend.y
                    },
                    LineTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(45.0, lnode))
                        y:bind pend.y-8.0*Math.sin(getArrow(45.0, lnode))
                    },
                    closePath
                ]
                strokeWidth: bind path.strokeWidth
                stroke: bind path.stroke
                fill: bind if (closePath == null) null else path.stroke
            };
        } else if (type.equals(ARROW_TYPE_MESSAGE)) {
            arrow = Path {
                elements: [
                    MoveTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(-45.0, lnode))
                        y:bind pend.y-8.0*Math.sin(getArrow(-45.0, lnode))
                    },
                    LineTo{
                        x:bind pend.x
                        y:bind pend.y
                    },
                    LineTo{
                        x:bind pend.x+8.0*Math.cos(getArrow(45.0, lnode))
                        y:bind pend.y-8.0*Math.sin(getArrow(45.0, lnode))
                    },
                    ClosePath{}
                ]
                strokeWidth: bind path.strokeWidth
                stroke: bind path.stroke
                styleClass: "arrowMessage"
            };
        }
    }

    /**Actualiza los puntos inicial y final del objeto de conexión*/
    public function updatePoints() {
        updateStartPoint();
        updateEndPoint();
        updateMiddlePoints();
        createPath();
    }

    function updateMiddlePoints() {
        pinter1=Point{ x: getInter1ConnectionX(ini, end, pini, pend), y: getInter1ConnectionY(ini, end, pini, pend)};
        pinter2=Point{ x: getInter2ConnectionX(ini, end, pini, pend), y: getInter2ConnectionY(ini, end, pini, pend)};
    }

    /**Actualiza el punto inicial del objeto de conexión*/
    public function updateStartPoint() {
        pini.x = getStartConnectionX(ini, end);
        pini.y = getStartConnectionY(ini, end);
    }

    /**Actualiza el punto final del objeto de conexión*/
    public function updateEndPoint() {
        pend.x = getEndConnectionX(end, ini);
        pend.y = getEndConnectionY(end, ini);
    }

    public function updateLabelAnchors() {
        labelAnchorStart = pini;
        if (not handles.isEmpty()) {
            labelAnchorEnd = handles[0].getPoint();
        } else {
            labelAnchorEnd = pend;
        }
    }


    /**Elimina el objeto de conexión del área de trabajo*/
    public function remove()
    {
        modeler.remove(this);
    }

    override var onMouseMoved = function (e: MouseEvent) : Void {
        if (not modeler.isLocked()) {
            modeler.mouseMoved(e);
        }
    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
        if (not modeler.isLocked()) {
            if(ModelerUtils.clickedNode==null)
            {
                ModelerUtils.clickedNode=this;
                modeler.setSelectedNode(this);
                modeler.setFocusedNode(this);
            }
            modeler.mousePressed(e);
        }
    }

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if (not modeler.isLocked()) {
            if(ModelerUtils.clickedNode==this)
            {
                ModelerUtils.clickedNode=null;
            }
            modeler.mouseReleased(e);
        }
    }

    override var onMouseDragged = function (e: MouseEvent) {
        if (not modeler.isLocked()) {
            modeler.mouseDragged(e);
        }
    }

    override var onMouseEntered = function(e : MouseEvent)
    {
        if (not modeler.isLocked()) {
            over = true;
            if(modeler.tempNode==null and ModelerUtils.clickedNode==null)modeler.disablePannable=true;
        }
        
    }

    override var onMouseExited = function(e : MouseEvent)
    {
        if (not modeler.isLocked()) {
            over = false;
            if(modeler.tempNode==null and ModelerUtils.clickedNode==null)modeler.disablePannable=false
        }
    }

    override var onMouseClicked = function (e: MouseEvent)
    {
        if (not modeler.isLocked()) {
            if (e.button == e.button.SECONDARY) {
                var p = Point {
                    x: e.sceneX
                    y: e.sceneY
                };
                if (handles.isEmpty()) {
                    buildDefaultHandlers();
                } else {
                    addLineHandler(p, true);
                }
            }
        }
    }

    override var onKeyPressed = function( e: KeyEvent )
    {
        if (not modeler.isLocked()) {
            keyPressed(e);
            modeler.keyPressed(e);
        }
    }

    public function keyPressed( e: KeyEvent )
    {
        if(e.code==e.code.VK_DELETE)
        {
            remove();
        }
    }

    override var onKeyReleased = function( e: KeyEvent )
    {
        if (not modeler.isLocked()) {
            keyReleased(e);
            modeler.keyReleased(e);
        }
    }

    public function keyReleased( e: KeyEvent )
    {
    }

    /**Agrega un nodo tirador al trayecto del objeto de conexión en las coordenadas del punto p*/
    public function addLineHandler(p: Point, order: Boolean) : Void {
        var ha = LineHandle {
            x: p.x
            y: p.y
            modeler: modeler
            visible: bind selected
            owner: this
        }
        //ha.setGraphParent();
        
        insert ha into handles;
        if (order) {
            handles = Sequences.sort(handles, LineHandle.xAscComparator) as LineHandle[];
        }
        createPath();
    }

    /**Elimina un nodo tirador del trayecto del objeto de conexión*/
    public function removeLineHandler(lh: LineHandle) : Void {
        delete lh from handles;
        updatePoints();
    }

    /**Agrega los tiradores por defecto al objeto de conexión*/
    public function buildDefaultHandlers() {
        if (pini.x == pend.x or pini.y == pend.y) return;

        delete handles;
        var p1 = Point {
            x: getInter1ConnectionX(ini, end, pini, pend)
            y: getInter1ConnectionY(ini, end, pini, pend)
        }
        var p2 = Point {
            x: getInter2ConnectionX(ini, end, pini, pend)
            y: getInter2ConnectionY(ini, end, pini, pend)
        }
        addLineHandler(p1, false);
        addLineHandler(p2, false);
        labelAnchorStart = handles[0].getPoint();
        labelAnchorEnd = handles[1].getPoint();
    }

    /**Calcula la coordenada en X del punto inicial del objeto de conexión*/
    protected function getStartConnectionX(ini: GraphicalElement, end: GraphicalElement): Number
    {
        var lnode = Point {x: end.x y: end.y}

        if (not handles.isEmpty()) {
            var han = handles[0];
            lnode = Point {x: han.x y: han.y}
        }

        if(ini != null)
        {
            if(end != null)
            {
                var dx = lnode.x - ini.x;
                var dy = lnode.y - ini.y;
                if(Math.abs(dx) >= Math.abs(dy))
                {
                    if(dx > 0)
                    {
                        ini.x + ini.w / 2 + 2;
                    }else
                    {
                        ini.x - ini.w / 2 - 2;
                    }
                }else
                {
                    ini.x;
                }
            }else
            {
                var dx = modeler.mousex - ini.x;
                var dy = modeler.mousey - ini.y;
                if(Math.abs(dx) >= Math.abs(dy))
                {
                    if(dx > 0)
                    {
                        ini.x + ini.w / 2 + 2;
                    }else
                    {
                        ini.x - ini.w / 2 - 2;
                    }
                }else
                {
                    ini.x;
                }
            }
        }else
        {
            var dx = end.x - modeler.mousex;
            var dy = end.y - modeler.mousey;
            if(Math.abs(dx) >= Math.abs(dy))
            {
                if(dx > 0)
                {
                    modeler.mousex + 10 / 2 + 2;
                }else
                {
                    modeler.mousex - 10 / 2 - 2;
                }
            }else
            {
                modeler.mousex;
            }
        }
    }

    /**Calcula la coordenada en Y del punto inicial del objeto de conexión*/
    protected function getStartConnectionY(ini: GraphicalElement, end: GraphicalElement): Number
    {
        var lnode = Point {x: end.x y: end.y}

        if (not handles.isEmpty()) {
            var han = handles[0];
            lnode = Point {x: han.x y: han.y}
        }

        if(ini != null)
        {
            if(end != null)
            {
                var dx = lnode.x - ini.x;
                var dy = lnode.y - ini.y;
                if(Math.abs(dy) > Math.abs(dx))
                {
                    if(dy > 0)
                    {
                        ini.y + ini.h / 2 + 2;
                    }else
                    {
                        ini.y - ini.h / 2 - 2;
                    }
                }else
                {
                    ini.y;
                }
            }else
            {
                var dx = modeler.mousex - ini.x;
                var dy = modeler.mousey - ini.y;
                if(Math.abs(dy) > Math.abs(dx))
                {
                    if(dy > 0)
                    {
                        ini.y + ini.h / 2 + 2;
                    }else
                    {
                        ini.y - ini.h / 2 - 2;
                    }
                }else
                {
                    ini.y;
                }
            }
        }else
        {
            var dx = end.x - modeler.mousex;
            var dy = end.y - modeler.mousey;
            if(Math.abs(dy) > Math.abs(dx))
            {
                if(dy > 0)
                {
                    modeler.mousey + 10 / 2 + 2;
                }else
                {
                    modeler.mousey - 10 / 2 - 2;
                }
            }else
            {
                modeler.mousey;
            }
        }
    }

    /**Calcula la coordenada en X del punto final del objeto de conexión*/
    protected function getEndConnectionX(ini: GraphicalElement, end: GraphicalElement): Number
    {
        var lnode = Point {x: end.x y: end.y}

        if (not handles.isEmpty()) {
            var han = handles[handles.size()-1];
            lnode = Point {x: han.x y: han.y}
        }

        if(ini != null)
        {
            if(end != null)
            {
                var dx = lnode.x - ini.x;
                var dy = lnode.y - ini.y;
                if(Math.abs(dx) >= Math.abs(dy))
                {
                    if(dx > 0)
                    {
                        ini.x + ini.w / 2 + 2;
                    }else
                    {
                        ini.x - ini.w / 2 - 2;
                    }
                }else
                {
                    ini.x;
                }
            }else
            {
                var dx = modeler.mousex - ini.x;
                var dy = modeler.mousey - ini.y;
                if(Math.abs(dx) >= Math.abs(dy))
                {
                    if(dx > 0)
                    {
                        ini.x + ini.w / 2 + 2;
                    }else
                    {
                        ini.x - ini.w / 2 - 2;
                    }
                }else
                {
                    ini.x;
                }
            }
        }else
        {
            var dx = end.x - modeler.mousex;
            var dy = end.y - modeler.mousey;
            if(Math.abs(dx) >= Math.abs(dy))
            {
                if(dx > 0)
                {
                    modeler.mousex + 10 / 2 + 2;
                }else
                {
                    modeler.mousex - 10 / 2 - 2;
                }
            }else
            {
                modeler.mousex;
            }
        }
    }

    /**Calcula la coordenada en Y del punto final del objeto de conexión*/
    protected function getEndConnectionY(ini: GraphicalElement, end: GraphicalElement): Number
    {
        var lnode = Point {x: end.x y: end.y}

        if (not handles.isEmpty()) {
            var han = handles[handles.size()-1];
            lnode = Point {x: han.x y: han.y}
        }

        if(ini != null)
        {
            if(end != null)
            {
                var dx = lnode.x - ini.x;
                var dy = lnode.y - ini.y;
                if(Math.abs(dy) > Math.abs(dx))
                {
                    if(dy > 0)
                    {
                        ini.y + ini.h / 2 + 2;
                    }else
                    {
                        ini.y - ini.h / 2 - 2;
                    }
                }else
                {
                    ini.y;
                }
            }else
            {
                var dx = modeler.mousex - ini.x;
                var dy = modeler.mousey - ini.y;
                if(Math.abs(dy) > Math.abs(dx))
                {
                    if(dy > 0)
                    {
                        ini.y + ini.h / 2 + 2;
                    }else
                    {
                        ini.y - ini.h / 2 - 2;
                    }
                }else
                {
                    ini.y;
                }
            }
        }else
        {
            var dx = end.x - modeler.mousex;
            var dy = end.y - modeler.mousey;
            if(Math.abs(dy) > Math.abs(dx))
            {
                if(dy > 0)
                {
                    modeler.mousey + 10 / 2 + 2;
                }else
                {
                    modeler.mousey - 10 / 2 - 2;
                }
            }else
            {
                modeler.mousey;
            }
        }
    }

    /**Calcula la coordenada en X del primer tirador intermedio del objeto de conexión*/
    function getInter1ConnectionX(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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

    /**Calcula la coordenada en Y del primer tirador intermedio del objeto de conexión*/
    function getInter1ConnectionY(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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

    /**Calcula la coordenada en X del segundo tirador intermedio del objeto de conexión*/
    function getInter2ConnectionX(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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

    /**Calcula la coordenada en Y del segundo tirador intermedio del objeto de conexión*/
    function getInter2ConnectionY(ini: GraphicalElement, end: GraphicalElement, pini: Point,pend: Point): Number
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

    /**Calcula el ángulo de la flecha del objeto de conexión*/
    protected bound function getArrow(grad: Number, p: Point) : Number
    {
        if(pend.x >= p.x)
        {
            Math.PI-Math.atan((pend.y-p.y)/(pend.x-p.x))+(grad*Math.PI)/180;
        }else
        {
            2*Math.PI-Math.atan((pend.y-p.y)/(pend.x-p.x))+(grad*Math.PI)/180;
        }
    }

    /**Indica si el objeto de conexión es visible en el área de trabajo*/
    public bound function canView():Boolean
    {
        return modeler.containerElement==ini.container or modeler.containerElement==end.container;
    }

    /**Hace una copia del objeto de conexión.*/
    public function copy() : ConnectionObject {
        var t = ConnectionObject {
            ini: this.ini
            end: this.end
            arrowType: this.arrowType
            //cubicCurve: this.cubicCurve
            notGroup: this.notGroup
            modeler: this.modeler
            title:this.title
            uri:"new:sequenceflow:{modeler.toolBar.counter++}"
        }

        for (handle in handles) {
            t.addLineHandler(handle.getPoint(), false);
        }
        return t;
    }

    /**Obtiene el URI del objeto de conexión*/
    public function getURI() : String {
        return uri;
    }

    public function getTitle() : String {
        return title;
    }

    public function getStartPoint() : Point {
        return pini;
    }

    public function getEndPoint() : Point {
        return pend;
    }

    public function getPoints() : Point [] {
        var ret: Point[];
        insert pini into ret;
        for (ele in handles) {
            insert (ele as LineHandle).getPoint() into ret;
        }
        insert pend into ret;
        return ret;
    }

}