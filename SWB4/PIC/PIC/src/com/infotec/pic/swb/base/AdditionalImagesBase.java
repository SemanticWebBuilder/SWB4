package com.infotec.pic.swb.base;

public interface AdditionalImagesBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty pic_hasImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasImage");
    public static final org.semanticwb.platform.SemanticClass pic_AdditionalImages=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AdditionalImages");

    public java.util.Iterator<String> listImages();

    public void addImage(String value);
    public void removeAllImage();
    public void removeImage(String value);
}
