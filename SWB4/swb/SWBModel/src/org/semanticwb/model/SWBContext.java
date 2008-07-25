
package org.semanticwb.model;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBContext 
{
    private static Logger log=SWBUtils.getLogger(SWBContext.class);
    private static SWBContext instance=null;
    private static SWBVocabulary vocabulary;
    
    private SWBContext()
    {
        log.event("Initialize Semantic WebBuilder Context...");
        init();
    }
    
    //Initialize context
    private void init()
    {
        vocabulary=new SWBVocabulary();
    }
  
    static public synchronized SWBContext createInstance()
    {
        if (instance == null)
        {
            instance = new SWBContext();
        }
        return instance;
    }
    
    public static SWBVocabulary getVocabulary()
    {
        return vocabulary;
    }

//    public static User getUser(String uri)
//    {
//        
//    }

}
