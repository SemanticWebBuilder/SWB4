/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.searcher;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author javier.solis
 */
public class SearchQuery
{
    public static final int OPER_OR=1;
    public static final int OPER_AND=2;
    public static final int OPER_NOT=3;

    private ArrayList<SearchTerm> terms=new ArrayList();
    private ArrayList<SearchQuery> querys=new ArrayList();
    private int operation=0;

    public SearchQuery()
    {
    }

    public SearchQuery(int operation)
    {
        this.operation=operation;
    }

    public void addTerm(SearchTerm term)
    {
        terms.add(term);
    }

    public Iterator<SearchTerm> listSearchTerms()
    {
        return terms.iterator();
    }

    public void addQuery(SearchQuery query)
    {
        querys.add(query);
    }

    public Iterator<SearchQuery> listQueries()
    {
        return querys.iterator();
    }

    public int getOperation()
    {
        return operation;
    }

}
