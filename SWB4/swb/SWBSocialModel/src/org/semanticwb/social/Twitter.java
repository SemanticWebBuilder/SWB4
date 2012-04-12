package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;


public class Twitter extends org.semanticwb.social.base.TwitterBase 
{
    Message msg=null;
    Photo photo=null;
    public Twitter(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void postMsg(Message message, HttpServletRequest request, SWBActionResponse response) {
        addPost(message);
        this.msg=message;
        if(msg!=null)
        {
            System.out.println("Mensaje de Twitter:"+msg.getMsg_Text());
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        addPost(photo);
        System.out.println("Twitter login:"+getLogin());
        System.out.println("Twitter Passw:"+getPassword());
        System.out.println("Twitter SK:"+getSecreatKey());
        this.photo=photo;
        if(photo!=null)
        {
            System.out.println("Mensaje de Photo de Twitter:"+photo.getComment());
            System.out.println("Photo de Twitter:"+photo.getPhoto());
        }
    }

}
