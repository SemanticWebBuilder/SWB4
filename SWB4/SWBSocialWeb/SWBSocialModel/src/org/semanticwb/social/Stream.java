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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import org.semanticwb.model.Activeable;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.social.listener.ListenerMgr;


   /**
   * Clase que contendra los streams que configurados para cada usuario 
   */
public class Stream extends org.semanticwb.social.base.StreamBase 
{
    public Stream(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    static {
       //Observador de la clase "Stream", cada que haya un cambio en ella se ejecuta el siguiente código
        Stream.sclass.registerObserver(new SemanticObserver() {
            Timer timer = null;
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(Stream.social_Stream))
                {
                    Stream stream = (Stream) obj.createGenericInstance();
                    SemanticProperty semProp = (SemanticProperty) prop;;
                    //System.out.println("Stream/Accion:"+action+", streamUri:"+stream.getURI()+",semProp:"+semProp+",A ver k pex:"+Stream.social_keepAliveManager.getURI());
                    if(action.equalsIgnoreCase("CREATE"))   //Quiere decir que se esta creando una instancia de la clase nueva
                    {
                        //System.out.println("Entra a Stream/Create...");
                        ListenerMgr.createUpdateTimers(stream);
                    }else if((action.equalsIgnoreCase("SET") && (semProp.getName().equals(Activeable.swb_active.getName()) || semProp.getURI().equals(Stream.social_keepAliveManager.getURI())
                       || semProp.getURI().equals(social_stream_PoolTime.getURI()) || semProp.getName().equals(social_stream_phrase.getName()) || semProp.getName().equals(social_stream_allPhrases.getName())
                            || semProp.getName().equals(social_stream_exactPhrase.getName()) || semProp.getName().equals(social_stream_fromAccount.getName()) || semProp.getName().equals(social_stream_notPhrase.getName())))
                       || (action.equalsIgnoreCase("ADD") && semProp.getURI().equals(social_hasStream_socialNetwork.getURI()))
                       || (action.equalsIgnoreCase("REMOVE") && semProp!=null && semProp.getURI().equals(social_hasStream_socialNetwork.getURI())))
                    {
                        /*
                        //Revisar redes sociales que se encuentran en este momento en el stream
                        ArrayList asocialNetIDs=new ArrayList();    //White List -Lista buena-Lista que si esta en el stream
                        Iterator <SocialNetwork> itSn=stream.listSocialNetworks();
                        while(itSn.hasNext())
                        {
                            SocialNetwork socialNet=itSn.next();
                            //System.out.println("socialNet lista blanca:"+socialNet.getId());
                            asocialNetIDs.add(socialNet.getId());   //Se agrega el id de la red social a la lista blanca (asocialNetIDs)
                        }
                        
                        //Si en la clase SocialNetStreamSearch existe alguna instancia de SocialNetwork para el stream que no este en la mismo Stream(Lista blanca de arriba), entonces se elimina
                        Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
                        while(itSocialNetStreamSearch.hasNext())
                        {
                            SocialNetStreamSearch socialNetStreamSearch=itSocialNetStreamSearch.next();
                            if(socialNetStreamSearch.getSocialNetwork()!=null)
                            {
                                //Si la red social del objeto socialNetStreamSearch, no se encuentra en la lista blanca, se eliminara dicho objeto
                                if(!asocialNetIDs.contains(socialNetStreamSearch.getSocialNetwork().getId()))  
                                {
                                    //System.out.println("Se elimina SocialNet:"+socialNetStreamSearch.getSocialNetwork().getId()+" de la clase:SocialNetStreamSearch, puesto que ya no esta en el strea:"+stream.getId());
                                    socialNetStreamSearch.remove();
                                }
                            }
                        }
                        //Se actualiza el Listener del stream
                        * */
                        
                        ListenerMgr.createUpdateTimers(stream);
                        
                    }
                    if(action.equalsIgnoreCase("REMOVE") && semProp==null)  //Quiere decir que se esta eliminando una instancia de clase completa, no una propiedad
                    {
                        //System.out.println("Entra a Stream/Remove...");
                        ListenerMgr.removeTimer(stream, true);
                        
                        //Si se elimina el stream, se supone que por la ontología(removeDependency=true), se borrarían los objetos SocialNetStreamSearch
                        //que tuviera asociados, sin embargo, aqui también lo hago de manera manual, ver si no fuera necesario, quitar el sig. código despues
                        //TODO:ELIMINAR EL SIGUIENTE CÓDIGO, DE NO SER NECESARIO.
                        Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
                        while(itSocialNetStreamSearch.hasNext())
                        {
                            SocialNetStreamSearch socialNetStreamSearch=itSocialNetStreamSearch.next();
                            //System.out.println("Remueve en Stream:"+socialNetStreamSearch);
                            socialNetStreamSearch.remove();
                        }
                        
                    }
                }
            }
        });
        
                
    }
   
}
