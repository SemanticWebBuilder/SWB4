package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


   /**
   * Clase en donde se almacenan las palabras que se desean monitorear 
   */
public class WordsToMonitor extends org.semanticwb.social.base.WordsToMonitorBase 
{
    public WordsToMonitor(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    
     static {
        //Observador de la clase "WordsToMonitor", cada que haya un cambio en ella se ejecuta el siguiente código
        WordsToMonitor.sclass.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(WordsToMonitor.social_WordsToMonitor))
                {
                    WordsToMonitor words2Monitor = (WordsToMonitor) obj.createGenericInstance();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(words2Monitor.getSemanticObject().getModel().getName());
                    Iterator<SocialNetwork> itSocialNetWorks=SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                    while(itSocialNetWorks.hasNext())
                    {
                        SocialNetwork socialNet=itSocialNetWorks.next();
                        if(socialNet instanceof KeepAliveListenerable)
                        {
                            KeepAliveListenerable keepAliveListenerable=(KeepAliveListenerable)socialNet;
                            if(keepAliveListenerable.isIsKeepingConnection()) //Tiene la propiedad de mantener la conexión en true, por lo tanto no enviar a timer
                            {
                                keepAliveListenerable.stopListenAlive();
                                keepAliveListenerable.listenAlive(wsite);
                            }
                        }
                    }
                 }
            }
        });
    }

}
