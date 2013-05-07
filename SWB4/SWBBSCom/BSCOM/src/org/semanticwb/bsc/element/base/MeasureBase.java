package org.semanticwb.bsc.element.base;


public abstract class MeasureBase extends org.semanticwb.bsc.element.BSCElement 
{
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");

    public static class ClassMgr
    {
       /**
       * Returns a list of Measure for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasures(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Measure for all models
       * @return Iterator of org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasures()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure>(it, true);
        }

        public static org.semanticwb.bsc.element.Measure createMeasure(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Measure.ClassMgr.createMeasure(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return A org.semanticwb.bsc.element.Measure
       */
        public static org.semanticwb.bsc.element.Measure getMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Measure)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return A org.semanticwb.bsc.element.Measure
       */
        public static org.semanticwb.bsc.element.Measure createMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Measure)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       */
        public static void removeMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return true if the org.semanticwb.bsc.element.Measure exists, false otherwise
       */

        public static boolean hasMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeasure(id, model)!=null);
        }
    }

    public static MeasureBase.ClassMgr getMeasureClassMgr()
    {
        return new MeasureBase.ClassMgr();
    }

   /**
   * Constructs a MeasureBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Measure
   */
    public MeasureBase(org.semanticwb.platform.SemanticObject base)
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
