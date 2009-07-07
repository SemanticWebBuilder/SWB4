package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.SWBContextBase;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticVocabulary;

public class SWBContext extends SWBContextBase
{
    private static Logger log=SWBUtils.getLogger(SWBContext.class);
    
    public static String WEBSITE_ADMIN="SWBAdmin";
    public static String WEBSITE_ONTEDITOR="SWBOntEdit";
    public static String WEBSITE_GLOBAL="SWBGlobal";
    public static String USERREPOSITORY_DEFAULT="urswb";
    public static String USERREPOSITORY_ADMIN="uradm";

    private static ArrayList<String> filtered=new ArrayList();
    
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
        filtered.add(WEBSITE_ADMIN);
        filtered.add(WEBSITE_ONTEDITOR);
        //filtered.add(WEBSITE_GLOBAL);
    }
    
    public static WebSite getAdminWebSite()
    {
        return getWebSite(WEBSITE_ADMIN);
    }
    
    public static WebSite getOntEditor()
    {
        return getWebSite(WEBSITE_ONTEDITOR);
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

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        if(SWBPlatform.getEnv("swb/adminShow","false").equals("false"))
        {
            ArrayList<org.semanticwb.model.WebSite> arr=new ArrayList();
            Iterator<org.semanticwb.model.WebSite> it=swb_WebSite.listGenericInstances();
            while(it.hasNext())
            {
                WebSite ws=it.next();
                if(!filtered.contains(ws.getId()))
                {
                    arr.add(ws);
                }
            }
            return arr.iterator();
        }else
        {
            return (java.util.Iterator<org.semanticwb.model.WebSite>)swb_WebSite.listGenericInstances();
            //return SWBContextBase.listWebSites();
        }
    }

    /**
     *
     */
    public static class UTILS {

        public static String getIconClass(SemanticObject obj)
        {
            //System.out.println("getIconClass:"+obj);
            String ret=null;
            SemanticClass cls=obj.getSemanticClass();
            if(cls.hasProperty(Iconable.swb_iconClass.getName()))
            {
                ret=obj.getProperty(Iconable.swb_iconClass);
            }
            if(ret==null)
            {
                //System.out.println("getIconClass:1");
                ret="swbIcon"+cls.getName();
                if(cls.hasProperty(Activeable.swb_active.getName()))
                {
                    if(!obj.getBooleanProperty(Activeable.swb_active))
                    {
                        ret+="U";
                        return ret;
                    }
                    //System.out.println("getIconClass:2");
                }
                if(cls.hasProperty(SWBClass.swb_valid.getName()))
                {
                    if(!obj.getBooleanProperty(SWBClass.swb_valid))
                    {
                        ret+="W";
                        return ret;
                    }
                    //System.out.println("getIconClass:2");
                }
                if(cls.hasProperty(RoleRefable.swb_hasRoleRef.getName()))
                {
                    if(obj.listValidObjectProperties(RoleRefable.swb_hasRoleRef).hasNext())
                    {
                        ret+="F";
                        return ret;
                    }
                    //System.out.println("getIconClass:3");
                }
                if(cls.hasProperty(UserGroupRefable.swb_hasUserGroupRef.getName()))
                {
                    if(obj.listValidObjectProperties(UserGroupRefable.swb_hasUserGroupRef).hasNext())
                    {
                        ret+="F";
                        return ret;
                    }
                    //System.out.println("getIconClass:4");
                }
                if(cls.hasProperty(RuleRefable.swb_hasRuleRef.getName()))
                {
                    if(obj.listValidObjectProperties(RuleRefable.swb_hasRuleRef).hasNext())
                    {
                        ret+="F";
                        return ret;
                    }
                }
                if(cls.hasProperty(CalendarRefable.swb_hasCalendarRef.getName()))
                {
                    if(obj.listValidObjectProperties(CalendarRefable.swb_hasCalendarRef).hasNext())
                    {
                        ret+="F";
                        return ret;
                    }
                }
                if(cls.hasProperty(Versionable.swb_actualVersion.getName()))
                {
                    if(obj.getObjectProperty(Versionable.swb_actualVersion)==null)
                    {
                        ret+="W";
                        return ret;
                    }
                }

            }
            return ret;
        }
    }

}