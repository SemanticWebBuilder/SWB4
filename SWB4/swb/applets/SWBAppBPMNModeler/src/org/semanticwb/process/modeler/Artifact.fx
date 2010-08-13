/*
 * Artifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Polyline;

/**
 * @author javier.solis
 */

public def TYPE_MULTIPLE="multiple";
public def TYPE_INPUT="input";
public def TYPE_OUTPUT="output";

public class Artifact extends GraphicalElement
{
    protected var ix:Number;                          //offset imagen x
    protected var iy:Number;                          //offset imagen x
    protected var is:Number=1;                        //image scale

    protected var message=ImageView
    {
        x: bind x+ix;
        y: bind y+iy;
        scaleX: bind is;
        scaleY: bind is;
        visible:false;
    };

    override public function create(): Node
    {
        cursor=Cursor.HAND;
        w=55;
        h=60;
        stkw=1;
        stkwo=2;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y 
            width: bind w
            height: bind h
        }

        setType(type);

        shape=Polyline
        {
            points: [
                w-w/4,0,
                0,0,
                0,h,
                w,h,
                w,h/4,
                w-w/4,0,
            ]
            translateX:bind x-w/2
            translateY:bind y-h/2
            style:Styles.style_artifact
        };

        return Group
        {
            content: [
                shape,
                Polyline{
                    points: [
                        w-w/4,0,
                        w-w/4,h/4,
                        w,h/4,
                    ]
                    translateX:bind x-w/2
                    translateY:bind y-h/2
                    stroke:bind shape.stroke
                    strokeWidth:bind shape.strokeWidth
                },
                message,text,
            ]
            scaleX: bind s;
            scaleY: bind s;
            effect: Styles.dropShadow
            visible: bind canView()
        };
    }

    override public function mouseEntered( e: MouseEvent )
    {
        super.mouseEntered(e);
    }

    override public function mouseExited( e: MouseEvent )
    {
        super.mouseExited(e);
    }

    override public function mousePressed( e: MouseEvent )
    {
       super.mousePressed(e);
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if(type.equals(TYPE_INPUT))
        {
            message.image=Styles.ICO_EVENT_W_LINK;
            ix=-message.image.width/2;
            iy=h/2-message.image.height;
            is=.9;
        }else if(type.equals(TYPE_OUTPUT))
        {
            message.image=Styles.ICO_EVENT_B_LINK;
            ix=-message.image.width/2;
            iy=h/2-message.image.height;
            is=.9;
        }else if(type.equals(TYPE_MULTIPLE))
        {
            message.image=Styles.ICO_TASK_MULTIPLE;
            ix=-message.image.width/2;
            iy=h/2-message.image.height-3;
            is=1;
        }else
        {
             message.visible=false;
        }
    }

    public override function canAttach(parent:GraphicalElement):Boolean
    {
        var ret=false;
        if(parent instanceof Pool or parent instanceof Lane)
        {
            ret=true;
        }
        return ret;
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        if (not(link instanceof AssociationFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError3"));
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);

        if (link.ini instanceof Event) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError4"));
        }

        if (not(link instanceof AssociationFlow)) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError3"));
        } else if (link.ini instanceof Artifact) {
            ret = false;
            ModelerUtils.setErrorMessage(ModelerUtils.getLocalizedString("msgError5"));
        }
        return ret;
    }
}