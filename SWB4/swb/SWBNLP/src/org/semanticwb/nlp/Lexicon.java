/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Hits;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
//NOTE: For JDK6 use following import instead
//import java.text.Normalizer
//import sun.text.Normalizer;

/**
 * @author hasdai
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
 */
public class Lexicon {
    String propDirPath;
    String objDirPath;
    Searcher searcher = null;
    IndexWriter writer = null;
    private Analyzer luceneAnalyzer = new StandardAnalyzer();
    private String language = "es";
    private List prefixes = new ArrayList();
    private List namespaces = new ArrayList();
    private String prefixString = "";
    private Analyzer SnballAnalyzer = null;
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

    public static final String FIELD_DPNAME = "displayName";
    public static final String FIELD_DPNAMELOWER = "displayNameLower";
    public static final String FIELD_TAG = "tag";
    public static final String FIELD_PREFIX = "prefix";
    public static final String FIELD_SNOWBALLNAME = "snowballName";
    public static final String FIELD_CLSNAME = "name";
    public static final String FIELD_CLSID = "id";
    public static final String FIELD_RGCLSNAME = "rangeName";

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
        System.out.println("MSG - Lexicon created");
        language = lang;      

        //Set directory path
        objDirPath = SWBPlatform.getWorkPath() + "/index/objDir_" + language;
        propDirPath = SWBPlatform.getWorkPath() + "/index/propDir_" + language;

        //Create object directory
        File file = new File(objDirPath);
        if (!file.exists()) {
            createIndex(objDirPath, luceneAnalyzer);
        }

        //Create property directory
        file = new File(propDirPath);
        if (!file.exists()) {
            createIndex(propDirPath, luceneAnalyzer);
        }

        //Initialize lang codes map
        langCodes = new HashMap<String, String>();
        langCodes.put("es", "Spanish");
        langCodes.put("en", "English");
        langCodes.put("de", "Dutch");
        langCodes.put("pt", "Portuguese");
        langCodes.put("ru", "Russian");

