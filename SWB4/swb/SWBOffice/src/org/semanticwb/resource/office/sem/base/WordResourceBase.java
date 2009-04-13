package org.semanticwb.resource.office.sem.base;


public class WordResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    public static final org.semanticwb.platform.SemanticProperty office_numberOfPages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#numberOfPages");
    public static final org.semanticwb.platform.SemanticProperty office_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#content");
    public static final org.semanticwb.platform.SemanticProperty office_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty office_Paginated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#Paginated");
    public static final org.semanticwb.platform.SemanticProperty office_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#repositoryName");
    public static final org.semanticwb.platform.SemanticClass office_WordResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");

    public WordResourceBase()
    {
    }

    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");

    public int getNumberOfPages()
    {
        return getSemanticObject().getIntProperty(office_numberOfPages);
    }

    public void setNumberOfPages(int numberOfPages)
    {
        getSemanticObject().setLongProperty(office_numberOfPages, numberOfPages);
    }

    public String getContent()
    {
        return getSemanticObject().getProperty(office_content);
    }

    public void setContent(String content)
    {
        getSemanticObject().setProperty(office_content, content);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(office_versionToShow);
    }

    public void setVersionToShow(String versionToShow)
    {
        getSemanticObject().setProperty(office_versionToShow, versionToShow);
    }

    public boolean isPaginated()
    {
        return getSemanticObject().getBooleanProperty(office_Paginated);
    }

    public void setPaginated(boolean Paginated)
    {
        getSemanticObject().setBooleanProperty(office_Paginated, Paginated);
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
