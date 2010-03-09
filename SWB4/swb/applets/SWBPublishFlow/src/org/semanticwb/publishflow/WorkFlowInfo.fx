/*
 * WorkFlowInfo.fx
 *
 * Created on 5/03/2010, 11:00:39 AM
 */

package org.semanticwb.publishflow;
import javafx.scene.CustomNode;
import javafx.scene.Group;
import javafx.scene.Node;
import org.semanticwb.publishflow.EditableText;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Stack;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Panel;
import javafx.scene.Cursor;


/**
 * @author victor.lorenzana
 */

public class WorkFlowInfo extends CustomNode{

    public var text : EditableText;    
    public var x : Number;
    public var y : Number;
    public var w : Number=100;
    public var h : Number=60;
    public var version:String="1.0";
    public var canEdit:Boolean;
    public var id_workflow:String;
    public var name:String="name workflow 1";
    public var description:String="workflow 1";
    public var description_text:EditableText;
    var chbox:CheckBox;
    public var resourceTypes:String[];
    
    public override function create(): Node
    {
        var group:Group;
        var shape : Rectangle = Rectangle {
            x: bind x
            y: bind y
            width: w
            height: h
            style: Styles.style_infoWorkflow
            opacity: 0.8
            smooth: true;
            cursor:Cursor.DEFAULT

        };
        chbox=CheckBox
        {
            selected:bind canEdit;
            text:"Permitir edición"
            blocksMouse:true
            onMousePressed:function (e:MouseEvent)
            {
                    if(e.button==MouseButton.PRIMARY)
                    {
                        canEdit=not canEdit;
                    }
            }
            cursor:Cursor.HAND;

        }

        var labelversion: Text=Text
        {
            content:bind "Versión: {version}";
            x:bind x;
            y: bind y;
        }

        var labelname: Text=Text
        {
            content:bind "Título:";
            x:bind x;
            y: bind y;
        }
        var labeldescription: Text=Text
        {
            content:bind "Descripción:";
            x:bind x;
            y: bind y;
           

        }

        text=EditableText
        {
            text: bind name with inverse
            width: bind w*3/4;
            height: bind h;
            //blocksMouse:true
            cursor:Cursor.HAND
            onMousePressed:function(e:MouseEvent)
            {
                if(e.clickCount==2 and e.button==MouseButton.PRIMARY)
                {
                    text.startEditing();

                }
            }
            
        }
        description_text=EditableText
        {
            text: bind description with inverse
            width: bind w*3/4;
            height: bind h;
            //blocksMouse:true
            cursor:Cursor.HAND
            onMousePressed:function(e:MouseEvent)
            {
                if(e.clickCount==2 and e.button==MouseButton.PRIMARY)
                {
                    description_text.startEditing();

                }
            }

        }
        group=Group
        {
           content: [Stack
           {
                content: [shape,
                        HBox
                        {
                            spacing:10
                            content: [Panel
                            {
                                width:10.0
                            },

                                    VBox
                                    {
                                        content: [Panel
                            {
                                width:10.0
                            },labelversion,HBox
                                    {
                                            content:[labelname,text]
                                            spacing:5
                                    },HBox
                                    {
                                            content:[labeldescription,description_text]
                                            spacing:5
                                    },chbox]
                                        spacing:10
                                    }

                                    ]
                        }


                        ]
           }]

                   
           
                   

           translateX:bind x;
           translateY: bind y;
           effect: Styles.dropShadow;

        }
        return group;        
    }
}
