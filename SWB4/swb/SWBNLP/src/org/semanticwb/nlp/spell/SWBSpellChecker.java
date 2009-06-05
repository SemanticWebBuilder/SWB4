/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.spell;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
/**
 *
 * @author haxdai
 */
public class SWBSpellChecker {
    private SpellChecker checker;
    private LuceneDictionary spellDict;
    private RAMDirectory spellDir;
    private int numSug = 5;

    public SWBSpellChecker(String directoryPath) {
        //TODO: load words from file at directoryPath and stem them to a RAMDirectory
    }

    public SWBSpellChecker(Directory dir, String fieldName) throws IOException {
       indexSpellDir(dir, fieldName);
    }

    public SWBSpellChecker(Directory originalDir, Directory spellDirectory, String fieldName) {
        spellDir = null;
        try {
            //Create RAMDirectory from the specified directory
            spellDir = new RAMDirectory(spellDirectory);

            //Create spellcheck index directory and dictionary
            indexSpellDir(originalDir, fieldName);
        } catch (IOException ex) {
            Logger.getLogger(SWBSpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getNumSug() {
        return numSug;
    }

    public void setNumSug(int numSug) {
        this.numSug = numSug;
    }

    public void writeSpellDirectory(String destPath) {
        //TODO: Write spell directory to a file at destPath
    }


    /**
     * Creates and indexes a new Directory for spell checking.
     * Call this method when you want to
     * @param dir Name of the original index directory.
     * @param fieldName Name of the field which new spell dictionary and index
     * directory will have.
     */
    public void indexSpellDir(Directory dir, String fieldName) throws java.io.IOException {
        IndexReader reader = null;

        if (IndexReader.isLocked(dir)) return;

        try {
            //Open original directory
            reader = IndexReader.open(dir);
            spellDir = new RAMDirectory();

            //Create a lucene dictionary with terms taken from fieldName field of
            //the orignal index directory
            spellDict = new LuceneDictionary(reader, fieldName);

            //Create the spellChecker using spellDir as index directory for spell checking
            checker = new SpellChecker(spellDir);

            //Index the spellDictionary
            checker.indexDictionary(spellDict);
        } catch (CorruptIndexException ex) {
            Logger.getLogger(SWBSpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SWBSpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Gets a set of words similar to the given input.
     * @param word Wort to have spell check done on.
     * @return Set of words similar to 'word'.
     * @throws java.io.IOException
     */
    public String [] getSuggestions (String word) throws IOException {
        return checker.suggestSimilar(word, numSug);
    }
}
