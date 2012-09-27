/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.tree;

/**
 *
 * @author jorge.jimenez
 */
public class Element {

    private static final long serialVersionUID = -9145887024839938516L;

    private String name;
    private String uri;
    private String zulPage;
    private String iconElement;
    private String categoryID;

    public Element(String name, String uri, String iconElement) {
        this.name=name;
        this.uri=uri;
        this.iconElement = iconElement;
        this.zulPage=null;
        this.categoryID=null;
    }

    public Element(String name, String uri, String zulPage, String iconElement) {
        this.name=name;
        this.uri=uri;
        this.iconElement = iconElement;
        this.zulPage=zulPage;
        this.categoryID=null;
    }

    public Element(String name, String uri, String zulPage, String iconElement, String categoryID) {
        this.name=name;
        this.uri=uri;
        this.zulPage=zulPage;
        this.iconElement = iconElement;
        this.categoryID=categoryID;
    }

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
}
