/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.nlp;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.logging.Level;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 * Lexicon para un idioma determinado.
 * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
 */
public class SWBLocaleLexicon {
    public static final String OBJ_TAG = "OBJ"; //SemanticClass
    public static final String DTT_TAG = "LPR"; //Datatype Property
    public static final String OBT_TAG = "OPR"; //Objecttype Property
    public static final String PARAM_URI = "URI"; //URI
    public static final String PARAM_ID = "ID"; //ID

    private HashMap<String, Word> objHash;       //Word hash for classes
    private HashMap<String, Word> propHash;      //Word hash for properties
    private HashMap<String, String> prefixHash;  //String hash for prefix definitions
    private ArrayList<String> prefixFilters;     //List of filtering prefixes
    private String langCode = "es";
    private String langName = "Spanish";
    private int maxWordLength;
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


    /**
     * Constructor.
     * Creates a new instance of a {@link SWBLocaleLexicon} with the default language (Spanish).
     * <p>
     * Crea una nueva instancia de un {@link SWBLocaleLexicon} para el idioma por defecto (Español).
     */
    public SWBLocaleLexicon() {
        this("es", "Spanish");
    }

    /**
     * Constructor.
     * Creates a new instance of a {@link SWBLocaleLexicon} for the specified languaje.
     * <p>
     * Crea una nueva instancia de un {@link SWBLocaleLexicon} para el idioma especificado.
     */
    public SWBLocaleLexicon(String languageCode, String languageName) {
        this(languageCode, languageName, "");
    }

    /**
     * Constructor.
     * Creates a new instance of a {@link SWBLocaleLexicon} for the specified languaje.
     * <p>
     * Crea una nueva instancia de un {@link SWBLocaleLexicon} para un idioma especificado.
     * definido.
     * @param languageCode Language code. <p> Código del idioma como se usa en Semantic WebBuilder
     * @param languageName Language name. <p> Nombre del idioma como se usa por el algoritmo de
     * snowball de lucene.
     * @param prexFilter Filtering prefixes, comma-separated.<p>Prefijos para filtrado separados por comas.
     */
    public SWBLocaleLexicon(String languageCode, String languageName, String prexFilter) {
        langCode = languageCode;
        langName = languageName;
        maxWordLength = 0;
        prefixHash = new HashMap<String, String>();
        objHash = new HashMap<String, Word>();
        propHash = new HashMap<String, Word>();
        if (!prexFilter.trim().equals(""))
            prefixFilters = new ArrayList<String>(Arrays.asList(prexFilter.split(",")));

        prefixHash.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        prefixHash.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
    }

