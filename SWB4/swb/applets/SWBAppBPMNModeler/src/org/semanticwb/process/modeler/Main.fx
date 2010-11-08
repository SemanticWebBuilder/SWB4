/*
 * Main.fx
 *
 * Created on 15/11/2009, 02:46:27 AM
 */

package org.semanticwb.process.modeler;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import org.semanticwb.process.modeler.ModelerUtils;
import javafx.util.StringLocalizer;
import java.util.Locale;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * @author javier.solis
 */

var maxx : Number = bind scene.width; //on replace{ modeler.organizeMap();};
var maxy : Number = bind scene.height; //on replace{ modeler.organizeMap();};

var lang : String = FX.getArgument("lang") as String;
//var lang : String = "es";
println("Lang:{lang} {Locale.getDefault()}");
if(lang!=null)Locale.setDefault(new Locale(lang));
println("Locale:{Locale.getDefault()}");
var localizer: StringLocalizer = StringLocalizer{}
//public-read var inBrowser = "true".equals(FX.getArgument("isApplet") as String);

ModelerUtils.setLocalizer(localizer);

var modeler:Modeler = Modeler
{
    layoutX:0 //69
    layoutY:0
    width:bind maxx
    height:bind maxy
    pannable:true
    cursor:Cursor.CROSSHAIR
};

modeler.load("home");
modeler.organizeMap();

var toolbar:ToolBar = ToolBar
{
    w:49
    h:bind maxy
    modeler: modeler
}

modeler.toolBar=toolbar;

var path:ContainerPath = ContainerPath
{
    w:bind maxx
    h:20
    x:bind maxx-path.flow.boundsInLocal.width-2
    y:0
    modeler: modeler
    visible:bind if(modeler.containerElement!=null)true else false
}

var scene : Scene = Scene {
    content: [
        modeler,
        path,
        toolbar,
        ModelerUtils.getToolTip(),
//            MenuBar{
//                menus: [
//                    Menu{
//                        text: "File"
//                        items: [
//                            MenuItem{
//                                text: "Open File"
//                                action: function(){
//                                    println("Open File!")
//                                }
//                                blocksMouse:true;
//                            }
//                            MenuItem{
//                                text: "Save File"
//                                action: function(){
//                                    println("Save File!")
//                                }
//                                blocksMouse:true;
//                            }
//                        ]
//                        blocksMouse:true;
//                    },
//                    Menu{
//                        text: "Edit"
//                        items: [
//                            MenuItem{
//                                text: "Copy"
//                                action: function(){
//                                    println("Copy!")
//                                }
//                                cursor:Cursor.DEFAULT
//                            }
//                            MenuItem{
//                                text: "Paste"
//                                action: function(){
//                                    println("Paste!")
//                                }
//                                cursor:Cursor.DEFAULT
//                            }
//                        ]
//                        cursor:Cursor.DEFAULT
//                    }
//                ]
//                width:bind maxx;
//                blocksMouse:true;
//                //cursor:Cursor.DEFAULT
//            }
        ModelerUtils.splash
    ]
    width: 600
    height: 300
    stylesheets: "{__DIR__}Modeler.css"
}

var stage : Stage = Stage {
    title: ModelerUtils.getLocalizedString("appTitle");
    resizable: true
    scene: scene
    //style: StageStyle.TRANSPARENT    //StageStyle.UNDECORATED
}

toolbar.stage=stage;