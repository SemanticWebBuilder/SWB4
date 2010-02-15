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
public-read var TIMER="timer";
public-read var MESSAGE="message";
public-read var RULE="rule";
public-read var LINK="link";
public-read var MULTIPLE="multiple";

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
        if(type.equals(TIMER))
        {
            message.image=Styles.msg_timer;
            ix=11;
            iy=11;
            is=0.8;
        }else if(type.equals(LINK))
        {
            message.image=Styles.msg_link;
            ix=10;
            iy=10;
            is=0.8;
        }else if(type.equals(MESSAGE))
        {
            message.image=Styles.msg_message;
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(RULE))
        {
            message.image=Styles.msg_rule;
            ix=8;
            iy=9;
            is=0.9;
        }else if(type.equals(MULTIPLE))
        {
            message.image=Styles.msg_multiple;
            ix=12;
            iy=12;
            is=0.8;
        }
    }

}
