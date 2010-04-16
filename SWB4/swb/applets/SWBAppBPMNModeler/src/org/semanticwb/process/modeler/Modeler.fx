/*
 * Modeler.fx
 *
 * Created on 13/02/2010, 11:01:48 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.ClipView;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.GraphElement;
import org.semanticwb.process.modeler.ModelerUtils;

/**
 * @author javier.solis
 */

public class Modeler extends CustomNode
{
    public var width:Number;
    public var height:Number;
    public var contents: Node[];
    public var pannable: Boolean;
    public var disablePannable: Boolean=false;
    public var tempNode: Node;                          //Nodo temporal por toolbar
    public var overNode: GraphElement;
    public var mousex:Number;
    public var mousey:Number;
    public var clipView:ClipView;
    public var containerElement: GraphElement;

    var focusedNode: Node;                       //Nodo con el foco

    public override function create(): Node
    {
         //var ret=ScrollPane
         clipView=ClipView
         {
             node:Group
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
                        if(ModelerUtils.clickedNode==null) //elemento sobre modeler
                        {
                            if(tempNode instanceof Lane)
                            {
                                //no se permite crear lanes sin pool
                            }else
                            {
                                if(tempNode instanceof Pool)addFirst(tempNode) else add(tempNode);
                                var a=tempNode as GraphElement;
                                a.x=e.x+clipView.clipX;
                                a.y=e.y+clipView.clipY;
                                a.snapToGrid();
                                a.setContainer(containerElement);
                            }
                        }else if(ModelerUtils.clickedNode instanceof Pool or ModelerUtils.clickedNode instanceof Lane)
                        {
                            if(tempNode instanceof Pool) //se esta agregando un pool
                            {
                                //addFirst(tempNode)
                            }else if(tempNode instanceof Lane) //se esta agregando un lane
                            {
                                if(ModelerUtils.clickedNode instanceof Pool) //sobre un pool
                                {
                                    var p=ModelerUtils.clickedNode as Pool;
                                    p.addLane();
                                    //p.updateLanesCords();
                                }else  //sobre un lane
                                {
                                    //TODO: Falta lanes dentro de lanes
                                    var p=(ModelerUtils.clickedNode as Lane).getPool();
                                    p.addLane();
                                    //p.updateLanesCords();
                                }
                            }else
                            {
                                add(tempNode);
                            }
                            var a=tempNode as GraphElement;
                            a.x=e.x+clipView.clipX;
                            a.y=e.y+clipView.clipY;
                            a.snapToGrid();
                            if(a.canAttach(ModelerUtils.clickedNode as GraphElement))
                            {
                                a.setGraphParent(ModelerUtils.clickedNode as GraphElement);
                            }
                            a.setContainer(containerElement);
                        }else//se presiono algun boton del toolbar
                        {
                            close=false;
                        }
                    }else if(tempNode instanceof ConnectionObject)
                    {
                        var a=tempNode as ConnectionObject;
                        if(ModelerUtils.clickedNode instanceof GraphElement)
                        {
                            var ge=ModelerUtils.clickedNode as GraphElement;
                            if(ge.canIniLink(a))
                            {
                                a.ini=ge;
                                add(tempNode);
                            }
                            close=false;
                            ModelerUtils.clickedNode=null;
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
                        var a=tempNode as ConnectionObject;
                        if(overNode!=null)
                        {
                            if(overNode.canEndLink(a))a.end=overNode;
                        }else
                        {
                            a.end=null;
                        }
                    }
                }
                else if(ModelerUtils.clickedNode instanceof ConnectionObject)
                {
                    tempNode=ModelerUtils.clickedNode;
                    var a=tempNode as ConnectionObject;
                    a.end=null;
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
                             remove(tempNode);
                         }
                         tempNode=null;
                         disablePannable=false;
                     }
                 }

//                 if(ModelerUtils.clickedNode instanceof Pool)
//                 {
//                     var p=ModelerUtils.clickedNode as Pool;
//                     p.updateLanesCords();
//                 }
//                 if(ModelerUtils.clickedNode instanceof Lane)
//                 {
//                     var p=(ModelerUtils.clickedNode as Lane).getPool();
//                     p.updateLanesCords();
//                 }


             }
//             onKeyTyped: function( e: KeyEvent ):Void
//             {
//                 println(e);
//             }
         };
         //return ret;
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
                if(ge instanceof Pool)
                {
                    var pool=ge as Pool;
                    var lane=pool.getLaneByURI(uri);
                    if(lane!=null)return lane;
                }
            }
        }
        return null;
    }

    public function setFocusedNode(node:Node)
    {
        focusedNode=node;
        ModelerUtils.setResizeNode(node);
    }

    public function getFocusedNode():Node
    {
        return focusedNode;
    }

}