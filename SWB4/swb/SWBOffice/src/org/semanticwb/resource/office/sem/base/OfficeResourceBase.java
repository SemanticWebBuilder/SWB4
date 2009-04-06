package org.semanticwb.resource.office.sem.base;


public class OfficeResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty cm_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#content");
    public static final org.semanticwb.platform.SemanticProperty cm_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty cm_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#repositoryName");
    public static final org.semanticwb.platform.SemanticClass cm_OfficeResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeResource");

    public OfficeResourceBase()
    {
    }

    public OfficeResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeResource");

    public String getContent()
    {
        return getSemanticObject().getProperty(cm_content);
    }

    public void setContent(String content)
    {
        getSemanticObject().setProperty(cm_content, content);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(cm_versionToShow);
    }

    public void setVersionToShow(String versionToShow)
    {
        getSemanticObject().setProperty(cm_versionToShow, versionToShow);
    }

    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(cm_repositoryName);
    }

    public void setRepositoryName(String repositoryName)
    {
        getSemanticObject().setProperty(cm_repositoryName, repositoryName);
    }
}
