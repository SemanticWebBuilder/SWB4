package org.semanticwb.social.base;


public abstract class YouTubeWordsBase extends org.semanticwb.social.WordsToMonitor implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass social_YouTubeWords=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YouTubeWords");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YouTubeWords");

    public static class ClassMgr
    {
       /**
       * Returns a list of YouTubeWords for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.YouTubeWords
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeWords> listYouTubeWordses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeWords>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.YouTubeWords for all models
       * @return Iterator of org.semanticwb.social.YouTubeWords
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeWords> listYouTubeWordses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeWords>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.YouTubeWords
       * @param id Identifier for org.semanticwb.social.YouTubeWords
       * @param model Model of the org.semanticwb.social.YouTubeWords
       * @return A org.semanticwb.social.YouTubeWords
       */
        public static org.semanticwb.social.YouTubeWords getYouTubeWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YouTubeWords)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.YouTubeWords
       * @param id Identifier for org.semanticwb.social.YouTubeWords
       * @param model Model of the org.semanticwb.social.YouTubeWords
       * @return A org.semanticwb.social.YouTubeWords
       */
        public static org.semanticwb.social.YouTubeWords createYouTubeWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YouTubeWords)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.YouTubeWords
       * @param id Identifier for org.semanticwb.social.YouTubeWords
       * @param model Model of the org.semanticwb.social.YouTubeWords
       */
        public static void removeYouTubeWords(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.YouTubeWords
       * @param id Identifier for org.semanticwb.social.YouTubeWords
       * @param model Model of the org.semanticwb.social.YouTubeWords
       * @return true if the org.semanticwb.social.YouTubeWords exists, false otherwise
       */

        public static boolean hasYouTubeWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (getYouTubeWords(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.YouTubeWords with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.YouTubeWords
       * @return Iterator with all the org.semanticwb.social.YouTubeWords
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeWords> listYouTubeWordsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeWords> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTubeWords with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.YouTubeWords
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeWords> listYouTubeWordsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeWords> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTubeWords with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.YouTubeWords
       * @return Iterator with all the org.semanticwb.social.YouTubeWords
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeWords> listYouTubeWordsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeWords> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.YouTubeWords with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.YouTubeWords
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeWords> listYouTubeWordsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeWords> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a YouTubeWordsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the YouTubeWords
   */
    public YouTubeWordsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
