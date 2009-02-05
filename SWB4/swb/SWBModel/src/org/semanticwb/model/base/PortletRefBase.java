package org.semanticwb.model.base;


public class PortletRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Deleteable,org.semanticwb.model.Activeable,org.semanticwb.model.Priorityable
{
    public static final org.semanticwb.platform.SemanticProperty swb_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticClass swb_Portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portlet");
    public static final org.semanticwb.platform.SemanticProperty swb_portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portlet");
    public static final org.semanticwb.platform.SemanticClass swb_PortletRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");

    public PortletRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.PortletRef getPortletRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PortletRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.PortletRef> listPortletRefs(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, it, true);
    }

    public static org.semanticwb.model.PortletRef createPortletRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PortletRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.PortletRef createPortletRef(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.PortletRef.createPortletRef(String.valueOf(id), model);
    }

    public static void removePortletRef(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPortletRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPortletRef(id, model)!=null);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(swb_priority, priority);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, deleted);
    }

    public void setPortlet(org.semanticwb.model.Portlet portlet)
    {
        getSemanticObject().setObjectProperty(swb_portlet, portlet.getSemanticObject());
    }

    public void removePortlet()
    {
        getSemanticObject().removeProperty(swb_portlet);
    }

    public org.semanticwb.model.Portlet getPortlet()
    {
         org.semanticwb.model.Portlet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_portlet);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Portlet)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
