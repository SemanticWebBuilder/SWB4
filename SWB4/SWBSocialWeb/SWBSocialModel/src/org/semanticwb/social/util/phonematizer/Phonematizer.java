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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util.phonematizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jorge.jimenez
 */
/*
 * Clase de Prueba, no se esta utilizando...
 */
public class Phonematizer {

    static ArrayList<String> aDoubles=new ArrayList();
    static HashMap<String, String> hmapChanges=new HashMap();

    public static void main(String [] args)
    {
        init();
        String in_word="inovasion";

        in_word=normalizer(in_word);
        System.out.println("normalizer:"+in_word);
        String out_word=phonematize(in_word);
        System.out.println("out_word:"+out_word);
    }



    private static String normalizer(String in_word)
    {
        String tmp="";
        char [] in_wordArray=in_word.toCharArray();
        for(int i=0;i<in_wordArray.length;i++)
        {
            String in_wordChar=String.valueOf(in_wordArray[i]);
            if(tmp.length()>1)
            {
                if(aDoubles.contains(in_wordChar))
                {
                    if(tmp.substring(tmp.length()-2, tmp.length()-1).equals(tmp.substring(tmp.length()-1)) && (tmp.substring(tmp.length()-1).equals(in_wordChar)))
                    {
                        continue;
                    }else
                    {
                        tmp+=in_wordChar;
                    }
                }else{
                    if(tmp.substring(tmp.length()-1).equals(in_wordChar))
                    {
                        continue;
                    }else{
                        tmp+=in_wordChar;
                    }
                }
            }else{
                tmp+=in_wordChar;
            }
        }

        //If que Revisa si los primeros 2 caracteres son iguales, si es así, elimina el Primero.
        if(tmp.length()>2 && tmp.substring(0,1).equals(tmp.substring(1,2)))
        {
            tmp=tmp.substring(1);
        }
        //If que Revisa si los ultimos 2 caracteres son iguales, si es así, elimina el último.
        if(tmp.length()>2 && tmp.substring(tmp.length()-2, tmp.length()-1).equals(tmp.substring(tmp.length()-1)))
        {
            tmp=tmp.substring(0, tmp.length()-1);
        }
        return tmp;
    }

    private static String phonematize(String in_word)
    {
        String tmp="";
        String out_word = "";
        in_word = in_word.toLowerCase();

        char [] in_wordArray=in_word.toCharArray();
        for(int i=0;i<in_wordArray.length;i++)
        {
            String in_wordChar=String.valueOf(in_wordArray[i]);
            if(aDoubles.contains(in_wordChar))
            {
                tmp+=in_wordChar;
                continue;
            }else if(tmp.trim().length()>0)    //Busca sonidos que se representan graficamente con dos caracteres
            {
                if(hmapChanges.containsKey(tmp + in_wordChar))
                {
                    out_word += hmapChanges.get(tmp + in_wordChar);
                    tmp = "";
                    continue;
                }else if(hmapChanges.containsKey(tmp))
                {
                    out_word+= hmapChanges.get(tmp) + in_wordChar;
                    tmp = "";
                    continue;
                }
                if(hmapChanges.containsKey(in_wordChar))
                {
                    if(aDoubles.contains(hmapChanges.get(in_wordChar)))
                    {
                        tmp+=hmapChanges.get(in_wordChar);
                        continue;
                    }
                } else {
                        out_word+=tmp+in_wordChar;
                        tmp="";
                        continue;
                    }
            }else { //Mapea los caracteres con su sonido correspondiente
                if(hmapChanges.containsKey(in_wordChar))
                {
                    if(aDoubles.contains(hmapChanges.get(in_wordChar)))
                    {
                        tmp+=hmapChanges.get(in_wordChar);
                        continue;
                    }else{
                        out_word+=hmapChanges.get(in_wordChar);
                        tmp="";
                        continue;
                    }
                }else if(in_wordChar.equals("h")){  //Elimina la h
                    continue;
                }
                out_word+=in_wordChar;
                tmp="";
                continue;
            }
        }
        if(tmp.length()>0)
        {
            out_word+=tmp;
            tmp="";
        }

        return out_word;
    }


    private static void init()
    {
         //Carga Valores a ArrayList
         aDoubles.add("b");
         aDoubles.add("p");
         aDoubles.add("q");
         aDoubles.add("s");
         aDoubles.add("c");
         aDoubles.add("g");
         aDoubles.add("n");
         aDoubles.add("m");
         aDoubles.add("l");
         aDoubles.add("p");
         aDoubles.add("t");
         aDoubles.add("f");
         aDoubles.add("d");


         //Carga valores a HashMap
         hmapChanges.put("nge", "nje");
         hmapChanges.put("ngi", "nji");
         hmapChanges.put("ch", "ch");
         hmapChanges.put("cc", "ks");
         hmapChanges.put("ci", "si");
         hmapChanges.put("qu", "k");
         hmapChanges.put("w", "gu");
         hmapChanges.put("nn", "n");
         hmapChanges.put("mm", "m");
         hmapChanges.put("ll", "y");
         hmapChanges.put("pp", "p");
         hmapChanges.put("ce", "se");
         hmapChanges.put("q", "k");
         hmapChanges.put("ss", "s");
         hmapChanges.put("ge", "je");
         hmapChanges.put("tt", "t");
         hmapChanges.put("ff", "f");
         hmapChanges.put("v", "b");
         hmapChanges.put("x", "ks");
         hmapChanges.put("z", "s");
         hmapChanges.put("dd", "d");
         hmapChanges.put("gi", "ji");
         hmapChanges.put("bb", "b");
         hmapChanges.put("c", "k");
    }

}