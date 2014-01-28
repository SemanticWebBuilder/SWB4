package org.semanticwb.process.model.base;


public abstract class MessageStartEventBase extends org.semanticwb.process.model.StartEventNode implements org.semanticwb.process.model.CatchMessageable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.process.model.ActionCodeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageStartEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageStartEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of MessageStartEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MessageStartEvent for all models
       * @return Iterator of org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent>(it, true);
        }

        public static org.semanticwb.process.model.MessageStartEvent createMessageStartEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MessageStartEvent.ClassMgr.createMessageStartEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MessageStartEvent
       * @param id Identifier for org.semanticwb.process.model.MessageStartEvent
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return A org.semanticwb.process.model.MessageStartEvent
       */
        public static org.semanticwb.process.model.MessageStartEvent getMessageStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageStartEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MessageStartEvent
       * @param id Identifier for org.semanticwb.process.model.MessageStartEvent
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return A org.semanticwb.process.model.MessageStartEvent
       */
        public static org.semanticwb.process.model.MessageStartEvent createMessageStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageStartEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MessageStartEvent
       * @param id Identifier for org.semanticwb.process.model.MessageStartEvent
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       */
        public static void removeMessageStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MessageStartEvent
       * @param id Identifier for org.semanticwb.process.model.MessageStartEvent
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return true if the org.semanticwb.process.model.MessageStartEvent exists, false otherwise
       */

        public static boolean hasMessageStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageStartEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined ItemAwareMapping
       * @param value ItemAwareMapping of the type org.semanticwb.process.model.ItemAwareMapping
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareMapping, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined ItemAwareMapping
       * @param value ItemAwareMapping of the type org.semanticwb.process.model.ItemAwareMapping
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareMapping,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByNext(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByNext(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MessageStartEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageStartEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MessageStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MessageStartEventBase.ClassMgr getMessageStartEventClassMgr()
    {
        return new MessageStartEventBase.ClassMgr();
    }

   /**
   * Constructs a MessageStartEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MessageStartEvent
   */
    public MessageStartEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ItemAwareMapping
   * @return A GenericIterator with all the org.semanticwb.process.model.ItemAwareMapping
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping>(getSemanticObject().listObjectProperties(swp_hasItemAwareMapping));
    }

   /**
   * Gets true if has a ItemAwareMapping
   * @param value org.semanticwb.process.model.ItemAwareMapping to verify
   * @return true if the org.semanticwb.process.model.ItemAwareMapping exists, false otherwise
   */
    public boolean hasItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasItemAwareMapping,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ItemAwareMapping
   * @param value org.semanticwb.process.model.ItemAwareMapping to add
   */

    public void addItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value)
    {
        getSemanticObject().addObjectProperty(swp_hasItemAwareMapping, value.getSemanticObject());
    }
   /**
   * Removes all the ItemAwareMapping
   */

    public void removeAllItemAwareMapping()
    {
        getSemanticObject().removeProperty(swp_hasItemAwareMapping);
    }
   /**
   * Removes a ItemAwareMapping
   * @param value org.semanticwb.process.model.ItemAwareMapping to remove
   */

    public void removeItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value)
    {
        getSemanticObject().removeObjectProperty(swp_hasItemAwareMapping,value.getSemanticObject());
    }

   /**
   * Gets the ItemAwareMapping
   * @return a org.semanticwb.process.model.ItemAwareMapping
   */
    public org.semanticwb.process.model.ItemAwareMapping getItemAwareMapping()
    {
         org.semanticwb.process.model.ItemAwareMapping ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasItemAwareMapping);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAwareMapping)obj.createGenericInstance();
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
