/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

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
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;


/**
 * @author hasdai
 * A lexicon is a list of tagged words. In this case, word labels are the
 * display names (in a specific language) for Semantic Classes and Semantic
 * Properties. For the analysis purpose, Class names and Propery names are
 * stored in separated Directories.
 */
public class Lexicon {
    private Analyzer luceneAnalyzer = new StandardAnalyzer();
    private RAMDirectory objDir = null;
    private RAMDirectory propDir = null;
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

    /**
     * Creates a new Lexicon given the user's language. This method traverses the
     * SemanticVocabulary and retrieves the displayName of all Semantic Classes and
     * Semantic Properties, then adds each of the names to the corresponding Lucene Directory.
     * @param lang language for the new Lexicon.
     */
    public Lexicon(String lang) throws CorruptIndexException, LockObtainFailedException, IOException {
        language = lang;

        //Create in-memory directories
        objDir = new RAMDirectory();
        propDir = new RAMDirectory();        

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

        //Build prefix string for SparQL queries
        prefixString = prefixString + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
        for (int i = 0; i < prefixes.size(); i++) {
            prefixString = prefixString + "PREFIX " + prefixes.get(i) + ": " + "<" + namespaces.get(i) + ">\n";
        }

        //Optimize directories
        IndexWriter wr = new IndexWriter(objDir, luceneAnalyzer);
        wr.optimize();
        wr.close();
        wr = new IndexWriter(propDir, luceneAnalyzer);
        wr.optimize();
        wr.close();
    }

    /**
     * Sets the words that are not going to be considered in snowball analysis.
     * @param sw List of words to ignore (stop words).
     */
    public void setStopWords(String []sw) {
        stopWords = sw;
        
        //Create SnowballAnalizer to get word root
        SnballAnalyzer = new SnowballAnalyzer(langCodes.get(language), stopWords);
    }

    /**
     * Adds a document with the information of a SemanticClass to the search index.
     * @param o SemanticClass to extract information from.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public void addWord(SemanticClass o) throws CorruptIndexException, IOException {
        if (search4Object(o.getDisplayName(language), "displayNameLower", objDir) == null) {
            //Create in-memory index writer
            IndexWriter objWriter = new IndexWriter(objDir, luceneAnalyzer);

            //Create lucene document with SemanticClass info.
            Document doc = createDocument("OBJ", o.getDisplayName(language),
                    o.getPrefix(), o.getDisplayName(language).toLowerCase(),
                    getSnowballLexForm(o.getDisplayName(language)) ,o.getName() , o.getClassId(), "");

            //Write document to index
            objWriter.addDocument(doc);
            objWriter.close();
        }
    }

    /**
     * Adds a document with the information of a SemanticProperty to the search index.
     * @param p SemanticProperty to extract information from.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public void addWord(SemanticProperty p) throws CorruptIndexException, IOException {
        if (search4Object(p.getDisplayName(language), "displayNameLower", propDir) == null) {
            //Create in-memory index writers
            IndexWriter propWriter = new IndexWriter(propDir, luceneAnalyzer, true);

            //If p is an ObjectProperty
            if (p.isObjectProperty()) {
                //Get name of property range class
                StringBuffer bf = new StringBuffer();
                bf.append(p.getRangeClass());

                //Attempt to get range class
                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                if (rg != null) {
                    //Create lucene document for the Semantic Property
                    Document doc = createDocument("PRO", p.getDisplayName(language), p.getPrefix(), 
                            p.getName(), getSnowballLexForm(p.getDisplayName(language)) ,p.getName().toLowerCase(), p.getPropId(), rg.getClassId());
                    propWriter.addDocument(doc);
                }
            } else {
                //Create lucene document for the Semantic Property
                Document doc = createDocument("PRO", p.getDisplayName(language), p.getPrefix(), 
                        p.getName(), getSnowballLexForm(p.getDisplayName(language)) ,p.getName().toLowerCase(), p.getPropId(), "");
                propWriter.addDocument(doc);
            }
            propWriter.close();
        }
    }

    /**
     * Gets the language of the Lexicon.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Gets the prefixes string of the Lexicon.
     */
    public String getPrefixString() {
        return prefixString;
    }

