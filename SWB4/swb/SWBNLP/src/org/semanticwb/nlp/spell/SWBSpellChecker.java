/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.spell;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
/**
 *
 * @author haxdai
 */
public class SWBSpellChecker {
    private SpellChecker checker;
    private LuceneDictionary spellDict;
    private Directory spellDir;
    private int numSug = 5;

    private static Logger log=SWBUtils.getLogger(SWBSpellChecker.class);    

    public SWBSpellChecker(String directoryPath, String fieldName) {
        indexSpellDir(directoryPath, fieldName);
    }

    public SWBSpellChecker(File txtDictFile) {
        indexSpellTextFile(txtDictFile);
    }

    public int getNumSug() {
        return numSug;
    }

    public void setNumSug(int numSug) {
        this.numSug = numSug;
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

            /*System.out.println("-----Words in lexicon----");
            Iterator wit = spellDict.getWordsIterator();
            while (wit.hasNext()) {
                String word = (String)wit.next();
                System.out.println(word);
            }*/
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

    /**
     * Gets a set of words similar to the given input.
     * @param word Wort to have spell check done on.
     * @return Set of words similar to 'word'.
     * @throws java.io.IOException
     */
    public String[] getSuggestions(String word) {
        try {
            if (checker.exist(word)) {
                return null;
            }
            String res[] = checker.suggestSimilar(word, numSug);
            return res;
        /*for (int i = 0; i < res.length; i++) {
        String string = res[i];
        System.out.println(string);*/
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;

    }    
    //DESCOMPONER LA CADENA EN PALABRAS, BUSCAR SUGERENCIAS PARA LAS PALABRAS SEPARADAS
    //RECONSTRUIR CADENA CON PALABRAS SUGERIDAS
}
