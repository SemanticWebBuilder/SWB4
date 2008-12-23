package org.semanticwb.repository;

public interface Lockable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty jcr_lockOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockOwner");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockIsDeep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockIsDeep");
    public static final org.semanticwb.platform.SemanticClass mix_Lockable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#lockable");
    public String getLockOwner();
    public void setLockOwner(String lockOwner);
    public boolean isLockIsDeep();
    public void setLockIsDeep(boolean lockIsDeep);
}
