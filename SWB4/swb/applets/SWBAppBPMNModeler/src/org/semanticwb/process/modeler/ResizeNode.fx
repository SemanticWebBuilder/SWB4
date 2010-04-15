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

public class ResizeNode extends CustomNode
{
    public var attachedNode:GraphElement;
    public var modeler:Modeler;

    override protected function create () : Node
    {
        var c1=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:1
        };
        var c2=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:2
        };
        var c3=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:3
        };
        var c4=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:4
        };
        var c5=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:5
        };
        var c6=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:6
        };
        var c7=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:7
        };
        var c8=ResizePoint{
            attachedNode:bind attachedNode
            modeler:bind modeler
            type:8
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
