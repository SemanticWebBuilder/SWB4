package org.semanticwb.portal.community;

public interface Addressable extends org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_extNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_intNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#intNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_street=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#street");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#city");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Addressable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Addressable");
    public String getExtNumber();
    public void setExtNumber(String extNumber);
    public String getIntNumber();
    public void setIntNumber(String intNumber);
    public String getStreet();
    public void setStreet(String street);
    public String getCity();
    public void setCity(String city);
}
