package org.semanticwb.bsc.formelement.base;


   /**
   * Elemento que muestra un componente de selección para escoger una serie designada como referente para evaluar el indicador padre. 
   */
public abstract class SeriesOfEvaluationBase extends org.semanticwb.model.SelectOne 
{
   /**
   * Elemento que muestra un componente de selección para escoger una serie designada como referente para evaluar el indicador padre.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_SeriesOfEvaluation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SeriesOfEvaluation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SeriesOfEvaluation");

    public static class ClassMgr
    {
       /**
       * Returns a list of SeriesOfEvaluation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SeriesOfEvaluation
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SeriesOfEvaluation> listSeriesOfEvaluations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SeriesOfEvaluation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SeriesOfEvaluation for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SeriesOfEvaluation
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SeriesOfEvaluation> listSeriesOfEvaluations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SeriesOfEvaluation>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param id Identifier for org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param model Model of the org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @return A org.semanticwb.bsc.formelement.SeriesOfEvaluation
       */
        public static org.semanticwb.bsc.formelement.SeriesOfEvaluation getSeriesOfEvaluation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SeriesOfEvaluation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param id Identifier for org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param model Model of the org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @return A org.semanticwb.bsc.formelement.SeriesOfEvaluation
       */
        public static org.semanticwb.bsc.formelement.SeriesOfEvaluation createSeriesOfEvaluation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SeriesOfEvaluation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param id Identifier for org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param model Model of the org.semanticwb.bsc.formelement.SeriesOfEvaluation
       */
        public static void removeSeriesOfEvaluation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param id Identifier for org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @param model Model of the org.semanticwb.bsc.formelement.SeriesOfEvaluation
       * @return true if the org.semanticwb.bsc.formelement.SeriesOfEvaluation exists, false otherwise
       */

        public static boolean hasSeriesOfEvaluation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSeriesOfEvaluation(id, model)!=null);
        }
    }

    public static SeriesOfEvaluationBase.ClassMgr getSeriesOfEvaluationClassMgr()
    {
        return new SeriesOfEvaluationBase.ClassMgr();
    }

   /**
   * Constructs a SeriesOfEvaluationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SeriesOfEvaluation
   */
    public SeriesOfEvaluationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
