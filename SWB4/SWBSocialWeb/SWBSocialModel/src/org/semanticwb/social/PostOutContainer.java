/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
