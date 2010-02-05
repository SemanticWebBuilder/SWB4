package org.semanticwb.process.model.base;

public interface AttachableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
    public static final org.semanticwb.platform.SemanticProperty swp_target=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#target");
    public static final org.semanticwb.platform.SemanticClass swp_Attachable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Attachable");

    public void setTarget(org.semanticwb.process.model.Activity activity);

    public void removeTarget();

    public org.semanticwb.process.model.Activity getTarget();
}
