package org.semanticwb.social.base;

public interface SecreteableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty social_secretKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#secretKey");
    public static final org.semanticwb.platform.SemanticProperty social_appKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#appKey");
    public static final org.semanticwb.platform.SemanticClass social_Secreteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Secreteable");

    public String getSecretKey();

    public void setSecretKey(String value);

    public String getAppKey();

    public void setAppKey(String value);
}
