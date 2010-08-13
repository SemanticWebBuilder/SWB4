/*
 * Pool.fx
 *
 * Created on 13/02/2010, 10:55:36 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.Cursor;
import javafx.scene.shape.Line;
import org.semanticwb.process.modeler.ModelerUtils;
import javafx.stage.Alert;
import org.semanticwb.process.modeler.MessageFlow;
import org.semanticwb.process.modeler.AssociationFlow;

/**
 * @author javier.solis
 */

public class Pool extends GraphicalElement
{

    var py=bind y on replace
    {
        updateSize();
    }

    public var lanes:Lane[];

    override public function create(): Node
    {
        //stkw=2;
        //stkwo=2;
        resizeable=true;
        resizeType=ResizeNode.RESIZE_A;
        cursor=Cursor.HAND;
        w=600;
        h=200;
        text=EditableText
        {
            text: bind title with inverse
            x:bind x-w/2+10
            y:bind y
            width: bind h
            height: 20
            rotate: -90
        }

        shape= Rectangle
        {
            x: bind x-w/2
            y: bind y-h/2
            width: bind w
            height: bind h
            style: Styles.style_pool
        };

        return Group
        {
            content: [
                shape,Line
                {
                    startX: bind x-w/2+20
                    startY: bind y-h/2+1
                    endX: bind x-w/2+20
                    endY: bind y+h/2-1
                    style: Styles.style_pool_line
                    stroke: bind shape.stroke
                },text,
                Group
                {
                    content:bind lanes
                    visible: bind sizeof lanes > 0
                }
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

    override public function remove(validate:Boolean)
    {
       if(not validate or sizeof graphChilds == 0 or Alert.confirm("Remove {this}", "Are you sure you want to delete \"{this.title}\" {this}?"))
       {
           super.remove(validate);
       }
    }

    public function removeLane(lane:Lane)
    {
        //println("{this} removeLane {lane}");
        delete lane from lanes;
        if(sizeof lanes==0)resizeType=ResizeNode.RESIZE_A;
        updateSize();
    }

    public function addLane():Lane
    {
        //println("{this} addLane");
        var lane=Lane
        {
            title:"Lane"
            modeler:modeler;
            uri:"new:Lane:{ToolBar.counter++}"
            w:bind w
        };
        lane.setGraphParent(this);
        lane.setContainer(getContainer());
        insert lane into lanes;
        ModelerUtils.setResizeNode(null);
        resizeType=ResizeNode.RESIZE_H;
        updateSize();
        return lane;
    }

    public override function updateSize()
    {
        var t:Number=0;
        for(lane in lanes)
        {
            t=t+lane.h;
        }
        
        if(t>0)
        {
            h=t;
        }

        var ay=y-h/2;
        for(lane in lanes)
        {
            lane.y=ay+lane.h/2;
            ay=ay+lane.h;
        }
    }



    public function getLaneByURI(uri:String):GraphicalElement
    {
        for(lane in lanes)
        {
            println("{lane.uri}={uri}");
            if(lane.uri.equals(uri))return lane;
        }
        return null;
    }

    override public function canStartLink(link:ConnectionObject) : Boolean
    {
        var ret = true;
        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage("Pool cannot have outgoing SequenceFlow");
        }        
        return ret;
    }

    override public function canEndLink(link:ConnectionObject) : Boolean
    {
        var ret = true;
        if (link instanceof SequenceFlow) {
            ret = false;
            ModelerUtils.setErrorMessage("Pool cannot have incoming SequenceFlow");
        }
        if (link instanceof MessageFlow) {
            if (link.ini.getPool() == this) {
                ret = false;
                ModelerUtils.setErrorMessage("Cannot link MessageFlow from within the Pool");
            }
        }
        return ret;
    }

}