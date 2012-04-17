package org.semanticwb.social.base;

public interface SecreatableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty social_secreatKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#secreatKey");
    public static final org.semanticwb.platform.SemanticClass social_Secreatable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Secreatable");

    public String getSecreatKey();

    public void setSecreatKey(String value);
}
