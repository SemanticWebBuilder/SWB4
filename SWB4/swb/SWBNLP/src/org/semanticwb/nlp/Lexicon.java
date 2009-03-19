/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Word lexicon. A list of {@link Word}s and their {@link WordTag}s.
 * @author hasdai
 */
public class Lexicon {
    //TODO: Poner el léxico en un árbol
    private ArrayList<Word> lexic;

    /**
     * Creates a new instance of a lexicon with the given list of {@link Word}s.
     * @param lex {@link Word} List.
     */
    public Lexicon(ArrayList<Word> lex) {
        lexic = lex;
    }

    /**
     * Creates a new instance of a lexicon. The created lexicon is empty.
     */
    public Lexicon() {
        lexic = new ArrayList<Word>();
    }

    /**
     * Adds a {@link Word} to the lexicon (if the word doesn exist).
     * @param w {@link Word} to add to the lexicon.
     */
    public void addWord(Word w) {
        if (!entryExist(w)) {
            lexic.add(w);
        }
    }

    /**
     * Gets the {@link WordTag} of the given {@link Word}.
     * @param w {@link Word} to tag.
     * @return {@link WordTag} for the word.
     */
    public WordTag getWordTag(Word w) {
        //TODO: Arreglar para hacer la búsqueda en un árbol
        for (int i = 0; i < lexic.size(); i++) {
            if (lexic.get(i).getLabel().toUpperCase().compareTo(w.getLabel().toUpperCase()) == 0) {
                return new WordTag(lexic.get(i).getTag().getLabel(),
                        lexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    /**
     * Gets the {@link WordTag} of the given {@link Word} label.
     * @param label of Word to tag.
     * @return {@link WordTag} for the word.
     */
    public WordTag getWordTag(String label) {
        for (int i = 0; i < lexic.size(); i++) {
            if (lexic.get(i).getLabel().toUpperCase().compareTo(label.toUpperCase()) == 0) {
                return new WordTag(lexic.get(i).getTag().getLabel(),
                        lexic.get(i).getTag().getType());
            }
        }
        return new WordTag("VAR", "");
    }

    /**
     * Verifies if the given {@link Word} already exists in the dictionary.
     * @param entry {@link Word} to search for.
     * @return true if the word is already in Dictionary, false otherwise.
     */
    public boolean entryExist(Word entry)
    {
        //TODO: Arreglar para hacer la búsqueda en un árbol
        for (int i = 0; i < lexic.size(); i++)
        {
            if (lexic.get(i).getLabel().toUpperCase().compareTo(entry.getLabel().toUpperCase()) == 0
                    && lexic.get(i).getTag().getLabel().compareTo(entry.getTag().getLabel()) == 0
                    && lexic.get(i).getTag().getType().compareTo(entry.getTag().getType())==0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets an iterator to the {@link Word}s in the Dictionary.
     */
    public Iterator<Word> listEntries()
    {
        return lexic.iterator();
    }
}