/*
 * ToolBar.fx
 *
 * Created on 13/02/2010, 11:37:17 AM
 */

package org.semanticwb.process.modeler;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.layout.Flow;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import applets.commons.WBConnection;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

public var counter: Integer;
public var conn:WBConnection = new WBConnection(FX.getArgument(WBConnection.PRM_JSESS).toString(),FX.getArgument(WBConnection.PRM_CGIPATH).toString(),FX.getProperty("javafx.application.codebase"));


/**
 * @author javier.solis
 */

public class SubMenu extends CustomNode
{
    public var modeler:Modeler;
    public var stage:Stage;
    public var toolBar:ToolBar;
    public var x:Number;
    public var y:Number;
    public var w:Number;
    public var h:Number;
    public var offx:Number;
    public var offy:Number;
    public var text:String;
    public var image:String;
    public var imageOver:String;
    public var imageClicked:String;
    public var subBar:Node;
    public var buttons:ImgButton[];
    public var action:function():Void;

    var content:Node;
    var over:Boolean;
    var clicked:Boolean;

    //Image for start of submenu
    def imgSubMenuInicio: Image = Image {
        url: "{__DIR__}images/submenu_inicio.png"
    }

    //Image for end of submenu
    def imgSubMenuFin: Image = Image {
        url: "{__DIR__}images/submenu_fin.png"
    }

    public override function create(): Node
    {
        def imgN: Image = Image {
            url: "{__DIR__}{image}"
        }

        def imgO: Image = Image {
            url: "{__DIR__}{imageOver}"
        }

        def imgC: Image = Image {
            url: "{__DIR__}{imageClicked}"
        }
        
         content=Group
         {
             content:[
               ImageView {
	         image: imgN
                 smooth: false
                 visible: bind (not over)
               },
               ImageView {
	         image: imgO
                 smooth: false
                 visible: bind over
               }
            ]
            onMouseEntered: function( e: MouseEvent ):Void
            {
                 over=true;

                 if(toolBar!=null)
                 {
                     ModelerUtils.startToolTip(text, toolBar.x+layoutX, toolBar.y+layoutY+layoutBounds.height);
                 }else
                 {
                     ModelerUtils.startToolTip(text, layoutX, layoutY+layoutBounds.height);
                 }
            }
            onMouseExited: function( e: MouseEvent ):Void
            {
                 over=false;
                 ModelerUtils.stopToolTip();
            }
            onMousePressed: function( e: MouseEvent ):Void
            {
                 if(modeler.tempNode==null)modeler.disablePannable=true;
                 ModelerUtils.clickedNode=this;
                 clicked=true;
            }
            onMouseReleased: function( e: MouseEvent ):Void
            {
                if(modeler.tempNode==null)modeler.disablePannable=false;
                ModelerUtils.clickedNode=null;
            }
            onKeyTyped: function( e: KeyEvent ):Void
            {
                 action();
            }
        }

         subBar=Group
         {
             layoutX:bind layoutX + offx
             layoutY:bind layoutY + offy
             visible: bind clicked
             content: [
                Flow {
                    height: bind content.layoutBounds.height
                    width: 1000
                    vertical:true
                    content: [
                        ImageView {
                            image: imgC
                            smooth: false
                        },
                        ImageView {
                            image: imgSubMenuInicio
                            smooth: false
                        },
                        buttons,
                        ImageView {
                            image: imgSubMenuFin
                            smooth: false
                        }
                    ]
                }
             ]
             onMouseExited: function( e: MouseEvent ):Void
             {
                 content.requestFocus();
                 clicked=false;
             }
             cursor:Cursor.HAND;
         };

         for(button in buttons)
         {
            button.subMenu=this;
            button.toolBar=toolBar;
         }

         return content;
    }
}