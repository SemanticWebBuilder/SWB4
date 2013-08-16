package org.semanticwb.process.model.base;


public abstract class MultipleEndEventBase extends org.semanticwb.process.model.EndEventNode implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_MultipleEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleEndEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleEndEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of MultipleEndEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MultipleEndEvent for all models
       * @return Iterator of org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent>(it, true);
        }

        public static org.semanticwb.process.model.MultipleEndEvent createMultipleEndEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultipleEndEvent.ClassMgr.createMultipleEndEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MultipleEndEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleEndEvent
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return A org.semanticwb.process.model.MultipleEndEvent
       */
        public static org.semanticwb.process.model.MultipleEndEvent getMultipleEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MultipleEndEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleEndEvent
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return A org.semanticwb.process.model.MultipleEndEvent
       */
        public static org.semanticwb.process.model.MultipleEndEvent createMultipleEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MultipleEndEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleEndEvent
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       */
        public static void removeMultipleEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MultipleEndEvent
       * @param id Identifier for org.semanticwb.process.model.MultipleEndEvent
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return true if the org.semanticwb.process.model.MultipleEndEvent exists, false otherwise
       */

        public static boolean hasMultipleEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultipleEndEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MultipleEndEvent
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MultipleEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MultipleEndEventBase.ClassMgr getMultipleEndEventClassMgr()
    {
        return new MultipleEndEventBase.ClassMgr();
    }

   /**
   * Constructs a MultipleEndEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MultipleEndEvent
   */
    public MultipleEndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
