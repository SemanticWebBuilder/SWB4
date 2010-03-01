/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.searcher;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchTerm.
 * 
 * @author javier.solis
 */
public class SearchTerm
{
    
    /** The Constant OPER_OR. */
    public static final int OPER_OR=1;
    
    /** The Constant OPER_AND. */
    public static final int OPER_AND=2;
    
    /** The Constant OPER_NOT. */
    public static final int OPER_NOT=3;

    /** The field. */
    private String field=null;
    
    /** The text. */
    private String text=null;
    
    /** The operation. */
    private int operation = 1;

    /**
     * Instantiates a new search term.
     * 
     * @param field the field
     * @param text the text
     * @param oper the oper
     */
    public SearchTerm(String field, String text, int oper)
    {
        this.field=field;
        this.text=text;
        this.operation=oper;
    }

    /**
     * Gets the field.
     * 
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the field.
     * 
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Gets the text.
     * 
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     * 
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the operation.
     * 
     * @return the operation
     */
    public int getOperation() {
        return operation;
    }

    /**
     * Sets the operation.
     * 
     * @param operation the operation to set
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }



}
