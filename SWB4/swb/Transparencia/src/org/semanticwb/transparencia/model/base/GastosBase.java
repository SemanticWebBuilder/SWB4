package org.semanticwb.transparencia.model.base;


public abstract class GastosBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass trans_Gastos=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Gastos");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Gastos");

    public static class ClassMgr
    {
       /**
       * Returns a list of Gastos for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.Gastos
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Gastos> listGastoses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Gastos>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.Gastos for all models
       * @return Iterator of org.semanticwb.transparencia.model.Gastos
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Gastos> listGastoses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Gastos>(it, true);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.Gastos
       * @param id Identifier for org.semanticwb.transparencia.model.Gastos
       * @param model Model of the org.semanticwb.transparencia.model.Gastos
       * @return A org.semanticwb.transparencia.model.Gastos
       */
        public static org.semanticwb.transparencia.model.Gastos getGastos(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Gastos)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.Gastos
       * @param id Identifier for org.semanticwb.transparencia.model.Gastos
       * @param model Model of the org.semanticwb.transparencia.model.Gastos
       * @return A org.semanticwb.transparencia.model.Gastos
       */
        public static org.semanticwb.transparencia.model.Gastos createGastos(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Gastos)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.Gastos
       * @param id Identifier for org.semanticwb.transparencia.model.Gastos
       * @param model Model of the org.semanticwb.transparencia.model.Gastos
       */
        public static void removeGastos(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.Gastos
       * @param id Identifier for org.semanticwb.transparencia.model.Gastos
       * @param model Model of the org.semanticwb.transparencia.model.Gastos
       * @return true if the org.semanticwb.transparencia.model.Gastos exists, false otherwise
       */

        public static boolean hasGastos(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGastos(id, model)!=null);
        }
    }

   /**
   * Constructs a GastosBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Gastos
   */
    public GastosBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
