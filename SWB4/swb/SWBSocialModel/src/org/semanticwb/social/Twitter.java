package org.semanticwb.social;


public class Twitter extends org.semanticwb.social.base.TwitterBase 
{
    Message msg=null;
    Photo photo=null;
    public Twitter(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
    public void postPhoto() {
        Photo photo=null;
        Iterator <Post> itPost=listPosts();
        while(itPost.hasNext()){
            Post post=itPost.next();
            if(post instanceof Photo)
            {
                photo=(Photo)post;
                break;
            }
        }
    }

    public void postMsg() {
        Iterator <Post> itPost=listPosts();
        while(itPost.hasNext()){
            Post post=itPost.next();
            if(post instanceof Message)
            {
                System.out.println("Entra a poner en Twitter Msg...:"+getTitle());
                msg=(Message)post;
                System.out.println("Entra a poner en Twitter Msg-2...:"+msg.getMsg_Text());
                break;
            }
        }
    }
*/
   
    public void postMsg(Message message) {
        this.msg=message;
        if(msg!=null)
        {
            System.out.println("Mensaje de Twitter:"+msg.getMsg_Text());
        }
    }

    public void postPhoto(Photo photo) {
        System.out.println("Twitter login:"+getLogin());
        System.out.println("Twitter Passw:"+getPassword());
        System.out.println("Twitter SK:"+getSecreatKey());
        this.photo=photo;
        if(photo!=null)
        {
            System.out.println("Mensaje de Photo de Twitter:"+photo.getMsg_Text());
            System.out.println("Photo de Twitter:"+photo.getPhoto());
        }
    }
}
