/*
 * Task.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.geometry.VPos;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import org.semanticwb.process.modeler.ModelerUtils;

/**
 * @author javier.solis
 */

public def TYPE_USER="user";
public def TYPE_RULE="rule";
public def TYPE_SERVICE="service";
public def TYPE_SCRIPT="script";
public def TYPE_MANUAL="manual";
public def TYPE_SEND="send";
public def TYPE_RECEIVE="receive";
public class Task extends Activity
{
    var icons: ImageView[];
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale

    def adjust: ColorAdjust = ColorAdjust {
        hue: -0.83
        brightness: -0.07
        contrast: 0.25
        saturation: 1
    }

    def imgLoop = ImageView {
        image: Image {
            url: "{__DIR__}images/n_ciclo.png"
        }
        effect: adjust
    }

    def imgMulti = ImageView {
        image: Image {
            url: "{__DIR__}images/n_objeto.png"
        }
        effect: adjust
    }

    def imgComp = ImageView {
        image: Image {
            url: "{__DIR__}images/n_compensa_b.png"
        }
        effect: adjust
    }

    var modifiers: HBox = HBox {
        nodeVPos: VPos.CENTER
        translateX: bind shape.boundsInLocal.minX + (shape.boundsInLocal.width - modifiers.boundsInLocal.width) / 2
        translateY: bind shape.boundsInLocal.minY + shape.boundsInLocal.height - (modifiers.boundsInLocal.height + 6)
        spacing: 5
        content: bind icons
    }

    protected var message=ImageView
    {
        x: bind shape.boundsInLocal.minX+3;
        y: bind shape.boundsInLocal.minY+3;
        scaleX: bind is;
        scaleY: bind is;
        effect: adjust
    }

    var loop=bind isLoop on replace
    {
        setModifier(TYPE_LOOP, isLoop);
    }

    var compensation=bind isForCompensation on replace
    {
        setModifier(TYPE_COMPENSATION, isForCompensation);
    }

    var multiple=bind isMultiInstance on replace
    {
        setModifier(TYPE_MULTIPLE, isMultiInstance);
    }

    public override function create(): Node
    {
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
            styleClass: "task"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        }

        var actions: MenuItem[] = [
            MenuItem {
                caption: ##"actMultiInstance";
                status: bind if (isMultiInstance) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                action: function (e: MouseEvent) {
                    this.setModifier(TYPE_MULTIPLE, not isMultiInstance);
                    ModelerUtils.popup.hide();
                }
            },
            MenuItem {
                caption: ##"actLoop";
                status: bind if (isLoop) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                action: function (e: MouseEvent) {
                    this.setModifier(TYPE_LOOP, not isLoop);
                    ModelerUtils.popup.hide();
                }
            },
            MenuItem {
                caption: ##"actCompensa";
                status: bind if (isForCompensation) MenuItem.STATUS_SELECTED else MenuItem.STATUS_ENABLED
                action: function (e: MouseEvent) {
                    this.setModifier(TYPE_COMPENSATION, not isForCompensation);
                    ModelerUtils.popup.hide();
                }
            },
//            Action {isSeparator: true},
//            Action {
//                label: "Convertir en subproceso"
//                status: MenuItem.STATUS_ENABLED
//                action: function (e: MouseEvent) {
//                    var _title = title;
//                    //crear nuevo elemento
//                    var sp = SubProcess {
//                        modeler: modeler
//                        title: _title
//                        uri:"new:subprocess:{this.modeler.toolBar.counter++}"
//                    }
//                    //pasar las entradas al nuevo elemento
//                    for(ele in getInputConnectionObjects()) {
//                        ele.end = sp;
//                    }
//
//                    for (ele in getOutputConnectionObjects()) {
//                        ele.ini = sp;
//                    }
//
//                    sp.x = x;
//                    sp.dpx = dpx;
//                    sp.dpy = dpy;
//                    sp.dx = dx;
//                    sp.dy = dy;
//                    sp.layoutX = layoutX;
//                    sp.layoutY = layoutY;
//                    sp.scaleX = scaleX;
//                    sp.scaleY = scaleY;
//                    sp.scaleZ = scaleZ;
//                    sp.layoutInfo = layoutInfo;
//                    sp.y = y;
//                    sp.container = container;
//                    sp.w = w;
//                    sp.h = h;
//                    sp.graphParent = graphParent;
//                    modeler.add(sp);
//                    remove(true);
//                }
//            },
            MenuItem {isSeparator: true}
        ];
        insert actions before menuOptions[0];

        getMarkers();
        
        return Group
        {
            content:[
                shape,text,message, modifiers
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
        } else if (type.equals(TYPE_SERVICE)) {
            message.styleClass = "modifierService";
            ix = -w / 2 + 3;
            iy = -h / 2;
            is = 0.8;
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
        } else if(type.equals(TYPE_SEND)) {
            message.styleClass = "modifierMessageThrow";
            ix = -w / 2 + 5;
            iy = -h / 2 + 3;
            is = 0.8;
        } else if(type.equals(TYPE_RECEIVE)) {
            message.styleClass = "modifierMessageCatch";
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
        getMarkers();
    }

    function getMarkers() : Void {
        delete icons;

        if (isLoop) {
            insert imgLoop into icons;
        } else if (isMultiInstance) {
            insert imgMulti into icons;
        }

        if (isForCompensation) {
            insert imgComp into icons;
        }
    }
}