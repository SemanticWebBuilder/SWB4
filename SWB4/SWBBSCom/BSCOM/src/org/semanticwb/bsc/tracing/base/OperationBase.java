package org.semanticwb.bsc.tracing.base;


public abstract class OperationBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_operator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#operator");
    public static final org.semanticwb.platform.SemanticClass bsc_Operation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Operation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Operation");

    public static class ClassMgr
    {
       /**
       * Returns a list of Operation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Operation> listOperations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Operation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Operation for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Operation> listOperations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Operation>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Operation createOperation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Operation.ClassMgr.createOperation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Operation
       * @param id Identifier for org.semanticwb.bsc.tracing.Operation
       * @param model Model of the org.semanticwb.bsc.tracing.Operation
       * @return A org.semanticwb.bsc.tracing.Operation
       */
        public static org.semanticwb.bsc.tracing.Operation getOperation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Operation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Operation
       * @param id Identifier for org.semanticwb.bsc.tracing.Operation
       * @param model Model of the org.semanticwb.bsc.tracing.Operation
       * @return A org.semanticwb.bsc.tracing.Operation
       */
        public static org.semanticwb.bsc.tracing.Operation createOperation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Operation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Operation
       * @param id Identifier for org.semanticwb.bsc.tracing.Operation
       * @param model Model of the org.semanticwb.bsc.tracing.Operation
       */
        public static void removeOperation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Operation
       * @param id Identifier for org.semanticwb.bsc.tracing.Operation
       * @param model Model of the org.semanticwb.bsc.tracing.Operation
       * @return true if the org.semanticwb.bsc.tracing.Operation exists, false otherwise
       */

        public static boolean hasOperation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOperation(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Operation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Operation
       * @return Iterator with all the org.semanticwb.bsc.tracing.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Operation> listOperationByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Operation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Operation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Operation> listOperationByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Operation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Operation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Operation
       * @return Iterator with all the org.semanticwb.bsc.tracing.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Operation> listOperationByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Operation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Operation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Operation
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Operation> listOperationByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Operation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OperationBase.ClassMgr getOperationClassMgr()
    {
        return new OperationBase.ClassMgr();
    }

   /**
   * Constructs a OperationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Operation
   */
    public OperationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Operator property
* @return String with the Operator
*/
    public String getOperator()
    {
        return getSemanticObject().getProperty(bsc_operator);
    }

/**
* Sets the Operator property
* @param value long with the Operator
*/
    public void setOperator(String value)
    {
        getSemanticObject().setProperty(bsc_operator, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
