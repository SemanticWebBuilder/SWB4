/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.forms;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author jorge.jimenez
 */
public class SWBProperty implements SWBFormLayer{

    private static Logger log = SWBUtils.getLogger(SWBProperty.class);
    private Node tag = null;
    private String uri = null;
    private String type= null;
    private SWBFormMgr swbFormMgr=null;
    private String htmlType=null;
    HttpServletRequest request=null;
    SWBParamRequest paramRequest=null;
    Resource base=null;
    User user=null;

    public SWBProperty(Node tag, SWBFormMgr swbFormMgr, HttpServletRequest request, SWBParamRequest paramRequest) {
        this.tag = tag;
        this.swbFormMgr=swbFormMgr;
        this.request=request;
        this.paramRequest=paramRequest;
        base=paramRequest.getResourceBase();
        user=paramRequest.getUser();
        setAttributes();
    }

    public SWBProperty(Node tag, String htmlType, HttpServletRequest request, SWBParamRequest paramRequest) {//Utilizado para el caso de que la propiedad sea un boton (Tag swbButton)
        this.tag = tag;
        this.htmlType=htmlType;
        this.request=request;
        this.paramRequest=paramRequest;
        base=paramRequest.getResourceBase();
        user=paramRequest.getUser();
        setAttributes();
    }

    public void setAttributes() {
        if (tag != null) {
            NamedNodeMap nnodemap = tag.getAttributes();
            if (nnodemap.getLength() > 0) {
                for (int i = 0; i < nnodemap.getLength(); i++) {
                    String attrName = nnodemap.item(i).getNodeName();
                    String attrValue = nnodemap.item(i).getNodeValue();
                    if (attrValue != null && !attrValue.equals("")) {
                        if (attrName.equalsIgnoreCase("uri")) {
                            uri = attrValue;
                        }else if (attrName.equalsIgnoreCase("type")) {
                            type = attrValue;
                        }
                    }
                }
            }
        }
    }

    public String getHtml() {
        String renderElement=null;
        if(request!=null){
            try{
                String tagName=tag.getNodeName();

                if(tagName.equalsIgnoreCase("button") && type!=null){

                    if(type.equalsIgnoreCase("savebtn")) {
                        renderElement=SWBFormButton.newSaveButton().renderButton(request, htmlType, user.getLanguage());
                    }else if(type.equalsIgnoreCase("cancelbtn")) {
                        renderElement=SWBFormButton.newCancelButton().renderButton(request, htmlType, user.getLanguage());
                    }
                }
                else if(swbFormMgr!=null && uri!=null && uri.trim().length()>0){
                    SemanticProperty semProp=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
                    if(semProp!=null)
                    {
                        if(tag.getNodeName().equalsIgnoreCase("property")){
                            String mode=swbFormMgr.MODE_CREATE;
                            if(base.getAttribute("objInst")!=null && base.getAttribute("objInst").trim().length()>0) {
                                mode=swbFormMgr.MODE_EDIT;
                            }
                            renderElement=swbFormMgr.renderElement(request, semProp, mode);
                        }else if(tag.getNodeName().equalsIgnoreCase("label")){
                            renderElement=semProp.getDisplayName(user.getLanguage());
                        }
                    }
                }
            }catch(Exception e){log.error(e);}
        }
        return renderElement;
    }

    public String getTag() {
        String retString=SWBUtils.XML.nodeToString(tag);
        if(retString!=null) retString=SWBUtils.TEXT.replaceAll(retString, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        return retString;
    }

    public SWBFormMgr getswbFormMgr(){
        return swbFormMgr;
    }

    public String getMoreAttr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMoreAttr(String moreattr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
