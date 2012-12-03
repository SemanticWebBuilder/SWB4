package org.semanticwb.process.model.base;

public interface StoreRepositoryNodeableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeId");
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryDirectory");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeDirectory");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeName");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareStatus");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeStatus");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeComment");
    public static final org.semanticwb.platform.SemanticClass swp_StoreRepositoryNodeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryNodeable");

    public String getNodeId();

    public void setNodeId(String value);

   /**
   * Sets a value from the property NodeDirectory
   * @param value An instance of org.semanticwb.process.model.RepositoryDirectory
   */
    public void setNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value);

   /**
   * Remove the value from the property NodeDirectory
   */
    public void removeNodeDirectory();

    public org.semanticwb.process.model.RepositoryDirectory getNodeDirectory();

    public String getNodeName();

    public void setNodeName(String value);

   /**
   * Sets a value from the property NodeStatus
   * @param value An instance of org.semanticwb.process.model.ItemAwareStatus
   */
    public void setNodeStatus(org.semanticwb.process.model.ItemAwareStatus value);

   /**
   * Remove the value from the property NodeStatus
   */
    public void removeNodeStatus();

    public org.semanticwb.process.model.ItemAwareStatus getNodeStatus();

    public String getNodeComment();

    public void setNodeComment(String value);
}
