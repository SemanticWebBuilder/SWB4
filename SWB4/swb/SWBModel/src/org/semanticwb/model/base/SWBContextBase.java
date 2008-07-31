
package org.semanticwb.model.base;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.SWBInstance;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author  Javier Solis Gonzalez (jsolis@infotec.com.mx)
 */

public class SWBContextBase 
{
    private static SWBVocabulary vocabulary=new SWBVocabulary();
    private static SemanticMgr mgr=SWBInstance.getSemanticMgr();
    
    public static SWBVocabulary getVocabulary()
    {
        return vocabulary;
    }

    public static void removeObject(String uri)
    {
        removeObject(mgr.getOntology().getSemanticObject(uri));
    }
    
    public static void removeObject(SemanticObject obj)
    {
        if(obj!=null)
        {
            mgr.getOntology().getRDFOntModel().remove(obj.getRDFResource(), null, null);
        }
    }

    public static User getUser(String uri)
    {
        return (User)mgr.getOntology().getSemanticObject(uri,vocabulary.WebPage);
    }
    
    public static User createUser(SemanticModel model, String uri)
    {
        return (User)model.createSemanticObject(uri, vocabulary.User);
    }
    
    public static Iterator<org.semanticwb.model.User> listUsers()
    {
        return (Iterator<org.semanticwb.model.User>)vocabulary.User.listInstances();
    }
    
    public static WebPage getWebPage(String uri)
    {
        return (WebPage)mgr.getOntology().getSemanticObject(uri,vocabulary.WebPage);
    }
    
    public static WebPage createWebPage(SemanticModel model, String uri)
    {
        return (WebPage)model.createSemanticObject(uri, vocabulary.WebPage);
    }    
    
    public static Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return (Iterator<org.semanticwb.model.WebPage>)vocabulary.WebPage.listInstances();
    }    

}
