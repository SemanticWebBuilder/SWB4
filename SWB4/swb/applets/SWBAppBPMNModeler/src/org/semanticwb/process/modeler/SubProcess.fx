/*
 * SubProcess.fx
 *
 * Created on 13/02/2010, 11:21:05 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Alert;

/**
 * @author javier.solis
 */

public class SubProcess extends Activity
{
    var ix:Number;                          //offset imagen x
    var iy:Number;                          //offset imagen x
    var is:Number=1;                        //image scale

    public var message=ImageView
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
        containerable=true;
        initializeCustomNode();
        cursor=Cursor.HAND;
        w=100;
        h=60;

        setType(type);

        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y - h/6
            width: bind w
            height: bind h/2
        }

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

        var trans;
        if(type.equals(TYPE_TRANSACTION))
        {
            trans=Rectangle
            {
                x: bind x-w/2+3
                y: bind y-h/2+3
                width: w-6
                height: h-6
                style: Styles.style_task
                //smooth:true;
            };
        }

        return Group
        {
            content: [
                shape, trans, text, Rectangle
                {
                    x: bind x-7
                    y: bind y+7
                    width: 14
                    height: 14
                    style: Styles.style_message
                    //smooth:true;
                }, Line{
                    startX: bind x-5
                    startY: bind y+14
                    endX: bind x+5
                    endY: bind y+14
                    style: Styles.style_message
                    //smooth:true;
                }, Line{
                    startX: bind x
                    startY: bind y+14-5
                    endX: bind x
                    endY: bind y+14+5
                    style: Styles.style_message
                    //smooth:true;
                }, message
            ]
            scaleX: bind s;
            scaleY: bind s;
            effect: Styles.dropShadow
            visible:bind canView()
        };
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        if(type.equals(TYPE_ADHOC))
        {
            message.image=Styles.ICO_TASK_ADHOC;
            ix=-message.image.width/2-15;
            iy=h/2-message.image.height-11;
            is=1;
        }else if(type.equals(TYPE_COMPENSATION))
        {
            message.image=Styles.ICO_EVENT_W_COMPENSATION;
            ix=-message.image.width/2-19;
            iy=h/2-message.image.height-9;
            is=1;
        }else if(type.equals(TYPE_LOOP))
        {
            message.image=Styles.ICO_TASK_LOOP;
            ix=-message.image.width/2-15;
            iy=h/2-message.image.height-10;
            is=1;
        }else if(type.equals(TYPE_MULTIPLE))
        {
            message.image=Styles.ICO_TASK_MULTIPLE;
            ix=-message.image.width/2-16;
            iy=h/2-message.image.height-8;
            is=1;
        }else if(type.equals(TYPE_TRANSACTION))
        {
             message.visible=false;
//            message.image=Styles.ICO_TASK_USER;
//            ix=-w/2+5;
//            iy=-h/2+3;
//            is=1;
        }else
        {
            message.visible=false;
        }
    }

    override public function remove(validate:Boolean)
    {
        if(not validate or sizeof containerChilds == 0 or Alert.confirm("Remove {this}", "Are you sure you want to delete \"{this.title}\" {this}?"))
        {
            for(child in containerChilds)
            {
                child.remove(false);
            }
            delete containerChilds;

            super.remove(validate);
        }
    }

}