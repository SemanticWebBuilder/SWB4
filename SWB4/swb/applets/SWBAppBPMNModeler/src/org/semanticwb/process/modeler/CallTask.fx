/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.modeler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.CallManualTask;
import org.semanticwb.process.modeler.CallBusinessRuleTask;
import org.semanticwb.process.modeler.CallScriptTask;
import org.semanticwb.process.modeler.CallUserTask;

/**
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */

public def TYPE_USER="user";
public def TYPE_MANUAL="manual";
public def TYPE_SCRIPT="script";
public def TYPE_RULE="rule";
public class CallTask extends CallActivity {
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale

    def adjust: ColorAdjust = ColorAdjust {
        hue: -0.83
        brightness: -0.07
        contrast: 0.25
        saturation: 1
    }

    protected var message=ImageView
    {
        x: bind shape.boundsInLocal.minX+3;
        y: bind shape.boundsInLocal.minY+3;
        scaleX: bind is;
        scaleY: bind is;
        effect: adjust
    }

    public override function create(): Node
    {
        blocksMouse = true;
        resizeable=true;
        w=100;
        h=60;
        initializeCustomNode();

        setType(type);

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            styleClass: "globalTask"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        }

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actType"
                items: [
                    MenuItem {
                        caption: ##"actManual"
                        status: bind if (this instanceof CallManualTask) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = CallManualTask {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:callmanualtask:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actRule"
                        status: bind if (this instanceof CallBusinessRuleTask) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = CallBusinessRuleTask {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:callbusinessruletask:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actScript"
                        status: bind if (this instanceof CallScriptTask) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = CallScriptTask {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:callscripttask:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                    MenuItem {
                        caption: ##"actUser"
                        status: bind if (this instanceof CallUserTask) MenuItem.STATUS_DISABLED else MenuItem.STATUS_ENABLED
                        action: function (e: MouseEvent) {
                            ModelerUtils.popup.hide();
                            var _title = title;
                            var _desc = description;
                            //crear nuevo elemento
                            var sp = CallUserTask {
                                modeler: modeler
                                title: _title
                                description: _desc
                                uri:"new:callusertask:{this.modeler.toolBar.counter++}"
                            }
                            //pasar las entradas al nuevo elemento
                            for(ele in getInputConnectionObjects()) {
                                ele.end = sp;
                            }

                            for (ele in getOutputConnectionObjects()) {
                                ele.ini = sp;
                            }

                            sp.x = x;
                            sp.y = y;
                            sp.container = container;
                            sp.w = w;
                            sp.h = h;
                            sp.setGraphParent(getGraphParent());
                            modeler.add(sp);
                            remove(true);
                        }
                    },
                ]
            },
            MenuItem {isSeparator: true},
            MenuItem {
                caption: ##"actCopy"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = copy();
                    modeler.setCopyNode(t);
                }
            },
            MenuItem {
                caption: ##"actCut"
                action: function(e: MouseEvent) {
                    ModelerUtils.popup.hide();
                    var t = cut();
                    modeler.setCopyNode(t);
                    ModelerUtils.setResizeNode(null);
                }
            }
        ];
        insert actions before menuOptions[0];

        return Group
        {
            content:[
                shape,text,message
            ]
            scaleX: bind s
            scaleY: bind s
            visible: bind canView()
        };
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if (type.equals(TYPE_USER)) {
            message.styleClass = "modifierUser";
            ix = -w / 2 + 5;
            iy = -h / 2 + 3;
            is = 1;
        } else if(type.equals(TYPE_SCRIPT)) {
            message.styleClass = "modifierScript";
            ix = -w / 2 + 3;
            iy = -h / 2 + 1;
            is = 0.8;
        } else if(type.equals(TYPE_MANUAL)) {
            message.styleClass = "modifierManual";
            ix = -w / 2 + 5;
            iy = -h / 2 + 3;
            is = 0.8;
        } else if(type.equals(TYPE_RULE)) {
            message.styleClass = "modifierRule";
            ix = -w / 2 + 5;
            iy = -h / 2 + 3;
            is = 0.8;
        } else {
            message.visible = false;
        }
    }

    public function setModifier(modif: String, val:Boolean): Void {
        if(modif.equals(TYPE_COMPENSATION)) {
            isForCompensation = val;
        } else if(modif.equals(TYPE_LOOP)) {
            isLoop = val;

            if (isLoop) {
                if (isMultiInstance) {
                    isMultiInstance = false;
                }
            }
        } else if(modif.equals(TYPE_MULTIPLE)) {
            isMultiInstance = val;

            if (isMultiInstance) {
                if (isLoop) {
                    isLoop = false;
                }
            }
        }
    }

    override public function copy() : GraphicalElement {
        var t = CallTask {
            title: this.title
            description: this.description
            type: this.type
            modeler: this.modeler
            isLoop: this.isLoop
            isForCompensation: this.isForCompensation
            isMultiInstance: this.isMultiInstance
            container: this.container
            uri: "new:call{type}task:{modeler.toolBar.counter++}"
            x: this.x + 10
            y: this.y + 10
        }
        return t;
    }
}