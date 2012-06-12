package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


   /**
   * Palabras y Frases en secuencia que puede ir aprendiendo el sistema, esto por medio de la re-clasificación manual de los mensajes por parte de los usuarios 
   */
public class SentimentalLearningPhrase extends org.semanticwb.social.base.SentimentalLearningPhraseBase 
{
    public SentimentalLearningPhrase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static SentimentalLearningPhrase getSentimentalLearningPhrasebyPhrase(String phrase, SWBModel model)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(social_Phrase, phrase.trim().toLowerCase()); //No encuentra
        if(it.hasNext())
        {
            SemanticObject obj=it.next();
            SentimentalLearningPhrase slp=(SentimentalLearningPhrase)obj.createGenericInstance();
            return slp;
        }
        return null;
    }
}
