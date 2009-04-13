package org.semanticwb.resource.office.sem.base;


public class PPTResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    public static final org.semanticwb.platform.SemanticProperty office_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#content");
    public static final org.semanticwb.platform.SemanticProperty office_showDownload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#showDownload");
    public static final org.semanticwb.platform.SemanticProperty office_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty office_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#repositoryName");
    public static final org.semanticwb.platform.SemanticClass office_PPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#PPTResource");

    public PPTResourceBase()
    {
    }

    public PPTResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#PPTResource");

    public String getContent()
    {
        return getSemanticObject().getProperty(office_content);
    }

    public void setContent(String content)
    {
        getSemanticObject().setProperty(office_content, content);
    }

    public boolean isShowDownload()
    {
        return getSemanticObject().getBooleanProperty(office_showDownload);
    }

    public void setShowDownload(boolean showDownload)
    {
        getSemanticObject().setBooleanProperty(office_showDownload, showDownload);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(office_versionToShow);
    }

    public void setVersionToShow(String versionToShow)
    {
        getSemanticObject().setProperty(office_versionToShow, versionToShow);
    }

    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(office_repositoryName);
    }

    public void setRepositoryName(String repositoryName)
    {
        getSemanticObject().setProperty(office_repositoryName, repositoryName);
    }
}
