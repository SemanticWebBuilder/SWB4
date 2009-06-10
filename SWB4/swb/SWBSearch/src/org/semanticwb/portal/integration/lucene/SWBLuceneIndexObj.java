package org.semanticwb.portal.integration.lucene;

/*
 * WBIndexObj.java
 *
 * Created on 24 de mayo de 2006, 12:17 PM
 */

import org.apache.lucene.document.Document;
import org.semanticwb.portal.indexer.SWBIndexObj;
import org.semanticwb.portal.indexer.SWBIndexer;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBLuceneIndexObj extends SWBIndexObj
{
    Document doc=null;
    
    /** Creates a new instance of WBIndexObj */
    public SWBLuceneIndexObj(Document doc)
    {
        this.doc=doc;
        //System.out.println(this+" "+getCategory());
    }
    
    /**
     * Getter for property url.
     * @return Value of property url.
     */
    public java.lang.String getUrl()
    {
        return doc.get(SWBIndexer.ATT_URL);
    }
    
    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public java.lang.String getType()
    {
        return doc.get(SWBIndexer.ATT_TYPE);
    }
    
    /**
     * Getter for property tmid.
     * @return Value of property tmid.
     */
    public java.lang.String getTopicMapID()
    {
        return doc.get(SWBIndexer.ATT_TOPICMAP);
    }
    
    /**
     * Getter for property tpid.
     * @return Value of property tpid.
     */
    public java.lang.String getTopicID()
    {
        return doc.get(SWBIndexer.ATT_TOPIC);
    }

    /**
     * Getter for property lang.
     * @return Value of property lang.
     */
    public java.lang.String getLang()
    {
        return doc.get(SWBIndexer.ATT_LANG);
    }
    
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId()
    {
        return doc.get(SWBIndexer.ATT_ID);
    }
    
    /**
     * Getter for property title.
     * @return Value of property title.
     */
    public java.lang.String getTitle()
    {
        return doc.get(SWBIndexer.ATT_TITLE);
    }
    
    /**
     * Getter for property summary.
     * @return Value of property summary.
     */
    public java.lang.String getSummary()
    {
        if(super.getSummary()==null)
        {
            return doc.get(SWBIndexer.ATT_SUMMARY);
        }else
        {
           return super.getSummary(); 
        }
    }
    
    /**
     * Getter for property category.
     * @return Value of property category.
     */
    public java.lang.String getCategory()
    {
        return doc.get(SWBIndexer.ATT_CATEGORY);
    }

    /**
     * Getter for property data.
     * @return Value of property data.
     */
    public java.lang.String getData()
    {
        return doc.get(SWBIndexer.ATT_DATA);
    }
    
    /**
     * Getter for property data.
     * @return Value of property data.
     */
    public java.lang.String getResId()
    {
        return doc.get(SWBIndexer.ATT_RESID);
    }    
    
    public String toString()
    {
        return "id("+getId()+"), type("+getType()+")";
    }
    
}
