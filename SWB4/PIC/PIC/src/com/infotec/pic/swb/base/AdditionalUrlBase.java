package com.infotec.pic.swb.base;

public interface AdditionalUrlBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty pic_url=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#url");
    public static final org.semanticwb.platform.SemanticClass pic_AdditionalUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AdditionalUrl");

    public String getUrl();

    public void setUrl(String value);
}
