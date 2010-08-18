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
import javafx.scene.input.MouseEvent;

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
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale

    protected var message=ImageView
    {
        x: bind x+ix;
        y: bind y+iy;
        scaleX: bind is;
        scaleY: bind is;

        effect: ColorAdjust
        {
            hue:-0.83
            brightness:-0.07
            contrast:0.25
            saturation:1
        }
    };

    public override function create(): Node
    {
        resizeable=true;
        initializeCustomNode();
        w=100;
        h=60;

        setType(type);

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            styleClass: "task"
            onKeyPressed: onKeyPressed
        };

        return Group
        {
            content: [
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
            is = 1;
        } else if(type.equals(TYPE_SEND)) {
            message.styleClass = "modifierMessageThrow";
            ix = -w / 2 + 5;
            iy = -h / 2 + 3;
            is = 1;
        } else if(type.equals(TYPE_RECEIVE)) {
            message.styleClass = "modifierMessageCatch";
            ix = -w / 2 + 5;
            iy = -h / 2 + 3;
            is = 1;
        } else {
            message.visible = false;
        }
    }
}