/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.forms;

import java.lang.String;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericFormElement;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.base.FormElementBase;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author jorge.jimenez
 */
public class SWBProperty implements SWBFormLayer{

    private static Logger log = SWBUtils.getLogger(SWBProperty.class);
    private Node tag = null;
    HashMap hattributes=new HashMap();
    NamedNodeMap nmFormElement=null;
    private String htmlType=null;
    HttpServletRequest request=null;
    SWBParamRequest paramRequest=null;
    SWBProcessFormMgr mgr=null;
    Resource base=null;
    User user=null;
    String uri=null;
    String tagName="";

    public SWBProperty(Node tag, HttpServletRequest request, SWBParamRequest paramRequest, SWBProcessFormMgr mgr) {
        this.tag = tag;
        this.request=request;
        this.paramRequest=paramRequest;
        base=paramRequest.getResourceBase();
        user=paramRequest.getUser();
        this.mgr=mgr;
        setAttributes();
    }

     public SWBProperty(Node tag, String htmlType, HttpServletRequest request, SWBParamRequest paramRequest, SWBProcessFormMgr mgr) {//Utilizado para el caso de que la propiedad sea un boton (Tag swbButton)
        this.tag = tag;
        this.htmlType=htmlType;
        this.request=request;
        this.paramRequest=paramRequest;
        base=paramRequest.getResourceBase();
        user=paramRequest.getUser();
        this.mgr=mgr;
        setAttributes();
    }


    public void setAttributes() {
        Node formElementNode=tag.getFirstChild();
        if(formElementNode!=null && formElementNode.getNodeName().equalsIgnoreCase("formElement")){
           nmFormElement=formElementNode.getAttributes();
        }
         Node nodeAttr=tag.getAttributes().getNamedItem("uri");
         if(nodeAttr!=null) uri=nodeAttr.getNodeValue();
         tagName=tag.getNodeName();
    }

   
    public String getHtml() {
        String renderElement=null;
        if(request!=null){
            try{
                if(tagName.equalsIgnoreCase("button")){
                    String type=null;
                    Node nodeAttr=tag.getAttributes().getNamedItem("type");
                    if(nodeAttr!=null) type=nodeAttr.getNodeValue();

                    if(type.equalsIgnoreCase("savebtn")) {
                        renderElement=SWBFormButton.newSaveButton().renderButton(request, htmlType, user.getLanguage());
                    }else if(type.equalsIgnoreCase("cancelbtn")) {
                        renderElement=SWBFormButton.newCancelButton().renderButton(request, htmlType, user.getLanguage());
                    }else if(type.equalsIgnoreCase("submit")) {
                        renderElement=renderGenericBtn(tag);
                    }
                }else{                   
                    SemanticProperty semProp=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
                    if(semProp!=null)
                    {
                         //if(tag.getNodeName().equalsIgnoreCase("property")){
                            String mode="create";
                            if(base.getAttribute("objInst")!=null && base.getAttribute("objInst").trim().length()>0) {
                                mode="edit";
                            }
                            renderElement=renderElement(request, semProp, mode);
                        //}else if(tag.getNodeName().equalsIgnoreCase("label")){
                        //    System.out.println("");
                        //    renderElement=semProp.getDisplayName(user.getLanguage());
                        //}
                    }
                }
            }catch(Exception e){log.error(e);}
        }
        return renderElement;
    }

    private String renderGenericBtn(Node tag){
        SWBFormButton bt=new SWBFormButton();
        Node nodeAttr=null;

        nodeAttr=tag.getAttributes().getNamedItem("name");
        if(nodeAttr!=null && nodeAttr.getNodeValue()!=null) bt.setAttribute("name", nodeAttr.getNodeValue());

        nodeAttr=tag.getAttributes().getNamedItem("title");
        if(nodeAttr!=null && nodeAttr.getNodeValue()!=null) bt.setTitle(nodeAttr.getNodeValue(), "es");

        nodeAttr=tag.getAttributes().getNamedItem("isBusyButton");
        if(nodeAttr!=null && nodeAttr.getNodeValue()!=null) bt.setBusyButton(Boolean.getBoolean(nodeAttr.getNodeValue()));

        bt.setAttribute("type", "submit");

        return bt.renderButton(request, htmlType, user.getLanguage());
    }


    public String renderElement(HttpServletRequest request, SemanticProperty semprop, String mode)
    {
        if(tag.getNodeName().equalsIgnoreCase("property")) return mgr.renderElement(request, semprop, mode);
        else if(tag.getNodeName().equalsIgnoreCase("label")) return mgr.renderLabel(request, semprop, mode);
        else return null;
    }

    public String getNodeName(){
        return tagName;
    }

    /*
    public String getTag() {
        String retString=SWBUtils.XML.nodeToString(tag);
        if(retString!=null) retString=SWBUtils.TEXT.replaceAll(retString, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        return retString;
    }*/

    public String getTag() {
        try{
            String retString=SWBUtils.XML.domToXml(SWBUtils.XML.node2Document(tag));
            if(retString!=null) retString=SWBUtils.TEXT.replaceAll(retString, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
            return retString;
        }catch(Exception e){log.error(e);}
        return null;
    }

    public String getUri(){
        return uri;
    }

    public NamedNodeMap getAttributes(){
        return tag.getAttributes();
    }

    public NamedNodeMap getFormElementSttributes(){
        return nmFormElement;
    }

    
    public String getMoreAttr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMoreAttr(String moreattr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
