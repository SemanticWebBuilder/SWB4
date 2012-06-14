package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


   /**
   * Conjunto de Palabras a buscar dentro de los Post que llegan por el Listener y con las cuales se podra determinar el sentimiento y la intensidad de los mismos(Post). 
   */
public class SentimentWords extends org.semanticwb.social.base.SentimentWordsBase 
{
    public SentimentWords(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
    public static SentimentWords getSentimentalWordByWord(SWBModel model, String word)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(social_sentimentalWord, word);
        if(it.hasNext())
        {
            SemanticObject obj=it.next();
            return (SentimentWords)obj.createGenericInstance();
        }
        return null;
    }
     * 
     */
}
