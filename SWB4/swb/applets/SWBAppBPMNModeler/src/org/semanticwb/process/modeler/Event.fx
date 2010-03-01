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

public class Event extends FlowObject
{
    public var type:String;

    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale

    public var message=ImageView
    {
        x: bind x-ix;
        y: bind y-iy;
        opacity: o;
        smooth: true;
        scaleX: bind is;
        scaleY: bind is;
//        effect: ColorAdjust
//        {
//            hue:0.5
//        }
    };

    public override function create(): Node
    {
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
            smooth:true;
        };

        setType(type);

        return Group
        {
            content: [
                shape,
                text,
                message,
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
            effect: Styles.dropShadow
        };
    }

    public function setType(type:String):Void
    {
        if(type.equals(W_TIMER))
        {
            message.image=Styles.msg_timer;
            ix=11;
            iy=11;
            is=0.8;
        }else if(type.equals(W_LINK))
        {
            message.image=Styles.msg_link;
            ix=10;
            iy=10;
            is=0.8;
        }else if(type.equals(W_MESSAGE))
        {
            message.image=Styles.msg_message;
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(W_CONDITINAL))
        {
            message.image=Styles.msg_rule;
            ix=8;
            iy=9;
            is=0.9;
        }else if(type.equals(W_MULTIPLE))
        {
            message.image=Styles.msg_multiple;
            ix=12;
            iy=12;
            is=0.8;
        }
    }

}
