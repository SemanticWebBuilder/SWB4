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
import applets.commons.JSONArray;
import javafx.stage.AppletStageExtension;
import javafx.stage.Alert;
import javafx.stage.Stage;
import applets.commons.WBConnection;
import org.semanticwb.process.modeler.SubMenu;
import java.lang.Exception;
import org.semanticwb.process.modeler.StartEvent;
import org.semanticwb.process.modeler.SequenceFlow;
import org.semanticwb.process.modeler.SubProcess;
import applets.commons.WBXMLParser;
import org.semanticwb.process.modeler.ConditionalFlow;
import org.semanticwb.process.modeler.ComplexGateWay;

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


    public function loadProcess(): Void
    {
        try
        {
            var comando="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getProcessJSON</cmd></req>";
            var json=conn.getData(comando);
            //println("json:{json}");

            var jsobj=new JSONObject(json);
            var jsarr = jsobj.getJSONArray("nodes");
            var i=0;
            //GraphicElements
            while(i<jsarr.length())
            {
                //generic
                var js = jsarr.getJSONObject(i);
                var cls:String=js.getString("class");
                var uri:String=js.getString("uri");

                var ge:GraphElement=null;
                if(cls.endsWith(".UserTask"))
                {
                    ge=Task{};
                }else if(cls.endsWith(".Process"))
                {
                    ge=SubProcess{};
                }else if(cls.endsWith(".InitEvent"))
                {
                    ge=StartEvent{};
                }else if(cls.endsWith(".EndEvent"))
                {
                    ge=EndEvent{};
                }else if(cls.endsWith(".GateWay"))
                {
                    ge=GateWay{};
                }else if(cls.endsWith(".ORGateWay"))
                {
                    ge=ORGateWay{};
                }else if(cls.endsWith(".ANDGateWay"))
                {
                    ge=ANDGateWay{};
                }
                if(ge!=null)
                {
                    var title=js.getString("title");
                    var x=js.getInt("x");
                    var y=js.getInt("y");

                    ge.modeler=modeler;
                    ge.uri=uri;
                    ge.title=title;
                    ge.x=x;
                    ge.y=y;
                    modeler.add(ge);
                    //println("jsobj:{js.toString()}, i: {i}");
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
                var uri:String=js.getString("uri");

                var co:ConnectionObject=null;

                if(cls.endsWith(".SequenceFlow"))
                {
                    co=SequenceFlow{};
                }
                if(cls.endsWith(".ConditionalFlow"))
                {
                    co=ConditionalFlow{};
                    var cond=js.getString("action");
                    if(cond!=null)co.title=cond;
                }
                if(co!=null)
                {
                    //ConnectionObjects
                    var start=js.getString("start");
                    var end=js.getString("end");

                    co.modeler=modeler;
                    co.uri=uri;
                    //co.title=title;
                    co.ini=modeler.getGraphElementByURI(start);
                    co.end=modeler.getGraphElementByURI(end);
                    modeler.add(co);
                    //println("jsobj:{js.toString()}, i: {i}");
                }
                i++;
            }
        }catch(e:Exception){println(e);}
    }

    public override function create(): Node
    {
        loadProcess();


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
                        modeler.tempNode=SequenceFlow
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
                        modeler.tempNode=SequenceFlow
                        {
                            modeler:modeler
                            uri:"new:messageflow:{counter++}"
                        }
                    }
                },
                ImgButton {
                    text:"Association Flow"
                    toolBar:this;
                    image: "images/doc_asocia1.png"
                    imageOver: "images/doc_asocia2.png"
                    action: function():Void {
                        modeler.disablePannable=true;
                        modeler.tempNode=SequenceFlow
                        {
                            modeler:modeler
                            uri:"new:associationflow:{counter++}"
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
                        modeler.tempNode=Task
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
                        modeler.tempNode=Task
                        {
                            modeler:modeler
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
                        modeler.tempNode=Task
                        {
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
                        modeler.tempNode=Task
                        {
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
                        modeler.tempNode=Task
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
                        modeler.tempNode=Task
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
                        modeler.tempNode=Task
                        {
                            modeler:modeler
                            title:"Group Artifact"
                            uri:"new:groupartifact:{counter++}"
                        }
                    }
                },
            ]
        }

        var pool=ImgButton
        {
            text:"Pool"
            toolBar:this;
            image: "images/pool_1.png"
            imageOver: "images/pool_2.png"
            action: function():Void {
                modeler.disablePannable=true;
                modeler.tempNode=Pool
                {
                    modeler:modeler
                    title:"Pool"
                    uri:"new:pool:{counter++}"
                }
            }
        };

        var lane=ImgButton
        {
            text:"Lane"
            toolBar:this;
            image: "images/lane_1.png"
            imageOver: "images/lane_2.png"
            action: function():Void {
                modeler.disablePannable=true;
                modeler.tempNode=Pool
                {
                    modeler:modeler
                    title:"Lane"
                    uri:"new:Lane:{counter++}"
                }
            }
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
                                stage.fullScreen = not stage.fullScreen;
                            }
                        },
                        ImgButton {
                            text:"Open"
                            toolBar:this;
                            image: "images/open_doc_1.png"
                            imageOver: "images/open_doc_2.png"
                            action: function():Void
                            {
                            }
                        },
                        ImgButton {
                            text:"Save"
                            toolBar:this;
                            image: "images/save_1.png"
                            imageOver: "images/save_2.png"
                            action: function():Void
                            {
                                var obj:JSONObject =new JSONObject();
                                obj.put("uri","test");
                                var nodes:JSONArray =new JSONArray();
                                obj.putOpt("nodes",nodes);
                                for(node in modeler.contents)
                                {
                                    var ele:JSONObject=new JSONObject();
                                    nodes.put(ele);
                                    if(node instanceof GraphElement)
                                    {
                                       var ge=node as GraphElement;
                                       ele.put("class",ge.getClass().getName());
                                       ele.put("title",ge.title);
                                       ele.put("uri",ge.uri);
                                       ele.put("x",ge.x);
                                       ele.put("y",ge.y);
                                    }
                                    if(node instanceof FlowObject)
                                    {
                                       var ge=node as FlowObject;
                                       ele.put("lane",ge.pool.uri);
                                    }
                                    if(node instanceof Event)
                                    {
                                       var ge=node as Event;
                                       ele.put("type",ge.type);
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

                                }
                                //println(obj.toString());

                                var comando="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>updateModel</cmd><json>{WBXMLParser.encode(obj.toString(),"UTF8")}</json></req>";
                                var data=conn.getData(comando);
                                AppletStageExtension.eval("parent.reloadTreeNodeByURI('{conn.getUri()}')");
                                if(data.indexOf("OK")>0)
                                {
                                    Alert.inform("SemanticWebBuilder","Los datos fueron enviados correctamente");
                                    delete modeler.contents;
                                    loadProcess();
                                }else
                                {
                                    Alert.inform("Error",data);
                                }
                            }
                        },
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
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
                        },
                        sequence,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                        },
                        artifacts,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                        },
                        pool,
                        lane,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_bottom.png"
                            }
                        }
                    ]
                },
                task.subBar,
                subtask.subBar,
                startEvent.subBar,
                interEvent.subBar,
                endEvent.subBar,
                gateWay.subBar,
                sequence.subBar,
                artifacts.subBar
             ]
             cursor:Cursor.HAND;
        };

        return ret;
    }
}

