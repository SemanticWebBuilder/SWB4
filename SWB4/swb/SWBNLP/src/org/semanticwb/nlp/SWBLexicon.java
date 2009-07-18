package org.semanticwb.nlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import org.apache.lucene.analysis.Analyzer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 * A lexicon is a list of tagged words. For Semantic WebBuilder, the lexical form
 * of a word is the display name (in a specific language) for Semantic Classes
 * and Semantic Properties. For the analysis purpose, Class names and Propery
 * names are stored in separated sets.
 *
 * Un SWBLexicon (diccionario) es una lista de palabras etiquetadas. Para Semantic
 * WebBuilder, la forma lexica de las palabras es el displayName (para un idioma
 * especifico) de las clases y propiedades semanticas. Para propósitos del
 * analisis, los nombres de las clases y de las propiedades se almacenan en
 * conjuntos separados.
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBLexicon {

    private Logger log = SWBUtils.getLogger(SWBLexicon.class);
    private File outputFile = null;
    private Writer outr = null;
    private String language = "es";
    private String spellDictPath;
    private String prefixString = "";
    private ArrayList<String> preflist = null;
    private HashMap<String, WordTag> objHash = null;
    private HashMap<String, WordTag> propHash = null;
    private HashMap<String, String> langCodes;
    private List prefixes = new ArrayList();
    private List namespaces = new ArrayList();
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
     * Creates a new SWBLexicon given the user's language. This method traverses the
     * SemanticVocabulary and retrieves the displayName of all Semantic Classes and
     * Semantic Properties, then adds each of the names to the corresponding set.
     *
     * Crea un nuevo diccionario para el lenguaje de usuario proporcionado. Este
     * metodo recorre el vocabulario semantico y recupera los displayNames de
     * las clases y propiedades semanticas, despues agrega los nombres al conjunto
     * correspondiente.
     *
     * @param lang language for the new SWBLexicon. Idioma del nuevo diccionario.
     * @param prexs comma-separated prefixes to filter SemanticClasses. Prefijos
     * para el filtrado de clases semánticas separados por comas.
     */
    public SWBLexicon(String lang, String prexs) {
        language = lang;
        spellDictPath = SWBPlatform.getWorkPath() + "/index/spell_" +
                language + ".txt";

        preflist = new ArrayList<String>(Arrays.asList(prexs.split(",")));

        //Create word hashes
        objHash = new HashMap<String, WordTag>();
        propHash = new HashMap<String, WordTag>();

        //Initialize lang codes map (for snowball algorithm)
        langCodes = new HashMap<String, String>();
        langCodes.put("es", "Spanish");
        langCodes.put("en", "English");
        langCodes.put("de", "Dutch");
        langCodes.put("pt", "Portuguese");
        langCodes.put("ru", "Russian");

        outputFile = new File(spellDictPath);
        try {
            outr = new BufferedWriter(new FileWriter(outputFile));

            //Traverse the ontology model to fill the dictionary
            Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
            while (its.hasNext()) {
                SemanticClass sc = its.next();
                if (preflist.contains(sc.getPrefix())) {
                    addWord(sc);

                    //Add class prefix to the prefixes string (for SparQl queries)
                    if (!prefixes.contains(sc.getPrefix())) {
                        prefixes.add(sc.getPrefix());
                    }

                    //Add namespace class to namespaces string (for SparQl queries)
                    if (!namespaces.contains(sc.getOntClass().getNameSpace())) {
                        namespaces.add(sc.getOntClass().getNameSpace());
                    }

                    Iterator<SemanticProperty> ip = sc.listProperties();
                    while (ip.hasNext()) {
                        SemanticProperty sp = ip.next();
                            addWord(sp);

                            //Add property prefix to prefixes string (for SparQl queries)
                            if (!prefixes.contains(sp.getPrefix())) {
                                prefixes.add(sp.getPrefix());
                            }

                            //Add property namespace to namespaces string (for SparQl queries)
                            if (!namespaces.contains(sp.getRDFProperty().getNameSpace())) {
                                namespaces.add(sp.getRDFProperty().getNameSpace());
                            }
                    }
                }
            }

            //Build prefixes string for SparQL queries
            prefixString = prefixString + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
            
            for (int i = 0; i < prefixes.size(); i++) {
                prefixString = prefixString + "PREFIX " + prefixes.get(i) +
                        ": " + "<" + namespaces.get(i) + ">\n";
            }
        } catch (IOException ex) {
            log.error(ex);
        }

        //Close writer
        if (outr != null) {
            try {
                outr.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    /**
     * Adds an entry to the lexicon. Creates a WordTag with the information
     * retrieved from a SemanticClass. The lexical form of the word
     * entry is the displayName of the associated SemanticClass.
     *
     * Agrega una entrada al diccionario. Crea un WordTag con la informacion de
     * una clase semantica. La forma lexica de la palabra agregada es el
     * displayName de la clase semantica asociada.
     * @param o SemanticClass to extract information from. Clase semantica para
     * la cual se creara una entrada.
     */
    public void addWord(SemanticClass o) {
        String oName = o.getDisplayName(language);
        WordTag wt = objHash.get(getSnowballForm(oName));

        if (wt == null) {
            if (outr != null) {
                try {
                    outr.write(oName + "\n");
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
            objHash.put(getSnowballForm(oName), new WordTag("OBJ", oName, o.getPrefix() + ":" + o.getName(), o.getClassName(), o.getClassId(), ""));
        }
    }

    /**
     * Adds an entry to the lexicon. Creates a WordTag with the information
     * retrieved from a SemanticProperty. The lexical form of the word entry is
     * the displayName of the associated SemanticProperty.
     *
     * Agrega una entrada al diccionario. Crea un WordTag con la informacion de
     * una propiedad semantica. La forma lexica de la palabra agregada es el
     * displayName de la propiedad semantica asociada.
     * @param p SemanticProperty to extract information from. Propiedad semantica
     * para la cual se creara una entrada.
     */
    public void addWord(SemanticProperty p) {
        String pName = p.getDisplayName(language);
        WordTag wt = propHash.get(getSnowballForm(pName));

        if (wt == null) {
            if (outr != null) {
                try {
                    outr.write(pName + "\n");
                } catch (IOException ex) {
                    log.error(ex);
                }
            }

            //If it is an object property
            if (p.isObjectProperty()) {
                //Attempt to get range class object
                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(p.getRangeClass().getURI());
                propHash.put(getSnowballForm(pName), new WordTag("PRO", pName, p.getPrefix() + ":" + p.getName(), p.getName(), p.getPropId(), rg.getClassId()));
            } else {
                propHash.put(getSnowballForm(pName), new WordTag("PRO", pName, p.getPrefix() + ":" + p.getName(), p.getName(), p.getPropId(), ""));
            }
        }
    }

    /**
     * Gets the language of the SWBLexicon.
     * Obtiene el idioma del diccionario.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Gets the tag for the specified lexical form (name of a class). It searches
     * only in the classes set.
     *
     * Obtiene una etiqueta compuesta para la forma lexica especificada (nombre
     * de una clase semantica). Busca solo en el conjunto de clases.
     * @param label name of the class to get tag for.
     * @return WordTag object with the tag and type for the given class name.
     */
    public WordTag getObjWordTag(String label) {
        WordTag wt = null;

        wt = objHash.get(getSnowballForm(label));
        if (wt != null) {
            return wt;
        }
        return new WordTag("VAR", "", "", "", "", "");
    }

    /**
     * Gets the prefixes string of the SWBLexicon (for a SparQl query).
     * Obtiene la lista de prefijos del diccionario (para una consulta SparQl).
     */
    public String getPrefixString() {
        return prefixString;
    }

    /**
     * Gets the tag for the specified lexical form (name of a property). It
     * searches only in the properties set.
     *
     * Obtiene una etiqueta compuesta para la forma lexica especificada (nombre de
     * una propiedad semantica). Busca solo en el conjunto de propiedades.
     * @param label name of the property to get tag for.
     * @return WordTag object with the tag and type for the given property name.
     */
    public WordTag getPropWordTag(String label) {
        WordTag wt = null;
        wt = propHash.get(getSnowballForm(label));

        if (wt != null) {
            return wt;
        }
        return new WordTag("VAR", "", "", "", "", "");
    }

    /**
     * Gets the snowball-ed form of an input text. Applies the snowball algorithm
     * to each word in the text to get its root or stem.
     *
     * Obtiene el lexema o raiz de una palabra mediante el algoritmo de snowball.
     * @param input Text to stem.
     * @return Root of the input.
     */
    public String getSnowballForm(String input) {
        String res = "";
        
        //Create snowball analyzer
        Analyzer SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language), stopWords);

        //Create token stream for prhase composition
        TokenStream ts = SnballAnalyzer.tokenStream("sna", new StringReader(input));

        //Build the result string with the analyzed tokens
        try {
            Token tk;
            while ((tk = ts.next()) != null) {
                res = res + tk.termText() + " ";
            }
            ts.close();
        } catch (Exception ex) {
            log.error(ex);
        }
        return res.trim();
    }

    /**
     * Gets the path to the text file containing the list of displayNames for
     * spell checking.
     */
    public String getSpellDictPath() {
        return spellDictPath;
    }

    /**
     * Gets the tag for the specified lexical form. It searches in both, classes
     * and properties sets in order to find a tag.
     *
     * Obtiene una etiqueta compuesta para la forma lexica especificada. Busca
     * tanto en el conjunto de clases como en el de propiedades para encontrar
     * la etiqueta.
     * @param label lexical form of the word to tag. Forma lexica a etiquetar.
     * @return WordTag object with the tag and type for the word label.
     */
    public WordTag getWordTag(String label) {
        WordTag wt = null;

        wt = objHash.get(getSnowballForm(label));
        if (wt != null) {
            return wt;
        }

        wt = propHash.get(getSnowballForm(label));
        if (wt != null) {
            return wt;
        }
        return new WordTag("VAR", "", "", "", "", "");
    }

    /**
     * Returns an iterator to the list of lexemes for the SemanticClases in the
     * Lexicon.
     */
    public Iterator<String> listClassNames() {
        List res = new ArrayList();

        Iterator it = objHash.values().iterator();
        while (it.hasNext()) {
            WordTag wt = (WordTag) it.next();
            res.add(wt.getDisplayName());
        }
        return res.iterator();
    }

    /**
     * Returns an iterator to the list of lexemes for the SemanticProperties in
     * the Lexicon.
     */
    public Iterator<String> listPropertyNames() {
        List res = new ArrayList();

        Iterator it = propHash.values().iterator();
        while (it.hasNext()) {
            WordTag wt = (WordTag) it.next();
            res.add(wt.getDisplayName());
        }
        return res.iterator();
    }
}