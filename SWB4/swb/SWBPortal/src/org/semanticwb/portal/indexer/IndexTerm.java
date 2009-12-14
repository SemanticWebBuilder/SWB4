/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer;

/**
 *
 * @author javier.solis
 */
public class IndexTerm
{
    private String field=null;
    private String text=null;
    private boolean stored=false;
    private int indexed=1;

    public static int INDEXED_NO=0;                     //no indexado
    public static int INDEXED_ANALYZED=1;               //tokenizado y analizado
    public static int INDEXED_TOKENIZED_NO_ANALYZED=2;  //tokenizado y no analizado
    public static int INDEXED_NO_ANALYZED=3;            //no tokenizado y no analizado

    public IndexTerm(String field, boolean stored, int indexed)
    {
        this(field, null, stored, indexed);
    }

    public IndexTerm(String field, String text, boolean stored, int indexed)
    {
        this.field=field;
        this.text=text;
        this.stored=stored;
        this.indexed=indexed;
        if(indexed==INDEXED_NO_ANALYZED)
        {
            SWBIndexer.addNoAnalyzedIndexTerm(this);
        }
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the indexed
     */
    public int getIndexed() {
        return indexed;
    }

    /**
     * @param indexed the indexed to set
     */
    public void setIndexed(int indexed)
    {
        this.indexed = indexed;
        if(indexed==INDEXED_NO_ANALYZED)
        {
            SWBIndexer.addNoAnalyzedIndexTerm(this);
        }else
        {
            SWBIndexer.removeNoAnalyzedIndexTerm(field);
        }
    }

    /**
     * @return the stored
     */
    public boolean getStored() {
        return stored;
    }

    /**
     * @param stored the stored to set
     */
    public void setStored(boolean stored) {
        this.stored = stored;
    }

}
