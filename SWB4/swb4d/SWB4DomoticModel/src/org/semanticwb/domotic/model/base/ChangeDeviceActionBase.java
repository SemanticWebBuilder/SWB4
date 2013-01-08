package org.semanticwb.domotic.model.base;


public abstract class ChangeDeviceActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasChangeDevice=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasChangeDevice");
    public static final org.semanticwb.platform.SemanticProperty swb4d_changeDeviceStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#changeDeviceStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeDeviceAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeDeviceAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeDeviceAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeDeviceAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeDeviceAction for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeDeviceAction createChangeDeviceAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeDeviceAction.ClassMgr.createChangeDeviceAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeDeviceAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeDeviceAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return A org.semanticwb.domotic.model.ChangeDeviceAction
       */
        public static org.semanticwb.domotic.model.ChangeDeviceAction getChangeDeviceAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeDeviceAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeDeviceAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeDeviceAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return A org.semanticwb.domotic.model.ChangeDeviceAction
       */
        public static org.semanticwb.domotic.model.ChangeDeviceAction createChangeDeviceAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeDeviceAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeDeviceAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeDeviceAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       */
        public static void removeChangeDeviceAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeDeviceAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeDeviceAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return true if the org.semanticwb.domotic.model.ChangeDeviceAction exists, false otherwise
       */

        public static boolean hasChangeDeviceAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeDeviceAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined ChangeDevice
       * @param value ChangeDevice of the type org.semanticwb.domotic.model.DomDevice
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByChangeDevice(org.semanticwb.domotic.model.DomDevice value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasChangeDevice, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined ChangeDevice
       * @param value ChangeDevice of the type org.semanticwb.domotic.model.DomDevice
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByChangeDevice(org.semanticwb.domotic.model.DomDevice value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasChangeDevice,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeDeviceAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeDeviceAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeDeviceAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeDeviceAction> listChangeDeviceActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeDeviceAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeDeviceActionBase.ClassMgr getChangeDeviceActionClassMgr()
    {
        return new ChangeDeviceActionBase.ClassMgr();
    }

   /**
   * Constructs a ChangeDeviceActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeDeviceAction
   */
    public ChangeDeviceActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomDevice
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomDevice
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice> listChangeDevices()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomDevice>(getSemanticObject().listObjectProperties(swb4d_hasChangeDevice));
    }

   /**
   * Gets true if has a ChangeDevice
   * @param value org.semanticwb.domotic.model.DomDevice to verify
   * @return true if the org.semanticwb.domotic.model.DomDevice exists, false otherwise
   */
    public boolean hasChangeDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasChangeDevice,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ChangeDevice
   * @param value org.semanticwb.domotic.model.DomDevice to add
   */

    public void addChangeDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasChangeDevice, value.getSemanticObject());
    }
   /**
   * Removes all the ChangeDevice
   */

    public void removeAllChangeDevice()
    {
        getSemanticObject().removeProperty(swb4d_hasChangeDevice);
    }
   /**
   * Removes a ChangeDevice
   * @param value org.semanticwb.domotic.model.DomDevice to remove
   */

    public void removeChangeDevice(org.semanticwb.domotic.model.DomDevice value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasChangeDevice,value.getSemanticObject());
    }

   /**
   * Gets the ChangeDevice
   * @return a org.semanticwb.domotic.model.DomDevice
   */
    public org.semanticwb.domotic.model.DomDevice getChangeDevice()
    {
         org.semanticwb.domotic.model.DomDevice ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasChangeDevice);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomDevice)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ChangeDeviceStat property
* @return int with the ChangeDeviceStat
*/
    public int getChangeDeviceStat()
    {
        return getSemanticObject().getIntProperty(swb4d_changeDeviceStat);
    }

/**
* Sets the ChangeDeviceStat property
* @param value long with the ChangeDeviceStat
*/
    public void setChangeDeviceStat(int value)
    {
        getSemanticObject().setIntProperty(swb4d_changeDeviceStat, value);
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
