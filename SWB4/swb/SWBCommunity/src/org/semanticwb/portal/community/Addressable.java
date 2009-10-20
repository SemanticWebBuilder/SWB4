package org.semanticwb.portal.community;

public interface Addressable extends org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_extNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_intNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#intNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_state=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#state");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_streetName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#streetName");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_cityCouncil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#cityCouncil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#city");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Addressable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Addressable");
    public String getExtNumber();
    public void setExtNumber(String extNumber);
    public String getIntNumber();
    public void setIntNumber(String intNumber);
    public String getState();
    public void setState(String state);
    public String getStreetName();
    public void setStreetName(String streetName);
    public String getCityCouncil();
    public void setCityCouncil(String cityCouncil);
    public String getCity();
    public void setCity(String city);
}
