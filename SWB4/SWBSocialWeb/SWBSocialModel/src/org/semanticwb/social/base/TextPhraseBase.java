package org.semanticwb.social.base;


public abstract class TextPhraseBase extends org.semanticwb.model.Text 
{
   /**
   * The set of social networks that will be accepted in this property
   */
    public static final org.semanticwb.platform.SemanticProperty social_tf_socialNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#tf_socialNet");
    public static final org.semanticwb.platform.SemanticClass social_TextPhrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TextPhrase");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TextPhrase");

    public static class ClassMgr
    {
       /**
       * Returns a list of TextPhrase for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.TextPhrase
       */

        public static java.util.Iterator<org.semanticwb.social.TextPhrase> listTextPhrases(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TextPhrase>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.TextPhrase for all models
       * @return Iterator of org.semanticwb.social.TextPhrase
       */

        public static java.util.Iterator<org.semanticwb.social.TextPhrase> listTextPhrases()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TextPhrase>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.TextPhrase
       * @param id Identifier for org.semanticwb.social.TextPhrase
       * @param model Model of the org.semanticwb.social.TextPhrase
       * @return A org.semanticwb.social.TextPhrase
       */
        public static org.semanticwb.social.TextPhrase getTextPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TextPhrase)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.TextPhrase
       * @param id Identifier for org.semanticwb.social.TextPhrase
       * @param model Model of the org.semanticwb.social.TextPhrase
       * @return A org.semanticwb.social.TextPhrase
       */
        public static org.semanticwb.social.TextPhrase createTextPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TextPhrase)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.TextPhrase
       * @param id Identifier for org.semanticwb.social.TextPhrase
       * @param model Model of the org.semanticwb.social.TextPhrase
       */
        public static void removeTextPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.TextPhrase
       * @param id Identifier for org.semanticwb.social.TextPhrase
       * @param model Model of the org.semanticwb.social.TextPhrase
       * @return true if the org.semanticwb.social.TextPhrase exists, false otherwise
       */

        public static boolean hasTextPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextPhrase(id, model)!=null);
        }
    }

    public static TextPhraseBase.ClassMgr getTextPhraseClassMgr()
    {
        return new TextPhraseBase.ClassMgr();
    }

   /**
   * Constructs a TextPhraseBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TextPhrase
   */
    public TextPhraseBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Tf_socialNet property
* @return String with the Tf_socialNet
*/
    public String getTf_socialNet()
    {
        return getSemanticObject().getProperty(social_tf_socialNet);
    }

/**
* Sets the Tf_socialNet property
* @param value long with the Tf_socialNet
*/
    public void setTf_socialNet(String value)
    {
        getSemanticObject().setProperty(social_tf_socialNet, value);
    }
}
