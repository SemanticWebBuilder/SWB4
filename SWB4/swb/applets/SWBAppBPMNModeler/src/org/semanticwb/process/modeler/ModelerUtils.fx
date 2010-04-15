/*
 * ModelerUtils.fx
 *
 * Created on 10/03/2010, 01:00:38 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

/**
 * @author javier.solis
 */

public var clickedNode: Node;

var toolTip=ToolTip
{
    visible:false
}

var resize=ResizeNode{};

// Tooltips are displayed after a delay.
var toolTipTimeline= Timeline
{
    repeatCount: Timeline.INDEFINITE
    keyFrames :
        KeyFrame {
            time : 1000ms
            action: function()
            {
            // The tooltip needs to be displayed on top of
            // any other component.
                toolTip.visible = true;
            }
        }
};

public function getToolTip():ToolTip
{
    return toolTip;
}

public function getResizeNode():ResizeNode
{
    return resize;
}

public function startToolTip(text:String,x:Number,y:Number)
{
    toolTip.text=text;
    toolTip.x=x;
    toolTip.y=y;
    toolTipTimeline.play();
}

public function stopToolTip()
{
    if(toolTipTimeline.running)
    {
        toolTipTimeline.stop();
    }
    toolTip.visible=false;
    toolTip.layout();
}

public function setResizeNode(node:Node)
{
    if(node==null)
    {
        resize.attachedNode=null;
    }else if(node instanceof GraphElement)
    {
        resize.attachedNode=node as GraphElement;
    }
}

public class ModelerUtils {

}
