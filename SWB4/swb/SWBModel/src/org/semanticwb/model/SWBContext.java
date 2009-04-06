package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.SWBContextBase;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticVocabulary;

public class SWBContext extends SWBContextBase
{
    private static Logger log=SWBUtils.getLogger(SWBContext.class);
    
    public static String WEBSITE_ADMIN="SWBAdmin";
    public static String WEBSITE_GLOBAL="SWBGlobal";
    public static String USERREPOSITORY_DEFAULT="urswb";
    public static String USERREPOSITORY_ADMIN="uradm";
    
    private static SWBContext instance=null;
    static public synchronized SWBContext createInstance()
    {
        if (instance == null)
        {
            instance = new SWBContext();
        }
        return instance;
    }
    
    private SWBContext()
    {
        log.event("Initializing SemanticWebBuilder Context...");
    }
    
    public static WebSite getAdminWebSite()
    {
        return getWebSite(WEBSITE_ADMIN);
    }
    
    public static WebSite getGlobalWebSite()
    {
        return getWebSite(WEBSITE_GLOBAL);
    }    
    
    public static UserRepository getDefaultRepository()
    {
        return getUserRepository(USERREPOSITORY_DEFAULT);
    }

    public static UserRepository getAdminRepository()
    {
        return getUserRepository(USERREPOSITORY_ADMIN);
    }
    
    public static FormView getFormView(String id)
    {
        FormView view=null;
        if(id!=null)
        {
            SemanticObject obj=SemanticObject.createSemanticObject(SemanticVocabulary.SWBXF_URI+id);
            if(obj!=null)
            {
                view=(FormView)obj.createGenericInstance();
            }
            System.out.println("id:"+id+" obj:"+obj);
        }
        return view;
    }

    /**
     *
     */
    public static class UTILS {

        public static String getIconClass(SemanticObject obj)
        {
            String ret=null;
            SemanticClass cls=obj.getSemanticClass();
            if(cls.hasProperty(Iconable.swb_iconClass.getName()))
            {
                ret=obj.getProperty(Iconable.swb_iconClass);
            }
            if(ret==null)
            {
                ret="swbIcon"+cls.getName();
                if(cls.hasProperty(Activeable.swb_active.getName()))
                {
                    if(!obj.getBooleanProperty(Activeable.swb_active))
                    {
                        ret+="U";
                    }
                }
            }
            return ret;
        }
    }

}