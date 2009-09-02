package org.semanticwb.portal.community;

public interface Contactable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#name");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_email=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#email");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_phoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#phoneNumber");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Contactable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Contactable");
    public String getName();
    public void setName(String name);
    public String getEmail();
    public void setEmail(String email);
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);
}
