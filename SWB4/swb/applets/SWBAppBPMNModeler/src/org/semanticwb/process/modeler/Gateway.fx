/*
 * GateWay.fx
 *
 * Created on 13/02/2010, 11:23:01 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

/**
 * @author javier.solis
 */

public class Gateway extends FlowNode
{
    public var modifier: ImageView;
    protected var colorAdjust: ColorAdjust;
    
    public override function create(): Node
    {
        initializeCustomNode();
        w=50;
        h=50;
        
        colorAdjust = ColorAdjust {
            hue: 0.25
            brightness: 0.28
            contrast: 0.25
            saturation: 1
        };

        shape= Polygon
        {
            points: [w/2,0,w,h/2,w/2,h,0,h/2]
            styleClass: "gateway"
            onKeyPressed: onKeyPressed
            onKeyReleased: onKeyReleased
        };

        return Group
        {
            content: [
                Group
                {
                    content: [
                        shape
                    ]
                    translateX: bind x - w/2
                    translateY: bind y - w/2
                    scaleX: bind s;
                    scaleY: bind s;
                }
            ]
            visible: bind canView()
        };
    }

    public override function canStartLink(link:ConnectionObject) : Boolean {
        var ret = super.canStartLink(link);
        var ci = 0;
        var co = 0;

        for(ele in getInputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                ci++;
            }
        }

        for(ele in getOutputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                co++;
            }
        }
        
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError17");
        }

        if (link instanceof SequenceFlow) {
            if (ci > 1 and co != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError18");
            }
        }
        return ret;
    }

    public override function canEndLink(link:ConnectionObject) : Boolean {
        var ret = super.canEndLink(link);
        var ci = 0;
        var co = 0;

        for(ele in getInputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                ci++;
            }
        }

        for(ele in getOutputConnectionObjects()) {
            if(ele instanceof SequenceFlow) {
                co++;
            }
        }
        
        if (link instanceof MessageFlow) {
            ret = false;
            ModelerUtils.setErrorMessage(##"msgError17");
        }

        if (link instanceof SequenceFlow) {
            if (co > 1 and ci != 0) {
                ret = false;
                ModelerUtils.setErrorMessage(##"msgError19");
            }
        }
        return ret;
    }
}