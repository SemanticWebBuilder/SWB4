package org.semanticwb.social.base;


   /**
   * Preposiciones (De lugar, de tiempo, etc) que no seran tomadas como palabras dentro de los mensajes de los post dentro del listener. Es decir, estas no deben ser evaluadas para analisis sentimental. 
   */
public abstract class PrepositionsBase extends org.semanticwb.social.TakeOut 
{
   /**
   * Preposición
   */
    public static final org.semanticwb.platform.SemanticProperty social_preposition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#preposition");
   /**
   * Preposiciones (De lugar, de tiempo, etc) que no seran tomadas como palabras dentro de los mensajes de los post dentro del listener. Es decir, estas no deben ser evaluadas para analisis sentimental.
   */
    public static final org.semanticwb.platform.SemanticClass social_Prepositions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Prepositions");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Prepositions");

    public static class ClassMgr
    {
       /**
       * Returns a list of Prepositions for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Prepositions
       */

        public static java.util.Iterator<org.semanticwb.social.Prepositions> listPrepositionses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Prepositions>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Prepositions for all models
       * @return Iterator of org.semanticwb.social.Prepositions
       */

        public static java.util.Iterator<org.semanticwb.social.Prepositions> listPrepositionses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Prepositions>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Prepositions
       * @param id Identifier for org.semanticwb.social.Prepositions
       * @param model Model of the org.semanticwb.social.Prepositions
       * @return A org.semanticwb.social.Prepositions
       */
        public static org.semanticwb.social.Prepositions getPrepositions(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Prepositions)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Prepositions
       * @param id Identifier for org.semanticwb.social.Prepositions
       * @param model Model of the org.semanticwb.social.Prepositions
       * @return A org.semanticwb.social.Prepositions
       */
        public static org.semanticwb.social.Prepositions createPrepositions(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Prepositions)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Prepositions
       * @param id Identifier for org.semanticwb.social.Prepositions
       * @param model Model of the org.semanticwb.social.Prepositions
       */
        public static void removePrepositions(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Prepositions
       * @param id Identifier for org.semanticwb.social.Prepositions
       * @param model Model of the org.semanticwb.social.Prepositions
       * @return true if the org.semanticwb.social.Prepositions exists, false otherwise
       */

        public static boolean hasPrepositions(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPrepositions(id, model)!=null);
        }
    }

    public static PrepositionsBase.ClassMgr getPrepositionsClassMgr()
    {
        return new PrepositionsBase.ClassMgr();
    }

   /**
   * Constructs a PrepositionsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Prepositions
   */
    public PrepositionsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Preposition property
* @return String with the Preposition
*/
    public String getPreposition()
    {
        return getSemanticObject().getProperty(social_preposition);
    }

/**
* Sets the Preposition property
* @param value long with the Preposition
*/
    public void setPreposition(String value)
    {
        getSemanticObject().setProperty(social_preposition, value);
    }
}
