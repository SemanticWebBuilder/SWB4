/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.searcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.indexer.parser.GenericParser;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchResults.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SearchResults
{
    
    /** The docs. */
    private TreeSet<SearchDocument> docs = null;
    
    /** The m_user. */
    private User m_user = null;

    /** The page length. */
    private int pageLength=10;
    
    /**
     * Instantiates a new search results.
     * 
     * @param user the user
     */
    public SearchResults (User user)
    {
        this(user,null);
    }
    
    /**
     * Instantiates a new search results.
     * 
     * @param user the user
     * @param comparator the comparator
     */
    public SearchResults (User user, Comparator comparator)
    {
        m_user=user;
        if(comparator!=null)
        {
            docs=new TreeSet(comparator);
        }else
        {
            docs=new TreeSet();
        }
    }

    /**
     * Adds the.
     * 
     * @param doc the doc
     */
    public void add(SearchDocument doc)
    {
        //System.out.println("add:"+doc.getURI());
        Searchable sdoc=doc.getSearchable();
        if(sdoc!=null)
        {
            //TODO:Traer idexador del modelo
            GenericParser parser=SWBPortal.getIndexMgr().getDefaultIndexer().getParser(sdoc);
            if(parser.canUserView(sdoc, m_user))
            {
                docs.add(doc);
            }
        }
    }

    /**
     * Size.
     * 
     * @return the int
     */
    public int size() {
        return docs.size();
    }

    /**
     * List documents.
     * 
     * @return the iterator
     */
    public Iterator<SearchDocument> listDocuments () {
        return docs.iterator();
    }

    /**
     * List documents.
     * 
     * @param page the page
     * @return the iterator
     */
    public Iterator<SearchDocument> listDocuments (int page)
    {
        ArrayList arr=new ArrayList();
        int size=docs.size();
        int index=page*pageLength;
        if(index<size)
        {
            int x=0;
            Iterator<SearchDocument> it=docs.iterator();
            while (it.hasNext())
            {
                SearchDocument searchDocument = it.next();
                if(x>=index+pageLength)
                {
                    break;
                }else if(x>=index)
                {
                    arr.add(searchDocument);
                }
                x++;
            }
        }
        return arr.iterator();
    }


    /**
     * Getter for property pageLength.
     * @return Value of property pageLength.
     */
    public int getPageLength()
    {
        return pageLength;
    }

    /**
     * Setter for property pageLength.
     * @param pageLength New value of property pageLength.
     */
    public void setPageLength(int pageLength)
    {
        this.pageLength = pageLength;
    }

}
