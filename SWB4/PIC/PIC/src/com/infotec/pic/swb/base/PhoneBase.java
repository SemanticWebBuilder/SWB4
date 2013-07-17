package com.infotec.pic.swb.base;


public abstract class PhoneBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Número de teléfono
   */
    public static final org.semanticwb.platform.SemanticProperty pic_phoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#phoneNumber");
    public static final org.semanticwb.platform.SemanticProperty pic_countryCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#countryCode");
    public static final org.semanticwb.platform.SemanticClass pic_PhoneType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#PhoneType");
    public static final org.semanticwb.platform.SemanticProperty pic_phoneType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#phoneType");
    public static final org.semanticwb.platform.SemanticProperty pic_areaCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#areaCode");
    public static final org.semanticwb.platform.SemanticClass pic_Phone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Phone");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Phone");

    public static class ClassMgr
    {
       /**
       * Returns a list of Phone for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Phone
       */

        public static java.util.Iterator<com.infotec.pic.swb.Phone> listPhones(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Phone for all models
       * @return Iterator of com.infotec.pic.swb.Phone
       */

        public static java.util.Iterator<com.infotec.pic.swb.Phone> listPhones()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone>(it, true);
        }

        public static com.infotec.pic.swb.Phone createPhone(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Phone.ClassMgr.createPhone(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Phone
       * @param id Identifier for com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.Phone
       * @return A com.infotec.pic.swb.Phone
       */
        public static com.infotec.pic.swb.Phone getPhone(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Phone)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Phone
       * @param id Identifier for com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.Phone
       * @return A com.infotec.pic.swb.Phone
       */
        public static com.infotec.pic.swb.Phone createPhone(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Phone)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Phone
       * @param id Identifier for com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.Phone
       */
        public static void removePhone(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Phone
       * @param id Identifier for com.infotec.pic.swb.Phone
       * @param model Model of the com.infotec.pic.swb.Phone
       * @return true if the com.infotec.pic.swb.Phone exists, false otherwise
       */

        public static boolean hasPhone(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhone(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Phone with a determined PhoneType
       * @param value PhoneType of the type com.infotec.pic.swb.PhoneType
       * @param model Model of the com.infotec.pic.swb.Phone
       * @return Iterator with all the com.infotec.pic.swb.Phone
       */

        public static java.util.Iterator<com.infotec.pic.swb.Phone> listPhoneByPhoneType(com.infotec.pic.swb.PhoneType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_phoneType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Phone with a determined PhoneType
       * @param value PhoneType of the type com.infotec.pic.swb.PhoneType
       * @return Iterator with all the com.infotec.pic.swb.Phone
       */

        public static java.util.Iterator<com.infotec.pic.swb.Phone> listPhoneByPhoneType(com.infotec.pic.swb.PhoneType value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Phone> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_phoneType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PhoneBase.ClassMgr getPhoneClassMgr()
    {
        return new PhoneBase.ClassMgr();
    }

   /**
   * Constructs a PhoneBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Phone
   */
    public PhoneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the PhoneNumber property
* @return String with the PhoneNumber
*/
    public String getPhoneNumber()
    {
        return getSemanticObject().getProperty(pic_phoneNumber);
    }

/**
* Sets the PhoneNumber property
* @param value long with the PhoneNumber
*/
    public void setPhoneNumber(String value)
    {
        getSemanticObject().setProperty(pic_phoneNumber, value);
    }

/**
* Gets the CountryCode property
* @return String with the CountryCode
*/
    public String getCountryCode()
    {
        return getSemanticObject().getProperty(pic_countryCode);
    }

/**
* Sets the CountryCode property
* @param value long with the CountryCode
*/
    public void setCountryCode(String value)
    {
        getSemanticObject().setProperty(pic_countryCode, value);
    }
   /**
   * Sets the value for the property PhoneType
   * @param value PhoneType to set
   */

    public void setPhoneType(com.infotec.pic.swb.PhoneType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_phoneType, value.getSemanticObject());
        }else
        {
            removePhoneType();
        }
    }
   /**
   * Remove the value for PhoneType property
   */

    public void removePhoneType()
    {
        getSemanticObject().removeProperty(pic_phoneType);
    }

   /**
   * Gets the PhoneType
   * @return a com.infotec.pic.swb.PhoneType
   */
    public com.infotec.pic.swb.PhoneType getPhoneType()
    {
         com.infotec.pic.swb.PhoneType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_phoneType);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.PhoneType)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AreaCode property
* @return String with the AreaCode
*/
    public String getAreaCode()
    {
        return getSemanticObject().getProperty(pic_areaCode);
    }

/**
* Sets the AreaCode property
* @param value long with the AreaCode
*/
    public void setAreaCode(String value)
    {
        getSemanticObject().setProperty(pic_areaCode, value);
    }
}
