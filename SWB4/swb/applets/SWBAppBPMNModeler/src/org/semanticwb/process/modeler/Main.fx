/*
 * Main.fx
 *
 * Created on 15/11/2009, 02:46:27 AM
 */

package org.semanticwb.process.modeler;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.light.DistantLight;
import javafx.scene.Cursor;

/**
 * @author javier.solis
 */

var maxx : Number = bind scene.width on replace{ modeler.organizeMap();};
var maxy : Number = bind scene.height on replace{ modeler.organizeMap();};

var lighting = Lighting{
    surfaceScale: 5
    light: DistantLight{
        azimuth: 10
        elevation: 40
    }
}

//println("dir:{__DIR__}");

var modeler:Modeler = Modeler
{
    layoutX:0 //69
    layoutY:0
    width:bind maxx
    height:bind maxy-23
    pannable:true
    cursor:Cursor.CROSSHAIR
}
modeler.load("home");
modeler.organizeMap();

var toolbar:ToolBar = ToolBar
{
    w:69
    h:bind maxy
    modeler: modeler
}

var scene : Scene = Scene {
    content: [
            modeler,
            toolbar
    ]
    width: 600
    height: 300
    stylesheets: bind Styles.stylesheets
    //fill: Color.TRANSPARENT
}

var stage : Stage = Stage {
    title: "BPMN Modeler"
    resizable: true
    scene: scene
    //style: StageStyle.TRANSPARENT //StageStyle.UNDECORATED
}

toolbar.stage=stage;