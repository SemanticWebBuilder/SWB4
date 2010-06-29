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
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceURL;
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
                    Iterator <Node>itSWBPropertyNodes=findPropertyNodes(ndllevel1.item(0), aSWBPropertyNodes).iterator();
                    while (itSWBPropertyNodes.hasNext()) {//Barrido de las propiedades existentes
                        Node swbPropertyNode = itSWBPropertyNodes.next();
                        SWBProperty property = new SWBProperty(swbPropertyNode, htmlType, request, paramRequest);
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
                if (snodelName.equalsIgnoreCase("property") || snodelName.equalsIgnoreCase("label") ||  snodelName.equalsIgnoreCase("Button")) {
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
        int pos=-1;
        pos=xml.indexOf("<form");
        if(pos>-1){
            int pos1=xml.indexOf(">", pos);
            xml=xml.substring(0,pos)+xml.substring(pos1+1);
        }
        
        strb.append("<form name=\"SWBFormMgrLayer\" method=\"post\" action=\"" + actionUrl + "\" id=\"SWBFormMgrLayer\" " + sDojo + " class=\"swbform\">");
        Iterator<SWBProperty> itaProperties = aProperties.iterator();
        while (itaProperties.hasNext()) {
            SWBProperty swbProperty = itaProperties.next();
                String match=swbProperty.getTag();
                System.out.println("match:"+match);
                String replace=swbProperty.getHtml();
                //Me barro todas las porpiedades del xml, pero voy guardando el indice para seguir barriendo el xml apartir de la ultima propiedad encontrada
                //index=xml.indexOf(match,y);
                //xmlTmp+=xml.substring(y, index);
                //xmlTmp+=replace;
                //y = index + match.length();
            }
            //if(swbClass.getSWBFormMgr().getFormHiddens()!=null){
            //    strb.append(swbClass.getSWBFormMgr().getFormHiddens());//Agrego los hiddens que me regresa el FormMgr para cada Clase
            //}
        if(xmlTmp.trim().length()>0){
            xmlTmp+=xml.substring(index);
            xml = xmlTmp;
        }
        strb.append(xml);
        strb.append("</form>");
        return strb.toString();
    }

    /*
    public String getHtml(){ //En estos momentos solo funcionando para una sola clase
    StringBuilder strb=new StringBuilder();
    SWBResourceURL actionUrl=paramRequest.getActionUrl();
    actionUrl.setAction("update");
    String sDojo=htmlType;
    if(sDojo.equalsIgnoreCase("dojo")) sDojo="dojoType=\"dijit.form.Form\"";
    strb.append("<form name=\"SWBFormMgrLayer\" method=\"post\" action=\""+actionUrl+"\" id=\"SWBFormMgrLayer\" "+sDojo+" class=\"swbform\">");
    Iterator <SWBClass> itaClasses=aClasses.iterator();
    while(itaClasses.hasNext()){
    SWBClass swbClass=itaClasses.next();
    Iterator <SWBFormLayer> itElements=swbClass.getElements().iterator();
    while(itElements.hasNext()){
    SWBFormLayer swbFormLayerElement=itElements.next();
    xml=xml.replaceFirst(swbFormLayerElement.getTag(), swbFormLayerElement.getHtml()); //TODO:Ver si puedo no barrerme todo el archivo por cada una de las propiedades
    }
    String hiddens=swbClass.getSWBFormMgr().getFormHiddens();
    strb.append(hiddens);
    }
    Iterator <SWBProperty>itproperties=aProperties.iterator();
    while(itproperties.hasNext()){
    SWBProperty swbProperty=itproperties.next();
    xml=xml.replaceFirst(swbProperty.getTag(), swbProperty.getHtml()); //TODO:Ver si puedo no barrerme todo el archivo por cada una de las propiedades
    }
    strb.append(xml);
    strb.append("</form>");
    return strb.toString();
    }*/
    public static SemanticObject update2DB(HttpServletRequest request, SWBActionResponse response) { //En estos momentos solo funcionando para una sola clase
        String action = response.getAction();
        Resource base = response.getResourceBase();
        try {
            if (action != null && action.equals("update")) {
                SWBFormMgr mgr = null;
                SemanticObject semObj = null;
                if (base.getAttribute("objInst") != null && base.getAttribute("objInst").trim().length() > 0) {
                    SemanticObject semObject = SemanticObject.createSemanticObject(base.getAttribute("objInst"));
                    mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                } else {
                    SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("scls"));
                    mgr = new SWBFormMgr(cls, base.getSemanticObject(), null);
                }
                if (mgr != null) {
                    semObj = mgr.processForm(request);
                }
                if (semObj != null) {
                    return semObj;
                }
            } else if (action.equals("remove")) {
                //TODO
                System.out.println("Entra a update2DB Delete");
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}
