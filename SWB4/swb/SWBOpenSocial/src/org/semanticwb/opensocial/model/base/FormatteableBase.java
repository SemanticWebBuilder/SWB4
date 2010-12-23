package org.semanticwb.opensocial.model.base;

public interface FormatteableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty data_formatted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#formatted");
    public static final org.semanticwb.platform.SemanticClass opensocial_Formatteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial#formatteable");

    public String getFormatted();

    public void setFormatted(String value);
}
