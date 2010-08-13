/*
 * GraphicalElement.fx
 *
 * Created on 13/02/2010, 10:54:18 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
    public var modeler:Modeler;
    public var x : Number;
    public var y : Number;
    public var w : Number;
    public var h : Number;

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

//    protected var tooltip: Tooltip;

    public var uri : String;                    //uri del elemento

    protected var shape : Shape;
    protected var text : EditableText;          //objeto para editar el titulo

    public var stroke=Color.web(Styles.color);
    public var strokeOver=Color.web(Styles.color_over);
    public var strokeFocused=Color.web(Styles.color_focused);

    public var s : Number = 1;                     //size
    public var stkw : Number = 2;                  //strokeWidth
    public var stkwo : Number = 3;                 //strokeWidth Over

    public var resizeable:Boolean=false;
    public var resizeType:Number=ResizeNode.RESIZE_A;

    var mx : Number;                               //temporal movimiento x
    var my : Number;                               //temporal movimiento y
    protected var dx : Number;                               //temporal drag x
    protected var dy : Number;                               //temporal drag y

    protected var zindex=0;

    protected var graphParent:GraphicalElement;
    protected var graphChilds:GraphicalElement[];

    protected var dpx : Number;                     //diference of parent
    protected var dpy : Number;                     //diference of parent

    protected var container:GraphicalElement;                 //Container Element
    protected var containerChilds:GraphicalElement[];         //Container Childs
    public var containerable:Boolean=false;               //can contains

    public-read var over:Boolean;                       //el mause se encuentra sobre el elemento

    public var useGrid:Boolean=true;                    //Se usa snap to grid

    var px = bind graphParent.x on replace
    {
        onParentXChange();
    }
    var py = bind graphParent.y on replace
    {
        onParentYChange();
    }

    var focusState = bind focused on replace
    {
        if (not focused)
        {
            shape.stroke=stroke;
        }
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

//        tooltip = Tooltip
//        {
//            text: bind toolTipText;
//        }
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
        //println("onMouseClicked node:{e}");
        if(e.button==e.button.SECONDARY)
        {
            //println("popup");
            ModelerUtils.popup.event=e;
        }else
        {
            if(e.clickCount >= 2)
            {
                //println("starEditing");
                if(containerable)
                {
                    if(text.boundsInLocal.contains(e.sceneX, e.sceneY))
                    {
                        text.startEditing();
                    }else
                    {
                        modeler.containerElement=this;
                    }
                }else
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
        //if(modeler.tempNode==null)
            modeler.disablePannable=true;
        dx=x-e.sceneX;
        dy=y-e.sceneY;
        //toFront();
        requestFocus();
        //println("onMousePress node:{e}");

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
        //if(modeler.tempNode==null)modeler.disablePannable=false;
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
        //println("onMouseRelease {overNode.title}");
    }

    public function getGraphParent() : GraphicalElement
    {
        return graphParent;
    }

    public function setGraphParent(parent:GraphicalElement):Void
    {
        //println("{this} setGraphParent {parent}");
        if(parent!=null)
        {
            dpx=x-parent.x;
            dpy=y-parent.y;

            delete this from graphParent.graphChilds;
            graphParent=parent;
            insert this into parent.graphChilds;
            //println("add {uri} parent:{parent.uri}");
            modeler.moveFront(this, parent);
        }else
        {
            delete this from graphParent.graphChilds;
            graphParent=null;
            //println("remove {uri} parent:{parent.uri}");
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
        //var name=getClass().getName();
        //println(name);
//        tooltip.activate();
        over=true;
        if (title == null or title.trim() == "") {
            ModelerUtils.startToolTip("{toolTipText}", x - w / 2 - modeler.getXScroll(), y + h / 2 - modeler.getYScroll() + 3);
        } else {
            ModelerUtils.startToolTip("{title}", x - w / 2 - modeler.getXScroll(), y + h / 2 - modeler.getYScroll() + 3);
        }
        mouseEntered(e);
    }

    public function mouseEntered( e: MouseEvent )
    {
        //println("x:{x} {localToScene(x,y).x}");
        modeler.overNode=this;
        shape.stroke=strokeOver;
        shape.strokeWidth=stkwo;
        //overtimer.playFromStart();
        if(modeler.tempNode==null)modeler.disablePannable=true;
    }

    override var onMouseExited = function(e)
    {
//        tooltip.deactivate();
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
        if(focused) shape.stroke=strokeFocused
        else shape.stroke=stroke;
        shape.strokeWidth=stkw;
    }


    override var onKeyPressed = function( e: KeyEvent )
    {
        keyPressed(e);
    }

    public function keyPressed( e: KeyEvent )
    {
        if(e.code==e.code.VK_DELETE)
        {
            remove(true);
        }
        //println(e);
    }

    public function remove(validate:Boolean) : Void
    {
        var del:Node[];
        //println("remove {this} {validate}");
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

