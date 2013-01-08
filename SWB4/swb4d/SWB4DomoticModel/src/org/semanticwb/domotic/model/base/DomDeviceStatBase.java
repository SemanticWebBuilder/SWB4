package org.semanticwb.domotic.model.base;


public abstract class DomDeviceStatBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#status");
    public static final org.semanticwb.platform.SemanticProperty swb4d_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#active");
    public static final org.semanticwb.platform.SemanticProperty swb4d_lastUpdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#lastUpdate");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDeviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceStat");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDeviceStat");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomDeviceStat for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomDeviceStat
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDeviceStat> listDomDeviceStats(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDeviceStat>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomDeviceStat for all models
       * @return Iterator of org.semanticwb.domotic.model.DomDeviceStat
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDeviceStat> listDomDeviceStats()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDeviceStat>(it, true);
        }

        public static org.semanticwb.domotic.model.DomDeviceStat createDomDeviceStat(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomDeviceStat.ClassMgr.createDomDeviceStat(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomDeviceStat
       * @param id Identifier for org.semanticwb.domotic.model.DomDeviceStat
       * @param model Model of the org.semanticwb.domotic.model.DomDeviceStat
       * @return A org.semanticwb.domotic.model.DomDeviceStat
       */
        public static org.semanticwb.domotic.model.DomDeviceStat getDomDeviceStat(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomDeviceStat)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomDeviceStat
       * @param id Identifier for org.semanticwb.domotic.model.DomDeviceStat
       * @param model Model of the org.semanticwb.domotic.model.DomDeviceStat
       * @return A org.semanticwb.domotic.model.DomDeviceStat
       */
        public static org.semanticwb.domotic.model.DomDeviceStat createDomDeviceStat(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomDeviceStat)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomDeviceStat
       * @param id Identifier for org.semanticwb.domotic.model.DomDeviceStat
       * @param model Model of the org.semanticwb.domotic.model.DomDeviceStat
       */
        public static void removeDomDeviceStat(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomDeviceStat
       * @param id Identifier for org.semanticwb.domotic.model.DomDeviceStat
       * @param model Model of the org.semanticwb.domotic.model.DomDeviceStat
       * @return true if the org.semanticwb.domotic.model.DomDeviceStat exists, false otherwise
       */

        public static boolean hasDomDeviceStat(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomDeviceStat(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDeviceStat with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.DomDeviceStat
       * @return Iterator with all the org.semanticwb.domotic.model.DomDeviceStat
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDeviceStat> listDomDeviceStatByDomDevice(org.semanticwb.domotic.model.DomDevice value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDeviceStat> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDevice, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomDeviceStat with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.DomDeviceStat
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomDeviceStat> listDomDeviceStatByDomDevice(org.semanticwb.domotic.model.DomDevice value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDeviceStat> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domDevice,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomDeviceStatBase.ClassMgr getDomDeviceStatClassMgr()
    {
        return new DomDeviceStatBase.ClassMgr();
    }

   /**
   * Constructs a DomDeviceStatBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomDeviceStat
   */
    public DomDeviceStatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property DomDevice
   * @param value DomDevice to set
   */

    public void setDomDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domDevice, value.getSemanticObject());
        }else
        {
            removeDomDevice();
        }
    }
   /**
   * Remove the value for DomDevice property
   */

    public void removeDomDevice()
    {
        getSemanticObject().removeProperty(swb4d_domDevice);
    }

   /**
   * Gets the DomDevice
   * @return a org.semanticwb.domotic.model.DomDevice
   */
    public org.semanticwb.domotic.model.DomDevice getDomDevice()
    {
         org.semanticwb.domotic.model.DomDevice ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domDevice);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDevice)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Status property
* @return int with the Status
*/
    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swb4d_status);
    }

/**
* Sets the Status property
* @param value long with the Status
*/
    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swb4d_status, value);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb4d_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb4d_active, value);
    }

/**
* Gets the LastUpdate property
* @return java.util.Date with the LastUpdate
*/
    public java.util.Date getLastUpdate()
    {
        return getSemanticObject().getDateProperty(swb4d_lastUpdate);
    }

/**
* Sets the LastUpdate property
* @param value long with the LastUpdate
*/
    public void setLastUpdate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb4d_lastUpdate, value);
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