    /**
     * Builds the lexicon.
     * Traverses the semantic classes and properties in the Semantic vocabulary of loaded
     * ontologies and adds their names to the lexicon through a {@link Word}.
     * <p>
     * Recorre las clases y propiedades en el vocabulario semántico de las ontologías
     * cargadas y agrega sus nombres al lexicon mediante un objeto de tipo {@link Word}.
     */
    public void buildLexicon () {
        Iterator<SemanticClass> scit = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
        while(scit.hasNext()) {
            SemanticClass sc = scit.next();

            //Store the max word length for n-grammar tokenization purposes
            if (sc.getDisplayName(langCode).split("[\\s]+").length > maxWordLength) {
                maxWordLength = sc.getDisplayName(langCode).split("[\\s]+").length;
            }

            if (prefixFilters == null || (prefixFilters != null && prefixFilters.contains(sc.getPrefix()))) {
                //Add the class information: lexical form, lemma, tag, URI and ID to a Word object
                String wLemma = getSnowballForm(sc.getDisplayName(langCode));
                if (objHash.get(wLemma) == null) {
                    Word w = new Word(sc.getDisplayName(langCode));
                    w.setLemma(wLemma);

                    Tag t = new Tag(OBJ_TAG);
                    t.setTagInfoParam(PARAM_URI, sc.getURI());
                    t.setTagInfoParam(PARAM_ID, sc.getPrefix() + ":" + sc.getName());

                    //Word objects have just one tag for now
                    w.setTag(t);
                    objHash.put(wLemma, w);
                }
                
                //Get the prefix definition for the class and add it to the prefix definition hash
                addPrefixDefinition(sc.getPrefix().trim(), sc.getOntClass().getNameSpace().trim());

                Iterator<SemanticProperty> spit = sc.listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    //Get the prefix definition for the property and add it to the prefix definition hash
                    addPrefixDefinition(sp.getPrefix().trim(), sp.getRDFProperty().getNameSpace().trim());

                    wLemma = getSnowballForm(sp.getDisplayName(langCode));
                    Word pw = propHash.get(wLemma);

                    //If property does not exists, create it
                    if (pw == null) {
                        pw = new Word(sp.getDisplayName(langCode));
                        pw.setLemma(wLemma);
                        propHash.put(wLemma, pw);
                    }
                    
                    Tag t = null;
                    if (sp.isObjectProperty()) {
                        t = new Tag(OBT_TAG);
                    } else {
                        t = new Tag(DTT_TAG);
                    }

                    t.setTagInfoParam(PARAM_URI, sp.getURI());
                    t.setTagInfoParam(PARAM_ID, sp.getPrefix() + ":" + sp.getName());
                    pw.addTag(t);
                }
            }
        }
    }

    /**
     * Builds a prefix definition string for a SPARQL query.
     * <p>
     * Construye una cadena de definición de prefijo para una consulta SPARQL.
     * @param prefix Prefix. <p> Prefijo.
     * @param namespace Namespace. <p> Namespace.
     * @return Prefix definition string. <p> Cadena de definición de prefijo.
     */
    private String buildPrefixEntry(String prefix, String namespace) {
        String ret = "";
        if (prefix != null && namespace != null && prefix.length() > 0 && namespace.length() > 0) {
            ret = "PREFIX " + prefix + ": " + "<" + namespace + ">";
        }
        return ret;
    }

    /**
     * Adds a new {@link Word} to the lexicon.
     * <p>
     * Agrega una nueva palabra ({@link Word}) al lexicon.
     * @param w The {@link Word}. <p> La palabra ({@link Word}).
     * @param asObject Wheter to add the {@link Word} to the Object or Property hash. <p>
     * Indica si la palabra se agregará al hash de objetos o de propiedades.
     */
    public void addWord(Word w, boolean asObject) {
        if (asObject) {
            objHash.put(w.getLemma(), w);
        } else {
            propHash.put(w.getLemma(), w);
        }
        //TODO: Agregar sinónimos a partir de las variantes
    }

    /**
     * Adds a new stop-word for the lexicon analyzer. <p>
     * Agrega un nuevo stop-word al analizador del léxicon.
     * @param w stop-word.
     */
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

     /**
     * Gets the snowball form of a word. <p>
     * Obtiene la raíz de la palabra usando el algoritmo snowball.
     * @param input Input word characters.
     * @return Snowball form for the word characters.
     */
    public String getSnowballForm(String input) {
        String res = "";

        //Create snowball analyzer
        Set stopTable = StopFilter.makeStopSet(Version.LUCENE_36, stopWords);
        Analyzer SnballAnalyzer = new SnowballAnalyzer(Version.LUCENE_36, langName, stopTable);

        //Create token stream for prhase composition
        TokenStream ts = SnballAnalyzer.tokenStream("sna", new StringReader(input));

        //Build the result string with the analyzed tokens
        try {
            boolean hasNext = ts.incrementToken();
            while (hasNext) {
                CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
                res = res + ta.toString() + " ";
                hasNext = ts.incrementToken();
                //res = res + new String(tk.termBuffer(), 0, tk.termLength()) + " ";
            }
            ts.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SWBLocaleLexicon.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res.trim();
    }

    public String getPrefixString () {
        StringBuilder ret = new StringBuilder();
        Iterator<String> keys = prefixHash.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            ret.append(buildPrefixEntry(key, prefixHash.get(key)));
            if (keys.hasNext()) {
                ret.append("\n");
            }
        }
        return ret.toString();
    }

    public void addPrefixDefinition(String prefix, String namespace) {
        if (prefixHash.get(prefix) == null) {
            prefixHash.put(prefix, namespace);
        }
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

    public List<String> getWordLexForms(boolean asObjects) {
        ArrayList<String> ret = new ArrayList<String>();
        if (asObjects) {
            Iterator<Word> vals = objHash.values().iterator();
            while(vals.hasNext()) {
                Word val = vals.next();
                ret.add(val.getLexicalForm());
            }
        } else {
            Iterator<Word> vals = propHash.values().iterator();
            while(vals.hasNext()) {
                Word val = vals.next();
                ret.add(val.getLexicalForm());
            }
        }

        return ret;
    }

    public List<String> getWordLexForms() {
        ArrayList<String> ret = new ArrayList<String>();
        Iterator<Word> vals = objHash.values().iterator();
        while(vals.hasNext()) {
            Word val = vals.next();
            ret.add(val.getLexicalForm());
        }

        vals = propHash.values().iterator();
        while(vals.hasNext()) {
            Word val = vals.next();
            ret.add(val.getLexicalForm());
        }
        return ret;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }
}