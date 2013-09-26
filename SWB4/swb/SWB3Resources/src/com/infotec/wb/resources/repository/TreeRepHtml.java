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
 * TreeRepHtml.java
 *
 * Created on 20 de abril de 2004, 12:25 PM
 */
package com.infotec.wb.resources.repository;

import javax.servlet.http.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;
import javax.xml.transform.*;
import java.io.IOException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;

/**
 * Muestra el �rbol de los diferentes sub carpetas de las que est� compuesto el
 * repositorio, permitiendo seleccionar la sub carpeta o carpeta principal para
 * ver la lista de documentos existentes.
 *
 * Shows the tree of the differents sub folders from wich is the repository
 * compound, allowing to select sub folder or main folder to see the existing
 * document list.
 *
 * @author Jorge Alberto Jim�nez
 */
public class TreeRepHtml {

    org.semanticwb.model.Resource base = null;
    String webpath = SWBPortal.getContextPath();
    Templates plt;
    Transformer trans;
    DocumentBuilderFactory dbf = null;
    DocumentBuilder db = null;
    String strRes = "";
    String strWorkPath = "";
    Vector vTopic = new Vector();
    int intMaxLevel = 1;

    /**
     * Creates a new instance of TreeRepHtml
     */
    public TreeRepHtml() {
    }

    /**
     * Asigna la informaci�n de la base de datos al recurso.
     *
     * @param base La informaci�n del recurso en memoria.
     */
    public void setResourceBase(org.semanticwb.model.Resource base) {
        this.base = base;
    }

