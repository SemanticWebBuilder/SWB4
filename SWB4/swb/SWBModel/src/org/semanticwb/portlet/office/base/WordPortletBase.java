package org.semanticwb.portlet.office.base;


public class WordPortletBase extends org.semanticwb.portlet.office.OfficePortlet implements org.semanticwb.model.Viewable,org.semanticwb.model.Versionable,org.semanticwb.model.Deleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Priorityable,org.semanticwb.model.Calendarable,org.semanticwb.model.Hitable,org.semanticwb.model.RoleRefable,org.semanticwb.model.XMLable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Indexable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty word_paginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/content/office/word#paginated");
    public static final org.semanticwb.platform.SemanticProperty word_NumberOfPages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/content/office/word#NumberOfPages");
    public static final org.semanticwb.platform.SemanticClass swbrep_WordPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#WordPortlet");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#WordPortlet");

    public WordPortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.portlet.office.WordPortlet getWordPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.WordPortlet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.WordPortlet> listWordPortlets(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.WordPortlet>(org.semanticwb.portlet.office.WordPortlet.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.WordPortlet> listWordPortlets()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.WordPortlet>(org.semanticwb.portlet.office.WordPortlet.class, it, true);
    }

    public static org.semanticwb.portlet.office.WordPortlet createWordPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.WordPortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeWordPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasWordPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (getWordPortlet(id, model)!=null);
    }

    public boolean isPaginated()
    {
        return getSemanticObject().getBooleanProperty(word_paginated);
    }

    public void setPaginated(boolean paginated)
    {
        getSemanticObject().setBooleanProperty(word_paginated, paginated);
    }

    public int getNumberOfPages()
    {
        return getSemanticObject().getIntProperty(word_NumberOfPages);
    }

    public void setNumberOfPages(int NumberOfPages)
    {
        getSemanticObject().setLongProperty(word_NumberOfPages, NumberOfPages);
    }
}
