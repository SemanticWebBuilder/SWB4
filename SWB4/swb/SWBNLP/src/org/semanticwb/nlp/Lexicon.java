/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.nlp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 * @author hasdai
 * A lexicon is a list of tagged words. In this case, word labels are the
 * display names (in the given language) for Semantic Classes and Semantic
 * Properties. For the analysis purpose, Class names and Propery names are
 * stored in separated sets.
 */
public class Lexicon {
    //TODO: Poner el léxico en un árbol

    private ArrayList<Word> pLexic;
    private ArrayList<Word> oLexic;
    private String language = "es";
    private List prefixes = new ArrayList();
    private List namespaces = new ArrayList();
    private String prefixString = "";

    /**
     * Creates a new Lexicon given the user's language. This method traverses the
     * SemanticVocabulary and retrieves the displayName of all Semantic Classes and
     * Semantic Properties, then adds each of the names to the corresponding list.
     * @param lang language for the new Lexicon.
     */
    public Lexicon(String lang) {
        pLexic = new ArrayList<Word>();
        oLexic = new ArrayList<Word>();
        language = lang;
        getVocabularyPrefixes();

        //Create a new word dictionary instance
        Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();

        //Traverse the ontology model to fill the dictionary
        while (its.hasNext()) {
            SemanticClass sc = its.next();

            this.addWord(new Word(sc.getDisplayName(lang),
                    new WordTag("OBJ", sc.getPrefix() + ":" + sc.getName(), sc.getName())));

            Iterator<SemanticProperty> ip = sc.listProperties();

            while (ip.hasNext()) {
                SemanticProperty prop = ip.next();

                this.addWord(new Word(prop.getDisplayName(lang),
                        new WordTag("PRO", prop.getPropId(), prop.getName())));
            }
        }
    }

    /**
     * Creates a new Lexicon with the given list of tagged words and its
     * language.
     * @param lex list of tagged words.
     * @param lang language for the new Lexicon.
     */
    public Lexicon(ArrayList<Word> lex, String lang) {
        Iterator<Word> wit = lex.iterator();
        pLexic = new ArrayList<Word>();
        oLexic = new ArrayList<Word>();
        language = lang;

        getVocabularyPrefixes();
        while (wit.hasNext()) {
            Word t = wit.next();

            if (t.getTag().getTag().equals("PRO")) {
                pLexic.add(t);
            } else {
                oLexic.add(t);
            }
        }
    }

    /**
     * Gets the language of the Lexicon.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Gets the prefixes from the SemanticVocabulary of the lexicon, adding
     * rdf and rdfs default prefixes.
     */
    private void getVocabularyPrefixes() {
        Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
        while (its.hasNext()) {
            SemanticClass sc = its.next();

            if (!prefixes.contains(sc.getPrefix())) {
                prefixes.add(sc.getPrefix());
            }
            if (!namespaces.contains(sc.getOntClass().getNameSpace())) {
                namespaces.add(sc.getOntClass().getNameSpace());
            }

            Iterator<org.semanticwb.platform.SemanticProperty> ip = sc.listProperties();
            while (ip.hasNext()) {
                SemanticProperty prop = ip.next();

                if (!prefixes.contains(prop.getPrefix())) {
                    prefixes.add(prop.getPrefix());
                }
                if (!namespaces.contains(prop.getRDFProperty().getNameSpace())) {
                    namespaces.add(prop.getRDFProperty().getNameSpace());
                }
            }
        }

        prefixString = prefixString + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
        for (int i = 0; i < prefixes.size(); i++) {
            prefixString = prefixString + "PREFIX " + prefixes.get(i) + ": " + "<" + namespaces.get(i) + ">\n";
        }
    }

    /**
     * Gets the prefixes string of the Lexicon.
     */
    public String getPrefixString() {
        return prefixString;
    }

    /**
     * Adds a word to the léxicon if it doesn't already exist.
     * @param w word to add.
     */
    public void addWord(Word w) {
        if (!entryExist(w)) {
            if (w.getTag().getTag().equals("PRO")) {
                pLexic.add(w);
            } else {
                oLexic.add(w);
            }
        }
    }

