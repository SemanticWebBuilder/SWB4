/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.tree;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

/*
 * Clase de tipo Pojo que maneja cada uno de los elementos o nodos en el árbol de navegación
 */
public class Element {

    private static final long serialVersionUID = -9145887024839938518L;

    private String name;
    private String uri;
    private String zulPage;
    private String iconElement;
    private String categoryID;
    private String modelID;

    /*
     * Metodo constructoe de clase
     * @param name String del nombre del elemento
     * @param uri String del uri del elemento
     * @param iconElement String del iconElement del elemento
     */
    public Element(String name, String uri, String iconElement) {
        this.name=name;
        this.uri=uri;
        this.iconElement = iconElement;
        this.zulPage=null;
        this.categoryID=null;
        this.modelID=null;
    }

     /*
     * Metodo constructoe de clase
     * @param name String del nombre del elemento
     * @param uri String del uri del elemento
     * @param zulPage String del uri del elemento
     * @param iconElement String del iconElement del elemento
     */
    public Element(String name, String uri, String zulPage, String iconElement, String categoryID, String modelID) {
        this.name=name;
        this.uri=uri;
        this.zulPage=zulPage;
        this.iconElement = iconElement;
        this.categoryID=categoryID;
        this.modelID=modelID;
    }

    /*
     * Metodo constructoe de clase
     * @param name String del nombre del elemento
     * @param uri String del uri del elemento
     * @param zulPage String del uri del elemento
     * @param iconElement String del iconElement del elemento
     * @param categoryID String del categoryID del elemento
     */
    public Element(String name, String uri, String zulPage, String iconElement, String categoryID) {
        this.name=name;
        this.uri=uri;
        this.zulPage=zulPage;
        this.iconElement = iconElement;
        this.categoryID=categoryID;
    }

    /*
     * Sets
     */
    public void setName(String name)
    {
        this.name=name;
    }

    public void setUri(String uri)
    {
        this.uri=uri;
    }

    public void setZulPage(String zulPage)
    {
        this.zulPage=zulPage;
    }

    public void seticonElement(String iconElement)
    {
        this.iconElement=iconElement;
    }

    public void setCategoryID(String categoryID)
    {
        this.categoryID=categoryID;
    }

    public void setModelID(String modelID)
    {
        this.modelID=modelID;
    }

    /*
     * Gets
     */

    public String getName() {
        return name;
    }

    public String getUri()
    {
        return uri;
    }

    public String getZulPage() {
        return zulPage;
    }

    public String getIconElement() {
        return iconElement;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getModelID() {
        return modelID;
    }
}
