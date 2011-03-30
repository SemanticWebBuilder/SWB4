package org.semanticwb.process.model.base;

public interface ResourceAssignmentableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ResourceAssignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ResourceAssignment");
    public static final org.semanticwb.platform.SemanticProperty swp_resourceAssignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#resourceAssignment");
    public static final org.semanticwb.platform.SemanticClass swp_ResourceAssignmentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ResourceAssignmentable");

   /**
   * Sets a value from the property ResourceAssignment
   * @param value An instance of org.semanticwb.process.model.ResourceAssignment
   */
    public void setResourceAssignment(org.semanticwb.process.model.ResourceAssignment value);

   /**
   * Remove the value from the property ResourceAssignment
   */
    public void removeResourceAssignment();

    public org.semanticwb.process.model.ResourceAssignment getResourceAssignment();
}
