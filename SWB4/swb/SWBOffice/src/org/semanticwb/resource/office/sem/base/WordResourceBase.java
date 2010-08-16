package org.semanticwb.resource.office.sem.base;


public abstract class WordResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    public static final org.semanticwb.platform.SemanticProperty swboffice_position=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#position");
    public static final org.semanticwb.platform.SemanticProperty swboffice_txtant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#txtant");
    public static final org.semanticwb.platform.SemanticProperty swboffice_txtsig=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#txtsig");
    public static final org.semanticwb.platform.SemanticProperty swboffice_tfont=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#tfont");
    public static final org.semanticwb.platform.SemanticProperty swboffice_npages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#npages");
    public static final org.semanticwb.platform.SemanticProperty swboffice_content=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#content");
    public static final org.semanticwb.platform.SemanticProperty swboffice_tpred=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#tpred");
    public static final org.semanticwb.platform.SemanticProperty swboffice_versionToShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#versionToShow");
    public static final org.semanticwb.platform.SemanticProperty swboffice_pages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#pages");
    public static final org.semanticwb.platform.SemanticProperty swboffice_deletestyles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#deletestyles");
    public static final org.semanticwb.platform.SemanticProperty swboffice_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#repositoryName");
    public static final org.semanticwb.platform.SemanticClass swboffice_WordResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");

    public WordResourceBase()
    {
    }

    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getPosition()
    {
        return getSemanticObject().getIntProperty(swboffice_position);
    }

    public void setPosition(int value)
    {
        getSemanticObject().setIntProperty(swboffice_position, value);
    }

    public String getTxtant()
    {
        return getSemanticObject().getProperty(swboffice_txtant);
    }

    public void setTxtant(String value)
    {
        getSemanticObject().setProperty(swboffice_txtant, value);
    }

    public String getTxtsig()
    {
        return getSemanticObject().getProperty(swboffice_txtsig);
    }

    public void setTxtsig(String value)
    {
        getSemanticObject().setProperty(swboffice_txtsig, value);
    }

    public String getTfont()
    {
        return getSemanticObject().getProperty(swboffice_tfont);
    }

    public void setTfont(String value)
    {
        getSemanticObject().setProperty(swboffice_tfont, value);
    }

    public int getNpages()
    {
        return getSemanticObject().getIntProperty(swboffice_npages);
    }

    public void setNpages(int value)
    {
        getSemanticObject().setIntProperty(swboffice_npages, value);
    }

    public String getContent()
    {
        return getSemanticObject().getProperty(swboffice_content);
    }

    public void setContent(String value)
    {
        getSemanticObject().setProperty(swboffice_content, value);
    }

    public boolean isTpred()
    {
        return getSemanticObject().getBooleanProperty(swboffice_tpred);
    }

    public void setTpred(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_tpred, value);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(swboffice_versionToShow);
    }

    public void setVersionToShow(String value)
    {
        getSemanticObject().setProperty(swboffice_versionToShow, value);
    }

    public boolean isPages()
    {
        return getSemanticObject().getBooleanProperty(swboffice_pages);
    }

    public void setPages(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_pages, value);
    }

    public boolean isDeletestyles()
    {
        return getSemanticObject().getBooleanProperty(swboffice_deletestyles);
    }

    public void setDeletestyles(boolean value)
    {
        getSemanticObject().setBooleanProperty(swboffice_deletestyles, value);
    }

    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(swboffice_repositoryName);
    }

    public void setRepositoryName(String value)
    {
        getSemanticObject().setProperty(swboffice_repositoryName, value);
    }
}
