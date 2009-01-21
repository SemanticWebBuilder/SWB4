package org.semanticwb.portlet.office.base;


public class OfficePortletBase extends org.semanticwb.model.Portlet 
{
    public static final org.semanticwb.platform.SemanticProperty swbrep_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#content");
    public static final org.semanticwb.platform.SemanticProperty swbrep_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty swbrep_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#repositoryName");
    public static final org.semanticwb.platform.SemanticClass swbrep_OfficePortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#OfficePortlet");


    public static org.semanticwb.portlet.office.OfficePortlet createOfficePortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.OfficePortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbrep_OfficePortlet), swbrep_OfficePortlet);
    }

    public OfficePortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
