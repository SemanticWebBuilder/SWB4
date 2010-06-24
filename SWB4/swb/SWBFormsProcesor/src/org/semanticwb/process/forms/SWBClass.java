/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.forms;

import java.util.ArrayList;
import java.util.Locale;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 *
 * @author jorge.jimenez
 */
public class SWBClass {

    private static Logger log = SWBUtils.getLogger(SWBClass.class);
    private Node tag = null;
    private Resource base = null;
    private String name = null;
    private String uri = null;
    private String htmlType=null;
    private String sfilterRequired=null;
    private Locale locale = null;
    private ArrayList<SWBFormLayer> aElements = null;
    private SWBFormMgr swbFormMgr=null;
    private SWBParamRequest paramRequest=null;
    
    public SWBClass(Node tag, SWBParamRequest paramRequest, String htmlType) {
        this.tag = tag;
        this.paramRequest=paramRequest;
        this.htmlType=htmlType;
        aElements = new ArrayList();
        init();
    }

    public SWBClass(Node tag, Resource base) {
        this.tag = tag;
        this.base = base;
        aElements = new ArrayList();
        init();
    }

    public void setHtmlType(String htmlType){
        this.htmlType=htmlType;
    }

    private void init() {
        base = paramRequest.getResourceBase();

        //Saca el nombre de la clase que hay que instanciar, ya esta preparado para que el tag acpte más atributos en lo futuro
        if (tag != null) {
            NamedNodeMap nnodemap = tag.getAttributes();
            if (nnodemap.getLength() > 0) {
                for (int i = 0; i < nnodemap.getLength(); i++) {
                    String attrName = nnodemap.item(i).getNodeName();
                    String attrValue = nnodemap.item(i).getNodeValue();
                    if (attrValue != null && !attrValue.equals("")) {
                        if (attrName.equalsIgnoreCase("uri")) {
                            uri = attrValue;
                        }else if (attrName.equalsIgnoreCase("uri")) {
                            sfilterRequired = attrValue;
                        }
                        //Se pueden sacar mas atributos
                    }
                }
            }
        }
        //uri=cls1.getURI();
        if(uri!=null && uri.trim().length()>0){
            if(base.getAttribute("objInst")!=null && base.getAttribute("objInst").trim().length()>0) {
                //Es edición y ahorita solo funciona para una clase (No para varias)
                SemanticObject semObject = SemanticObject.createSemanticObject(base.getAttribute("objInst"));
                swbFormMgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            }else{ //Se esta creando apenas una instancia de la clase
                SemanticClass cls1 = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                swbFormMgr = new SWBFormMgr(cls1, base.getSemanticObject(), null);
            }

            if(htmlType!=null) {
                swbFormMgr.setType(htmlType);
            }
            if(swbFormMgr!=null && (sfilterRequired!=null && (sfilterRequired.equalsIgnoreCase("true") || sfilterRequired.equalsIgnoreCase("false")))) {
                swbFormMgr.setFilterRequired(Boolean.getBoolean(sfilterRequired));
            }
        }
    }

        //sets

    public void setName(String name) {
        this.name = name;
    }

    //Sets
    /**
     * Sets the locale.
     * @param locale the new locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void addElement(SWBFormLayer element) {
        aElements.add(element);
    }

    //gets
    public String getName() {
        return name;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return this.locale;
    }

    public ArrayList getElements() {
        return aElements;
    }

    public String getHtml() {
        return null;
    }

    public SWBFormMgr getSWBFormMgr(){
        return swbFormMgr;
    }
}
