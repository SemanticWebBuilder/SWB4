package org.semanticwb.process.model.base;


public abstract class MultipleIntermediateThrowEventBase extends org.semanticwb.process.model.IntermediateThrowEvent implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_IntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#IntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticProperty swp_hasThrowEvents=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasThrowEvents");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleIntermediateThrowEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleIntermediateThrowEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of MultipleIntermediateThrowEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MultipleIntermediateThrowEvent for all models
       * @return Iterator of org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent>(it, true);
        }

        public static org.semanticwb.process.model.MultipleIntermediateThrowEvent createMultipleIntermediateThrowEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.createMultipleIntermediateThrowEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return A org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */
        public static org.semanticwb.process.model.MultipleIntermediateThrowEvent getMultipleIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleIntermediateThrowEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return A org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */
        public static org.semanticwb.process.model.MultipleIntermediateThrowEvent createMultipleIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleIntermediateThrowEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */
        public static void removeMultipleIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return true if the org.semanticwb.process.model.MultipleIntermediateThrowEvent exists, false otherwise
       */

        public static boolean hasMultipleIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultipleIntermediateThrowEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined ThrowEvents
       * @param value ThrowEvents of the type org.semanticwb.process.model.IntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByThrowEvents(org.semanticwb.process.model.IntermediateThrowEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasThrowEvents, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined ThrowEvents
       * @param value ThrowEvents of the type org.semanticwb.process.model.IntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByThrowEvents(org.semanticwb.process.model.IntermediateThrowEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasThrowEvents,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleIntermediateThrowEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MultipleIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MultipleIntermediateThrowEventBase.ClassMgr getMultipleIntermediateThrowEventClassMgr()
    {
        return new MultipleIntermediateThrowEventBase.ClassMgr();
    }

   /**
   * Constructs a MultipleIntermediateThrowEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MultipleIntermediateThrowEvent
   */
    public MultipleIntermediateThrowEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.IntermediateThrowEvent
   * @return A GenericIterator with all the org.semanticwb.process.model.IntermediateThrowEvent
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateThrowEvent> listThrowEventses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateThrowEvent>(getSemanticObject().listObjectProperties(swp_hasThrowEvents));
    }

   /**
   * Gets true if has a ThrowEvents
   * @param value org.semanticwb.process.model.IntermediateThrowEvent to verify
   * @return true if the org.semanticwb.process.model.IntermediateThrowEvent exists, false otherwise
   */
    public boolean hasThrowEvents(org.semanticwb.process.model.IntermediateThrowEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasThrowEvents,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ThrowEvents
   * @param value org.semanticwb.process.model.IntermediateThrowEvent to add
   */

    public void addThrowEvents(org.semanticwb.process.model.IntermediateThrowEvent value)
    {
        getSemanticObject().addObjectProperty(swp_hasThrowEvents, value.getSemanticObject());
    }
   /**
   * Removes all the ThrowEvents
   */

    public void removeAllThrowEvents()
    {
        getSemanticObject().removeProperty(swp_hasThrowEvents);
    }
   /**
   * Removes a ThrowEvents
   * @param value org.semanticwb.process.model.IntermediateThrowEvent to remove
   */

    public void removeThrowEvents(org.semanticwb.process.model.IntermediateThrowEvent value)
    {
        getSemanticObject().removeObjectProperty(swp_hasThrowEvents,value.getSemanticObject());
    }

   /**
   * Gets the ThrowEvents
   * @return a org.semanticwb.process.model.IntermediateThrowEvent
   */
    public org.semanticwb.process.model.IntermediateThrowEvent getThrowEvents()
    {
         org.semanticwb.process.model.IntermediateThrowEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasThrowEvents);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.IntermediateThrowEvent)obj.createGenericInstance();
         }
         return ret;
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
