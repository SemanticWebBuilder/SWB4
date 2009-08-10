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
        //System.out.println("setResourceBase");
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  
        }
    }    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //System.out.println("doView");
        response.setContentType("text/html; charset=utf-8");
        Resource base=getResourceBase();
        String out;

        String cssClass = base.getAttribute("cssClass");
        if(cssClass == null) {
            out = renderWithStyle();
        }else {
            out = render();
        }
        
        //System.out.println("out");
        PrintWriter pw = response.getWriter();
        //pw.println("-ini-");
        pw.println(out);
        //pw.println("-end-");
        pw.flush();
    }

    private String renderWithStyle() {
        StringBuilder out = new StringBuilder();
        Resource base=getResourceBase();

        int width;
        try {
            width = Integer.parseInt(base.getAttribute("width","0"));
        }catch(NumberFormatException nfe) {
            width = 0;
        }
        int height;
        try {
            height = Integer.parseInt(base.getAttribute("height","0"));
        }catch(NumberFormatException nfe) {
            height = 0;
        }

        String textcolor = base.getAttribute("textcolor");

        String title = base.getAttribute("title");
        String titleStyle = base.getAttribute("titleStyle","");

        String subtitle = base.getAttribute("subtitle");
        String subtitleStyle = base.getAttribute("subtitleStyle","");

        String imgfile = base.getAttribute("imgfile");
        String caption = base.getAttribute("caption");
        String captionStyle = base.getAttribute("captionStyle","");
        int imgWidth;
        try {
            imgWidth = Integer.parseInt(base.getAttribute("imgWidth"));
        }catch(NumberFormatException nfe) {
            imgWidth = 150;
        }
        int imgHeight;
        try {
            imgHeight = Integer.parseInt(base.getAttribute("imgHeight"));
        }catch(NumberFormatException nfe) {
            imgHeight = 180;
        }

        String text = base.getAttribute("text");
        String textStyle = base.getAttribute("textStyle","");

        String more = base.getAttribute("more");
        String moreStyle = base.getAttribute("moreStyle","");
        String url = base.getAttribute("url");

        int imgPos;
        try {
            imgPos = Integer.parseInt(base.getAttribute("imgPos","1"));
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\"swb-promo\" style=\"");
            if(textcolor != null) {
                out.append("color:"+textcolor+";");
            }
            if(width>0)  {
                out.append("width:"+width+"px;");
            }
            if(height>0) {
                out.append("height:"+height+"px;");
            }
            out.append("\">");

            //title
            if(title != null) {
                out.append("<h1 style=\""+titleStyle+"\"><span> \n");
                out.append(title);
                out.append("</span></h1> \n");
            }

            //image
            String margin = "";
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                if(imgPos == 3) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6 style=\""+captionStyle+"\"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    margin = "margin-right:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 4) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6 style=\""+captionStyle+"\"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    margin = "margin-left:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 2) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px;\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6 style=\""+captionStyle+"\"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else if(imgPos == 1) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px;\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6 style=\""+captionStyle+"\"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else if(imgPos == 5) {
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6 style=\""+captionStyle+"\"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else {
                    imgPos = 6;
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6 style=\""+captionStyle+"\"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2 style=\""+subtitleStyle+"\"><span>"+subtitle+"</span></h2> \n");
            }

            if( base.getAttribute("more")==null ) {
                //texto
                out.append("<p style=\"text-align:justify;"+margin+"\"> \n");
                if(url != null) {
                    out.append("<a href=\""+url+"\" style=\""+textStyle);
                    if("0".equalsIgnoreCase(base.getAttribute("uline", "0"))) {
                        out.append(" ;text-decoration:none;");
                    }
                    if ("1".equalsIgnoreCase(base.getAttribute("target", "0").trim())) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append("\"> \n");
                    out.append(text);
                    out.append("\n</a> \n");
                }else {
                    out.append("<span style=\""+textStyle+"\"> \n");
                    out.append(text);
                    out.append("</span> \n");
                }
                out.append("</p> \n");
            }else {
                out.append("<p style=\"text-align:justify;"+margin+"\"> \n");
                out.append("<span style=\""+textStyle+"\"> \n");
                out.append(text);
                out.append("</span> \n");
                out.append("</p> \n");
                //más...
                if( url!=null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li> \n");
                    out.append("<a href=\""+url+"\" style=\""+moreStyle);
                    if("0".equalsIgnoreCase(base.getAttribute("uline", "0"))) {
                        out.append(" ;text-decoration:none;\"");
                    }
                    if ("1".equalsIgnoreCase(base.getAttribute("target", "0").trim())) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append("> \n");
                    out.append(more);
                    out.append("\n</a> \n");
                    out.append("</li></ul> \n");
                }
            }

            if( imgfile!=null && imgPos==6 ) {
                out.append(img);
            }

            //marco
            out.append("</div>");
        }catch (Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return out.toString();
    }

    private String render() {
        StringBuilder out = new StringBuilder();
        Resource base=getResourceBase();
        
        String cssClass = base.getAttribute("cssClass","");
        String title = base.getAttribute("title");
        String subtitle = base.getAttribute("subtitle");
        String imgfile = base.getAttribute("imgfile");
        String caption = base.getAttribute("caption");
        int imgWidth;
        try {
            imgWidth = Integer.parseInt(base.getAttribute("imgWidth"));
        }catch(NumberFormatException nfe) {
            imgWidth = 150;
        }
        int imgHeight;
        try {
            imgHeight = Integer.parseInt(base.getAttribute("imgHeight"));
        }catch(NumberFormatException nfe) {
            imgHeight = 180;
        }

        String text = base.getAttribute("text");
        String more = base.getAttribute("more");
        String url = base.getAttribute("url");

        int imgPos;
        try {
            imgPos = Integer.parseInt(base.getAttribute("imgPos","1"));
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\""+cssClass+"\"> \n");

            //title
            if(title != null) {
                out.append("<h1><span> \n");
                out.append(title);
                out.append("</span></h1> \n");
            }

            //image
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                if(imgPos != 6) {
                    img.append("<div> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else {
                    imgPos = 6;
                    img.append("<div> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" width=\""+imgWidth+"\" height=\""+imgHeight+"\" /></span> \n");
                    if(caption != null) {
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2><span>"+subtitle+"</span></h2> \n");
            }

            if( base.getAttribute("more")==null ) {
                //texto
                out.append("<p> \n");
                if(url != null) {
                    out.append("<a href=\""+url+"\">\n");
                    out.append(text);
                    out.append("\n</a> \n");
                }else {
                    out.append("<span> \n");
                    out.append(text);
                    out.append("</span> \n");
                }
                out.append("</p> \n");
            }else {
                out.append("<p> \n");
                out.append("<span> \n");
                out.append(text);
                out.append("</span> \n");
                out.append("</p> \n");
                //más...
                if( url!=null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li> \n");
                    out.append("<a href=\""+url+"\">\n");
                    out.append(more);
                    out.append("\n</a> \n");
                    out.append("</li></ul> \n");
                }
            }

            if( imgfile!=null && imgPos==6 ) {
                out.append(img);
            }

            //marco
            out.append("</div>");
        }catch (Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return out.toString();
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
            }
        }else if("3".equals(position)) {
            ret.append("<center> \n");
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" /> \n");
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
            }
            ret.append(getTextHtml(base));
        }else if("5".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" /> \n");
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
            }
        }else if("7".equals(position)) {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
            }
        }else if("8".equals(position)) {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
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
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        //base. addHit(request, response.getUser(), response.getTopic());
        String url = base.getAttribute("url", "").trim();
        if (!url.equals("")) response.sendRedirect(url);
    }    
}
