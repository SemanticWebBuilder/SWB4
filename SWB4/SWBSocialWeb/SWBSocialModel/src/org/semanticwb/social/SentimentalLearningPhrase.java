/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
                        /*
                        System.out.println("sLPhrase:"+sLPhrase);
                        System.out.println("semProp:"+semProp);
                        System.out.println("Original Phrase:"+sLPhrase.getOriginalPhrase());
                        System.out.println("Phrase:"+sLPhrase.getPhrase());
                        * */
                        String phrase=sLPhrase.getOriginalPhrase();
                        phrase = phrase.toLowerCase().trim();
                        phrase=SWBSocialUtil.Strings.removePrepositions(phrase);
                        phrase = SWBSocialUtil.Classifier.normalizer(phrase).getNormalizedPhrase();
                        phrase = SWBSocialUtil.Classifier.getRootPhrase(phrase);
                        phrase = SWBSocialUtil.Classifier.phonematize(phrase);
                        //System.out.println("Prase a Grabar:"+phrase);
                        sLPhrase.setPhrase(phrase);
                    }
                }
            }
        });
     }
    
}
