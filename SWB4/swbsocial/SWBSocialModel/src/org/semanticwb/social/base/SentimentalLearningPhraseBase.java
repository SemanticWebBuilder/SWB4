package org.semanticwb.social.base;


   /**
   * Palabras y Frases en secuencia que puede ir aprendiendo el sistema, esto por medio de la re-clasificación manual de los mensajes por parte de los usuarios 
   */
public abstract class SentimentalLearningPhraseBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Frase a ser re-clasificada por los usuarios.
   */
    public static final org.semanticwb.platform.SemanticProperty social_Phrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#Phrase");
   /**
   * Calor sentimental que le dan los usuarios al clasificar los mensajes, este valor se lo dan a las palabras o secuencia de palabras que se guardan en este objeto. 1=Positivo, 2=Negativo
   */
    public static final org.semanticwb.platform.SemanticProperty social_SentimentType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#SentimentType");
   /**
   * Valor que le dan los usuarios al clasificar una palabra o secuencia de palabra. 1=Alto;2=Bajo;0=Medio
   */
    public static final org.semanticwb.platform.SemanticProperty social_IntensityType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#IntensityType");
   /**
   * Palabras y Frases en secuencia que puede ir aprendiendo el sistema, esto por medio de la re-clasificación manual de los mensajes por parte de los usuarios
   */
    public static final org.semanticwb.platform.SemanticClass social_SentimentalLearningPhrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SentimentalLearningPhrase");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SentimentalLearningPhrase");

    public static class ClassMgr
    {
       /**
       * Returns a list of SentimentalLearningPhrase for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SentimentalLearningPhrase
       */

        public static java.util.Iterator<org.semanticwb.social.SentimentalLearningPhrase> listSentimentalLearningPhrases(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SentimentalLearningPhrase>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SentimentalLearningPhrase for all models
       * @return Iterator of org.semanticwb.social.SentimentalLearningPhrase
       */

        public static java.util.Iterator<org.semanticwb.social.SentimentalLearningPhrase> listSentimentalLearningPhrases()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SentimentalLearningPhrase>(it, true);
        }

        public static org.semanticwb.social.SentimentalLearningPhrase createSentimentalLearningPhrase(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SentimentalLearningPhrase.ClassMgr.createSentimentalLearningPhrase(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SentimentalLearningPhrase
       * @param id Identifier for org.semanticwb.social.SentimentalLearningPhrase
       * @param model Model of the org.semanticwb.social.SentimentalLearningPhrase
       * @return A org.semanticwb.social.SentimentalLearningPhrase
       */
        public static org.semanticwb.social.SentimentalLearningPhrase getSentimentalLearningPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SentimentalLearningPhrase)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SentimentalLearningPhrase
       * @param id Identifier for org.semanticwb.social.SentimentalLearningPhrase
       * @param model Model of the org.semanticwb.social.SentimentalLearningPhrase
       * @return A org.semanticwb.social.SentimentalLearningPhrase
       */
        public static org.semanticwb.social.SentimentalLearningPhrase createSentimentalLearningPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SentimentalLearningPhrase)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SentimentalLearningPhrase
       * @param id Identifier for org.semanticwb.social.SentimentalLearningPhrase
       * @param model Model of the org.semanticwb.social.SentimentalLearningPhrase
       */
        public static void removeSentimentalLearningPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SentimentalLearningPhrase
       * @param id Identifier for org.semanticwb.social.SentimentalLearningPhrase
       * @param model Model of the org.semanticwb.social.SentimentalLearningPhrase
       * @return true if the org.semanticwb.social.SentimentalLearningPhrase exists, false otherwise
       */

        public static boolean hasSentimentalLearningPhrase(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSentimentalLearningPhrase(id, model)!=null);
        }
    }

    public static SentimentalLearningPhraseBase.ClassMgr getSentimentalLearningPhraseClassMgr()
    {
        return new SentimentalLearningPhraseBase.ClassMgr();
    }

   /**
   * Constructs a SentimentalLearningPhraseBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SentimentalLearningPhrase
   */
    public SentimentalLearningPhraseBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Phrase property
* @return String with the Phrase
*/
    public String getPhrase()
    {
        return getSemanticObject().getProperty(social_Phrase);
    }

/**
* Sets the Phrase property
* @param value long with the Phrase
*/
    public void setPhrase(String value)
    {
        getSemanticObject().setProperty(social_Phrase, value);
    }

/**
* Gets the SentimentType property
* @return int with the SentimentType
*/
    public int getSentimentType()
    {
        return getSemanticObject().getIntProperty(social_SentimentType);
    }

/**
* Sets the SentimentType property
* @param value long with the SentimentType
*/
    public void setSentimentType(int value)
    {
        getSemanticObject().setIntProperty(social_SentimentType, value);
    }

/**
* Gets the IntensityType property
* @return int with the IntensityType
*/
    public int getIntensityType()
    {
        return getSemanticObject().getIntProperty(social_IntensityType);
    }

/**
* Sets the IntensityType property
* @param value long with the IntensityType
*/
    public void setIntensityType(int value)
    {
        getSemanticObject().setIntProperty(social_IntensityType, value);
    }
}
