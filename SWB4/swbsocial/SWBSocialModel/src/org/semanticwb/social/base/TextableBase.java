package org.semanticwb.social.base;

public interface TextableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty social_msg_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#msg_Text");
    public static final org.semanticwb.platform.SemanticClass social_Textable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Textable");

    public String getMsg_Text();

    public void setMsg_Text(String value);
}
