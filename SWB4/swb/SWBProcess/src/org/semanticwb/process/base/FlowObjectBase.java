package org.semanticwb.process.base;

public interface FlowObjectBase extends org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbps_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasFromConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFromConnectionObjectInv");
    public static final org.semanticwb.platform.SemanticClass swbps_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasFlowObjectInstansInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObjectInstansInv");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasToConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasToConnectionObject");
    public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swbps_parentProcessInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInv");
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty swbps_y=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#y");
    public static final org.semanticwb.platform.SemanticProperty swbps_x=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#x");
    public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");

    public java.util.Date getCreated();

    public void setCreated(java.util.Date value);

    public void setModifiedBy(org.semanticwb.model.User user);

    public void removeModifiedBy();

    public org.semanticwb.model.User getModifiedBy();

    public String getTitle();

    public void setTitle(String value);

    public String getTitle(String lang);

    public String getDisplayTitle(String lang);

    public void setTitle(String title, String lang);

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listFromConnectionObjects();
    public boolean hasFromConnectionObject(org.semanticwb.process.ConnectionObject connectionobject);

    public java.util.Date getUpdated();

    public void setUpdated(java.util.Date value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances();
    public boolean hasFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance);

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listToConnectionObjects();
    public boolean hasToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject);

    public void addToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject);

    public void removeAllToConnectionObject();

    public void removeToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject);

    public org.semanticwb.process.ConnectionObject getToConnectionObject();

    public void setParentProcess(org.semanticwb.process.Process process);

    public void removeParentProcess();

    public org.semanticwb.process.Process getParentProcess();

    public boolean isValid();

    public void setValid(boolean value);

    public void setCreator(org.semanticwb.model.User user);

    public void removeCreator();

    public org.semanticwb.model.User getCreator();

    public String getDescription();

    public void setDescription(String value);

    public String getDescription(String lang);

    public String getDisplayDescription(String lang);

    public void setDescription(String description, String lang);

    public int getY();

    public void setY(int value);

    public int getX();

    public void setX(int value);
}
