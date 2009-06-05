/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.nlp.analysis;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenizer;

/**
 * N-gram analyzer class based on Lucene's NGramTokenizer. Extracted from
 * http://sujitpal.blogspot.com/2007/12/spelling-checker-with-lucene.html
 *
 * @author Sujit Pal
 */
public class NGramAnalyzer extends Analyzer {
    private int minGram;
    private int maxGram;

    public NGramAnalyzer (int minGram, int maxGram) {
        this.minGram = minGram;
        this.maxGram = maxGram;
    }

    public TokenStream tokenStream(String fieldName, Reader reader) {
        return new NGramTokenizer(reader, minGram, maxGram);
    }
}