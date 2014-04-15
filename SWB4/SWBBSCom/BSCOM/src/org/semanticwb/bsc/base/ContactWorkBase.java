package org.semanticwb.bsc.base;


   /**
   * Persiste los datos de contacto de trabajo de un usuario 
   */
public abstract class ContactWorkBase extends org.semanticwb.model.UserTypeDef implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_phone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#phone");
    public static final org.semanticwb.platform.SemanticProperty bsc_employment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#employment");
    public static final org.semanticwb.platform.SemanticProperty bsc_location=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#location");
    public static final org.semanticwb.platform.SemanticProperty bsc_lada=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#lada");
    public static final org.semanticwb.platform.SemanticProperty bsc_ext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#ext");
    public static final org.semanticwb.platform.SemanticProperty bsc_twitter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#twitter");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty bsc_chief=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#chief");
   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty bsc_area_=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#area_");
    public static final org.semanticwb.platform.SemanticProperty bsc_skype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#skype");
   /**
   * Persiste los datos de contacto de trabajo de un usuario
   */
    public static final org.semanticwb.platform.SemanticClass bsc_ContactWork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ContactWork");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ContactWork");

    public static class ClassMgr
    {
       /**
       * Returns a list of ContactWork for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.ContactWork
       */

        public static java.util.Iterator<org.semanticwb.bsc.ContactWork> listContactWorks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.ContactWork>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.ContactWork for all models
       * @return Iterator of org.semanticwb.bsc.ContactWork
       */

        public static java.util.Iterator<org.semanticwb.bsc.ContactWork> listContactWorks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.ContactWork>(it, true);
        }

        public static org.semanticwb.bsc.ContactWork createContactWork(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.ContactWork.ClassMgr.createContactWork(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.ContactWork
       * @param id Identifier for org.semanticwb.bsc.ContactWork
       * @param model Model of the org.semanticwb.bsc.ContactWork
       * @return A org.semanticwb.bsc.ContactWork
       */
        public static org.semanticwb.bsc.ContactWork getContactWork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.ContactWork)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.ContactWork
       * @param id Identifier for org.semanticwb.bsc.ContactWork
       * @param model Model of the org.semanticwb.bsc.ContactWork
       * @return A org.semanticwb.bsc.ContactWork
       */
        public static org.semanticwb.bsc.ContactWork createContactWork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.ContactWork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.ContactWork
       * @param id Identifier for org.semanticwb.bsc.ContactWork
       * @param model Model of the org.semanticwb.bsc.ContactWork
       */
        public static void removeContactWork(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.ContactWork
       * @param id Identifier for org.semanticwb.bsc.ContactWork
       * @param model Model of the org.semanticwb.bsc.ContactWork
       * @return true if the org.semanticwb.bsc.ContactWork exists, false otherwise
       */

        public static boolean hasContactWork(String id, org.semanticwb.model.SWBModel model)
        {
            return (getContactWork(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.ContactWork with a determined Chief
       * @param value Chief of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.ContactWork
       * @return Iterator with all the org.semanticwb.bsc.ContactWork
       */

        public static java.util.Iterator<org.semanticwb.bsc.ContactWork> listContactWorkByChief(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.ContactWork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_chief, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.ContactWork with a determined Chief
       * @param value Chief of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.ContactWork
       */

        public static java.util.Iterator<org.semanticwb.bsc.ContactWork> listContactWorkByChief(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.ContactWork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_chief,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.ContactWork with a determined Area_
       * @param value Area_ of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.ContactWork
       * @return Iterator with all the org.semanticwb.bsc.ContactWork
       */

        public static java.util.Iterator<org.semanticwb.bsc.ContactWork> listContactWorkByArea_(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.ContactWork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_area_, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.ContactWork with a determined Area_
       * @param value Area_ of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.ContactWork
       */

        public static java.util.Iterator<org.semanticwb.bsc.ContactWork> listContactWorkByArea_(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.ContactWork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_area_,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ContactWorkBase.ClassMgr getContactWorkClassMgr()
    {
        return new ContactWorkBase.ClassMgr();
    }

   /**
   * Constructs a ContactWorkBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ContactWork
   */
    public ContactWorkBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Phone property
* @return int with the Phone
*/
    public int getPhone()
    {
        return getSemanticObject().getIntProperty(bsc_phone);
    }

/**
* Sets the Phone property
* @param value long with the Phone
*/
    public void setPhone(int value)
    {
        getSemanticObject().setIntProperty(bsc_phone, value);
    }

/**
* Gets the Employment property
* @return String with the Employment
*/
    public String getEmployment()
    {
        return getSemanticObject().getProperty(bsc_employment);
    }

/**
* Sets the Employment property
* @param value long with the Employment
*/
    public void setEmployment(String value)
    {
        getSemanticObject().setProperty(bsc_employment, value);
    }

/**
* Gets the Location property
* @return String with the Location
*/
    public String getLocation()
    {
        return getSemanticObject().getProperty(bsc_location);
    }

/**
* Sets the Location property
* @param value long with the Location
*/
    public void setLocation(String value)
    {
        getSemanticObject().setProperty(bsc_location, value);
    }

/**
* Gets the Lada property
* @return int with the Lada
*/
    public int getLada()
    {
        return getSemanticObject().getIntProperty(bsc_lada);
    }

/**
* Sets the Lada property
* @param value long with the Lada
*/
    public void setLada(int value)
    {
        getSemanticObject().setIntProperty(bsc_lada, value);
    }

/**
* Gets the Ext property
* @return int with the Ext
*/
    public int getExt()
    {
        return getSemanticObject().getIntProperty(bsc_ext);
    }

/**
* Sets the Ext property
* @param value long with the Ext
*/
    public void setExt(int value)
    {
        getSemanticObject().setIntProperty(bsc_ext, value);
    }

/**
* Gets the Twitter property
* @return String with the Twitter
*/
    public String getTwitter()
    {
        return getSemanticObject().getProperty(bsc_twitter);
    }

/**
* Sets the Twitter property
* @param value long with the Twitter
*/
    public void setTwitter(String value)
    {
        getSemanticObject().setProperty(bsc_twitter, value);
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
   * Sets the value for the property Chief
   * @param value Chief to set
   */

    public void setChief(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_chief, value.getSemanticObject());
        }else
        {
            removeChief();
        }
    }
   /**
   * Remove the value for Chief property
   */

    public void removeChief()
    {
        getSemanticObject().removeProperty(bsc_chief);
    }

   /**
   * Gets the Chief
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getChief()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_chief);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Area_
   * @param value Area_ to set
   */

    public void setArea_(org.semanticwb.model.UserGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_area_, value.getSemanticObject());
        }else
        {
            removeArea_();
        }
    }
   /**
   * Remove the value for Area_ property
   */

    public void removeArea_()
    {
        getSemanticObject().removeProperty(bsc_area_);
    }

   /**
   * Gets the Area_
   * @return a org.semanticwb.model.UserGroup
   */
    public org.semanticwb.model.UserGroup getArea_()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_area_);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
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
* Gets the Skype property
* @return String with the Skype
*/
    public String getSkype()
    {
        return getSemanticObject().getProperty(bsc_skype);
    }

/**
* Sets the Skype property
* @param value long with the Skype
*/
    public void setSkype(String value)
    {
        getSemanticObject().setProperty(bsc_skype, value);
    }
}
