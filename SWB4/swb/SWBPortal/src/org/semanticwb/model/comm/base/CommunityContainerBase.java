package org.semanticwb.model.comm.base;


public class CommunityContainerBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Localeable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteType");
    public static final org.semanticwb.platform.SemanticClass swbcomm_CommunityContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityContainer");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityContainer");

    public CommunityContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.CommunityContainer> listCommunityContainers(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.CommunityContainer>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.CommunityContainer> listCommunityContainers()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.CommunityContainer>(it, true);
    }

    public static org.semanticwb.model.comm.CommunityContainer getCommunityContainer(String id)
    {
       org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
       org.semanticwb.model.comm.CommunityContainer ret=null;
       org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
       if(model!=null)
       {
           org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
           if(obj!=null)
           {
               ret=(org.semanticwb.model.comm.CommunityContainer)obj.createGenericInstance();
           }
       }
       return ret;
    }

    public static org.semanticwb.model.comm.CommunityContainer createCommunityContainer(String id, String namespace)
    {
        org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
        org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
        return (org.semanticwb.model.comm.CommunityContainer)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
    }

    public static void removeCommunityContainer(String id)
    {
       org.semanticwb.model.comm.CommunityContainer obj=getCommunityContainer(id);
       if(obj!=null)
       {
           obj.remove();
       }
    }

    public static boolean hasCommunityContainer(String id)
    {
        return (getCommunityContainer(id)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSiteType> listMicroSiteTypes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSiteType>(getSemanticObject().listObjectProperties(swbcomm_hasMicroSiteType));
    }

    public boolean hasMicroSiteType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        if(micrositetype==null)return false;        return getSemanticObject().hasObjectProperty(swbcomm_hasMicroSiteType,micrositetype.getSemanticObject());
    }

    public void addMicroSiteType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasMicroSiteType, micrositetype.getSemanticObject());
    }

    public void removeAllMicroSiteType()
    {
        getSemanticObject().removeProperty(swbcomm_hasMicroSiteType);
    }

    public void removeMicroSiteType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasMicroSiteType,micrositetype.getSemanticObject());
    }

    public org.semanticwb.model.comm.MicroSiteType getMicroSiteType()
    {
         org.semanticwb.model.comm.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMicroSiteType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.comm.MicroSiteType)obj.createGenericInstance();
         }
         return ret;
    }
}
