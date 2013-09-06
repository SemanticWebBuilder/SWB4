package org.semanticwb.bsc.formelement.base;


   /**
   * OrdinalCategorical maneja un valores ordinales que no se repiten para instancias de una clase que comparten la misma instancia de la super clase inmediata. 
   */
public abstract class OrdinalCategoricalBase extends org.semanticwb.bsc.formelement.Ordinal 
{
   /**
   * OrdinalCategorical maneja un valores ordinales que no se repiten para instancias de una clase que comparten la misma instancia de la super clase inmediata.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_OrdinalCategorical=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#OrdinalCategorical");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#OrdinalCategorical");

    public static class ClassMgr
    {
       /**
       * Returns a list of OrdinalCategorical for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.OrdinalCategorical
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.OrdinalCategorical> listOrdinalCategoricals(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.OrdinalCategorical>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.OrdinalCategorical for all models
       * @return Iterator of org.semanticwb.bsc.formelement.OrdinalCategorical
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.OrdinalCategorical> listOrdinalCategoricals()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.OrdinalCategorical>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param id Identifier for org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param model Model of the org.semanticwb.bsc.formelement.OrdinalCategorical
       * @return A org.semanticwb.bsc.formelement.OrdinalCategorical
       */
        public static org.semanticwb.bsc.formelement.OrdinalCategorical getOrdinalCategorical(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.OrdinalCategorical)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param id Identifier for org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param model Model of the org.semanticwb.bsc.formelement.OrdinalCategorical
       * @return A org.semanticwb.bsc.formelement.OrdinalCategorical
       */
        public static org.semanticwb.bsc.formelement.OrdinalCategorical createOrdinalCategorical(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.OrdinalCategorical)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param id Identifier for org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param model Model of the org.semanticwb.bsc.formelement.OrdinalCategorical
       */
        public static void removeOrdinalCategorical(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param id Identifier for org.semanticwb.bsc.formelement.OrdinalCategorical
       * @param model Model of the org.semanticwb.bsc.formelement.OrdinalCategorical
       * @return true if the org.semanticwb.bsc.formelement.OrdinalCategorical exists, false otherwise
       */

        public static boolean hasOrdinalCategorical(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOrdinalCategorical(id, model)!=null);
        }
    }

    public static OrdinalCategoricalBase.ClassMgr getOrdinalCategoricalClassMgr()
    {
        return new OrdinalCategoricalBase.ClassMgr();
    }

   /**
   * Constructs a OrdinalCategoricalBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OrdinalCategorical
   */
    public OrdinalCategoricalBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
