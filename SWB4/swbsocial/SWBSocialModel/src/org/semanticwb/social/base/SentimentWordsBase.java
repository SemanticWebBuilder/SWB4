package org.semanticwb.social.base;


   /**
   * Conjunto de Palabras a buscar dentro de los Post que llegan por el Listener y con las cuales se podra determinar el sentimiento y la intensidad de los mismos(Post). 
   */
public abstract class SentimentWordsBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Valor sentimental de la palabra
   */
    public static final org.semanticwb.platform.SemanticProperty social_SentimentalValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#SentimentalValue");
   /**
   * Valor de intensidad de la palabra
   */
    public static final org.semanticwb.platform.SemanticProperty social_IntensityValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#IntensityValue");
   /**
   * Conjunto de Palabras a buscar dentro de los Post que llegan por el Listener y con las cuales se podra determinar el sentimiento y la intensidad de los mismos(Post).
   */
    public static final org.semanticwb.platform.SemanticClass social_SentimentWords=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SentimentWords");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SentimentWords");

    public static class ClassMgr
    {
       /**
       * Returns a list of SentimentWords for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SentimentWords
       */

        public static java.util.Iterator<org.semanticwb.social.SentimentWords> listSentimentWordses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SentimentWords>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SentimentWords for all models
       * @return Iterator of org.semanticwb.social.SentimentWords
       */

        public static java.util.Iterator<org.semanticwb.social.SentimentWords> listSentimentWordses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SentimentWords>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SentimentWords
       * @param id Identifier for org.semanticwb.social.SentimentWords
       * @param model Model of the org.semanticwb.social.SentimentWords
       * @return A org.semanticwb.social.SentimentWords
       */
        public static org.semanticwb.social.SentimentWords getSentimentWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SentimentWords)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SentimentWords
       * @param id Identifier for org.semanticwb.social.SentimentWords
       * @param model Model of the org.semanticwb.social.SentimentWords
       * @return A org.semanticwb.social.SentimentWords
       */
        public static org.semanticwb.social.SentimentWords createSentimentWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SentimentWords)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SentimentWords
       * @param id Identifier for org.semanticwb.social.SentimentWords
       * @param model Model of the org.semanticwb.social.SentimentWords
       */
        public static void removeSentimentWords(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SentimentWords
       * @param id Identifier for org.semanticwb.social.SentimentWords
       * @param model Model of the org.semanticwb.social.SentimentWords
       * @return true if the org.semanticwb.social.SentimentWords exists, false otherwise
       */

        public static boolean hasSentimentWords(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSentimentWords(id, model)!=null);
        }
    }

    public static SentimentWordsBase.ClassMgr getSentimentWordsClassMgr()
    {
        return new SentimentWordsBase.ClassMgr();
    }

   /**
   * Constructs a SentimentWordsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SentimentWords
   */
    public SentimentWordsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the SentimentalValue property
* @return float with the SentimentalValue
*/
    public float getSentimentalValue()
    {
        return getSemanticObject().getFloatProperty(social_SentimentalValue);
    }

/**
* Sets the SentimentalValue property
* @param value long with the SentimentalValue
*/
    public void setSentimentalValue(float value)
    {
        getSemanticObject().setFloatProperty(social_SentimentalValue, value);
    }

/**
* Gets the IntensityValue property
* @return float with the IntensityValue
*/
    public float getIntensityValue()
    {
        return getSemanticObject().getFloatProperty(social_IntensityValue);
    }

/**
* Sets the IntensityValue property
* @param value long with the IntensityValue
*/
    public void setIntensityValue(float value)
    {
        getSemanticObject().setFloatProperty(social_IntensityValue, value);
    }
}
