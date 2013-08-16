package org.semanticwb.process.model.base;


public abstract class TerminationEndEventBase extends org.semanticwb.process.model.EndEventNode implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_TerminationEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TerminationEndEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TerminationEndEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of TerminationEndEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.TerminationEndEvent for all models
       * @return Iterator of org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent>(it, true);
        }

        public static org.semanticwb.process.model.TerminationEndEvent createTerminationEndEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TerminationEndEvent.ClassMgr.createTerminationEndEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.TerminationEndEvent
       * @param id Identifier for org.semanticwb.process.model.TerminationEndEvent
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return A org.semanticwb.process.model.TerminationEndEvent
       */
        public static org.semanticwb.process.model.TerminationEndEvent getTerminationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TerminationEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.TerminationEndEvent
       * @param id Identifier for org.semanticwb.process.model.TerminationEndEvent
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return A org.semanticwb.process.model.TerminationEndEvent
       */
        public static org.semanticwb.process.model.TerminationEndEvent createTerminationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TerminationEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.TerminationEndEvent
       * @param id Identifier for org.semanticwb.process.model.TerminationEndEvent
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       */
        public static void removeTerminationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.TerminationEndEvent
       * @param id Identifier for org.semanticwb.process.model.TerminationEndEvent
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return true if the org.semanticwb.process.model.TerminationEndEvent exists, false otherwise
       */

        public static boolean hasTerminationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTerminationEndEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.TerminationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TerminationEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.TerminationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TerminationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TerminationEndEventBase.ClassMgr getTerminationEndEventClassMgr()
    {
        return new TerminationEndEventBase.ClassMgr();
    }

   /**
   * Constructs a TerminationEndEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TerminationEndEvent
   */
    public TerminationEndEventBase(org.semanticwb.platform.SemanticObject base)
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
