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
import org.semanticwb.publishflow.StartEvent;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;
import java.util.Locale;
import org.semanticwb.publishflow.ConnectionObject;
import java.lang.String;
import java.lang.System;
import java.util.Iterator;
import org.semanticwb.publishflow.EndEvent;
import java.lang.Exception;
import org.semanticwb.publishflow.NoAuthorizeLink;
import org.semanticwb.publishflow.AuthorizeLink;


/**
 * @author victor.lorenzana
 */

public class Modeler extends CustomNode
{
    var locale:Locale;
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
    public var tm:String;
    public var info:WorkFlowInfo=WorkFlowInfo
    {
        w:200
        h:110
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
                               if(co.ini!=null and co.ini!=overNode)
                               {
                                    co.end=overNode as FlowObject;
                               }
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

     public function loadWorkflow(id : String) : Void
    {
        var xml:String = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getWorkflow</cmd><id>{id}</id><tm>{tm}</tm></req>";
        //var respxml : String= ToolBar.conn.getData(xml);
        var respxml :String= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><workflows><workflow id=\"1_infotec\" name=\"newsletter - a\" version=\"4.0\"><description>Secciones revisadas por Ana Laura GarcÃƒÂ­a</description><activity days=\"0\" hours=\"0\" name=\"RevisiÃƒÂ³n\" type=\"Activity:\"><description>Primer filtro de estilo, diseÃƒÂ±o y contenido</description><user id=\"443164462_wb\" name=\"Ana Laura GarcÃƒÂ­a\"/></activity><activity name=\"Generador de contenido\" type=\"AuthorActivity\"/><activity name=\"Terminar flujo\" type=\"EndActivity\"/><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"Generador de contenido\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"true\" to=\"Terminar flujo\" type=\"authorized\"><service>mail</service><service>publish</service><service>noauthorize</service></link><resourceType id=\"110\" name=\"ExcelContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"9\" name=\"LocalContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"109\" name=\"PPTContent\" topicmap=\"WBAGlobal\"/></workflow><workflow id=\"1_infotec\" name=\"newsletter - a\" version=\"3.0\"><description>Secciones revisadas por Ana Laura GarcÃƒÂ­a</description><activity days=\"0\" hours=\"0\" name=\"RevisiÃƒÂ³n\" type=\"Activity\"><description>Primer filtro de estilo, diseÃƒÂ±o y contenido</description><user id=\"443164462_wb\" name=\"Ana Laura GarcÃƒÂ­a\"/></activity><activity days=\"0\" hours=\"0\" name=\"AutorizaciÃƒÂ³n\" type=\"Activity\"><description>RevisiÃƒÂ³n y liberaciÃƒÂ³n del artÃƒÂ­culo</description><user id=\"443164311_wb\" name=\"Jose Luis Rodriguez\"/></activity><activity name=\"Generador de contenido\" type=\"AuthorActivity\"/><activity name=\"Terminar flujo\" type=\"EndActivity\"/><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"Generador de contenido\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"AutorizaciÃƒÂ³n\" type=\"authorized\"><service>mail</service></link><link authorized=\"false\" from=\"AutorizaciÃƒÂ³n\" publish=\"false\" to=\"Generador de contenido\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><link authorized=\"false\" from=\"AutorizaciÃƒÂ³n\" publish=\"true\" to=\"Terminar flujo\" type=\"authorized\"><service>mail</service><service>publish</service><service>noauthorize</service></link><resourceType id=\"110\" name=\"ExcelContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"9\" name=\"LocalContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"109\" name=\"PPTContent\" topicmap=\"WBAGlobal\"/></workflow><workflow id=\"1_infotec\" name=\"newsletter - a\" version=\"2.0\"><description>Secciones revisadas por Ana Laura GarcÃƒÂ­a</description><activity days=\"0\" hours=\"0\" name=\"RevisiÃƒÂ³n\" type=\"Activity\"><description>Primer filtro de estilo, diseÃƒÂ±o y contenido</description><user id=\"443164462_wb\" name=\"Ana Laura GarcÃƒÂ­a\"/></activity><activity days=\"0\" hours=\"0\" name=\"AutorizaciÃƒÂ³n\" type=\"Activity\"><description>RevisiÃƒÂ³n y liberaciÃƒÂ³n del artÃƒÂ­culo</description><user id=\"443164311_wb\" name=\"Jose Luis Rodriguez\"/></activity><activity name=\"Generador de contenido\" type=\"AuthorActivity\"/><activity name=\"Terminar flujo\" type=\"EndActivity\"/><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"Generador de contenido\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"AutorizaciÃƒÂ³n\" type=\"authorized\"><service>mail</service></link><link authorized=\"false\" from=\"AutorizaciÃƒÂ³n\" publish=\"false\" to=\"RevisiÃƒÂ³n\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><link authorized=\"false\" from=\"AutorizaciÃƒÂ³n\" publish=\"true\" to=\"Terminar flujo\" type=\"authorized\"><service>mail</service><service>publish</service><service>noauthorize</service></link><resourceType id=\"110\" name=\"ExcelContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"9\" name=\"LocalContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"109\" name=\"PPTContent\" topicmap=\"WBAGlobal\"/></workflow><workflow id=\"1_infotec\" name=\"newsletter - a\" version=\"1.0\"><description>Secciones revisadas por Ana Laura GarcÃƒÂ­a</description><activity days=\"0\" hours=\"0\" name=\"RevisiÃƒÂ³n\" type=\"Activity\"><description>Primer filtro de estilo, diseÃƒÂ±o y contenido</description><user id=\"443164246_wb\" name=\"Aurora Alejandra Flores\"/></activity><activity days=\"0\" hours=\"0\" name=\"AutorizaciÃƒÂ³n\" type=\"Activity\"><description>RevisiÃƒÂ³n y liberaciÃƒÂ³n del artÃƒÂ­culo</description><user id=\"443164311_wb\" name=\"Jose Luis Rodriguez\"/></activity><activity name=\"Generador de contenido\" type=\"AuthorActivity\"/><activity name=\"Terminar flujo\" type=\"EndActivity\"/><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"AutorizaciÃƒÂ³n\" type=\"authorized\"><service>mail</service></link><link authorized=\"false\" from=\"RevisiÃƒÂ³n\" publish=\"false\" to=\"Generador de contenido\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><link authorized=\"false\" from=\"AutorizaciÃƒÂ³n\" publish=\"true\" to=\"Terminar flujo\" type=\"authorized\"><service>mail</service><service>publish</service><service>noauthorize</service></link><link authorized=\"false\" from=\"AutorizaciÃƒÂ³n\" publish=\"false\" to=\"RevisiÃƒÂ³n\" type=\"unauthorized\"><service>mail</service><service>noauthorize</service></link><resourceType id=\"110\" name=\"ExcelContent\" topicmap=\"WBAGlobal\"/><resourceType id=\"9\" name=\"LocalContent\" topicmap=\"WBAGlobal\"/></workflow></workflows>";

        var parser : WBXMLParser= new WBXMLParser();
        var exml:WBTreeNode = parser.parse(respxml);
        if (exml.getFirstNode() != null and exml.getFirstNode().getFirstNode() != null)
        {
            var eworkflow:WBTreeNode = exml.getFirstNode().getFirstNode();
            if (eworkflow.getName().equals("workflow"))
            {
                info.name=eworkflow.getAttribute("name");
                info.version=eworkflow.getAttribute("version");
                if(eworkflow.getAttribute("canEdit")!=null and not eworkflow.getAttribute("canEdit").equals("") and eworkflow.getAttribute("canEdit").equals("true"))
                {
                    info.canEdit=true;
                }
                var edescription:WBTreeNode=eworkflow.getNodebyName("description");
                if(edescription!=null)
                {
                    info.description=edescription.getFirstNode().getText();
                }
                var startEvent:StartEvent=StartEvent
                {
                    x:100
                    y:100
                    modeler:this
                }
                add(startEvent);

                var initActivity=true;
                var activities: Iterator=eworkflow.getNodesbyName("activity");
                while(activities.hasNext())
                {


                    var eactivity: WBTreeNode=activities.next() as WBTreeNode;
                    var type:String=eactivity.getAttribute("type");
                    if(type.equals("EndActivity"))
                    {
                        //this.modelactivities.addActivity(new EndActivity(locale));
                        var end:EndEvent=EndEvent
                        {
                            x:100
                            y:100
                            type:{type};
                            modeler:this
                            uri:"new:EndEvent({ToolBar.counter++})"
                        }
                        //insert end into contents
                        add(end);

                    }
                    else if(type.equals("AuthorActivity"))
                    {
                        var act:AuthorActivity=AuthorActivity
                        {
                            x:100
                            y:100
                            type:{type};
                            modeler:this
                            uri:"new:AuthorActivity({ToolBar.counter++})"
                        }
                        add(act);
                    }
                    else
                    {

                        var desc: String="";

                        var edesc : WBTreeNode=eactivity.getNodebyName("description");

                        if(edesc!=null)
                        {
                            desc=edesc.getFirstNode().getText();
                        }
                        var name:String=eactivity.getAttribute("name");
                        var title:String=name;
                        if(eactivity.getAttribute("title")!=null and not eactivity.getAttribute("title").equals(""))
                        {
                            title=eactivity.getAttribute("title");
                        }

                        var activity: Task =Task
                        {
                            x:100
                            y:100
                            description:{desc}
                            title:title
                            modeler:this
                            uri:"new:Task({ToolBar.counter++})"

                        }
                        add(activity);
                        if(initActivity)
                        {
                            var co:ConnectionObject=ConnectionObject
                            {
                                ini:{startEvent};
                                end:activity;
                                modeler:{this};
                            }
                            initActivity=not initActivity;
                        }
                        
                        var days:Integer=0;
                        var minutes:Integer=0;
                        var seconds:Integer =0;
                        var hours:Integer=0;
                        try
                        {
                            days=Integer.parseInt(eactivity.getAttribute("days"));
                            if(eactivity.getAttribute("hours")!=null and not eactivity.getAttribute("hours").equals(""))
                            {
                                hours=Integer.parseInt(eactivity.getAttribute("hours"));
                            }
                        }
                        catch( e: Exception)
                        {
                            e.printStackTrace(System.out);
                        }
                        //activity.setDuraction(days,hours);
                        activity.days=days;
                        activity.hours=hours;
                        var roles: Iterator=eactivity.getNodesbyName("role");
                        while(roles.hasNext())
                        {

                            var erole: WBTreeNode=roles.next() as WBTreeNode;
                            //var role: Role=new Role(erole.getAttribute("id"),erole.getAttribute("name"),erole.getAttribute("repository"));
                            var rolid:String =erole.getAttribute("id");
                            var rolname:String=erole.getAttribute("name");
                            var rep:String=erole.getAttribute("repository");
                            var srole:String="{rolid}@{rep}@{rolname}";
                            insert srole into activity.roles;
                        }
                        var users: Iterator=eactivity.getNodesbyName("user");
                        while(users.hasNext())
                        {
                            var euser: WBTreeNode=users.next() as WBTreeNode;
                            //User user=new User(,);
                            var userid:String=euser.getAttribute("id");
                            var username:String=euser.getAttribute("name");
                            var srole:String="{id}@{username}";
                            //activity.getUserModel().addUser(user);
                            insert srole into activity.users;
                        }
                    }
                }

                var links: Iterator=eworkflow.getNodesbyName("link");
                while(links.hasNext())
                {
                    var elink : WBTreeNode=links.next() as WBTreeNode;
                    var type:String=elink.getAttribute("type");
                    var nameto:String=elink.getAttribute("to");
                    var namefrom:String=elink.getAttribute("from");
                    var activityto:Task;
                    var activityfrom:Task;
                    for(node in this.contents)
                    {
                        if(node instanceof Task)
                        {
                            var activity:Task=node as Task;
                            if(activity.title.equals(nameto))
                            {
                                activityto=activity;
                            }
                            if(activity.title.equals(namefrom))
                            {
                                activityfrom=activity;
                            }
                        }

                        if(activityto!=null and activityfrom!=null)
                        {
                            //String linktype=elink.getAttribute("type");
                            //var link:Link=new Link(activityto, activityfrom,linktype);
                            var link:LinkConnection;
                            if(type.equals("authorized"))
                            {
                                link=AuthorizeLink
                                {
                                    ini: activityfrom
                                    end: activityto
                                    pini:bind activityfrom.connectionPoints[0];
                                    pend:bind activityto.connectionPoints[1];
                                    modeler:this;
                                }
                            }
                            else
                            {
                                link=NoAuthorizeLink
                                {
                                    ini: activityfrom
                                    end: activityto
                                    pini:bind activityfrom.connectionPoints[0];
                                    pend:bind activityto.connectionPoints[1];
                                    modeler:this;
                                }
                            }
                            add(link);
                            var itNotifications : Iterator=elink.getNodesbyName("notification");
                            while(itNotifications.hasNext())
                            {
                                var eNotification: WBTreeNode=itNotifications.next() as WBTreeNode;
                                if(eNotification.getAttribute("type").equals("user"))
                                {
                                    var userid:String=eNotification.getAttribute("to");
                                    var user:String="{userid}@";
                                    insert user into link.users;
                                }
                                else
                                {
                                    var roleID:String=eNotification.getAttribute("to");
                                    var repository:String=eNotification.getAttribute("repository");
                                    var role:String="{roleID}@{repository}";
                                    insert role into link.roles;
                                }
                            }
                            link.authorized=Boolean.valueOf(elink.getAttribute("authorized"));
                            link.published=(Boolean.valueOf(elink.getAttribute("publish")).booleanValue());
                        }
                    }
                }
                /*Iterator it = this.workflow.getResourcesModel().iterator();
                while (it.hasNext())
                {
                    ResourceType res = (ResourceType) it.next();
                    jTableResourceTypeModel resmodel = (jTableResourceTypeModel) this.jTableTipos_Recursos.getModel();
                    Iterator recursos = resmodel.iterator();
                    while (recursos.hasNext())
                    {
                        ResourceType rescat = (ResourceType) recursos.next();
                        if (rescat.equals(res))
                        {
                            rescat.setSelected(true);
                        }
                    }

                }*/

            }
        }
    }
    

    public function save() : Void
    {
        var endEvent:EndEvent;
        var startEvent:StartEvent;
        for(content in contents)
        {
            if(content instanceof StartEvent)
            {
                startEvent=content as StartEvent;
                break;
            }
        }

        if(startEvent==null)
        {
            JOptionPane.showMessageDialog(null, "Debe indicar un nodo de inicio", "Guardar Flujo", JOptionPane.OK_OPTION + JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(startEvent.getBusyPoints()==0)
        {
            JOptionPane.showMessageDialog(null, "Debe inidicar cual es la actividad inicial", "Guardar Flujo", JOptionPane.OK_OPTION + JOptionPane.ERROR_MESSAGE);
            startEvent.requestFocus();
            return;
        }

        for(content in contents)
        {
            if(content instanceof EndEvent)
            {
                endEvent=content as EndEvent;
                break;
            }
        }
        if(endEvent==null)
        {
            JOptionPane.showMessageDialog(null, "Debe indicar un nodo final", "Guardar Flujo", JOptionPane.OK_OPTION + JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(endEvent.getBusyPoints()==0)
        {
            JOptionPane.showMessageDialog(null, "El nodo final no tiene una conexión", "Guardar Flujo", JOptionPane.OK_OPTION + JOptionPane.ERROR_MESSAGE);
            return;
        }

        for(content in contents)
        {
            if(content instanceof Task)
            {
                var task=content as Task;
                if(not task.hasIniAuthorizeLink())
                {
                    JOptionPane.showMessageDialog(null, "La tarea {task.title} no tiene conexión en caso de autorización", "Guardar Flujo", JOptionPane.OK_OPTION + JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(not task.hasIniNoAuthorizeLink())
                {
                    JOptionPane.showMessageDialog(null, "La tarea {task.title} no tiene conexión en caso de no autorización", "Guardar Flujo", JOptionPane.OK_OPTION + JOptionPane.ERROR_MESSAGE);
                    return;
                }

                break;
            }
        }

        




        if (info.id_workflow != null)
        {
            var ask:String=ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("ask1");
            var msg:String=ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("msg2");
            var status:Integer = JOptionPane.showConfirmDialog(
                    null,"{ask}\r\n{msg}",
                    ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("title"),
                    JOptionPane.YES_NO_OPTION+
                    JOptionPane.QUESTION_MESSAGE);
            if (status == JOptionPane.NO_OPTION)
            {
                return;
            }

        }

        var iniActivity:Task=startEvent.getConnectionObject(0).end as Task;





        var  parse : WBXMLParser = new WBXMLParser();
        var  node: WBTreeNode = parse.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        var wf:WBTreeNode = node.addNode();
        wf.setName("workflow");
        wf.addAttribute("version", info.version);
        wf.addAttribute("canEdit", info.canEdit.toString());
        if (info.id_workflow != null)
        {
            wf.addAttribute("id", info.id_workflow);
        }
        wf.addAttribute("name", info.name);
        var desc: WBTreeNode = wf.addNode();
        desc.setName("description");
        desc.setText(info.description.trim());
        var eactivity : WBTreeNode = wf.addNode();
        eactivity.setName("activity");
        eactivity.addAttribute("name", iniActivity.title);
        eactivity.addAttribute("type", "Activity");
        eactivity.addAttribute("days", String.valueOf(iniActivity.days));
        eactivity.addAttribute("hours", String.valueOf(iniActivity.hours));
        var edesc : WBTreeNode = eactivity.addNode();
        edesc.setName("description");
        edesc.setText(iniActivity.description);
        for(svalue in iniActivity.roles)
        {
            var values:String[]=svalue.split("@");
            var erole : WBTreeNode = eactivity.addNode();
            erole.setName("role");
            erole.addAttribute("id", values[0]);
            erole.addAttribute("name", values[2]);
            erole.addAttribute("repository",values[1]);
        }
        for(svalue in iniActivity.users)
        {
            var values:String[]=svalue.split("@");
            var euser: WBTreeNode = eactivity.addNode();
            euser.setName("user");
            euser.addAttribute("id", values[0]);
            euser.addAttribute("name", values[1]);
        }
        for(content in contents)
        {
            if(content instanceof FlowObject)
            {
                if(iniActivity!=content)
                {
                var activity:FlowObject=content as FlowObject;
                eactivity = wf.addNode();
                eactivity.setName("activity");
                eactivity.addAttribute("name", activity.uri);
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
                    eactivity.addAttribute("title", task.title);
                    eactivity.addAttribute("type", "Activity");
                    eactivity.addAttribute("days", String.valueOf(task.days));
                    eactivity.addAttribute("hours", String.valueOf(task.hours));
                    edesc = eactivity.addNode();
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
        
        println(node.getXML());

        
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
