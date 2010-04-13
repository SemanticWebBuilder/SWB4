package org.semanticwb.process.model.base;

public interface AuditableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Auditing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Auditing");
    public static final org.semanticwb.platform.SemanticProperty swp_auditing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#auditing");
    public static final org.semanticwb.platform.SemanticClass swp_Auditable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Auditable");

    public void setAuditing(org.semanticwb.process.model.Auditing auditing);

    public void removeAuditing();

    public org.semanticwb.process.model.Auditing getAuditing();
}
