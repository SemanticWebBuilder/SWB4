/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

/**
 *
 * @author Jorge Jiménez
 */
public class ContentStyles {

    private String name;
    private String font;
    private String size;
    private String color;
   
    public ContentStyles(){
        this.name=null;
        this.font=null;
        this.size=null;
        this.color=null;
    }

    //sets
    public void setName(String name){
        this.name=name;
    }
    public void setFont(String font){
        this.font=font;
    }
    public void setSize(String size){
        this.size=size;
    }
    public void setColor(String color){
        this.color=color;
    }

    //gets
    public String getName(){
        return name;
    }
    public String getFont(){
        return font;
    }
    public String getSize(){
        return size;
    }
    public String getColor(){
        return color;
    }

}