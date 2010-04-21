/*
 * ToolBar.fx
 *
 * Created on 13/02/2010, 11:37:17 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.Flow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import applets.commons.JSONObject;
import javafx.stage.Stage;
import applets.commons.WBConnection;
import org.semanticwb.process.modeler.SubMenu;
import java.lang.Exception;
import org.semanticwb.process.modeler.StartEvent;
import org.semanticwb.process.modeler.SequenceFlow;
import org.semanticwb.process.modeler.SubProcess;
import org.semanticwb.process.modeler.ConditionalFlow;
import org.semanticwb.process.modeler.ComplexGateWay;
import applets.commons.JSONArray;
import javafx.stage.AppletStageExtension;
import javafx.stage.Alert;
import applets.commons.WBXMLParser;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import org.semanticwb.process.modeler.GraphElement;
import org.semanticwb.process.modeler.ModelerUtils;

public var counter: Integer;
public var conn:WBConnection = new WBConnection(FX.getArgument(WBConnection.PRM_JSESS).toString(),FX.getArgument(WBConnection.PRM_CGIPATH).toString(),FX.getProperty("javafx.application.codebase"));

/**
 * @author javier.solis
 */

public class ToolBar extends CustomNode
{
    public var modeler:Modeler;
    public var stage:Stage;
    public var x:Number;
    public var y:Number;
    public var w:Number;
    public var h:Number;
    var dx : Number;                        //temporal drag x
    var dy : Number;                        //temporal drag y

    var isApplet:Boolean=false;

    var fileChooser = javax.swing.JFileChooser{};
    var imageFileChooser = javax.swing.JFileChooser{};

