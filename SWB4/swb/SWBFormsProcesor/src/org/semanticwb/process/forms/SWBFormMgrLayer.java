/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.forms;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFormMgrLayer {

    private String xml = null;
    HttpServletRequest request = null;
    ArrayList<SWBPropertyTag> aProperties = null;
    SWBProcessFormMgr mgr = null;
    SWBParamRequest paramRequest = null;
    private String htmlType = "dojo";
    private static Logger log = SWBUtils.getLogger(SWBFormMgrLayer.class);

    public SWBFormMgrLayer(String xml, SWBParamRequest paramRequest, HttpServletRequest request) {
        this.xml = xml;
        this.request = request;
        this.paramRequest = paramRequest;
        init();
        createObjs();
    }

    private void init() {
        aProperties = new ArrayList();
        
        String suri = request.getParameter("suri");
        FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        mgr = new SWBProcessFormMgr(foi);
        //mgr.clearProperties();

    }

    private void createObjs() {
            HtmlTag tag = new HtmlTag();
            int pos = -1;
            int pos1 = -1;
            StringBuffer ret = new StringBuffer();
            try
            {
                HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(xml.getBytes()));
                while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
                {
                    int ttype = tok.getTokenType();
                    if (ttype == HtmlStreamTokenizer.TT_TAG)
                    {
                        tok.parseTag(tok.getStringValue(), tag);
                        if (tok.getRawString().toLowerCase().startsWith("<!--[if"))continue;
                        String tagName=tag.getTagString();
                        if(tagName.toLowerCase().equals("form")){
                            if(tag.getParam("htmlType")!=null) htmlType=tag.getParam("htmlType");
                        }else if(tagName.toLowerCase().equals("label") || tagName.toLowerCase().equals("property") || tagName.toLowerCase().equals("button"))
                        {
                            SWBPropertyTag property = new SWBPropertyTag(request, paramRequest, mgr, tok, htmlType);
                            aProperties.add(property);
                       }
                    }
                }
            }catch (Exception e)
            {
                log.error(e);
            }
    }

    public String getHtml() { //En estos momentos solo funcionando para una sola clase
        StringBuilder strb = new StringBuilder();
        int index = 0;
        int y = 0;
        String xmlTmp = "";
        SWBResourceURL actionUrl = paramRequest.getActionUrl();
        actionUrl.setAction("update");
        String sDojo = htmlType;
        if (sDojo!=null && sDojo.equalsIgnoreCase("dojo")) {
            sDojo = "dojoType=\"dijit.form.Form\"";
        }
        int pos = -1;
        pos = xml.indexOf("<form");
        if (pos > -1) {
            int pos1 = xml.indexOf(">", pos);
            xml = xml.substring(0, pos) + xml.substring(pos1 + 1);
        }

        String lang = paramRequest.getUser().getLanguage();

        strb.append("<form name=\"" + mgr.getFormName() + "\" method=\"post\" action=\"" + actionUrl + "\" id=\"" + mgr.getFormName() + "\" " + sDojo + " class=\"swbform\">");

        Iterator<SWBPropertyTag> itaProperties = aProperties.iterator();
        while (itaProperties.hasNext()) {
            SWBPropertyTag swbProperty = itaProperties.next();
            String match = swbProperty.getTag();
            System.out.println("match:" + match);
            String replace = swbProperty.getHtml();
            System.out.println("replace by:" + replace);
            //Me barro todas las porpiedades del xml, pero voy guardando el indice para seguir barriendo el xml apartir de la ultima propiedad encontrada
            index = xml.indexOf(match, y);
            xmlTmp += xml.substring(y, index);
            xmlTmp += replace;
            y = index + match.length();
        }
        if (mgr.getFormHiddens() != null) {
            strb.append(mgr.getFormHiddens());//Agrego los hiddens que me regresa el FormMgr para cada Clase
        }
        if (xmlTmp.trim().length() > 0) {
            xmlTmp += xml.substring(y);
            xml = xmlTmp;
        }
        strb.append(xml);
        strb.append("</form>");
        return strb.toString();
    }

    public static SemanticObject update2DB(HttpServletRequest request, SWBActionResponse response, FlowNodeInstance foi) { //En estos momentos solo funcionando para una sola clase
        try {
            String suri=request.getParameter("suri");
            if(suri==null) return null;
            SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
            mgr.clearProperties();

            Iterator<ProcessObject> it = foi.listHeraquicalProcessObjects().iterator();
            while (it.hasNext()) {
                ProcessObject obj = it.next();
                SemanticClass cls = obj.getSemanticObject().getSemanticClass();
                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    if (isViewProperty(response, cls, prop)) {
                        mgr.addProperty(prop, cls, SWBFormMgr.MODE_VIEW);
                    } else if (isEditProperty(response, cls, prop)) {
                        mgr.addProperty(prop, cls, SWBFormMgr.MODE_EDIT);
                    }
                }
            }
            mgr.processForm(request);
            if (request.getParameter("accept") != null) {
                foi.close(response.getUser(), Instance.ACTION_ACCEPT);
                response.sendRedirect(foi.getProcessWebPage().getUrl());
            } else if (request.getParameter("reject") != null) {
                foi.close(response.getUser(), Instance.ACTION_REJECT);
                response.sendRedirect(foi.getProcessWebPage().getUrl());
            }
            response.setRenderParameter("suri", suri);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static boolean isViewProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop) {
        boolean ret = false;
        String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if (data != null && data.indexOf(cls.getClassId() + "|" + prop.getPropId() + "|view") > -1) {
            return ret = true;
        }
        return ret;
    }

    public static boolean isEditProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop) {
        boolean ret = false;
        String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if (data != null && data.indexOf(cls.getClassId() + "|" + prop.getPropId() + "|edit") > -1) {
            return ret = true;
        }
        return ret;
    }
}
