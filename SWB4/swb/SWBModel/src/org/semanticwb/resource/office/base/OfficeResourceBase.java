package org.semanticwb.resource.office.base;


public class OfficeResourceBase extends org.semanticwb.model.Resource implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Localeable,org.semanticwb.model.Campable,org.semanticwb.model.XMLable,org.semanticwb.model.Activeable,org.semanticwb.model.Hitable,org.semanticwb.model.Priorityable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Indexable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Viewable,org.semanticwb.model.Calendarable
{
    public static final org.semanticwb.platform.SemanticProperty swbrep_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#content");
    public static final org.semanticwb.platform.SemanticProperty swbrep_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty swbrep_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#repositoryName");
    public static final org.semanticwb.platform.SemanticClass swbrep_OfficeResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#OfficeResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#OfficeResource");

    public OfficeResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.resource.office.OfficeResource getOfficeResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.OfficeResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.OfficeResource> listOfficeResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.OfficeResource>(org.semanticwb.resource.office.OfficeResource.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.OfficeResource> listOfficeResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.OfficeResource>(org.semanticwb.resource.office.OfficeResource.class, it, true);
    }

    public static org.semanticwb.resource.office.OfficeResource createOfficeResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.OfficeResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeOfficeResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasOfficeResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getOfficeResource(id, model)!=null);
    }

    public String getContent()
    {
        return getSemanticObject().getProperty(swbrep_content);
    }

    public void setContent(String content)
    {
        getSemanticObject().setProperty(swbrep_content, content);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(swbrep_versionToShow);
    }

    public void setVersionToShow(String versionToShow)
    {
        getSemanticObject().setProperty(swbrep_versionToShow, versionToShow);
    }

    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(swbrep_repositoryName);
    }

    public void setRepositoryName(String repositoryName)
    {
        getSemanticObject().setProperty(swbrep_repositoryName, repositoryName);
    }
}
