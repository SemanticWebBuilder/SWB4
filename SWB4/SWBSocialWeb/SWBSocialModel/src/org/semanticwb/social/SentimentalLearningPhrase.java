package org.semanticwb.social;

import java.util.Iterator;
import java.util.Timer;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.social.util.SWBSocialUtil;


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
    
     static {
       //Observador de la clase "Stream", cada que haya un cambio en ella se ejecuta el siguiente código
        SentimentalLearningPhrase.sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                SemanticProperty semProp = (SemanticProperty) prop;
                if(action!=null && (action.equalsIgnoreCase("CREATE") || (action.equalsIgnoreCase("SET") && !semProp.getURI().equals(SentimentalLearningPhrase.social_Phrase.getURI())) && obj.instanceOf(SentimentalLearningPhrase.social_SentimentalLearningPhrase)))
                {
                    SentimentalLearningPhrase sLPhrase = (SentimentalLearningPhrase) obj.createGenericInstance();
                    if(sLPhrase.getOriginalPhrase()!=null)
                    {
                        System.out.println("sLPhrase:"+sLPhrase);
                        System.out.println("semProp:"+semProp);
                        System.out.println("Original Phrase:"+sLPhrase.getOriginalPhrase());
                        System.out.println("Phrase:"+sLPhrase.getPhrase());
                        String phrase=sLPhrase.getOriginalPhrase();
                        phrase = phrase.toLowerCase().trim();
                        phrase=SWBSocialUtil.Util.removePrepositions(phrase);
                        phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                        phrase = SWBSocialUtil.Classifier.getRootPhrase(phrase);
                        phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                        System.out.println("Prase a Grabar:"+phrase);
                        sLPhrase.setPhrase(phrase);
                    }
                }
            }
        });
     }
    
}