    /**
     * Gets the tag for the specified word label. It searches in both, classes and
     * properties directory in order to find a tag.
     * @param w label of word to get tag for.
     * @return WordTag object with the tag and type for the word label.
     */
    public WordTag getWordTag(String label) throws CorruptIndexException, IOException {
        Document doc = search4Object(label, "displayNameLower", propDir);

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }

        doc = search4Object(label, "displayNameLower", objDir);
        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Gets the tag for the specified label (name of a property). It searches
     * only in the properties directory.
     * @param label name of the property to get tag for.
     * @return WordTag object with the tag and type for the given property name.
     */
    public WordTag getPropWordTag(String label) throws CorruptIndexException, IOException {
        Document doc = search4Object(label, "displayNameLower", propDir);

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Gets the tag for the specified label (name of a class). It searches
     * only in the classes directory.
     * @param label name of the class to get tag for.
     * @return WordTag object with the tag and type for the given class name.
     */
    public WordTag getObjWordTag(String label) throws CorruptIndexException, java.io.IOException {

        Document doc = search4Object(label, "displayNameLower", objDir);

        if (doc != null) {
            return new WordTag(doc.get("tag"), doc.get("prefix") + ":" + doc.get("name"), doc.get("name"), doc.get("id"), doc.get("rangeName"));
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Creates a lucene document for indexing lexicon entries.
     * @param objTag The tag for the indexed SemanticObject (class or property)
     * @param objDisplayName Display name of the SemanticObject
     * @param objName RDF Name of the SemanticObject (for prefix extraction)
     * @param objId ID of the SemanticObject.
     * @param rangeObjName The name of the related SemanticClass (range Class)
     * if the current element is an ObjectProperty.
     * @param isObjectProp Wheter the document should include the range class.
     * @return
     */
    private Document createDocument (String objTag, String objDisplayName, String objPrefix, String objDisplayNameLower, String snowball, String objName, String objId, String rangeObjName) {
        Document doc = new Document();
        doc.add(new Field("tag", objTag, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("prefix", objPrefix, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("displayName", objDisplayName, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("displayNameLower", objDisplayNameLower, Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field("snowballName", snowball, Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field("name", objName, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("id", objId, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("rangeName", rangeObjName, Field.Store.YES, Field.Index.NO));        

        return doc;
    }

    /**
     * Search for a SemanticObject's displayName in the indexed vocabulary.
     * @param keyWord lowercase name of the requested SemanticObject.
     * @param dir RAMDirectory for searching.
     * @return Lucene document for the found term.
     * @throws org.apache.lucene.index.CorruptIndexException
     * @throws java.io.IOException
     */
    public Document search4Object (String keyWord, String fieldName, RAMDirectory dir) throws CorruptIndexException, IOException {
        if (dir.list().length == 0) return null;

        //Create a new index searcher
        IndexSearcher searcher = new IndexSearcher(dir);

        //Create a term for a term query (search for keyWord in displayNameLower field)
        Term t = new Term(fieldName, keyWord.toLowerCase());

        //Build term query
        Query query = new TermQuery(t);

        //Get search results
        Hits hits = searcher.search(query);

        if (hits.length() == 0) { searcher.close(); return null; }

        //Return first search result document
        Document doc = hits.doc(0);
        searcher.close();
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
        IndexSearcher searcher = null;

        if (forClasses) {
            searcher = new IndexSearcher(objDir);
        } else {
            searcher = new IndexSearcher(propDir);
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
        return res;
    }

    public RAMDirectory getObjDirectory() {
        return objDir;
    }

    public RAMDirectory getPropDirectory() {
        return propDir;
    }

    public String getSnowballLexForm(String word) throws IOException {
        TokenStream ts = SnballAnalyzer.tokenStream("sna", new StringReader(word));
        String res = "";

        Token tk;
        while ((tk = ts.next()) != null) {            
            res = res + tk.termText();
        }
        ts.close();
        System.out.println(res);
        return res;
    }
}