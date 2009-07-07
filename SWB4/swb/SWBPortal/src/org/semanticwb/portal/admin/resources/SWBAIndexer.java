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
 * direccion electronica:
 *
 *                                          http://www.webbuilder.org.mx
 *
 * SWBAIndexer.java
 *
 */
package org.semanticwb.portal.admin.resources;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.indexer.SWBIndexer;

/** Recurso de WB para la administracion de los Indexadores
 *
 * @author  Juan Antonio Fernandez Arias
 */
public class SWBAIndexer extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBAIndexer.class);
    private Resource base = null;

    /** Creates a new instance of SWBAIndexer */
    public SWBAIndexer() {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        // Mostrar la lista de indexadores existentes
        HashMap hmtmind = new HashMap();

        base = getResourceBase();
        String act = request.getParameter("act");
        String indexName = request.getParameter("indexName");
        String status = request.getParameter("bar_status");
        if (null == act) {
            act = "";
        }

//        if("".equals(act))
//        {
//            SWBResourceURL url = paramRequest.getRenderUrl();
//            url.setParameter("act","tmList");
//            out.println("<form class=box action=\""+url+"\" method=\"post\">");
//            out.println("<table border=0 cellpadding=5 cellspacing=0 width=100%>");
//            out.println("<tr>");
//            out.println("<td class=tabla colspan=2>"+"Lista de indexadores"+"</td>");
//            out.println("</tr>");
//            out.println("<tr>");
//            out.println("<td class=datos align=\"right\"></td>");
//            out.println("<td><select name=\"indexName\">");
//            out.println("<option value=\"0\">Selecciona un indexador</option>");
//            HashMap hmind = SWBPortal.getIndexMgr().getIndexers();
//            Iterator iteind = hmind.keySet().iterator();
//            while(iteind.hasNext())
//            {
//                String key = (String) iteind.next();
//                SWBIndexer obj = (SWBIndexer) hmind.get(key);
//                out.println("<option value=\""+key+"\">"+obj.getName()+"</option>");
//            }
//            out.println("</select>");
//            out.println("</td>");
//            out.println("</tr>");
//            out.println("<tr>");
//            out.println("<td colspan=2 align=right><HR noshade size=\"1\">");
//            out.println("<input type=submit class=boton value=\"Enviar\">");
//            out.println("</td></tr>");
//            out.println("</table></form>");
//        }
        //else if("tmList".equals(act))
        SWBIndexer ind = SWBPortal.getIndexMgr().getDefaultIndexer();
        if ("".equals(act)&&ind.getIndexSize()==0) {
            indexName = SWBPortal.getIndexMgr().getDefaultIndexer().getName();
            if (null != indexName) {
                //indexName = SWBPortal.getIndexMgr().getDefaultIndexer().getName();
                hmtmind = loadHMINDTM(indexName);
                

                //out.println("<script type=\"javascript\">include_dom('" + SWBPlatform.getContextPath() + "/swbadmin/js/indexer.js');</script>");
                out.println("<div class=\"swbform\">");
                out.println("<form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/frmInd\" name=\"" + getResourceBase().getId() + "/frmInd\" action=\"\" method=\"post\">");
                out.println("<fieldset>");
                out.println("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">");
                out.println("<tr>");
                out.println("<td>" + "Lista de sitios asociados al indexador" + " - " + indexName + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">");
                Iterator ittm = SWBContext.listWebSites();
                while (ittm.hasNext()) {
                    WebSite tmp = (WebSite) ittm.next();
//                    if(indexName.equals(tmp.getDbdata().getIndexer()) && SWBAdmFilterMgr.getInstance().haveAccess2WebSite(user,tmp.getId())>0)
                    {
                        out.println("<tr><td align=right >");
                        String tmid = tmp.getId();
                        String str_check = "";
                        if (null != hmtmind && hmtmind.get(tmid) != null) {
                            str_check = "checked";
                        }
                        out.println("<input type=\"checkbox\" name=\"tmids\" value=\"" + tmid + "\" " + str_check + ">");
                        out.println("</td><td align=left>");
                        out.println(tmp.getDisplayTitle(user.getLanguage()));
                        out.println("</td>");
                        out.println("</tr>");
                    }
                }

                out.println("<tr><td align=\"right\" width=\"150\">");
                out.println("Reemplazar Indice" + ":");
                out.println("</td><td align=\"left\">");
                out.println("<input type=\"checkbox\" name=\"reemplazar\" value=\"replaceIndex\">");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr><td align=\"right\"  width=\"150\">");
                out.println("Indice bloqueado" + ":");
                out.println("</td><td align=\"left\" >");
                out.println(ind.isLocked() ? "Si" : "No");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr><td align=\"right\"  width=\"150\">");
                out.println("Archivos por indexar" + ":");
                out.println("</td><td align=\"left\" >");
                out.println(ind.getIndexSize());
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</td>");
                out.println("</tr>");

                SWBResourceURL urlInd = paramRequest.getActionUrl();
                urlInd.setAction("indexar");
                SWBResourceURL urldel = paramRequest.getActionUrl();
                urldel.setAction("eliminar");
                SWBResourceURL urldes = paramRequest.getActionUrl();
                urldes.setAction("desbloquear");
                SWBResourceURL urlOpt = paramRequest.getActionUrl();
                urlOpt.setAction("optimizar");

                out.println("</table>");
                out.println("<input type=\"hidden\" name=\"indexID\" value=\"" + indexName + "\">");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"enviar('" + getResourceBase().getId() + "/frmInd','" + urlInd + "'); return false;\">Indexar</button>");
                //out.println("<input name=\"btn_index\" onclick=\"enviar(frmInd,'" + urlInd + "');\" type=button class=boton value=\"" + "Indexar" + "\">");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"enviar('" + getResourceBase().getId() + "/frmInd','" + urldel + "'); return false;\">Eliminar</button>");
                //out.println("<input name=\"btn_del\" onclick=\"enviar(frmInd,'" + urldel + "');\" type=button class=boton value=\"" + "Eliminar" + "\">");
                if (ind.isLocked()) {
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"enviar('" + getResourceBase().getId() + "/frmInd','" + urldes + "'); return false;\">Desbloquear</button>");
                //out.println("<input name=\"btn_des\" onclick=\"enviar(frmInd,'" + urldes + "');\" type=button class=boton value=\"" + "Desbloquear" + "\">");
                }
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"enviar('" + getResourceBase().getId() + "/frmInd','" + urlOpt + "'); return false;\">Optimizar</button>");
                //out.println("<input name=\"btn_opt\" onclick=\"enviar(frmInd,'" + urlOpt + "');\" type=button class=boton value=\"" + "Optimizar" + "\">");
                out.println("</fieldset>");
                out.println("</form>");
                out.println("</div>");
            }
        }
        else if(ind.getIndexSize()>0)
        {
            StringBuffer status_msg = new StringBuffer();
            status_msg.append("\n<div id=\"" + base.getId() + "/statusIndex\" class=\"swbform\">");
            status_msg.append("\n<fieldset>");
            //status_msg.append("\n<font face=\"arial, verdana\" size=\"2\"><b>" + indexName + "</b> - indexando. Archivos faltantes: <b><>" + ind.getIndexSize()+ "</></b></font>");
            status_msg.append("\n<label for=\"indice\">"+indexName+",  - indexando. Archivos faltantes:</label>");
            status_msg.append("\n<input type=\"text\" name=\"" + base.getId() + "/indice\" value=\""+ind.getIndexSize()+"\" dojoType=\"dijit.form.TextBox\" trim=\"true\" id=\"" + base.getId() + "/indice\"  />");
            status_msg.append("\n</fieldset>");
            status_msg.append("\n</div>");
            out.println(status_msg.toString());
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setMode("doStatus");
            url.setCallMethod(SWBResourceURL.Call_DIRECT);
            url.setWindowState(url.WinState_MAXIMIZED);
            out.println("<script type=\"text/javascript\">");
            out.println("  indexcheck('"+url+"','" + base.getId() + "/indice');");
            out.println("</script>");
        }

        

    }

     public void doStatus(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        HashMap hmtmind = new HashMap();
        SWBIndexer ind = SWBPortal.getIndexMgr().getDefaultIndexer();
        base = getResourceBase();
        String indexName = ind.getName();
        if (null != indexName) {
            hmtmind = loadHMINDTM(indexName);
            StringBuffer status_msg = new StringBuffer();
            status_msg.append(ind.getIndexSize());
            //System.out.println("size indx:"+ind.getIndexSize());
            out.println(status_msg.toString());
        }

    }

    public HashMap loadHMINDTM(String indexName) {
        base = getResourceBase();
        HashMap hm_ret = new HashMap();
        String ind_tms = base.getAttribute("index" + indexName, "");

        log.debug("loadHMINDTM: " + ind_tms);

        if (!"".equals(ind_tms)) {
            StringTokenizer str_token = new StringTokenizer(ind_tms, "|");
            while (str_token.hasMoreTokens()) {
                String token = str_token.nextToken();
                hm_ret.put(token, token);
            }

        }
        return hm_ret;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        base = getResourceBase();
        String act = response.getAction();

        if (null == act) {
            act = "";
        }
        String newInd = request.getParameter("reemplazar");
        String indexName = request.getParameter("indexID");
        indexName = SWBPortal.getIndexMgr().getDefaultIndexer().getName();
        HashMap hmtmind = null;
        if (null != indexName) {
            hmtmind = loadHMINDTM(indexName);
        }

        if ("indexar".equals(act)) {
            if (null != newInd && "replaceIndex".equals(newInd)) {
                SWBIndexer tmindexer = SWBPortal.getIndexMgr().getDefaultIndexer();
                tmindexer.reset();
                hmtmind = new HashMap();
            }

            String[] tmarr = request.getParameterValues("tmids");
            if (null != tmarr) {
                SWBIndexer tmindexer = SWBPortal.getIndexMgr().getDefaultIndexer();
                String tmids = "";
                for (int i = 0; i < tmarr.length; i++) {
                    String tmid = tmarr[i];
                    if (null != hmtmind && hmtmind.get(tmid) == null) {
                        WebSite tm_o = SWBContext.getWebSite(tmid);
                        tmindexer.indexWebSite(tm_o);
                    }
                    if (null != hmtmind && hmtmind.get(tmid) != null) {
                        hmtmind.remove(tmid);
                    }
                    log.debug("ws:" + tmid + ", cadena: " + tmids);
                    tmids += tmid;
                    if (i < tmarr.length) {
                        tmids += "|";
                    }
                }
                if (null != hmtmind && !hmtmind.isEmpty()) // QUITANDO TM DEL INDICE
                {
                    Iterator ite = hmtmind.keySet().iterator();
                    while (ite.hasNext()) {
                        String tm_del = (String) ite.next();
                        tmindexer.removeWebSite(tm_del); //

                    }
                }
                log.debug("cadena: " + tmids + ", name: " + indexName);
                base.setAttribute("index" + indexName, tmids);
            } else {
                base.removeAttribute("index" + indexName);
            }
            response.setRenderParameter("act", "");
            response.setRenderParameter("status","go");

        }

        if ("eliminar".equals(act)) {
            if (null != indexName && "".equals(indexName)) {
                SWBIndexer tmindexer = SWBPortal.getIndexMgr().getDefaultIndexer();
                tmindexer.remove();  // eliminar �ndice
            }
            base.removeAttribute("index" + indexName);
            response.setRenderParameter("act", "");
        }

        if ("desbloquear".equals(act)) {
            if (null != indexName && "".equals(indexName)) {
                SWBIndexer tmindexer = SWBPortal.getIndexMgr().getDefaultIndexer();
                if (tmindexer.isLocked()) {
                    tmindexer.unLock();  // desbloquear indice
                }
            }
            response.setRenderParameter("act", "");
        }

        if ("optimizar".equals(act)) {
            if (null != indexName && "".equals(indexName)) {
                SWBIndexer tmindexer = SWBPortal.getIndexMgr().getDefaultIndexer();
                tmindexer.optimize();
            }
            response.setRenderParameter("act", "");
        }
        if (null != indexName) {
            response.setRenderParameter("indexName", indexName);
        }

        try {
            base.updateAttributesToDB();
        } catch (Exception e) {
            log.error("Error al actualizar datos del recurso SWBAIndexer.", e);
        }

    }




    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("doStatus"))
        {
            doStatus(request,response,paramRequest);
        }
        else
        {
            super.processRequest(request, response, paramRequest);
        }
    }


}
