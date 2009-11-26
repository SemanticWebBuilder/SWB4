/*
 * Main.fx
 *
 * Created on 15/11/2009, 02:46:27 AM
 */

package org.semanticwb.process.modeler;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.scene.text.TextOrigin;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import javafx.scene.effect.Lighting;
import javafx.scene.effect.light.DistantLight;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.lang.Math;
import javafx.scene.layout.ClipView;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Flow;
import javafx.scene.input.KeyEvent;


/**
 * @author javier.solis
 */

var stylesheets = "{__DIR__}style.css";
var style_task="fill: #FFFFFF; stroke: #6060FF; strokeWidth: 1; arcWidth: 10; arcHeight: 10;";
var style_task_text="font-size: 13px; font-family: \"Helvetica, Arial\"; fill: #6060FF; font-weight: bold;";
var style_event="fill: #FFFFFF; stroke: #6060FF; strokeWidth: 1;";
var style_connection="stroke: #6060FF; strokeWidth: 2;";
var style_toolbar="fill: #f0f0f0; stroke: #909090; strokeWidth: 1;";

var maxx : Number = bind scene.width on replace{ modeler.organizeMap();};
var maxy : Number = bind scene.height on replace{ modeler.organizeMap();};

var dropShadow = DropShadow {
    offsetX: 3
    offsetY: 3
    radius: 10.0
    color: Color.web("#707070")
}

var lighting = Lighting{
    surfaceScale: 5
    light: DistantLight{
        azimuth: 10
        elevation: 40
    }
}

var modeler:Modeler = Modeler
{
    layoutX:0
    layoutY:23
    width:bind maxx
    height:bind maxy-23
    pannable:true
    cursor:Cursor.CROSSHAIR
}

modeler.load("home");
modeler.organizeMap();

var scene : Scene = Scene {
    content: [modeler,ToolBar{}]
    width: 600
    height: 300
    stylesheets: bind stylesheets
    //fill: Color.TRANSPARENT
}

var stage : Stage = Stage {
    title: "BPMN Modeler"
    resizable: true
    scene: scene
    //style: StageStyle.TRANSPARENT //StageStyle.UNDECORATED
}
/******************************************************************************/
class ToolBar extends CustomNode
{
    public override function create(): Node
    {
         var ret=Group
         {
             content: [
                Rectangle{
                    width:bind maxx
                    height:23
                    style:style_toolbar
                },
                Flow {
                    height: 23
                    width:bind maxx
                    content: [
                        Button{
                            text:"Task"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=Task
                                {
                                    title:"Task"
                                    uri:"task"
                                }
                            }
                        },
                        Button{
                            text:"SubProcess"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=Task
                                {
                                    title:"SubProcess"
                                    uri:"subprocess"
                                }
                            }
                        },
                        Button{
                            text:"Start Event"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=StartEvent
                                {
                                    title:"Start Event"
                                    uri:"startevent"
                                }
                            }
                        },
                        Button{
                            text:"End Event"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=EndEvent
                                {
                                    title:"End Event"
                                    uri:"endevent"
                                }
                            }
                        },
                        Button{
                            text:"Inter Event"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=InterEvent
                                {
                                    title:"Inter Event"
                                    uri:"interevent"
                                }
                            }
                        },
                        Button{
                            text:"OR Gateway"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=Task
                                {
                                    title:"OR Gateway"
                                    uri:"orgateway"
                                }
                            }
                        },
                        Button{
                            text:"AND Gateway"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=Task
                                {
                                    title:"AND Gateway"
                                    uri:"andgateway"
                                }
                            }
                        }
                        Button{
                            text:"Flow Link"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=FlowLink
                                {
                                    uri:"flowlink"
                                }
                            }
                        }
                    ]
                }
             ]
         };
         return ret;
    }
}


/******************************************************************************/
class Modeler extends CustomNode
{
    public var width:Number;
    public var height:Number;
    public var contents: Node[];
    public var pannable: Boolean;
    public var disablePannable: Boolean=false;
    public var tempNode: Node;                          //Nodo temporal por toolbar
    public var focusedNode: Node;
    public var clickedNode: Node;

