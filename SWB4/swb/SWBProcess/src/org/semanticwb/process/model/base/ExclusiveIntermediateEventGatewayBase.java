package org.semanticwb.process.model.base;


public abstract class ExclusiveIntermediateEventGatewayBase extends org.semanticwb.process.model.EventBasedGateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ExclusiveIntermediateEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ExclusiveIntermediateEventGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ExclusiveIntermediateEventGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of ExclusiveIntermediateEventGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ExclusiveIntermediateEventGateway for all models
       * @return Iterator of org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway>(it, true);
        }

        public static org.semanticwb.process.model.ExclusiveIntermediateEventGateway createExclusiveIntermediateEventGateway(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.createExclusiveIntermediateEventGateway(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param id Identifier for org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return A org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */
        public static org.semanticwb.process.model.ExclusiveIntermediateEventGateway getExclusiveIntermediateEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExclusiveIntermediateEventGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param id Identifier for org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return A org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */
        public static org.semanticwb.process.model.ExclusiveIntermediateEventGateway createExclusiveIntermediateEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExclusiveIntermediateEventGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param id Identifier for org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */
        public static void removeExclusiveIntermediateEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param id Identifier for org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return true if the org.semanticwb.process.model.ExclusiveIntermediateEventGateway exists, false otherwise
       */

        public static boolean hasExclusiveIntermediateEventGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getExclusiveIntermediateEventGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ExclusiveIntermediateEventGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ExclusiveIntermediateEventGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ExclusiveIntermediateEventGatewayBase.ClassMgr getExclusiveIntermediateEventGatewayClassMgr()
    {
        return new ExclusiveIntermediateEventGatewayBase.ClassMgr();
    }

   /**
   * Constructs a ExclusiveIntermediateEventGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ExclusiveIntermediateEventGateway
   */
    public ExclusiveIntermediateEventGatewayBase(org.semanticwb.platform.SemanticObject base)
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
