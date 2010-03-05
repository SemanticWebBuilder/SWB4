/*
 * Modeler.fx
 *
 * Created on 26/02/2010, 12:34:46 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.layout.ClipView;
import javafx.scene.input.MouseEvent;
import org.semanticwb.publishflow.FlowObject;
import javafx.scene.Group;
import applets.commons.WBTreeNode;
import applets.commons.WBXMLParser;


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

    public var info:WorkFlowInfo=WorkFlowInfo
    {
        w:200
        h:100
        x:bind width-205;
        y:bind height-105;
        canEdit:false;
        description:"workflow 1.0"
        name:"workflow 1.0"
        id_workflow:"1";
        version:"1.0"
    }


    
    


    
    public override function create(): Node
    {
        
        insert info into contents;

         clipView=ClipView
         //var ret=ScrollPane
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
                            remove(tempNode);                            
                         }
                         tempNode=null;
                         disablePannable=false;
                     }
                 }
             }
             onMouseMoved:function( e: MouseEvent ):Void
             {
                 if(overNode!=null)
                 {
                     overNode.mouseMoved(e);
                 }

             }

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

    public function save() : Void
    {
        var  parse : WBXMLParser = new WBXMLParser();
        var  node: WBTreeNode = parse.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        var wf:WBTreeNode = node.addNode();
        wf.setName("workflow");
        wf.addAttribute("version", info.version);
        wf.addAttribute("canEdit", info.canEdit.toString());
        if (id != null)
        {
            wf.addAttribute("id", info.id_workflow);
        }
        wf.addAttribute("name", info.name);
        var desc: WBTreeNode = wf.addNode();
        desc.setName("description");
        desc.setText(info.description.trim());
        for(content in contents)
        {
            if(content instanceof FlowObject)
            {
                var activity:FlowObject=content as FlowObject;
                var eactivity : WBTreeNode = wf.addNode();
                eactivity.setName("activity");
                eactivity.addAttribute("name", activity.title);
                if (activity instanceof EndEvent)
                {
                    eactivity.addAttribute("name", "EndActivity");
                    eactivity.addAttribute("type", "EndActivity");
                }
                else if (activity instanceof AuthorActivity)
                {
                    eactivity.addAttribute("name", "AuthorActivity");
                    eactivity.addAttribute("type", "AuthorActivity");
                }
                else if (activity instanceof Task)
                {
                    var task:Task=activity as Task;
                    eactivity.addAttribute("type", "Activity");
                    eactivity.addAttribute("days", String.valueOf(task.days));
                    eactivity.addAttribute("hours", String.valueOf(task.hours));
                    var edesc : WBTreeNode = eactivity.addNode();
                    edesc.setName("description");
                    edesc.setText(task.description);
                    for(svalue in task.roles)
                    {
                        var values:String[]=svalue.split("@");
                        var erole : WBTreeNode = eactivity.addNode();
                        erole.setName("role");
                        erole.addAttribute("id", values[0]);
                        erole.addAttribute("name", values[2]);
                        erole.addAttribute("repository",values[1]);
                    }
                    for(svalue in task.users)
                    {
                        var values:String[]=svalue.split("@");
                        var euser: WBTreeNode = eactivity.addNode();
                        euser.setName("user");
                        euser.addAttribute("id", values[0]);
                        euser.addAttribute("name", values[1]);
                    }
                }
            }
        }
        for(content in contents)
        {
            if(content instanceof LinkConnection)
            {
                var link:LinkConnection=content as LinkConnection;
                var elink :WBTreeNode = wf.addNode();
                elink.setName("link");


                for(svalue in link.users)
                {
                    var values:String[]=svalue.split("@");
                    var notifica : WBTreeNode = elink.addNode();
                    notifica.setName("notification");
                    notifica.addAttribute("to", values[0]);
                    notifica.addAttribute("type", "user");
                }

                for(svalue in link.roles)
                {
                    var values:String[]=svalue.split("@");
                    var notifica : WBTreeNode = elink.addNode();
                    notifica.setName("notification");
                    notifica.addAttribute("to", values[0]);
                    notifica.addAttribute("repository", values[1]);
                    notifica.addAttribute("type", "role");
                }

                elink.addAttribute("from", link.ini.title);
                elink.addAttribute("to", link.end.title);
                elink.addAttribute("type", link.type);
                elink.addAttribute("publish", String.valueOf(link.published));
                elink.addAttribute("authorized", String.valueOf(link.authorized));
                var eservicemail : WBTreeNode = elink.addNode();
                eservicemail.setName("service");
                eservicemail.setText("mail");
                var activityTo:FlowObject=link.end;
                if (link.end instanceof AuthorActivity)
                {
                    if (link.authorized)
                    {
                        var eservice : WBTreeNode= elink.addNode();
                        eservice.setName("service");
                        eservice.setText("authorize");
                    }
                    else
                    {
                        var eservice : WBTreeNode = elink.addNode();
                        eservice.setName("service");
                        eservice.setText("noauthorize");
                    }
                }                
                else if (activityTo instanceof EndEvent)
                {
                    if (link.published)
                    {
                        var eservice : WBTreeNode= elink.addNode();
                        eservice.setName("service");
                        eservice.setText("publish");
                    }
                    if (link.authorized)
                    {
                        var eservice : WBTreeNode = elink.addNode();
                        eservice.setName("service");
                        eservice.setText("authorize");
                    }
                    else
                    {
                        var eservice : WBTreeNode = elink.addNode();
                        eservice.setName("service");
                        eservice.setText("noauthorize");
                    }
                }
                else
                {
                    if (not link.type.equalsIgnoreCase("authorized"))
                    {
                        var eservice : WBTreeNode= elink.addNode();
                        eservice.setName("service");
                        eservice.setText("noauthorize");
                    }
                }

            }

        }


        
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
        if(obj instanceof ConnectionObject)
        {
            var con=obj as ConnectionObject;
            con.deleted=true;
            if(con.ini!=null)
            {
                for(cp in con.ini.connectionPoints)
                {
                    if(cp.connectionObject!=null and cp.connectionObject.uri.equals(con.uri))
                    {
                        cp.connectionObject=null;
                    }
                }
            }
            if(con.end!=null)
            {
                for(cp in con.end.connectionPoints)
                {
                    if(cp.connectionObject!=null and cp.connectionObject.uri.equals(con.uri))
                    {
                        cp.connectionObject=null;
                    }
                }
            }
             
        }

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
