package org.semanticwb.social.base;

public interface ComentableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty social_comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#comment");
    public static final org.semanticwb.platform.SemanticClass social_Comentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Comentable");

    public String getComment();

    public void setComment(String value);
}
