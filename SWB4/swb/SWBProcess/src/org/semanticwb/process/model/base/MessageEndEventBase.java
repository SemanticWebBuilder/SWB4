package org.semanticwb.process.model.base;


public abstract class MessageEndEventBase extends org.semanticwb.process.model.EndEventNode implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.ActionCodeable,org.semanticwb.process.model.ThrowMessageable
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageEndEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageEndEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of MessageEndEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MessageEndEvent for all models
       * @return Iterator of org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent>(it, true);
        }

        public static org.semanticwb.process.model.MessageEndEvent createMessageEndEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MessageEndEvent.ClassMgr.createMessageEndEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MessageEndEvent
       * @param id Identifier for org.semanticwb.process.model.MessageEndEvent
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return A org.semanticwb.process.model.MessageEndEvent
       */
        public static org.semanticwb.process.model.MessageEndEvent getMessageEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MessageEndEvent
       * @param id Identifier for org.semanticwb.process.model.MessageEndEvent
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return A org.semanticwb.process.model.MessageEndEvent
       */
        public static org.semanticwb.process.model.MessageEndEvent createMessageEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MessageEndEvent
       * @param id Identifier for org.semanticwb.process.model.MessageEndEvent
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       */
        public static void removeMessageEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MessageEndEvent
       * @param id Identifier for org.semanticwb.process.model.MessageEndEvent
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return true if the org.semanticwb.process.model.MessageEndEvent exists, false otherwise
       */

        public static boolean hasMessageEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageEndEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined MessageItemAware
       * @param value MessageItemAware of the type org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByMessageItemAware(org.semanticwb.process.model.ItemAware value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageItemAware, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined MessageItemAware
       * @param value MessageItemAware of the type org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByMessageItemAware(org.semanticwb.process.model.ItemAware value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageItemAware,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MessageEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MessageEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MessageEndEventBase.ClassMgr getMessageEndEventClassMgr()
    {
        return new MessageEndEventBase.ClassMgr();
    }

   /**
   * Constructs a MessageEndEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MessageEndEvent
   */
    public MessageEndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ItemAware
   * @return A GenericIterator with all the org.semanticwb.process.model.ItemAware
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> listMessageItemAwares()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware>(getSemanticObject().listObjectProperties(swp_hasMessageItemAware));
    }

   /**
   * Gets true if has a MessageItemAware
   * @param value org.semanticwb.process.model.ItemAware to verify
   * @return true if the org.semanticwb.process.model.ItemAware exists, false otherwise
   */
    public boolean hasMessageItemAware(org.semanticwb.process.model.ItemAware value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageItemAware,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a MessageItemAware
   * @param value org.semanticwb.process.model.ItemAware to add
   */

    public void addMessageItemAware(org.semanticwb.process.model.ItemAware value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageItemAware, value.getSemanticObject());
    }
   /**
   * Removes all the MessageItemAware
   */

    public void removeAllMessageItemAware()
    {
        getSemanticObject().removeProperty(swp_hasMessageItemAware);
    }
   /**
   * Removes a MessageItemAware
   * @param value org.semanticwb.process.model.ItemAware to remove
   */

    public void removeMessageItemAware(org.semanticwb.process.model.ItemAware value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageItemAware,value.getSemanticObject());
    }

   /**
   * Gets the MessageItemAware
   * @return a org.semanticwb.process.model.ItemAware
   */
    public org.semanticwb.process.model.ItemAware getMessageItemAware()
    {
         org.semanticwb.process.model.ItemAware ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageItemAware);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAware)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ActionCode property
* @return String with the ActionCode
*/
    public String getActionCode()
    {
        return getSemanticObject().getProperty(swp_actionCode);
    }

/**
* Sets the ActionCode property
* @param value long with the ActionCode
*/
    public void setActionCode(String value)
    {
        getSemanticObject().setProperty(swp_actionCode, value);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
