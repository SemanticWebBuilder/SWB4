package org.semanticwb.process.model.base;


public abstract class CompensationEndEventBase extends org.semanticwb.process.model.EndEventNode implements org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.ActionCodeable
{
    public static final org.semanticwb.platform.SemanticClass swp_CompensationEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationEndEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationEndEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of CompensationEndEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CompensationEndEvent for all models
       * @return Iterator of org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent>(it, true);
        }

        public static org.semanticwb.process.model.CompensationEndEvent createCompensationEndEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CompensationEndEvent.ClassMgr.createCompensationEndEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CompensationEndEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationEndEvent
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return A org.semanticwb.process.model.CompensationEndEvent
       */
        public static org.semanticwb.process.model.CompensationEndEvent getCompensationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationEndEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CompensationEndEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationEndEvent
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return A org.semanticwb.process.model.CompensationEndEvent
       */
        public static org.semanticwb.process.model.CompensationEndEvent createCompensationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationEndEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CompensationEndEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationEndEvent
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       */
        public static void removeCompensationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CompensationEndEvent
       * @param id Identifier for org.semanticwb.process.model.CompensationEndEvent
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return true if the org.semanticwb.process.model.CompensationEndEvent exists, false otherwise
       */

        public static boolean hasCompensationEndEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompensationEndEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CompensationEndEvent
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationEndEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CompensationEndEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationEndEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CompensationEndEventBase.ClassMgr getCompensationEndEventClassMgr()
    {
        return new CompensationEndEventBase.ClassMgr();
    }

   /**
   * Constructs a CompensationEndEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CompensationEndEvent
   */
    public CompensationEndEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
