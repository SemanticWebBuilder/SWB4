package org.semanticwb.process.model.base;


public abstract class ComplexGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * N√∫mero de tokens o flujos completados necesarios para que la compuerta se active cuando se usa como compuerta convergente.
   */
    public static final org.semanticwb.platform.SemanticProperty swp_startTokens=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#startTokens");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ComplexGateway");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ComplexGateway");

    public static class ClassMgr
    {
       /**
       * Returns a list of ComplexGateway for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ComplexGateway for all models
       * @return Iterator of org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway>(it, true);
        }

        public static org.semanticwb.process.model.ComplexGateway createComplexGateway(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ComplexGateway.ClassMgr.createComplexGateway(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ComplexGateway
       * @param id Identifier for org.semanticwb.process.model.ComplexGateway
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return A org.semanticwb.process.model.ComplexGateway
       */
        public static org.semanticwb.process.model.ComplexGateway getComplexGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ComplexGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ComplexGateway
       * @param id Identifier for org.semanticwb.process.model.ComplexGateway
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return A org.semanticwb.process.model.ComplexGateway
       */
        public static org.semanticwb.process.model.ComplexGateway createComplexGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ComplexGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ComplexGateway
       * @param id Identifier for org.semanticwb.process.model.ComplexGateway
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       */
        public static void removeComplexGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ComplexGateway
       * @param id Identifier for org.semanticwb.process.model.ComplexGateway
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return true if the org.semanticwb.process.model.ComplexGateway exists, false otherwise
       */

        public static boolean hasComplexGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComplexGateway(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ComplexGateway
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ComplexGateway with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ComplexGateway
       */

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ComplexGatewayBase.ClassMgr getComplexGatewayClassMgr()
    {
        return new ComplexGatewayBase.ClassMgr();
    }

   /**
   * Constructs a ComplexGatewayBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ComplexGateway
   */
    public ComplexGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the StartTokens property
* @return int with the StartTokens
*/
    public int getStartTokens()
    {
        return getSemanticObject().getIntProperty(swp_startTokens);
    }

/**
* Sets the StartTokens property
* @param value long with the StartTokens
*/
    public void setStartTokens(int value)
    {
        getSemanticObject().setIntProperty(swp_startTokens, value);
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
