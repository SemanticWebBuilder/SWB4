/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 * Lexicon para un idioma determinado.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class LocaleLexicon {
    public static final String OBJ_TAG = "OBJ"; //SemanticClass
    public static final String DTT_TAG = "LPR"; //Datatype Property
    public static final String OBT_TAG = "OPR"; //Objecttype Property
    
    private HashMap<String, Word> objHash;
    private HashMap<String, Word> propHash;
    private ArrayList<String> prefixFilters;
    private String langCode = "es";
    private String langName = "Spanish";
    private String prefixString;
    private String[] stopWords = {"a", "ante", "bajo", "cabe", "con",
        "contra", "de", "desde", "durante",
        "en", "entre", "hacia", "hasta",
        "mediante", "para", "por", "según",
        "sin", "sobre", "tras", "el", "la",
        "los", "las", "ellos", "ellas", "un",
        "uno", "unos", "una", "unas", "y", "o",
        "pero", "si", "no", "como", "que", "su",
        "sus", "esto", "eso", "esta", "esa",
        "esos", "esas", "del"
    };


    public LocaleLexicon() {
        this("es", "Spanish");
    }

    public LocaleLexicon(String languageCode, String languageName) {
        this(languageCode, languageName, "");
    }

    /**
     * Constructor. Crea una nueva instancia de LocaleLexicon para un idioma
     * definido.
     * @param languageCode código del idioma como se usa en Semantic WebBuilder
     * @param languageName código del idioma como se usa por el algoritmo de
     * snowball de lucene.
     * @param prexFilter prefijos para filtrado separados por comas.
     */
    public LocaleLexicon(String languageCode, String languageName, String prexFilter) {
        langCode = languageCode;
        langName = languageName;

        //System.out.println("----Creando lexicon");
        if (!prexFilter.trim().equals(""))
            prefixFilters = new ArrayList<String>(Arrays.asList(prexFilter.split(",")));

        prefixString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
        objHash = new HashMap<String, Word>();
        propHash = new HashMap<String, Word>();
        buildLexicon();
        /*System.out.println("---" + objHash.values().size() + " objetos agregados");
        System.out.println("---" + propHash.values().size() + " propiedades agregadas");
        System.out.println("---Cadena de prefijos:");
        System.out.println(prefixString);*/
    }

    public void buildLexicon () {
        Iterator<SemanticClass> scit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
        while(scit.hasNext()) {
            SemanticClass sc = scit.next();

            if (prefixFilters == null || (prefixFilters != null && prefixFilters.contains(sc.getPrefix()))) {
                addWord(sc.getDisplayName(langCode), OBJ_TAG);

                String pf = buildPrefixEntry(sc.getPrefix(), sc.getOntClass().getNameSpace());
                if (prefixString.indexOf(pf) == -1) prefixString += pf + "\n";

                Iterator<SemanticProperty> spit = sc.listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    pf = buildPrefixEntry(sp.getPrefix(), sp.getRDFProperty().getNameSpace());
                    if (prefixString.indexOf(pf) == -1) prefixString += pf + "\n";

                    if (sp.isObjectProperty())
                        addWord(sp.getDisplayName(langCode), OBT_TAG);
                    else if (sp.isDataTypeProperty()) {
                        addWord(sp.getDisplayName(langCode), DTT_TAG);
                    }
                }
            }
        }
    }

    private String buildPrefixEntry(String prefix, String namespace) {
        if (prefix.trim().equals("") || namespace.trim().equals(""))
            return "";
        return "PREFIX " + prefix + ": " + "<" + namespace + ">";
    }

    public void addWord(String lexForm, String firstTag) {
        String wLemma = getSnowballForm(lexForm);
        if (firstTag.equals(OBJ_TAG) && objHash.get(wLemma) != null) return;
        if ((firstTag.equals(DTT_TAG) || firstTag.equals(OBT_TAG)) && propHash.get(wLemma) != null) return;

        Word w = new Word(lexForm);
        w.setLemma(wLemma);
        w.setTag(new Tag(firstTag));

        if (firstTag.equals(OBJ_TAG)) objHash.put(wLemma, w);
        if (firstTag.equals(OBT_TAG) || firstTag.equals(DTT_TAG)) propHash.put(wLemma, w);
        //TODO: Agregar sinónimos a partir de las variantes
    }

    public void addStopWord(String w) {
        if (!Arrays.asList(stopWords).contains(w.toLowerCase())) {
            String [] tList = new String[stopWords.length+1];
            for (int i = 0; i < stopWords.length; i++) {
                tList[i] = stopWords[i];
            }
            tList[stopWords.length] = w.toLowerCase();
            stopWords = tList;
        }
            
    }

    public String getSnowballForm(String input) {
        String res = "";

        //Create snowball analyzer
        Analyzer SnballAnalyzer = new SnowballAnalyzer(langName, stopWords);

        //Create token stream for prhase composition
        TokenStream ts = SnballAnalyzer.tokenStream("sna", new StringReader(input));

        //Build the result string with the analyzed tokens
        Token tk;
        try {
            while ((tk = ts.next()) != null) {
                res = res + new String(tk.termBuffer(), 0, tk.termLength()) + " ";
            }
            ts.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SWBLexicon.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res.trim();
    }

    public String getPrefixString () {
        return prefixString;
    }

    public Iterator<Word> listWords () {
        ArrayList<Word> totWords = new ArrayList<Word>();
        totWords.addAll(objHash.values());
        totWords.addAll(propHash.values());

        return totWords.iterator();
    }

    public Iterator<Word> listObjectWords() {
        return objHash.values().iterator();
    }

    public Iterator<Word> listPropertyWords() {
        return propHash.values().iterator();
    }

    public String getLanguageCode() {
        return langCode;
    }

    public String getLanguageName() {
        return langName;
    }

    public Word getWord(String lexForm, boolean asObject) {
        if (asObject) return objHash.get(getSnowballForm(lexForm));
        return propHash.get(getSnowballForm(lexForm));
    }
}
