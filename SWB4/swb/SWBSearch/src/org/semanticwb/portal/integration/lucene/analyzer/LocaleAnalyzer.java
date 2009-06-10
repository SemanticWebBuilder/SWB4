package org.semanticwb.portal.integration.lucene.analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.snowball.*;

import java.io.Reader;
import java.util.Set;

/** Filters {@link StandardTokenizer} with {@link StandardFilter}, {@link
 * LowerCaseFilter} and {@link StopFilter}. */
public class LocaleAnalyzer extends Analyzer
{
    private Set stopTable;

    /** An array containing some common English words that are usually not
     useful for searching. */
    public static final String[] STOP_WORDS = {
        "a", "and", "are", "as", "at", "be", "but", "by",
        "for", "if", "in", "into", "is", "it",
        "no", "not", "of", "on", "or", "s", "such",
        "t", "that", "the", "their", "then", "there", "these",
        "they", "this", "to", "was", "will", "with",
        "y", "para", "por", "con", "de",
        "en", "un", "es", "el",
        "o", "que", "los", "lo", "este", "ese",
        "esos"
    };

    private String name = "Spanish";

    /** Builds an analyzer. */
    public LocaleAnalyzer()
    {
        this(STOP_WORDS);
    }

    public LocaleAnalyzer(String name)
    {
        this(STOP_WORDS);
        this.name = name;
    }

    public LocaleAnalyzer(String name, String[] stopWords)
    {
        this.name = name;
        stopTable = StopFilter.makeStopSet(stopWords);
    }


    /** Builds an analyzer with the given stop words. */
    public LocaleAnalyzer(String[] stopWords)
    {
        stopTable = StopFilter.makeStopSet(stopWords);
    }

    /** Constructs a {@link StandardTokenizer} filtered by a {@link
     StandardFilter}, a {@link LowerCaseFilter} and a {@link StopFilter}. */
    public final TokenStream tokenStream(String fieldName, Reader reader)
    {
        TokenStream result = new StandardTokenizer(reader);
        result = new StandardFilter(result);
        //result = new LowerCaseFilter(result);
        result = new LocaleTokenizer(reader);
        result = new StopFilter(result, stopTable);
        result = new SnowballFilter(result, name);
        return result;
    }
}
