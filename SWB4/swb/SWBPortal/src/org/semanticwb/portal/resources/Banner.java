/**  
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
**/ 
 


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
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
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
        //System.out.println("Banner.doView");
        StringBuffer ret = new StringBuffer("");
        Resource base = getResourceBase();
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
        out.print(ret.toString());        
    }

    /**
     * Metodo para hacer operaciones
     */
    @Override
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        Resource base=getResourceBase();
        base.addHit(request, response.getUser(), response.getWebPage());
        String url = base.getAttribute("url", "").trim();
        if (!url.equals("")) response.sendRedirect(url);
    }

}
