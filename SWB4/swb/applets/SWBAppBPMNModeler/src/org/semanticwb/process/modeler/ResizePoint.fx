/*
 * ResizeNode.fx
 *
 * Created on 30/03/2010, 06:42:33 PM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.util.Math;

/**
 * @author javier.solis
 */

public class ResizePoint extends CustomNode
{
    public var attachedNode:GraphicalElement;
    public var modeler:Modeler;
    public var type:Number;

    var ox:Number;
    var oy:Number;

    override protected function create () : Node
    {
        var cursor:Cursor;
        var ix=0.0;
        var iy=0.0;
        if(type==1)
        {
            cursor=Cursor.NW_RESIZE;
            ix=-0.5;
            iy=-0.5;
        }else if(type==2)
        {
            cursor=Cursor.V_RESIZE;
            ix=0;
            iy=-0.5;
        }else if(type==3)
        {
            cursor=Cursor.NE_RESIZE;
            ix=0.5;
            iy=-0.5;
        }else if(type==4)
        {
            cursor=Cursor.H_RESIZE;
            ix=-0.5;
            iy=0;
        }else if(type==5)
        {
            cursor=Cursor.H_RESIZE;
            ix=0.5;
            iy=0;
        }else if(type==6)
        {
            cursor=Cursor.NE_RESIZE;
            ix=-0.5;
            iy=0.5;
        }else if(type==7)
        {
            cursor=Cursor.V_RESIZE;
            ix=0;
            iy=0.5;
        }else if(type==8)
        {
            cursor=Cursor.NW_RESIZE;
            ix=0.5;
            iy=0.5;
        }

        var c=Circle{
            centerX:bind attachedNode.x+(ix*attachedNode.w)//-modeler.getXScroll()
            centerY:bind attachedNode.y+(iy*attachedNode.h)//-modeler.getYScroll()
            radius:5
            cursor:cursor
            styleClass: "resizepoint"
            onMousePressed:function( e: MouseEvent ):Void
            {
                if(ModelerUtils.clickedNode==null)
                {
                    modeler.disablePannable=true;
                    ModelerUtils.clickedNode=this;
                    ox=attachedNode.x-attachedNode.w*ix;
                    oy=attachedNode.y-attachedNode.h*iy;
                    attachedNode.resizing = true;
                }
            }
            onMouseReleased:function( e: MouseEvent ):Void
            {
                if(ModelerUtils.clickedNode==this)
                {
                    ModelerUtils.clickedNode=null;
                    attachedNode.resizing = false;
                    attachedNode.snapToGrid();
                }
            }
            onMouseDragged:function( e: MouseEvent ):Void
            {
                if(ModelerUtils.clickedNode==this)
                {
                    if(ix!=0)
                    {
                        if (Math.abs(ox-(e.sceneX)) > attachedNode.minW) {
                            attachedNode.w=Math.abs(ox-(e.sceneX));
                            attachedNode.x=ox+attachedNode.w*ix;
                        }
                    }
                    if(iy!=0)
                    {
                        if (Math.abs(oy-(e.sceneY)) > attachedNode.minH) {
                            attachedNode.h=Math.abs(oy-(e.sceneY));
                            if(attachedNode instanceof Lane)
                            {
                                //attachedNode.y=attachedNode.getGraphParent().y-attachedNode.getGraphParent().h/2+attachedNode.boundsInParent.minY+attachedNode.h/2;
                            }else
                            {
                                attachedNode.y=oy+attachedNode.h*iy;
                            }
                        }
                    }
                }
            }
        };
        return c;
    }

}
