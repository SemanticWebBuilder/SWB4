package org.semanticwb.social.base;

public interface ImageableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty social_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#photo");
    public static final org.semanticwb.platform.SemanticClass social_Imageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Imageable");

    public String getPhoto();

    public void setPhoto(String value);
}
