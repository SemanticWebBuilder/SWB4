package org.semanticwb.process.model.base;

public interface ModelableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasProperty");
    public static final org.semanticwb.platform.SemanticClass swp_Modelable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Modelable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listProperties();
    public boolean hasProperty(org.semanticwb.process.model.Property property);

    public void addProperty(org.semanticwb.process.model.Property property);

    public void removeAllProperty();

    public void removeProperty(org.semanticwb.process.model.Property property);

    public org.semanticwb.process.model.Property getProperty();
}
