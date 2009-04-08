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

    private ArrayList<SemanticProperty> pLexic;
    private ArrayList<SemanticClass> oLexic;
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
        pLexic = new ArrayList<SemanticProperty>();
        oLexic = new ArrayList<SemanticClass>();
        language = lang;
        getVocabularyPrefixes();

        //Create a new word dictionary instance
        Iterator<SemanticClass> its = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();

        //Traverse the ontology model to fill the dictionary
        while (its.hasNext()) {
            SemanticClass sc = its.next();
            addWord(sc);
            Iterator<SemanticProperty> ip = sc.listProperties();

            while (ip.hasNext()) {
                SemanticProperty sp = ip.next();
                addWord(sp);
            }
        }
    }

    public void addWord(SemanticClass o) {
        if (!entryExist(o, true)) {
            oLexic.add(o);
        }
    }

    public void addWord(SemanticProperty p) {
        if (!entryExist(p, false)) {
            pLexic.add(p);
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
     * Gets the tag for the specified word label. It searches in both, classes and
     * properties list in order to find a tag.
     * @param w label of word to get tag for.
     * @return WordTag object with the tag and type for the word label.
     */
    public WordTag getWordTag(String label) {
        boolean found = false;
        int index = 0;

        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getDisplayName(language).toUpperCase().compareTo(label.toUpperCase()) == 0) {
                found = true;
                index = i;
            }
        }

        if (found) {
            SemanticProperty sp = pLexic.get(index);
            String rgs = "";

            if (sp.isObjectProperty()) {
                StringBuffer bf = new StringBuffer();
                bf.append(sp.getRangeClass());

                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                if (rg != null) {
                    rgs = rg.getClassId();
                }
            }
            return new WordTag("PRO",
                    pLexic.get(index).getPrefix() + ":" + pLexic.get(index).getName(),
                    pLexic.get(index).getName(),
                    pLexic.get(index).getPropId(),
                    rgs);
        }

        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getDisplayName(language).toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag("OBJ",
                        oLexic.get(i).getPrefix() + ":" + oLexic.get(i).getName(),
                        oLexic.get(i).getName(),
                        oLexic.get(i).getClassId(), "");
            }
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Gets the tag for the specified label (name of a property). It searches
     * only in the properties list.
     * @param label name of the property to get tag for.
     * @return WordTag object with the tag and type for the given property name.
     */
    public WordTag getPropWordTag(String label) {
        boolean found = false;
        int index = 0;

        for (int i = 0; i < pLexic.size() && !found; i++) {
            if (pLexic.get(i).getDisplayName(language).toUpperCase().compareTo(label.toUpperCase()) == 0) {
                found = true;
                index = i;
            }
        }

        if (found) {
            SemanticProperty sp = pLexic.get(index);
            String rgs = "";

            if (sp.isObjectProperty()) {
                StringBuffer bf = new StringBuffer();
                bf.append(sp.getRangeClass());

                SemanticClass rg = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(bf.toString());
                if (rg != null) {
                    rgs = rg.getClassId();
                }
            }
            return new WordTag("PRO",
                    pLexic.get(index).getPrefix() + ":" + pLexic.get(index).getName(),
                    pLexic.get(index).getName(),
                    pLexic.get(index).getPropId(),
                    rgs);
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Gets the tag for the specified label (name of a class). It searches
     * only in the classes list.
     * @param label name of the class to get tag for.
     * @return WordTag object with the tag and type for the given class name.
     */
    public WordTag getObjWordTag(String label) {
        for (int i = 0; i < oLexic.size(); i++) {
            if (oLexic.get(i).getDisplayName(language).toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag("OBJ",
                        oLexic.get(i).getPrefix() + ":" + oLexic.get(i).getName(),
                        oLexic.get(i).getName(),
                        oLexic.get(i).getClassId(), "");
            }
        }
        return new WordTag("VAR", "", "", "", "");
    }

    /**
     * Verifies if a word already exists in the Lexicon.
     * @param entry word to search for.
     * @return true if word is in the Lexicon, false otherwise.
     */
    public boolean entryExist(Object entry, boolean isClass) {
        //TODO: Arreglar para hacer la búsqueda en un árbol
        boolean found = false;

        if (isClass) {
            SemanticClass s = (SemanticClass) entry;
            for (int i = 0; i < oLexic.size() && !found; i++) {
                if (oLexic.get(i).getClassId().compareTo(s.getClassId()) == 0) {
                    found = true;
                }
            }
        } else {
            SemanticProperty s = (SemanticProperty) entry;
            for (int i = 0; i < pLexic.size() && !found; i++) {
                if (pLexic.get(i).getPropId().compareTo(s.getPropId()) == 0) {
                    found = true;
                }
            }
        }
        return found;
    }

    /**
     * Gets an iterator to the list of words for the classes in the
     * SemanticVocabulary.
     * @return Iterator to Class words.
     */
    public Iterator<SemanticClass> listObjEntries() {
        return oLexic.iterator();
    }

    /**
     * Gets an iterator to the list of words for the properties in the
     * SemanticVocabulary.
     * @return Iterator to Property words.
     */
    public Iterator<SemanticProperty> listPropEntries() {
        return pLexic.iterator();
    }
}