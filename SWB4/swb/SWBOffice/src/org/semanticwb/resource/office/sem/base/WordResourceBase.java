package org.semanticwb.resource.office.sem.base;


public class WordResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
{
    public static class ClassMgr
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
       public static final org.semanticwb.platform.SemanticProperty swboffice_repositoryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#repositoryName");
       public static final org.semanticwb.platform.SemanticClass swboffice_WordResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");
    }

    public WordResourceBase()
    {
    }

    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getPosition()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swboffice_position);
    }

    public void setPosition(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swboffice_position, value);
    }

    public String getTxtant()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_txtant);
    }

    public void setTxtant(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_txtant, value);
    }

    public String getTxtsig()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_txtsig);
    }

    public void setTxtsig(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_txtsig, value);
    }

    public String getTfont()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_tfont);
    }

    public void setTfont(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_tfont, value);
    }

    public int getNpages()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swboffice_npages);
    }

    public void setNpages(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swboffice_npages, value);
    }

    public String getContent()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_content);
    }

    public void setContent(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_content, value);
    }

    public boolean isTpred()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swboffice_tpred);
    }

    public void setTpred(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swboffice_tpred, value);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_versionToShow);
    }

    public void setVersionToShow(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_versionToShow, value);
    }

    public boolean isPages()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swboffice_pages);
    }

    public void setPages(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swboffice_pages, value);
    }

    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_repositoryName);
    }

    public void setRepositoryName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_repositoryName, value);
    }
}
