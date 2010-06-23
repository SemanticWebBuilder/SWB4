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
    private SWBFormMgr swbFormMgr=null;
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
                        }
                    }
                }
            }
        }
        System.out.println("Uri Prop k llega:"+uri);
    }

    public String getHtml() {
        
        if(swbFormMgr!=null && request!=null && uri!=null && uri.trim().length()>0){
            //String renderElement=swbFormMgr.renderElement(request, ServiceProvider.pymtur_contactFirstName, swbFormMgr.MODE_CREATE);
            
            SemanticProperty semProp=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
            if(semProp!=null)
            {
                System.out.println("semProp:"+semProp);

                String mode=swbFormMgr.MODE_CREATE;
                if(base.getAttribute("objInst")!=null && base.getAttribute("objInst").trim().length()>0) {
                    mode=swbFormMgr.MODE_EDIT;
                }
                String renderElement=semProp.getDisplayName(user.getLanguage())+swbFormMgr.renderElement(request, semProp, mode);

                System.out.println("renderElement:"+renderElement);
                return renderElement;
            }
        }
        return null;
    }

    public SWBFormMgr getswbFormMgr(){
        return swbFormMgr;
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

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getStyle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getStyleClass() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setId(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLabel(String label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMoreAttr(String moreattr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStyle(String style) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStyleClass(String styleclass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