        //Create SnowballAnalizer to get word root
        SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language), stopWords);

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

        //Optimize directories after adding all entries
        optimizeIndex();
    }

    public void optimizeIndex() {
        try {
            IndexWriter wr = new IndexWriter(objDirPath, luceneAnalyzer);
            wr.optimize();
            wr.close();
            wr = new IndexWriter(propDirPath, luceneAnalyzer);
            wr.optimize();
            wr.close();
        } catch (Exception ex) {
            Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the words that are not going to be considered in snowball analysis.
     *
     * Establece el conjunto de palabras que el analizador snowball ignorara.
     * @param sw List of words to ignore (stop words). Lista de palabras a
     * ignorar (stop words).
     */
    public void setStopWords(String []sw) {
        stopWords = sw;

        //Create SnowballAnalizer to get word root
        SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language), stopWords);
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
        Document doc = search4Object(oName, FIELD_DPNAMELOWER, objDirPath);
        
        if (doc == null) {
            try {
                //Create in-memory index writer
                IndexWriter objWriter = new IndexWriter(objDirPath, luceneAnalyzer);

                //Create lucene document with SemanticClass info.
                doc = createDocument("OBJ", oName, o.getPrefix(), oName.toLowerCase(),
                        getSnowballLexForm(oName), o.getName(), o.getClassId(), "");

                //Write document to index
                objWriter.addDocument(doc);
                objWriter.close();
            } catch (Exception ex) {
                Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        Document doc = search4Object(pName, FIELD_DPNAMELOWER, propDirPath);
        
        if (doc == null) {
            try {
            //Create in-memory index writers
            IndexWriter propWriter = new IndexWriter(propDirPath, luceneAnalyzer, true);

            //If p is an ObjectProperty
            if (p.isObjectProperty()) {
                //Get name of property range class
                StringBuffer bf = new StringBuffer();
                bf.append(p.getRangeClass());

                //Attempt to get range class
                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                if (rg != null) {
                    //Create lucene document for the Semantic Property
                    doc = createDocument("PRO", pName, p.getPrefix(),
                            p.getName(), getSnowballLexForm(pName) ,p.getName().toLowerCase(), p.getPropId(), rg.getClassId());
                    propWriter.addDocument(doc);
                }
            } else {
                //Create lucene document for the Semantic Property
                doc = createDocument("PRO", pName, p.getPrefix(),
                        p.getName(), getSnowballLexForm(pName) ,p.getName().toLowerCase(), p.getPropId(), "");
                propWriter.addDocument(doc);
            }
            propWriter.close();
            } catch (Exception ex) {
                Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
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
    public WordTag getWordTag(String label, boolean snowball) throws CorruptIndexException, IOException {
        Document doc = null;

        if (snowball) {
            doc = search4Object(getSnowballLexForm(label), FIELD_SNOWBALLNAME, propDirPath);
        } else {
            doc = search4Object(label, FIELD_DPNAMELOWER, propDirPath);
        }

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }

        doc = null;
        if (snowball) {
            doc = search4Object(getSnowballLexForm(label), FIELD_SNOWBALLNAME, objDirPath);
        } else {
            doc = search4Object(label, FIELD_DPNAMELOWER, objDirPath);
        }

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }
        return new WordTag("VAR", "", "", "", "");
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
    public WordTag getPropWordTag(String label, boolean snowball) throws CorruptIndexException, IOException {
        Document doc = null;

        if (snowball) {
            doc = search4Object(getSnowballLexForm(label), FIELD_SNOWBALLNAME, propDirPath);
        } else {
            doc = search4Object(label, FIELD_DPNAMELOWER, propDirPath);
        }

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }
        return new WordTag("VAR", "", "", "", "");
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
    public WordTag getObjWordTag(String label, boolean snowball) throws CorruptIndexException, java.io.IOException {
        Document doc = null;

        if (snowball) {
            doc = search4Object(getSnowballLexForm(label), FIELD_SNOWBALLNAME, objDirPath);
        } else {
            doc = search4Object(label, FIELD_DPNAMELOWER, objDirPath);
        }

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Creates a new Lucene Document to index a lexicon entry into a directory.
     *
     * Crea un nuevo documento de Lucene para indexar en un directorio del
     * diccionario.
     * @param objTag
     * @param objDisplayName
     * @param objPrefix
     * @param objDisplayNameLower
     * @param snowball
     * @param objName
     * @param objId
     * @param rangeObjName
     * @return
     */
    private Document createDocument (String objTag, String objDisplayName, String objPrefix, String objDisplayNameLower, String snowball, String objName, String objId, String rangeObjName) {
        Document doc = new Document();
        doc.add(new Field(FIELD_TAG, objTag, Field.Store.YES, Field.Index.NO));
        doc.add(new Field(FIELD_PREFIX, objPrefix, Field.Store.YES, Field.Index.NO));
        doc.add(new Field(FIELD_DPNAME, objDisplayName, Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field(FIELD_DPNAMELOWER, objDisplayNameLower, Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field(FIELD_SNOWBALLNAME, snowball, Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field(FIELD_CLSNAME, objName, Field.Store.YES, Field.Index.NO));
        doc.add(new Field(FIELD_CLSID, objId, Field.Store.YES, Field.Index.NO));
        doc.add(new Field(FIELD_RGCLSNAME, rangeObjName, Field.Store.YES, Field.Index.NO));

        return doc;
    }

    /**
     * Search for a SemanticObject's displayName in the indexed vocabulary.
     * @param keyWord Lowercase name of the requested SemanticObject.
     * @param fieldName Name of the field to search in.
     * @param dir RAMDirectory for searching.
     * @return Lucene document for the found term.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public Document search4Object (String keyWord, String fieldName, String dir) {
        Document doc = null;

        try {
            File f = new File(dir);
            if (!f.exists()) return null;
            
            //Create a new index searcher
            searcher = new IndexSearcher(dir);

            //Create a term for a term query (search for keyWord in displayNameLower field)
            Term t = new Term(fieldName, keyWord.toLowerCase());

            //Build term query
            Query query = new TermQuery(t);

            //Get search results
            Hits hits = searcher.search(query);

            if (hits.length() == 0) {
                searcher.close();
                return null;
            }
            //Return first search result document
            doc = hits.doc(0);
        } catch (Exception ex) {
            Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (Exception ex) {
                    Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
                }
                searcher = null;
            }
        }
        return doc;
    }

    /**
     * Gets a list of displayNames similar to the provided label.
     * @param label Word to get suggestions to.
     * @param forClasses Wheter to search for SemanticClass or SemanticProperty names.
     * @return List of names similar to label.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public ArrayList<String> suggestDisplayName (String label, boolean forClasses) throws CorruptIndexException, IOException {
        //Create a new index searcher
        searcher = null;

        if (forClasses) {
            searcher = new IndexSearcher(objDirPath);
        } else {
            searcher = new IndexSearcher(propDirPath);
        }

        System.out.println("Obteniendo sugerencias para " + label.toLowerCase());

        //Create term for a fuzzy query (search for similar keyword in displayNameLower field)
        Term t = new Term("displayNameLower", label.toLowerCase());

        //Build fuzzy query with minimumSimilarity = 0.5f
        Query query = new FuzzyQuery(t);

        //Get search results
        Hits hits = searcher.search(query);

        if (hits.length() == 0) { searcher.close(); return null; }

        //Put search results into the return list
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            res.add(doc.get("displayName"));
        }
        //Close searcher and return search result
        searcher.close();
        return res;
    }

    public String getObjDirectory() {
        return objDirPath;
    }

    public String getPropDirectory() {
        return propDirPath;
    }

    /**
     * Gets the snowball-ed form of an input text. Applies the snowball algorithm
     * to each word in the text to get its root or stem.
     * @param input Text to stem.
     * @return
     * @throws java.io.IOException
     */
    public String getSnowballLexForm(String input) {
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

    /**
     * Removes all accented characters from a string. Uses sun.text.Normalizer
     * class. Nothe that in jdk6 Normalizer class has moved to java.text package.
     * @param input String to normalize.
     * @return String without accented characters.
     */
    /*public String removeAccents(String input) {
        //NOTE: For JDK6 use following line instead
        //java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        String res = Normalizer.normalize(input, Normalizer.DECOMP, 0);
        return res.replaceAll("[^\\p{ASCII}]", "");
    }*/

    public void createIndex(String path, Analyzer analyzer) {
        try {
            File file = new File(path);
            file.mkdirs();
            writer = new IndexWriter(path, analyzer, true);
        } catch (Exception ex) {
            Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ex) {
                    Logger.getLogger(Lexicon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            writer = null;
        }
    }
}