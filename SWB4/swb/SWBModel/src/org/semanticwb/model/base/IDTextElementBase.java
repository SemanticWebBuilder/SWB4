package org.semanticwb.model.base;


public abstract class IDTextElementBase extends org.semanticwb.model.Text 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_IDTextElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#IDTextElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#IDTextElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of IDTextElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.IDTextElement
       */

        public static java.util.Iterator<org.semanticwb.model.IDTextElement> listIDTextElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.IDTextElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.IDTextElement for all models
       * @return Iterator of org.semanticwb.model.IDTextElement
       */

        public static java.util.Iterator<org.semanticwb.model.IDTextElement> listIDTextElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.IDTextElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.IDTextElement
       * @param id Identifier for org.semanticwb.model.IDTextElement
       * @param model Model of the org.semanticwb.model.IDTextElement
       * @return A org.semanticwb.model.IDTextElement
       */
        public static org.semanticwb.model.IDTextElement getIDTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.IDTextElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.IDTextElement
       * @param id Identifier for org.semanticwb.model.IDTextElement
       * @param model Model of the org.semanticwb.model.IDTextElement
       * @return A org.semanticwb.model.IDTextElement
       */
        public static org.semanticwb.model.IDTextElement createIDTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.IDTextElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.IDTextElement
       * @param id Identifier for org.semanticwb.model.IDTextElement
       * @param model Model of the org.semanticwb.model.IDTextElement
       */
        public static void removeIDTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.IDTextElement
       * @param id Identifier for org.semanticwb.model.IDTextElement
       * @param model Model of the org.semanticwb.model.IDTextElement
       * @return true if the org.semanticwb.model.IDTextElement exists, false otherwise
       */

        public static boolean hasIDTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getIDTextElement(id, model)!=null);
        }
    }

    public static IDTextElementBase.ClassMgr getIDTextElementClassMgr()
    {
        return new IDTextElementBase.ClassMgr();
    }

   /**
   * Constructs a IDTextElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the IDTextElement
   */
    public IDTextElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
