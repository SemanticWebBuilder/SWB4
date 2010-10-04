package org.semanticwb.model.base;


public abstract class LoginElementBase extends org.semanticwb.model.Text 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_LoginElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#LoginElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#LoginElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of LoginElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.LoginElement
       */

        public static java.util.Iterator<org.semanticwb.model.LoginElement> listLoginElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.LoginElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.LoginElement for all models
       * @return Iterator of org.semanticwb.model.LoginElement
       */

        public static java.util.Iterator<org.semanticwb.model.LoginElement> listLoginElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.LoginElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.LoginElement
       * @param id Identifier for org.semanticwb.model.LoginElement
       * @param model Model of the org.semanticwb.model.LoginElement
       * @return A org.semanticwb.model.LoginElement
       */
        public static org.semanticwb.model.LoginElement getLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.LoginElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.LoginElement
       * @param id Identifier for org.semanticwb.model.LoginElement
       * @param model Model of the org.semanticwb.model.LoginElement
       * @return A org.semanticwb.model.LoginElement
       */
        public static org.semanticwb.model.LoginElement createLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.LoginElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.LoginElement
       * @param id Identifier for org.semanticwb.model.LoginElement
       * @param model Model of the org.semanticwb.model.LoginElement
       */
        public static void removeLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.LoginElement
       * @param id Identifier for org.semanticwb.model.LoginElement
       * @param model Model of the org.semanticwb.model.LoginElement
       * @return true if the org.semanticwb.model.LoginElement exists, false otherwise
       */

        public static boolean hasLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLoginElement(id, model)!=null);
        }
    }

   /**
   * Constructs a LoginElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LoginElement
   */
    public LoginElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
