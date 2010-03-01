package org.semanticwb.portal.community.base;

public interface AddressableBase extends org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_extNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_intNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#intNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_state=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#state");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_streetName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#streetName");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_cityCouncil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#cityCouncil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#city");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Addressable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Addressable");

    public String getExtNumber();

    public void setExtNumber(String value);

    public String getIntNumber();

    public void setIntNumber(String value);

    public String getState();

    public void setState(String value);

    public String getStreetName();

    public void setStreetName(String value);

    public String getCityCouncil();

    public void setCityCouncil(String value);

    public String getCity();

    public void setCity(String value);
}
