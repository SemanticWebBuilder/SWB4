package org.semanticwb.social;

import java.util.Iterator;


public class Facebook extends org.semanticwb.social.base.FacebookBase 
{
    Message msg=null;
    Photo photo=null;

    public Facebook(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    
   public void postMsg(Message message) {
        addPost(message);
        this.msg=message;
        if(msg!=null)
        {
            System.out.println("Mensaje de Facebook:"+msg.getMsg_Text());
        }
    }

    public void postPhoto(Photo photo) {
        addPost(photo);
        System.out.println("Twitter login:"+getLogin());
        System.out.println("Twitter Passw:"+getPassword());
        System.out.println("Twitter SK:"+getSecreatKey());
        this.photo=photo;
        if(photo!=null)
        {
            System.out.println("Mensaje de Photo de Facebook:"+photo.getComment());
            System.out.println("Photo de Facebook:"+photo.getPhoto());
        }
    }
}
