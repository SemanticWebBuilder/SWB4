package org.semanticwb.social;

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
                        System.out.println("Entra a Stream/Create...");
                        System.out.println("En la clase Stream-ADD:"+stream.getId()+", activo:"+stream.isActive());
                        
                        Listener.createUpdateTimers(stream);
                    }else if((action.equalsIgnoreCase("SET") && (semProp.getURI().equals(social_stream_PoolTime.getURI()) || semProp.getURI().equals(social_stream_phrase)))
                       || (action.equalsIgnoreCase("ADD") && semProp.getURI().equals(social_hasStream_socialNetwork.getURI()))
                       || (action.equalsIgnoreCase("REMOVE") && semProp!=null && semProp.getURI().equals(social_hasStream_socialNetwork.getURI())))
                    {
                        System.out.println("Entra a Stream/Update...Activo??:"+stream.isActive());
                        Listener.createUpdateTimers(stream);
                    }
                    if(action.equalsIgnoreCase("REMOVE") && semProp==null)  //Quiere decir que se esta eliminando una instancia de clase completa, no una propiedad
                    {
                        System.out.println("Entra a Stream/Remove...");
                        Listener.removeTimer(stream);
                    }
                }
            }
        });
    }
   
}
