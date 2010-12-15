/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

import com.arthurdo.parser.HtmlStreamTokenizer;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.w3c.dom.NamedNodeMap;

/**
 *
 * @author jorge.jimenez
 */
public class SWBButtonTag implements SWBFormLayer {

    private static Logger log = SWBUtils.getLogger(SWBButtonTag.class);
    private HtmlStreamTokenizer tok = null;
    NamedNodeMap nmFormElement = null;
    private String htmlType = "dojo";
    HttpServletRequest request = null;
    SWBParamRequest paramRequest = null;
    SWBProcessFormMgr mgr = null;
    User user = null;
    String stag="";
    String sname=null;
    String stype=null;
    String stitle=null;
    String isBusyButton=null;
    HashMap hMapProperties=new HashMap();


    public SWBButtonTag(HttpServletRequest request, SWBParamRequest paramRequest, HashMap hmapClasses, HashMap hMapProperties, SWBProcessFormMgr mgr, HtmlStreamTokenizer tok, String htmlType) {
        this.tok = tok;
        stag=tok.getRawString();
        this.request = request;
        this.paramRequest = paramRequest;
        if(htmlType!=null) this.htmlType=htmlType;
        if(htmlType!=null && htmlType.equalsIgnoreCase("dojo")) htmlType=SWBFormMgr.TYPE_DOJO;
        user = paramRequest.getUser();
        this.mgr = mgr;
        this.hMapProperties=hMapProperties;
        setAttributes();
    }

    public void setAttributes() {
        try{
            Iterator<String> itProps=hMapProperties.keySet().iterator();
            while(itProps.hasNext()){
                String strPropKey=itProps.next();
                if (strPropKey.equalsIgnoreCase("type")) {
                    stype = (String)hMapProperties.get(strPropKey);
                }else if (strPropKey.equalsIgnoreCase("name")) {
                    sname = (String)hMapProperties.get(strPropKey);
                }else if (strPropKey.equalsIgnoreCase("title")) {
                    stitle = (String)hMapProperties.get(strPropKey);
                }else if (strPropKey.equalsIgnoreCase("isBusyButton")) {
                    isBusyButton = (String)hMapProperties.get(strPropKey);
                }
            }

        }catch(Exception e){
            log.error(e);
        }
    }

    public String getHtml() {
        String renderElement = null;
        if (request != null) {
            try {
                if(stype.equalsIgnoreCase("savebtn")) {
                    renderElement=SWBFormButton.newSaveButton().renderButton(request, htmlType, user.getLanguage());
                }if(stype.equalsIgnoreCase("backbtn")) {
                    renderElement=SWBFormButton.newBackButton().renderButton(request, htmlType, user.getLanguage());
                }else if(stype.equalsIgnoreCase("cancelbtn")) {
                    renderElement=SWBFormButton.newCancelButton().renderButton(request, htmlType, user.getLanguage());
                }else if(stype.equalsIgnoreCase("acceptbtn")) {
                    String msg="Concluir Tarea"; //TODO:Internacionalizar el recurso
                    if(user.getLanguage().equalsIgnoreCase("en")) msg="Accept Task";
                    renderElement=new SWBFormButton().setTitle(msg,user.getLanguage()).setAttribute("name", "accept").setAttribute("type", "submit").renderButton(request, htmlType, user.getLanguage());
                }else if(stype.equalsIgnoreCase("rejectbtn")) {
                     String msg="Concluir Tarea"; //TODO:Internacionalizar el recurso
                    if(user.getLanguage().equalsIgnoreCase("en")) msg="Reject Task";
                    renderElement=new SWBFormButton().setTitle(msg,user.getLanguage()).setAttribute("name", "reject").setAttribute("type", "submit").renderButton(request, htmlType, user.getLanguage());
                }else if(stype.equalsIgnoreCase("submit")) {
                    renderElement=renderGenericBtn();
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        return renderElement;
    }

    private String renderGenericBtn() {
        SWBFormButton bt = new SWBFormButton();        
        if (sname != null)  bt.setAttribute("name", sname);
        if (stitle != null)  bt.setTitle(stitle, "es");
        if (isBusyButton != null)  bt.setBusyButton(Boolean.getBoolean(isBusyButton));
        bt.setAttribute("type", "submit");
        return bt.renderButton(request, htmlType, user.getLanguage());
    }

    
    public String getTag() {
        return stag;
    }

    public String getMoreAttr() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMoreAttr(String moreattr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}