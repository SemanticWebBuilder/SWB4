package org.semanticwb.model;

public interface Expirable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#expiration");
    public static final org.semanticwb.platform.SemanticClass swb_Expirable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Expirable");
    public java.util.Date getExpiration();
    public void setExpiration(java.util.Date expiration);
}
