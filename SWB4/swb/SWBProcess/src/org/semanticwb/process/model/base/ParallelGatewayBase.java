package org.semanticwb.process.model.base;


public abstract class ParallelGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ParallelGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of ParallelGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ParallelGateway for all models
       * @return Iterator of org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway>(it, true);
        }

        public static org.semanticwb.process.model.ParallelGateway createParallelGateway(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ParallelGateway.ClassMgr.createParallelGateway(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ParallelGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelGateway
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return A org.semanticwb.process.model.ParallelGateway
       */
        public static org.semanticwb.process.model.ParallelGateway getParallelGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParallelGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ParallelGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelGateway
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return A org.semanticwb.process.model.ParallelGateway
       */
        public static org.semanticwb.process.model.ParallelGateway createParallelGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ParallelGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ParallelGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelGateway
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       */
        public static void removeParallelGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ParallelGateway
       * @param id Identifier for org.semanticwb.process.model.ParallelGateway
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return true if the org.semanticwb.process.model.ParallelGateway exists, false otherwise
       */

        public static boolean hasParallelGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParallelGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ParallelGateway
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ParallelGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ParallelGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParallelGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ParallelGatewayBase.ClassMgr getParallelGatewayClassMgr()
    {
        return new ParallelGatewayBase.ClassMgr();
    }

   /**
   * Constructs a ParallelGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ParallelGateway
   */
    public ParallelGatewayBase(org.semanticwb.platform.SemanticObject base)
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
