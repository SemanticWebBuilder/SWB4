package org.semanticwb.process.model.base;


public abstract class MultiInstanceLoopCharacteristicsBase extends org.semanticwb.process.model.LoopCharacteristics implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_MultiInstanceLoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultiInstanceLoopCharacteristics");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultiInstanceLoopCharacteristics");

    public static class ClassMgr
    {
       /**
       * Returns a list of MultiInstanceLoopCharacteristics for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MultiInstanceLoopCharacteristics for all models
       * @return Iterator of org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics>(it, true);
        }

        public static org.semanticwb.process.model.MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @return A org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */
        public static org.semanticwb.process.model.MultiInstanceLoopCharacteristics getMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultiInstanceLoopCharacteristics)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @return A org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */
        public static org.semanticwb.process.model.MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultiInstanceLoopCharacteristics)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */
        public static void removeMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @return true if the org.semanticwb.process.model.MultiInstanceLoopCharacteristics exists, false otherwise
       */

        public static boolean hasMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultiInstanceLoopCharacteristics(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MultiInstanceLoopCharacteristics with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultiInstanceLoopCharacteristics with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultiInstanceLoopCharacteristics with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultiInstanceLoopCharacteristics with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultiInstanceLoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MultiInstanceLoopCharacteristicsBase.ClassMgr getMultiInstanceLoopCharacteristicsClassMgr()
    {
        return new MultiInstanceLoopCharacteristicsBase.ClassMgr();
    }

   /**
   * Constructs a MultiInstanceLoopCharacteristicsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MultiInstanceLoopCharacteristics
   */
    public MultiInstanceLoopCharacteristicsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
