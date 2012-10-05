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