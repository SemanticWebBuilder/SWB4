package org.semanticwb.social;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.social.listener.Listener;


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
                    //System.out.println("Stream/Accion:"+action+", streamUri:"+stream.getURI()+",semProp:"+semProp);
                    if(action.equalsIgnoreCase("CREATE"))   //Quiere decir que se esta creando una instancia de la clase nueva
                    {
                        //System.out.println("Entra a Stream/Create...");
                        Listener.createUpdateTimers(stream);
                    }else if((action.equalsIgnoreCase("SET") && (semProp.getURI().equals(social_stream_PoolTime.getURI()) || semProp.getURI().equals(social_stream_phrase)))
                       || (action.equalsIgnoreCase("ADD") && semProp.getURI().equals(social_hasStream_socialNetwork.getURI()))
                       || (action.equalsIgnoreCase("REMOVE") && semProp!=null && semProp.getURI().equals(social_hasStream_socialNetwork.getURI())))
                    {
                        System.out.println("Entra a Stream/Update...Activo??:"+stream.isActive());
                        ArrayList asocialNetIDs=new ArrayList();    //White List -Lista buena-Lista que si esta en el stream
                        Iterator <SocialNetwork> itSn=stream.listSocialNetworks();
                        while(itSn.hasNext())
                        {
                            SocialNetwork socialNet=itSn.next();
                            //System.out.println("socialNet lista blanca:"+socialNet.getId());
                            asocialNetIDs.add(socialNet.getId());   //Se agrega el id de la red social a la lista blanca (asocialNetIDs)
                        }
                        
                        //Si en la clase SocialNetStreamSearch existe alguna instancia de SocialNetwork para el stream que no este en la mismo Stream, entonces se elimina
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
                        Listener.createUpdateTimers(stream);
                        
                    }
                    if(action.equalsIgnoreCase("REMOVE") && semProp==null)  //Quiere decir que se esta eliminando una instancia de clase completa, no una propiedad
                    {
                        System.out.println("Entra a Stream/Remove...");
                        Listener.removeTimer(stream);
                        
                        //Si se elimina el stream, se supone que por la ontología(removeDependency=true), se borrarían los objetos SocialNetStreamSearch
                        //que tuviera asociados, sin embargo, aqui también lo hago de manera manual, ver si no fuera necesario, quitar el sig. código despues
                        //TODO:ELIMINAR EL SIGUIENTE CÓDIGO, DE NO SER NECESARIO.
                        Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
                        while(itSocialNetStreamSearch.hasNext())
                        {
                            SocialNetStreamSearch socialNetStreamSearch=itSocialNetStreamSearch.next();
                            System.out.println("Remueve en Stream:"+socialNetStreamSearch);
                            socialNetStreamSearch.remove();
                        }
                        
                    }
                }
            }
        });
    }
   
}
