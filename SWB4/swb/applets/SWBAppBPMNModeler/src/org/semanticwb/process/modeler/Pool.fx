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
import javafx.scene.layout.Flow;
import org.semanticwb.process.modeler.ModelerUtils;

/**
 * @author javier.solis
 */

public class Pool extends GraphElement
{

    public var flow:Flow;

    public var lanes:Lane[];

    var lanesh = bind flow.boundsInLocal.height on replace
    {
        h=lanesh;
    }

    override public function create(): Node
    {
        stkw=2;
        stkwo=2;
        resizeable=true;
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

        addLane();
        addLane();
        addLane();

        flow=Flow
        {
            width:bind w
            content: bind lanes
            translateX:bind x-w/2+20
            translateY:bind y-h/2
            //hgap:10
            vgap:-1
        };

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
                    startY: bind y-h/2
                    endX: bind x-w/2+20
                    endY: bind y+h/2
                    style: Styles.style_pool_line
                },text,flow
            ]
            scaleX: bind s;
            scaleY: bind s;
            opacity: bind o;
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

    override public function remove()
    {
       super.remove();
    }

    public function removeLane(lane:Lane)
    {
        println("{this} removeLane {lane}");
        delete lane from lanes;
    }

    public function addLane()
    {
        var lane=Lane
        {
            title:"Lane"
            modeler:modeler;
            uri:"new:Lane:{ToolBar.counter++}"
            w:bind w
        };
        lane.setGraphParent(this);
        insert lane into lanes;
    }

}