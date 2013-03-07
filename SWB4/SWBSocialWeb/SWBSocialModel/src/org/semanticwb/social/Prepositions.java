package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


   /**
   * Preposiciones (De lugar, de tiempo, etc) que no seran tomadas como palabras dentro de los mensajes de los post dentro del listener. Es decir, estas no deben ser evaluadas para analisis sentimental. 
   */
public class Prepositions extends org.semanticwb.social.base.PrepositionsBase 
{
    public Prepositions(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

}
