package org.semanticwb.bsc.tracing.base;


public abstract class SerieBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Serie=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Serie");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Serie");

    public static class ClassMgr
    {
       /**
       * Returns a list of Serie for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Serie
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Serie> listSeries(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Serie>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Serie for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Serie
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Serie> listSeries()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Serie>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Serie createSerie(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Serie.ClassMgr.createSerie(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Serie
       * @param id Identifier for org.semanticwb.bsc.tracing.Serie
       * @param model Model of the org.semanticwb.bsc.tracing.Serie
       * @return A org.semanticwb.bsc.tracing.Serie
       */
        public static org.semanticwb.bsc.tracing.Serie getSerie(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Serie)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Serie
       * @param id Identifier for org.semanticwb.bsc.tracing.Serie
       * @param model Model of the org.semanticwb.bsc.tracing.Serie
       * @return A org.semanticwb.bsc.tracing.Serie
       */
        public static org.semanticwb.bsc.tracing.Serie createSerie(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Serie)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Serie
       * @param id Identifier for org.semanticwb.bsc.tracing.Serie
       * @param model Model of the org.semanticwb.bsc.tracing.Serie
       */
        public static void removeSerie(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Serie
       * @param id Identifier for org.semanticwb.bsc.tracing.Serie
       * @param model Model of the org.semanticwb.bsc.tracing.Serie
       * @return true if the org.semanticwb.bsc.tracing.Serie exists, false otherwise
       */

        public static boolean hasSerie(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSerie(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Serie with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Serie
       * @return Iterator with all the org.semanticwb.bsc.tracing.Serie
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Serie> listSerieByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Serie> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Serie with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Serie
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Serie> listSerieByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Serie> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Serie with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Serie
       * @return Iterator with all the org.semanticwb.bsc.tracing.Serie
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Serie> listSerieByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Serie> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Serie with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Serie
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Serie> listSerieByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Serie> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SerieBase.ClassMgr getSerieClassMgr()
    {
        return new SerieBase.ClassMgr();
    }

   /**
   * Constructs a SerieBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Serie
   */
    public SerieBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
