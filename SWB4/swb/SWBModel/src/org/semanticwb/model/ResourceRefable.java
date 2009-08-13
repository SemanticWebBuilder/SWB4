package org.semanticwb.model;

public interface ResourceRefable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRef");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefs();
    public boolean hasResourceRef(org.semanticwb.model.ResourceRef resourceref);

    public void addResourceRef(org.semanticwb.model.ResourceRef resourceref);

    public void removeAllResourceRef();

    public void removeResourceRef(org.semanticwb.model.ResourceRef resourceref);

    public ResourceRef getResourceRef();
}
