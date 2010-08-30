package org.semanticwb.model.base;


public abstract class RichTextBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_RichText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");

    public static class ClassMgr
    {
       /**
       * Returns a list of RichText for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.RichText
       */

        public static java.util.Iterator<org.semanticwb.model.RichText> listRichTexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RichText>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.RichText for all models
       * @return Iterator of org.semanticwb.model.RichText
       */

        public static java.util.Iterator<org.semanticwb.model.RichText> listRichTexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RichText>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.RichText
       * @param id Identifier for org.semanticwb.model.RichText
       * @param model Model of the org.semanticwb.model.RichText
       * @return A org.semanticwb.model.RichText
       */
        public static org.semanticwb.model.RichText getRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.RichText
       * @param id Identifier for org.semanticwb.model.RichText
       * @param model Model of the org.semanticwb.model.RichText
       * @return A org.semanticwb.model.RichText
       */
        public static org.semanticwb.model.RichText createRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.RichText
       * @param id Identifier for org.semanticwb.model.RichText
       * @param model Model of the org.semanticwb.model.RichText
       */
        public static void removeRichText(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.RichText
       * @param id Identifier for org.semanticwb.model.RichText
       * @param model Model of the org.semanticwb.model.RichText
       * @return true if the org.semanticwb.model.RichText exists, false otherwise
       */

        public static boolean hasRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRichText(id, model)!=null);
        }
    }

   /**
   * Constructs a RichTextBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RichText
   */
    public RichTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
