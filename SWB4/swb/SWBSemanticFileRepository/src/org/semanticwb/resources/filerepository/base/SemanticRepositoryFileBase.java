package org.semanticwb.resources.filerepository.base;


public class SemanticRepositoryFileBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_see=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#see");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_msgupdated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#msgupdated");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_msgcrated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#msgcrated");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_msgdeleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#msgdeleted");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_modify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#modify");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_useFolders=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#useFolders");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_admin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#admin");
    public static final org.semanticwb.platform.SemanticClass swbfilerep_SemanticRepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#SemanticRepositoryFile");

    public SemanticRepositoryFileBase()
    {
    }

    public SemanticRepositoryFileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#SemanticRepositoryFile");

    public String getSee()
    {
        return getSemanticObject().getProperty(swbfilerep_see);
    }

    public void setSee(String see)
    {
        getSemanticObject().setProperty(swbfilerep_see, see);
    }

    public String getMsgupdated()
    {
        return getSemanticObject().getProperty(swbfilerep_msgupdated);
    }

    public void setMsgupdated(String msgupdated)
    {
        getSemanticObject().setProperty(swbfilerep_msgupdated, msgupdated);
    }

    public String getMsgcrated()
    {
        return getSemanticObject().getProperty(swbfilerep_msgcrated);
    }

    public void setMsgcrated(String msgcrated)
    {
        getSemanticObject().setProperty(swbfilerep_msgcrated, msgcrated);
    }

    public String getMsgdeleted()
    {
        return getSemanticObject().getProperty(swbfilerep_msgdeleted);
    }

    public void setMsgdeleted(String msgdeleted)
    {
        getSemanticObject().setProperty(swbfilerep_msgdeleted, msgdeleted);
    }

    public String getModify()
    {
        return getSemanticObject().getProperty(swbfilerep_modify);
    }

    public void setModify(String modify)
    {
        getSemanticObject().setProperty(swbfilerep_modify, modify);
    }

    public boolean isUseFolders()
    {
        return getSemanticObject().getBooleanProperty(swbfilerep_useFolders);
    }

    public void setUseFolders(boolean useFolders)
    {
        getSemanticObject().setBooleanProperty(swbfilerep_useFolders, useFolders);
    }

    public String getAdmin()
    {
        return getSemanticObject().getProperty(swbfilerep_admin);
    }

    public void setAdmin(String admin)
    {
        getSemanticObject().setProperty(swbfilerep_admin, admin);
    }
}
