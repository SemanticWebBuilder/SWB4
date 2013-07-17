package com.infotec.pic.swb.base;

public interface SepomexBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty pic_clave=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#clave");
    public static final org.semanticwb.platform.SemanticClass pic_Sepomex=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Sepomex");

    public String getClave();

    public void setClave(String value);
}
