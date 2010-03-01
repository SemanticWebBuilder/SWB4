package org.semanticwb.portal.community.base;

public interface FacilitiesEnableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_foodCourt=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#foodCourt");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_elevator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#elevator");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_parkingLot=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#parkingLot");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_impairedPeopleAccessible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#impairedPeopleAccessible");
    public static final org.semanticwb.platform.SemanticClass swbcomm_FacilitiesEnable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#FacilitiesEnable");

    public boolean isFoodCourt();

    public void setFoodCourt(boolean value);

    public boolean isElevator();

    public void setElevator(boolean value);

    public boolean isParkingLot();

    public void setParkingLot(boolean value);

    public boolean isImpairedPeopleAccessible();

    public void setImpairedPeopleAccessible(boolean value);
}
