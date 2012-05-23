package org.semanticwb.social.base;

public interface Video_ableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty social_video=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#video");
    public static final org.semanticwb.platform.SemanticClass social_Video_able=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Video_able");

    public String getVideo();

    public void setVideo(String value);
}
