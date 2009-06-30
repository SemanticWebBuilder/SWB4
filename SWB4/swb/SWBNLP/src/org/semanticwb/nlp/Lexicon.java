/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import org.apache.lucene.analysis.Analyzer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 * A lexicon is a list of tagged words. In this case, word labels are the
 * display names (in a specific language) for Semantic Classes and Semantic
 * Properties. For the analysis purpose, Class names and Propery names are
 * stored in separated Directories.
 *
 * Un Lexicon (diccionario) es una lista de palabras etiquetadas. En este caso,
 * la formas lexicas de las palabras son los displayNames (para un idioma
 * especifico) de las clases y propiedades semanticas. Para propositos del
 * analisis, los nombres de las clases y de las propiedades se almacenan en
 * directorios separados.
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class Lexicon {
    private String language = "es";
    private HashMap<String, WordTag> objHash = null;
    private HashMap<String, WordTag> propHash = null;
    private String spellDictPath;  

    private List prefixes = new ArrayList();
    private List namespaces = new ArrayList();
    private String prefixString = "";
    
    private HashMap<String, String> langCodes;
    private String [] stopWords = {"a", "ante", "bajo", "cabe", "con",
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
    File outputFile = null;
    Writer outr = null;

    /**
     * Creates a new Lexicon given the user's language. This method traverses the
     * SemanticVocabulary and retrieves the displayName of all Semantic Classes and
     * Semantic Properties, then adds each of the names to the corresponding Lucene
     * Directory.
     *
     * Crea un nuevo diccionario para el lenguaje de usuario proporcionado. Este
     * metodo recorre el vocabulario semantico y recupera los displayNames de
     * las clases y propiedades semanticas, despues agrega los nombres al directorio
     * de Lucene correspondiente.
     *
     * @param lang language for the new Lexicon. Idioma del nuevo diccionario.
     */
    public Lexicon(String lang) {        
        language = lang;
        spellDictPath = SWBPlatform.getWorkPath() + "/index/spell_" + language + ".txt";
        outputFile = new File(spellDictPath);
        
        try {
            outr = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException ex) {
            Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
        }
        objHash = new HashMap<String, WordTag>();
        propHash = new HashMap<String, WordTag>();

        //Initialize lang codes map
        langCodes = new HashMap<String, String>();
        langCodes.put("es", "Spanish");
        langCodes.put("en", "English");
        langCodes.put("de", "Dutch");
        langCodes.put("pt", "Portuguese");
        langCodes.put("ru", "Russian");        

        //Traverse the ontology model to fill the dictionary
        Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
        while (its.hasNext()) {
            SemanticClass sc = its.next();            
            addWord(sc);

            //Add class prefix to the prefixes string
            if (!prefixes.contains(sc.getPrefix())) {
                prefixes.add(sc.getPrefix());
            }

            //Add namespace class to namespaces string
            if (!namespaces.contains(sc.getOntClass().getNameSpace())) {
                namespaces.add(sc.getOntClass().getNameSpace());
            }

            Iterator<SemanticProperty> ip = sc.listProperties();
            while (ip.hasNext()) {
                SemanticProperty sp = ip.next();
                addWord(sp);

                //Add property prefix to prefixes string
                if (!prefixes.contains(sp.getPrefix())) {
                    prefixes.add(sp.getPrefix());
                }

                //Add property namespace to namespaces string
                if (!namespaces.contains(sp.getRDFProperty().getNameSpace())) {
                    namespaces.add(sp.getRDFProperty().getNameSpace());
                }
            }
        }

        //Build prefixes string for SparQL queries
        prefixString = prefixString + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
        for (int i = 0; i < prefixes.size(); i++) {
            prefixString = prefixString + "PREFIX " + prefixes.get(i) + ": " + "<" + namespaces.get(i) + ">\n";
        }

        if (outr != null) {
            try {
                outr.close();
            } catch (IOException ex) {
                Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Adds an entry to the lexicon. Creates a Lucene document with the
     * information retrieved from a SemanticClass. The lexical form of the word
     * entry is the displayName of the associated SemanticClass.
     *
     * Agrega una entrada al diccionario. Crea un documento de Lucene con la
     * informacion de una clase semantica. La forma lexica de la palabra
     * agregada es el displayName de la clase semantica asociada.
     * @param o SemanticClass to extract information from. Clase semantica para
     * la cual se creara una entrada.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public void addWord(SemanticClass o) {
        String oName = o.getDisplayName(language);
        WordTag wt = objHash.get(oName.toLowerCase());

        if (wt == null) {
            if (outr != null) {
                try {
                    outr.write("[" + o.getDisplayName(language) + "]\n");
                } catch (IOException ex) {
                    Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            objHash.put(oName.toLowerCase(), new WordTag("OBJ", getSnowballLexForm(oName), o.getPrefix() + ":" + o.getName(), o.getClassName(), o.getClassId(), ""));
        }
    }

    /**
     * Adds an entry to the lexicon. Creates a Lucene document with the
     * information retrieved from a SemanticProperty. The lexical form of the
     * word entry is the displayName of the associated SemanticProperty.
     *
     * Agrega una entrada al diccionario. Crea un documento de Lucene con la
     * informacion de una propiedad semantica. La forma lexica de la palabra
     * agregada es el displayName de la propiedad semantica asociada.
     * @param p SemanticProperty to extract information from. Propiedad semantica
     * para la cual se creara una entrada.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public void addWord(SemanticProperty p) {
        String pName = p.getDisplayName(language);
        WordTag wt = propHash.get(pName.toLowerCase());

        if (wt == null) {
            if (outr != null) {
                try {
                    outr.write(p.getDisplayName(language) + "\n");
                } catch (IOException ex) {
                    Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //If it is an object property
            if (p.isObjectProperty()) {
                //Attempt to get range class object
                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(p.getRangeClass().getURI());
                propHash.put(pName.toLowerCase(), new WordTag("PRO", getSnowballLexForm(pName), p.getPrefix() + ":" + p.getName(), p.getName(), p.getPropId(), rg.getClassId()));
            } else {
                propHash.put(pName.toLowerCase(), new WordTag("PRO", getSnowballLexForm(pName), p.getPrefix() + ":" + p.getName(), p.getName(), p.getPropId(), ""));
            }
        }
    }

    /**
     * Gets the language of the Lexicon.
     * Obtiene el idioma del diccionario.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Gets the prefixes string of the Lexicon (for a SparQl query).
     * Obtiene la lista de prefijos del diccionario (para una consulta SparQl).
     */
    public String getPrefixString() {
        return prefixString;
    }

    /**
     * Gets the tag for the specified lexical form. It searches in both, classes
     * and properties directory in order to find a tag.
     *
     * Obtiene una etiqueta compuesta para la forma lexica especificada. Busca
     * tanto en el directorio de clases como en el de propiedades para encontrar
     * la etiqueta.
     * @param label lexical form of word to get tag for. Forma lexica a etiquetar.
     * @param snowball Wheter to use snowball analyzer (better search flexibility).
     * @return WordTag object with the tag and type for the word label.
     */
    public WordTag getWordTag(String label, boolean snowball) {
        WordTag wt = null;
        //Document doc = null;

        if (snowball) {
            wt  = objHash.get(label.toLowerCase());
            if (wt != null && !getSnowballLexForm(label).equals(wt.getSnowballForm())) {
                wt = null;
            }
        } else {
            wt  = objHash.get(label);
        }

        if (wt != null) return wt;

        wt = null;
        if (snowball) {
            wt  = propHash.get(label.toLowerCase());
            if (wt != null && !getSnowballLexForm(label).equals(wt.getSnowballForm())) {
                wt = null;
            }
        } else {
            wt  = propHash.get(label);
        }

        if (wt != null) return wt;
        
        return null;//return new WordTag("", "VAR", "", "", "", "");
    }

    /**
     * Gets the tag for the specified lexical form (name of a property). It
     * searches only in the properties directory.
     *
     * Obtiene una etiqueta compuesta para la forma lexica especificada (nombre de
     * una propiedad semantica). Busca solo en el directorio de propiedades.
     * @param label name of the property to get tag for.
     * @param snowball Wheter to use snowball analyzer (better search flexibility).
     * @return WordTag object with the tag and type for the given property name.
     */
    public WordTag getPropWordTag(String label, boolean snowball) {
        WordTag wt = null;
        if (snowball) {
            wt = propHash.get(label.toLowerCase());
            if (wt != null && !getSnowballLexForm(label).equals(wt.getSnowballForm())) {
                wt = null;
            }
        } else {
            wt = propHash.get(label.toLowerCase());
        }

        if (wt != null) {
            return wt;
        }

        return null;//return new WordTag("", "VAR", "", "", "", "");
    }

    /**
     * Gets the tag for the specified lexical form (name of a class). It searches
     * only in the classes directory.
     *
     * Obtiene una etiqueta compuesta para la forma lexica especificada (nombre
     * de una clase semantica). Busca solo en el directorio de clases.
     * @param label name of the class to get tag for.
     * @param snowball Wheter to use snowball analyzer (better search flexibility).
     * @return WordTag object with the tag and type for the given class name.
     */
    public WordTag getObjWordTag(String label, boolean snowball) {
        WordTag wt = null;
        if (snowball) {
            wt = objHash.get(label.toLowerCase());
            if (wt != null && !getSnowballLexForm(label).equals(wt.getSnowballForm())) {
                wt = null;
            }
        } else {
            wt = objHash.get(label.toLowerCase());
        }

        if (wt != null) {
            return wt;
        }
        
        return null;//return new WordTag("", "VAR", "", "", "", "");
    }

    /**
     * Gets the snowball-ed form of an input text. Applies the snowball algorithm
     * to each word in the text to get its root or stem.
     * @param input Text to stem.
     * @return
     * @throws java.io.IOException
     */
    public String getSnowballLexForm(String input) {
        //Create snowball analyzer
        Analyzer SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language), stopWords);
        TokenStream ts = SnballAnalyzer.tokenStream("sna", new StringReader(input));
        String res = "";

        try {
            Token tk;
            while ((tk = ts.next()) != null) {
                res = res + tk.termText() + " ";
            }
            ts.close();
        } catch (Exception ex) {
            Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res.trim();
    }

    public String getSpellDictPath() {
        return spellDictPath;
    }
}