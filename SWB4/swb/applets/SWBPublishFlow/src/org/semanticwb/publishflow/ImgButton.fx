/*
 * ImgButton.fx
 *
 * Created on 26/02/2010, 12:27:17 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.CustomNode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import org.semanticwb.publishflow.ModelerUtils;

/**
 * @author victor.lorenzana
 */

public class ImgButton extends CustomNode
{
    public var text:String;
    public var image:String;
    public var imageOver:String;
    public var over:Boolean;
    public var content:Node;
    public var subMenu:SubMenu;
    public var toolBar:ToolBar;

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
                 if(subMenu!=null)
                 {
                     if(toolBar!=null)
                     {
                         ModelerUtils.startToolTip(text, toolBar.x+layoutX, toolBar.y+subMenu.layoutY+layoutBounds.height);
                     }else
                     {
                         ModelerUtils.startToolTip(text, layoutX, subMenu.layoutY+layoutBounds.height);
                     }
                 }else
                 {
                     if(toolBar!=null)
                     {
                         ModelerUtils.startToolTip(text, toolBar.x+layoutX, toolBar.y+layoutY+layoutBounds.height);
                     }else
                     {
                        ModelerUtils.startToolTip(text, layoutX, layoutY+layoutBounds.height);
                     }
                 }
            }
            onMouseExited: function( e: MouseEvent ):Void
            {
                 over=false;
                 ModelerUtils.stopToolTip();
            }
            onMousePressed: function( e: MouseEvent ):Void
            {
                 content.requestFocus();
                 subMenu.action=this.action;
                 ModelerUtils.clickedNode=this;
                 action();
            }
            onMouseReleased: function( e: MouseEvent ):Void
            {
                ModelerUtils.clickedNode=null;
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


