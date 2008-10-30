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


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/** Esta clase se encarga de desplegar y administrar un texto promocional con una
 * imagen opcional bajo ciertos criterios(configuraci�n de recurso). Es un recurso
 * que viene de la versi�n 2 de WebBuilder y puede ser instalado como recurso de
 * estrategia o recurso de contenido.
 *
 * This class displays and administrate a promocional text with an optional image
 * under criteria like configuration. This resource comes from WebBuilder 2 and can
 * be installed as content resource or a strategy resource.
 * @author Jorge Alberto Jim�nez Sandoval (JAJS)
 */
public class Promo extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(Promo.class);
    
    String webWorkPath= "/work";
    
    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Portlet base)
    {
        try 
        {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
    }    
    
    /**
     * Genera el html final del recurso
     * @param request
     * @param response
     * @param reqParams
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        StringBuffer ret = new StringBuffer("");
        Portlet base=getResourceBase();
        try 
        {
            String position = base.getAttribute("pos", "3").trim();
            
            ret.append("<table border=0  width=\"99%\"> \n");
            ret.append("<tr> \n");
            if (!"".equals(base.getAttribute("title", "").trim())) {
                if ("5".equals(position)) {
                    ret.append("<td colspan=2> \n");
                    ret.append(base.getAttribute("title").trim());
                    ret.append("</td></tr><tr><td valign=top> \n");
                }else {
                    ret.append("<td> \n");
                    ret.append(base.getAttribute("title").trim());
                    ret.append("<br> \n");
                }
            }else {
                if ("5".equals(position)) {
                    ret.append("<td valign=top> \n");
                }else {
                    ret.append("<td> \n");
                }
            }
            if (!"".equals(base.getAttribute("url", "").trim())) {
                ret.append(getUrlHtml(paramRequest, base));
            }
            if (!"".equals(base.getAttribute("img", "").trim())) {
                ret.append(getImgPromo(paramRequest, base));
            }else {
                ret.append(getTextHtml(base));
            }
            if (!"".equals(base.getAttribute("url", "").trim())) {
                ret.append("</a> \n");
            }
            ret.append("</td></tr></table> \n");            
        } 
        catch (Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        PrintWriter out = response.getWriter();
        out.println(ret.toString());
        out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin promo</a>");
    }
    
    /**
     * Obtiene las ligas de redireccionamiento del promocional
     */    
    private String getUrlHtml(SWBParamRequest reqParams, Portlet base)
    {
        StringBuffer ret = new StringBuffer("");
        SWBResourceURL wburl=reqParams.getActionUrl();
        ret.append("<a href=\"" + wburl.toString() + "\"");
        if ("0".equals(base.getAttribute("uline", "0").trim())) {
            ret.append(" style=\"TEXT-DECORATION: none\"");
        }
        if ("1".equals(base.getAttribute("target", "0").trim())) {
            ret.append(" target=\"_newprm\"");
        }
        ret.append("> \n");
        return ret.toString();
    }
    
    /**
     * Obtiene la imagen del promocional asi como su posicionamiento (en caso de
     * existir)
     */    
    private String getImgPromo(SWBParamRequest reqParams, Portlet base)
    {
        StringBuffer ret = new StringBuffer("");
        String position = base.getAttribute("pos", "3").trim();
        String img=base.getAttribute("img", "").trim();
        String url=base.getAttribute("url", "").trim();
        
        if ("1".equals(position))
        {
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append(" align=left vspace=1 hspace=5> \n");
            }
            ret.append(getTextHtml(base));
        }
        else if ("2".equals(position))
        {
            ret.append(getTextHtml(base));
            if (!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td><td> \n");
            if (!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append(" align=left vspace=1 hspace=5> \n");
            }
        }
        else if ("3".equals(position))
        {
            ret.append("<center> \n");
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append(" align=left> \n");
            }
            if (!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</center></td></tr><tr><td> \n");
            if (!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getTextHtml(base));
        }
        else if ("4".equals(position))
        {
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append(" align=right vspace=1 hspace=\"10\"> \n");
            }
            ret.append(getTextHtml(base));
        }
        else if ("5".equals(position))
        {
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append("> \n");
            }
            if (!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td><td> \n");
            if (!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getTextHtml(base));
        }
        else if ("6".equals(position))
        {
            ret.append(getTextHtml(base));
            if (!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td></tr><tr><td> \n");
            if (!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append("> \n");
            }
        }
        else if ("7".equals(position))
        {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append(" align=left vspace=1 hspace=5> \n");
            }
        }
        else if ("8".equals(position))
        {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if (!img.endsWith(".swf")) {
                ret.append(" align=right vspace=1 hspace=\"10\"> \n");
            }
        }
        return ret.toString();
    }

    /**
     * Obtiene el html de la imagen
     */    
    private String getImgHtml(SWBParamRequest reqParams, Portlet base)
    {
        StringBuffer ret = new StringBuffer("");
        String width=base.getAttribute("width", "").trim();
        String height=base.getAttribute("height", "").trim();
        if (base.getAttribute("img", "").trim().endsWith(".swf"))
        {
            ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\"");
            if (!"".equals(width)) ret.append(" width=\"" + width + "\"");
            if (!"".equals(height)) ret.append(" height=\"" + height + "\"");
            ret.append("> \n");
            ret.append("<param name=movie value=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"> \n");
            ret.append("<param name=quality value=high> \n");
            ret.append("<embed src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim());
            ret.append("\" quality=high pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\" type=\"application/x-shockwave-flash\"");
            if (!"".equals(width)) ret.append(" width=\"" + width + "\"");
            if (!"".equals(height)) ret.append(" height=\"" + height + "\"");
            ret.append("> \n");
            ret.append("</embed> \n");
            ret.append("</object> \n");
        }
        else
        {
            String border=(String)reqParams.getArguments().get("border");
            ret.append("<img alt=\"\" src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
            if (border != null && !"".equals(border.trim())) {
                ret.append(" border=\""+ border +"\"");
            }else {
                ret.append(" border=\"0\"");
            }
            if (!"".equals(width)) {
                ret.append(" width=\""+ width +"\"");
            }
            if (!"".equals(height)) {
                ret.append(" height=\""+ height +"\"");
            }
       }
        return ret.toString();
    }

    /**
     * Obtiene el texto del promocional ya armado
     */    
    private String getTextHtml(Portlet base)
    {
        StringBuffer ret = new StringBuffer("");
        if (!"".equals(base.getAttribute("text", "").trim())) 
        {
            if (!"".equals(base.getAttribute("textcolor", "").trim())) 
                ret.append("<font color=\""+ base.getAttribute("textcolor").trim() +"\"> \n");
            ret.append(base.getAttribute("text").trim());
            if (!"".equals(base.getAttribute("textcolor", "").trim())) ret.append("</font> \n");
        }
        return ret.toString();
    }

    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException 
    {
        Portlet base=getResourceBase();
        //base. addHit(request, response.getUser(), response.getTopic());
        String url = base.getAttribute("url", "").trim();
        if (!url.equals("")) response.sendRedirect(url);
    }    
}
