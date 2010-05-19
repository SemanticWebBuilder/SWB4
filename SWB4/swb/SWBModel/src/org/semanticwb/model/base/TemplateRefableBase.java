package org.semanticwb.model.base;

public interface TemplateRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRef");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs();
    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs();

    public void addTemplateRef(org.semanticwb.model.TemplateRef value);

    public void removeAllTemplateRef();

    public void removeTemplateRef(org.semanticwb.model.TemplateRef value);

    public org.semanticwb.model.TemplateRef getTemplateRef();
}
