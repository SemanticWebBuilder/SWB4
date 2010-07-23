/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.forms;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.lang.String;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.w3c.dom.NamedNodeMap;
import java.util.Enumeration;

/**
 *
 * @author jorge.jimenez
 */
public class SWBPropertyTag implements SWBFormLayer {

    private static Logger log = SWBUtils.getLogger(SWBProperty.class);
    private HtmlStreamTokenizer tok = null;
    HashMap hattributes = new HashMap();
    NamedNodeMap nmFormElement = null;
    private String htmlType = "dojo";
    HttpServletRequest request = null;
    SWBParamRequest paramRequest = null;
    SWBProcessFormMgr mgr = null;
    Resource base = null;
    User user = null;
    String uri = null;
    String tagName = "";
    HtmlTag tag = new HtmlTag();
    String stag="";

    public SWBPropertyTag(HttpServletRequest request, SWBParamRequest paramRequest, SWBProcessFormMgr mgr, HtmlStreamTokenizer tok, String htmlType) {
        this.tok = tok;
        this.request = request;
        this.paramRequest = paramRequest;
        if(htmlType!=null) this.htmlType=htmlType;
        base = paramRequest.getResourceBase();
        user = paramRequest.getUser();
        this.mgr = mgr;
        setAttributes();
    }

    public void setAttributes() {
        try{
            tok.parseTag(tok.getStringValue(), tag);
            Enumeration en = tag.getParamNames();
            while (en.hasMoreElements()) {
                String name = (String) en.nextElement();
                String value = tag.getParam(name);
                if (name.equalsIgnoreCase("uri")) {
                    uri = value;
                }
            }
            tagName = tag.getTagString();
            stag=tok.getRawString();
        }catch(Exception e){
            log.debug(e);
        }
    }

    public String getHtml() {
        String renderElement = null;
        if (request != null) {
            try {
                if (tagName.equalsIgnoreCase("button")) {
                    String type = null;
                    type = tag.getParam("type");
                    if (type != null && type.equalsIgnoreCase("savebtn")) {
                        renderElement = SWBFormButton.newSaveButton().renderButton(request, htmlType, user.getLanguage());
                    } else if (type != null && type.equalsIgnoreCase("cancelbtn")) {
                        renderElement = SWBFormButton.newCancelButton().renderButton(request, htmlType, user.getLanguage());
                    } else if (type != null && type.equalsIgnoreCase("submit")) {
                        renderElement = renderGenericBtn();
                    }
                } else if(uri!=null){
                    SemanticProperty semProp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
                    if (semProp != null) {
                        String mode = "create";
                        if (base.getAttribute("objInst") != null && base.getAttribute("objInst").trim().length() > 0) {
                            mode = "edit";
                        }
                        renderElement = renderElement(request, semProp, mode);
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        return renderElement;
    }

    private String renderGenericBtn() {
        SWBFormButton bt = new SWBFormButton();
        String attrValue = null;

        attrValue = tag.getParam("name");
        if (attrValue != null)  bt.setAttribute("name", attrValue);
        
        attrValue = tag.getParam("title");
        if (attrValue != null)  bt.setTitle(attrValue, "es");
        
        attrValue = tag.getParam("isBusyButton");
        if (attrValue != null)  bt.setBusyButton(Boolean.getBoolean(attrValue));

        bt.setAttribute("type", "submit");

        return bt.renderButton(request, htmlType, user.getLanguage());
    }

    public String renderElement(HttpServletRequest request, SemanticProperty semprop, String mode) {
        if (tagName.equalsIgnoreCase("property")) {
            return mgr.renderElement(request, semprop, mode);
        } else if (tagName.equalsIgnoreCase("label")) {
            return mgr.renderLabel(request, semprop, mode);
        } else {
            return null;
        }
    }

    public String getNodeName() {
        return tagName;
    }

    
    public String getTag() {
        return stag;
    }
   

    public String getUri() {
        return uri;
    }

    public Enumeration getAttributes() {
        return tag.getParamNames();
    }

    public NamedNodeMap getFormElementSttributes() {
        return nmFormElement;
    }

    public String getMoreAttr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMoreAttr(String moreattr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
