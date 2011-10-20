package org.semanticwb.process.model.base;

public interface StoreRepositoryFileableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryDirectory");
    public static final org.semanticwb.platform.SemanticProperty swp_processDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processDirectory");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareStatus");
    public static final org.semanticwb.platform.SemanticProperty swp_srfStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#srfStatus");
    public static final org.semanticwb.platform.SemanticProperty swp_processFileName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processFileName");
    public static final org.semanticwb.platform.SemanticProperty swp_repositoryFileId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#repositoryFileId");
    public static final org.semanticwb.platform.SemanticClass swp_StoreRepositoryFileable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryFileable");

   /**
   * Sets a value from the property ProcessDirectory
   * @param value An instance of org.semanticwb.process.model.RepositoryDirectory
   */
    public void setProcessDirectory(org.semanticwb.process.model.RepositoryDirectory value);

   /**
   * Remove the value from the property ProcessDirectory
   */
    public void removeProcessDirectory();

    public org.semanticwb.process.model.RepositoryDirectory getProcessDirectory();

   /**
   * Sets a value from the property Status
   * @param value An instance of org.semanticwb.process.model.ItemAwareStatus
   */
    public void setStatus(org.semanticwb.process.model.ItemAwareStatus value);

   /**
   * Remove the value from the property Status
   */
    public void removeStatus();

    public org.semanticwb.process.model.ItemAwareStatus getStatus();

    public String getProcessFileName();

    public void setProcessFileName(String value);

    public String getRepositoryFileId();

    public void setRepositoryFileId(String value);
}
