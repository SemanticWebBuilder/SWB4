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
import org.semanticwb.portal.indexer.parser.GenericParser;

/**
 *
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class SearchResults
{
    private TreeSet<SearchDocument> docs = null;
    private User m_user = null;

    private int pageLength=10;
    
    public SearchResults (User user)
    {
        this(user,null);
    }
    
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

    public void add(SearchDocument doc)
    {
        //System.out.println("add:"+doc.getURI());
        Searchable sdoc=doc.getSearchable();
        GenericParser parser=SWBPortal.getIndexMgr().getIndexer(sdoc.getSemanticObject().getModel().getName()).getParser(sdoc);
        if(parser.canUserView(sdoc, m_user))
        {
            docs.add(doc);
        }
    }

    public int size() {
        return docs.size();
    }

    public Iterator<SearchDocument> listDocuments () {
        return docs.iterator();
    }

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
