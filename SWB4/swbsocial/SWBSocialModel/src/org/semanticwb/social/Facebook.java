package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;


public class Facebook extends org.semanticwb.social.base.FacebookBase 
{
    Message msg=null;
    Photo photo=null;

    public Facebook(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }


   public void postMsg(Message message, HttpServletRequest request, SWBActionResponse response) {
        addPost(message);
        this.msg=message;
        if(msg!=null)
        {
            System.out.println("Mensaje de Facebook:"+msg.getMsg_Text());
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        addPost(photo);
        System.out.println("Twitter login:"+getLogin());
        System.out.println("Twitter Passw:"+getPassword());
        System.out.println("Twitter SK:"+getSecretKey());
        this.photo=photo;
        if(photo!=null)
        {
            System.out.println("Mensaje de Photo de Facebook:"+photo.getComment());
            System.out.println("Photo de Facebook:"+photo.getPhoto());
        }
    }

    public void postVideo(Video video, HttpServletRequest request, SWBActionResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