    /**
     * Gets the tag for the specified word. It searches in both, classes and
     * properties list in order to find a tag.
     * @param w word to get tag for.
     * @return WordTag object with the tag and type for the word.
     */
    public WordTag getWordTag(Word w) {
        //TODO: Arreglar para hacer la búsqueda en un árbol

        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }

        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Gets the tag for the specified word label. It searches in both, classes and
     * properties list in order to find a tag.
     * @param w label of word to get tag for.
     * @return WordTag object with the tag and type for the word label.
     */
    public WordTag getWordTag(String label) {
        boolean found = false;

        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }

        for (int i = 0; i < oLexic.size() && !found; i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Gets the tag for the specified word (word for a property). It searches
     * only in the properties list.
     * @param w word to get tag for.
     * @return WordTag object with the tag and type for the word.
     */
    public WordTag getPropWordTag(Word w) {
        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Gets the tag for the specified label (name of a property). It searches
     * only in the properties list.
     * @param label name of the property to get tag for.
     * @return WordTag object with the tag and type for the given property name.
     */
    public WordTag getPropWordTag(String label) {
        for (int i = 0; i < pLexic.size(); i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(pLexic.get(i).getTag().getTag(),
                        pLexic.get(i).getTag().getType(), pLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Gets the tag for the specified word (word for a class). It searches
     * only in the classes list.
     * @param w word to get tag for.
     * @return WordTag object with the tag and type for the word.
     */
    public WordTag getObjWordTag(Word w) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Gets the tag for the specified label (name of a class). It searches
     * only in the classes list.
     * @param label name of the class to get tag for.
     * @return WordTag object with the tag and type for the given class name.
     */
    public WordTag getObjWordTag(String label) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(oLexic.get(i).getTag().getTag(),
                        oLexic.get(i).getTag().getType(), oLexic.get(i).getTag().getWClassName());
            }
        }
        return new WordTag("VAR", "", "");
    }

    /**
     * Verifies if a word already exists in the Lexicon.
     * @param entry word to search for.
     * @return true if word is in the Lexicon, false otherwise.
     */
    public boolean entryExist(Word entry) {
        boolean found = false;
        //TODO: Arreglar para hacer la búsqueda en un árbol
        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0 && pLexic.get(i).getTag().getTag().compareTo(entry.getTag().getTag()) == 0 && pLexic.get(i).getTag().getType().compareTo(entry.getTag().getType()) == 0 && pLexic.get(i).getTag().getWClassName().compareTo(entry.getTag().getWClassName()) == 0) {
                found = true;
            }
        }

        for (int i = 0; i < oLexic.size() && !found; i++) {
            if (oLexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0 && oLexic.get(i).getTag().getTag().compareTo(entry.getTag().getTag()) == 0 && oLexic.get(i).getTag().getType().compareTo(entry.getTag().getType()) == 0) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Gets an iterator to the list of words for the classes in the
     * SemanticVocabulary.
     * @return Iterator to Class words.
     */
    public Iterator<Word> listObjEntries() {
        return oLexic.iterator();
    }

    /**
     * Gets an iterator to the list of words for the properties in the
     * SemanticVocabulary.
     * @return Iterator to Property words.
     */
    public Iterator<Word> listPropEntries() {
        return pLexic.iterator();
    }

    @Override
    public String toString() {
        String res = "";

        Iterator<Word> wit = pLexic.iterator();
        while (wit.hasNext()) {
            Word t = wit.next();

            res = res + t.getLabel() + "[" + t.getTag().getTag() + ", " +
                    t.getTag().getType() + "]\n";
        }

        wit = oLexic.iterator();
        while (wit.hasNext()) {
            Word t = wit.next();

            res = res + t.getLabel() + "[" + t.getTag().getTag() + ", " +
                    t.getTag().getType() + "]\n";
        }
        return res;
    }
}