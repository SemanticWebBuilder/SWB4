


/*
 * WBIndexObjList.java
 *
 * Created on 11 de julio de 2006, 02:55 PM
 */

package org.semanticwb.portal.indexer;

import java.util.AbstractList;
import java.util.ArrayList;
import org.semanticwb.model.User;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBIndexObjList extends AbstractList
{
    private ArrayList objs=new ArrayList(400);
    //protected int size=0;
    protected User user;
    protected int page;
    protected int pindex;
    private int hits=0;
    private int pageLength=10;
    
    /** Creates a new instance of WBIndexObjList */
    public SWBIndexObjList(User user, int page, int pindex)
    {
        this.user=user;
        this.page=page;
        this.pindex=pindex;
    }
    
    public Object get(int index)
    {
        return objs.get(index);
    }
    
    public int size()
    {
        return objs.size();
    }
    
    public void add(int index, Object element) 
    {
        SWBIndexObj obj=(SWBIndexObj)element;
        if(SWBIndexer.validate(obj, user))
        {
            objs.add(index,obj);
            //size++;
        }
    }    
    
    /**
     * Getter for property hits.
     * @return Value of property hits.
     */
    public int getHits()
    {
        return hits;
    }
    
    /**
     * Setter for property hits.
     * @param hits New value of property hits.
     */
    protected void setHits(int hits)
    {
        this.hits = hits;
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
