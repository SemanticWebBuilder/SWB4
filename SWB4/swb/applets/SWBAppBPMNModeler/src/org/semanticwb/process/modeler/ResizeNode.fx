/*
 * ResizeNode.fx
 *
 * Created on 30/03/2010, 06:42:33 PM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;

/**
 * @author javier.solis
 */

public-read var RESIZE_A=1;                 //All
public-read var RESIZE_V=2;                 //Vertical
public-read var RESIZE_H=3;                 //Horizontal

public class ResizeNode extends CustomNode
{
    public var attachedNode:GraphicalElement;
    public var modeler:Modeler;

    override protected function create () : Node
    {
        var c1=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:1
            visible:bind attachedNode.resizeType==ResizeNode.RESIZE_A
        };
        var c2=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:2
            visible:bind attachedNode.resizeType!=ResizeNode.RESIZE_H
        };
        var c3=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:3
            visible:bind attachedNode.resizeType==ResizeNode.RESIZE_A
        };
        var c4=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:4
            visible:bind attachedNode.resizeType!=ResizeNode.RESIZE_V
        };
        var c5=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:5
            visible:bind attachedNode.resizeType!=ResizeNode.RESIZE_V
        };
        var c6=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:6
            visible:bind attachedNode.resizeType==ResizeNode.RESIZE_A
        };
        var c7=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:7
            visible:bind attachedNode.resizeType!=ResizeNode.RESIZE_H
        };
        var c8=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:8
            visible:bind attachedNode.resizeType==ResizeNode.RESIZE_A
        };
        return Group{
            content: 
            [
                c1,c2,c3,c4,c5,c6,c7,c8
            ]
            visible:bind attachedNode.resizeable and attachedNode.canView()
        };
    }
}
