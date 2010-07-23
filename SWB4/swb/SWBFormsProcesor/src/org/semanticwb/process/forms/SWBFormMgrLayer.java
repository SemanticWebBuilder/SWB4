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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class SWBFormMgrLayer {

    private String xml = null;
    private Document dom = null;
    private User user = null;
    HttpServletRequest request = null;
    ArrayList<SWBProperty> aProperties = null;
    SWBProcessFormMgr mgr = null;
    SWBParamRequest paramRequest = null;
    private String htmlType = "";
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
        user = paramRequest.getUser();

        String suri = request.getParameter("suri");
        FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        mgr = new SWBProcessFormMgr(foi);
        //mgr.clearProperties();

    }

    private void createObjs() {
        try {
            dom = SWBUtils.XML.xmlToDom(xml);
            NodeList ndllevel1 = dom.getChildNodes();
            if (ndllevel1.getLength() > 0) { //en el tag swbForm pueden venir algunos atributos para la forma, ej. si se utilizara Dojo como framework
                if (ndllevel1.item(0).getNodeName().equalsIgnoreCase("Form")) {
                    NamedNodeMap nnodemap = ndllevel1.item(0).getAttributes();
                    if (nnodemap.getLength() > 0) {
                        for (int i = 0; i < nnodemap.getLength(); i++) { //Preparado para si se requiere agregar más parametros al tag swbForm
                            String attrName = nnodemap.item(i).getNodeName();
                            String attrValue = nnodemap.item(i).getNodeValue();
                            if (attrValue != null && !attrValue.equals("")) {
                                if (attrName.equalsIgnoreCase("htmlType")) {
                                    htmlType = attrValue;
                                }
                            }
                        }
                    }

                    ArrayList<Node> aSWBPropertyNodes = new ArrayList();
                    Iterator<Node> itSWBPropertyNodes = findPropertyNodes(ndllevel1.item(0), aSWBPropertyNodes).iterator();
                    while (itSWBPropertyNodes.hasNext()) {//Barrido de las propiedades existentes
                        Node swbPropertyNode = itSWBPropertyNodes.next();
                        SWBProperty property = new SWBProperty(swbPropertyNode, htmlType, request, paramRequest, mgr);
                        aProperties.add(property);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private ArrayList findPropertyNodes(Node swbClassNode, ArrayList aSWBPropsNodes) {
        NodeList nlist = swbClassNode.getChildNodes();
        for (int i = 0; i <= nlist.getLength(); i++) {
            Node unKwonnode = nlist.item(i);
            if (unKwonnode != null && !unKwonnode.getNodeName().startsWith("#text")) {
                String snodelName = unKwonnode.getNodeName();
                if (snodelName.equalsIgnoreCase("property") || snodelName.equalsIgnoreCase("label") || snodelName.equalsIgnoreCase("button")) {
                    aSWBPropsNodes.add(unKwonnode);
                } else {
                    findPropertyNodes(unKwonnode, aSWBPropsNodes);
                }
            }
        }
        return aSWBPropsNodes;
    }

    public String getHtml() { //En estos momentos solo funcionando para una sola clase
        StringBuilder strb = new StringBuilder();
        int index = 0;
        int y = 0;
        String xmlTmp = "";
        SWBResourceURL actionUrl = paramRequest.getActionUrl();
        actionUrl.setAction("update");
        String sDojo = htmlType;
        if (sDojo.equalsIgnoreCase("dojo")) {
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

        Iterator<SWBProperty> itaProperties = aProperties.iterator();
        while (itaProperties.hasNext()) {
            SWBProperty swbProperty = itaProperties.next();
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
