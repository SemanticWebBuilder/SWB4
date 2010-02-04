package org.semanticwb.process.model.base;

public interface AssignableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Assignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Assignment");
    public static final org.semanticwb.platform.SemanticProperty swp_hasAssignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasAssignment");
    public static final org.semanticwb.platform.SemanticClass swp_Assignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Assignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> listAssignments();
    public boolean hasAssignment(org.semanticwb.process.model.Assignment assignment);

    public void addAssignment(org.semanticwb.process.model.Assignment assignment);

    public void removeAllAssignment();

    public void removeAssignment(org.semanticwb.process.model.Assignment assignment);

    public org.semanticwb.process.model.Assignment getAssignment();
}
