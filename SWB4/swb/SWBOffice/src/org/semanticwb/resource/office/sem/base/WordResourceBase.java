package org.semanticwb.resource.office.sem.base;


public class WordResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    public static final org.semanticwb.platform.SemanticProperty cm_numberOfPages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#numberOfPages");
    public static final org.semanticwb.platform.SemanticProperty cm_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#content");
    public static final org.semanticwb.platform.SemanticProperty cm_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty cm_Paginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#Paginated");
    public static final org.semanticwb.platform.SemanticProperty cm_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#repositoryName");
    public static final org.semanticwb.platform.SemanticClass cm_WordResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#WordResource");

    public WordResourceBase()
    {
    }

    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#WordResource");

    public int getNumberOfPages()
    {
        return getSemanticObject().getIntProperty(cm_numberOfPages);
    }

    public void setNumberOfPages(int numberOfPages)
    {
        getSemanticObject().setLongProperty(cm_numberOfPages, numberOfPages);
    }

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

    public boolean isPaginated()
    {
        return getSemanticObject().getBooleanProperty(cm_Paginated);
    }

    public void setPaginated(boolean Paginated)
    {
        getSemanticObject().setBooleanProperty(cm_Paginated, Paginated);
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
