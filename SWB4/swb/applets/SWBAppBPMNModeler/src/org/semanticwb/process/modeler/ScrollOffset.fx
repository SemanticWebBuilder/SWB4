/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 * @author javier.solis
 */
public class ScrollOffset extends CustomNode
{
    public var x : Number;
    public var y : Number;

    public-read var sceneX:Number;
    public-read var sceneY:Number;

    var sx= bind x on replace
    {
        sceneX=localToScene(x, y).x;
    }

    var sy= bind y on replace
    {
        sceneY=localToScene(x, y).y;
    }

    public override function create(): Node
    {
        return Line{startX:0,startY:0,endX:0,endY:0};
    }

}