    /**
     * User view in html format
     *
     * @param request The input parameters
     * @param response The answer to the user request
     * @param user The WBUser object
     * @param topicrec a Topic object
     * @param arguments A list of parameters
     * @param topic A Topic object
     * @param paramsRequest A list of objects (topic, user, action, ...)
     * @throws AFException An Application Framework exception
     * @return The user html view of the directory structure
     */
    public String getHtml(HttpServletRequest request, HttpServletResponse response, User user, WebPage topicrec, HashMap arguments, WebPage topic, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        Document dcmDom = base.getDom();
        if (dcmDom == null) {
            throw new SWBResourceException("Dom nulo - getHtml()");
        }
        StringBuffer sbfRet = new StringBuffer();

        SWBResourceURLImp url1 = new SWBResourceURLImp(request, base, topic, SWBResourceURLImp.UrlType_RENDER);
        url1.setMode(url1.Mode_VIEW);
        url1.setAdminTopic(paramsRequest.getAdminTopic());
        try {
            WebSite tm = topic.getWebSite();
            Document dom = null;
            WebPage tpid = null;
            Vector vctPath = new Vector();
            int intLevel =1, intWidth = 10;
            String idhome = null;
            String dir = topic.getId();
            int widthsize = 10;
           String backg = "class=\"cerrado\""; 

            if (request.getParameter("reptp") != null && !request.getParameter("reptp").trim().equals("")) {
                tpid = tm.getWebPage(request.getParameter("reptp"));
                vctPath = getMapPath(tpid);
            } else {
                tpid = paramsRequest.getWebPage();
                vctPath = getMapPath(tpid);
            }

            if (dir != null) {
                idhome = dir;
            }

            WebPage tpsite = null;
            if (idhome != null) {
                tpsite = tm.getWebPage(idhome);
                if (tpsite == null) {
                    tpsite = tm.getHomePage();
                }
            } else {
                tpsite = tm.getHomePage();
            }

            sbfRet.append("<ul>");
            backg = "class=\"cerrado\""; 
            if ((tpid != null && tpid.getId().equals(topic.getId())) || (tpid == null)) {
                backg = "class=\"abierto selec\"";
            } else if(tpid!=null&&tpid.isChildof(topic)) {
                backg ="class=\"abierto\"";
            }

            String tmpName = topic.getDisplayName(user.getLanguage());
            String tmpNameShort = topic.getDisplayName(user.getLanguage());
            if(null!=tmpName&&tmpName.trim().length()>15){
                tmpNameShort = tmpName.substring(0, 15)+"...";
            }
            
            sbfRet.append("<li >");
            url1.setTopic(topic);
            sbfRet.append("<a " + backg + "  href=\"" + url1 + "\" title=\""+tmpName+"\">");
            sbfRet.append( tmpNameShort);
            sbfRet.append("</a>");
            boolean cerrar = Boolean.FALSE;

            Iterator<WebPage> it = sortByOrderedName(tpsite.listChilds());
            if(it.hasNext()){
                sbfRet.append("<ul>");
                cerrar = Boolean.TRUE;
            }
            while (it.hasNext()) {
                intLevel = 1;
                WebPage tp = it.next();
                if (!tp.isActive() || tp.isDeleted() || tp.isHidden()) {
                    continue;
                }
                if (user.haveAccess(tp) && tp.getId() != null) {
                    
                    backg = "class=\"cerrado\""; 
                    if (tpid != null && tpid.getId().equals(tp.getId())) {
                        backg = " class=\"abierto selec\"";
                    } 
//                    else if(tpid != null && tpid.isParentof(tp)){
//                        backg ="class=\"abierto\"";
//                    }
                    tmpName = tp.getDisplayName();
                    tmpNameShort = tp.getDisplayName();
                    if (null != tmpName && tmpName.trim().length() > 15) {
                        tmpNameShort = tmpName.substring(0, 14) + "...";
                    }
                    sbfRet.append("<li>");
                    url1.setTopic(tp);
                    sbfRet.append("<a " + backg + " href=\"" + url1 + "?reptp=" + tp.getId() + "\" title=\""+tmpName+"\">");
                    sbfRet.append( tmpNameShort +"</a>");
                    if ((intLevel < intMaxLevel || (tpid != null && tp.getId().equals(tpid.getId()))
                            || vctPath.contains(tp.getId())) && sortByOrderedName(tp.listChilds()).hasNext()) {
                        sbfRet.append("<ul>");
                        sbfRet.append(getChilds(tpid, tp, user, vctPath, intLevel + 1, intWidth, "", widthsize, paramsRequest));
                        sbfRet.append("</ul>");
                    }
                    sbfRet.append("</li>");
                }
            }
            if(cerrar){
                sbfRet.append("</ul>");
                cerrar = Boolean.TRUE;
            }
            sbfRet.append("</li>");
            sbfRet.append("</ul>");

        } catch (Exception e) {
            Repository.log.error("Error in resource " + "TreeRepHtml:getHtml", e);
        }
        return sbfRet.toString();
    }

