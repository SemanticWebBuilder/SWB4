/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

/**
 *
 * @author jorge.jimenez
 */
public interface SWBFormLayer {

    /**
     * insert extra attributes to one form element*.
     *
     * @param moreattr the new more attr
     */
    public void setMoreAttr(String moreattr);


    /**
     * Set attributes to class according with the xml tag element.
     */
    void setAttributes();


    //gets
    
    /**
     * obtine atributos insertados adicionalmente a un elemento de forma *.
     *
     * @return the more attr
     */
    public String getMoreAttr();

    /**
     * regresa el html del elemento de la forma
     * Returs the html of the form element.
     *
     * @return the html
     */
    public String getHtml();

    public String getTag();

}
