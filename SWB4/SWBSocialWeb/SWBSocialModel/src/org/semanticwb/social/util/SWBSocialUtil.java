/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.util;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.SWBModel;
import org.semanticwb.social.PunctuationSign;
import org.semanticwb.social.WordsToMonitor;
import org.semanticwb.social.util.lucene.SpanishAnalizer;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase de tipo utilerías 
 */

public class SWBSocialUtil implements SWBAppObject{

    /**
     * Holds a reference to a log utility.
     * <p>Mantiene una referencia a la utiler&iacute;a de generaci&oacute;n de bit&aacute;coras.</p>
     */
    private static Logger log = SWBUtils.getLogger(SWBSocialUtil.class);
    /**
     * Holds a reference to an object of this class.
     * <p>Mantiene una referencia a un objeto de esta clase.</p>
     */
    static private SWBSocialUtil instance;
    static ArrayList<String> aDoubles=new ArrayList();
    static HashMap<String, String> hmapChanges=new HashMap();
    
    //SENTIMENTAL STATATIC CONTANTS
    static public int SENTIMENT_NEUTRAL=0;
    static public int SENTIMENT_POSITIVE=1;
    static public int SENTIMENT_NEGATIVE=2;
    
    //INTENSITIVE STATATIC CONTANTS
    static public int INTENSITIVE_HIGH=2;
    static public int INTENSITIVE_MEDIUM=1;
    static public int INTENSITIVE_LOW=0;
    
    private static Properties prop = new Properties();
    
    
    

    /**
     * Creates a new object of this class.
     */
    public SWBSocialUtil() {
       System.out.println("Entra a SWBSocialUtil/createInstance");
       //createInstance();
       init();
    }

   
    /**
     * Retrieves a reference to the only one existing object of this class.
     * <p>Obtiene una referencia al &uacute;nico objeto existente de esta clase.</p>
     * @param applicationPath a string representing the path for this application
     * @return a reference to the only one existing object of this class
     */
    static public synchronized SWBSocialUtil createInstance() {
        System.out.println("Entra a SWBSocialUtil/createInstance");
        if (instance == null) {
            instance = new SWBSocialUtil();
        }
        return instance;
    }
    
     
    public ArrayList getDoublesArray()
    {
        return aDoubles;
    }

