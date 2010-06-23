/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.SWBFormMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author jorge.jimenez
 */
public class FieldSet extends SWBFormLayerContainer{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(FieldSet.class);

    /** The legend. */
    private String legend = null;

    /** The tag. */
    protected Node tag = null;

    /** The locale. */
    private Locale locale=null;

    private SWBFormMgr swbFormMgr=null;
    HttpServletRequest request=null;


    /**
     * Creates a new instwance with the default parameters.
     *
     * @param tag the tag
     * @param base the base
     * @param form the form
     */
    public FieldSet(Node tag, SWBFormMgr swbFormMgr, HttpServletRequest request) {
        this.tag = tag;
        this.swbFormMgr=swbFormMgr;
        this.request=request;
        setAttributes();
        //createObjs();
    }

    /**
     * Sets the legend.
     *
     * @param legend the new legend
     */
    public void setLegend(String legend) {
        this.legend = legend;
    }

    /**
     * Sets the legend.
     *
     * @return the string
     */
    public String setLegend() {
        return legend;
    }


    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     *
     * @return the html
     */
    @Override
    public String getHtml(){
        StringBuffer ret=new StringBuffer("");
        String xml="";
        try
        {
            Document dom=SWBUtils.XML.getNewDocument();
            if(dom!=null)
            {
                Element child=dom.createElement("fieldset");
                if(name!=null) {
                    child.setAttribute("name",name);
                }
                if(id!=null) {
                    child.setAttribute("id",id);
                }
                if(style!=null) {
                    child.setAttribute("style",style);
                }
                if(styleclass!=null) {
                    child.setAttribute("class",styleclass);
                }
                if(moreattr!=null) {
                    child.setAttribute("moreattr",moreattr);
                }
                if(legend!=null) {
                    child.setAttribute("legend",legend);
                }
                dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) {
                    xml=xml.substring(xml.indexOf("<fieldset"), xml.indexOf("/>", xml.indexOf("<fieldset"))) + ">";
                }
                else {
                    xml="";
                }
            }
        }
        catch(Exception e) { log.error(e); }
        ret.append(xml);
        ret.append(super.getHtml());
        ret.append("\n</fieldset>");
        return ret.toString();
    }


    //Sets
    /**
     * Sets the locale.
     *
     * @param locale the new locale
     */
    public void setLocale(Locale locale){
        this.locale=locale;
    }

    //gets
    /**
     * agrega el action del elemento forma.
     *
     * @return the locale
     */
    public Locale getLocale(){
        return this.locale;
    }

     /* (non-Javadoc)
      * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#add(java.lang.Object)
      */
     public void add(SWBFormLayer obj){
       addElement(obj);
    }

  
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#setAttributes()
     */
    @Override
    public void setAttributes() {
        if (tag != null) {
            NamedNodeMap nnodemap = tag.getAttributes();
            if (nnodemap.getLength() > 0) {
                for (int i = 0; i < nnodemap.getLength(); i++) {
                    String attrName = nnodemap.item(i).getNodeName();
                    String attrValue = nnodemap.item(i).getNodeValue();
                    if (attrValue != null && !attrValue.equals("")) {
                        //defecto
                        if (attrName.equalsIgnoreCase("id")) {
                            id = attrValue;
                        } else if (attrName.equalsIgnoreCase("style")) {
                            style = attrValue;
                        } else if (attrName.equalsIgnoreCase("class")) {
                            styleclass = attrValue;
                        } else if (attrName.equalsIgnoreCase("moreattr")) {
                            moreattr = attrValue;
                        } //propios
                        else if (attrName.equalsIgnoreCase("legend")) {
                            legend = attrValue;
                        }
                    }
                }
            }
        }
    }
   
}
