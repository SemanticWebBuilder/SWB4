/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.integration.lucene.searcher;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SearchQuery {
    public static final Occur AND = Occur.SHOULD;
    public static final Occur OR = Occur.MUST;
    public static final Occur NOT = Occur.MUST_NOT;
    private CachingWrapperFilter filter = null;
    private Analyzer analyzer = null;
    private IndexSearcher searcher = null;
    private QueryParser qp = null;
    private BooleanQuery bq = null;

    public SearchQuery(IndexSearcher searcher, Analyzer analyzer) {
        this.analyzer = analyzer;
        this.searcher = searcher;
        bq = new BooleanQuery();
    }

    public void addSearchTerm(String field, String text, Occur operation) throws ParseException {
        qp = new QueryParser(field, analyzer);
        addSearchTerm(qp.parse(text), operation);
    }

    public void addSearchTerm(Query query, Occur operation) {
        bq.add(query, operation);
    }

    public void setFilter (String field, String text) throws ParseException {
        qp = new QueryParser(field, analyzer);
        filter = new CachingWrapperFilter(new QueryWrapperFilter(qp.parse(text)));
    }

    public void removeFilter () {
        filter = null;
    }

    public CachingWrapperFilter getFilter () {
        return filter;
    }
    
    public BooleanQuery getBooleqnQuery () {
        return bq;
    }

    public boolean equals(SearchQuery obj) {
        return (obj.getBooleqnQuery().equals(getBooleqnQuery()) && filter.equals(obj.getFilter()));
    }

    public SearchResults execute(String lang) throws IOException {
        if (filter != null) return new SearchResults(searcher.search(bq, filter), lang);
        return new SearchResults(searcher.search(bq), lang);
    }
}
