package org.semanticwb.bsc.formelement.base;


   /**
   * Campo de texto para datos numéricos validados 
   */
public abstract class NumberBase extends org.semanticwb.model.Text 
{
   /**
   * Campo de texto para datos numéricos validados
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Number=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Number");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Number");

    public static class ClassMgr
    {
       /**
       * Returns a list of Number for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.Number
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.Number> listNumbers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.Number>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.Number for all models
       * @return Iterator of org.semanticwb.bsc.formelement.Number
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.Number> listNumbers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.Number>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.Number
       * @param id Identifier for org.semanticwb.bsc.formelement.Number
       * @param model Model of the org.semanticwb.bsc.formelement.Number
       * @return A org.semanticwb.bsc.formelement.Number
       */
        public static org.semanticwb.bsc.formelement.Number getNumber(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.Number)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.Number
       * @param id Identifier for org.semanticwb.bsc.formelement.Number
       * @param model Model of the org.semanticwb.bsc.formelement.Number
       * @return A org.semanticwb.bsc.formelement.Number
       */
        public static org.semanticwb.bsc.formelement.Number createNumber(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.Number)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.Number
       * @param id Identifier for org.semanticwb.bsc.formelement.Number
       * @param model Model of the org.semanticwb.bsc.formelement.Number
       */
        public static void removeNumber(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.Number
       * @param id Identifier for org.semanticwb.bsc.formelement.Number
       * @param model Model of the org.semanticwb.bsc.formelement.Number
       * @return true if the org.semanticwb.bsc.formelement.Number exists, false otherwise
       */

        public static boolean hasNumber(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNumber(id, model)!=null);
        }
    }

    public static NumberBase.ClassMgr getNumberClassMgr()
    {
        return new NumberBase.ClassMgr();
    }

   /**
   * Constructs a NumberBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Number
   */
    public NumberBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
