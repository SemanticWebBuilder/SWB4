/*
 * GraphicalElement.fx
 *
 * Created on 13/02/2010, 10:54:18 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import java.lang.Math;
import javafx.scene.Group;
import javafx.scene.CacheHint;
import org.semanticwb.process.modeler.ConnectionObject;
import javafx.util.Sequences;
import org.semanticwb.process.modeler.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.lang.Void;
import org.semanticwb.process.modeler.ModelerUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * Clase que representa un elemento gráfico en un diagrama BPMN 2.0. Es la
 * superclase de todos los nodos de flujo en el diagrama.
 * @author javier.solis
 */

public class GraphicalElement extends CustomNode
{
    public var over:Boolean;                       //el mouse se encuentra sobre el elemento
    public var selected: Boolean;
    public-read var sceneX:Number;
    public-read var sceneY:Number;
    public var type:String;                     //tipo del elemento
    public var title : String;                  //titulo del elemento
    public var description: String;
    public var toolTipText : String;            //tooltip del elemento
    public var uri : String;                    //uri del elemento
    public var s : Number = 1;                     //size
    public var resizeable:Boolean=false;
    public var resizeType:Number=ResizeNode.RESIZE_A;
    public var modeler:Modeler;
    public var isMultiInstance: Boolean = false;
    public var isSequentialMultiInstance: Boolean = false;
    public var isLoop: Boolean = false;
    public var isForCompensation: Boolean = false;
    public var isAdHoc: Boolean = false;
    public var isTransaction: Boolean = false;
    public var isInterrupting: Boolean = true;
    public var x : Number;
    public var y : Number;
    public var w : Number;
    public var h : Number;
    public var containerable:Boolean=false;               //can contains
    public var useGrid:Boolean=true;                    //Se usa snap to grid

    protected var shape : Shape;
    protected var text : EditableText;          //objeto para editar el titulo
    protected var dx : Number;                               //temporal drag x
    protected var dy : Number;                               //temporal drag y
    protected var zindex=0;
    protected var graphParent:GraphicalElement;
    protected var graphChilds:GraphicalElement[];
    protected var dpx : Number;                     //diference of parent
    protected var dpy : Number;                     //diference of parent
    protected var container:GraphicalElement;                 //Container Element
    protected var containerChilds:GraphicalElement[];         //Container Childs
    protected var menuOptions: MenuItem[];

    var mx : Number;                               //temporal movimiento x
    var my : Number;                               //temporal movimiento y
    public var minW: Number;
    public var minH: Number;
    public var resizing: Boolean = false;

    var px = bind graphParent.x on replace
    {
        onParentXChange();
    }
    var py = bind graphParent.y on replace
    {
        onParentYChange();
    }

    var tx = bind x on replace {
        for (con in getInputConnectionObjects()) {
            con.updatePoints();
        }
        for (con in getOutputConnectionObjects()) {
            con.updatePoints();
        }
    }
    var ty = bind y on replace {
        for (con in getInputConnectionObjects()) {
            con.updatePoints();
        }
        for (con in getOutputConnectionObjects()) {
            con.updatePoints();
        }
    }

    public function onParentXChange()
    {
       if(graphParent!=null and not graphParent.resizing)x=px+dpx;
       //println("Cambiando X");
    }

    public function onParentYChange()
    {
        if(graphParent!=null and not graphParent.resizing)y=py+dpy;
        //println("Cambiando Y");
    }

    protected function initializeCustomNode():Void
    {
        cache=true;
        cacheHint=CacheHint.SPEED;
        if(toolTipText==null)toolTipText=title;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y
            owner:this
            width: bind w
            height: bind h
        }
        minW = w;
        minH = h;

