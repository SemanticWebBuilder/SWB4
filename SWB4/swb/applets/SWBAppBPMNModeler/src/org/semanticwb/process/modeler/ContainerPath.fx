/*
 * ContainerPath.fx
 *
 * Created on 13/04/2010, 11:37:17 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.Flow;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.scene.shape.Rectangle;


/**
 * @author javier.solis
 */

public class ContainerPath extends CustomNode
{
    public var modeler:Modeler;
    public var x:Number;
    public var y:Number;
    public var w:Number;
    public var h:Number;
    public var flow:Flow;

    public var contents: Node[];

    var container = bind modeler.containerElement on replace
    {
        delete contents;
        var path=getText("Path:");
        insert path into contents;
        addContainer(container);
    }

    public function getText(txt:String): Text
    {
        return Text
        {
            content:txt
            font: Font {size:10 name:"Verdana"}
        };
    }


    public function addContainer(container:GraphicalElement): Void
    {
        //println("addContainer:{container}");
        var button = ContainerButton
        {
            container:container
            modeler:modeler
        }
        insert button before contents[1];
        if(container!=null)
        {
            insert getText(">") before contents[1];
            addContainer(container.container);
        }
    }

    public override function create(): Node
    {
        flow=Flow {
                    height: bind h
                    width: bind w
                    content: bind contents
                    //style:Styles.style_task
                    nodeHPos:HPos.RIGHT
        };
        var ret=Group
        {
             layoutX:bind x
             layoutY:bind y
             content: [
                Rectangle{
                    x:bind flow.boundsInLocal.minX-1
                    y:bind flow.boundsInLocal.minY-1
                    width:bind flow.boundsInLocal.width+2
                    height:bind flow.boundsInLocal.height+2
                    style:Styles.style_pool
                },
                flow
             ]
        };
        return ret;
    }
}