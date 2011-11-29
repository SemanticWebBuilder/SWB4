package org.semanticwb.process.model.base;

public interface ResourceAssignmentableBase extends org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Referensable,org.semanticwb.model.RuleRefable,org.semanticwb.model.RoleRefable
{
    public static final org.semanticwb.platform.SemanticProperty swp_resourceAssignationRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#resourceAssignationRule");
    public static final org.semanticwb.platform.SemanticClass swp_ResourceAssignmentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ResourceAssignmentable");

    public int getResourceAssignationRule();

    public void setResourceAssignationRule(int value);
}
