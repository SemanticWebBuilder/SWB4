package org.semanticwb.resource.office.sem.base;


public class WordResourceBase extends org.semanticwb.resource.office.sem.OfficeResource 
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

    public WordResourceBase()
    {
    }

    public WordResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#WordResource");

    public int getPosition()
    {
        return getSemanticObject().getIntProperty(swboffice_position);
    }

    public void setPosition(int position)
    {
        getSemanticObject().setLongProperty(swboffice_position, position);
    }

    public String getTxtant()
    {
        return getSemanticObject().getProperty(swboffice_txtant);
    }

    public void setTxtant(String txtant)
    {
        getSemanticObject().setProperty(swboffice_txtant, txtant);
    }

    public String getTxtsig()
    {
        return getSemanticObject().getProperty(swboffice_txtsig);
    }

    public void setTxtsig(String txtsig)
    {
        getSemanticObject().setProperty(swboffice_txtsig, txtsig);
    }

    public String getTfont()
    {
        return getSemanticObject().getProperty(swboffice_tfont);
    }

    public void setTfont(String tfont)
    {
        getSemanticObject().setProperty(swboffice_tfont, tfont);
    }

    public int getNpages()
    {
        return getSemanticObject().getIntProperty(swboffice_npages);
    }

    public void setNpages(int npages)
    {
        getSemanticObject().setLongProperty(swboffice_npages, npages);
    }

    public String getContent()
    {
        return getSemanticObject().getProperty(swboffice_content);
    }

    public void setContent(String content)
    {
        getSemanticObject().setProperty(swboffice_content, content);
    }

    public boolean isTpred()
    {
        return getSemanticObject().getBooleanProperty(swboffice_tpred);
    }

    public void setTpred(boolean tpred)
    {
        getSemanticObject().setBooleanProperty(swboffice_tpred, tpred);
    }

    public String getVersionToShow()
    {
        return getSemanticObject().getProperty(swboffice_versionToShow);
    }

    public void setVersionToShow(String versionToShow)
    {
        getSemanticObject().setProperty(swboffice_versionToShow, versionToShow);
    }

    public boolean isPages()
    {
        return getSemanticObject().getBooleanProperty(swboffice_pages);
    }

    public void setPages(boolean pages)
    {
        getSemanticObject().setBooleanProperty(swboffice_pages, pages);
    }

    public String getRepositoryName()
    {
        return getSemanticObject().getProperty(swboffice_repositoryName);
    }

    public void setRepositoryName(String repositoryName)
    {
        getSemanticObject().setProperty(swboffice_repositoryName, repositoryName);
    }
}
