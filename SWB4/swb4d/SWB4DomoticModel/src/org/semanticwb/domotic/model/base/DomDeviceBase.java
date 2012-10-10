package org.semanticwb.domotic.model.base;


public abstract class DomDeviceBase extends org.semanticwb.domotic.model.DomItem implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb4d_devStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#devStatus");
    public static final org.semanticwb.platform.SemanticProperty swb4d_dimerizable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#dimerizable");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGateway");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domGateway");
    public static final org.semanticwb.platform.SemanticProperty swb4d_devZone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#devZone");
    public static final org.semanticwb.platform.SemanticProperty swb4d_devId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#devId");
    public static final org.semanticwb.platform.SemanticProperty swb4d_devSerial=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#devSerial");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPeriodRef");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasDomPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasDomPeriodRef");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceStat");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domDeviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domDeviceStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasGroup");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceType");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domDeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domDeviceType");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomDevice for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDevices(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomDevice for all models
       * @return Iterator of org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDevices()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice>(it, true);
        }

        public static org.semanticwb.domotic.model.DomDevice createDomDevice(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomDevice.ClassMgr.createDomDevice(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomDevice
       * @param id Identifier for org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return A org.semanticwb.domotic.model.DomDevice
       */
        public static org.semanticwb.domotic.model.DomDevice getDomDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomDevice)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomDevice
       * @param id Identifier for org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return A org.semanticwb.domotic.model.DomDevice
       */
        public static org.semanticwb.domotic.model.DomDevice createDomDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomDevice)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomDevice
       * @param id Identifier for org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       */
        public static void removeDomDevice(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomDevice
       * @param id Identifier for org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return true if the org.semanticwb.domotic.model.DomDevice exists, false otherwise
       */

        public static boolean hasDomDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomDevice(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined Permission
       * @param value Permission of the type org.semanticwb.domotic.model.DomPermission
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByPermission(org.semanticwb.domotic.model.DomPermission value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasPermission, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined Permission
       * @param value Permission of the type org.semanticwb.domotic.model.DomPermission
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByPermission(org.semanticwb.domotic.model.DomPermission value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasPermission,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomGateway
       * @param value DomGateway of the type org.semanticwb.domotic.model.DomGateway
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomGateway(org.semanticwb.domotic.model.DomGateway value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domGateway, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomGateway
       * @param value DomGateway of the type org.semanticwb.domotic.model.DomGateway
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomGateway(org.semanticwb.domotic.model.DomGateway value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domGateway,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomPeriodRef
       * @param value DomPeriodRef of the type org.semanticwb.domotic.model.DomPeriodRef
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomPeriodRef(org.semanticwb.domotic.model.DomPeriodRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomPeriodRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomPeriodRef
       * @param value DomPeriodRef of the type org.semanticwb.domotic.model.DomPeriodRef
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomPeriodRef(org.semanticwb.domotic.model.DomPeriodRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomPeriodRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomDeviceStat
       * @param value DomDeviceStat of the type org.semanticwb.domotic.model.DomDeviceStat
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomDeviceStat(org.semanticwb.domotic.model.DomDeviceStat value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDeviceStat, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomDeviceStat
       * @param value DomDeviceStat of the type org.semanticwb.domotic.model.DomDeviceStat
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomDeviceStat(org.semanticwb.domotic.model.DomDeviceStat value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDeviceStat,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomDevice(org.semanticwb.domotic.model.DomGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomDevice(org.semanticwb.domotic.model.DomGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomDeviceType
       * @param value DomDeviceType of the type org.semanticwb.domotic.model.DomDeviceType
       * @param model Model of the org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomDeviceType(org.semanticwb.domotic.model.DomDeviceType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDeviceType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDevice with a determined DomDeviceType
       * @param value DomDeviceType of the type org.semanticwb.domotic.model.DomDeviceType
       * @return Iterator with all the org.semanticwb.domotic.model.DomDevice
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDevice> listDomDeviceByDomDeviceType(org.semanticwb.domotic.model.DomDeviceType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDeviceType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomDeviceBase.ClassMgr getDomDeviceClassMgr()
    {
        return new DomDeviceBase.ClassMgr();
    }

   /**
   * Constructs a DomDeviceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomDevice
   */
    public DomDeviceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Status property
* @return int with the Status
*/
    public int getStatus()
    {
        //Override this method in DomDevice object
        return getSemanticObject().getIntProperty(swb4d_devStatus,false);
    }

/**
* Sets the Status property
* @param value long with the Status
*/
    public void setStatus(int value)
    {
        //Override this method in DomDevice object
        getSemanticObject().setIntProperty(swb4d_devStatus, value,false);
    }

/**
* Gets the Dimerizable property
* @return boolean with the Dimerizable
*/
    public boolean isDimerizable()
    {
        return getSemanticObject().getBooleanProperty(swb4d_dimerizable);
    }

/**
* Sets the Dimerizable property
* @param value long with the Dimerizable
*/
    public void setDimerizable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb4d_dimerizable, value);
    }
   /**
   * Sets the value for the property DomGateway
   * @param value DomGateway to set
   */

    public void setDomGateway(org.semanticwb.domotic.model.DomGateway value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domGateway, value.getSemanticObject());
        }else
        {
            removeDomGateway();
        }
    }
   /**
   * Remove the value for DomGateway property
   */

    public void removeDomGateway()
    {
        getSemanticObject().removeProperty(swb4d_domGateway);
    }

   /**
   * Gets the DomGateway
   * @return a org.semanticwb.domotic.model.DomGateway
   */
    public org.semanticwb.domotic.model.DomGateway getDomGateway()
    {
         org.semanticwb.domotic.model.DomGateway ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domGateway);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomGateway)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the DevZone property
* @return String with the DevZone
*/
    public String getDevZone()
    {
        return getSemanticObject().getProperty(swb4d_devZone);
    }

/**
* Sets the DevZone property
* @param value long with the DevZone
*/
    public void setDevZone(String value)
    {
        getSemanticObject().setProperty(swb4d_devZone, value);
    }

/**
* Gets the DevId property
* @return String with the DevId
*/
    public String getDevId()
    {
        return getSemanticObject().getProperty(swb4d_devId);
    }

/**
* Sets the DevId property
* @param value long with the DevId
*/
    public void setDevId(String value)
    {
        getSemanticObject().setProperty(swb4d_devId, value);
    }

/**
* Gets the Serial property
* @return String with the Serial
*/
    public String getSerial()
    {
        return getSemanticObject().getProperty(swb4d_devSerial);
    }

/**
* Sets the Serial property
* @param value long with the Serial
*/
    public void setSerial(String value)
    {
        getSemanticObject().setProperty(swb4d_devSerial, value);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomPeriodRef
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomPeriodRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef> listDomPeriodRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPeriodRef>(getSemanticObject().listObjectProperties(swb4d_hasDomPeriodRef));
    }

   /**
   * Gets true if has a DomPeriodRef
   * @param value org.semanticwb.domotic.model.DomPeriodRef to verify
   * @return true if the org.semanticwb.domotic.model.DomPeriodRef exists, false otherwise
   */
    public boolean hasDomPeriodRef(org.semanticwb.domotic.model.DomPeriodRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasDomPeriodRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomPeriodRef
   * @param value org.semanticwb.domotic.model.DomPeriodRef to add
   */

    public void addDomPeriodRef(org.semanticwb.domotic.model.DomPeriodRef value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasDomPeriodRef, value.getSemanticObject());
    }
   /**
   * Removes all the DomPeriodRef
   */

    public void removeAllDomPeriodRef()
    {
        getSemanticObject().removeProperty(swb4d_hasDomPeriodRef);
    }
   /**
   * Removes a DomPeriodRef
   * @param value org.semanticwb.domotic.model.DomPeriodRef to remove
   */

    public void removeDomPeriodRef(org.semanticwb.domotic.model.DomPeriodRef value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasDomPeriodRef,value.getSemanticObject());
    }

   /**
   * Gets the DomPeriodRef
   * @return a org.semanticwb.domotic.model.DomPeriodRef
   */
    public org.semanticwb.domotic.model.DomPeriodRef getDomPeriodRef()
    {
         org.semanticwb.domotic.model.DomPeriodRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasDomPeriodRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomPeriodRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DomDeviceStat
   * @param value DomDeviceStat to set
   */

    public void setDomDeviceStat(org.semanticwb.domotic.model.DomDeviceStat value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domDeviceStat, value.getSemanticObject());
        }else
        {
            removeDomDeviceStat();
        }
    }
   /**
   * Remove the value for DomDeviceStat property
   */

    public void removeDomDeviceStat()
    {
        getSemanticObject().removeProperty(swb4d_domDeviceStat);
    }

   /**
   * Gets the DomDeviceStat
   * @return a org.semanticwb.domotic.model.DomDeviceStat
   */
    public org.semanticwb.domotic.model.DomDeviceStat getDomDeviceStat()
    {
         org.semanticwb.domotic.model.DomDeviceStat ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domDeviceStat);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDeviceStat)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.domotic.model.DomGroup
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> listDomDevices()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup>(getSemanticObject().listObjectProperties(swb4d_hasGroup));
    }

   /**
   * Gets true if has a DomDevice
   * @param value org.semanticwb.domotic.model.DomGroup to verify
   * @return true if the org.semanticwb.domotic.model.DomGroup exists, false otherwise
   */
    public boolean hasDomDevice(org.semanticwb.domotic.model.DomGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasGroup,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomDevice
   * @param value org.semanticwb.domotic.model.DomGroup to add
   */

    public void addDomDevice(org.semanticwb.domotic.model.DomGroup value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasGroup, value.getSemanticObject());
    }
   /**
   * Removes all the DomDevice
   */

    public void removeAllDomDevice()
    {
        getSemanticObject().removeProperty(swb4d_hasGroup);
    }
   /**
   * Removes a DomDevice
   * @param value org.semanticwb.domotic.model.DomGroup to remove
   */

    public void removeDomDevice(org.semanticwb.domotic.model.DomGroup value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasGroup,value.getSemanticObject());
    }

   /**
   * Gets the DomDevice
   * @return a org.semanticwb.domotic.model.DomGroup
   */
    public org.semanticwb.domotic.model.DomGroup getDomDevice()
    {
         org.semanticwb.domotic.model.DomGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomGroup)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DomDeviceType
   * @param value DomDeviceType to set
   */

    public void setDomDeviceType(org.semanticwb.domotic.model.DomDeviceType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domDeviceType, value.getSemanticObject());
        }else
        {
            removeDomDeviceType();
        }
    }
   /**
   * Remove the value for DomDeviceType property
   */

    public void removeDomDeviceType()
    {
        getSemanticObject().removeProperty(swb4d_domDeviceType);
    }

   /**
   * Gets the DomDeviceType
   * @return a org.semanticwb.domotic.model.DomDeviceType
   */
    public org.semanticwb.domotic.model.DomDeviceType getDomDeviceType()
    {
         org.semanticwb.domotic.model.DomDeviceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domDeviceType);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDeviceType)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
