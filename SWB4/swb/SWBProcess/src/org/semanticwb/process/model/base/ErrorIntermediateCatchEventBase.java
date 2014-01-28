package org.semanticwb.process.model.base;


public abstract class ErrorIntermediateCatchEventBase extends org.semanticwb.process.model.IntermediateCatchEvent implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ActionCodeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_ErrorIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ErrorIntermediateCatchEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ErrorIntermediateCatchEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of ErrorIntermediateCatchEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ErrorIntermediateCatchEvent for all models
       * @return Iterator of org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.ErrorIntermediateCatchEvent createErrorIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.createErrorIntermediateCatchEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return A org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.ErrorIntermediateCatchEvent getErrorIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ErrorIntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return A org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.ErrorIntermediateCatchEvent createErrorIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ErrorIntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */
        public static void removeErrorIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return true if the org.semanticwb.process.model.ErrorIntermediateCatchEvent exists, false otherwise
       */

        public static boolean hasErrorIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getErrorIntermediateCatchEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ErrorIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ErrorIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ErrorIntermediateCatchEventBase.ClassMgr getErrorIntermediateCatchEventClassMgr()
    {
        return new ErrorIntermediateCatchEventBase.ClassMgr();
    }

   /**
   * Constructs a ErrorIntermediateCatchEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ErrorIntermediateCatchEvent
   */
    public ErrorIntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
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
