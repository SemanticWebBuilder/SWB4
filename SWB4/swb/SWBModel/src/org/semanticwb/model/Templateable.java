package org.semanticwb.model;

public interface Templateable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplate");
    public static final org.semanticwb.platform.SemanticClass swb_Templateable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Templateable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> listTemplates();

    public void addTemplate(org.semanticwb.model.Template template);

    public void removeAllTemplate();

    public void removeTemplate(org.semanticwb.model.Template template);

    public Template getTemplate();
}
