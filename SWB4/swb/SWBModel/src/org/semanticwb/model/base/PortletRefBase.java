package org.semanticwb.model.base;


public class PortletRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Deleteable,org.semanticwb.model.Activeable,org.semanticwb.model.Priorityable
{
    public static final org.semanticwb.platform.SemanticProperty swb_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticClass swb_Portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portlet");
    public static final org.semanticwb.platform.SemanticProperty swb_portlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portlet");
    public static final org.semanticwb.platform.SemanticClass swb_PortletRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");

    public PortletRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