    public function openProcess(): Void
    {
        //fileChooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
        if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            var file = fileChooser.getSelectedFile();
            //println(file);
            try
            {
                var in=new FileInputStream(file);
                var proc=WBConnection.readInputStream(in);
                //println(proc);
                delete modeler.contents;
                modeler.containerElement=null;
                createProcess(proc);
            }catch(e:Exception){Alert.inform("Error",e.getMessage());}
        }
    }

    public function saveAsImage(): Void
    {
        //fileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        if (imageFileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            var file = imageFileChooser.getSelectedFile();
            if(not file.getName().toLowerCase().endsWith("png"))
            {
                file=new File("{file.getPath()}.png");
            }
            println(file);
            var bufferedImage=modeler.renderToImage(25);
            println(bufferedImage);
            try
            {
                var out=new FileOutputStream(file);
                def bufferedOutputStream = new BufferedOutputStream(out);
                println(bufferedOutputStream);
                javax.imageio.ImageIO.write( bufferedImage, "PNG", bufferedOutputStream );
                bufferedOutputStream.close();
                out.close();
                println("end");
            }catch(e:Exception)
            {
                println(e.getMessage());
                Alert.inform("Error",e.getMessage());
            }
        }
    }

    public function saveProcess(): Void
    {
        //fileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            var file = fileChooser.getSelectedFile();
            if(not file.getName().toLowerCase().endsWith("swp"))
            {
                file=new File("{file.getPath()}.swp");
            }

            //println(file);
            var proc=getProcess();
            try
            {
                var out=new FileOutputStream(file);
                out.write(proc.getBytes());
                out.close();
            }catch(e:Exception){Alert.inform("Error",e.getMessage());}
        }
    }

    public function storeProcess(): Void
    {
        var process=getProcess();

        var comando="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>updateModel</cmd><json>{WBXMLParser.encode(process,"UTF8")}</json></req>";
        var data=conn.getData(comando);
        AppletStageExtension.eval("parent.reloadTreeNodeByURI('{conn.getUri()}')");
        if(data.indexOf("OK")>0)
        {
            Alert.inform("SemanticWebBuilder","Los datos fueron enviados correctamente");
            loadProcess();
        }else
        {
            Alert.inform("Error",data);
        }

    }

    public function loadProcess(): Void
    {
        try
        {
            var comando="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getProcessJSON</cmd></req>";
            var json=conn.getData(comando);
            //println("json:{json}");
            delete modeler.contents;
            createProcess(json);
        }catch(e:Exception){println(e);}

    }

    /**
    * Serialyze the process to JSON
    */
    public function getProcess(): String
    {
        var obj:JSONObject =new JSONObject();
        obj.put("uri","test");
        var nodes:JSONArray =new JSONArray();
        obj.putOpt("nodes",nodes);
        for(node in modeler.contents)
        {
            var ele=getJSONObject(node);
            nodes.put(ele);

            if(node instanceof Pool)
            {
                var pool=node as Pool;
                for(lane in pool.lanes)
                {
                    nodes.put(getJSONObject(lane));
                }
            }
        }
        return obj.toString();
    }

    function getJSONObject(node:Node):JSONObject
    {
        var ele:JSONObject=new JSONObject();
        if(node instanceof GraphElement)
        {
           var ge=node as GraphElement;
           ele.put("class",ge.getClass().getName());
           ele.put("container",ge.getContainer().uri);
           ele.put("parent",ge.getGraphParent().uri);
           ele.put("title",ge.title);
           ele.put("type",ge.type);
           ele.put("uri",ge.uri);
           ele.put("x",ge.x);
           ele.put("y",ge.y);
           ele.put("w",ge.w);
           ele.put("h",ge.h);
        }
        if(node instanceof ConnectionObject)
        {
           var ge=node as ConnectionObject;
           ele.put("class",ge.getClass().getName());
           ele.put("uri",ge.uri);
           ele.put("title",ge.title);
           ele.put("start",ge.ini.uri);
           ele.put("end",ge.end.uri);
           if(node instanceof ConditionalFlow)
           {
               var con=node as ConditionalFlow;
               ele.put("action", ge.action);
           }
        }
        return ele;
    }


    /**
    * Increment the internal counter for new uris
    */
    function validateUri(uri:String):String
    {
        if(uri.startsWith("new:"))
        {
            var c=Integer.parseInt(uri.substring(uri.lastIndexOf(":")+1));
            if(c>counter)counter=c+1;
        }
        return uri;
    }

    /**
    * Create a process from a JSON
    */
    public function createProcess(json:String): Void
    {
        println("Arguments:{FX.getArgument}");

        var jsobj=new JSONObject(json);
        var jsarr = jsobj.getJSONArray("nodes");
        var i=0;
        //GraphicElements
        while(i<jsarr.length())
        {
            //generic
            var js = jsarr.getJSONObject(i);
            var cls:String=js.getString("class");
            var uri:String=validateUri(js.getString("uri"));

            var clss=getClass().forName(cls);
            var node=clss.newInstance() as Node;

            var ge:GraphElement=null;
            if(node instanceof GraphElement)
            {
                ge=node as GraphElement;
            }

            if(ge!=null and not (ge instanceof Lane))
            {
                var title=js.getString("title");
                var type=js.getString("type");
                var x=js.getInt("x");
                var y=js.getInt("y");
                var w=js.getInt("w");
                var h=js.getInt("h");

                ge.modeler=modeler;
                ge.uri=uri;
                //println("uri:{ge.uri}");
                ge.title=title;
                ge.setType(type);
                ge.x=x;
                ge.y=y;
                if(w>0)ge.w=w;
                if(h>0)ge.h=h;
                modeler.add(ge);
                //println("jsobj:{js.toString()}, i: {i}");
            }
            i++;
        }


        //Lanes
        i=0;
        while(i<jsarr.length())
        {
            //generic
            var js = jsarr.getJSONObject(i);
            var cls:String=js.getString("class");
            var uri:String=validateUri(js.getString("uri"));

            var clss=getClass().forName(cls);
            var node=clss.newInstance() as Node;

            if(node instanceof Lane)
            {
                var parent=js.getString("parent");
                var title=js.getString("title");
                var type=js.getString("type");
                var h=js.getInt("h");
                var p=modeler.getGraphElementByURI(parent);
                if(p instanceof Pool)
                {
                    var pool=p as Pool;
                    var lane=pool.addLane();
                    lane.title=title;
                    lane.type=type;
                    lane.h=h;
                    lane.uri=uri;
                }
            }
            i++;
        }


        //ConnectionObjects
        i=0;
        while(i<jsarr.length())
        {
            //generic
            var js = jsarr.getJSONObject(i);
            var cls:String=js.getString("class");
            var uri:String=validateUri(js.getString("uri"));

            var clss=getClass().forName(cls);
            var node=clss.newInstance() as Node;

            //Parents
            var ge:GraphElement=null;
            if(node instanceof GraphElement)
            {
                ge=modeler.getGraphElementByURI(uri);
            }

            if(ge!=null)
            {
                var parent=js.getString("parent");
                ge.setGraphParent(modeler.getGraphElementByURI(parent));
                var container=js.getString("container");
                ge.setContainer(modeler.getGraphElementByURI(container));
                println("{ge} parent:{ge.getGraphParent()}");
            }

            //Connections
            var co:ConnectionObject=null;
            if(node instanceof ConnectionObject)
            {
                co=node as ConnectionObject;
            }

            if(co!=null)
            {
                //ConnectionObjects
                var start=js.getString("start");
                var end=js.getString("end");
                var title=js.getString("title");

                co.modeler=modeler;
                co.uri=uri;
                co.title=title;
                co.ini=modeler.getGraphElementByURI(start);
                co.end=modeler.getGraphElementByURI(end);
                modeler.add(co);
                //println("jsobj:{js.toString()}, i: {i}");
            }
            i++;
        }
    }

    public override function create(): Node
    {
        var filter = FileFilter{};
        fileChooser.setFileFilter(filter);
        var imgFilter = ImageFileFilter{};
        imageFileChooser.setFileFilter(imgFilter);

        if(isApplet)loadProcess();


        var file=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"File"
            image: "images/file1.png"
            imageOver: "images/file2.png"
            imageClicked: "images/file3.png"
            buttons: [
                ImgButton {
                    text:"New"
                    toolBar:this;
                    image: "images/file_nuevo1.png"
                    imageOver: "images/file_nuevo2.png"
                    action: function():Void
                    {
                        ModelerUtils.clickedNode=null;
                        ModelerUtils.setResizeNode(null);
                        modeler.containerElement=null;
                        modeler.disablePannable=false;
                        delete modeler.contents;
                    }
                },
                ImgButton {
                    text:"Open"
                    toolBar:this;
                    image: "images/file_abrir1.png"
                    imageOver: "images/file_abrir2.png"
                    action: function():Void
                    {
                        ModelerUtils.clickedNode=null;
                        ModelerUtils.setResizeNode(null);
                        modeler.containerElement=null;
                        modeler.disablePannable=false;
                        openProcess();
                    }
                },
                ImgButton {
                    text:"Save"
                    toolBar:this;
                    image: "images/file_guardar1.png"
                    imageOver: "images/file_guardar2.png"
                    action: function():Void
                    {
                        ModelerUtils.clickedNode=null;
                        modeler.disablePannable=false;
                        saveProcess();
                    }
                },
                ImgButton {
                    text:"Print"
                    toolBar:this;
                    image: "images/file_print1.png"
                    imageOver: "images/file_print2.png"
                    action: function():Void
                    {
                        var print=PrintUtil{};
                        var aux=modeler.containerElement;
                        modeler.containerElement=null;
                        var arr=[modeler.renderToImage(1)];
                        for(node in modeler.contents)
                        {
                            if(node instanceof GraphElement)
                            {
                                var ge=node as GraphElement;
                                if(ge.containerable)
                                {
                                    modeler.containerElement=ge;
                                    insert modeler.renderToImage(1) into arr;
                                }
                            }
                        }
                        modeler.containerElement=aux;
                        print.print(arr);
                        //TODO:
                    }
                },
                ImgButton {
                    text:"Save As"
                    toolBar:this;
                    image: "images/file_saveas1.png"
                    imageOver: "images/file_saveas2.png"
                    action: function():Void
                    {
                        ModelerUtils.clickedNode=null;
                        modeler.disablePannable=false;
                        saveAsImage();
                    }
                },
                ImgButton {
                    text:"About"
                    toolBar:this;
                    image: "images/file_about1.png"
                    imageOver: "images/file_about2.png"
                    action: function():Void
                    {
                        ModelerUtils.clickedNode=null;
                        modeler.disablePannable=false;
                        ModelerUtils.splash.showDialog(modeler.width/2,modeler.height/2);
                    }
                },
            ]
        };

        var task=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"Task"
            image: "images/task_1.png"
            imageOver: "images/task_2.png"
            imageClicked: "images/task_3.png"
            buttons: [
                ImgButton {
                    text:"Task"
                    toolBar:this;
                    image: "images/task_normal1.png"
                    imageOver: "images/task_normal2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            title:"Task"
                            uri:"new:task:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Adhoc Task"
                    toolBar:this;
                    image: "images/subtask_adhoc1.png"
                    imageOver: "images/subtask_adhoc2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_ADHOC
                            title:"Adhoc Task"
                            uri:"new:adhoctask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Loop Task"
                    toolBar:this;
                    image: "images/subtask_ciclo1.png"
                    imageOver: "images/subtask_ciclo2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_LOOP
                            title:"Loop Task"
                            uri:"new:looptask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Compensation Task"
                    toolBar:this;
                    image: "images/subtask_compensa1.png"
                    imageOver: "images/subtask_compensa2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_COMPENSATION
                            title:"Compensation Task"
                            uri:"new:compensationtask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Multiple Task"
                    toolBar:this;
                    image: "images/subtask_instancia1.png"
                    imageOver: "images/subtask_instancia2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_MULTIPLE
                            title:"Multiple Task"
                            uri:"new:multipletask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"User Task"
                    toolBar:this;
                    image: "images/task_usr1.png"
                    imageOver: "images/task_usr2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_USER
                            title:"User Task"
                            uri:"new:usertask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Service Task"
                    toolBar:this;
                    image: "images/task_servicio1.png"
                    imageOver: "images/task_servicio2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_SERVICE
                            title:"Service Task"
                            uri:"new:servicetask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Script Task"
                    toolBar:this;
                    image: "images/task_script1.png"
                    imageOver: "images/task_script2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_SCRIPT
                            title:"Script Task"
                            uri:"new:scripttask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Manual Task"
                    toolBar:this;
                    image: "images/task_manual1.png"
                    imageOver: "images/task_manual2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_MANUAL
                            title:"Manual Task"
                            uri:"new:manualtask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Send Task"
                    toolBar:this;
                    image: "images/task_envio1.png"
                    imageOver: "images/task_envio2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_SEND
                            title:"Send Task"
                            uri:"new:sendtask:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Receive Task"
                    toolBar:this;
                    image: "images/task_recive1.png"
                    imageOver: "images/task_recive2.png"
                    action: function():Void
                    {
                        //println("click");
                        modeler.disablePannable=true;
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            type:Task.TYPE_RECEIVE
                            title:"Receive Task"
                            uri:"new:receivetask:{counter++}"
                        }
                    }
                },
            ]
        };

        var subtask=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"SubTask"
            image: "images/subtask_1.png"
            imageOver: "images/subtask_2.png"
            imageClicked: "images/subtask_3.png"
            buttons: [
                ImgButton {
                    text:"SubProcess"
                    toolBar:this;
                    image: "images/subtask_colapsado1.png"
                    imageOver: "images/subtask_colapsado2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SubProcess
                        {
                            modeler:modeler
                            title:"SubProcess"
                            uri:"new:subprocess:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Adhoc SubProcess"
                    toolBar:this;
                    image: "images/subtask_adhoc+1.png"
                    imageOver: "images/subtask_adhoc+2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SubProcess
                        {
                            modeler:modeler
                            type:SubProcess.TYPE_ADHOC
                            title:"Adhoc SubProcess"
                            uri:"new:adhocsubprocess:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Loop SubProcess"
                    toolBar:this;
                    image: "images/subtask_ciclo+1.png"
                    imageOver: "images/subtask_ciclo+2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SubProcess
                        {
                            modeler:modeler
                            type:SubProcess.TYPE_LOOP
                            title:"Loop SubProcess"
                            uri:"new:loopsubprocess:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Compensation SubProcess"
                    toolBar:this;
                    image: "images/subtask_compensa+1.png"
                    imageOver: "images/subtask_compensa+2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SubProcess
                        {
                            modeler:modeler
                            type:SubProcess.TYPE_COMPENSATION
                            title:"Compensation SubProcess"
                            uri:"new:compensationsubprocess:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Multiple SubProcess"
                    toolBar:this;
                    image: "images/subtask_instancia+1.png"
                    imageOver: "images/subtask_instancia+2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SubProcess
                        {
                            modeler:modeler
                            type:SubProcess.TYPE_MULTIPLE
                            title:"Multiple SubProcess"
                            uri:"new:multiplesubprocess:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Transaction SubProcess"
                    toolBar:this;
                    image: "images/subtask_transaccion1.png"
                    imageOver: "images/subtask_transaccion2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SubProcess
                        {
                            modeler:modeler
                            type:SubProcess.TYPE_TRANSACTION
                            title:"Transaction SubProcess"
                            uri:"new:transactionsubprocess:{counter++}"
                        }
                    }
                },
            ]
        };


        var startEvent=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"Start Event"
            image: "images/start_1.png"
            imageOver: "images/start_2.png"
            imageClicked: "images/start_3.png"
            buttons: [
                ImgButton {
                    text:"Start Event"
                    image: "images/start_normal1.png"
                    imageOver: "images/start_normal2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Start Event"
                            uri:"new:startevent:{counter++}"
                            //type: Event.RULE;
                        }
                    }
                },
                ImgButton {
                    text:"Message Start Event"
                    image: "images/start_msj1.png"
                    imageOver: "images/start_msj2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Message Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_MESSAGE;
                        }
                    }
                },
                ImgButton {
                    text:"Timer Start Event"
                    image: "images/start_tmp1.png"
                    imageOver: "images/start_tmp2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Timer Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_TIMER;
                        }
                    }
                },
                ImgButton {
                    text:"Conditional Start Event"
                    image: "images/start_cond1.png"
                    imageOver: "images/start_cond2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Conditional Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_CONDITINAL;
                        }
                    }
                },
                ImgButton {
                    text:"Signal Start Event"
                    image: "images/start_senal1.png"
                    imageOver: "images/start_senal2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Signal Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_SIGNAL;
                        }
                    }
                },
                ImgButton {
                    text:"Multiple Start Event"
                    image: "images/start_multi1.png"
                    imageOver: "images/start_multi2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Multiple Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_MULTIPLE;
                        }
                    }
                },
                ImgButton {
                    text:"Parallel Start Event"
                    image: "images/start_paralelo1.png"
                    imageOver: "images/start_paralelo2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Parallel Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_PARALLEL;
                        }
                    }
                },
                ImgButton {
                    text:"Escalation Start Event"
                    image: "images/start_escala1.png"
                    imageOver: "images/start_escala2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Escalation Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_SCALATION;
                        }
                    }
                },
                ImgButton {
                    text:"Error Start Event"
                    image: "images/start_error1.png"
                    imageOver: "images/start_error2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Error Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_ERROR;
                        }
                    }
                },
                ImgButton {
                    text:"Compensation Start Event"
                    image: "images/start_compensa1.png"
                    imageOver: "images/start_compensa2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartEvent
                        {
                            modeler:modeler
                            title:"Compensation Start Event"
                            uri:"new:startevent:{counter++}"
                            type: Event.W_COMPENSATION;
                        }
                    }
                }
            ]
        };

        var interEvent=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"Inter Event"
            image:"images/inter_1.png"
            imageOver:"images/inter_2.png"
            imageClicked: "images/inter_3.png"
            buttons: [
                ImgButton {
                    text:"Inter Event"
                    image: "images/inter_normal1.png"
                    imageOver: "images/inter_normal2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Inter Event"
                            uri:"new:interevent:{counter++}"
                            //type: Event.RULE;
                        }
                    }
                },
                ImgButton {
                    text:"Message Inter Event"
                    image: "images/inter_msj_b_1.png"
                    imageOver: "images/inter_msj_b_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Message Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_MESSAGE;
                        }
                    }
                },
                ImgButton {
                    text:"Throwing Message Inter Event"
                    image: "images/inter_msj_n_1.png"
                    imageOver: "images/inter_msj_n_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Throwing Message Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.B_MESSAGE;
                        }
                    }
                },
                ImgButton {
                    text:"Timer Inter Event"
                    image: "images/inter_tmp1.png"
                    imageOver: "images/inter_tmp2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Timer Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_TIMER;
                        }
                    }
                },
                ImgButton {
                    text:"Error Inter Event"
                    image: "images/inter_error1.png"
                    imageOver: "images/inter_error2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Error Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_ERROR;
                        }
                    }
                },
                ImgButton {
                    text:"Cancelation Inter Event"
                    image: "images/inter_cancel_1.png"
                    imageOver: "images/inter_cancel_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Cancelation Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_CANCELATION;
                        }
                    }
                },
                ImgButton {
                    text:"Compensation Inter Event"
                    image: "images/inter_compensa_b_1.png"
                    imageOver: "images/inter_compensa_b_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Compensation Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_COMPENSATION;
                        }
                    }
                },
                ImgButton {
                    text:"Throwing Compensation Inter Event"
                    image: "images/inter_compensa_n_1.png"
                    imageOver: "images/inter_compensa_n_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Throwing Compensation Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.B_COMPENSATION;
                        }
                    }
                },
                ImgButton {
                    text:"Conditional Inter Event"
                    image: "images/inter_cond1.png"
                    imageOver: "images/inter_cond2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Conditional Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_CONDITINAL;
                        }
                    }
                },
                ImgButton {
                    text:"Link Inter Event"
                    image: "images/inter_enlace_b_1.png"
                    imageOver: "images/inter_enlace_b_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Link Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_LINK;
                        }
                    }
                },
                ImgButton {
                    text:"Throwing Link Inter Event"
                    image: "images/inter_enlace_n_1.png"
                    imageOver: "images/inter_enlace_n_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Throwing Link Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.B_LINK;
                        }
                    }
                },
                ImgButton {
                    text:"Signal Inter Event"
                    image: "images/inter_senal_b_1.png"
                    imageOver: "images/inter_senal_b_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Signal Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_SIGNAL;
                        }
                    }
                },
                ImgButton {
                    text:"Throwing Signal Inter Event"
                    image: "images/inter_senal_n_1.png"
                    imageOver: "images/inter_senal_n_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Throwing Signal Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.B_SIGNAL;
                        }
                    }
                },
                ImgButton {
                    text:"Multiple Inter Event"
                    image: "images/inter_multi_b_1.png"
                    imageOver: "images/inter_multi_b_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Multiple Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_MULTIPLE;
                        }
                    }
                },
                ImgButton {
                    text:"Throwing Multiple Inter Event"
                    image: "images/inter_multi_n_1.png"
                    imageOver: "images/inter_multi_n_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Throwing Multiple Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.B_MULTIPLE;
                        }
                    }
                },
                ImgButton {
                    text:"Scalation Inter Event"
                    image: "images/inter_escala_b_1.png"
                    imageOver: "images/inter_escala_b_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Scalation Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_SCALATION;
                        }
                    }
                },
                ImgButton {
                    text:"Throwing Scalation Inter Event"
                    image: "images/inter_escala_n_1.png"
                    imageOver: "images/inter_escala_n_2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Throwing Scalation Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.B_SCALATION;
                        }
                    }
                },
                ImgButton {
                    text:"Parallel Inter Event"
                    image: "images/inter_paralelo1.png"
                    imageOver: "images/inter_paralelo2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterEvent
                        {
                            modeler:modeler
                            title:"Parallel Inter Event"
                            uri:"new:interevent:{counter++}"
                            type: Event.W_PARALLEL;
                        }
                    }
                }
            ]
        };

        var endEvent=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"End Event"
            image: "images/end_1.png"
            imageOver: "images/end_2.png"
            imageClicked: "images/end_3.png"
            buttons: [
                ImgButton {
                    text:"End Event"
                    image: "images/end_normal1.png"
                    imageOver: "images/end_normal2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"End Event"
                            uri:"new:endevent:{counter++}"
                            //type: Event.RULE;
                        }
                    }
                },
                ImgButton {
                    text:"Message End Event"
                    image: "images/end_msj1.png"
                    imageOver: "images/end_msj2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Message End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_MESSAGE;
                        }
                    }
                },
                ImgButton {
                    text:"Error End Event"
                    image: "images/end_error1.png"
                    imageOver: "images/end_error2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Error End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_ERROR;
                        }
                    }
                },
                ImgButton {
                    text:"Cancelation End Event"
                    image: "images/end_cancel1.png"
                    imageOver: "images/end_cancel2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Cancelation End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_CANCELATION;
                        }
                    }
                },
                ImgButton {
                    text:"Compensation End Event"
                    image: "images/end_compensa1.png"
                    imageOver: "images/end_compensa2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Compensation End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_COMPENSATION;
                        }
                    }
                },
                ImgButton {
                    text:"Signal End Event"
                    image: "images/end_senal1.png"
                    imageOver: "images/end_senal2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Signal End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_SIGNAL;
                        }
                    }
                },
                ImgButton {
                    text:"Multiple End Event"
                    image: "images/end_multi1.png"
                    imageOver: "images/end_multi2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Multiple End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_MULTIPLE;
                        }
                    }
                },
                ImgButton {
                    text:"Escalation End Event"
                    image: "images/end_escala1.png"
                    imageOver: "images/end_escala2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Escalation End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_SCALATION;
                        }
                    }
                },
                ImgButton {
                    text:"Termination End Event"
                    image: "images/end_termina1.png"
                    imageOver: "images/end_termina2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EndEvent
                        {
                            modeler:modeler
                            title:"Termination End Event"
                            uri:"new:endevent:{counter++}"
                            type: Event.B_TERMINATION;
                        }
                    }
                }         
            ]
        };

        var gateWay=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"GateWay"
            image: "images/if_1.png"
            imageOver: "images/if_2.png"
            imageClicked: "images/if_3.png"
            buttons: [
                ImgButton {
                    text:"Data XOR GateWay"
                    image: "images/gate_normal1.png"
                    imageOver: "images/gate_normal2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=GateWay
                        {
                            modeler:modeler
                            title:"GateWay"
                            uri:"new:gateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Data XOR GateWay"
                    image: "images/gate_datos1.png"
                    imageOver: "images/gate_datos2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=XORGateWay
                        {
                            modeler:modeler
                            title:"XOR GateWay"
                            uri:"new:dataxorgateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"OR GateWay"
                    image: "images/gate_inclusiva_1.png"
                    imageOver: "images/gate_inclusiva_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=ORGateWay
                        {
                            modeler:modeler
                            title:"OR GateWay"
                            uri:"new:orgateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Start Event OR GateWay"
                    image: "images/gate_eventos_str_1.png"
                    imageOver: "images/gate_eventos_str_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=StartORGateWay
                        {
                            modeler:modeler
                            title:"Initial Event OR GateWay"
                            uri:"new:initialeventorgateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Inter Event OR GateWay"
                    image: "images/gate_eventos_int_1.png"
                    imageOver: "images/gate_eventos_int_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=InterORGateWay
                        {
                            modeler:modeler
                            title:"Inter Event OR GateWay"
                            uri:"new:intereventorgateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"AND GateWay"
                    image: "images/gate_paralela_n_1.png"
                    imageOver: "images/gate_paralela_n_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=ANDGateWay
                        {
                            modeler:modeler
                            title:"AND GateWay"
                            uri:"new:andgateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Event AND GateWay"
                    image: "images/gate_paralela_b_1.png"
                    imageOver: "images/gate_paralela_b_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=EventANDGateWay
                        {
                            modeler:modeler
                            title:"Event AND GateWay"
                            uri:"new:eventandgateway:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Complex GateWay"
                    image: "images/gate_compleja_1.png"
                    imageOver: "images/gate_compleja_2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=ComplexGateWay
                        {
                            modeler:modeler
                            title:"Complex GateWay"
                            uri:"new:complexgateway:{counter++}"
                        }
                    }
                },
            ]
        }

        var sequence=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"Connection Objects"
            image:"images/flow_1.png"
            imageOver:"images/flow_2.png"
            imageClicked: "images/flow_3.png"
            buttons: [
                ImgButton {
                    text:"Sequence Flow"
                    toolBar:this;
                    image: "images/flow_normal1.png"
                    imageOver: "images/flow_normal2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SequenceFlow
                        {
                            modeler:modeler
                            uri:"new:sequenceflow:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Conditional Flow"
                    toolBar:this;
                    image: "images/flow_condicion1.png"
                    imageOver: "images/flow_condicion2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=ConditionalFlow
                        {
                            modeler:modeler
                            uri:"new:conditionalflow:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Default Flow"
                    toolBar:this;
                    image: "images/flow_defecto1.png"
                    imageOver: "images/flow_defecto2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=DefaultFlow
                        {
                            modeler:modeler
                            uri:"new:defaultflow:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Message Flow"
                    toolBar:this;
                    image: "images/flow_msj1.png"
                    imageOver: "images/flow_msj2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=MessageFlow
                        {
                            modeler:modeler
                            uri:"new:messageflow:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Association"
                    toolBar:this;
                    image: "images/doc_dir_asocia1.png"
                    imageOver: "images/doc_dir_asocia2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=Association
                        {
                            modeler:modeler
                            uri:"new:associationflow:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Directional Association"
                    toolBar:this;
                    image: "images/doc_asocia1.png"
                    imageOver: "images/doc_asocia2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=DirectionalAssociation
                        {
                            modeler:modeler
                            uri:"new:dirassociationflow:{counter++}"
                        }
                    }
                },
            ]
        }

        var artifacts=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"Artifacts"
            image:"images/doc_1.png"
            imageOver:"images/doc_2.png"
            imageClicked: "images/doc_3.png"
            buttons: [
                ImgButton {
                    text:"Artifact"
                    toolBar:this;
                    image: "images/doc_normal1.png"
                    imageOver: "images/doc_normal2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=Artifact
                        {
                            modeler:modeler
                            title:"Artifact"
                            uri:"new:artifact:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Multiple Artifact"
                    toolBar:this;
                    image: "images/doc_objeto1.png"
                    imageOver: "images/doc_objeto2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=Artifact
                        {
                            modeler:modeler
                            type:Artifact.TYPE_MULTIPLE
                            title:"Multiple Artifact"
                            uri:"new:multipleartifact:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Input Artifact"
                    toolBar:this;
                    image: "images/doc_entrada1.png"
                    imageOver: "images/doc_entrada2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=Artifact
                        {
                            type:Artifact.TYPE_INPUT
                            modeler:modeler
                            title:"Input Artifact"
                            uri:"new:inputartifact:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Output Artifact"
                    toolBar:this;
                    image: "images/doc_salida1.png"
                    imageOver: "images/doc_salida2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=Artifact
                        {
                            type:Artifact.TYPE_OUTPUT
                            modeler:modeler
                            title:"Output Artifact"
                            uri:"new:outputartifact:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Data Store Artifact"
                    toolBar:this;
                    image: "images/doc_base1.png"
                    imageOver: "images/doc_base2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=DataStoreArtifact
                        {
                            modeler:modeler
                            title:"Data Store Artifact"
                            uri:"new:datastoreartifact:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Annotation Artifact"
                    toolBar:this;
                    image: "images/doc_anota1.png"
                    imageOver: "images/doc_anota2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=AnnotationArtifact
                        {
                            modeler:modeler
                            title:"Annotation Artifact"
                            uri:"new:annotationartifact:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Group Artifact"
                    toolBar:this;
                    image: "images/doc_grupo1.png"
                    imageOver: "images/doc_grupo2.png"
                    action: function():Void
                    {
                        modeler.disablePannable=true;
                        modeler.tempNode=GroupArtifact
                        {
                            modeler:modeler
                            title:"Group Artifact"
                            uri:"new:groupartifact:{counter++}"
                        }
                    }
                },
            ]
        }

        var pool=SubMenu
        {
            modeler: modeler
            toolBar:this;
            text:"Pool"
            image: "images/pool_1.png"
            imageOver: "images/pool_2.png"
            imageClicked: "images/pool_3.png"
            buttons: [
                ImgButton
                {
                    text:"Pool"
                    toolBar:this;
                    image: "images/pool_pool1.png"
                    imageOver: "images/pool_pool2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=Pool
                        {
                            modeler:modeler
                            title:"Pool"
                            uri:"new:pool:{counter++}"
                        }
                    }
                },
                ImgButton
                {
                    text:"Lane"
                    toolBar:this;
                    image: "images/pool_lane1.png"
                    imageOver: "images/pool_lane2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=Lane
                        {
                            modeler:modeler
                            title:"Lane"
                            uri:"new:Lane:{counter++}"
                        }
                    }
                }
            ]
        };

        var ret=Group
        {
             layoutX:bind x
             layoutY:bind y
             //scaleX:.5
             //scaleY:.5
             content: [
                Flow {
                    height: bind h
                    width: bind w
                    content: [
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_mov.png"
                            }
                            cursor:Cursor.MOVE
                            //blocksMouse:true
                            onMousePressed: function (e: MouseEvent): Void
                            {
                                ModelerUtils.clickedNode=this;
                                modeler.disablePannable=true;
                                dx=x-e.sceneX;
                                dy=y-e.sceneY;
                            }
                            onMouseDragged: function (e: MouseEvent): Void
                            {
                                x=dx+e.sceneX;
                                y=dy+e.sceneY;
                            }
                            onMouseReleased: function (e: MouseEvent): Void
                            {
                                ModelerUtils.clickedNode=null;
                                modeler.disablePannable=false;
                            }
                        },
                        ImgButton {
                            text:"Hide"
                            toolBar:this;
                            image: "images/sube_1.png"
                            imageOver: "images/sube_2.png"
                            action: function():Void
                            {
                                //stage.fullScreen = not stage.fullScreen;
                            }
                        },
                        ImgButton {
                            text:"Maximize"
                            toolBar:this;
                            image: "images/maxim_1.png"
                            imageOver: "images/maxim_2.png"
                            action: function():Void
                            {
                                ModelerUtils.clickedNode=null;
                                modeler.disablePannable=false;
                                stage.fullScreen = not stage.fullScreen;
                            }
                        },
                        file,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                            //blocksMouse:true
                        },
                        task,
                        subtask,
                        startEvent,
                        interEvent,
                        endEvent,
                        gateWay,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                            //blocksMouse:true
                        },
                        sequence,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                            //blocksMouse:true
                        },
                        artifacts,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                            //blocksMouse:true
                        },
                        pool,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_bottom.png"
                            }
                            //blocksMouse:true
                        }
                    ]
                },
                file.subBar,
                task.subBar,
                subtask.subBar,
                startEvent.subBar,
                interEvent.subBar,
                endEvent.subBar,
                gateWay.subBar,
                sequence.subBar,
                artifacts.subBar,
                pool.subBar
             ]
             cursor:Cursor.HAND;
             blocksMouse:true
        };

        return ret;
    }
}

class FileFilter extends javax.swing.filechooser.FileFilter {
    override public function getDescription() : String {
        return "SWP Process File";
    }

    override public function accept(f: java.io.File) : Boolean {
        return f.isDirectory() or f.getName().endsWith(".swp")
    }
}

class ImageFileFilter extends javax.swing.filechooser.FileFilter {
    override public function getDescription() : String {
        return "PNG Image";
    }

    override public function accept(f: java.io.File) : Boolean {
        return f.isDirectory() or f.getName().endsWith(".png")
    }
}