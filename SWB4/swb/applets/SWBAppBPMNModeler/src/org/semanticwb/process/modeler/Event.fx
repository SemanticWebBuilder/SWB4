/*
 * Event.fx
 *
 * Created on 13/02/2010, 11:28:15 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;

/**
 * @author javier.solis
 */
public-read var W_TIMER="w_timer";
public-read var W_MESSAGE="w_message";
public-read var W_CONDITINAL="w_conditinal";
public-read var W_LINK="w_link";
public-read var W_MULTIPLE="w_multiple";
public-read var W_SIGNAL="w_signal";
public-read var W_PARALLEL="w_parallel";
public-read var W_SCALATION="w_scalation";
public-read var W_ERROR="w_error";
public-read var W_COMPENSATION="w_compensation";
public-read var W_CANCELATION="w_cancelation";

public-read var B_MESSAGE="b_message";
public-read var B_ERROR="b_error";
public-read var B_CANCELATION="b_cancelation";
public-read var B_COMPENSATION="b_compensation";
public-read var B_SIGNAL="b_signal";
public-read var B_MULTIPLE="b_multiple";
public-read var B_SCALATION="b_scalation";
public-read var B_TERMINATION="b_termination";
public-read var B_LINK="b_link";

public class Event extends FlowNode
{
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale
    protected var scaleOff:Number=0;                  //scale offset

    protected var colorAdjust=ColorAdjust
    {
        hue:0
        brightness:0
        contrast:1
        saturation:0
    }

    public var message=ImageView
    {
        x: bind x-ix-.5;
        y: bind y-iy-.5;
        opacity: o;
        //smooth: true;
        scaleX: bind is+scaleOff;
        scaleY: bind is+scaleOff;
        effect: colorAdjust;
    };

    public override function create(): Node
    {
        initializeCustomNode();
        cursor=Cursor.HAND;
        w=30;
        h=30;

        shape= Circle
        {
            centerX: bind x
            centerY: bind y
            radius: bind w/2
            //styleClass: "event"
            style: Styles.style_event
            //smooth:true;
        };

        setType(type);

        return Group
        {
            content: [
                shape,
                //text,
                message
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if(type.equals(W_TIMER))
        {
            message.image=Styles.ICO_EVENT_W_TIMER;
            ix=11;
            iy=11;
            is=1;
        }else if(type.equals(W_LINK))
        {
            message.image=Styles.ICO_EVENT_W_LINK;
            ix=10;
            iy=10;
            is=0.9;
        }else if(type.equals(W_MESSAGE))
        {
            message.image=Styles.ICO_EVENT_W_MESSAGE;
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(W_CONDITINAL))
        {
            message.image=Styles.ICO_EVENT_W_CONDITINAL;
            ix=8;
            iy=9;
            is=1;
        }else if(type.equals(W_SIGNAL))
        {
            message.image=Styles.ICO_EVENT_W_SIGNAL;
            ix=10;
            iy=11;
            is=1.1;
        }else if(type.equals(W_MULTIPLE))
        {
            message.image=Styles.ICO_EVENT_W_MULTIPLE;
            ix=11;
            iy=12;
            is=1;
        }else if(type.equals(W_PARALLEL))
        {
            message.image=Styles.ICO_EVENT_W_PARALLEL;
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(W_SCALATION))
        {
            message.image=Styles.ICO_EVENT_W_SCALATION;
            ix=9;
            iy=12;
            is=1;
        }else if(type.equals(W_ERROR))
        {
            message.image=Styles.ICO_EVENT_W_ERROR;
            ix=10;
            iy=10;
            is=1;
        }else if(type.equals(W_COMPENSATION))
        {
            message.image=Styles.ICO_EVENT_W_COMPENSATION;
            ix=11;
            iy=6;
            is=1;
        }else if(type.equals(W_CANCELATION))
        {
            message.image=Styles.ICO_EVENT_W_CANCELATION;
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(B_LINK))
        {
            message.image=Styles.ICO_EVENT_B_LINK;
            ix=10;
            iy=10;
            is=0.9;
        }else if(type.equals(B_MESSAGE))
        {
            message.image=Styles.ICO_EVENT_B_MESSAGE;
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(B_SIGNAL))
        {
            message.image=Styles.ICO_EVENT_B_SIGNAL;
            ix=10;
            iy=11;
            is=1.1;
        }else if(type.equals(B_MULTIPLE))
        {
            message.image=Styles.ICO_EVENT_B_MULTIPLE;
            ix=11;
            iy=12;
            is=1;
        }else if(type.equals(B_SCALATION))
        {
            message.image=Styles.ICO_EVENT_B_SCALATION;
            ix=9;
            iy=12;
            is=1;
        }else if(type.equals(B_ERROR))
        {
            message.image=Styles.ICO_EVENT_B_ERROR;
            ix=10;
            iy=10;
            is=1;
        }else if(type.equals(B_COMPENSATION))
        {
            message.image=Styles.ICO_EVENT_B_COMPENSATION;
            ix=11;
            iy=6;
            is=1;
        }else if(type.equals(B_CANCELATION))
        {
            message.image=Styles.ICO_EVENT_B_CANCELATION;
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(B_TERMINATION))
        {
            message.image=Styles.ICO_EVENT_B_TERMINATION;
            ix=8;
            iy=8;
            is=1.1;
        }else
        {
             message.visible=false;
        }

    }

}
