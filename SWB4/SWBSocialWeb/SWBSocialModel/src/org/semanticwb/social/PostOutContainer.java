package org.semanticwb.social;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas. De acuerdo al PostOut, puede buscar si este existe en la clase PostListenerContainerBase, si si existe, quiere decir que es un PostOut que se origino mediante la contestación de un PostIn, si no existe en esa clase, es que es un PostOut que se origino como nuevo, sin que haya sido contestación de un PostIn. 
   */
public class PostOutContainer extends org.semanticwb.social.base.PostOutContainerBase 
{
    public PostOutContainer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public static PostOutContainer getPostContainerByDate(Date date, SWBModel model)
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
            PostOutContainer postContainer=(PostOutContainer)obj.createGenericInstance();
            System.out.println("postContainer encontrado:"+postContainer+",year:"+postContainer.getYear()+", month:"+postContainer.getMonth());
            if(postContainer.getMonth().equals(String.valueOf(month)));
            {
                System.out.println("Regresa PostContainer:"+postContainer);
                return postContainer;
            }
        }
        System.out.println("Va ha crear nuevo postContainer..");
        PostOutContainer postContainer=PostOutContainer.ClassMgr.createPostOutContainer(model);
        postContainer.setYear(year+"");
        postContainer.setMonth(month+"");
        return postContainer;
    }
}
