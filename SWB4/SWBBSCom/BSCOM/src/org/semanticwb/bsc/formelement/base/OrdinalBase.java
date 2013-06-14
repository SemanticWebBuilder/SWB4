package org.semanticwb.bsc.formelement.base;


   /**
   * OrdinalCategorical maneja un valores ordinales que no se repiten para instancias de una misma clase.  
   */
public abstract class OrdinalBase extends org.semanticwb.bsc.formelement.Number 
{
   /**
   * OrdinalCategorical maneja un valores ordinales que no se repiten para instancias de una misma clase. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Ordinal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Ordinal");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Ordinal");

    public static class ClassMgr
    {
       /**
       * Returns a list of Ordinal for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.Ordinal
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.Ordinal> listOrdinals(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.Ordinal>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.Ordinal for all models
       * @return Iterator of org.semanticwb.bsc.formelement.Ordinal
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.Ordinal> listOrdinals()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.Ordinal>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.Ordinal
       * @param id Identifier for org.semanticwb.bsc.formelement.Ordinal
       * @param model Model of the org.semanticwb.bsc.formelement.Ordinal
       * @return A org.semanticwb.bsc.formelement.Ordinal
       */
        public static org.semanticwb.bsc.formelement.Ordinal getOrdinal(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.Ordinal)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.Ordinal
       * @param id Identifier for org.semanticwb.bsc.formelement.Ordinal
       * @param model Model of the org.semanticwb.bsc.formelement.Ordinal
       * @return A org.semanticwb.bsc.formelement.Ordinal
       */
        public static org.semanticwb.bsc.formelement.Ordinal createOrdinal(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.Ordinal)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.Ordinal
       * @param id Identifier for org.semanticwb.bsc.formelement.Ordinal
       * @param model Model of the org.semanticwb.bsc.formelement.Ordinal
       */
        public static void removeOrdinal(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.Ordinal
       * @param id Identifier for org.semanticwb.bsc.formelement.Ordinal
       * @param model Model of the org.semanticwb.bsc.formelement.Ordinal
       * @return true if the org.semanticwb.bsc.formelement.Ordinal exists, false otherwise
       */

        public static boolean hasOrdinal(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOrdinal(id, model)!=null);
        }
    }

   /**
   * Constructs a OrdinalBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Ordinal
   */
    public OrdinalBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
