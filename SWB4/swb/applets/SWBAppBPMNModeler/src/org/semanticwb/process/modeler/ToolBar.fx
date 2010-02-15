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


    public override function create(): Node
    {
        var lane=ImgButton
        {
            text:"Lane"
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
             translateX:bind x
             translateY:bind y
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
                                modeler.clickedNode=this;
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
                                modeler.clickedNode=null;
                                modeler.disablePannable=false;
                            }
                        },
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/minimiza_1.png"
                            }
                            onMouseClicked: function (e: MouseEvent): Void {
                                stage.fullScreen = not stage.fullScreen;
                            }

                        },
                        ImgButton {
                            text:"Save"
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
                                    if(node instanceof ConnectionObject)
                                    {
                                       var ge=node as ConnectionObject;
                                       ele.put("class",ge.getClass().getName());
                                       ele.put("uri",ge.uri);
                                       ele.put("title",ge.title);
                                       ele.put("start",ge.ini.uri);
                                       ele.put("end",ge.end.uri);
                                    }

                                }
                                //println(obj.toString());

                                var comando="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>updateModel</cmd><json>{obj.toString()}</json></req>";
                                var data=conn.getData(comando);
                                AppletStageExtension.eval("parent.reloadTreeNodeByURI('{conn.getUri()}')");
                                if(data.indexOf("OK")>0)
                                {
                                    Alert.inform("SemanticWebBuilder","Los datos fueron enviados correctamente");
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
                        ImgButton {
                            text:"Task"
                            image: "images/task_1.png"
                            imageOver: "images/task_2.png"
                            action: function():Void
                            {
                                println("click");
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
                            text:"SubProcess"
                            image: "images/task_1.png"
                            imageOver: "images/task_2.png"
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
                            text:"Start Event"
                            image: "images/start_1.png"
                            imageOver: "images/start_2.png"
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
                            text:"Inter Event"
                            image:"images/inter_1.png"
                            imageOver:"images/inter_2.png"
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
                            text:"End Event"
                            image:"images/end_1.png"
                            imageOver:"images/end_2.png"
                            action: function():Void
                            {
                                modeler.disablePannable=true;
                                modeler.tempNode=EndEvent
                                {
                                    modeler:modeler
                                    title:"End Event"
                                    uri:"new:endevent:{counter++}"
                                    type: Event.RULE;
                                }
                            }
                        },
                        ImgButton {
                            text:"Gateway"
                            image: "images/if_1.png"
                            imageOver: "images/if_2.png"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=GateWay
                                {
                                    modeler:modeler
                                    title:"Gateway"
                                    uri:"new:gateway:{counter++}"
                                }
                            }
                        },
                        ImgButton {
                            text:"OR Gateway"
                            image: "images/if_1.png"
                            imageOver: "images/if_2.png"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=ORGateWay
                                {
                                    modeler:modeler
                                    title:"OR Gateway"
                                    uri:"new:orgateway:{counter++}"
                                }
                            }
                        },
                        ImgButton {
                            text:"AND Gateway"
                            image: "images/if_1.png"
                            imageOver: "images/if_2.png"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=ANDGateWay
                                {
                                    modeler:modeler
                                    title:"AND Gateway"
                                    uri:"new:andgateway:{counter++}"
                                }
                            }
                        },
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                        },
                        ImgButton {
                            text:"Flow Link"
                            image: "images/flow_1.png"
                            imageOver: "images/flow_2.png"
                            action: function():Void {
                                modeler.disablePannable=true;
                                modeler.tempNode=FlowLink
                                {
                                    modeler:modeler
                                    uri:"new:flowlink:{counter++}"
                                }
                            }
                        },
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                        },
                        ImgButton {
                            text:"Artifact"
                            image: "images/doc_1.png"
                            imageOver: "images/doc_2.png"
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
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_division.png"
                            }
                        },
                        ImgButton {
                            text:"Pool"
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
                        },
                        lane,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/barra_bottom.png"
                            }
                        }
                    ]
                },
                SubMenu
                {
                    modeler:modeler
                    w: 1000
                    h: 50
                    x: bind lane.layoutX+lane.layoutBounds.width
                    y: bind lane.layoutY
                }
             ]
             cursor:Cursor.HAND;
        };
        return ret;
    }
}

