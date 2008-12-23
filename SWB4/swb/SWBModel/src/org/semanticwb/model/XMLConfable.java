package org.semanticwb.model;

public interface XMLConfable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_xmlConf=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xmlConf");
    public static final org.semanticwb.platform.SemanticClass swb_XMLConfable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#XMLConfable");
    public String getXmlConf();
    public void setXmlConf(String xmlConf);
}