    /**
     * Obtiene los t�picos hijo relacionados al t�pico solicitado bajo una
     * estructura jer�rquica.
     *
     ** @param tpid Topic object thats represents a directory
     * @param tpc Topic object thats represents a directory
     * @param user The User object in session
     * @param vctPath A Vector object that hold the topic parents
     * @param intLevel Level of the user
     * @param intWidth The width size value
     * @param topicrec Topic object thats represents a directory
     * @param widthsize The image size
     * @param paramsRequest A list of objects (topic, user, action, ...)
     * @return The directory structure
     */
    public String getChilds(WebPage tpid, WebPage tpc, User user, Vector vctPath, int intLevel, int intWidth, String topicrec, int widthsize, SWBParamRequest paramsRequest) {

        Document dcmDom = base.getDom();

        StringBuffer sbfRet = new StringBuffer();
        try {

            SWBResourceURLImp url1 = new SWBResourceURLImp(null, base, tpid, SWBResourceURLImp.UrlType_RENDER);
            url1.setMode(url1.Mode_VIEW);
            url1.setAdminTopic(paramsRequest.getAdminTopic());
            Iterator<WebPage> it = sortByOrderedName(tpc.listChilds());
            while (it.hasNext()) {
                WebPage tpsub = it.next();
               if(!tpsub.isActive()||tpsub.isDeleted()||tpsub.isHidden()) continue;
                if (tpsub.getId() != null && user.haveAccess(tpsub)) {
                    if (vTopic.contains(tpsub)) {
                        break;
                    }
                    vTopic.addElement(tpsub);

                    String backg = "class=\"cerrado\""; 
                    if (tpid != null && tpsub.getId().equals(tpid.getId())) {
                        backg = " class=\"abierto selec\"";
                    } else if(tpid != null && tpsub.isParentof(tpid)){
                        backg ="class=\"abierto\"";
                    }

                    String tmpName =  tpsub.getDisplayName();
                    String tmpNameShort =  tpsub.getDisplayName();
                    if (null != tmpName && tmpName.trim().length() > 15) {
                        tmpNameShort = tmpName.substring(0, 14) + "...";
                    }
                    sbfRet.append("<li>");
                    url1.setTopic(tpsub);
                    sbfRet.append("<a " + backg + " href=\"" + url1 + "?reptp=" + tpsub.getId() + "\" title=\""+tmpName+"\">");
                    sbfRet.append( tmpNameShort + "</a>");
                    
                    if ((intLevel < intMaxLevel || (tpid != null && tpsub.getId().equals(tpid.getId()))
                            || vctPath.contains(tpsub.getId())) && sortByOrderedName(tpsub.listChilds()).hasNext()) {
                        sbfRet.append("<ul>");
                        sbfRet.append(getChilds(tpid, tpsub, user, vctPath, intLevel + 1, intWidth, "", widthsize + 5, paramsRequest));
                        sbfRet.append("</ul>");
                    }
                    sbfRet.append("</li>");
                    vTopic.removeElement(tpsub);
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in resource " + "TreeRepHtml:getChilds", e);
        }
        return sbfRet.toString();
    }

    /**
     * Calcula los t�picos padre de un t�pico.
     *
     * @param tpid El t�pico que solicita el recurso.
     * @return Regresa un objeto Vector que contiene los t�picos padre del
     * t�pico requerido.
     * @see infotec.topicmaps.Topic
     */
    public Vector getMapPath(WebPage tpid) {
        Vector vctPath = new Vector();
        if (!tpid.getWebSite().getHomePage().equals(tpid)) {
            Iterator<WebPage> aux = tpid.listVirtualParents();
            while (aux.hasNext()) {
                WebPage tp = aux.next();
                if(!tp.isActive()||tp.isDeleted()||tp.isHidden()) continue;
                vctPath.addElement(tp.getId());
                aux = tp.listVirtualParents();
                if (tpid.getWebSite().getHomePage().equals(tp)) {
                    break;
                }
            }
        }
        return vctPath;
    }

    /**
     * Calcula si un t�pico tiene t�picos hijo sin referencias c�clicas.
     *
     * @param tpid El t�pico que solicita el recurso.
     * @param tpsub El t�pico hijo de acuerdo al nivel de recursividad presente
     * en la generaci�n de la estructura jer�rquica.
     * @param vctPath El vector que almacena los t�picos padre del t�pico
     * requerido.
     * @return Regresa si el t�pico solicitado contiene o no t�picos hijo con
     * referencias c�clicas.
     * @see infotec.topicmaps.Topic
     */
    public boolean isMapParent(WebPage tpid, WebPage tpsub, Vector vctPath) {
        boolean bParent = false;
        Iterator<WebPage> hit = sortByOrderedName(tpsub.listChilds());
        if (hit.hasNext()) {
            do {
                WebPage htp = hit.next();
                if(!htp.isActive()||htp.isDeleted()||htp.isHidden()) continue;
                if (tpid != null) {
                    if (htp.getId() != null && !tpid.getId().equals(htp.getId()) && !vctPath.contains(htp.getId())) {
                        bParent = true;
                        break;
                    }
                } else {
                    if (htp.getId() != null && !vctPath.contains(htp.getId())) {
                        bParent = true;
                        break;
                    }
                }
            } while (hit.hasNext());
        } else {
            bParent = false;
        }

        return bParent;
    }

    /**
     * Get the directory tree of the repository
     *
     * @param request the input parameters
     * @param response the answer to the user request
     * @param user a WBUser object
     * @param topicrec Topic object
     * @param arguments a list of parameters
     * @param topic a topic object
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An application Framework exception
     * @return The directory structure
     */
    public String getDirs(HttpServletRequest request, HttpServletResponse response, User user, WebPage topicrec, HashMap arguments, WebPage topic, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        Document dcmDom = base.getDom();
        if (dcmDom == null) {
            throw new SWBResourceException("Dom nulo - getDirs()");
        }
        StringBuffer sbfRet = new StringBuffer();
        response.setContentType("text/html");
        SWBResourceURL url1 = paramsRequest.getRenderUrl();
        url1.setMode(url1.Mode_VIEW);
        url1.setParameter("repobj", "MoveDoc");
        if (request.getParameter("reptp_original") != null) {
            url1.setParameter("reptp_original", request.getParameter("reptp_original"));
        }
        if (request.getParameter("repiddoc") != null) {
            url1.setParameter("repiddoc", request.getParameter("repiddoc"));
        }
        String strUrl = url1.toString();
        try {
            WebSite tm = topic.getWebSite();

            WebPage tpid = null;
            Vector vctPath = new Vector();
            int intLevel = 4, intWidth = 10;
            String idhome = null;
            String dir = topic.getId();
            int widthsize = 15;
            String backg = "class=\"cerrado\""; 

            if (request.getParameter("reptp") != null && !request.getParameter("reptp").trim().equals("")) {
                tpid = tm.getWebPage(request.getParameter("reptp"));
                vctPath = getMapPath(tpid);
            }

            if (dir != null) {
                idhome = dir;
            }

            WebPage tpsite = null;
            if (idhome != null) {
                tpsite = tm.getWebPage(idhome);
                if (tpsite == null) {
                    tpsite = tm.getHomePage();
                }
            } else {
                tpsite = tm.getHomePage();
            }

            sbfRet.append("<ul>");
            backg = "class=\"cerrado\""; 
            if ((tpid != null && tpid.getId().equals(topic.getId())) || (tpid == null)) {
                backg = "class=\"abierto selec\"";
            } else if(tpid!=null&&tpid.isChildof(topic)) {
                backg ="class=\"abierto\"";
            }

            String tmpName = topic.getDisplayName(user.getLanguage());
            String tmpNameShort = topic.getDisplayName(user.getLanguage());
            if(null!=tmpName&&tmpName.trim().length()>15){
                tmpNameShort = tmpName.substring(0, 14)+"...";
            }
            
            sbfRet.append("<li   "+ backg +" >");
            sbfRet.append("<a href=\"" + strUrl + "&reptp=" + topic.getId() + "\" title=\""+tmpName+"\">");
            sbfRet.append( tmpNameShort);
            sbfRet.append("</a>");
            boolean cerrar = Boolean.FALSE;

            Iterator<WebPage> it = sortByOrderedName(tpsite.listChilds());
            
            if(it.hasNext()){
                sbfRet.append("<ul>");
                cerrar = Boolean.TRUE;
            }
            
            while (it.hasNext()) {
                intLevel = 1;
                WebPage tp = it.next();
                if(!tp.isActive()||tp.isDeleted()||tp.isHidden()) continue;
                if (tp.getId() != null) {
                    backg = "class=\"cerrado\""; 
                    if (tpid != null && tpid.getId().equals(tp.getId())) {
                        backg = " class=\"abierto selec\"";
                    } else if(tpid != null && tpid.isParentof(tp)){
                        backg ="class=\"abierto\"";
                    }
                    
                    tmpName = tp.getDisplayName(user.getLanguage());
                    tmpNameShort = tp.getDisplayName(user.getLanguage());
                    if (null != tmpName && tmpName.trim().length() > 15) {
                        tmpNameShort = tmpName.substring(0, 14) + "...";
                    }
            
                    sbfRet.append("<li>");
                    sbfRet.append("<a " + backg + " href=\"" + strUrl + "&reptp=" + tp.getId() + "\" title=\""+tmpName+"\">");
                    sbfRet.append(tmpNameShort);
                    sbfRet.append(" </a>");
                    if ((intLevel < intMaxLevel || (tpid != null && tp.getId().equals(tpid.getId()))
                            || vctPath.contains(tp.getId())) && sortByOrderedName(tp.listChilds()).hasNext()) {
                        sbfRet.append("<ul>");
                        sbfRet.append(getChilds2(tpid, tp, user, vctPath, intLevel + 1, intWidth, "", widthsize, paramsRequest, 0, request));
                        sbfRet.append("</ul>");
                    }
                    sbfRet.append("</li>");
                }
            }
            if(cerrar){
                sbfRet.append("</ul>");
                cerrar = Boolean.TRUE;
            }
            sbfRet.append("</li>");
            sbfRet.append("</ul>");
            sbfRet.append("\n<script type=\"text/javascript\">");
            sbfRet.append("\n function enviar(frm){");
            sbfRet.append("\n   ");
            sbfRet.append("\n    window.opener.regresa(frm.repfiddoc.value,frm.reptp_original.value,frm.reptp.value,frm.repobj.value,frm.repiddoc.value);");
            sbfRet.append("\n    window.close();");
            sbfRet.append("\n   }");
            sbfRet.append("\n</script>");
            SWBResourceURL urlAction = paramsRequest.getRenderUrl();
            urlAction.setCallMethod(urlAction.Call_CONTENT);
            if (user.isSigned()) {
                sbfRet.append("<form name=\"frmSnd\" action=\"" + urlAction.toString() + "\" method=post>");
            }
            sbfRet.append("<input type=hidden name=\"repfiddoc\" value=\"movedoctodir\">");
            sbfRet.append("<input type=hidden name=\"reptp_original\" value=\"" + request.getParameter("reptp_original") + "\">");
            sbfRet.append("<input type=hidden name=\"reptp\" value=\"" + request.getParameter("reptp") + "\">");
            sbfRet.append("<input type=hidden name=\"repobj\" value=\"MoveDoc\">");
            sbfRet.append("<input type=hidden name=\"repiddoc\" value=\"" + request.getParameter("repiddoc") + "\">");
            sbfRet.append("<table width=100% cellpadding=0 cellspacing=0>");
            sbfRet.append("<tr><td align=center><HR noshade size=1>");
            if (user.isSigned()) {
                sbfRet.append("<div id=\"diropcion\">");
                sbfRet.append("<a href=\"#\"  class=\"crear\"  name=\"mover\" onclick=\"if(confirm('" + paramsRequest.getLocaleString("msgConfirmShureMove") + "?')) {enviar(frmSnd);} else { return(false);}\"><span>" + paramsRequest.getLocaleString("msgBTNMove") + "</span></a>&nbsp;");
                sbfRet.append("<a href=\"#\"  class=\"crear\" name=\"btn_cancel\" onclick=\"javascript:window.close();\"><span>" + paramsRequest.getLocaleString("msgBTNCancel") + "</span></a>");
                sbfRet.append("</div>");
            }
            sbfRet.append("</td></tr>");
            sbfRet.append("</table>");
            if (user.isSigned()) {
                sbfRet.append("</form>");
            }
        } catch (Exception e) {
            Repository.log.error("Error in resource " + "TreeRepHtml:getHtml", e);
        }
        return sbfRet.toString();
    }

    /**
     * Shows the directory tree for move a selected file
     *
     * @param tpid Topic object thats represents a directory
     * @param tpc Topic object thats represents a directory
     * @param user The WBUser object in session
     * @param vctPath A Vector object that hold the topic parents
     * @param intLevel Level of the user
     * @param intWidth The width size value
     * @param topicrec Topic object thats represents a directory
     * @param widthsize The image size
     * @param paramsRequest A list of objects (topic, user, action, ...)
     * @param direct An integer value
     * @param request Rhe input parameters
     * @return The directory structure
     */
    public String getChilds2(WebPage tpid, WebPage tpc, User user, Vector vctPath, int intLevel, int intWidth, String topicrec, int widthsize, SWBParamRequest paramsRequest, int direct, HttpServletRequest request) {

        StringBuffer sbfRet = new StringBuffer();
        try {
            String strUrl = tpc.getWebSite().getHomePage().getUrl();
            SWBResourceURL url1 = paramsRequest.getRenderUrl();
            url1.setMode(url1.Mode_VIEW);
            url1.setParameter("repobj", "MoveDoc");
            if (request.getParameter("reptp_original") != null) {
                url1.setParameter("reptp_original", request.getParameter("reptp_original"));
            }
            if (request.getParameter("repiddoc") != null) {
                url1.setParameter("repiddoc", request.getParameter("repiddoc"));
            }
            strUrl = url1.toString();
            Iterator<WebPage> it =sortByOrderedName(tpc.listChilds());

            while (it.hasNext()) {
                WebPage tpsub = it.next();
                if(!tpsub.isActive()||tpsub.isDeleted()||tpsub.isHidden()) continue;

                if (tpsub.getId() != null && user.haveAccess(tpsub)) {
                    if (vTopic.contains(tpsub)) {
                        break;
                    }
                    vTopic.addElement(tpsub);
                   String backg = "class=\"cerrado\""; 
                    if (tpid != null && tpsub.getId().equals(tpid.getId())) {
                        backg = " class=\"abierto selec\"";
                    } else if(tpid != null && tpsub.isParentof(tpid)){
                        backg ="class=\"abierto\"";
                    }
                    String tmpName = tpsub.getDisplayName();
                    String tmpNameShort = tpsub.getDisplayName();
                    if (null != tmpName && tmpName.trim().length() > 15) {
                        tmpNameShort = tmpName.substring(0, 14) + "...";
                    }

                    sbfRet.append("<li>");
                    sbfRet.append("<a " + backg + " href=\"" + strUrl + "&reptp=" + tpsub.getId() + "\" title=\""+tmpName+"\">");
                    sbfRet.append(tmpNameShort);
                    sbfRet.append("</a>");
                    if ((intLevel < intMaxLevel || (tpid != null && tpsub.getId().equals(tpid.getId()))
                            || vctPath.contains(tpsub.getId())) && sortByOrderedName( tpsub.listChilds()).hasNext()) {
                        sbfRet.append("<ul>");
                        sbfRet.append(getChilds2(tpid, tpsub, user, vctPath, intLevel + 1, intWidth, "", widthsize + 10, paramsRequest, 1, request));
                        sbfRet.append("</ul>");
                    }
                    sbfRet.append("</li>");
                    vTopic.removeElement(tpsub);
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in resource " + "TreeRepHtml:getChilds", e);
        }
        return sbfRet.toString();
    }

    /**
     * Sort by rank.
     *
     * @param it the it
     * @param ascendente the ascendente
     * @return the sets the
     */
    private static Iterator<WebPage> sortByOrderedName(Iterator<WebPage> it) {
        TreeSet<WebPage> set = null;
        if (it == null) {
            return null;
        }

        set = new TreeSet(new Comparator() {
            public int compare(Object o1, Object o2) {
                String lang = "es";
                String name1;
                String name2;
                name1 = ((WebPage) o1).getSortName();
                name2 = ((WebPage) o2).getSortName();

                int ret = 0;
                if (name1 == null) {
                    name1 = ((WebPage) o1).getDisplayName(lang);
                }

                if (name2 == null) {
                    name2 = ((WebPage) o1).getDisplayName(lang);
                }
                if ((name1 != null) && (name2 != null)) {
                    ret = name1.compareToIgnoreCase(name2);

                    if (ret == 0) {
                        ret = -1;
                    }
                } else {
                    ret = -1;
                }
                return ret;
            }
        });

        while (it.hasNext()) {
            set.add(it.next());
        }

        return set.iterator();
    }
}
