/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * Recommend.java
 *
 * Created on 14 de octubre de 2002, 11:02 AM
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/** Objeto que se encarga de desplegar y administrar recomendaciones de usuarios finales bajo ciertos
 * criterios(configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer recommendations of end users under certain
 * criteria (resource configuration).
 *
 * @author : Vanessa Arredondo N��ez
 * @version 1.0
 */
public class Recommend extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(Recommend.class);
    
    javax.xml.transform.Templates tpl; 
    String webWorkPath = "/work";    
    String name=getClass().getName().substring(getClass().getName().lastIndexOf(".")+1);
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/";
    
    /** 
     * Creates a new instance of Recomendar. 
     */
    public Recommend() {
    }
    
    /**
     * @param base
     */    
    public void setResourceBase(Portlet base)
    {
        try 
        {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();            
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim())); 
                path=webWorkPath + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/"+name+"/"+name+".xslt")); 
            } 
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        }   
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */
    
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action = null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()) ? request.getParameter("rec_act").trim() : "rec_step2";
        Portlet base=getResourceBase();
        try
        {        
            Document  dom = SWBUtils.XML.getNewDocument();
            if("rec_step3".equals(action)) dom=getDomEmail(request, response, paramRequest); // Envia correo
            else
            { // Nueva ventana con formulario
                User user=paramRequest.getUser();
                SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode(url.Mode_VIEW);
                url.setWindowState(url.WinState_MAXIMIZED);
                url.setParameter("rec_act","rec_step3");
                url.setTopic(paramRequest.getTopic());
                url.setCallMethod(paramRequest.Call_DIRECT);

                Element el = dom.createElement("form");
                el.setAttribute("path", path);
                el.setAttribute("accion", url.toString());
                el.setAttribute("from",paramRequest.getLocaleString("msgFrom"));
                el.setAttribute("to",paramRequest.getLocaleString("msgTo"));
                dom.appendChild(el);

                el = dom.createElement("ftextsender");
                el.setAttribute("tag", paramRequest.getLocaleString("msgSender"));
                el.setAttribute("inname", "txtFromName");
                //if (user.isLoged())   TODO: VER4
                {
                    String strFromName = null != user.getUsrFirstName() && !"".equals(user.getUsrFirstName().trim()) ? user.getUsrFirstName().trim() : "";
                    strFromName+= null != user.getUsrLastName() && !"".equals(user.getUsrLastName().trim()) ? " " + user.getUsrLastName().trim() : "";
                    strFromName+= null != user.getUsrSecondLastName() && !"".equals(user.getUsrSecondLastName().trim()) ? " " + user.getUsrSecondLastName().trim() : "";
                    el.setAttribute("invalue", strFromName);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextsender");
                el.setAttribute("tag", paramRequest.getLocaleString("msgSenderEmail"));
                el.setAttribute("inname", "txtFromEmail");
                //if (user.isLoged()) //TODO VER4
                {
                    String strFromEmail = null != user.getUsrEmail() && !"".equals(user.getUsrEmail().trim()) ? user.getUsrEmail().trim() : "";
                    el.setAttribute("invalue", strFromEmail);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextreceiver");
                el.setAttribute("tag", paramRequest.getLocaleString("msgReceiver"));
                el.setAttribute("inname", "txtToName");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextreceiver");
                el.setAttribute("tag", paramRequest.getLocaleString("msgReceiverEmail"));
                el.setAttribute("inname", "txtToEmail");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextarea");
                el.setAttribute("tag", paramRequest.getLocaleString("msgMessage"));
                el.setAttribute("inname", "tarMsg");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("fsubmit");
                if (!"".equals(base.getAttribute("imgenviar", "").trim()))
                {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imgenviar").trim());
                    if (!"".equals(base.getAttribute("altenviar", "").trim())) el.setAttribute("alt", base.getAttribute("altenviar").trim());
                    else el.setAttribute("alt", paramRequest.getLocaleString("msgRecommend"));
                } 
                else
                {
                    el.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnenviar", "").trim())) el.setAttribute("tag", base.getAttribute("btnenviar").trim());
                    else el.setAttribute("tag", paramRequest.getLocaleString("btnSubmit"));
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("freset");
                if (!"".equals(base.getAttribute("imglimpiar", "").trim()))
                {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imglimpiar").trim());
                    if (!"".equals(base.getAttribute("altlimpiar", "").trim())) el.setAttribute("alt", base.getAttribute("altlimpiar").trim());
                    else el.setAttribute("alt", paramRequest.getLocaleString("btnReset"));
                } 
                else
                {
                    el.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnlimpiar", "").trim())) el.setAttribute("tag", base.getAttribute("btnlimpiar").trim());
                    else el.setAttribute("tag", paramRequest.getLocaleString("btnReset"));
                }
                dom.getChildNodes().item(0).appendChild(el);
            }
            return dom;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
    }
    
    /*
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String action = null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()) ? request.getParameter("rec_act").trim() : "rec_step2";
        Portlet base=getResourceBase();
        try
        {        
            Document  dom = SWBUtils.XML.getNewDocument();
            if("rec_step3".equals(action)) dom=getDomEmail(request, response, paramRequest); // Envia correo
            else
            { // Nueva ventana con formulario
                User user=paramRequest.getUser();
                SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode(url.Mode_VIEW);
                url.setWindowState(url.WinState_MAXIMIZED);
                url.setParameter("rec_act","rec_step3");
                url.setTopic(paramRequest.getTopic());
                url.setCallMethod(paramRequest.Call_DIRECT);

                Element el = dom.createElement("form");
                el.setAttribute("path", path);
                el.setAttribute("accion", url.toString());
                dom.appendChild(el);

                el = dom.createElement("ftext");
                el.setAttribute("tag", paramRequest.getLocaleString("msgSender"));
                el.setAttribute("inname", "txtFromName");
                //if (user.isLoged())   TODO: VER4
                {
                    String strFromName = null != user.getUsrFirstName() && !"".equals(user.getUsrFirstName().trim()) ? user.getUsrFirstName().trim() : "";
                    strFromName+= null != user.getUsrLastName() && !"".equals(user.getUsrLastName().trim()) ? " " + user.getUsrLastName().trim() : "";
                    strFromName+= null != user.getUsrSecondLastName() && !"".equals(user.getUsrSecondLastName().trim()) ? " " + user.getUsrSecondLastName().trim() : "";
                    el.setAttribute("invalue", strFromName);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftext");
                el.setAttribute("tag", paramRequest.getLocaleString("msgSenderEmail"));
                el.setAttribute("inname", "txtFromEmail");
                //if (user.isLoged()) //TODO VER4
                {
                    String strFromEmail = null != user.getUsrEmail() && !"".equals(user.getUsrEmail().trim()) ? user.getUsrEmail().trim() : "";
                    el.setAttribute("invalue", strFromEmail);
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftext");
                el.setAttribute("tag", paramRequest.getLocaleString("msgReceiver"));
                el.setAttribute("inname", "txtToName");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftext");
                el.setAttribute("tag", paramRequest.getLocaleString("msgReceiverEmail"));
                el.setAttribute("inname", "txtToEmail");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("ftextarea");
                el.setAttribute("tag", paramRequest.getLocaleString("msgMessage"));
                el.setAttribute("inname", "tarMsg");
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("fsubmit");
                if (!"".equals(base.getAttribute("imgenviar", "").trim()))
                {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imgenviar").trim());
                    if (!"".equals(base.getAttribute("altenviar", "").trim())) el.setAttribute("alt", base.getAttribute("altenviar").trim());
                    else el.setAttribute("alt", paramRequest.getLocaleString("msgRecommend"));
                } 
                else
                {
                    el.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnenviar", "").trim())) el.setAttribute("tag", base.getAttribute("btnenviar").trim());
                    else el.setAttribute("tag", paramRequest.getLocaleString("btnSubmit"));
                }
                dom.getChildNodes().item(0).appendChild(el);

                el = dom.createElement("freset");
                if (!"".equals(base.getAttribute("imglimpiar", "").trim()))
                {
                    el.setAttribute("img", "1");
                    el.setAttribute("src", webWorkPath + "/" + base.getAttribute("imglimpiar").trim());
                    if (!"".equals(base.getAttribute("altlimpiar", "").trim())) el.setAttribute("alt", base.getAttribute("altlimpiar").trim());
                    else el.setAttribute("alt", paramRequest.getLocaleString("btnReset"));
                } 
                else
                {
                    el.setAttribute("img", "0");
                    if (!"".equals(base.getAttribute("btnlimpiar", "").trim())) el.setAttribute("tag", base.getAttribute("btnlimpiar").trim());
                    else el.setAttribute("tag", paramRequest.getLocaleString("btnReset"));
                }
                dom.getChildNodes().item(0).appendChild(el);
            }
            return dom;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
    }
**/
    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */    
    public org.w3c.dom.Document getDomEmail(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        try
        {
            String strFromEmail = null != request.getParameter("txtFromEmail") && !"".equals(request.getParameter("txtFromEmail").trim()) ? request.getParameter("txtFromEmail").trim() : null;
            String strToEmail = null != request.getParameter("txtToEmail") && !"".equals(request.getParameter("txtToEmail").trim()) ? request.getParameter("txtToEmail").trim() : null;
            
            if(strFromEmail != null && strToEmail != null)
            {            
                Document  dom = SWBUtils.XML.getNewDocument();
                String strFromName = null != request.getParameter("txtFromName") && !"".equals(request.getParameter("txtFromName").trim()) ? request.getParameter("txtFromName").trim() : null;
                String strToName = null != request.getParameter("txtToName") && !"".equals(request.getParameter("txtToName").trim()) ? request.getParameter("txtToName").trim() : null;
                String strTarMsg = null != request.getParameter("tarMsg") && !"".equals(request.getParameter("tarMsg").trim()) ? request.getParameter("tarMsg").trim() : null;
                String strSubject = !"".equals(base.getAttribute("subject", "").trim()) ? base.getAttribute("subject").trim() : paramRequest.getLocaleString("msgSubject");
                String strUrl = "http://"+request.getServerName() + (request.getServerPort()!=80 ? ":"+request.getServerPort() : "");
                WebPage topic=paramRequest.getTopic();
                String lang=paramRequest.getUser().getLanguage();

                Element emn = dom.createElement("form");
                emn.setAttribute("path", path);
                emn.setAttribute("email", "1");
                dom.appendChild(emn);
                addElem(dom, emn, "site", topic.getWebSiteId());
                addElem(dom, emn, "siteurl", strUrl);
                addElem(dom, emn, "topic", topic.getTitle(lang));
                addElem(dom, emn, "topicurl", strUrl + topic.getUrl());
                            
                if(strFromName != null) addElem(dom, emn, "fromname", strFromName);
                addElem(dom, emn, "fromemail", strFromEmail);
                if(strToName != null) addElem(dom, emn, "toname", strToName);
                addElem(dom, emn, "toemail", strToEmail);
                addElem(dom, emn, "subject", strSubject);
                if(strTarMsg != null) addElem(dom, emn, "message", strTarMsg);
                
                String strHeadermsg = "<br> \n";
                strHeadermsg += "<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=2> \n";
                strHeadermsg += "----------------------------------------------------------------------<br> \n";
                strHeadermsg += paramRequest.getLocaleString("msgHeaderMessage") + "<br> \n";
                strHeadermsg += "----------------------------------------------------------------------<br> \n";
                if (!"".equals(base.getAttribute("headermsg", "").trim()))
                {
                    addElem(dom, emn, "headermsg", base.getAttribute("headermsg").trim());
                    strHeadermsg += "<br>" + base.getAttribute("headermsg").trim() + "<br><br> \n";
                }                
                strHeadermsg += "<br> \n";
                strHeadermsg += " " + paramRequest.getLocaleString("msgToMessage") + " ";
                strHeadermsg += null != strToName ? "<I>"+ strToName +"</I>" : "";
                strHeadermsg += ",<br><br> \n";
                strHeadermsg += " " + paramRequest.getLocaleString("msgFromMessage") + " ";
                strHeadermsg += null != strFromName ? "<I>"+ strFromName +"</I>" : "";
                strHeadermsg += " " + paramRequest.getLocaleString("msgBodyMessage") + "<br> \n";
                strHeadermsg += " <a href=\""+ strUrl + topic.getUrl() +"\">";
                strHeadermsg += topic.getTitle(lang);
                strHeadermsg += "</a> \n";
                if(strTarMsg != null) strHeadermsg +="<br><br> \n";
                String strFootermsg = "";
                if (!"".equals(base.getAttribute("footermsg", "").trim()))
                {
                    addElem(dom, emn, "footermsg", base.getAttribute("footermsg").trim());
                    strFootermsg +="<br><br><br>"+ base.getAttribute("footermsg").trim() +" \n";
                }                
                strFootermsg += "<br><br> \n";
                strFootermsg += "----------------------------------------------------------------------<br> \n";
                strFootermsg += " " + paramRequest.getLocaleString("msgFooterMessage") + "<br> \n";
                strFootermsg += " <a href=\""+strUrl +"\">" + topic.getWebSiteId() + "</a> \n";
                strFootermsg += "</font> \n";
                strFootermsg += "<br><br> \n";
                addElem(dom, emn, "emailbody", strHeadermsg + strTarMsg + strFootermsg);
                return dom;
            }
            else {
                throw new SWBResourceException("Error Missing Data. The following data fields are required: " +
                "\n\t email account of the sender: "+strFromEmail+
                "\n\t email account of the receiver: "+strToEmail);
            }
        }
        catch (Exception e) { log.error("Error while generating email message in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html");
        StringBuffer ret = new StringBuffer("");
        Portlet base=getResourceBase();
        String action = null != request.getParameter("rec_act") && !"".equals(request.getParameter("rec_act").trim()) ? request.getParameter("rec_act").trim() : "rec_step1";
        if("rec_step1".equals(action))  
        { // Objeto (imagen/bot�n) para invocar la nueva ventana con formulario
            String onclick = "";
            if ("1".equals(base.getAttribute("menubar", "0").trim())) onclick = "menubar=yes";
            else onclick = "menubar=no";
            if ("1".equals(base.getAttribute("toolbar", "0").trim())) onclick += ",toolbar=yes";
            else onclick += ",toolbar=no";
            if ("1".equals(base.getAttribute("status", "0").trim())) onclick += ",status=yes";
            else onclick += ",status=no";
            if ("1".equals(base.getAttribute("location", "0").trim())) onclick += ",location=yes";
            else onclick += ",location=no";
            if ("1".equals(base.getAttribute("directories", "0").trim())) onclick += ",directories=yes";
            else onclick += ",directories=no";
            if ("1".equals(base.getAttribute("scrollbars", "0").trim())) onclick += ",scrollbars=yes";
            else onclick += ",scrollbars=no";
            if ("1".equals(base.getAttribute("resizable", "0").trim())) onclick += ",resizable=yes";
            else onclick += ",resizable=no";
            onclick += ",width="+ base.getAttribute("width", "450").trim();
            onclick += ",height="+ base.getAttribute("height", "400").trim();
            onclick += ",top="+ base.getAttribute("top", "10").trim();
            onclick += ",left="+ base.getAttribute("left", "10").trim();

            SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(), SWBResourceURLImp.UrlType_RENDER);
            url.setResourceBase(base);
            url.setMode(url.Mode_VIEW);
            url.setWindowState(url.WinState_MAXIMIZED);
            url.setParameter("rec_act","rec_step2");
            url.setTopic(paramRequest.getTopic());
            url.setCallMethod(paramRequest.Call_DIRECT);
            onclick="javascript:window.open('" + url.toString() +"','_newrec','" + onclick + "'); return false;";

            synchronized (ret)
            {
                if (!"".equals(base.getAttribute("img", "").trim()))
                {
                    ret.append("\n<a href=\""+onclick+"\"><img onClick=\""+ onclick +"\" src=\"");
                    ret.append(webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                    if (!"".equals(base.getAttribute("alt","").trim())) ret.append(" alt=\"" + base.getAttribute("alt").trim().replaceAll("\"", "&#34;") + "\"");
                    ret.append(" border=0></a>");
                } 
                else if (!"".equals(base.getAttribute("btntexto", "").trim()))
                {
                    ret.append("\n<form name=frmRecomendar>");
                    ret.append("\n<input type=button name=btnRecomendar onClick=\""+ onclick +"\" value=");
                    ret.append("\"" + base.getAttribute("btntexto").trim().replaceAll("\"", "&#34;") + "\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
                    ret.append("\n></form>");
                }
                else
                {
                    ret.append("\n<a href=\""+onclick+"\" onClick=\""+ onclick +"\"");
                    if (!"".equals(base.getAttribute("blnstyle", "").trim())) ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"", "&#34;") + "\"");
                    ret.append(">");
                    if (!"".equals(base.getAttribute("lnktexto", "").trim())) ret.append(base.getAttribute("lnktexto").trim());
                    else ret.append(paramRequest.getLocaleString("msgRecommend"));
                    ret.append("</a>");
                }
            }            
        }
        else
        {
            try
            {
                Document dom =getDom(request, response, paramRequest);
                if(dom != null) 
                {
                    ret.append(SWBUtils.XML.transformDom(tpl, dom));
                    if("rec_step3".equals(action))
                    {
                        String from   =dom.getElementsByTagName("fromemail").item(0).getFirstChild().getNodeValue();
                        String to     =dom.getElementsByTagName("toemail").item(0).getFirstChild().getNodeValue();
                        String subject=dom.getElementsByTagName("subject").item(0).getFirstChild().getNodeValue();
                        
                        String strUrl = "=\"http://"+request.getServerName() + (request.getServerPort()!=80 ? ":"+request.getServerPort() : "") + path;
                        ret = new StringBuffer(SWBUtils.TEXT.replaceAll(ret.toString(), "=\""+path, strUrl));
                        
                        javax.mail.internet.InternetAddress address1 = new javax.mail.internet.InternetAddress();
                        address1.setAddress(to);
                        ArrayList aAddress = new ArrayList();
                        aAddress.add(address1);
                        
                        //SWBUtils.EMAIL.setSMTPServer("webmail.infotec.com.mx");
                        if ((from!=null && to!=null && subject!=null) && SWBUtils.EMAIL.sendMail(from, from, aAddress, null, null, subject, "text",  ret.toString(), null, null, null)!=null)
                        {
                            ret.append("\n<script>");
                            ret.append("\nalert('" + paramRequest.getLocaleString("msgSendEmail") + "');");
                            ret.append("\nwindow.close();");
                            ret.append("\n</script>");
                        }
                        else
                        {
                            ret.append("\n<script>");
                            ret.append("\nalert('" + paramRequest.getLocaleString("msgEmailRequired") + "');");
                            ret.append("\nhistory.go(-1);");
                            ret.append("\n</script>");
                        }
                    }
                }
            }
            catch(Exception e){log.error(e);}           
        }
         PrintWriter out = response.getWriter();
        out.println(ret.toString());
        out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin Recomend</a>");
    }
    
    /**
     * @param doc
     * @param parent
     * @param elemName
     * @param elemValue
     */      
    private void addElem(org.w3c.dom.Document doc, org.w3c.dom.Element parent, String elemName, String elemValue)
    {
        org.w3c.dom.Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
    }    
}