package org.semanticwb.process.model.base;


public abstract class MessageIntermediateThrowEventBase extends org.semanticwb.process.model.IntermediateThrowEvent implements org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.ActionCodeable,org.semanticwb.process.model.ThrowMessageable
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageIntermediateThrowEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageIntermediateThrowEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of MessageIntermediateThrowEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MessageIntermediateThrowEvent for all models
       * @return Iterator of org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent>(it, true);
        }

        public static org.semanticwb.process.model.MessageIntermediateThrowEvent createMessageIntermediateThrowEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.createMessageIntermediateThrowEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return A org.semanticwb.process.model.MessageIntermediateThrowEvent
       */
        public static org.semanticwb.process.model.MessageIntermediateThrowEvent getMessageIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageIntermediateThrowEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return A org.semanticwb.process.model.MessageIntermediateThrowEvent
       */
        public static org.semanticwb.process.model.MessageIntermediateThrowEvent createMessageIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageIntermediateThrowEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */
        public static void removeMessageIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return true if the org.semanticwb.process.model.MessageIntermediateThrowEvent exists, false otherwise
       */

        public static boolean hasMessageIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageIntermediateThrowEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined MessageItemAware
       * @param value MessageItemAware of the type org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByMessageItemAware(org.semanticwb.process.model.ItemAware value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageItemAware, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined MessageItemAware
       * @param value MessageItemAware of the type org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByMessageItemAware(org.semanticwb.process.model.ItemAware value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageItemAware,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateThrowEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MessageIntermediateThrowEventBase.ClassMgr getMessageIntermediateThrowEventClassMgr()
    {
        return new MessageIntermediateThrowEventBase.ClassMgr();
    }

   /**
   * Constructs a MessageIntermediateThrowEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MessageIntermediateThrowEvent
   */
    public MessageIntermediateThrowEventBase(org.semanticwb.platform.SemanticObject base)
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
