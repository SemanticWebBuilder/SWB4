package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
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
 * 
 * @modified by Carlos Ramos (CIRI)
 */

public class Promo extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(Promo.class);
    
    String webWorkPath= "/work";
    
    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  
        }
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
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();
        
        try {
            String position = base.getAttribute("pos", "3").trim();

            System.out.println("promo...");

            out.println("<div id=\"swb--promo\">");

            System.out.println("out="+out);

            out.println("<table border=\"0\"  width=\"99%\"> \n");
            out.println("<tr> \n");
            if(!"".equalsIgnoreCase(base.getAttribute("title", ""))) {
                if ("5".equalsIgnoreCase(position)) {
                    out.println("<td colspan=\"2\"> \n");
                    out.println("<h1>"+base.getAttribute("title")+"</h1>");
                    out.println("</td> \n");
                    out.println("</tr> \n");
                    out.println("<tr> \n");
                    out.println("<td align=\"justify\" valign=\"top\"> \n");
                }else {
                    out.println("<td align=\"justify\"> \n");
                    out.println("<h1>"+base.getAttribute("title")+"</h1>");
                }
            }else {
                if ("5".equalsIgnoreCase(position)) {
                    out.println("<td align=\"justify\" valign=\"top\"> \n");
                }else {
                    out.println("<td align=\"justify\"> \n");
                }
            }
            if (!"".equalsIgnoreCase(base.getAttribute("url", ""))) {
                out.println(getUrlHtml(paramRequest, base));
            }
            if (!"".equalsIgnoreCase(base.getAttribute("img", ""))) {
                out.println(getImgPromo(paramRequest, base));
            }else {
                out.println(getTextHtml(base));
            }
            if (!"".equalsIgnoreCase(base.getAttribute("url", ""))) {
                out.println("</a>\n");
            }
            out.println("</td> \n");
            out.println("</tr> \n");
            out.println("</table> \n");
            out.println("</div>");
        }catch (Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }        
        out.flush();
    }
    
    /**
     * Obtiene las ligas de redireccionamiento del promocional
     */    
    private String getUrlHtml(SWBParamRequest paramRequest, Resource base) {
        StringBuffer ret = new StringBuffer("");
        SWBResourceURL wburl = paramRequest.getActionUrl();
        
        ret.append("<a href=\"" + wburl.toString() + "\"");
        if("0".equals(base.getAttribute("uline", "0").trim())) {
            ret.append(" style=\"text-decoration:none;\"");
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
    private String getImgPromo(SWBParamRequest reqParams, Resource base) {
        StringBuffer ret = new StringBuffer("");
        String position = base.getAttribute("pos", "3").trim();
        String img=base.getAttribute("img", "").trim();
        String url=base.getAttribute("url", "").trim();
        
        if("1".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
            ret.append(getTextHtml(base));
        }else if("2".equals(position)) {
            ret.append(getTextHtml(base));
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
        }else if("3".equals(position)) {
            ret.append("<center> \n");
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ///ret.append("</div>");
            }
            ret.append("</center> \n");
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getTextHtml(base));
        }else if ("4".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
            ret.append(getTextHtml(base));
        }else if("5".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getTextHtml(base));
        }else if("6".equals(position)) {
            ret.append(getTextHtml(base));
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
        }else if("7".equals(position)) {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
        }else if("8".equals(position)) {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
                ////ret.append("<h6>"+base.getAttribute("caption", "")+"</h6>");
                ////ret.append("</div>");
            }
        }
        return ret.toString();
    }

    /**
     * Obtiene el html de la imagen
     */    
    private String getImgHtml(SWBParamRequest paramRequest, Resource base) {
        StringBuffer ret = new StringBuffer("");
        String width=base.getAttribute("width", "");
        String height=base.getAttribute("height", "");

        if(base.getAttribute("img", "").trim().endsWith(".swf")) {
            ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\"");
            if(!"".equals(width)) {
                ret.append(" width=\"" + width + "\"");
            }
            if(!"".equals(height)) {
                ret.append(" height=\"" + height + "\"");
            }
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
        }else {
            String border = (String)paramRequest.getArguments().get("border");
            ////ret.append("<div>");
            ret.append("<img alt=\"\" src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
            if(border != null && !"".equals(border.trim())) {
                ret.append(" border=\""+ border +"\"");
            }else {
                ret.append(" border=\"0\"");
            }
            if(!"".equals(width)) {
                ret.append(" width=\""+ width +"\"");
            }
            if(!"".equals(height)) {
                ret.append(" height=\""+ height +"\"");
            }
       }
        return ret.toString();
    }

    /**
     * Obtiene el texto del promocional ya armado
     */    
    private String getTextHtml(Resource base) {
        StringBuffer ret = new StringBuffer("");
        if(!"".equals(base.getAttribute("text", ""))) {            
            if(!"".equals(base.getAttribute("textcolor", ""))) {
                ret.append("<font color=\""+base.getAttribute("textcolor")+"\"> \n");
            }
            ////ret.append("<h2 style=\"text-align:justify\">" + base.getAttribute("text").trim() + "</h2>");
            ret.append(base.getAttribute("text")+"\n");
            if(!"".equals(base.getAttribute("textcolor", ""))) {
                ret.append("</font> \n");
            }
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
        Resource base=getResourceBase();
        //base. addHit(request, response.getUser(), response.getTopic());
        String url = base.getAttribute("url", "").trim();
        if (!url.equals("")) response.sendRedirect(url);
    }    
}
