package org.semanticwb.process.model.base;

public interface ProcessTraceableBase extends org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_endedby=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endedby");
    public static final org.semanticwb.platform.SemanticProperty swp_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ended");
    public static final org.semanticwb.platform.SemanticProperty swp_assignedto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#assignedto");
    public static final org.semanticwb.platform.SemanticProperty swp_assigned=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#assigned");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessTraceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessTraceable");

   /**
   * Sets a value from the property Endedby
   * @param value An instance of org.semanticwb.model.User
   */
    public void setEndedby(org.semanticwb.model.User value);

   /**
   * Remove the value from the property Endedby
   */
    public void removeEndedby();

    public org.semanticwb.model.User getEndedby();

    public java.util.Date getEnded();

    public void setEnded(java.util.Date value);

   /**
   * Sets a value from the property Assignedto
   * @param value An instance of org.semanticwb.model.User
   */
    public void setAssignedto(org.semanticwb.model.User value);

   /**
   * Remove the value from the property Assignedto
   */
    public void removeAssignedto();

    public org.semanticwb.model.User getAssignedto();

    public java.util.Date getAssigned();

    public void setAssigned(java.util.Date value);
}
