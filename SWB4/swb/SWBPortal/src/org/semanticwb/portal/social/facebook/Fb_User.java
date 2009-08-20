/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.social.facebook;

/**
 *
 * @author jorge.jimenez
 */
public class Fb_User {

    String name=null;
    String pic=null;
    String city=null;
    String state=null;
    String country=null;
    String profile_url=null;
    String pic_with_logo=null;
    String birthday=null;

    public void setName(String name){
        this.name=name;
    }
    public void setPic(String pic){
        this.pic=pic;
    }
    public void setPic_with_logo(String pic_with_logo){
        this.pic_with_logo=pic_with_logo;
    }
    public void setCity(String city){
        this.city=city;
    }
    public void setState(String state){
        this.state=state;
    }
    public void setCountry(String country){
        this.country=country;
    }
    public void setProfile_url(String profile_url){
        this.profile_url=profile_url;
    }
    public void setBirthday(String birthday){
        this.birthday=birthday;
    }

    //gets

    public String getName(){
        return name;
    }

    public String getPic(){
        return pic;
    }

    public String getPic_with_logo(){
        return pic_with_logo;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getCountry(){
        return country;
    }

    public String getProfile_url(){
        return profile_url;
    }

    public String getBirthday(){
        return birthday;
    }
}
