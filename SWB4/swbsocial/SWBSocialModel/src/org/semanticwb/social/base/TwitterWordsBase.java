package org.semanticwb.social.base;


public abstract class TwitterWordsBase extends org.semanticwb.social.WordsToMonitor implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass social_TwitterWords=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TwitterWords");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TwitterWords");

    public static class ClassMgr
    {
       /**
       * Returns a list of TwitterWords for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.TwitterWords
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterWords> listTwitterWordses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterWords>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.TwitterWords for all models
       * @return Iterator of org.semanticwb.social.TwitterWords
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterWords> listTwitterWordses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterWords>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.TwitterWords
       * @param id Identifier for org.semanticwb.social.TwitterWords
       * @param model Model of the org.semanticwb.social.TwitterWords
       * @return A org.semanticwb.social.TwitterWords
       */
        public static org.semanticwb.social.TwitterWords getTwitterWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TwitterWords)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.TwitterWords
       * @param id Identifier for org.semanticwb.social.TwitterWords
       * @param model Model of the org.semanticwb.social.TwitterWords
       * @return A org.semanticwb.social.TwitterWords
       */
        public static org.semanticwb.social.TwitterWords createTwitterWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TwitterWords)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.TwitterWords
       * @param id Identifier for org.semanticwb.social.TwitterWords
       * @param model Model of the org.semanticwb.social.TwitterWords
       */
        public static void removeTwitterWords(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.TwitterWords
       * @param id Identifier for org.semanticwb.social.TwitterWords
       * @param model Model of the org.semanticwb.social.TwitterWords
       * @return true if the org.semanticwb.social.TwitterWords exists, false otherwise
       */

        public static boolean hasTwitterWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTwitterWords(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.TwitterWords with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.TwitterWords
       * @return Iterator with all the org.semanticwb.social.TwitterWords
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterWords> listTwitterWordsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterWords> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TwitterWords with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.TwitterWords
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterWords> listTwitterWordsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterWords> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TwitterWords with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.TwitterWords
       * @return Iterator with all the org.semanticwb.social.TwitterWords
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterWords> listTwitterWordsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterWords> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TwitterWords with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.TwitterWords
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterWords> listTwitterWordsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterWords> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a TwitterWordsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TwitterWords
   */
    public TwitterWordsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
