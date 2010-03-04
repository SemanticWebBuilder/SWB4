/*
 * ToolBar.fx
 *
 * Created on 26/02/2010, 12:25:24 PM
 */
package org.semanticwb.publishflow;

/**
 * @author victor.lorenzana
 */
import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.Flow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import applets.commons.WBConnection;

public var counter: Integer;
public var conn: WBConnection = new WBConnection(FX.getArgument(WBConnection.PRM_JSESS).toString(), FX.getArgument(WBConnection.PRM_CGIPATH).toString(), FX.getProperty("javafx.application.codebase"));

public class ToolBar extends CustomNode {

    public var modeler: Modeler;
    public var stage: Stage;
    public var x: Number;
    public var y: Number;
    public var w: Number;
    public var h: Number;
    var dx: Number;                        //temporal drag x
    var dy: Number;                        //temporal drag y

    public function loadProcess(): Void {
    }

    public override function create(): Node {
        loadProcess();
        var ret = Group {
                    translateX: bind x
                    translateY: bind y
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
                                    cursor: Cursor.MOVE
                                    onMousePressed: function (e: MouseEvent): Void {
                                        modeler.clickedNode = this;
                                        modeler.disablePannable = true;
                                        dx = x - e.sceneX;
                                        dy = y - e.sceneY;
                                    }
                                    onMouseDragged: function (e: MouseEvent): Void {
                                        x = dx + e.sceneX;
                                        y = dy + e.sceneY;
                                    }
                                    onMouseReleased: function (e: MouseEvent): Void {
                                        modeler.clickedNode = null;
                                        modeler.disablePannable = false;
                                    }
                                },
                                ImgButton {
                                    text: "Hide"
                                    image: "images/sube_1.png"
                                    imageOver: "images/sube_2.png"
                                    action: function (): Void {
                                        //stage.fullScreen = not stage.fullScreen;
                                    }
                                },
                                ImgButton {
                                    text: "Maximize"
                                    image: "images/maxim_1.png"
                                    imageOver: "images/maxim_2.png"
                                    action: function (): Void {
                                        stage.fullScreen = not stage.fullScreen;
                                    }
                                },
                                ImgButton {
                                    text: "Save"
                                    image: "images/save_1.png"
                                    imageOver: "images/save_2.png"
                                    action: function (): Void {

                                    }
                                },
                                ImageView {
                                    image: Image {
                                        url: "{__DIR__}images/barra_division.png"
                                    }
                                },
                                ImgButton {
                                    text: "Task"
                                    image: "images/task_1.png"
                                    imageOver: "images/task_2.png"
                                    action: function (): Void {
                                        //println("click");
                                        counter++;
                                        modeler.disablePannable = true;
                                        modeler.tempNode = Task {
                                            modeler: modeler                                            
                                            title: "Task {counter}"
                                            uri: "new:task:{counter}"
                                        }
                                    }
                                },
                                ImgButton {
                                    text: "Start Event"
                                    image: "images/start_1.png"
                                    imageOver: "images/start_2.png"
                                    action: function (): Void {
                                        if(modeler.hasAnElementWith("new:startevent:"))
                                        {
                                            return;
                                        }
                                            
                                        modeler.disablePannable = true;
                                        modeler.tempNode = StartEvent {
                                            modeler: modeler
                                            title: "Start Event"
                                            uri: "new:startevent:{counter++}"
                                        //type: Event.RULE;
                                        }
                                    }
                                },
                                ImgButton {
                                    text: "End Event"
                                    image: "images/end_1.png"
                                    imageOver: "images/end_2.png"
                                    action: function (): Void {
                                        if(modeler.hasAnElementWith("new:endevent:"))
                                        {
                                            return;
                                        }


                                        modeler.disablePannable = true;
                                        modeler.tempNode = EndEvent {
                                            modeler: modeler
                                            title: "End Event"
                                            uri: "new:endevent:{counter++}"
                                            type: Event.RULE;
                                        }
                                    }
                                },
                                ImgButton {
                                    text: "SequenceFlow"
                                    image: "images/flow_1.png"
                                    imageOver: "images/flow_2.png"
                                    action: function (): Void {
                                        modeler.disablePannable = true;
                                        modeler.tempNode = SequenceFlow {
                                            modeler: modeler
                                            uri: "new:sequenceflow:{counter++}"
                                        }
                                    }
                                },
                                ImgButton {
                                    text: "SequenceFlow"
                                    image: "images/flow_1.png"
                                    imageOver: "images/flow_2.png"
                                    action: function (): Void {
                                        modeler.disablePannable = true;
                                        modeler.tempNode = AuthorizeLink {
                                            modeler: modeler
                                            uri: "new:sequenceflow:{counter++}"
                                        }
                                    }
                                },
                                ImgButton {
                                    text: "SequenceFlow"
                                    image: "images/flow_1.png"
                                    imageOver: "images/flow_2.png"
                                    action: function (): Void {
                                        modeler.disablePannable = true;
                                        modeler.tempNode = NoAuthorizeLink {
                                            modeler: modeler
                                            uri: "new:sequenceflow:{counter++}"
                                        }
                                    }
                                },
                                ImageView {
                                    image: Image {
                                        url: "{__DIR__}images/barra_bottom.png"
                                    }
                                }
                            ]
                        }
                    ]
                    cursor: Cursor.HAND;
                };
        return ret;
    }

}


