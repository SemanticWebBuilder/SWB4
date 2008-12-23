package org.semanticwb.model;

public interface TemplateRefable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRef");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs();

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref);

    public void removeAllTemplateRef();

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref);

    public TemplateRef getTemplateRef();
}
