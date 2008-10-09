/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.GenericXformsResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class Banner1 extends GenericXformsResource {

    private static Logger log = SWBUtils.getLogger(Banner1.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        Portlet base = getResourceBase();
        try {
            System.out.println("Entra Banne1Jorge:" + base.getXml());
            String local = base.getAttribute("txtLocal", "0").trim();
            if (local.equals("0")) {
                String img = base.getAttribute("wbFile_img", "").trim();
                if (!img.equals("")) {
                    String wburl = paramRequest.getActionUrl().toString();
                    String url = base.getAttribute("url", "").trim();
                    String width = base.getAttribute("width", "").trim();
                    String height = base.getAttribute("height", "").trim();
                    if (url.toLowerCase().startsWith("mailto:") || url.toLowerCase().startsWith("javascript:")) {
                        wburl = url.replaceAll("\"", "&#34;");
                    }
                    synchronized (ret) {
                        if (img.endsWith(".swf")) {
                            String schema = new URL(request.getRequestURL().toString()).getProtocol();

                            ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"" + schema + "://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\"");
                            if (!width.equals("")) {
                                ret.append(" width=\"" + width + "\"");
                            }
                            if (!height.equals("")) {
                                ret.append(" height=\"" + height + "\"");
                            }
                            ret.append(">\n");
                            ret.append("<param name=movie value=\"" + SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + img + "\" />\n");
                            ret.append("<param name=\"quality\" value=\"high\" />\n");
                            ret.append("<param name=\"wmode\" value=\"transparent\" /> \n");
                            ret.append("<param name=\"FlashVars\" value=\"liga=" + wburl + "\" />\n");
                            ret.append("<embed id=\"" + img + "\" name=\"" + img + "\" src=\"");
                            ret.append(SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + img + "\"");
                            ret.append(" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" ");
                            if (!width.equals("")) {
                                ret.append(" width=\"" + width + "\"");
                            }
                            if (!height.equals("")) {
                                ret.append(" height=\"" + height + "\"");
                            }
                            ret.append(">");
                            ret.append("</embed>\n");
                            ret.append("</object>");
                        } else {
                            String alt = base.getAttribute("alt", "").trim();
                            if (!url.equals("")) {
                                String target = base.getAttribute("target", "0").trim();
                                synchronized (ret) {
                                    ret.append("<a href=\"" + wburl + "\"");
                                    if (target.equals("1")) {
                                        ret.append(" target=\"_newbnr\"");
                                    }
                                    ret.append(">");
                                }
                            }
                            ret.append("<img src=\"");
                            ret.append(SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + img + "\"");
                            if (paramRequest.getArguments().containsKey("border")) {
                                ret.append(" border=\"" + (String) paramRequest.getArguments().get("border") + "\"");
                            } else {
                                ret.append(" border=\"0\"");
                            }
                            if (!alt.equals("")) {
                                ret.append(" alt=\"" + alt + "\"");
                            }
                            if (!width.equals("")) {
                                ret.append(" width=\"" + width + "\"");
                            }
                            if (!height.equals("")) {
                                ret.append(" height=\"" + height + "\"");
                            }
                            ret.append(">");
                            if (!url.equals("")) {
                                ret.append("</a>");
                            }
                        }
                    }
                }
            } else if (local.equals("1")) { //publicidad externa
                ret.append(base.getAttribute("code", ""));
            }
        } catch (Exception e) {
            log.error("Error in resource Banner while bringing HTML", e);
        }
        PrintWriter out = response.getWriter();
        out.println(ret.toString());
        out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin</a>");
    }

    /**
     * Carga instancia, ya sea la de inicio o una ya grabada en BD del recurso en cuestión.
     */
    @Override
    public void doLoadInstance(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
        try {
            if (request.getParameter("wbmode") != null && request.getParameter("wbmode").equals("view")) { //Se desea la instancia de vista (Front-End)
                //Carga la instancia de archivo
                String res = super.initViewModel(request, paramsRequest);
                response.getWriter().print(res);
            } else { //Se desea la instancia de Administración
                Portlet base = getResourceBase();
                Document dom = SWBUtils.XML.xmlToDom(initAdminModel(request, paramsRequest));
                NodeList nElements = dom.getFirstChild().getChildNodes();
                for (int i = 0; i < nElements.getLength(); i++) {
                    Node nElement = nElements.item(i);
                    if(nElement.getNodeName().startsWith("wbFile_"))
                    {
                        NamedNodeMap nEleAttrs = nElement.getAttributes();
                        if (nEleAttrs.getLength() > 0) {
                            Node nodeShowFile = nEleAttrs.getNamedItem("showFile");
                            if (nodeShowFile != null && nodeShowFile.getNodeValue()!=null) { //Si existe el atributo showFile en algún nodo del xml
                                if (nElement.getFirstChild() != null) {
                                    String value = "<img src=\"" + SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + nElement.getFirstChild().getNodeValue() + "\">";
                                    addElement(nodeShowFile.getNodeValue(), value, (Element) dom.getFirstChild());
                                }
                            }
                        }
                        nElement.getFirstChild().setNodeValue("");
                        Node nodeFile = nEleAttrs.getNamedItem("file");
                        if(nodeFile!=null){
                            nodeFile.setNodeValue(nElement.getFirstChild().getNodeValue());
                        }
                    }
                }
                String xml = SWBUtils.XML.domToXml(dom);
                System.out.println("Entra Admin BannerJ1Sip:" + xml);
                response.getWriter().print(xml);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return;
    }

    /*
    @Override
    public void doLoadInstance(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) {
    try {
    if (request.getParameter("wbmode") != null && request.getParameter("wbmode").equals("view")) { //Se desea la instancia de vista (Front-End)
    //Carga la instancia de archivo
    String res = super.initViewModel(request, paramsRequest);
    response.getWriter().print(res);
    } else { //Se desea la instancia de Administración
    Document dom = SWBUtils.XML.xmlToDom(initAdminModel(request, paramsRequest));
    Node node = dom.getFirstChild();
    if (node != null) {
    Portlet base = getResourceBase();
    String value="<img src=\"" + SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + base.getAttribute("img", "").trim() + "\">";
    Node nodeShowImg=addElement("showimg", value , (Element) node);
    ((Element)nodeShowImg).setAttribute("wbsave","0");
    Node nImg=dom.getElementsByTagName("img").item(0);
    if(nImg!=null && nImg.getFirstChild()!=null){
    nImg.getFirstChild().setNodeValue("");
    }
    Node nodeFile=nImg.getAttributes().getNamedItem("file");
    nodeFile.setNodeValue(base.getAttribute("img", "").trim());
    String xml=SWBUtils.XML.domToXml(dom);
    System.out.println("Entra a doLoadInstanceJ-Banner1:"+xml);
    response.getWriter().print(xml);                    
    }
    }
    } catch (Exception e) {
    log.error(e);
    }
    return;
    }
     **/
    private Element addElement(String name, String value, Element parent) {
        org.w3c.dom.Document doc = parent.getOwnerDocument();
        Element ele = doc.createElement(name);
        if (value != null) {
            ele.appendChild(doc.createTextNode(value));
        }
        parent.appendChild(ele);
        return ele;
    }
}
