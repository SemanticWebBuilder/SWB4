package org.semanticwb.social.base;


   /**
   * Plataforma (Sistema Operativo) desde donde se envió el mensaje de entrada. 
   */
public abstract class DevicePlatformBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Tipo de dispositivo desde donde se envía el mensaje de entrada. Android Phone, Android Table, Ipad, Windows phone, etc
   */
    public static final org.semanticwb.platform.SemanticClass social_DeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeviceType");
    public static final org.semanticwb.platform.SemanticProperty social_hasDeviceTypeInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasDeviceTypeInv");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInDevicePlatformInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInDevicePlatformInv");
   /**
   * Plataforma (Sistema Operativo) desde donde se envió el mensaje de entrada.
   */
    public static final org.semanticwb.platform.SemanticClass social_DevicePlatform=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DevicePlatform");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DevicePlatform");

    public static class ClassMgr
    {
       /**
       * Returns a list of DevicePlatform for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatforms(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.DevicePlatform for all models
       * @return Iterator of org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatforms()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return A org.semanticwb.social.DevicePlatform
       */
        public static org.semanticwb.social.DevicePlatform getDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DevicePlatform)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return A org.semanticwb.social.DevicePlatform
       */
        public static org.semanticwb.social.DevicePlatform createDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DevicePlatform)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       */
        public static void removeDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return true if the org.semanticwb.social.DevicePlatform exists, false otherwise
       */

        public static boolean hasDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDevicePlatform(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined DeviceTypeInv
       * @param value DeviceTypeInv of the type org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByDeviceTypeInv(org.semanticwb.social.DeviceType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasDeviceTypeInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined DeviceTypeInv
       * @param value DeviceTypeInv of the type org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByDeviceTypeInv(org.semanticwb.social.DeviceType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasDeviceTypeInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined PostInDevicePlatformInv
       * @param value PostInDevicePlatformInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByPostInDevicePlatformInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDevicePlatformInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined PostInDevicePlatformInv
       * @param value PostInDevicePlatformInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByPostInDevicePlatformInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDevicePlatformInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DevicePlatformBase.ClassMgr getDevicePlatformClassMgr()
    {
        return new DevicePlatformBase.ClassMgr();
    }

   /**
   * Constructs a DevicePlatformBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DevicePlatform
   */
    public DevicePlatformBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.DeviceType
   * @return A GenericIterator with all the org.semanticwb.social.DeviceType
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> listDeviceTypeInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType>(getSemanticObject().listObjectProperties(social_hasDeviceTypeInv));
    }

   /**
   * Gets true if has a DeviceTypeInv
   * @param value org.semanticwb.social.DeviceType to verify
   * @return true if the org.semanticwb.social.DeviceType exists, false otherwise
   */
    public boolean hasDeviceTypeInv(org.semanticwb.social.DeviceType value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasDeviceTypeInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the DeviceTypeInv
   * @return a org.semanticwb.social.DeviceType
   */
    public org.semanticwb.social.DeviceType getDeviceTypeInv()
    {
         org.semanticwb.social.DeviceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasDeviceTypeInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.DeviceType)obj.createGenericInstance();
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
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInDevicePlatformInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInDevicePlatformInv));
    }

   /**
   * Gets true if has a PostInDevicePlatformInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInDevicePlatformInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInDevicePlatformInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInDevicePlatformInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInDevicePlatformInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInDevicePlatformInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
}
