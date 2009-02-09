package org.semanticwb.portlet.office.base;


public class OfficePortletBase extends org.semanticwb.model.Portlet implements org.semanticwb.model.RoleRefable,org.semanticwb.model.Versionable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Hitable,org.semanticwb.model.Deleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.XMLable,org.semanticwb.model.Viewable,org.semanticwb.model.Priorityable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Localeable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Calendarable,org.semanticwb.model.Indexable,org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticProperty swbrep_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#content");
    public static final org.semanticwb.platform.SemanticProperty swbrep_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty swbrep_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#repositoryName");
    public static final org.semanticwb.platform.SemanticClass swbrep_OfficePortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#OfficePortlet");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#OfficePortlet");

    public OfficePortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.portlet.office.OfficePortlet getOfficePortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.OfficePortlet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.OfficePortlet> listOfficePortlets(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.OfficePortlet>(org.semanticwb.portlet.office.OfficePortlet.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.OfficePortlet> listOfficePortlets()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.OfficePortlet>(org.semanticwb.portlet.office.OfficePortlet.class, it, true);
    }

    public static org.semanticwb.portlet.office.OfficePortlet createOfficePortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.OfficePortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeOfficePortlet(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasOfficePortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (getOfficePortlet(id, model)!=null);
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
