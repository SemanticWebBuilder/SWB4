/*
 * Modeler.fx
 *
 * Created on 26/02/2010, 12:34:46 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.ClipView;
import javafx.scene.input.MouseEvent;

/**
 * @author victor.lorenzana
 */

public class Modeler extends CustomNode
{
    public var width:Number;
    public var height:Number;
    public var contents: Node[];
    public var pannable: Boolean;
    public var disablePannable: Boolean=false;
    public var tempNode: Node;                          //Nodo temporal por toolbar
    public var focusedNode: Node;                       //Nodo con el foco
    public var clickedNode: Node;
    public var overNode: GraphElement;
    public var mousex:Number;
    public var mousey:Number;
    public var clipView:ClipView;
    //public var overPool: Pool;                          //Nodo temporal por toolbar

    public override function create(): Node
    {
         clipView=ClipView
         //var ret=ScrollPane
         {
             node:Group
             //content:Group
             {
                 content: bind contents
             }
             width:bind width
             height:bind height
             pannable: bind pannable and not disablePannable
             //translateX:40;
             onMousePressed: function( e: MouseEvent ):Void
             {
                //println("onMousePressed modeler:{e}");
                mousex=e.x+clipView.clipX;
                mousey=e.y+clipView.clipY;
                if(tempNode!=null)
                {
                    var close: Boolean=true;

                    if(tempNode instanceof GraphElement)
                    {
                        add(tempNode);
                        var a=tempNode as GraphElement;
                        a.x=e.x+clipView.clipX;
                        a.y=e.y+clipView.clipY;
                        a.snapToGrid();
                    }else if(tempNode instanceof ConnectionObject)
                    {
                        var a=tempNode as ConnectionObject;
                        if(clickedNode instanceof GraphElement)
                        {
                            var ge=clickedNode as GraphElement;
                            if(ge.canIniLink(a) and ge instanceof FlowObject)
                            {
                                a.ini=ge as FlowObject;
                                add(tempNode);
                            }
                            close=false;
                            clickedNode=null;
                        }
                    }

                    if(close)
                    {
                        tempNode=null;
                        disablePannable=false;
                        //println(e);
                        //println(tempNode);
                    }
                }
             }
             onMouseDragged: function( e: MouseEvent ):Void
             {
                //println("onMouseDragged modeler:{e}");
                if(tempNode!=null)
                {
                    mousex=e.x+clipView.clipX;
                    mousey=e.y+clipView.clipY;
                    if(tempNode instanceof ConnectionObject)
                    {
                        var co: ConnectionObject=tempNode as ConnectionObject;
                        if(overNode!=null and overNode instanceof FlowObject)
                        {
                            if(overNode.canEndLink(co))
                            {
                               co.end=overNode as FlowObject;
                            }
                        }else
                        {
                            var fo : FlowObject=co.ini;                            
                            co.end=null;
                        }
                    }
                }
                else if(clickedNode instanceof ConnectionObject)
                {
                    tempNode=clickedNode;
                    var co: ConnectionObject=tempNode as ConnectionObject;                                        
                    co.end=null;
                }
             }
             onMouseReleased: function( e: MouseEvent ):Void
             {
                 //println("onMouseReleased modeler:{e}");
                 if(tempNode!=null)
                 {
                     if(tempNode instanceof ConnectionObject)
                     {
                         var con=tempNode as ConnectionObject;
                         if(con.end==null)
                         {                              
                            for(cp in con.ini.connectionPoints)
                            {
                                if(cp.connectionObject!=null and cp.connectionObject.uri.equals(con.uri))
                                {
                                    println("Removing {cp.connectionObject.uri}");
                                    cp.connectionObject=null;
                                    println("cp.connectionObject {cp.connectionObject}");
                                }
                            }
                            println("con.ini.getAvailablePoints() {con.ini.getAvailablePoints()}");
                            remove(tempNode);
                            
                            
                            
                         }
                         tempNode=null;
                         disablePannable=false;
                     }
                 }
             }
//             onKeyTyped: function( e: KeyEvent ):Void
//             {
//                 println(e);
//             }
         };
         return clipView;
    }

    public function load(home: String)
    {
        /*
        var t1= Task {
            x : 50, y : 100
            title : "Tarea 1"
            uri : "task1"
            //cursor:Cursor.CROSSHAIR;
        };

        var t2= SubProcess {
            x : 100, y : 100
            title : "Javier Solis Gonzalez"
            uri : "task2"
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

        var p1= Pool {
            x : 400, y : 300
            title : "Pool"
            uri : "pool"
        };
        add(p1);

        add(ANDGateWay {
            x : 300, y : 100
            uri : "gateway1"
        });

        t1.pool=p1;


        add(t1);
        add(t2);
        add(se);
        add(ee);
        */
        //addRelation("home","padre1","Hijo","Padre");
    }

    public function organizeMap()
    {
    }

//    public function addRelation(tpuri1:String, tpuri2:String, tpr1:String, tpr2:String)
//    {
//    }

    public function add(obj:Node)
    {
        insert obj into contents;
    }

    public function addFirst(obj:Node)
    {
        insert obj before contents[0];
    }

    public function remove(obj:Node)
    {
        delete obj from contents;
    }

    public function getGraphElementByURI(uri:String):GraphElement
    {
        for(node in contents)
        {
            if(node instanceof GraphElement)
            {
                var ge=node as GraphElement;
                if(ge.uri.equals(uri))return ge;
            }
        }
        return null;
    }

    public function hasAnElementWith(uri:String):Boolean
    {
        for(node in contents)
        {
            if(node instanceof GraphElement)
            {
                var ge=node as GraphElement;
                if(ge.uri.startsWith(uri))return true;
            }
        }
        return false;
    }

}
