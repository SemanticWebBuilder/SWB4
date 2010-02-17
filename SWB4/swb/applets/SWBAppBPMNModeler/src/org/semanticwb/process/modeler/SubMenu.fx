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
    public var x:Number;
    public var y:Number;
    public var w:Number;
    public var h:Number;
    public var text:String;
    public var image:String;
    public var imageOver:String;
    public var imageClicked:String;
    public var subBar:Node;
    public var buttons:ImgButton[];

    var content:Node;

    var dx:Number;                        //temporal drag x
    var dy:Number;                        //temporal drag y

    var over:Boolean;
    var clicked:Boolean;

    public var action:function():Void;

    public override function create(): Node
    {
         content=Group
         {
             content:[
               ImageView {
	         image: Image {
		   url: "{__DIR__}{image}"
	         }
                 visible: bind (not over)
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
                 clicked=true;
            }
            onKeyTyped: function( e: KeyEvent ):Void
            {
                 //clicked=true;
                 action();
            }
        }

         subBar=Group
         {
             translateX:bind layoutX
             translateY:bind layoutY
             visible: bind clicked
             content: [
                Flow {
                    height: bind content.layoutBounds.height
                    width: 1000
                    content: [
                        ImageView {
                            image: Image {
                                url: "{__DIR__}{imageClicked}"
                            }
                        },
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/submenu_inicio.png"
                            }
                        },
                        buttons,
                        ImageView {
                            image: Image {
                                url: "{__DIR__}images/submenu_fin.png"
                            }
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
         }

         return content;
    }
}

