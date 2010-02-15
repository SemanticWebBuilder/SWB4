/*
 * ImgButton.fx
 *
 * Created on 13/02/2010, 11:33:28 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.CustomNode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;

/**
 * @author javier.solis
 */

public class ImgButton extends CustomNode
{
    public var text:String;
    public var image:String;
    public var imageOver:String;
    public var over:Boolean;
    public var content:Node;

    public var action:function():Void;

    override protected function create():Node
    {
         content=Group
         {
             content:[
               ImageView {
	         image: Image {
		   url: "{__DIR__}{image}"
	         }
                 visible: bind not over
               },
               ImageView {
	         image: Image {
		   url: "{__DIR__}{imageOver}"
	         }
                 visible: bind over
               }
            ]
            onMouseEntered: function( e: MouseEvent ):Void
            {
                 over=true;
            }
            onMouseExited: function( e: MouseEvent ):Void
            {
                 over=false;
            }
            onMousePressed: function( e: MouseEvent ):Void
            {
                 content.requestFocus();
                 action();
            }
            onKeyTyped: function( e: KeyEvent ):Void
            {
                 content.requestFocus();
                 action();
            }
        }
        return content;
    }
}

