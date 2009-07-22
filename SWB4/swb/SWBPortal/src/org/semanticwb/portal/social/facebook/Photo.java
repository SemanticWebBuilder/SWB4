/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.social.facebook;

/**
 *
 * @author Jorge Jiménez
 */
public class Photo {
    
    String pid=null;
    String aid=null;
    String owner=null;
    String src_small=null;
    String src_big=null;
    String src=null;
    String link=null;
    String caption=null;
    String created=null;
    String modified=null;

    public void setPid(String pid){
        this.pid=pid;
    }

    public void setAid(String aid){
        this.aid=aid;
    }

    public void setOwner(String owner){
        this.owner=owner;
    }

    public void setSrc_small(String src_small){
        this.src_small=src_small;
    }

    public void setSrc_big(String src_big){
        this.src_big=src_big;
    }

    public void setSrc(String src){
        this.src=src;
    }

    public void setLink(String link){
        this.link=link;
    }

    public void setCaption(String caption){
        this.caption=caption;
    }
    public void setCreated(String created){
        this.created=created;
    }
    public void setModified(String modified){
        this.modified=modified;
    }

    //Sets

    public String getPid(){
        return pid;
    }

    public String getAid(){
        return aid;
    }

    public String getOwner(){
        return owner;
    }

    public String getSrc_small(){
        return src_small;
    }

    public String getSrc_big(){
        return src_big;
    }

    public String getSrc(){
        return src;
    }

    public String getLink(){
        return link;
    }

    public String getCaption(){
        return caption;
    }

    public String getCreated(){
        return created;
    }

    public String getModified(){
        return modified;
    }

}
