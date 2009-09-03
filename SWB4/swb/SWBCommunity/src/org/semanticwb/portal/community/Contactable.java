package org.semanticwb.portal.community;

public interface Contactable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_contactPhoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactPhoneNumber");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_contactName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactName");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_contactEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactEmail");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Contactable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Contactable");
    public String getContactPhoneNumber();
    public void setContactPhoneNumber(String contactPhoneNumber);
    public String getContactName();
    public void setContactName(String contactName);
    public String getContactEmail();
    public void setContactEmail(String contactEmail);
}
