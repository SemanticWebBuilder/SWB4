/*
 * GraphElement.fx
 *
 * Created on 26/02/2010, 12:24:06 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.lang.Math;
import javafx.scene.input.KeyEvent;
import org.semanticwb.publishflow.ConnectionObject;

/**
 * @author victor.lorenzana
 */

public class GraphElement extends CustomNode
{
    public var tooltip:String;
    public var modeler:Modeler;
    public var x : Number;
    public var y : Number;
    public var w : Number;

    public var h : Number;

    public var title : String;
    public var uri : String;

    public var shape : Shape;
    public var text : EditableText;

    public var stroke=Color.web(Styles.color);
    public var strokeOver=Color.web(Styles.color_over);
    public var strokeFocused=Color.web(Styles.color_focused);

    public var s : Number = 1;                     //size
    public var o : Number = 0.8;               //opacity
    public var stkw : Number = 2;                  //strokeWidth
    public var stkwo : Number = 3;                 //strokeWidth Over

    var mx : Number;                        //temporal movimiento x
    var my : Number;                        //temporal movimiento y
    var dx : Number;                        //temporal drag x
    var dy : Number;                        //temporal drag y

    public var zindex=0;

    var focusState = bind focused on replace
    {
        if (not focused)
        {
            shape.stroke=stroke;
        }
    }
    

    public override function create(): Node
    {
        text=EditableText
        {
            text: bind title with inverse
            width: bind w;
            height: bind h;
        }
        this.onMouseMoved=function ( e: MouseEvent ) : Void
        {
            mouseMoved(e);
        }

        return text;
    }

    public function mouseMoved( e: MouseEvent )
    {            
        
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


    override var onMouseDragged = function ( e: MouseEvent ) : Void
    {
        if(ModelerUtils.clickedNode==this)
        {
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



    public function getSequence() : ConnectionObject
    {
        SequenceFlow
        {
            modeler:modeler
            uri:"new:flowlink:{ToolBar.counter++}"
        }
    }


    public function mousePressed( e: MouseEvent )
    {
        modeler.focusedNode=this;
        //if(modeler.tempNode==null)
            modeler.disablePannable=true;
        dx=x-e.sceneX;
        dy=y-e.sceneY;
        //toFront();
        requestFocus();
        //println("onMousePress node:{e}");

        if(e.secondaryButtonDown)
        {
            var link=getSequence();
            if(canIniLink(link))modeler.tempNode=link;
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
        //println("onMouseRelease node");
    }

    public function snapToGrid()
    {
        x=(Math.round(x/25))*25;            //grid
        y=(Math.round(y/25))*25;            //grid
    }

    override var onMouseEntered = function(e)
    {
        mouseEntered(e);
    }

    public function mouseEntered( e: MouseEvent )
    {
        modeler.overNode=this;
        shape.stroke=strokeOver;
        shape.strokeWidth=stkwo;
        if(modeler.tempNode==null)modeler.disablePannable=true;
    }


    override var onMouseExited = function(e)
    {        
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
        //normaltimer.playFromStart();
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
        //println(e);
    }

    public function remove()
    {
        modeler.remove(this);
        for(connection in modeler.contents where connection instanceof ConnectionObject)
        {
            var c=connection as ConnectionObject;
            if(c.end == this)c.remove();
            if(c.ini == this)c.remove();
        }
    }

    /**
    * Indica si puede o no establecer una coneccion del tipo "link"
    */
    public function canIniLink(link:ConnectionObject) : Boolean
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

}

