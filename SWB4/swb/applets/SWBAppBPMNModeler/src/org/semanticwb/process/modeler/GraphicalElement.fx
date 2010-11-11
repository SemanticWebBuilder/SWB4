/*
 * GraphicalElement.fx
 *
 * Created on 13/02/2010, 10:54:18 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import java.lang.Math;
import javafx.scene.input.KeyEvent;
import javafx.scene.Group;
import javafx.scene.CacheHint;
import org.semanticwb.process.modeler.ModelerUtils;
import org.semanticwb.process.modeler.ConnectionObject;
import javafx.util.Sequences;


/**
 * @author javier.solis
 */

public class GraphicalElement extends CustomNode
{
    public-read var over:Boolean;                       //el mause se encuentra sobre el elemento
    public-read var sceneX:Number;
    public-read var sceneY:Number;
//    var sx= bind x on replace
//    {
//        sceneX=localToScene(x, y).x;
//    }
//
//    var sy= bind y on replace
//    {
//        sceneY=localToScene(x, y).y;
//    }

    public var type:String;                     //tipo del elemento
    public var title : String;                  //titulo del elemento
    public var toolTipText : String;            //tooltip del elemento
    public var uri : String;                    //uri del elemento
    public var s : Number = 1;                     //size
    public var resizeable:Boolean=false;
    public var resizeType:Number=ResizeNode.RESIZE_A;
    public var modeler:Modeler;
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
    protected var menuOptions: Action[];

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

    public function onParentXChange()
    {
        if(graphParent!=null)x=px+dpx;
    }

    public function onParentYChange()
    {
        if(graphParent!=null)y=py+dpy;
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
            width: bind w
            height: bind h
        }
        minW = w;
        minH = h;

        setCommonMenuOptions();
    }

    public function setCommonMenuOptions () {
        var props:Action[] = [
            Action {
            label: ModelerUtils.getLocalizedString("actDelete");
            action: function (e: MouseEvent) {
                    remove(true);
                }
            }//,
//            Action{isSeparator: true},
//            Action {
//            label: ModelerUtils.getLocalizedString("actEdit");
//            action: function (e: MouseEvent) {
//                    println("Editando propiedades de {this.title}");
//                }
//            }
        ];

        insert props into menuOptions;
    }

    public override function create(): Node
    {
        initializeCustomNode();
        return Group
        {
            content: [
                text
            ]
            visible: bind canView()
        }
    }

    override var onMouseClicked = function ( e: MouseEvent ) : Void
    {
        if(modeler.getFocusedNode()==this)
        {
            mouseClicked(e);
        }
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
    }

    public function mousePressed( e: MouseEvent )
    {
        modeler.setFocusedNode(this);
        modeler.disablePannable=true;
        dx=x-e.sceneX;
        dy=y-e.sceneY;
        requestFocus();

        if(e.secondaryButtonDown)
        {
            var link=SequenceFlow
            {
                modeler:modeler
                uri:"new:flowlink:{ToolBar.counter++}"
                visible:false
            }
            if(canStartLink(link))modeler.tempNode=link;
        }
    }


    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(ModelerUtils.clickedNode==this)
        {
            ModelerUtils.clickedNode=null;
            mouseReleased(e);
        }
    }

    public function mouseReleased( e: MouseEvent )
    {
        shape.requestFocus();
        snapToGrid();

        //check drop over node
        var overNode:GraphicalElement;
        for(node in modeler.contents)
        {
            if(node instanceof GraphicalElement)
            {
                if(node != this and (node as GraphicalElement).over)
                {
                    if(canAttach(node as GraphicalElement))
                    {
                        overNode=node as GraphicalElement;
                        if(not(this instanceof Lane) and overNode instanceof Pool)  //check lanes in pool
                        {
                            var p=overNode as Pool;
                            for(lane in p.lanes)
                            {
                                if(lane.over)
                                {
                                    overNode=lane;
                                    //break;
                                }
                            }
                        }
                        //break;
                    }
                }
            }
        }
        setGraphParent(overNode);
    }

    public function getGraphParent() : GraphicalElement
    {
        return graphParent;
    }

    public function setGraphParent(parent:GraphicalElement):Void
    {
        if(parent!=null)
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
                ModelerUtils.startToolTip("{toolTipText}", x - w / 2 - modeler.getXScroll(), y + h / 2 - modeler.getYScroll() + 3);
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
        if(e.code==e.code.VK_DELETE)
        {
            remove(true);
        }
        if (e.code == e.code.VK_RIGHT and not (this instanceof Lane)) {
            ModelerUtils.setResizeNode(null);
            this.x += 10;
            ModelerUtils.setResizeNode(this);
        } else if (e.code == e.code.VK_LEFT and not (this instanceof Lane)) {
            var sp = this.x - this.w / 2;
            if (sp - 10 > this.sceneX) {
                ModelerUtils.setResizeNode(null);
                this.x -= 10;
                ModelerUtils.setResizeNode(this);
            }
        } else if (e.code == e.code.VK_UP and not (this instanceof Lane)) {
            var sp = this.y - this.h / 2;
            if (sp - 10 > this.sceneY) {
                ModelerUtils.setResizeNode(null);
                this.y -= 10;
                ModelerUtils.setResizeNode(this);
            }
        } else if (e.code == e.code.VK_DOWN and not (this instanceof Lane)) {
            ModelerUtils.setResizeNode(null);
            this.y += 10;
            ModelerUtils.setResizeNode(this);
        }
    }

    override var onKeyReleased = function( e: KeyEvent )
    {
        keyReleased(e);
        modeler.keyReleased(e);
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
}