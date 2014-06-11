package org.semanticwb.bsc.tracing.base;


   /**
   * Las frecuencias de medición, definen bloques de períodos para determinar cuándo se requiere la captura de información. Frecuencia de medición. 
   */
public abstract class MeasurementFrequencyBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Roleable,org.semanticwb.bsc.Help,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Especifica el número de períodos que abarca esta frecuencia de medición
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_numberOfPeriods=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#numberOfPeriods");
   /**
   * Las frecuencias de medición, definen bloques de períodos para determinar cuándo se requiere la captura de información. Frecuencia de medición.
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
       * @return Iterator of org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.MeasurementFrequency for all models
       * @return Iterator of org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency>(it, true);
        }

        public static org.semanticwb.bsc.tracing.MeasurementFrequency createMeasurementFrequency(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.MeasurementFrequency.ClassMgr.createMeasurementFrequency(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return A org.semanticwb.bsc.tracing.MeasurementFrequency
       */
        public static org.semanticwb.bsc.tracing.MeasurementFrequency getMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.MeasurementFrequency)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return A org.semanticwb.bsc.tracing.MeasurementFrequency
       */
        public static org.semanticwb.bsc.tracing.MeasurementFrequency createMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.MeasurementFrequency)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       */
        public static void removeMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param id Identifier for org.semanticwb.bsc.tracing.MeasurementFrequency
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return true if the org.semanticwb.bsc.tracing.MeasurementFrequency exists, false otherwise
       */

        public static boolean hasMeasurementFrequency(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeasurementFrequency(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.MeasurementFrequency
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MeasurementFrequency with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.MeasurementFrequency
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MeasurementFrequency> listMeasurementFrequencyByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MeasurementFrequency> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
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
* Gets the NumberOfPeriods property
* @return int with the NumberOfPeriods
*/
    public int getNumberOfPeriods()
    {
        return getSemanticObject().getIntProperty(bsc_numberOfPeriods);
    }

/**
* Sets the NumberOfPeriods property
* @param value long with the NumberOfPeriods
*/
    public void setNumberOfPeriods(int value)
    {
        getSemanticObject().setIntProperty(bsc_numberOfPeriods, value);
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
