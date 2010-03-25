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
import org.semanticwb.publishflow.AuthorActivity;

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
    public function save() : Void
    {
            modeler.save();
    }

    public override function create(): Node {
        loadProcess();
        var submenuEvents=SubMenu
        {
            modeler: modeler
            toolBar:this
            text:"Start Event"
            image: "images/start_1.png"
            imageOver: "images/start_2.png"
            imageClicked: "images/start_2.png"
            buttons: [
                    ImgButton
                    {
                        text: "Start Event"
                        toolBar:this
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
                            }
                        }
                    },
                    ImgButton {
                        text: "End Event"
                        toolBar:this
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
                                uri: "new:endevent:{counter++}"
                                type: Event.RULE;
                            }
                        }
                    },
                    ImgButton {
                        text: "Author Event"
                        toolBar:this
                        image: "images/author_1.png"
                        imageOver: "images/author_2.png"
                        action: function (): Void {
                            if(modeler.hasAnElementWith("new:authoractivity:"))
                            {
                                return;
                            }
                            modeler.disablePannable = true;
                            modeler.tempNode = AuthorActivity {
                                modeler: modeler
                                uri: "new:authoractivity:{counter++}"
                                type: Event.RULE;
                            }
                        }
                    }
                    ]
        }

        var submenuLines=SubMenu
        {
            modeler: modeler
            toolBar:this
            text:"SequenceFlow"
            image: "images/flow_1.png"
            imageOver: "images/flow_2.png"
            imageClicked: "images/flow_2.png"
             buttons: [
                     ImgButton
                    {
                        text: "SequenceFlow"
                        toolBar:this
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
                        text: "AuthorizeFlow"
                        toolBar:this
                        image: "images/flow_a_1.png"
                        imageOver: "images/flow_a_2.png"
                        action: function (): Void {
                            modeler.disablePannable = true;
                            modeler.tempNode = AuthorizeLink {
                                modeler: modeler
                                uri: "new:sequenceflow:{counter++}"
                            }
                        }
                    },
                    ImgButton {
                        text: "UnAuthorizeFlow"
                        toolBar:this
                        image: "images/flow_u_1.png"
                        imageOver: "images/flow_u_2.png"
                        action: function (): Void {
                            modeler.disablePannable = true;
                            modeler.tempNode = NoAuthorizeLink {
                                modeler: modeler
                                uri: "new:sequenceflow:{counter++}"
                            }
                        }
                        }]
        };

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
                                    toolBar:this
                                    image: "images/save_1.png"
                                    imageOver: "images/save_2.png"
                                    action: function (): Void {
                                        save();
                                    }
                                },
                                 ImgButton {
                                    text: "Editar Propiedades"
                                    toolBar:this
                                    image: "images/edit_flujo_1.png"
                                    imageOver: "images/edit_flujo_2.png"
                                    action: function (): Void {
                                        modeler.editProps();

                                    }
                                },
                                ImageView {
                                    image: Image {
                                        url: "{__DIR__}images/barra_division.png"
                                    }
                                },
                                ImgButton {
                                    text: "Task"
                                    toolBar:this
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
                                submenuEvents,
                                submenuLines
                                ,
                                ImageView {
                                    image: Image {
                                        url: "{__DIR__}images/barra_bottom.png"
                                    }
                                }
                            ]
                        },
                        submenuLines.subBar,
                        submenuEvents.subBar
                    ]
                    cursor: Cursor.HAND;
                };
        return ret;
    }

}


