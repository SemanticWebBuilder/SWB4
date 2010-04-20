/*
 * ModelerUtils.fx
 *
 * Created on 10/03/2010, 01:00:38 AM
 */

package org.semanticwb.process.modeler;

import javafx.reflect.*;
import javafx.scene.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.awt.image.BufferedImage;

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

public function renderToImage(node:Node, minx:Integer, miny:Integer, width:Integer, height:Integer) : BufferedImage
{
    var context = FXLocal.getContext();
    var nodeClass = context.findClass("javafx.scene.Node");
    var getFXNode = nodeClass.getFunction("impl_getPGNode");
    var sgNode = (getFXNode.invoke(context.mirrorOf(node)) as FXLocal.ObjectValue).asObject();
    var g2dClass = (context.findClass("java.awt.Graphics2D") as FXLocal.ClassType).getJavaImplementationClass();
    var boundsClass=(context.findClass("com.sun.javafx.geom.Bounds2D") as FXLocal.ClassType).getJavaImplementationClass();
    var affineClass=(context.findClass("com.sun.javafx.geom.AffineTransform") as FXLocal.ClassType).getJavaImplementationClass();

    // getContentBounds() method have different signature in JavaFX 1.2
    var getBounds = sgNode.getClass().getMethod("getContentBounds",boundsClass,affineClass);
    var bounds = getBounds.invoke(sgNode, new com.sun.javafx.geom.Bounds2D(), new com.sun.javafx.geom.AffineTransform()) as com.sun.javafx.geom.Bounds2D;

    // Same with render() method
    var paintMethod = sgNode.getClass().getMethod("render", g2dClass, boundsClass, affineClass);
    var img = new java.awt.image.BufferedImage(width+minx, height+miny, java.awt.image.BufferedImage.TYPE_INT_ARGB);
    var g2 =img.createGraphics();
    paintMethod.invoke(sgNode,g2, bounds, new com.sun.javafx.geom.AffineTransform());
    g2.dispose();

    var img2 = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
    var g =img2.createGraphics();
    g.drawImage(img, -minx, -miny, null);
    g.dispose();
  
    return img2;
}


public class ModelerUtils {

}

