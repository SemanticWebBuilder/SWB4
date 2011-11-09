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

/**
 * @author javier.solis
 */

var maxx : Number = bind scene.width; //on replace{ modeler.organizeMap();};
var maxy : Number = bind scene.height; //on replace{ modeler.organizeMap();};

var lang : String = FX.getArgument("lang") as String;
var donePath : String = FX.getArgument("done") as String;
var currentActivities : String = FX.getArgument("currentActivities") as String;
var mode: String = FX.getArgument("mode") as String;
var taskPage: String = FX.getArgument("tp") as String;
var repPage: String = FX.getArgument("rp") as String;

if (mode == null) mode= "edit";
if (donePath == null) donePath="";
if (currentActivities == null) currentActivities="";
if (taskPage == null) taskPage = "";
if (repPage == null) repPage = "";
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
/**********************************************/
var toolbar:ToolBar = ToolBar
{
    w:49
    h:bind maxy
    modeler: modeler
    showHelp: true
}

if (not (mode.equals("view"))) {
    modeler.unLock();
}

if (mode.equals("view")) {
    modeler.lock();
    toolbar = null;
    toolbar = ToolBar
    {
        w:49
        h:bind maxy
        modeler: modeler
        showHelp: true
        simpleMode: true
    }
    toolbar.loadProcess();
    modeler.setCurrentProcessActivities(currentActivities);
    modeler.taskInboxUrl = taskPage;
    modeler.repositUrl = repPage;
}
/**********************************************/

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
        ModelerUtils.dialog,
        //ModelerUtils.idialog,
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
    title: ##"appTitle"
    resizable: true
    scene: scene
    //style: StageStyle.TRANSPARENT    //StageStyle.UNDECORATED
}

toolbar.stage=stage;