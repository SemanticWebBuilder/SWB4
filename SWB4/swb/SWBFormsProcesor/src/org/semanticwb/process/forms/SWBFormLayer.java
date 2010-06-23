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
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name);

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id);

    /**
     * Sets the label.
     *
     * @param label the new label
     */
    public void setLabel(String label);

    /**
     * insert extra attributes to one form element*.
     *
     * @param moreattr the new more attr
     */
    public void setMoreAttr(String moreattr);


    /**
     * Sets the style.
     *
     * @param style the new style
     */
    public void setStyle(String style);

    /**
     * Sets the style class.
     *
     * @param styleclass the new style class
     */
    public void setStyleClass(String styleclass);

    /**
     * Set attributes to class according with the xml tag element.
     */
    void setAttributes();


    //gets
    /**
     * regresa el nombre del elemento de la forma
     * Returs the form element name.
     *
     * @return The form element name
     */
    public String getName();

    /**
     * Gets the label.
     *
     * @return the label
     */
    public String getLabel();

    /**
     * obtine atributos insertados adicionalmente a un elemento de forma *.
     *
     * @return the more attr
     */
    public String getMoreAttr();

    /**
     * Gets the style.
     *
     * @return the style
     */
    public String getStyle();

    /**
     * Gets the style class.
     *
     * @return the style class
     */
    public String getStyleClass();

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId();


    /**
     * regresa el html del elemento de la forma
     * Returs the html of the form element.
     *
     * @return the html
     */
    public String getHtml();

}