    public override function create(): Node
    {
         var ret=ClipView
         {
             node:Group
             {
                 content: bind contents
             }
             width:bind width
             height:bind height
             pannable: bind pannable and not disablePannable
             onMousePressed: function( e: MouseEvent ):Void
             {
                if(tempNode!=null)
                {
                    if(tempNode instanceof FlowObject)
                    {
                        add(tempNode);
                        var a=tempNode as FlowObject;
                        a.x=e.x-this.clip.translateX;
                        a.y=e.y-clip.translateY;
                    }else if(tempNode instanceof ConnectionObject)
                    {
                        var a=tempNode as ConnectionObject;
                        if(modeler.clickedNode!=null and modeler.clickedNode instanceof FlowObject)
                        {
                            a.ini=clickedNode as FlowObject;
                            add(tempNode);
                        }
                    }
                    tempNode=null;
                }
                modeler.disablePannable=false;
                println(e);
             }
         };
         return ret;
    }

    public function load(home: String)
    {
        var t1= Task {
            x : 50, y : 100
            title : "Tarea 1"
            uri : "task1"
            //cursor:Cursor.CROSSHAIR;
        };

        var t2= Task {
            x : 100, y : 100
            title : "Javier Solis Gonzalez"
            uri : "task2"
        };

        var t3= Task {
            x : 150, y : 100
            title : "Tarea 3"
            uri : "task3"
        };

        var se= StartEvent {
            x : 200, y : 100
            title : "Inicio"
            uri : "ini1"
        };

        var ee= EndEvent {
            x : 250, y : 100
            title : "Inicio"
            uri : "end1"
        };

        var ie= InterEvent {
            x : 300, y : 100
            title : "Inicio"
            uri : "inter1"
        };

        add(FlowLink{
            ini: se
            end: t1
            title : "Prueba"
            uri : "co1"
        });
        add(FlowLink{
            ini: t1
            end: t2
            title : "Prueba"
            uri : "co2"
        });

        add(t1);
        add(t2);
        add(t3);
        add(se);
        add(ee);
        add(ie);
        
        //addRelation("home","padre1","Hijo","Padre");
    }

    public function organizeMap()
    {
    }

    public function addRelation(tpuri1:String, tpuri2:String, tpr1:String, tpr2:String)
    {
    }

//    public function getTopic(fouri:String): FlowObject
//    {
//        for(flowObject in objects)// where content instanceof Topic)
//        {
//            if(flowObject.uri==fouri)return flowObject;
//        }
//        return null;
//    }

    public function add(obj:Node)
    {
        insert obj into contents;
    }

}

/******************************************************************************/
class EndEvent extends Event
{
    public override function create(): Node
    {
         var ret=super.create();
         shape.styleClass="endEvent";
         stkw=4;
         stkwo=5;
         return ret;
    }
}

/******************************************************************************/
class InterEvent extends Event
{
    public override function create(): Node
    {
        cursor=Cursor.HAND;
        w=30;
        h=30;

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            //styleClass: "event"
            style: style_event
            smooth:true;
        };

        return Group
        {
            content: [
                shape,text,
                Circle
                {
                    centerX: bind x
                    centerY: bind y
                    radius: bind w/2-3
                    stroke: bind shape.stroke
                    //styleClass: "event"
                    style: style_event
                    smooth:true;
                }
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: dropShadow
        };
    }
}

/******************************************************************************/
class StartEvent extends Event
{

}

/******************************************************************************/
class Event extends FlowObject
{
    public override function create(): Node
    {
        cursor=Cursor.HAND;
        w=30;
        h=30;

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            //styleClass: "event"
            style: style_event
            smooth:true;
        };

        return Group
        {
            content: [
                shape,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: dropShadow
        };
    }
}

/******************************************************************************/
class Task extends FlowObject
{
    public override function create(): Node
    {
        cursor=Cursor.HAND;
        w=80;
        h=50;
        text= Text
        {
             content: bind title
             wrappingWidth: bind w
             style: style_task_text
             textOrigin: TextOrigin.TOP
             transforms: [
                 Translate{
                     x: bind x-(text.boundsInLocal.width)/2+2
                     y: bind y-(text.boundsInLocal.height)/2+2
                 }
             ]
             smooth:true;
        };

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: w
            height: h
            //effect: lighting
            //styleClass: "task"
            style: style_task
            smooth:true;
        };

