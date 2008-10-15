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
 * Banner.java
 *
 * Created on 28 de octubre de 2004, 15:10
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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;


/** Objeto que se encarga de desplegar y administrar un banner bajo ciertos
 * criterios(configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer a banner under certain 
 * criteria (resource configuration).
 *
 * @author : Jorge Alberto Jim�nez
 * @version 1.0
 */

public class Banner extends GenericAdmResource 
{
    private static Logger log = SWBUtils.getLogger(Banner.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        Portlet base = getResourceBase();
        try {
            String local = base.getAttribute("txtLocal", "0").trim();
            if (local.equals("0")) {
                String img = base.getAttribute("img", "").trim();
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
                            ret.append("/>");
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
}
