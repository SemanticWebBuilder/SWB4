/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.indexer.searcher;

/**
 *
 * @author javier.solis
 */
public class SearchTerm
{
    public static final int OPER_OR=1;
    public static final int OPER_AND=2;
    public static final int OPER_NOT=3;

    private String field=null;
    private String text=null;
    private int operation = 1;

    public SearchTerm(String field, String text, int oper)
    {
        this.field=field;
        this.text=text;
        this.operation=oper;
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
     * @return the operation
     */
    public int getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }



}