        return Group
        {
            content: [
                shape,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: dropShadow
        };
    }

}

/******************************************************************************/
class FlowObject extends CustomNode
{
    public var x : Number;
    public var y : Number;
    public var w : Number;
    public var h : Number;

    public var title : String;
    public var uri : String;

    var shape : Shape;
    var text : Text;

    var mx : Number;                        //temporal movimiento x
    var my : Number;                        //temporal movimiento y
    var s : Number = 1;                     //remporal size
    var o : Number = 0.8;                   //opacity
    var dx : Number;                        //temporal drag x
    var dy : Number;                        //temporal drag y
    var stkw : Number = 1;                  //strokeWidth
    var stkwo : Number = 2;                 //strokeWidth Over

    var overtimer = Timeline {
            repeatCount: 1 //Timeline.INDEFINITE
            keyFrames : [
                KeyFrame {
                    time : .1s
                    canSkip : true
                    values : [
                        o => 1.0 tween Interpolator.EASEBOTH
                    ]
                }
/*
                KeyFrame {
                    time : .1s
                    canSkip: true
                    values : [
                        s => 1.1 tween Interpolator.EASEBOTH
                    ] // values
                } // KeyFrame
*/
            ]
    };

    var normaltimer = Timeline {
            repeatCount: 1 //Timeline.INDEFINITE
            keyFrames : [
                KeyFrame {
                    time : .2s
                    canSkip : true
                    values : [
                        o => 0.8 tween Interpolator.EASEOUT
                    ]
                }
/*
                KeyFrame {
                    time : .2s
                    canSkip: true
                    values : [
                        s => 1 tween Interpolator.EASEOUT
                    ] // values
                } // KeyFrame
*/
            ]
    };

    var movetimer:Timeline;

    public function move(ax: Number, ay: Number)
    {
        visible=true;
        if(mx==ax and my==ay)return;
        mx=ax;
        my=ay;

        if(movetimer!=null)movetimer.stop();
        movetimer = Timeline
        {
            repeatCount: 1 //Timeline.INDEFINITE
            keyFrames : [
                KeyFrame {
                    time : .5s
                    canSkip : true
                    values : [
                        x => mx tween Interpolator.EASEIN
                    ]
                }
                KeyFrame {
                    time : .5s
                    canSkip: true
                    values : [
                        y => my tween Interpolator.EASEOUT
                    ] // values
                } // KeyFrame
/*
                KeyFrame {
                    time : .5s
                    canSkip: true
                    values : [
                        s => 1 tween Interpolator.LINEAR
                    ] // values
                } // KeyFrame
*/
            ]
        };
        movetimer.play();
    }

    public override function create(): Node
    {
        text= Text
        {
             content: bind title
             wrappingWidth: bind w
             //effect: dropShadow
             styleClass: "task-text"
             textOrigin: TextOrigin.TOP
             transforms: [
                 Translate{
                     x: bind x-(text.boundsInLocal.width)/2+2
                     y: bind y-(text.boundsInLocal.height)/2+2
                 }
             ]
             smooth:true;
        };
        return text;
    }

    override var onMouseDragged = function ( e: MouseEvent ) : Void
    {
        if(modeler.clickedNode==this)
        {
            var ax=dx+e.sceneX;
            var ay=dy+e.sceneY;
            if(ax>0)x=ax else x=0;
            if(ay>0)y=ay else y=0;
        }
    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
        if(modeler.clickedNode==null)
        {
            modeler.clickedNode=this;
            modeler.focusedNode=this;
            modeler.disablePannable=true;
            dx=x-e.sceneX;
            dy=y-e.sceneY;
            //toFront();
        }
        println("onMousePress node");
    }

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(modeler.clickedNode==this)
        {
            modeler.clickedNode=null;
            modeler.disablePannable=false;
            x=(Math.round(x/25))*25;
            y=(Math.round(y/25))*25;
        }
        println("onMouseRelease node");
    }

    override var onMouseEntered = function(e)
    {
        shape.stroke=Color.web("#FF6060");
        shape.strokeWidth=stkwo;
        //overtimer.playFromStart();
    }

    override var onMouseExited = function(e)
    {
        shape.stroke=Color.web("#6060FF");
        shape.strokeWidth=stkw;
        //normaltimer.playFromStart();
    }

    override var onKeyPressed = function( e: KeyEvent )
    {
        println(e);
    }



}

