package org.semanticwb.bsc.formelement.base;


   /**
   * Muestra en el modo vista, una liga hacia la página que contenga la información del tipo de objeto correspondiente, para bsc:Objective, bsc:Indicator, bsc:Initiative y bsc:Deliverable 
   */
public abstract class TextFieldBase extends org.semanticwb.model.Text 
{
   /**
   * Muestra en el modo vista, una liga hacia la página que contenga la información del tipo de objeto correspondiente, para bsc:Objective, bsc:Indicator, bsc:Initiative y bsc:Deliverable
   */
    public static final org.semanticwb.platform.SemanticClass bsc_TextField=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#TextField");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#TextField");

    public static class ClassMgr
    {
       /**
       * Returns a list of TextField for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.TextField
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.TextField> listTextFields(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.TextField>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.TextField for all models
       * @return Iterator of org.semanticwb.bsc.formelement.TextField
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.TextField> listTextFields()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.TextField>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.TextField
       * @param id Identifier for org.semanticwb.bsc.formelement.TextField
       * @param model Model of the org.semanticwb.bsc.formelement.TextField
       * @return A org.semanticwb.bsc.formelement.TextField
       */
        public static org.semanticwb.bsc.formelement.TextField getTextField(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.TextField)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.TextField
       * @param id Identifier for org.semanticwb.bsc.formelement.TextField
       * @param model Model of the org.semanticwb.bsc.formelement.TextField
       * @return A org.semanticwb.bsc.formelement.TextField
       */
        public static org.semanticwb.bsc.formelement.TextField createTextField(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.TextField)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.TextField
       * @param id Identifier for org.semanticwb.bsc.formelement.TextField
       * @param model Model of the org.semanticwb.bsc.formelement.TextField
       */
        public static void removeTextField(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.TextField
       * @param id Identifier for org.semanticwb.bsc.formelement.TextField
       * @param model Model of the org.semanticwb.bsc.formelement.TextField
       * @return true if the org.semanticwb.bsc.formelement.TextField exists, false otherwise
       */

        public static boolean hasTextField(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextField(id, model)!=null);
        }
    }

    public static TextFieldBase.ClassMgr getTextFieldClassMgr()
    {
        return new TextFieldBase.ClassMgr();
    }

   /**
   * Constructs a TextFieldBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TextField
   */
    public TextFieldBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
