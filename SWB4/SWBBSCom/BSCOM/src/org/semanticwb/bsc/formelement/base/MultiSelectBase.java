package org.semanticwb.bsc.formelement.base;


   /**
   * Para generar el contenido de las vistas resumen y detalle 
   */
public abstract class MultiSelectBase extends org.semanticwb.model.SelectMultiple 
{
   /**
   * Para generar el contenido de las vistas resumen y detalle
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MultiSelect=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MultiSelect");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MultiSelect");

    public static class ClassMgr
    {
       /**
       * Returns a list of MultiSelect for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.MultiSelect
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.MultiSelect> listMultiSelects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.MultiSelect>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.MultiSelect for all models
       * @return Iterator of org.semanticwb.bsc.formelement.MultiSelect
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.MultiSelect> listMultiSelects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.MultiSelect>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.MultiSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.MultiSelect
       * @param model Model of the org.semanticwb.bsc.formelement.MultiSelect
       * @return A org.semanticwb.bsc.formelement.MultiSelect
       */
        public static org.semanticwb.bsc.formelement.MultiSelect getMultiSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.MultiSelect)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.MultiSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.MultiSelect
       * @param model Model of the org.semanticwb.bsc.formelement.MultiSelect
       * @return A org.semanticwb.bsc.formelement.MultiSelect
       */
        public static org.semanticwb.bsc.formelement.MultiSelect createMultiSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.MultiSelect)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.MultiSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.MultiSelect
       * @param model Model of the org.semanticwb.bsc.formelement.MultiSelect
       */
        public static void removeMultiSelect(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.MultiSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.MultiSelect
       * @param model Model of the org.semanticwb.bsc.formelement.MultiSelect
       * @return true if the org.semanticwb.bsc.formelement.MultiSelect exists, false otherwise
       */

        public static boolean hasMultiSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultiSelect(id, model)!=null);
        }
    }

    public static MultiSelectBase.ClassMgr getMultiSelectClassMgr()
    {
        return new MultiSelectBase.ClassMgr();
    }

   /**
   * Constructs a MultiSelectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MultiSelect
   */
    public MultiSelectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
