package org.semanticwb.domotic.model.base;


public abstract class OnDeviceChangeBase extends org.semanticwb.domotic.model.DomEvent implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb4d_deviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#deviceStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasDomDevice4Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasDomDevice4Event");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnDeviceChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnDeviceChange");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnDeviceChange");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnDeviceChange for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChanges(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnDeviceChange for all models
       * @return Iterator of org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChanges()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange>(it, true);
        }

        public static org.semanticwb.domotic.model.OnDeviceChange createOnDeviceChange(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnDeviceChange.ClassMgr.createOnDeviceChange(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return A org.semanticwb.domotic.model.OnDeviceChange
       */
        public static org.semanticwb.domotic.model.OnDeviceChange getOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnDeviceChange)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return A org.semanticwb.domotic.model.OnDeviceChange
       */
        public static org.semanticwb.domotic.model.OnDeviceChange createOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnDeviceChange)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       */
        public static void removeOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnDeviceChange
       * @param id Identifier for org.semanticwb.domotic.model.OnDeviceChange
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return true if the org.semanticwb.domotic.model.OnDeviceChange exists, false otherwise
       */

        public static boolean hasOnDeviceChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnDeviceChange(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomDevice(org.semanticwb.domotic.model.DomDevice value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomDevice4Event, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomDevice
       * @param value DomDevice of the type org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomDevice(org.semanticwb.domotic.model.DomDevice value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomDevice4Event,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnDeviceChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnDeviceChange with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnDeviceChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnDeviceChange> listOnDeviceChangeByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnDeviceChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnDeviceChangeBase.ClassMgr getOnDeviceChangeClassMgr()
    {
        return new OnDeviceChangeBase.ClassMgr();
    }

   /**
   * Constructs a OnDeviceChangeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnDeviceChange
   */
    public OnDeviceChangeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the DeviceStat property
* @return String with the DeviceStat
*/
    public String getDeviceStat()
    {
        return getSemanticObject().getProperty(swb4d_deviceStat);
    }

/**
* Sets the DeviceStat property
* @param value long with the DeviceStat
*/
    public void setDeviceStat(String value)
    {
        getSemanticObject().setProperty(swb4d_deviceStat, value);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomDevice
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomDevice
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> listDomDevices()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice>(getSemanticObject().listObjectProperties(swb4d_hasDomDevice4Event));
    }

   /**
   * Gets true if has a DomDevice
   * @param value org.semanticwb.domotic.model.DomDevice to verify
   * @return true if the org.semanticwb.domotic.model.DomDevice exists, false otherwise
   */
    public boolean hasDomDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasDomDevice4Event,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomDevice
   * @param value org.semanticwb.domotic.model.DomDevice to add
   */

    public void addDomDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasDomDevice4Event, value.getSemanticObject());
    }
   /**
   * Removes all the DomDevice
   */

    public void removeAllDomDevice()
    {
        getSemanticObject().removeProperty(swb4d_hasDomDevice4Event);
    }
   /**
   * Removes a DomDevice
   * @param value org.semanticwb.domotic.model.DomDevice to remove
   */

    public void removeDomDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasDomDevice4Event,value.getSemanticObject());
    }

   /**
   * Gets the DomDevice
   * @return a org.semanticwb.domotic.model.DomDevice
   */
    public org.semanticwb.domotic.model.DomDevice getDomDevice()
    {
         org.semanticwb.domotic.model.DomDevice ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasDomDevice4Event);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDevice)obj.createGenericInstance();
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
