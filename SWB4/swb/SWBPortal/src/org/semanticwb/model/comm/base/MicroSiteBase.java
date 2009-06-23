package org.semanticwb.model.comm.base;


public class MicroSiteBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Undeleteable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Indexable
{
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_about=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#about");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMSMembersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMSMembersInv");
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_locatedIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#locatedIn");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#type");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");

    public MicroSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.MicroSite> listMicroSites(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSite>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.MicroSite> listMicroSites()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSite>(it, true);
    }

    public static org.semanticwb.model.comm.MicroSite getMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.MicroSite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.comm.MicroSite createMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.MicroSite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (getMicroSite(id, model)!=null);
    }

    public void setAbout(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(swbcomm_about, webpage.getSemanticObject());
    }

    public void removeAbout()
    {
        getSemanticObject().removeProperty(swbcomm_about);
    }

    public org.semanticwb.model.WebPage getAbout()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_about);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLocatedIn(org.semanticwb.model.catalogs.LocationEntity locationentity)
    {
        getSemanticObject().setObjectProperty(swbcomm_locatedIn, locationentity.getSemanticObject());
    }

    public void removeLocatedIn()
    {
        getSemanticObject().removeProperty(swbcomm_locatedIn);
    }

    public org.semanticwb.model.catalogs.LocationEntity getLocatedIn()
    {
         org.semanticwb.model.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_locatedIn);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }

    public void setType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        getSemanticObject().setObjectProperty(swbcomm_type, micrositetype.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(swbcomm_type);
    }

    public org.semanticwb.model.comm.MicroSiteType getType()
    {
         org.semanticwb.model.comm.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_type);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.comm.MicroSiteType)obj.createGenericInstance();
         }
         return ret;
    }
}
