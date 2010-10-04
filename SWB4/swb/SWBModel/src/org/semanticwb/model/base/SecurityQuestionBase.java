package org.semanticwb.model.base;


   /**
   * Elemento que muestra un componente grafico para mostrar la pregunta de seguridad del registro del usuarios 
   */
public abstract class SecurityQuestionBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Elemento que muestra un componente grafico para mostrar la pregunta de seguridad del registro del usuarios
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_SecurityQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SecurityQuestion");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SecurityQuestion");

    public static class ClassMgr
    {
       /**
       * Returns a list of SecurityQuestion for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SecurityQuestion
       */

        public static java.util.Iterator<org.semanticwb.model.SecurityQuestion> listSecurityQuestions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SecurityQuestion>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SecurityQuestion for all models
       * @return Iterator of org.semanticwb.model.SecurityQuestion
       */

        public static java.util.Iterator<org.semanticwb.model.SecurityQuestion> listSecurityQuestions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SecurityQuestion>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SecurityQuestion
       * @param id Identifier for org.semanticwb.model.SecurityQuestion
       * @param model Model of the org.semanticwb.model.SecurityQuestion
       * @return A org.semanticwb.model.SecurityQuestion
       */
        public static org.semanticwb.model.SecurityQuestion getSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SecurityQuestion)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SecurityQuestion
       * @param id Identifier for org.semanticwb.model.SecurityQuestion
       * @param model Model of the org.semanticwb.model.SecurityQuestion
       * @return A org.semanticwb.model.SecurityQuestion
       */
        public static org.semanticwb.model.SecurityQuestion createSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SecurityQuestion)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SecurityQuestion
       * @param id Identifier for org.semanticwb.model.SecurityQuestion
       * @param model Model of the org.semanticwb.model.SecurityQuestion
       */
        public static void removeSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SecurityQuestion
       * @param id Identifier for org.semanticwb.model.SecurityQuestion
       * @param model Model of the org.semanticwb.model.SecurityQuestion
       * @return true if the org.semanticwb.model.SecurityQuestion exists, false otherwise
       */

        public static boolean hasSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSecurityQuestion(id, model)!=null);
        }
    }

   /**
   * Constructs a SecurityQuestionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SecurityQuestion
   */
    public SecurityQuestionBase(org.semanticwb.platform.SemanticObject base)
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
