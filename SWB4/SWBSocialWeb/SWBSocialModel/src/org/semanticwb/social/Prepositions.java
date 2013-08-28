package org.semanticwb.social;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.social.util.SWBSocialUtil;


   /**
   * Preposiciones (De lugar, de tiempo, etc) que no seran tomadas como palabras dentro de los mensajes de los post dentro del listener. Es decir, estas no deben ser evaluadas para analisis sentimental. 
   */
public class Prepositions extends org.semanticwb.social.base.PrepositionsBase 
{
    public Prepositions(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    static {
       //Observador de la clase "Stream", cada que haya un cambio en ella se ejecuta el siguiente código
        Prepositions.sclass.registerObserver(new SemanticObserver() {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action) {
                if(action!=null && obj.instanceOf(Prepositions.social_Prepositions))
                {
                    SWBSocialUtil.loadPrepositions();
                }
            }
        });
    }

}
