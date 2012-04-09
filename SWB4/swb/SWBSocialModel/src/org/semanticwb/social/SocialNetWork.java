package org.semanticwb.social;

import java.util.Date;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;


public class SocialNetWork extends org.semanticwb.social.base.SocialNetWorkBase 
{
    public SocialNetWork(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }


    public void addPost(Post post)
    {
        Date date=new Date();
        int year=1900+date.getYear();
        int month=1+date.getMonth();
        String newID=this.getId()+"_"+year+"_"+month;
        //System.out.println("newID:"+newID);
        SWBModel swbModel=SWBContext.getSWBModel(post.getSemanticObject().getModel().getName());
        PostContainer postContainer=PostContainer.ClassMgr.createPostContainer(newID, swbModel);
        postContainer.addPost(post);
        postContainer.setYear(year);
        postContainer.setMonth(month);

        /*
        Iterator <PostContainer> itPostContainers=PostContainer.ClassMgr.listPostContainers();
        while(itPostContainers.hasNext())
        {
            PostContainer postCont=itPostContainers.next();
            System.out.println("postCont:"+postCont);
            Iterator <Post> itPost=postCont.listPosts();
            while(itPost.hasNext())
            {
                Post postTmp=itPost.next();
                System.out.println("post en PostContainer:"+postTmp.getId());

            }
        }
         * */

    }
}
