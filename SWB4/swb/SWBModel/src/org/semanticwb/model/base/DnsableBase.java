package org.semanticwb.model.base;

public interface DnsableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dns");
    public static final org.semanticwb.platform.SemanticClass swb_Dnsable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dnsable");

    public String getDns();

    public void setDns(String value);
}
