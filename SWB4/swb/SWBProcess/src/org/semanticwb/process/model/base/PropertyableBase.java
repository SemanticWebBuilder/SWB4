package org.semanticwb.process.model.base;

public interface PropertyableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasProperty");
    public static final org.semanticwb.platform.SemanticClass swp_Propertyable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Propertyable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listProperties();
    public boolean hasProperty(org.semanticwb.process.model.Property value);

    public void addProperty(org.semanticwb.process.model.Property value);

    public void removeAllProperty();

    public void removeProperty(org.semanticwb.process.model.Property value);

    public org.semanticwb.process.model.Property getProperty();
}
