package org.semanticwb.model.base;


   /**
   * Elemento para valores booleanos configurable como CheckBox, Radio y Select 
   */
public abstract class BooleanElementBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Elemento para valores booleanos configurable como CheckBox, Radio y Select
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_BooleanElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#BooleanElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#BooleanElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of BooleanElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.BooleanElement
       */

        public static java.util.Iterator<org.semanticwb.model.BooleanElement> listBooleanElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.BooleanElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.BooleanElement for all models
       * @return Iterator of org.semanticwb.model.BooleanElement
       */

        public static java.util.Iterator<org.semanticwb.model.BooleanElement> listBooleanElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.BooleanElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.BooleanElement
       * @param id Identifier for org.semanticwb.model.BooleanElement
       * @param model Model of the org.semanticwb.model.BooleanElement
       * @return A org.semanticwb.model.BooleanElement
       */
        public static org.semanticwb.model.BooleanElement getBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.BooleanElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.BooleanElement
       * @param id Identifier for org.semanticwb.model.BooleanElement
       * @param model Model of the org.semanticwb.model.BooleanElement
       * @return A org.semanticwb.model.BooleanElement
       */
        public static org.semanticwb.model.BooleanElement createBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.BooleanElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.BooleanElement
       * @param id Identifier for org.semanticwb.model.BooleanElement
       * @param model Model of the org.semanticwb.model.BooleanElement
       */
        public static void removeBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.BooleanElement
       * @param id Identifier for org.semanticwb.model.BooleanElement
       * @param model Model of the org.semanticwb.model.BooleanElement
       * @return true if the org.semanticwb.model.BooleanElement exists, false otherwise
       */

        public static boolean hasBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBooleanElement(id, model)!=null);
        }
    }

   /**
   * Constructs a BooleanElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BooleanElement
   */
    public BooleanElementBase(org.semanticwb.platform.SemanticObject base)
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
