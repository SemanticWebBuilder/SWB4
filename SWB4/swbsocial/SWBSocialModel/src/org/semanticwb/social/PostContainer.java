package org.semanticwb.social;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


public class PostContainer extends org.semanticwb.social.base.PostContainerBase 
{
    public PostContainer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static PostContainer getPostContainerByDate(Date date, SWBModel model)
    {
        //
        System.out.println("Borrado de todos los PostContainers...-No baorraraaaa");
        /*
        Iterator <PostContainer> itPostC=PostContainer.ClassMgr.listPostContainers(model);
        while(itPostC.hasNext())
        {
            PostContainer postC=itPostC.next();
            postC.remove();
        }
         * */

        System.out.println("Termina Borrado de todos los PostContainers...");
        //

        int year=1900+(date.getYear());
        int month=1+(date.getMonth());
        System.out.println("year pa buscar:"+year);
        System.out.println("month pa buscar:"+month);
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(social_year, year+""); //No encuentra
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            PostContainer postContainer=(PostContainer)obj.createGenericInstance();
            System.out.println("postContainer encontrado:"+postContainer+",year:"+postContainer.getYear()+", month:"+postContainer.getMonth());
            if(postContainer.getMonth().equals(String.valueOf(month)));
            {
                System.out.println("Regresa PostContainer:"+postContainer);
                return postContainer;
            }
        }
        System.out.println("Va ha crear nuevo postContainer..");
        PostContainer postContainer=PostContainer.ClassMgr.createPostContainer(model);
        postContainer.setYear(year+"");
        postContainer.setMonth(month+"");
        return postContainer;
    }

}