        setCommonMenuOptions();
    }

    public function setCommonMenuOptions () {
        var props:MenuItem[] = [
            MenuItem {
                caption: ##"actDelete";
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    remove(true);
                }
            },
            MenuItem {isSeparator:true},
            MenuItem {
                caption: ##"actChangeSize"
                items: [
                    MenuItem {
                        caption: ##"sizeSmall"
                        status: bind if(this.text.size == 8) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            this.setLabelSize(8);
                        }
                    },
                    MenuItem {
                        caption: ##"sizeNormal"
                        status: bind if(this.text.size == 10) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            this.setLabelSize(10);
                        }
                    },
                    MenuItem {
                        caption: ##"sizeMedium"
                        status: bind if(this.text.size == 12) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            this.setLabelSize(12);
                        }
                    },
                    MenuItem {
                        caption: ##"sizeLarge"
                        status: bind if(this.text.size == 14) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            this.setLabelSize(14);
                        }
                    }
                ]
            },
            MenuItem {isSeparator: true},
            MenuItem {
                caption: ##"actProps"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    ModelerUtils.setDialogNode(this);
                    ModelerUtils.dialog.show();
                }
            }
        ];
        insert props into menuOptions;
    }

    public override function create(): Node
    {
        blocksMouse = true;
        initializeCustomNode();
        return Group
        {
            scaleX: bind s
            scaleY: bind s
            content: [
                text
            ]
            visible: bind canView()
        }
    }

    override var onMouseMoved = function (e: MouseEvent) {
        modeler.mouseMoved(e);
    }

    override var onMouseClicked = function ( e: MouseEvent ) : Void
    {
        //println("Elemento {title} - [x: {x}, y:{y}, w:{getScaleWidth()}, h:{getScaleHeight()}]");
        if(modeler.getFocusedNode()==this)
        {
            mouseClicked(e);
        }
        modeler.mouseClicked(e);
    }

    public function mouseClicked( e: MouseEvent )
    {
        if(e.button==e.button.SECONDARY)
        {
            ModelerUtils.stopToolTip();
            ModelerUtils.popup.setOptions(menuOptions);
            ModelerUtils.popup.show(e);
        }else
        {
            if(e.clickCount >= 2)
            {
                if(text != null)
                {
                    text.startEditing();
                }
            }
        }
    }

    override var onMouseDragged = function ( e: MouseEvent ) : Void
    {
        if(ModelerUtils.clickedNode==this)
        {
            ModelerUtils.stopToolTip();
            mouseDragged(e);
        }
        modeler.mouseDragged(e);
    }

    public function mouseDragged( e: MouseEvent )
    {
        var ax=dx+e.sceneX;
        var ay=dy+e.sceneY;
        if(ax-w/2>0)x=ax else x=w/2;
        if(ay-h/2>0)y=ay else y=h/2;
    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
       if(ModelerUtils.clickedNode==null)
        {
            ModelerUtils.clickedNode=this;
            mousePressed(e);
        }
        modeler.mousePressed(e);
    }

    public function mousePressed( e: MouseEvent )
    {
        if (e.controlDown) {
            if (selected) {
                modeler.removeSelectedNode(this);
            } else {
                modeler.addSelectedNode(this);
                modeler.setFocusedNode(this);
                ModelerUtils.setResizeNode(null);
            }
        } else {
            if (not (modeler.tempNode instanceof ConnectionObject)) {
                modeler.unselectAll();
                modeler.addSelectedNode(this);
                modeler.setFocusedNode(this);
            }
        }        
        modeler.disablePannable=true;
        dx=x-e.sceneX;
        dy=y-e.sceneY;
        //requestFocus();

        if(e.secondaryButtonDown)
        {
            var link=SequenceFlow
            {
                modeler:modeler
                uri:"new:flowlink:{ToolBar.counter++}"
                visible:false
            }
            if(canStartLink(link))modeler.tempNode=link;
            ModelerUtils.setResizeNode(null);
            modeler.unselectAll();
        }
    }

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(ModelerUtils.clickedNode==this)
        {
            ModelerUtils.clickedNode=null;
            mouseReleased(e);
        }
        modeler.mouseReleased(e);
    }

    public function mouseReleased( e: MouseEvent )
    {
        if (this instanceof Pool or this instanceof Lane) return;
        snapToGrid();
        var overNode: GraphicalElement = getOverNode();
        setGraphParent(overNode);
    }

    public function getGraphParent() : GraphicalElement
    {
        return graphParent;
    }

    public function setGraphParent(parent:GraphicalElement):Void
    {
        if(parent!=null and not (parent.resizing))
        {
            dpx=x-parent.x;
            dpy=y-parent.y;

            delete this from graphParent.graphChilds;
            graphParent=parent;
            insert this into parent.graphChilds;
            modeler.moveFront(this, parent);
        }else
        {
            delete this from graphParent.graphChilds;
            graphParent=null;
        }
    }

    public function getContainer():GraphicalElement
    {
        return container;
    }

    public function setContainer(contain:GraphicalElement)
    {
        if(contain!=null)
        {
            delete this from container.graphChilds;
            container=contain;
            insert this into contain.containerChilds;
        }else
        {
            delete this from container.graphChilds;
            container=null;
        }
    }

    public function getContainerChilds():GraphicalElement[]
    {
        return containerChilds;
    }

    public function getgraphChilds():GraphicalElement[]
    {
        return graphChilds;
    }

    public function snapToGrid()
    {
        if(useGrid)
        {
            x=(Math.round(x/25))*25;            //grid
            y=(Math.round(y/25))*25;            //grid
        }
    }

    override var onMouseEntered = function(e)
    {
        over=true;
        if (not resizing) {
            if (title == null or title.trim() == "") {
                //ModelerUtils.startToolTip("{toolTipText}", x - w / 2 - modeler.getXScroll(), y + h / 2 - modeler.getYScroll() + 3);
            } else {
                ModelerUtils.startToolTip("{title}", x - w / 2 - modeler.getXScroll(), y + h / 2 - modeler.getYScroll() + 3);
            }
        }
        mouseEntered(e);
    }

    public function mouseEntered( e: MouseEvent )
    {
        modeler.overNode=this;
        if(modeler.tempNode==null)modeler.disablePannable=true;
    }

    override var onMouseExited = function(e)
    {
        over=false;
        ModelerUtils.stopToolTip();
        mouseExited(e);
    }

    public function mouseExited( e: MouseEvent )
    {
        if(modeler.overNode==this and ModelerUtils.clickedNode==null)
        {
            modeler.overNode=null;
            if(modeler.tempNode==null)modeler.disablePannable=false;
        }
    }

    override var onKeyPressed = function( e: KeyEvent )
    {
        keyPressed(e);
        modeler.keyPressed(e);
    }

    public function keyPressed( e: KeyEvent )
    {
//        if(e.code==e.code.VK_DELETE)
//        {
//            remove(true);
//        }
//        if (e.code == e.code.VK_RIGHT and not (this instanceof Lane)) {
//            ModelerUtils.setResizeNode(null);
//            this.x += 10;
//            ModelerUtils.setResizeNode(this);
//        } else if (e.code == e.code.VK_LEFT and not (this instanceof Lane)) {
//            var sp = this.x - this.w / 2;
//            if (sp - 10 > this.sceneX) {
//                ModelerUtils.setResizeNode(null);
//                this.x -= 10;
//                ModelerUtils.setResizeNode(this);
//            }
//        } else if (e.code == e.code.VK_UP and not (this instanceof Lane)) {
//            var sp = this.y - this.h / 2;
//            if (sp - 10 > this.sceneY) {
//                ModelerUtils.setResizeNode(null);
//                this.y -= 10;
//                ModelerUtils.setResizeNode(this);
//            }
//        } else if (e.code == e.code.VK_DOWN and not (this instanceof Lane)) {
//            ModelerUtils.setResizeNode(null);
//            this.y += 10;
//            ModelerUtils.setResizeNode(this);
//        }
        modeler.keyPressed(e);
    }

    override var onKeyReleased = function( e: KeyEvent )
    {
        keyReleased(e);
        modeler.keyReleased(e);
    }

    public function getOverNode() : GraphicalElement {
        var overNode: GraphicalElement = null;
        for (ele in modeler.contents where (ele instanceof GraphicalElement and ele != this)) {
            var t = ele as GraphicalElement;
            if (canAttach(ele as GraphicalElement)) {
                if (t instanceof Pool and (t as Pool).inBounds(this)) {
                    overNode = t;
                    for (lane in (t as Pool).lanes) {
                        if (lane.inBounds(this)) {
                            overNode = lane;
                        }
                    }
                } else {
                    if (t.inBounds(this)) {
                        overNode = t;
                    }
                }
            }
        }
        return overNode;
    }
    
    public function inBounds(node: Node) : Boolean {
        var ret = false;
        var wi = node.boundsInLocal.width;
        var he = node.boundsInLocal.height;
        if (node instanceof GraphicalElement) {
            wi = (node as GraphicalElement).w;
            he = (node as GraphicalElement).h;
        }

        var nx1 = node.boundsInLocal.minX;
        var ny1 = node.boundsInLocal.minY;
        var nx2 = node.boundsInLocal.minX + wi;
        var ny2 = node.boundsInLocal.minY + he;

        var ex1 = boundsInLocal.minX;
        var ey1 = boundsInLocal.minY;
        var ex2 = boundsInLocal.minX + w;
        var ey2 = boundsInLocal.minY + h;
        //println("  Revisando si {node.title} [{nx1}, {ny1}][{nx2}, {ny2}] está dentro del lane {lane.title} [{ex1}, {ey1}][{ex2}, {ey2}]");
        if (nx1 > ex1 and nx2 < ex2) {
            //println("  {node.title} dentro de {title} en X");
            if (ny1 > ey1 and ny2 < ey2) {
                //println("  {node.title} dentro de lane {title} en Y");
                ret = true;
            }
        }
        return ret;
    }

    public function keyReleased( e: KeyEvent )
    {
    }


    public function remove(validate:Boolean) : Void
    {
        var del:Node[];
        if (modeler.containerElement != null) {
            delete this from modeler.containerElement.containerChilds;
        }
        setGraphParent(null);
        setContainer(null);
        insert this into del;
        for(connection in modeler.contents where connection instanceof ConnectionObject)
        {
            var c=connection as ConnectionObject;
            if(c.end == this)insert c into del;
            if(c.ini == this)insert c into del;
        }

        var ch=Sequences.shuffle(graphChilds);
        for(child in ch)
        {
            (child as GraphicalElement).remove(false);
        }
        //delete graphChilds;

        for(node in del)
        {
            modeler.remove(node);
        }

        ModelerUtils.setResizeNode(null);
    }

    /**
    * Indica si puede o no establecer una coneccion del tipo "link"
    */
    public function canStartLink(link:ConnectionObject) : Boolean
    {
        return true;
    }

    /**
    * Indica si puede o no establecer una coneccion con el objeto inicial de la relacion
    */
    public function canEndLink(link:ConnectionObject) : Boolean
    {
        if(link.ini!=this)
        {
            return true;
        }else
        {
            return false;
        }
    }

    /**
    * Indica si un elemento gráfico puede agregarse o no al diagrama
    */
    public function canAddToDiagram(): Boolean {
        return true;
    }

    public function setType(type:String):Void
    {
        this.type=type;
    }

    public function canAttach(parent:GraphicalElement):Boolean
    {
        return false;
    }

    public bound function canView():Boolean
    {
        return modeler.containerElement==this.container;
    }

    public function updateSize()
    {

    }

    public function getInputConnectionObjects(): ConnectionObject[]
    {
        var ret:ConnectionObject[];
        for(ele in modeler.contents)
        {
            if(ele instanceof ConnectionObject)
            {
                var con= ele as ConnectionObject;
                if(con.end == this)
                {
                    insert con into ret;
                }
            }
        }
        return ret;
    }

    public function getOutputConnectionObjects(): ConnectionObject[]
    {
        var ret:ConnectionObject[];
        for(ele in modeler.contents)
        {
            if(ele instanceof ConnectionObject)
            {
                var con= ele as ConnectionObject;
                if(con.ini == this)
                {
                    insert con into ret;
                }
            }
        }
        return ret;
    }

    /**
    *  Regresa el primer padre si es un pool de lo contrario regresa null
    */
    public function getFirstGraphParent(): GraphicalElement
    {
        var ele=getGraphParent();
        if(ele==null)
        {
            return this;
        }
        else
        {
            return ele.getFirstGraphParent();
        }
    }

    /**
    *  Regresa el primer padre si es un pool de lo contrario regresa null
    */
    public function getPool(): Pool
    {
        var ele=getFirstGraphParent();
        if(ele instanceof Pool){
            return ele as Pool;
        } else {
            return null;
        }
    }

    public function setWidth(width: Number) : Void {
        if (width < minW) {
            minW = 100;
            w = minW;
        } else {
            w = width;
            minW = w;
        }
    }

    public function setHeight(height: Number) : Void {
        if (height < minH) {
            minH = 60;
            h = minH;
        } else {
            h = height;
            minH = h;
        }
    }
    
    public function copy() : GraphicalElement {
        return GraphicalElement {
            title: this.title
            type: this.type
            modeler: this.modeler
            isLoop: this.isLoop
            isForCompensation: this.isForCompensation
            isMultiInstance: this.isMultiInstance
            container: this.container
        }
    }
    
    public function cut () : GraphicalElement {
        var del:Node[];
        if (modeler.containerElement != null) {
            delete this from modeler.containerElement.containerChilds;
        }
        setGraphParent(null);
        setContainer(null);
        insert this into del;
        for(connection in modeler.contents where connection instanceof ConnectionObject) {
            var c=connection as ConnectionObject;
            if(c.end == this)insert c into del;
            if(c.ini == this)insert c into del;
        }

        for(node in del) {
            modeler.remove(node);
        }

        ModelerUtils.setResizeNode(null);
        return this;
    }

    public function setLabelSize(size: Number) : Void {
        if (this.text != null) {
            this.text.setSize(size);
        }
    }

    public function getGraphicsInfos(doc: Document) : Element {
        var ret = doc.createElement("NodeGraphicsInfos");
        var graphicInfo = doc.createElement("NodeGraphicsInfo");
        var coords = doc.createElement("Coordinates");
        
        graphicInfo.appendChild(coords);
        ret.appendChild(graphicInfo);

        graphicInfo.setAttribute("ToolId", "SemanticWebBuilder_Process_Modeler");
        graphicInfo.setAttribute("IsVisible", "{canView()}");
        graphicInfo.setAttribute("Width", "{w}");
        graphicInfo.setAttribute("Height", "{h}");

        if (getGraphParent() instanceof Lane) {
            graphicInfo.setAttribute("LaneId", "{getGraphParent().uri}");
        }

        coords.setAttribute("XCoordinate", "{x}");
        coords.setAttribute("YCoordinate", "{y}");
        return ret;
    }

    public function getXPDLDefinition(doc: Document) : Element {
        return null;
    }

    public function getScaleWidth() {
        return w * s;
    }

    public function getScaleHeight() {
        return h * s;
    }
}