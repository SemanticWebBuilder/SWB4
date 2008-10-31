package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.SWBContextBase;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticVocabulary;

public class SWBContext extends SWBContextBase
{
    private static Logger log=SWBUtils.getLogger(SWBContext.class);
    
    public static String WEBSITE_ADMIN="SWBAdmin";
    public static String WEBSITE_GLOBAL="SWBGlobal";
    public static String USERREPOSITORY_DEFAULT="urswb";
    
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
    
    public static FormView getFormView(String id)
    {
        FormView view=null;
        if(id!=null)
        {
            SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
            Resource res=ont.getRDFOntModel().getResource("http://www.semanticwebbuilder.org/swb4/xforms/ontology#"+id);
            Property type=ont.getRDFOntModel().getProperty(SemanticVocabulary.RDF_TYPE);
            if(ont.getRDFOntModel().contains(res, type))
            {
                SemanticObject obj=new SemanticObject(res);
                //System.out.println("id:"+id+" obj:"+obj);
                view=new FormView(obj);
            }
        }
        return view;
    }
}