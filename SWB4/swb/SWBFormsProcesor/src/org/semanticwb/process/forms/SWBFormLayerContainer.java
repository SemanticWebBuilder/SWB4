/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.forms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFormLayerContainer implements SWBFormLayer {

    protected ArrayList<SWBFormLayer> aElements;
    protected String name = null;
     protected String id = null;
    protected String moreattr = null;
    protected String style = null;
    protected String styleclass = null;
    protected String label = null;


     /**
     * Instantiates a new SWBFormLayerContainer
     */
    public SWBFormLayerContainer() {
        aElements = new ArrayList();
    }

    public void setAttributes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //Sets

    public void setName(String name) {
        this.name = name;
    }

     public void setId(String id) {
        this.id=id;
    }

    public void setLabel(String label) {
        this.label=label;
    }

    public void setMoreAttr(String moreattr) {
        this.moreattr=moreattr;
    }

    public void setStyle(String style) {
        this.style=style;
    }

    public void setStyleClass(String styleclass) {
        this.styleclass=styleclass;
    }

  

    //Gets


    public String getName() {
        return name;
    }

    public String getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLabel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getMoreAttr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getStyle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getStyleClass() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * Gets the form e.
     * @return the form e
     */
    public String getHtml() {
        StringBuilder strb = new StringBuilder();
        Iterator iobj = aElements.iterator();
        do {
            if (!iobj.hasNext()) {
                break;
            }
            Object obj = iobj.next();
            if (obj instanceof SWBFormLayer) {
                SWBFormLayer swbFormLayer = (SWBFormLayer) obj;
                strb.append(swbFormLayer.getHtml());
            }
        } while (true);
        return strb.toString();
    }

    /**
     * Gets the forms.
     *
     * @return the forms
     */
    public Iterator getElements() {
        return aElements.iterator();
    }

    void addElement(SWBFormLayer swbFormLayer) {
        aElements.add(swbFormLayer);
    }
}
