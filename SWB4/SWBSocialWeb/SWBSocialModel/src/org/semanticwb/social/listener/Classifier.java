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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.util.ArrayList;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 * 
 * Class which creates a ClassifierThread (Thread) to classify all messages retrieving from a SocialNetwork.
 * 
 */

/*
 * Clase cuya funcionalidad es la de levantar un thread por cada mensaje que llega por medio del listener.
 */
public class Classifier {

    private Logger log = SWBUtils.getLogger(Classifier.class);

    /*
    PostIn post=null;
    public Classifier (PostIn post)
    {
        this.post=post;
        try{
            ClassifierThread classThread=new ClassifierThread(post);
            //System.out.println("THREAD CREADO:"+classThread);
            classThread.start();
            //System.out.println("classThread.isAlive():"+classThread);
        }catch(Exception e){
            log.error(e);
        }

    }
    * */
    
    /*
     * Method that creates 
     */
    public Classifier (ArrayList <ExternalPost> aListExternalPost, Stream stream, SocialNetwork socialNetwork, boolean classifyGeoLocation)
    {
        try{
            ClassifierThread classThread=new ClassifierThread(aListExternalPost, stream, socialNetwork, classifyGeoLocation);
            //System.out.println("THREAD CREADO:"+classThread);
            classThread.start();
            //System.out.println("classThread.isAlive():"+classThread);
        }catch(Exception e){
            log.error(e);
        }

    }

}
