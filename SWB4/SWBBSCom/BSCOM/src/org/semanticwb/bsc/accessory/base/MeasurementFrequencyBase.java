package org.semanticwb.bsc.accessory.base;


   /**
   * Las frecuencias de medición, definen bloques de períodos para determinar cuándo se requiere la captura de información. 
   */
public abstract class MeasurementFrequencyBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Help
{
   /**
   * Especifica el número de períodos que abarca esta frecuencia de medición
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_numberOfPeriods=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#numberOfPeriods");
   /**
   * Las frecuencias de medición, definen bloques de períodos para determinar cuándo se requiere la captura de información.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MeasurementFrequency=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MeasurementFrequency");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MeasurementFrequency");

    public static class ClassMgr
    {
       /**
       * Returns a list of MeasurementFrequency for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.MeasurementFrequency> listMeasurementFrequencies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.MeasurementFrequency>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.MeasurementFrequency for all models
       * @return Iterator of org.semanticwb.bsc.accessory.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.MeasurementFrequency> listMeasurementFrequencies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.MeasurementFrequency>(it, true);
        }

        public static org.semanticwb.bsc.accessory.MeasurementFrequency createMeasurementFrequency(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.MeasurementFrequency.ClassMgr.createMeasurementFrequency(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.accessory.MeasurementFrequency
       * @return A org.semanticwb.bsc.accessory.MeasurementFrequency
       */
        public static org.semanticwb.bsc.accessory.MeasurementFrequency getMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.MeasurementFrequency)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.accessory.MeasurementFrequency
       * @return A org.semanticwb.bsc.accessory.MeasurementFrequency
       */
        public static org.semanticwb.bsc.accessory.MeasurementFrequency createMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.MeasurementFrequency)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.accessory.MeasurementFrequency
       */
        public static void removeMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.accessory.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.accessory.MeasurementFrequency
       * @return true if the org.semanticwb.bsc.accessory.MeasurementFrequency exists, false otherwise
       */

        public static boolean hasMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeasurementFrequency(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.MeasurementFrequency with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.accessory.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.MeasurementFrequency> listMeasurementFrequencyByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.MeasurementFrequency with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.MeasurementFrequency> listMeasurementFrequencyByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.MeasurementFrequency with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.accessory.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.MeasurementFrequency> listMeasurementFrequencyByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.MeasurementFrequency with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.MeasurementFrequency> listMeasurementFrequencyByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MeasurementFrequencyBase.ClassMgr getMeasurementFrequencyClassMgr()
    {
        return new MeasurementFrequencyBase.ClassMgr();
    }

   /**
   * Constructs a MeasurementFrequencyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MeasurementFrequency
   */
    public MeasurementFrequencyBase(org.semanticwb.platform.SemanticObject base)
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
