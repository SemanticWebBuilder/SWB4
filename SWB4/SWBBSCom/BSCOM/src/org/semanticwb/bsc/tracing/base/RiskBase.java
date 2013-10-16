package org.semanticwb.bsc.tracing.base;


public abstract class RiskBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help
{
    public static final org.semanticwb.platform.SemanticClass bsc_Risk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Risk");

    public static class ClassMgr
    {
       /**
       * Returns a list of Risk for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRisks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Risk for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRisks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Risk createRisk(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Risk.ClassMgr.createRisk(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return A org.semanticwb.bsc.tracing.Risk
       */
        public static org.semanticwb.bsc.tracing.Risk getRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Risk)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return A org.semanticwb.bsc.tracing.Risk
       */
        public static org.semanticwb.bsc.tracing.Risk createRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Risk)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       */
        public static void removeRisk(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Risk
       * @param id Identifier for org.semanticwb.bsc.tracing.Risk
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return true if the org.semanticwb.bsc.tracing.Risk exists, false otherwise
       */

        public static boolean hasRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRisk(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Risk
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Risk with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Risk
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Risk> listRiskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RiskBase.ClassMgr getRiskClassMgr()
    {
        return new RiskBase.ClassMgr();
    }

   /**
   * Constructs a RiskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Risk
   */
    public RiskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
