package org.semanticwb.process.model.base;


public abstract class StandarLoopCharacteristicsBase extends org.semanticwb.process.model.LoopCharacteristics implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_StandarLoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StandarLoopCharacteristics");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StandarLoopCharacteristics");

    public static class ClassMgr
    {
       /**
       * Returns a list of StandarLoopCharacteristics for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.StandarLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.StandarLoopCharacteristics> listStandarLoopCharacteristicses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StandarLoopCharacteristics>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.StandarLoopCharacteristics for all models
       * @return Iterator of org.semanticwb.process.model.StandarLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.StandarLoopCharacteristics> listStandarLoopCharacteristicses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StandarLoopCharacteristics>(it, true);
        }

        public static org.semanticwb.process.model.StandarLoopCharacteristics createStandarLoopCharacteristics(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.StandarLoopCharacteristics.ClassMgr.createStandarLoopCharacteristics(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.StandarLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.StandarLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.StandarLoopCharacteristics
       * @return A org.semanticwb.process.model.StandarLoopCharacteristics
       */
        public static org.semanticwb.process.model.StandarLoopCharacteristics getStandarLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StandarLoopCharacteristics)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.StandarLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.StandarLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.StandarLoopCharacteristics
       * @return A org.semanticwb.process.model.StandarLoopCharacteristics
       */
        public static org.semanticwb.process.model.StandarLoopCharacteristics createStandarLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.StandarLoopCharacteristics)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.StandarLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.StandarLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.StandarLoopCharacteristics
       */
        public static void removeStandarLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.StandarLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.StandarLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.StandarLoopCharacteristics
       * @return true if the org.semanticwb.process.model.StandarLoopCharacteristics exists, false otherwise
       */

        public static boolean hasStandarLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStandarLoopCharacteristics(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.StandarLoopCharacteristics with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.StandarLoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.StandarLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.StandarLoopCharacteristics> listStandarLoopCharacteristicsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StandarLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StandarLoopCharacteristics with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.StandarLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.StandarLoopCharacteristics> listStandarLoopCharacteristicsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StandarLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StandarLoopCharacteristics with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.StandarLoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.StandarLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.StandarLoopCharacteristics> listStandarLoopCharacteristicsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StandarLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.StandarLoopCharacteristics with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.StandarLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.StandarLoopCharacteristics> listStandarLoopCharacteristicsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StandarLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StandarLoopCharacteristicsBase.ClassMgr getStandarLoopCharacteristicsClassMgr()
    {
        return new StandarLoopCharacteristicsBase.ClassMgr();
    }

   /**
   * Constructs a StandarLoopCharacteristicsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StandarLoopCharacteristics
   */
    public StandarLoopCharacteristicsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
