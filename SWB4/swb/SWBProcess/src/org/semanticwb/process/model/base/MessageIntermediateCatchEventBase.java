package org.semanticwb.process.model.base;


public abstract class MessageIntermediateCatchEventBase extends org.semanticwb.process.model.IntermediateCatchEvent implements org.semanticwb.process.model.CatchMessageable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.process.model.ActionCodeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageIntermediateCatchEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageIntermediateCatchEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of MessageIntermediateCatchEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MessageIntermediateCatchEvent for all models
       * @return Iterator of org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.MessageIntermediateCatchEvent createMessageIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.createMessageIntermediateCatchEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return A org.semanticwb.process.model.MessageIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.MessageIntermediateCatchEvent getMessageIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageIntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return A org.semanticwb.process.model.MessageIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.MessageIntermediateCatchEvent createMessageIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageIntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */
        public static void removeMessageIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return true if the org.semanticwb.process.model.MessageIntermediateCatchEvent exists, false otherwise
       */

        public static boolean hasMessageIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageIntermediateCatchEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined ItemAwareMapping
       * @param value ItemAwareMapping of the type org.semanticwb.process.model.ItemAwareMapping
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareMapping, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined ItemAwareMapping
       * @param value ItemAwareMapping of the type org.semanticwb.process.model.ItemAwareMapping
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareMapping,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MessageIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MessageIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MessageIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MessageIntermediateCatchEventBase.ClassMgr getMessageIntermediateCatchEventClassMgr()
    {
        return new MessageIntermediateCatchEventBase.ClassMgr();
    }

   /**
   * Constructs a MessageIntermediateCatchEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MessageIntermediateCatchEvent
   */
    public MessageIntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
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
