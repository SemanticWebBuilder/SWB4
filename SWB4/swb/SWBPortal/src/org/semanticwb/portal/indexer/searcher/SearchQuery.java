/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.searcher;

import java.util.ArrayList;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchQuery.
 * 
 * @author javier.solis
 */
public class SearchQuery
{
    
    /** The Constant OPER_OR. */
    public static final int OPER_OR=1;
    
    /** The Constant OPER_AND. */
    public static final int OPER_AND=2;
    
    /** The Constant OPER_NOT. */
    public static final int OPER_NOT=3;

    /** The terms. */
    private ArrayList<SearchTerm> terms=new ArrayList();
    
    /** The querys. */
    private ArrayList<SearchQuery> querys=new ArrayList();
    
    /** The operation. */
    private int operation=0;

    /**
     * Instantiates a new search query.
     */
    public SearchQuery()
    {
    }

    /**
     * Instantiates a new search query.
     * 
     * @param operation the operation
     */
    public SearchQuery(int operation)
    {
        this.operation=operation;
    }

    /**
     * Adds the term.
     * 
     * @param term the term
     */
    public void addTerm(SearchTerm term)
    {
        terms.add(term);
    }

    /**
     * List search terms.
     * 
     * @return the iterator
     */
    public Iterator<SearchTerm> listSearchTerms()
    {
        return terms.iterator();
    }

    /**
     * Adds the query.
     * 
     * @param query the query
     */
    public void addQuery(SearchQuery query)
    {
        querys.add(query);
    }

    /**
     * List queries.
     * 
     * @return the iterator
     */
    public Iterator<SearchQuery> listQueries()
    {
        return querys.iterator();
    }

    /**
     * Gets the operation.
     * 
     * @return the operation
     */
    public int getOperation()
    {
        return operation;
    }

}
