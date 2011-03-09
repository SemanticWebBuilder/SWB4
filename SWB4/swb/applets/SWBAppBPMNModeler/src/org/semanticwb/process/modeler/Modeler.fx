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
import javafx.scene.input.KeyEvent;
import org.semanticwb.process.modeler.ConnectionObject;

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
    public var zoomFactor:Number = 1;
    public var copyNodes: GraphicalElement[];
    var actions: MenuItem[];
    var focusedNode: Node;                       //Nodo con el foco
    var scrollOffset:ScrollOffset;
    var selectedNodes: GraphicalElement[];
    //var styles: HashMap;
    var selectedStyle: String;
    var selectedMode: String;

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
                ModelerUtils.popup,
             ]
         }
         selectedStyle = "EyeCandy";
         selectedMode = "Begginer";

//         AddStyle("Vistoso", "Modeler");
//         AddStyle("Simple", "ModelerFlat");
//         AddStyle("Blanco y Negro", "ModelerBlackWhite");

         actions = [
            MenuItem {
                caption: ##"actPaste";
                status: bind if (copyNodes == null or copyNodes.isEmpty()) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                action: function (e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    for (ele in copyNodes) {
                        if (ele.canAddToDiagram()) {
//                            ele.x = mousex;
//                            ele.y = mousey;
                            ele.setContainer(containerElement);
                            insert ele into contents;
                            if (ele instanceof SubProcess) {
                                var ff = ele as SubProcess;
                                for (child in ff.containerChilds) {
                                    if (Sequences.indexOf(contents, child) == -1) {
                                        insert child into contents;
                                    }
                                }
                            }
//                            if (ele instanceof Pool) {
//                                println("*Se va a pegar un pool");
//                                var ff = t as Pool;
//                                for (ele in ff.graphChilds where not (ele instanceof Lane)) {
//                                    insert ele into contents;
//                                }
//                            }
                        }
                    }
                    delete copyNodes;
                }
            },
            MenuItem {
                caption: ##"actStyle"
                items: [
                    MenuItem {
                        caption: ##"actEyeCandy"
                        status: bind if (selectedStyle.equals("EyeCandy")) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            scene.stylesheets = "{__DIR__}Modeler.css";
                            selectedStyle = "EyeCandy";
                        }
                    },
                    MenuItem {
                        caption: ##"actSimple"
                        status: bind if (selectedStyle.equals("Simple")) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            scene.stylesheets = "{__DIR__}ModelerFlat.css";
                            selectedStyle = "Simple";
                        }
                    },
                    MenuItem {
                        caption: ##"actBlackWhite"
                        status: bind if (selectedStyle.equals("BlackWhite")) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            scene.stylesheets = "{__DIR__}ModelerBlackWhite.css";
                            selectedStyle = "BlackWhite";
                        }
                    }
                ]
            },
            MenuItem {
                caption: ##"actMode"
                items: [
                    MenuItem {
                        caption: ##"actExpert"
                        status: bind if (selectedMode.equals("Expert")) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            selectedMode = "Expert";
                            toolBar.showHelp = false;
                        }
                    },
                    MenuItem {
                        caption: ##"actBegginer"
                        status: bind if (selectedMode.equals("Begginer")) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            selectedMode = "Begginer";
                            toolBar.showHelp = true;
                        }
                    }
                ]
            }
        ];

         scrollView=ScrollView
         {
             node:content
             layoutInfo: LayoutInfo{width:bind width, height: bind height}
             pannable: bind pannable and not disablePannable
             cursor:bind if(pannable)Cursor.MOVE else Cursor.DEFAULT
             styleClass: "scrollView"
             
             onMousePressed: function( e: MouseEvent ):Void
             {
                this.mousePressed(e);
             }
             
             onMouseDragged: function( e: MouseEvent ):Void
             {
                this.mouseDragged(e);
             }
             
             onMouseReleased: function( e: MouseEvent ):Void
             {
                 this.mouseReleased(e);
             }

             onMouseClicked: function (e: MouseEvent) : Void {
                this.mouseClicked(e);
             }

             onMouseMoved: function (e: MouseEvent) {
                this.mouseMoved(e);
             }

             onKeyPressed: function (e: KeyEvent): Void
             {
                 this.keyPressed(e);
             }

             onKeyReleased: function (e: KeyEvent): Void
             {
                 this.keyReleased(e);
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


    public function keyPressed( e: KeyEvent ) : Void
    {
        //println("Modeler:{e.char} {e.CHAR_UNDEFINED} {e.code} {e.text} {e.metaDown} {e.shiftDown} {e.controlDown} {e.altDown}");
        //println("key released in {e.node}");
        if(e.text.toLowerCase().equals("z") and e.controlDown==true)
        {
            toolBar.undo();
        }
        if(e.text.toLowerCase().equals("y") and e.controlDown==true)
        {
            toolBar.redo();
        }
        if (e.code == e.code.VK_ESCAPE) {
            ModelerUtils.popup.hide();
        }
        if (e.code == e.code.VK_DELETE) {
//            if (focusedNode != null and focusedNode instanceof GraphicalElement) {
//                (focusedNode as GraphicalElement).remove(true);
//                focusedNode = null;
//            }
            var ts = Sequences.shuffle(selectedNodes);
            for (ele in ts) {
                (ele as GraphicalElement).remove(true);
            }
            delete selectedNodes;
        }
        if (e.code == e.code.VK_C and e.controlDown) {
            delete copyNodes;
            for (ele in selectedNodes) {
                var t = ele.copy();
                insert t into copyNodes;
            }

//            if (focusedNode != null and focusedNode instanceof GraphicalElement) {
//                copyNode = (focusedNode as GraphicalElement).copy();
//            }
        }
        if (e.code == e.code.VK_V and e.controlDown) {
            ModelerUtils.popup.hide();
            for (ele in copyNodes) {
                if (ele.canAddToDiagram()) {
//                    ele.x = mousex;
//                    ele.y = mousey;
                    insert ele into contents;
                    if (ele instanceof SubProcess) {
                        var ff = ele as SubProcess;
                        for (child in ff.containerChilds) {
                            insert child into contents;
                        }
                    }
//                    if (t instanceof Pool) {
//                        println("*Se va a pegar un pool");
//                        var ff = t as Pool;
//                        for (ele in ff.graphChilds where not (ele instanceof Lane)) {
//                            insert ele into contents;
//                        }
//                    }
                }
            }
            delete copyNodes;
        }
        if (e.code == e.code.VK_RIGHT) {
            for (ele in selectedNodes) {
                if (not (ele instanceof Lane)) {
                    ele.x += 10;
                }
            }
        } else if (e.code == e.code.VK_LEFT) {
            for (ele in selectedNodes) {
                if (not (ele instanceof Lane)) {
                    var sp = ele.x - ele.w / 2;
                    if (sp - 10 > ele.sceneX) {
                        ele.x -= 10;
                    }
                }
            }
        } else if (e.code == e.code.VK_UP) {
            for (ele in selectedNodes) {
                if (not (ele instanceof Lane)) {
                    var sp = ele.y - ele.h / 2;
                    if (sp - 10 > ele.sceneY) {
                        ele.y -= 10;
                    }
                }
            }
        } else if (e.code == e.code.VK_DOWN) {
            for (ele in selectedNodes) {
                if (not (ele instanceof Lane)) {
                    ele.y += 10;
                }
            }
        }
    }

    public function keyReleased( e: KeyEvent ) : Void
    {
        //println("key released in {e.node}");
        //println("Modeler:{e.char} {e.CHAR_UNDEFINED} {e.char} {e.code} {e.text} {e.metaDown} {e.shiftDown} {e.controlDown} {e.altDown}");
    }

    public function mouseReleased (e: MouseEvent) : Void {
         //println("MouseReleased in {e.node}");
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
             } else {
                 onMousePressed(e);
             }
         }
         if (e.button == e.button.PRIMARY) {
             ModelerUtils.popup.hide();
         }
    }

    public function mouseDragged(e: MouseEvent) : Void {
        //println("MouseDragged in {e.node}");
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

    public function mouseMoved(e: MouseEvent) : Void {
        //println("MouseMoved in {e.node}");
        mousex=e.x+getXScroll();
        mousey=e.y+getYScroll();
    }

    public function mouseClicked(e: MouseEvent) : Void {
        //println("MouseClicked in {e.node}");
        
        if (e.button == e.button.SECONDARY and overNode == null) {
            ModelerUtils.popup.setOptions(actions);
            ModelerUtils.popup.show(e);
        }
    }

    public function mousePressed(e: MouseEvent) : Void {
        mousex=e.x+getXScroll();
        mousey=e.y+getYScroll();

        if (e.node == scrollView) {
            //this.requestFocus();
            unselectAll();
        }

        if (ModelerUtils.clickedNode == null) {
            ModelerUtils.setResizeNode(null);
            //this.requestFocus();
        }

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
                            setFocusedNode(a);
                            setSelectedNode(a);
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
                            setFocusedNode(a);
                            setSelectedNode(a);
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

    public function unselectAll() : Void {
        for(ele in selectedNodes) {
            ele.text.cancelEditing();
            ele.selected = false;
        }
        delete selectedNodes;
    }

    public function addSelectedNode(ge: GraphicalElement) : Void {
        if (Sequences.indexOf(selectedNodes, ge) == -1) {
            ge.selected = true;
            insert ge into selectedNodes;
        }
    }

    public function removeSelectedNode(ge: GraphicalElement) : Void {
        if (Sequences.indexOf(selectedNodes, ge) != -1) {
            ge.selected = false;
            delete ge from selectedNodes;
        }
    }

    public function setSelectedNode(ge: GraphicalElement) : Void {
        unselectAll();
        addSelectedNode(ge);
        ge.selected = true;
    }

    public function addCopyNode(ge: GraphicalElement) : Void {
        if (Sequences.indexOf(copyNodes, ge) == -1) {
            insert ge into copyNodes;
        }
    }

    public function setCopyNode(ge: GraphicalElement) : Void {
        delete copyNodes;
        insert ge into copyNodes;
    }

//    public function AddStyle(name: String, path: String) : Void {
//        styles.put(name, path);
//    }

    public function isMultiSelection() : Boolean {
        return (selectedNodes != null and selectedNodes.size() > 1);
    }
}