    /*
     * Metodo que regresa phonemas
     */
    public HashMap getChangesMap()
    {
        return hmapChanges;
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init() {
        System.out.println("Init de SWBSocialUtil-Jorge");
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

    public static class Strings {

        /**
         * Lee una string y devuelve las palabras que se encuentren dentro.
         * @param text Texto a procesar
         * @return Listado de palabras procesadas
         */
        public static ArrayList<String> stripWordsByLine(String text) {
            StringTokenizer st = new StringTokenizer(text);
            ArrayList<String> words = new ArrayList<String>();
            while (st.hasMoreTokens())
            {
                String temp = st.nextToken();
                String word = temp.replaceAll("\\W+", "");
                words.add(word);
            }
            return words;
        }

        /*
         * Elimina signos de puntualcion
         */
        public static String removePuntualSigns(String text, SWBModel model)
        {
            Iterator <PunctuationSign> itPunctuationSigns=PunctuationSign.ClassMgr.listPunctuationSigns(model);
            while(itPunctuationSigns.hasNext())
            {
                PunctuationSign puncSign=itPunctuationSigns.next();
                text=text.replaceAll(puncSign.getPuntuationSign(), "");
            }
            return text;
        }


         /**
         * Lee una string caracter por caracter y si encuentra que existen en el mismo
         * mas de un caracter en mayusculas, entonces lo considera como una palabra (String)
         * intensa y regresa true, de lo contrario regresa false
         * @param word Palabra a procesar, Limitnumber es el limite para considerar una palabra intensa
         * @return True si tiene la palabra mas mayusculas que el número que llega en el parametro Limitnumber
          * o false de lo contrario
         */
        public static boolean isIntensiveWordByUpperCase(String word, int limitNumber)
        {
            int cont=0;
            int l = word.length();
            for (int x = 0; x < l; x++)
            {
                char chr = word.charAt(x);
                if (chr >= 'A' && chr <= 'Z')
                {
                    cont++;
                    if(cont>=limitNumber)
                    {
                        return true;
                    }
                }
            }
            return false;
        }


        /**
         * Replaces accented characters and blank spaces in the string given.
         * Makes the changes in a case sensitive manner, the following are some examples
         * of the changes this method makes: <br>
         *
         * @param txt a string in which the characters are going to be replaced
         * @param replaceSpaces a {@code boolean} indicating if blank spaces are going to be replaced or not
         * @param ch a {@code char} especifica algún caracter de puntuación permitido
         * @return a string similar to {@code txt} but with neither accented or
         * special characters nor symbols in it. un objeto string similar
         * a {@code txt} pero sin caracteres acentuados o especiales y sin s&iacute;mbolos
         * {@literal Á} is replaced by {@literal A} <br>
         * {@literal Ê} is replaced by {@literal E} <br>
         * {@literal Ï} is replaced by {@literal I} <br>
         * {@literal â} is replaced by {@literal a} <br>
         * {@literal ç} is replaced by {@literal c} <br>
         * and blank spaces are replaced by underscore characters, any symbol in
         * {@code txt} other than underscore is eliminated.
         * <p>Reemplaza caracteres acentuados y espacios en blanco en {@code txt}.
         * Realiza los cambios respetando caracteres en may&uacute;sculas o min&uacute;sculas
         * los caracteres en blanco son reemplazados por guiones bajos, cualquier s&iacute;mbolo
         * diferente a gui&oacute;n bajo es eliminado.</p>
         */
        public static String replaceSpecialCharacters(String txt)
        {
            StringBuilder ret = new StringBuilder();
            String aux = txt;
            //aux = aux.toLowerCase();
            aux = aux.replace('Á', 'A');
            aux = aux.replace('Ä', 'A');
            aux = aux.replace('Å', 'A');
            aux = aux.replace('Â', 'A');
            aux = aux.replace('À', 'A');
            aux = aux.replace('Ã', 'A');

            aux = aux.replace('É', 'E');
            aux = aux.replace('Ê', 'E');
            aux = aux.replace('È', 'E');
            aux = aux.replace('Ë', 'E');

            aux = aux.replace('Í', 'I');
            aux = aux.replace('Î', 'I');
            aux = aux.replace('Ï', 'I');
            aux = aux.replace('Ì', 'I');

            aux = aux.replace('Ó', 'O');
            aux = aux.replace('Ö', 'O');
            aux = aux.replace('Ô', 'O');
            aux = aux.replace('Ò', 'O');
            aux = aux.replace('Õ', 'O');

            aux = aux.replace('Ú', 'U');
            aux = aux.replace('Ü', 'U');
            aux = aux.replace('Û', 'U');
            aux = aux.replace('Ù', 'U');

            //aux = aux.replace('Ñ', 'N');


            aux = aux.replace('Ç', 'C');
            aux = aux.replace('Ý', 'Y');

            aux = aux.replace('á', 'a');
            aux = aux.replace('à', 'a');
            aux = aux.replace('ã', 'a');
            aux = aux.replace('â', 'a');
            aux = aux.replace('ä', 'a');
            aux = aux.replace('å', 'a');

            aux = aux.replace('é', 'e');
            aux = aux.replace('è', 'e');
            aux = aux.replace('ê', 'e');
            aux = aux.replace('ë', 'e');

            aux = aux.replace('í', 'i');
            aux = aux.replace('ì', 'i');
            aux = aux.replace('î', 'i');
            aux = aux.replace('ï', 'i');

            aux = aux.replace('ó', 'o');
            aux = aux.replace('ò', 'o');
            aux = aux.replace('ô', 'o');
            aux = aux.replace('ö', 'o');
            aux = aux.replace('õ', 'o');

            aux = aux.replace('ú', 'u');
            aux = aux.replace('ù', 'u');
            aux = aux.replace('ü', 'u');
            aux = aux.replace('û', 'u');

            //aux = aux.replace('ñ', 'n');

            aux = aux.replace('ç', 'c');
            aux = aux.replace('ÿ', 'y');
            aux = aux.replace('ý', 'y');

            int l = aux.length();
            for (int x = 0; x < l; x++)
            {
                char chr = aux.charAt(x);
                if (chr==' ' || (chr >= '0' && chr <= '9') || (chr >= 'a' && chr <= 'z')
                        || (chr >= 'A' && chr <= 'Z') || chr == '_')
                {
                    ret.append(chr);
                }
            }
            aux = ret.toString();
            return aux;
        }

    }

    public static class words2Monitor {

        /*
         * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
         * en el campo de palabras de la "Empresa" en todos los objetos de tipo WordsToMonitor
         */
        public static String[] getCompanyWords(SWBModel model) {
            String words = null;
            String[] awords = null;
            Iterator<WordsToMonitor> itWords2Monitor = WordsToMonitor.ClassMgr.listWordsToMonitors(model);
            while (itWords2Monitor.hasNext()) {
                WordsToMonitor words2monitor = itWords2Monitor.next();
                String wordsTmp = words2monitor.getCompany();
                if (wordsTmp != null) {
                    if (words == null) {
                        words = wordsTmp;
                    } else {
                        words += wordsTmp;
                    }
                }
            }
            if (words != null) {
                awords = words.split("\\;");
            }
            return awords;
        }

        /*
         * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
         * en el campo de palabras de la "Competencia" en todos los objetos de tipo WordsToMonitor
         */
        public static String[] getCompetitionWords(SWBModel model) {
            String words = null;
            String[] awords = null;
            Iterator<WordsToMonitor> itWords2Monitor = WordsToMonitor.ClassMgr.listWordsToMonitors(model);
            while (itWords2Monitor.hasNext()) {
                WordsToMonitor words2monitor = itWords2Monitor.next();
                String wordsTmp = words2monitor.getCompetition();
                if (wordsTmp != null) {
                    if (words == null) {
                        words = wordsTmp;
                    } else {
                        words += wordsTmp;
                    }
                }
            }
            if (words != null) {
                awords = words.split("\\;");
            }
            return awords;
        }

        /*
         * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
         * en el campo de palabras de "Productos y Servicios" en todos los objetos de tipo WordsToMonitor
         */
        public static String[] getProductAndServicesWords(SWBModel model) {
            String words = null;
            String[] awords = null;
            Iterator<WordsToMonitor> itWords2Monitor = WordsToMonitor.ClassMgr.listWordsToMonitors(model);
            while (itWords2Monitor.hasNext()) {
                WordsToMonitor words2monitor = itWords2Monitor.next();
                String wordsTmp = words2monitor.getProductsAndServices();
                if (wordsTmp != null) {
                    if (words == null) {
                        words = wordsTmp;
                    } else {
                        words += wordsTmp;
                    }
                }
            }
            if (words != null) {
                awords = words.split("\\;");
            }
            return awords;
        }

        /*
         * Regresa arrglo de string de todas las palabras (separadas x punto y coma(;))
         * en el campo de palabras de "Otras Palabras" en todos los objetos de tipo WordsToMonitor
         */
        public static String[] getOtherWords(SWBModel model) {
            String words = null;
            String[] awords = null;
            Iterator<WordsToMonitor> itWords2Monitor = WordsToMonitor.ClassMgr.listWordsToMonitors(model);
            while (itWords2Monitor.hasNext()) {
                WordsToMonitor words2monitor = itWords2Monitor.next();
                String wordsTmp = words2monitor.getOtherWords();
                if (wordsTmp != null) {
                    if (words == null) {
                        words = wordsTmp;
                    } else {
                        words += wordsTmp;
                    }
                }
            }
            if (words != null) {
                awords = words.split("\\;");
            }
            return awords;
        }

        /*
         * Metod que regresa cuales con la palabras o frases para monitorear en un determinado modelo
         * Este metodo es muy posible que NO nos sea util al final ya que pensamos utilizar diversos streams
         * los cuales pueden tener multiples frases, y todo esto en un mismo sitio o marca
         */
        public static String getWords2Monitor(String delimiter, SWBModel model) {
            //Palabras acerca de la compañia
            String words2monitor = "";
            String[] companyWords = getCompanyWords(model);
            if(companyWords!=null)
            {
                for (int i = 0; i < companyWords.length; i++) {
                    System.out.println("companyWord[" + i + "]:" + companyWords[i]);
                    if (words2monitor.length() == 0) {
                        words2monitor = companyWords[i];
                    } else {
                        words2monitor += delimiter + companyWords[i];
                    }
                }
            }
            //Palabras acerca de la competencia
            String[] competitionWords = getCompetitionWords(model);
            if(competitionWords!=null)
            {
                for (int i = 0; i < competitionWords.length; i++) {
                    System.out.println("competitionWords[" + i + "]:" + competitionWords[i]);
                    if (words2monitor.length() == 0) {
                        words2monitor = competitionWords[i];
                    } else {
                        words2monitor += delimiter + competitionWords[i];
                    }
                }
            }
            //Palabras acerca de productos y servicios
            String[] pAndServWords = getProductAndServicesWords(model);
            if(pAndServWords!=null)
            {
                for (int i = 0; i < pAndServWords.length; i++) {
                    System.out.println("pAndServWords[" + i + "]:" + pAndServWords[i]);
                    if (words2monitor.length() == 0) {
                        words2monitor = pAndServWords[i];
                    } else {
                        words2monitor += delimiter + pAndServWords[i];
                    }
                }
            }
            //Palabras acerca de productos y servicios
            String[] otherWords = getOtherWords(model);
            if(otherWords!=null)
            {
                for (int i = 0; i < otherWords.length; i++) {
                    System.out.println("otherWords[" + i + "]:" + otherWords[i]);
                    if (words2monitor.length() == 0) {
                        words2monitor = otherWords[i];
                    } else {
                        words2monitor += delimiter + otherWords[i];
                    }
                }
            }
            return words2monitor;
        }
    }

    /*
     * Metodo que normaliza una palabra, esto de acuerdo a definición realizada internamente en el área
     */
    public static class Classifier
    {
        public static NormalizerCharDuplicate normalizer(String in_word)
        {
            boolean isCharDuplicate=false;
            String tmp="";
            char [] in_wordArray=in_word.toCharArray();
            for(int i=0;i<in_wordArray.length;i++)
            {
                String in_wordChar=String.valueOf(in_wordArray[i]);
                if(tmp.length()>1)
                {
                    if(aDoubles.contains(in_wordChar))
                    {
                        if(tmp.substring(tmp.length()-2, tmp.length()-1).equalsIgnoreCase(tmp.substring(tmp.length()-1)) && (tmp.substring(tmp.length()-1).equalsIgnoreCase(in_wordChar)))
                        {
                            isCharDuplicate=true;
                            continue;
                        }else
                        {
                            tmp+=in_wordChar;
                        }
                    }else{
                        if(tmp.substring(tmp.length()-1).equalsIgnoreCase(in_wordChar))
                        {
                            isCharDuplicate=true;
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
            if(tmp.length()>2 && tmp.substring(0,1).equalsIgnoreCase(tmp.substring(1,2)))
            {
                isCharDuplicate=true;
                tmp=tmp.substring(1);
            }
            //If que Revisa si los ultimos 2 caracteres son iguales, si es así, elimina el último.
            if(tmp.length()>2 && tmp.substring(tmp.length()-2, tmp.length()-1).equalsIgnoreCase(tmp.substring(tmp.length()-1)))
            {
                isCharDuplicate=true;
                tmp=tmp.substring(0, tmp.length()-1);
            }

            return new NormalizerCharDuplicate(tmp, isCharDuplicate);

        }

        /*
         * Metodo que regresa el fonema de una palabra, esto de acuerdo a definición
         * realizada internamente en el área.
         */
        public static String phonematize(String phase)
        {
            if(phase==null || phase.isEmpty()) return phase;
            String tmp="";
            String out_word = "";
            phase = phase.toLowerCase();

            char [] in_wordArray=phase.toCharArray();
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
                    }else if(in_wordChar.equalsIgnoreCase("h")){  //Elimina la h
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

        /*
        public static String getRootWord(String word)
        {
            try
            {
                TokenStream tokenStream = new SpanishAnalizer().tokenStream("word", new StringReader(word));
                //OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                while (tokenStream.incrementToken()) {
                    String term = charTermAttribute.toString();
                    if(term!=null) return term;
                    else return word;
                }
            }catch(Exception e){
                log.error(e);
            }
            return word;
        }*/

        /*
         * Metodo que regrasa la raíz de una palabra
         */
        public static String getRootWord(String phrase)
        {
            String sphrase="";
            try
            {
                TokenStream tokenStream = new SpanishAnalizer().tokenStream("word", new StringReader(phrase));
                //OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                while (tokenStream.incrementToken()) {
                    String term = charTermAttribute.toString();
                    if(term!=null && sphrase.trim().length()>0) {
                        sphrase+=" "+term;
                    }else if(term!=null) {
                        sphrase=term;
                    }
                }
            }catch(Exception e){
                log.error(e);
            }
            if(sphrase.trim().length()>0)
            {
                return sphrase;
            }else{
                return phrase;
            }
        }

    }
    
    
    public static class Util
    {
        //Diferencias entre dos fechas
        //@param fechaInicial La fecha de inicio
        //@param fechaFinal  La fecha de fin
        //@return Retorna el numero de dias entre dos fechas
        public static int Datediff(Date fechaInicial, Date fechaFinal)
        {

            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String fechaInicioString = df.format(fechaInicial);
            try {
                fechaInicial = df.parse(fechaInicioString);
            } catch (ParseException ex) {
            }

            String fechaFinalString = df.format(fechaFinal);
            try {
                fechaFinal = df.parse(fechaFinalString);
            } catch (ParseException ex) {
            }

            long fechaInicialMs = fechaInicial.getTime();
            long fechaFinalMs = fechaFinal.getTime();
            long diferencia = fechaFinalMs - fechaInicialMs;
            double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
            return ((int) dias);
        }
    }
    
}

