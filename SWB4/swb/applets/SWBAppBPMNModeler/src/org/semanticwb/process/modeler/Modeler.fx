/*
 * Modeler.fx
 *
 * Created on 13/02/2010, 11:01:48 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.GraphicalElement;
import org.semanticwb.process.modeler.ModelerUtils;
import java.awt.image.BufferedImage;
import javafx.scene.control.ScrollView;
import javafx.scene.layout.LayoutInfo;
import javafx.scene.Cursor;
import javafx.util.Sequences;

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
    public var overNode: GraphicalElement;
    public var mousex:Number;
    public var mousey:Number;
    public var scrollView:ScrollView;
    public var content:Group;
    public var containerElement: GraphicalElement;
    public var toolBar:ToolBar;

    var focusedNode: Node;                       //Nodo con el foco
    var scrollOffset:ScrollOffset;

    public override function create(): Node
    {
        var resize=ModelerUtils.getResizeNode();
        resize.modeler=this;

         //var ret=ScrollPane
         content=Group
         {
             content:
             [
                scrollOffset=ScrollOffset{},
                Group
                {
                    content: bind contents
                },
                resize,
                ModelerUtils.popup
             ]
         }

         scrollView=ScrollView
         {
             node:content
             layoutInfo: LayoutInfo{ width:bind width, height: bind height }
             pannable: bind pannable and not disablePannable
             cursor:bind if(pannable)Cursor.MOVE else Cursor.DEFAULT
             styleClass: "scrollView"
             
             onMousePressed: function( e: MouseEvent ):Void
             {
                mousex=e.x+getXScroll();
                mousey=e.y+getYScroll();

                if(tempNode!=null)
                {
                    var close: Boolean=true;
                    if(tempNode instanceof GraphicalElement)
                    {
                        if(ModelerUtils.clickedNode==null) //elemento sobre modeler
                        {
                            if(tempNode instanceof Lane)
                            {
                                //no se permite crear lanes sin pool
                            }else
                            {                                
                                var a=tempNode as GraphicalElement;
                                if (a.canAddToDiagram()) {
                                    if(tempNode instanceof Pool)addFirst(tempNode) else add(tempNode);
                                    a.x=e.x+getXScroll();
                                    a.y=e.y+getYScroll();
                                    a.snapToGrid();
                                    a.setContainer(containerElement);
                                }
                            }
                        }else if(ModelerUtils.clickedNode instanceof Pool or ModelerUtils.clickedNode instanceof Lane)
                        {
                            if(tempNode instanceof Pool) //se esta agregando un pool dentro de otro pool o lane
                            {
                                //addFirst(tempNode)
                            }else if(tempNode instanceof Lane) //se esta agregando un lane dentro de lane o pool
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
                                var a=tempNode as GraphicalElement;
                                if (a.canAddToDiagram()) {
                                    add(tempNode);
                                    a.x=e.x+getXScroll();
                                    a.y=e.y+getYScroll();
                                    a.snapToGrid();
                                    if(a.canAttach(ModelerUtils.clickedNode as GraphicalElement))
                                    {
                                        a.setGraphParent(ModelerUtils.clickedNode as GraphicalElement);
                                    }
                                    a.setContainer(containerElement);
                                }
                            }
                        }else//se presiono algun boton del toolbar
                        {                            
                            close=false;
                        }
                    }else if(tempNode instanceof ConnectionObject)
                    {
                        var a=tempNode as ConnectionObject;
                        if(ModelerUtils.clickedNode instanceof GraphicalElement)
                        {
                            var ge=ModelerUtils.clickedNode as GraphicalElement;
                            if(ge.canStartLink(a))
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
                    }
                }
             }
             
             onMouseDragged: function( e: MouseEvent ):Void
             {
                if(tempNode!=null)
                {
                    mousex=e.x+getXScroll();
                    mousey=e.y+getYScroll();

                    if(tempNode instanceof ConnectionObject)
                    {
                        //activa el conection object cuando se inicia el drag
                        if(not tempNode.visible)
                        {
                            tempNode.visible=true;
                        }

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
                         disablePannable=true;
                         ModelerUtils.clickedNode=null;
                     }
                 }

             }
         };
         return scrollView;
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

        add(ParallelGateway {
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

    public function add(obj:Node)
    {
        insert obj into contents;
    }

    public function addFirst(obj:Node)
    {
        insert obj before contents[0];
    }

    public function moveFront(fobj:Node, tobj:Node)
    {
        var f=Sequences.indexOf(contents,fobj);
        var t=Sequences.indexOf(contents,tobj);
        if(f>-1 and f<t)
        {
            delete fobj from contents;
            insert fobj after contents[t-1];
        }
    }


    public function remove(obj:Node)
    {
        delete obj from contents;

    }

    public function getGraphElementByURI(uri:String):GraphicalElement
    {
        for(node in contents)
        {
            if(node instanceof GraphicalElement)
            {
                var ge=node as GraphicalElement;
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

    public bound function getXScroll():Number
    {
        //println("{scrollView.node.boundsInParent} {scrollView.vmin} {scrollView.vmax} {scrollView.vvalue}");
        //return scrollView.hvalue*(scrollView.node.boundsInParent.width-width);
        return -scrollOffset.localToScene(scrollOffset.boundsInLocal).minX;
    }

    public bound function getYScroll():Number
    {
        //return scrollView.vvalue*(scrollView.node.boundsInParent.height-height);
        return -scrollOffset.localToScene(scrollOffset.boundsInLocal).minY;
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

    public function renderToImage(margin:Integer):BufferedImage
    {
        var minx=1000000.0;
        var miny=1000000.0;
        var maxx=0.0;
        var maxy=0.0;
        var one=false;
        for(node in contents)   //se buscan elementos activos y visibles
        {
            if(node.visible)
            {
                var b=node.boundsInLocal;
                if(b.width>0 and b.height>0)
                {
                    if(b.minX<minx)minx=b.minX;
                    if(b.minY<miny)miny=b.minY;
                    if(b.maxX>maxx)maxx=b.maxX;
                    if(b.maxY>maxy)maxy=b.maxY;
                    //println("{node} {b.minX} {b.minY} {b.maxX} {b.maxY} {b.width} {b.height}");
                    one=true;
                }
            }
        }
        if(not one) //Si no hay ningun element se inicia a cero
        {
            minx=0;
            miny=0;
        }
        var bounds=content.boundsInLocal;
        //var bufferedImage = new PrintUtil().renderToImage(content,bounds.width, bounds.height);
        //println("{bounds.minX} {bounds.minY} {bounds.maxX} {bounds.maxY} {bounds.width} {bounds.height}");
        var bufferedImage = new ModelerUtils().renderToImage(content,minx-margin,miny-margin,maxx-minx+margin*2, maxy-miny+margin*2);
        return bufferedImage;
    }

}