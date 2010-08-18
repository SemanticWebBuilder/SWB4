/*
 * SubProcess.fx
 *
 * Created on 13/02/2010, 11:21:05 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Alert;
import javafx.scene.input.MouseEvent;

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
            styleClass: "task"
            onKeyPressed: onKeyPressed
        };

        var trans;
        if(type.equals(TYPE_TRANSACTION))
        {
            trans=Rectangle
            {
                x: bind x-w/2+3
                y: bind y-h/2+3
                width: bind w-6
                height: bind h-6
                stroke: bind shape.stroke
                styleClass: "task"
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
                    styleClass: "modifierCollapsed"
                    id: "rect"
                }, Line{
                    startX: bind x-5
                    startY: bind y+14
                    endX: bind x+5
                    endY: bind y+14
                    styleClass: "modifierCollapsed"
                    id: "rect"
                }, Line{
                    startX: bind x
                    startY: bind y+14-5
                    endX: bind x
                    endY: bind y+14+5
                    styleClass: "modifierCollapsed"
                    id: "rect"
                }, message
            ]
            scaleX: bind s;
            scaleY: bind s;
            visible:bind canView()            
        };
    }

    override var onMouseClicked = function (e: MouseEvent): Void {
        if (e.button == e.button.PRIMARY) {
            if (e.clickCount >= 2) {
                if (e.sceneX > text.boundsInParent.minX and e.sceneX < text.boundsInParent.maxX) {
                    if (e.sceneY > text.boundsInParent.minY and e.sceneY < text.boundsInParent.maxY) {
                        text.startEditing()
                    } else {
                        if(containerable)
                        {
                            modeler.containerElement=this;
                        }
                    }

                }
            }
        }
    }

    public override function setType(type:String):Void
    {
        super.setType(type);
        if(type.equals(TYPE_ADHOC))
        {
            message.styleClass =  "modifierAdhoc";
            ix=-message.image.width/2-15;
            iy=h/2-message.image.height-11;
            is=1;
        }else if(type.equals(TYPE_COMPENSATION))
        {
            message.styleClass =  "modifierComp";
            ix=-message.image.width/2-19;
            iy=h/2-message.image.height-9;
            is=1;
        }else if(type.equals(TYPE_LOOP))
        {
            message.styleClass =  "modifierLoop";
            ix=-message.image.width/2-15;
            iy=h/2-message.image.height-10;
            is=1;
        }else if(type.equals(TYPE_MULTIPLE))
        {
            message.styleClass =  "modifierMult";
            ix=-message.image.width/2-16;
            iy=h/2-message.image.height-8;
            is=1;
        }else if(type.equals(TYPE_TRANSACTION))
        {
             message.visible=false;
        }else
        {
            message.visible=false;
        }
    }

    override public function remove(validate:Boolean)
    {
        //TODO: Internacionalizar mensaje
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