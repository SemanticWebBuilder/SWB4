/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.spell;

import java.io.File;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Wrapper for Lucene @link{SpellChecker} class.
 * Envoltorio para la clase @link{SpellChecker} de Lucene.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SWBSpellChecker {
    /**Lucene SpellChecker object*/
    private SpellChecker checker = null;
    /**Lucene Dictionary to store words for spelling suggestions*/
    private LuceneDictionary spellDict = null;
    /**Directory to store spell indexes*/
    private Directory spellDir = null;
    /**Number of max suggestions to retrieve*/
    private int numSug = 5;

    private static Logger log = SWBUtils.getLogger(SWBSpellChecker.class);

    /**
     * Creates a new instance of a SWBSpellChecker. Creates a new SpellChecker
     * using a Lucene Dictionary built from the "fieldName" field of the Directory
     * in "directoryPath".
     * @param directoryPath path of the Directory to extract Dictionary words from.
     * @param fieldName
     */
    public SWBSpellChecker(String directoryPath, String fieldName) {
        indexSpellDir(directoryPath, fieldName);
    }

    public SWBSpellChecker(String txtDictFilePath) {
        File f = new File(txtDictFilePath);

        if (f.exists()) {
            indexSpellTextFile(f);
        }
    }

    /**
     * Creates and indexes a new Directory for spell checking.
     * Call this method when you want to
     * @param dir Name of the original index directory.
     * @param fieldName Name of the field which new spell dictionary and index
     * directory will have.
     */
    public void indexSpellDir(String dirPath, String fieldName) {
        try {
            spellDir = new RAMDirectory();
            checker = new SpellChecker(spellDir);
            spellDict = new LuceneDictionary(IndexReader.open(dirPath), fieldName);
            checker.indexDictionary(spellDict);
        } catch (Exception ex) {
           log.error(ex);
        }
    }

    private void indexSpellTextFile(File txtDictFile) {
         try {
            spellDir = new RAMDirectory();
            checker = new SpellChecker(spellDir);
            checker.indexDictionary(new PlainTextDictionary(txtDictFile));
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public int getNumSug() {
        return numSug;
    }

    public void setNumSug(int numSug) {
        this.numSug = numSug;
    }    

    /**
     * Gets a set of words similar to the given input.
     * @param word Wort to have spell check done on.
     * @return Set of words similar to 'word'.
     * @throws java.io.IOException
     */
    public String[] suggestSimilar(String word) {
        if (checker != null) {
            try {
                if (checker.exist(word)) {
                    return null;
                }
                String res[] = checker.suggestSimilar(word, numSug);
                return res;
            } catch (Exception ex) {
                log.error(ex);
            }
        }
        return null;
    }
}