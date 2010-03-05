/*
 * Tooltip.fx
 *
 * Created on 5/03/2010, 01:46:17 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.Stack;

/**
 * @author victor.lorenzana
 */

public class Tooltip extends CustomNode{

    public var text : String;
    public var x : Number;
    public var y : Number;
    public var w : Number=100;
    public var h : Number=15;
    public override function create(): Node
    {
        var group:Group;
         var shape : Rectangle = Rectangle {
            x: bind x
            y: bind y
            width: w
            height: h
            style: Styles.style_tooltip
            opacity: 0.5
            smooth: true;
        };
        var labelname: Text=Text
        {
            content:bind text;
            x:bind x;
            y: bind y;
        }
        group=Group
        {
            content: [Stack
            {
                content: [shape,labelname]
            }]
        }

        return group;
    }
}
