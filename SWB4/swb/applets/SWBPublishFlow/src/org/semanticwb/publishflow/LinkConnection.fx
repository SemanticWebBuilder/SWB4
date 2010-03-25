/*
 * LinkConnection.fx
 *
 * Created on 4/03/2010, 04:13:39 PM
 */

package org.semanticwb.publishflow;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.PathElement;
import java.lang.Void;

/**
 * @author victor.lorenzana
 */

public class LinkConnection extends ConnectionObject {

    public var users: String[];
    public var roles: String[];
    public var type: String;
    public var authorized:Boolean;
    public var published:Boolean;
    public var button:ImgButton;
    public override function create(): Node {

        var group:Group=super.create() as Group;
        button=ImgButton
        {
            text: "Edit"
            image: "images/edit_task_1.png"
            imageOver: "images/edit_task_1.png"
            scaleX:0.5
            scaleY:0.5
            translateX:bind getEditX(elements)
            translateY:bind getEditY(elements)
            opacity:0.8
            action:function(): Void
            {
                edit();
            }

        }
        insert button into group.content;
        return group;
    }
    public function edit() : Void
    {

    }

    function getEditX(elements:PathElement[]) : Number
    {
        var size:Integer=sizeof elements;                
        if(size>=4)
        {
            if(elements[1] instanceof LineTo and elements[2] instanceof LineTo)
            {
                var lineto1:LineTo=elements[1] as LineTo;
                var lineto2:LineTo=elements[2] as LineTo;
                if(lineto1.x-lineto2.x>0)
                {
                    lineto2.x+((lineto1.x-lineto2.x)/2)-20
                }
                else if(lineto1.x-lineto2.x<0)
                {
                    lineto1.x+((lineto2.x-lineto1.x)/2)-20
                }
                else
                {
                    lineto2.x-20
                }


            }
            else
            {
                pinter1.x-20
            }
        }
        else
        {
            pinter1.x-20
        }


    }

    function getEditY(elements:PathElement[]) : Number
    {
        var size:Integer=sizeof elements;
        if(size>=4)
        {
            if(elements[1] instanceof LineTo and elements[2] instanceof LineTo)
            {
                var lineto1:LineTo=elements[1] as LineTo;
                var lineto2:LineTo=elements[2] as LineTo;
                if(lineto1.y-lineto2.y>0)
                {
                    lineto2.y+((lineto1.y-lineto2.y)/2)-20
                }
                else if(lineto1.y-lineto2.y<0)
                {
                    lineto1.y+((lineto2.y-lineto1.y)/2)-20
                }
                else
                {
                    lineto2.y-20
                }


            }
            else
            {
                pinter1.y-20
            }
        }
        else
        {
            pinter1.y-20
        }
    }
}
