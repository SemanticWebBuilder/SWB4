package com.infotec.pic.swb.base;

public interface AdditionalContactDataBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass pic_Phone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Phone");
    public static final org.semanticwb.platform.SemanticProperty pic_hasPhone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasPhone");
    public static final org.semanticwb.platform.SemanticClass pic_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#SocialNetwork");
    public static final org.semanticwb.platform.SemanticProperty pic_hasSocial=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasSocial");
   /**
   * Correos electónicos adicionales 
   */
    public static final org.semanticwb.platform.SemanticProperty pic_hasEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#hasEmail");
    public static final org.semanticwb.platform.SemanticClass pic_AdditionalContactData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AdditionalContactData");

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone> listPhones();
    public boolean hasPhone(com.infotec.pic.swb.Phone value);

   /**
   * Adds the Phone
   * @param value An instance of com.infotec.pic.swb.Phone
   */
    public void addPhone(com.infotec.pic.swb.Phone value);

   /**
   * Remove all the values for the property Phone
   */
    public void removeAllPhone();

   /**
   * Remove a value from the property Phone
   * @param value An instance of com.infotec.pic.swb.Phone
   */
    public void removePhone(com.infotec.pic.swb.Phone value);

/**
* Gets the Phone
* @return a instance of com.infotec.pic.swb.Phone
*/
    public com.infotec.pic.swb.Phone getPhone();

    public org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> listSocials();
    public boolean hasSocial(com.infotec.pic.swb.SocialNetwork value);

   /**
   * Adds the Social
   * @param value An instance of com.infotec.pic.swb.SocialNetwork
   */
    public void addSocial(com.infotec.pic.swb.SocialNetwork value);

   /**
   * Remove all the values for the property Social
   */
    public void removeAllSocial();

   /**
   * Remove a value from the property Social
   * @param value An instance of com.infotec.pic.swb.SocialNetwork
   */
    public void removeSocial(com.infotec.pic.swb.SocialNetwork value);

/**
* Gets the Social
* @return a instance of com.infotec.pic.swb.SocialNetwork
*/
    public com.infotec.pic.swb.SocialNetwork getSocial();

    public java.util.Iterator<String> listEmails();

    public void addEmail(String value);
    public void removeAllEmail();
    public void removeEmail(String value);
}
