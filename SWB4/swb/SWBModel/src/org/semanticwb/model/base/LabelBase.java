package org.semanticwb.model.base;


   /**
   * Elemento que muestra texto plano 
   */
public abstract class LabelBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Elemento que muestra texto plano
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_Label=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Label");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Label");

    public static class ClassMgr
    {
       /**
       * Returns a list of Label for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Label
       */

        public static java.util.Iterator<org.semanticwb.model.Label> listLabels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Label>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Label for all models
       * @return Iterator of org.semanticwb.model.Label
       */

        public static java.util.Iterator<org.semanticwb.model.Label> listLabels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Label>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.Label
       * @param id Identifier for org.semanticwb.model.Label
       * @param model Model of the org.semanticwb.model.Label
       * @return A org.semanticwb.model.Label
       */
        public static org.semanticwb.model.Label getLabel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Label)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Label
       * @param id Identifier for org.semanticwb.model.Label
       * @param model Model of the org.semanticwb.model.Label
       * @return A org.semanticwb.model.Label
       */
        public static org.semanticwb.model.Label createLabel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Label)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Label
       * @param id Identifier for org.semanticwb.model.Label
       * @param model Model of the org.semanticwb.model.Label
       */
        public static void removeLabel(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Label
       * @param id Identifier for org.semanticwb.model.Label
       * @param model Model of the org.semanticwb.model.Label
       * @return true if the org.semanticwb.model.Label exists, false otherwise
       */

        public static boolean hasLabel(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLabel(id, model)!=null);
        }
    }

   /**
   * Constructs a LabelBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Label
   */
    public LabelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
