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
import javafx.scene.input.MouseEvent;
import javafx.util.StringLocalizer;

/**
 * @author javier.solis
 */

public var clickedNode: Node;
var localizer: StringLocalizer;

var toolTip=ToolTip
{
    visible:false
}

var errorMessage:String;

public function setErrorMessage(message:String)
{
    errorMessage=message;
    startToolTip(message, 0, getToolTip().scene.height-20);
    //startToolTip(message, getToolTip().scene.width/2, getToolTip().scene.height/2);
}

public function getLocalizedString(key: String): String {
    localizer.key = key;
    return localizer.localizedString;
}

public function setLocalizer (loc: StringLocalizer) : Void {
    localizer = loc;
}

public var splash=Splash{ };

// Primer menu flotante
public var popup=MenuPopup{
    corner:20
    padding:8
    borderWidth:4
    //borderColor:bind dynamicColor
    opacity: 0.9
    animate:true
    // opciones del menu
    content: [
        MenuItem { text:"Say Hello!", call: function(e:MouseEvent):Void{println("Hello");} },
        MenuItem { text:"Again, say Hello!", call:function(e:MouseEvent):Void{println("Hello 2");} },
        MenuSeparator { },
        MenuItem { text:"Say Bye!", call: function(e:MouseEvent):Void{println("Bye");} }
    ]
};


var resize=ResizeNode{};

public var version="0.1.0.0";

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
    }else if(node instanceof GraphicalElement)
    {
        resize.attachedNode=node as GraphicalElement;
    }else
    {
        resize.attachedNode=null;
    }

}

public function renderToImage(node:Node, minx:Integer, miny:Integer, width:Integer, height:Integer) : BufferedImage
{
//    var _bounds=new com.sun.javafx.geom.Bounds2D();
//    var _affine=new com.sun.javafx.geom.transform.Affine2D();
//    var bounds=node.impl_getPGNode().getContentBounds(_bounds, _affine);

    var context = FXLocal.getContext();
    var nodeClass = context.findClass("javafx.scene.Node");
    var getFXNode = nodeClass.getFunction("impl_getPGNode");
    var sgNode = (getFXNode.invoke(context.mirrorOf(node)) as FXLocal.ObjectValue).asObject();
    var g2dClass = (context.findClass("java.awt.Graphics2D") as FXLocal.ClassType).getJavaImplementationClass();
    var boundsClass=(context.findClass("com.sun.javafx.geom.Bounds2D") as FXLocal.ClassType).getJavaImplementationClass();
    var affineClass=(context.findClass("com.sun.javafx.geom.transform.Affine2D") as FXLocal.ClassType).getJavaImplementationClass();

    // getContentBounds() method have different signature in JavaFX 1.2
    var getBounds = sgNode.getClass().getMethod("getContentBounds",boundsClass,affineClass);
    var bounds = getBounds.invoke(sgNode, new com.sun.javafx.geom.Bounds2D(), new com.sun.javafx.geom.transform.Affine2D()) as com.sun.javafx.geom.Bounds2D;

    // Same with render() method
    var paintMethod = sgNode.getClass().getMethod("render", g2dClass, boundsClass, affineClass);
    var img = new java.awt.image.BufferedImage(width+minx, height+miny, java.awt.image.BufferedImage.TYPE_INT_ARGB);
    var g2 =img.createGraphics();
    paintMethod.invoke(sgNode,g2, bounds, new com.sun.javafx.geom.transform.Affine2D());
    g2.dispose();

    var img2 = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
    var g =img2.createGraphics();
    g.drawImage(img, -minx, -miny, null);
    g.dispose();
  
    return img2;
}


public class ModelerUtils {

}

