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
public-read var CATCH_TIMER="w_timer";
public-read var CATCH_MESSAGE="w_message";
public-read var CATCH_RULE="w_conditinal";
public-read var CATCH_LINK="w_link";
public-read var CATCH_MULTIPLE="w_multiple";
public-read var CATCH_SIGNAL="w_signal";
public-read var CATCH_PARALLEL="w_parallel";
public-read var CATCH_SCALATION="w_scalation";
public-read var CATCH_ERROR="w_error";
public-read var CATCH_COMPENSATION="w_compensation";
public-read var CATCH_CANCELATION="w_cancelation";

public-read var THROW_MESSAGE="b_message";
public-read var THROW_ERROR="b_error";
public-read var THROW_CANCELATION="b_cancelation";
public-read var THROW_COMPENSATION="b_compensation";
public-read var THROW_SIGNAL="b_signal";
public-read var THROW_MULTIPLE="b_multiple";
public-read var THROW_SCALATION="b_scalation";
public-read var THROW_TERMINATION="b_termination";
public-read var THROW_LINK="b_link";

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
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y + 30
            width: bind w + 60
            height: bind h
        }

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
                text,
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
        if(type.equals(CATCH_TIMER))
        {
            message.image=Styles.ICO_EVENT_W_TIMER;
            ix=11;
            iy=11;
            is=1;
        }else if(type.equals(CATCH_LINK))
        {
            message.image=Styles.ICO_EVENT_W_LINK;
            ix=10;
            iy=10;
            is=0.9;
        }else if(type.equals(CATCH_MESSAGE))
        {
            message.image=Styles.ICO_EVENT_W_MESSAGE;
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(CATCH_RULE))
        {
            message.image=Styles.ICO_EVENT_W_CONDITINAL;
            ix = 9.5;
            iy = 9.5;
            is = 1;
        }else if(type.equals(CATCH_SIGNAL))
        {
            message.image=Styles.ICO_EVENT_W_SIGNAL;
            ix=10;
            iy=11;
            is=1.1;
        }else if(type.equals(CATCH_MULTIPLE))
        {
            message.image=Styles.ICO_EVENT_W_MULTIPLE;
            ix=11;
            iy=12;
            is=1;
        }else if(type.equals(CATCH_PARALLEL))
        {
            message.image=Styles.ICO_EVENT_W_PARALLEL;
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(CATCH_SCALATION))
        {
            message.image=Styles.ICO_EVENT_W_SCALATION;
            ix=9;
            iy=12;
            is=1;
        }else if(type.equals(CATCH_ERROR))
        {
            message.image=Styles.ICO_EVENT_W_ERROR;
            ix=10;
            iy=10;
            is=1;
        }else if(type.equals(CATCH_COMPENSATION))
        {
            message.image=Styles.ICO_EVENT_W_COMPENSATION;
            ix=11;
            iy=6;
            is=1;
        }else if(type.equals(CATCH_CANCELATION))
        {
            message.image=Styles.ICO_EVENT_W_CANCELATION;
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(THROW_LINK))
        {
            message.image=Styles.ICO_EVENT_B_LINK;
            ix=10;
            iy=10;
            is=0.9;
        }else if(type.equals(THROW_MESSAGE))
        {
            message.image=Styles.ICO_EVENT_B_MESSAGE;
            ix=9;
            iy=8;
            is=1;
        }else if(type.equals(THROW_SIGNAL))
        {
            message.image=Styles.ICO_EVENT_B_SIGNAL;
            ix=10;
            iy=11;
            is=1.1;
        }else if(type.equals(THROW_MULTIPLE))
        {
            message.image=Styles.ICO_EVENT_B_MULTIPLE;
            ix=11;
            iy=12;
            is=1;
        }else if(type.equals(THROW_SCALATION))
        {
            message.image=Styles.ICO_EVENT_B_SCALATION;
            ix=9;
            iy=12;
            is=1;
        }else if(type.equals(THROW_ERROR))
        {
            message.image=Styles.ICO_EVENT_B_ERROR;
            ix=10;
            iy=10;
            is=1;
        }else if(type.equals(THROW_COMPENSATION))
        {
            message.image=Styles.ICO_EVENT_B_COMPENSATION;
            ix=11;
            iy=6;
            is=1;
        }else if(type.equals(THROW_CANCELATION))
        {
            message.image=Styles.ICO_EVENT_B_CANCELATION;
            ix=9;
            iy=9;
            is=1.1;
        }else if(type.equals(THROW_TERMINATION))
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

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link instanceof AssociationFlow and link.ini instanceof Artifact) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError11"));
        }
        return ret;
    }

}
