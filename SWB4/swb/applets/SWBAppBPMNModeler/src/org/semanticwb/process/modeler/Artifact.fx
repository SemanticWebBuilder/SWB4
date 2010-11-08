/*
 * Artifact.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;

public def TYPE_COLLECTION="collection";
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
        setCommonMenuOptions();
        w=55;
        h=60;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x
            y:bind y 
            width: bind w
            height: bind h
            styleClass: "dataObject"
            id: "label"
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
            styleClass: "dataObject"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
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
                message,text
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible: bind canView()
        };
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        message.visible=true;
        if(type.equals(TYPE_INPUT))
        {
            message.styleClass = "modifierLinkCatch";
            ix=-25;
            iy=-30;
            is=.7;
        }else if(type.equals(TYPE_OUTPUT))
        {
            message.styleClass = "modifierLinkThrow";
            ix=-25;
            iy=-30;
            is=.7;
        }else if(type.equals(TYPE_COLLECTION))
        {
            message.styleClass = "modifierMultiInstance";
            ix=-6;
            iy=13;
            is=.8;
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