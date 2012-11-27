/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.integration.lucene.analyzer;

import java.io.Reader;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.integration.lucene.SWBLuceneIndexer;

// TODO: Auto-generated Javadoc
/** Filters {@link StandardTokenizer} with {@link StandardFilter}, {@link
 * LowerCaseFilter} and {@link StopFilter}. */
public class LocaleAnalyzer extends Analyzer
{
    
    /** The stop table. */
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

    /** The name. */
    private String name = "Spanish";

    /** Builds an analyzer. */
    public LocaleAnalyzer()
    {
        this(STOP_WORDS);
    }

    /**
     * Instantiates a new locale analyzer.
     * 
     * @param name the name
     */
    public LocaleAnalyzer(String name)
    {
        this(STOP_WORDS);
        this.name = name;
    }

    /**
     * Instantiates a new locale analyzer.
     * 
     * @param name the name
     * @param stopWords the stop words
     */
    public LocaleAnalyzer(String name, String[] stopWords)
    {
        this.name = name;
        //stopTable = StopFilter.makeStopSet(stopWords);
        stopTable = StopFilter.makeStopSet(SWBLuceneIndexer.LUCENE_VERSION, stopWords);
    }


    /**
     * Builds an analyzer with the given stop words.
     * 
     * @param stopWords the stop words
     */
    public LocaleAnalyzer(String[] stopWords)
    {
        //stopTable = StopFilter.makeStopSet(stopWords);
        stopTable = StopFilter.makeStopSet(SWBLuceneIndexer.LUCENE_VERSION, stopWords);
    }

    /**
     * Constructs a {@link StandardTokenizer} filtered by a {@link
     * StandardFilter}, a {@link LowerCaseFilter} and a {@link StopFilter}.
     * 
     * @param fieldName the field name
     * @param reader the reader
     * @return the token stream
     */
    @Override
    public final TokenStream tokenStream(String fieldName, Reader reader)
    {
        TokenStream result = null;
        if(!SWBIndexer.containsNoAnalyzedIndexTerm(fieldName))
        {
            result = new LocaleTokenizer(reader);
            //result = new StopFilter(result, stopTable);
            result = new StopFilter(SWBLuceneIndexer.LUCENE_VERSION, result, stopTable);
            result = new SnowballFilter(result, name);
        }else
        {
            //result = new StandardFilter(result);
            result = new StandardFilter(SWBLuceneIndexer.LUCENE_VERSION, result);
            //result = new LowerCaseFilter(result);
        }
        return result;
    }
}
