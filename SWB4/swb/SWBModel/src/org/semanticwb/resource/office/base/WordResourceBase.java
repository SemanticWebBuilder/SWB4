package org.semanticwb.resource.office.base;


public class WordResourceBase extends org.semanticwb.resource.office.OfficeResource implements org.semanticwb.model.Calendarable,org.semanticwb.model.Viewable,org.semanticwb.model.Indexable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Hitable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Priorityable,org.semanticwb.model.XMLable,org.semanticwb.model.Localeable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Referensable,org.semanticwb.model.XMLConfable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Campable,org.semanticwb.model.Traceable,org.semanticwb.model.Deleteable
{
    public static final org.semanticwb.platform.SemanticProperty word_paginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/content/office/word#paginated");
    public static final org.semanticwb.platform.SemanticProperty word_NumberOfPages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/content/office/word#NumberOfPages");
    public static final org.semanticwb.platform.SemanticClass swbrep_WordResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#WordResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#WordResource");

    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.resource.office.WordResource getWordResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.WordResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.WordResource> listWordResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.WordResource>(org.semanticwb.resource.office.WordResource.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.WordResource> listWordResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.WordResource>(org.semanticwb.resource.office.WordResource.class, it, true);
    }

    public static org.semanticwb.resource.office.WordResource createWordResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.WordResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeWordResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasWordResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getWordResource(id, model)!=null);
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
