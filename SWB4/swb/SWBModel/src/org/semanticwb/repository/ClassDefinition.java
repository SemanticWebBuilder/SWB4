package org.semanticwb.repository;

public interface ClassDefinition extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty jcr_primaryItemName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryItemName");
    public static final org.semanticwb.platform.SemanticProperty jcr_orderableChildNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#orderableChildNodes");
    public static final org.semanticwb.platform.SemanticProperty mix_mixin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/mix/1.0#mixin");
    public static final org.semanticwb.platform.SemanticClass nt_ChildNodeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#childNodeDefinition");
    public static final org.semanticwb.platform.SemanticProperty jcr_childNodeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#childNodeDefinition");
    public static final org.semanticwb.platform.SemanticClass nt_PropertyDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#propertyDefinition");
    public static final org.semanticwb.platform.SemanticProperty jcr_propertyDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#propertyDefinition");
    public static final org.semanticwb.platform.SemanticProperty jcr_supertypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#supertypes");
    public static final org.semanticwb.platform.SemanticClass swbrep_ClassDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ClassDefinition");
    public boolean isOrderableChildNodes();
    public void setOrderableChildNodes(boolean orderableChildNodes);
}
