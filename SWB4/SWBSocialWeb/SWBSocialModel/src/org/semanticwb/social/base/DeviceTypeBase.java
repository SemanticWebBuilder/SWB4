package org.semanticwb.social.base;


   /**
   * Tipo de dispositivo desde donde se envía el mensaje de entrada. Android Phone, Android Table, Ipad, Windows phone, etc 
   */
public abstract class DeviceTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Plataforma (Sistema Operativo) desde donde se envió el mensaje de entrada.
   */
    public static final org.semanticwb.platform.SemanticClass social_DevicePlatform=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DevicePlatform");
   /**
   * Que tipo de plataforma es el deviceType.
   */
    public static final org.semanticwb.platform.SemanticProperty social_devicePlatform=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#devicePlatform");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInDeviceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInDeviceInv");
   /**
   * Tipo de dispositivo desde donde se envía el mensaje de entrada. Android Phone, Android Table, Ipad, Windows phone, etc
   */
    public static final org.semanticwb.platform.SemanticClass social_DeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeviceType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeviceType");

    public static class ClassMgr
    {
       /**
       * Returns a list of DeviceType for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.DeviceType for all models
       * @return Iterator of org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return A org.semanticwb.social.DeviceType
       */
        public static org.semanticwb.social.DeviceType getDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DeviceType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return A org.semanticwb.social.DeviceType
       */
        public static org.semanticwb.social.DeviceType createDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DeviceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       */
        public static void removeDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return true if the org.semanticwb.social.DeviceType exists, false otherwise
       */

        public static boolean hasDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDeviceType(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined DevicePlatform
       * @param value DevicePlatform of the type org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByDevicePlatform(org.semanticwb.social.DevicePlatform value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_devicePlatform, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined DevicePlatform
       * @param value DevicePlatform of the type org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByDevicePlatform(org.semanticwb.social.DevicePlatform value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_devicePlatform,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined PostInDeviceInv
       * @param value PostInDeviceInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByPostInDeviceInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDeviceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined PostInDeviceInv
       * @param value PostInDeviceInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByPostInDeviceInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDeviceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DeviceTypeBase.ClassMgr getDeviceTypeClassMgr()
    {
        return new DeviceTypeBase.ClassMgr();
    }

   /**
   * Constructs a DeviceTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DeviceType
   */
    public DeviceTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Sets the value for the property DevicePlatform
   * @param value DevicePlatform to set
   */

    public void setDevicePlatform(org.semanticwb.social.DevicePlatform value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_devicePlatform, value.getSemanticObject());
        }else
        {
            removeDevicePlatform();
        }
    }
   /**
   * Remove the value for DevicePlatform property
   */

    public void removeDevicePlatform()
    {
        getSemanticObject().removeProperty(social_devicePlatform);
    }

   /**
   * Gets the DevicePlatform
   * @return a org.semanticwb.social.DevicePlatform
   */
    public org.semanticwb.social.DevicePlatform getDevicePlatform()
    {
         org.semanticwb.social.DevicePlatform ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_devicePlatform);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.DevicePlatform)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInDeviceInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInDeviceInv));
    }

   /**
   * Gets true if has a PostInDeviceInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInDeviceInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInDeviceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInDeviceInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInDeviceInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInDeviceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
}
