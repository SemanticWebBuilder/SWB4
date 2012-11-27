/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.forms;

import com.arthurdo.parser.HtmlStreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticProperty;
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


    public SWBButtonTag(HttpServletRequest request, SWBParamRequest paramRequest, HashMap<String,ArrayList<SemanticProperty>> hmapClasses, HashMap hMapProperties, SWBProcessFormMgr mgr, HtmlStreamTokenizer tok, String htmlType) {
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
                    //renderElement=new SWBFormButton().setTitle(msg,user.getLanguage()).setAttribute("name", "accept").setAttribute("type", "submit").renderButton(request, htmlType, user.getLanguage());
                    renderElement=new SWBFormButton().newAcceptProcessTaskButton().setAttribute("type", "submit").renderButton(request, htmlType, user.getLanguage());
                }else if(stype.equalsIgnoreCase("rejectbtn")) {
                    //renderElement=new SWBFormButton().setTitle(msg,user.getLanguage()).setAttribute("name", "reject").setAttribute("type", "submit").renderButton(request, htmlType, user.getLanguage());
                    renderElement=new SWBFormButton().newRejectProcessTaskButton().setAttribute("type", "submit").renderButton(request, htmlType, user.getLanguage());
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