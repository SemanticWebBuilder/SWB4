package org.semanticwb.process.model.base;


public abstract class LinkIntermediateThrowEventBase extends org.semanticwb.process.model.IntermediateThrowEvent implements org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.process.model.ActionCodeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_LinkIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LinkIntermediateThrowEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LinkIntermediateThrowEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of LinkIntermediateThrowEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.LinkIntermediateThrowEvent for all models
       * @return Iterator of org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent>(it, true);
        }

        public static org.semanticwb.process.model.LinkIntermediateThrowEvent createLinkIntermediateThrowEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.createLinkIntermediateThrowEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return A org.semanticwb.process.model.LinkIntermediateThrowEvent
       */
        public static org.semanticwb.process.model.LinkIntermediateThrowEvent getLinkIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LinkIntermediateThrowEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return A org.semanticwb.process.model.LinkIntermediateThrowEvent
       */
        public static org.semanticwb.process.model.LinkIntermediateThrowEvent createLinkIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LinkIntermediateThrowEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */
        public static void removeLinkIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param id Identifier for org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return true if the org.semanticwb.process.model.LinkIntermediateThrowEvent exists, false otherwise
       */

        public static boolean hasLinkIntermediateThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLinkIntermediateThrowEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.LinkIntermediateThrowEvent
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LinkIntermediateThrowEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.LinkIntermediateThrowEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static LinkIntermediateThrowEventBase.ClassMgr getLinkIntermediateThrowEventClassMgr()
    {
        return new LinkIntermediateThrowEventBase.ClassMgr();
    }

   /**
   * Constructs a LinkIntermediateThrowEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LinkIntermediateThrowEvent
   */
    public LinkIntermediateThrowEventBase(org.semanticwb.platform.SemanticObject base)
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