/******************************************************************************/
class FlowLink extends ConnectionObject
{

}


/******************************************************************************/
class ConnectionObject extends CustomNode
{
    public var ini : FlowObject;
    public var end : FlowObject;

    public var title : String;
    public var uri : String;

    var shape : Shape;
    var text : Text;

    var o : Number = 0.8;                   //opacity

    public override function create(): Node
    {
        cursor=Cursor.HAND;
//        text= Text
//        {
//             content: bind title
//             styleClass: "task-text"
//             textOrigin: TextOrigin.TOP
//             transforms: [
//                 Translate{
//                     x: bind x-(text.boundsInLocal.width)/2+2
//                     y: bind y-(text.boundsInLocal.height)/2+2
//                 }
//             ]
//             smooth:true;
//        };

        var line= Line {
            startX: bind getConnectionX(ini,end);
            startY: bind getConnectionY(ini,end);
            endX: bind getConnectionX(end,ini);
            endY: bind getConnectionY(end,ini);
            styleClass: "connection"
            smooth:true;
        };
        shape= line;

        return Group
        {
            content: [
                shape,text,
                Line{
                    startX: bind line.endX;
                    startY: bind line.endY;
                    endX: bind line.endX+6*Math.cos(getArrow(line, -45));
                    endY: bind line.endY-6*Math.sin(getArrow(line, -45));
                    styleClass: "conn_arrow"
                    stroke: bind shape.stroke;
                    smooth:true;
                },
                Line{
                    startX: bind line.endX;
                    startY: bind line.endY;
                    endX: bind line.endX+6*Math.cos(getArrow(line, 45));
                    endY: bind line.endY-6*Math.sin(getArrow(line, 45));
                    styleClass: "conn_arrow"
                    stroke: bind shape.stroke;
                    smooth:true;
                }
            ]
            opacity: bind o;
            effect: dropShadow
        };
    }

    override var onMouseDragged = function ( e: MouseEvent ) : Void
    {
        if(modeler.clickedNode==this)
        {
//            x=dx+e.sceneX;
//            y=dy+e.sceneY;
        }
    }

    override var onMousePressed = function( e: MouseEvent ):Void
    {
        if(modeler.clickedNode==null)
        {
            modeler.clickedNode=this;
            modeler.focusedNode=this;
            modeler.disablePannable=true;
//            dx=x-e.sceneX;
//            dy=y-e.sceneY;
            //toFront();
        }
    }

    override var onMouseReleased = function( e: MouseEvent ):Void
    {
        if(modeler.clickedNode==this)
        {
            modeler.clickedNode=null;
            modeler.disablePannable=false;
            //modeler
        }
    }

    override var onMouseEntered = function(e)
    {
        shape.stroke=Color.web("#FF6060");
    }

    override var onMouseExited = function(e)
    {
        shape.stroke=Color.web("#6060FF");
    }

    bound function getConnectionX(ini: FlowObject, end: FlowObject): Number
    {
        var dx=end.x-ini.x;
        var dy=end.y-ini.y;
        if(Math.abs(dx)>Math.abs(dy))
        {
            if(dx>0)
            {
                ini.x+ini.w/2+2;
            }else
            {
                ini.x-ini.w/2-2;
            }
        }else
        {
            ini.x;
        }
    }

    bound function getConnectionY(ini: FlowObject, end: FlowObject): Number
    {
        var dx=end.x-ini.x;
        var dy=end.y-ini.y;
        if(Math.abs(dy)>Math.abs(dx))
        {
            if(dy>0)
            {
                ini.y+ini.h/2+2;
            }else
            {
                ini.y-ini.h/2-2;
            }
        }else
        {
            ini.y;
        }
    }

    bound function getArrow(line: Line, grad: Number) : Number
    {
        if(line.endX >= line.startX)
        {
            Math.PI-Math.atan((line.endY-line.startY)/(line.endX-line.startX))+(grad*Math.PI)/180;
        }else
        {
            2*Math.PI-Math.atan((line.endY-line.startY)/(line.endX-line.startX))+(grad*Math.PI)/180;
        }

    }




}