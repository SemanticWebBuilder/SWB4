/*
 * Task.fx
 *
 * Created on 13/02/2010, 11:19:11 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;

/**
 * @author javier.solis
 */

public-read var TYPE_USER="user";
public-read var TYPE_RULE="rule";
public-read var TYPE_SERVICE="service";
public-read var TYPE_SCRIPT="script";
public-read var TYPE_MANUAL="manual";
public-read var TYPE_SEND="send";
public-read var TYPE_RECEIVE="receive";

public class Task extends Activity
{
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale

    protected var message=ImageView
    {
        x: bind x+ix;
        y: bind y+iy;
        //smooth: true;
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
        cursor=Cursor.HAND;
        w=100;
        h=60;

        setType(type);

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            //effect: lighting
            //styleClass: "task"
            style: Styles.style_task
            //smooth:true;
        };

        return Group
        {
            content: [
                shape,text,message
            ]
            scaleX: bind s
            scaleY: bind s
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }    
    
    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if(type.equals(TYPE_ADHOC))
        {
            message.image=Styles.ICO_TASK_ADHOC;
            ix=-message.image.width/2;
            iy=h/2-message.image.height-3;
            is=1;
        }else if(type.equals(TYPE_COMPENSATION))
        {
            message.image=Styles.ICO_EVENT_W_COMPENSATION;
            ix=-message.image.width/2;
            iy=h/2-message.image.height-3;
            is=1;
        }else if(type.equals(TYPE_LOOP))
        {
            message.image=Styles.ICO_TASK_LOOP;
            ix=-message.image.width/2;
            iy=h/2-message.image.height-3;
            is=1;
        }else if(type.equals(TYPE_MULTIPLE))
        {
            message.image=Styles.ICO_TASK_MULTIPLE;
            ix=-message.image.width/2;
            iy=h/2-message.image.height-3;
            is=1;
        }else if(type.equals(TYPE_USER))
        {
            message.image=Styles.ICO_TASK_USER;
            ix=-w/2+5;
            iy=-h/2+3;
            is=1;
        }else if(type.equals(TYPE_SERVICE))
        {
            message.image=Styles.ICO_TASK_SERVICE;
            ix=-w/2+3;
            iy=-h/2;
            is=0.8;
        }else if(type.equals(TYPE_SCRIPT))
        {
            message.image=Styles.ICO_TASK_SCRIPT;
            ix=-w/2+3;
            iy=-h/2+1;
            is=0.8;
        }else if(type.equals(TYPE_MANUAL))
        {
            message.image=Styles.ICO_TASK_MANUAL;
            ix=-w/2+5;
            iy=-h/2+3;
            is=1;
        }else if(type.equals(TYPE_SEND))
        {
            message.image=Styles.ICO_EVENT_B_MESSAGE;
            ix=-w/2+5;
            iy=-h/2+3;
            is=1;
        }else if(type.equals(TYPE_RECEIVE))
        {
            message.image=Styles.ICO_EVENT_W_MESSAGE;
            ix=-w/2+5;
            iy=-h/2+3;
            is=1;
        }else
        {
            message.visible=false;
        }

    }

}
