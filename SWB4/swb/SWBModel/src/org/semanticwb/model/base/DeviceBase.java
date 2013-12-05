package org.semanticwb.model.base;


   /**
   * Un dispositivo es un elemento que tiene la capacidad de leer una Página Web, por ejemplo: un PDA, una PC o un celular. En SemanticWebBuilder se pueden encontrar algunos dispositivos ya definidos. 
   */
public abstract class DeviceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_dvcUserAgent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dvcUserAgent");
   /**
   * Un dispositivo es un elemento que tiene la capacidad de leer una Página Web, por ejemplo: un PDA, una PC o un celular. En SemanticWebBuilder se pueden encontrar algunos dispositivos ya definidos.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    public static final org.semanticwb.platform.SemanticProperty swb_dvcParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dvcParent");
    public static final org.semanticwb.platform.SemanticProperty swb_hasDvcChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasDvcChild");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");

    public static class ClassMgr
    {
       /**
       * Returns a list of Device for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDevices(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Device for all models
       * @return Iterator of org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDevices()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.Device
       * @param id Identifier for org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Device
       * @return A org.semanticwb.model.Device
       */
        public static org.semanticwb.model.Device getDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Device)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Device
       * @param id Identifier for org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Device
       * @return A org.semanticwb.model.Device
       */
        public static org.semanticwb.model.Device createDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Device)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Device
       * @param id Identifier for org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Device
       */
        public static void removeDevice(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Device
       * @param id Identifier for org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Device
       * @return true if the org.semanticwb.model.Device exists, false otherwise
       */

        public static boolean hasDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDevice(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined Parent
       * @param value Parent of the type org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByParent(org.semanticwb.model.Device value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_dvcParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined Parent
       * @param value Parent of the type org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByParent(org.semanticwb.model.Device value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_dvcParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined Child
       * @param value Child of the type org.semanticwb.model.Device
       * @param model Model of the org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByChild(org.semanticwb.model.Device value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasDvcChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Device with a determined Child
       * @param value Child of the type org.semanticwb.model.Device
       * @return Iterator with all the org.semanticwb.model.Device
       */

        public static java.util.Iterator<org.semanticwb.model.Device> listDeviceByChild(org.semanticwb.model.Device value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasDvcChild,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DeviceBase.ClassMgr getDeviceClassMgr()
    {
        return new DeviceBase.ClassMgr();
    }

   /**
   * Constructs a DeviceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Device
   */
    public DeviceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the UserAgent property
* @return String with the UserAgent
*/
    public String getUserAgent()
    {
        return getSemanticObject().getProperty(swb_dvcUserAgent);
    }

/**
* Sets the UserAgent property
* @param value long with the UserAgent
*/
    public void setUserAgent(String value)
    {
        getSemanticObject().setProperty(swb_dvcUserAgent, value);
    }
   /**
   * Sets the value for the property Parent
   * @param value Parent to set
   */

    public void setParent(org.semanticwb.model.Device value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_dvcParent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }
   /**
   * Remove the value for Parent property
   */

    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_dvcParent);
    }

   /**
   * Gets the Parent
   * @return a org.semanticwb.model.Device
   */
    public org.semanticwb.model.Device getParent()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_dvcParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.model.Device
   * @return A GenericIterator with all the org.semanticwb.model.Device
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Device> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Device>(getSemanticObject().listObjectProperties(swb_hasDvcChild));
    }

   /**
   * Gets true if has a Child
   * @param value org.semanticwb.model.Device to verify
   * @return true if the org.semanticwb.model.Device exists, false otherwise
   */
    public boolean hasChild(org.semanticwb.model.Device value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasDvcChild,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Child
   * @return a org.semanticwb.model.Device
   */
    public org.semanticwb.model.Device getChild()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasDvcChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
