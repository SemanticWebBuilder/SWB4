package org.semanticwb.jcr283.repository.model.base;

public interface LifecycleBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass nt_Base=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    public static final org.semanticwb.platform.SemanticProperty jcr_lifecyclePolicy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lifecyclePolicy");
    public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
    public static final org.semanticwb.platform.SemanticProperty jcr_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#name");
    public static final org.semanticwb.platform.SemanticProperty jcr_currentLifecycleState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#currentLifecycleState");
    public static final org.semanticwb.platform.SemanticClass mix_Lifecycle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#lifecycle");

    public void setLifecyclePolicy(org.semanticwb.jcr283.repository.model.Base base);

    public void removeLifecyclePolicy();

    public org.semanticwb.jcr283.repository.model.Base getLifecyclePolicy();

    public String getName();

    public void setName(String value);

    public String getCurrentLifecycleState();

    public void setCurrentLifecycleState(String value);
}
