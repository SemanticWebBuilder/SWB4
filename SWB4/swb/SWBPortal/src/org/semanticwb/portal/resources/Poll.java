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


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.semanticwb.portal.util.SWBCookieMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.FileUpload;

// TODO: Auto-generated Javadoc
/**
 * Poll se encarga de desplegar y administrar una encuesta de opinion bajo
 * ciertos criterios(configuraci?n de recurso).
 *
 * Poll is in charge to unfold and to administer a survey of opinion under
 * certain criteria (resource configuration).
 *
 * @author  Jorge Jiménez Sandoval
 */

public class Poll extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Poll.class);
    
    /** The Constant PREF. */
    private static final String PREF = "poll_";

    /** The hash prim. */
    HashMap hashPrim=new HashMap();


    /** The tpl. */
    private Templates tpl;

    /** The work path. */
    private String workPath;
    
    /** The web work path. */
    private String webWorkPath;

    /** The path. */
    private String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/Poll/";
    
    /** The restype. */
    private static String restype;
    
    /** The Mng cookie. */
    private SWBCookieMgr MngCookie;
    
    /** The adm res utils. */
    private WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();

    /**
     * The Enum Display.
     */
    public enum Display {
        
        /** The SLIDE. */
        SLIDE, 
 /** The POPUP. */
 POPUP, 
 /** The SIMPLE. */
 SIMPLE;
        
        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return Integer.toString(this.ordinal());
        }
    }

    /**
     * The Enum VMode.
     */
    public enum VMode {
        
        /** The IP. */
        IP, 
 /** The COOKIE. */
 COOKIE;
        
        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return Integer.toString(this.ordinal());
        }
    }

    /**
     * The Enum LocLnks.
     */
    public enum LocLnks {
        
        /** The INPOLL. */
        INPOLL, 
 /** The INRESULTS. */
 INRESULTS, 
 /** The INBOTH. */
 INBOTH;
        
        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return Integer.toString(this.ordinal());
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(Resource)
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            workPath    = SWBPortal.getWorkPath()+base.getWorkPath()+"/";
            webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath()+"/";
            restype= base.getResourceType().getResourceClassName();
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        if(!"".equals(base.getAttribute("template","").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path = webWorkPath;
            }catch(Exception e) {
                log.error("Error while loading resource template: "+base.getId(), e);
            }
        }
        if( tpl==null ) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Poll/Poll.xsl"));
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        }
    }

    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("showResults"))
            doShowPollResults(request,response,paramRequest);
        else if(paramRequest.getMode().equals("accesible"))
            doAccesible(request, response, paramRequest);
        else
            super.processRequest(request, response, paramRequest);
    }

    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws SWBResourceException the sWB resource exception
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base=paramRequest.getResourceBase();

        String title = base.getAttribute("header");
        String imgTitle = base.getAttribute("imgencuesta");
        String question = base.getAttribute("question");
        String display = base.getAttribute("display", Display.SLIDE.toString());

        SWBResourceURL url;
        if( Display.SIMPLE.toString().equals(display) ) {
            url = paramRequest.getRenderUrl();
            url.setMode("accesible");
            url.setParameter("NombreCookie", "VotosEncuesta" + base.getId());
        }else {
            url = new SWBResourceURLImp(request, base, paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
            url.setMode("showResults");
            url.setCallMethod(paramRequest.Call_DIRECT);
            url.setParameter("NombreCookie", "VotosEncuesta" + base.getId());
        }

        Document  dom = SWBUtils.XML.getNewDocument();
        Element root = dom.createElement("poll");
        root.setAttribute("action", url.toString());
        root.setAttribute("id", "poll_"+base.getId());
        dom.appendChild(root);

        Element e;
        if( title!=null ) {
            e = dom.createElement("title");
            e.appendChild(dom.createTextNode(title));
            root.appendChild(e);
        }

        e = dom.createElement("imgTitle");
        e.setAttribute("path", path);
        if( imgTitle!=null )
            e.setAttribute("src", webWorkPath+imgTitle);
        e.setAttribute("alt", base.getAttribute("header",question));
        root.appendChild(e);

        e = dom.createElement("question");
        e.appendChild(dom.createTextNode(question));
        root.appendChild(e);

        Element options = dom.createElement("options");
        root.appendChild(options);
        Document domResource = SWBUtils.XML.xmlToDom(base.getXml());
        NodeList nodes = domResource.getElementsByTagName("option");
        for (int i = 0; i < nodes.getLength(); i++) {
            e = dom.createElement("option");
            e.setAttribute("value", "enc_votos"+(i+1));
            e.setAttribute("id", "opc_"+base.getId()+"_"+i);
            e.setAttribute("name","radiobutton");
            e.appendChild(dom.createTextNode(nodes.item(i).getChildNodes().item(0).getNodeValue()));
            options.appendChild(e);
        }


        String imgVote = base.getAttribute("button");


        e = dom.createElement("vote");
        e.appendChild(dom.createTextNode(base.getAttribute("msg_tovote", paramRequest.getLocaleString("msg_tovote"))));
        e.setAttribute("path", path);
        if( imgVote!=null ) {
            e.setAttribute("src", webWorkPath+imgVote);
            e.setAttribute("alt", base.getAttribute("msg_tovote", paramRequest.getLocaleString("msg_tovote")));
        }
        e.setAttribute("label", base.getAttribute("msg_tovote",paramRequest.getLocaleString("msg_tovote")));
        if( !Display.SIMPLE.toString().equals(display) ) {
            boolean isCLIValidable = Boolean.parseBoolean(base.getAttribute("oncevote")) && VMode.COOKIE.ordinal()==Integer.parseInt(base.getAttribute("vmode", "0"));
            e.setAttribute("action", "buscaCookie('VotosEncuesta"+base.getId()+"','radiobutton',"+isCLIValidable+",'"+url+"')");
        }
        root.appendChild(e);


        e = dom.createElement("results");
        if( Display.POPUP.toString().equals(display) )
            e.setAttribute("action", "abreResultados('"+url.toString(true)+"')");
        else if( Display.SLIDE.toString().equals(display) )
            e.setAttribute("action", "getHtml('"+url+"','"+PREF+base.getId()+"'); expande();");
        else {
            e.setAttribute("label", base.getAttribute("msg_viewresults", paramRequest.getLocaleString("lblDoAdmin_msgResults").replaceAll("\"", "")));
            url = new SWBResourceURLImp(request, base, paramRequest.getWebPage(), SWBResourceURL.UrlType_RENDER);
            url.setMode("accesible");
            e.setAttribute("action", url.toString(true));
        }
        e.setAttribute("path", path);
        e.setAttribute("title", paramRequest.getLocaleString("msgResults_title"));
        e.appendChild(dom.createTextNode(base.getAttribute("msg_viewresults", paramRequest.getLocaleString("msg_viewresults"))));
        root.appendChild(e);

        if( LocLnks.INPOLL.toString().equals(base.getAttribute("wherelinks", "")) || LocLnks.INBOTH.toString().equals(base.getAttribute("wherelinks", "").trim()) ) {
            Element links = dom.createElement("links");
            root.appendChild(links);
            nodes = domResource.getElementsByTagName("link");
            if( nodes!=null ) {
                for(int i=0; i<nodes.getLength(); i++) {
                    String value = nodes.item(i).getChildNodes().item(0).getNodeValue().trim();
                    String content = paramRequest.getLocaleString("lblDoView_relatedLink");
                    if( !"".equals(value) ) {
                        int idx = value.indexOf(",");
                        if( idx>-1 ) {
                            content = value.substring(idx + 1);
                            value = value.substring(0, idx);
                        }
                        e = dom.createElement("link");
                        e.setAttribute("url", value);
                        e.setAttribute("title", content);
                        e.appendChild(dom.createTextNode(content));
                        links.appendChild(e);
                    }
                }
            }
        }
        return dom;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doXML(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Document dom=getDom(request, response, paramRequest);
        if( dom!=null )
            response.getWriter().println(SWBUtils.XML.domToXml(dom));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = paramRequest.getResourceBase();

        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0
        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

         try {
            //String jspFile = paramRequest.getResourceBase().getAttribute("jspfile","/swbadmin/jsp/poll/poll.jsp");
             String jspFile = paramRequest.getResourceBase().getAttribute("jspfile","/swbadmin/jsp/poll/pollAccessible.jsp");

            request.setAttribute("paramRequest", paramRequest);
            Document dom = getDom(request, response, paramRequest);
            String html = SWBUtils.XML.transformDom(tpl, dom);
            boolean isPopup = Boolean.valueOf(base.getAttribute("display", "true")).booleanValue();
            if( !isPopup )
                html += "<div id=\""+PREF+base.getId()+"\" class=\"swb-encuesta-res\"></div>";
            html+=getRenderScript(paramRequest);
            request.setAttribute("html", html);
            RequestDispatcher rd = request.getRequestDispatcher(jspFile);
            rd.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Do accesible.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doAccesible(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0

        StringBuilder html = new StringBuilder();
        try {
            Document dom = getDom(request, response, paramRequest);
            html.append( SWBUtils.XML.transformDom(tpl,dom) );

            vote(request, response, paramRequest);
            dom = SWBUtils.XML.xmlToDom( getResourceBase().getData() );
            html.append(getPollResults(request, paramRequest, dom));
        }catch(TransformerException te) {
            log.error("Error in a resource Poll while transforms the document. ", te);
        }
        response.getWriter().println(html.toString());
    }

    /**
     * Muestra el html al usuario final.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    /*
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0
        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

        Resource base=getResourceBase();

        //StringBuffer ret = new StringBuffer("");
        PrintWriter out = response.getWriter();
        //String action = null != request.getParameter("enc_act") && !"".equals(request.getParameter("enc_act").trim()) ? request.getParameter("enc_act").trim() : "enc_step1";

        try
        {
            Document dom=SWBUtils.XML.xmlToDom(base.getXml());
            if(dom!=null) {
                NodeList node = dom.getElementsByTagName("option");
                if (!"".equals(base.getAttribute("question", "").trim()) && node.getLength() > 1) {
                    if( base.getAttribute("cssClass")!=null && !base.getAttribute("cssClass").trim().equals("") ) {
                        out.println("<div class=\""+base.getAttribute("cssClass")+"\">");
                    }else {
                        out.println("<div class=\"swb-encuesta\">");
                    }

                    if( base.getAttribute("header")!=null ) {
                        String style = "";
                        if( base.getAttribute("headerStyle")!=null ) {
                            style = "style=\""+base.getAttribute("headerStyle")+"\"";
                        }
                        out.println("<h1 "+style+">"+base.getAttribute("header")+"</h1>");
                    }

                    out.println("<form name=\"frmEncuesta\" id=\"frmEncuesta_"+base.getId()+"\" action=\""+paramRequest.getRenderUrl()+"\" method=\"post\" style=\"color:"+base.getAttribute("textcolor")+";\" >");
                    if (!"".equals(base.getAttribute("imgencuesta", "").trim())) {
                        out.println("<img src=\""+webWorkPath+"/"+base.getAttribute("imgencuesta")+"\" border=\"0\" alt=\""+base.getAttribute("question")+"\" />");
                    }

                    out.println("<p>"+base.getAttribute("question")+"</p>");

                    //out.println("<p>");
                    for (int i = 0; i < node.getLength(); i++) {
                        out.println("<label><input type=\"radio\" name=\"radiobutton\" value=\"enc_votos"+(i + 1)+"\" />");
                        out.print(node.item(i).getChildNodes().item(0).getNodeValue()+"</label><br />");
                    }
                    //out.println("</p>");

                    out.println("<p><span>");
                    if(!"".equals(base.getAttribute("button", ""))) {
                        out.println("<input type=\"image\" name=\"votar\" src=\"" + webWorkPath +"/"+ base.getAttribute("button").trim() +"\" onClick=\"buscaCookie(this.form); return false;\"/>");
                    }else {
                        out.println("<input type=\"button\" name=\"votar\" value=\"" + base.getAttribute("msg_tovote",paramRequest.getLocaleString("msg_tovote")) +"\" onClick=\"buscaCookie(this.form);\"/>");
                    }
                    out.println("</span></p>");

                    SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getWebPage(),SWBResourceURL.UrlType_RENDER);
                    url.setMode("showResults");
                    url.setParameter("NombreCookie","VotosEncuesta"+ base.getId());
                    url.setCallMethod(paramRequest.Call_DIRECT);

                    boolean display = Boolean.valueOf(base.getAttribute("display","true")).booleanValue();
                    if(display) {
                        out.println("<p>");
                        out.println("<a href=\"javascript:;\" onclick=\"javascript:abreResultados(\'" + url.toString(true) + "\')\">" + base.getAttribute("msg_viewresults",paramRequest.getLocaleString("msg_viewresults")) + "</a>");
                        out.println("</p>");
                    }else {
                        out.println("<p>");
                        out.println("<a href=\"javascript:;\" onclick=\"getHtml('"+url+"','"+poll+base.getId()+"'); expande();\">" + base.getAttribute("msg_viewresults",paramRequest.getLocaleString("msg_viewresults")) + "</a>");
                        out.println("<div id=\""+poll+base.getId()+"\"> ");
                        out.println("</div>");
                        out.println("</p>");
                    }
                    if("1".equals(base.getAttribute("wherelinks", "").trim()) || "3".equals(base.getAttribute("wherelinks", "").trim())) {
                        out.println(getLinks(dom.getElementsByTagName("link"), paramRequest.getLocaleString("lblDoView_relatedLink")));
                    }
                    out.println("<input type=\"hidden\" name=\"NombreCookie\" value=\"VotosEncuesta"+ base.getId() +"\"/>");
                    out.println("</form>");
                    out.println("</div>");

                    StringBuilder win = new StringBuilder();
                    win.append("menubar="+ base.getAttribute("menubar", "no"));
                    win.append(",toolbar="+ base.getAttribute("toolbar", "no"));
                    win.append(",status="+ base.getAttribute("status", "no"));
                    win.append(",location="+ base.getAttribute("location", "no"));
                    win.append(",directories="+ base.getAttribute("directories", "no"));
                    win.append(",scrollbars="+ base.getAttribute("scrollbars", "no"));
                    win.append(",resizable="+ base.getAttribute("resizable", "no"));
                    win.append(",width="+ base.getAttribute("width", "360"));
                    win.append(",height="+ base.getAttribute("height", "260"));
                    win.append(",top="+ base.getAttribute("top", "125"));
                    win.append(",left="+ base.getAttribute("left", "220"));

                    out.println("<script type=\"text/javascript\">");
                    out.println("dojo.require(\"dojo.fx\");");

                    out.println("\nfunction buscaCookie(forma) {");
                    out.println("    var numcom = getCookie(forma.NombreCookie.value); ");
                    out.println("    if(numcom == \"SI\") { ");
                    if("true".equals(base.getAttribute("oncevote", "true").trim()) && !"0".equals(base.getAttribute("vmode", "0").trim())) {
                        out.println("    alert('"+ paramRequest.getLocaleString("msgDoView_msgVote") +"'); ");
                    }
                    out.println("     } ");
                    out.println("    grabaEncuesta(forma); ");
                    out.println("} ");
                    out.println("function setCookie(name) {");
                    out.println("    document.cookie = name; ");
                    out.println("    var expDate = new Date(); ");
                    out.println("    expDate.setTime(expDate.getTime() + ( 720 * 60 * 60 * 1000) ); ");
                    out.println("    expDate = expDate.toGMTString(); ");
                    out.println("    var str1 = name +\"=SI; expires=\" + expDate + \";Path=/\"; ");
                    out.println("    document.cookie = str1; ");
                    out.println("} ");
                    out.println("function getCookie (name) {");
                    out.println("    var arg = name + \"=\"; ");
                    out.println("    var alen = arg.length; ");
                    out.println("    var clen = document.cookie.length; ");
                    out.println("    var i = 0; ");
                    out.println("    while (i < clen) ");
                    out.println("    { ");
                    out.println("        var j = i + alen; ");
                    out.println("        if (document.cookie.substring(i, j) == arg) ");
                    out.println("            return getCookieVal (j); ");
                    out.println("        i = document.cookie.indexOf(\" \", i) + 1; ");
                    out.println("        if (i == 0) break; ");
                    out.println("    } ");
                    out.println("    return null; ");
                    out.println("} ");
                    out.println("function getCookieVal (offset) {");
                    out.println("    var endstr = document.cookie.indexOf (\";\", offset); ");
                    out.println("    if (endstr == -1) ");
                    out.println("        endstr = document.cookie.length; ");
                    out.println("    return unescape(document.cookie.substring(offset, endstr)); ");
                    out.println("} ");
                    out.println("function grabaEncuesta(forma) {");
                    out.println("    var optValue; ");
                    out.println("    for(var i=0; i< forma.length; i++) {");
                    out.println("        if(forma.elements[i].type == \"radio\") { ");
                    out.println("            if(forma.elements[i].checked) { ");
                    out.println("                optValue = forma.elements[i].value; ");
                    out.println("                break;");
                    out.println("            } ");
                    out.println("        } ");
                    out.println("    } ");
                    out.println("    if(optValue != null) { ");
                    if(display) {
                        out.println("  window.open(\'"+ url.toString() +"&radiobutton=\'+optValue,\'_newenc\',\'"+win+"\'); ");
                    }else {
                        out.println("  getHtml('"+url.toString()+"&radiobutton='+optValue,'"+poll+base.getId()+"'); expande();");
                    }
                    out.println("    }else { ");
                    out.println("       alert('"+ paramRequest.getLocaleString("msgDoView_msgAnswer") +"'); ");
                    out.println("    } ");
                    out.println("} ");

                    out.println("function abreResultados(ruta) {");
                    out.println("    window.open(ruta,\'_newenc\',\'"+ win +"\'); ");
                    out.println("} ");

                    out.println("function expande() {");
                    out.println("  var anim1 = dojo.fx.wipeIn( {node:\""+poll+base.getId()+"\", duration:500 });");
                    out.println("  var anim2 = dojo.fadeIn({node:\""+poll+base.getId()+"\", duration:650});");
                    out.println("  dojo.fx.combine([anim1,anim2]).play();");
                    out.println("}");

                    out.println("function collapse() {");
                    out.println("  var anim1 = dojo.fx.wipeOut( {node:\""+poll+base.getId()+"\", duration:500 });");
                    out.println("  var anim2 = dojo.fadeOut({node:\""+poll+base.getId()+"\", duration:650});");
                    out.println("  dojo.fx.combine([anim1, anim2]).play();");
                    out.println("}");

                    out.println("</script>");
                }
            }
        }catch (Exception e) {
            log.error(paramRequest.getLocaleString("msgDoView_resource") +" "+ restype +" "+ paramRequest.getLocaleString("msgDoView_method"), e);
        }
        out.flush();
    }
    **/

    /**
     * Muestra los resultados de la encuesta en especifico
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public void doShowPollResults(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0

        vote(request, response, paramRequest);
        Document dom = SWBUtils.XML.xmlToDom( getResourceBase().getData() );
        response.getWriter().println( getPollResults(request, paramRequest, dom) );
    }

    /**
     * Metodo que valida si se encuentra la cookie de la encuensta registrada en la maquina del usuario.
     * 
     * @param request the request
     * @return true, if successful
     */
    private boolean validateCookie(javax.servlet.http.HttpServletRequest request) {
        for(int i=0;i<request.getCookies().length;i++){
            javax.servlet.http.Cookie cookie=request.getCookies() [i];
            if(request.getParameter("NombreCookie").equals(cookie.getName()) && cookie.getValue().equals("SI")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que valida si la ip del usuario final ya voto.
     * 
     * @param request the request
     * @return true, if successful
     */
    private boolean validateIPAddress(javax.servlet.http.HttpServletRequest request) {
        boolean flag = false;
        String actualIP=request.getRemoteAddr();
        int minutes=20;
        try {
            minutes=Integer.parseInt(getResourceBase().getAttribute("time", "20").trim());
        }
        catch(Exception e){
            minutes=20;
        }
        Date date = new Date();
        Timestamp fctual = new Timestamp(date.getTime());
        date = new Date(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes() + minutes, date.getSeconds());
        Timestamp Tfctualmoretime = new Timestamp(date.getTime());
        if (hashPrim != null && hashPrim.size() > 0)
        {
            if(hashPrim.containsKey(actualIP)){
                Timestamp ipdate=(Timestamp)hashPrim.get(actualIP);
                if(ipdate.after(fctual)) {
                    return true; //No puede votar
                }else{ //Despues de pasado el tiempo se elimina la ip, para que pueda votar y vuelva a pasar el tiempo definido para volver a podet votar
                    hashPrim.remove(actualIP);
                }
            }else {
                hashPrim.put(request.getRemoteAddr(), Tfctualmoretime);
            }
        }else if (hashPrim.size() == 0)
        {
            hashPrim = new HashMap();
            hashPrim.put(request.getRemoteAddr(), Tfctualmoretime);
        }
        return flag;
    }

    /**
     * Vote.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void vote(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = null;
        String data = null;
        Document dom = null;

        if(request.getParameter("radiobutton")==null)
            return;

        base = getResourceBase();
        data = base.getData();
        dom = SWBUtils.XML.xmlToDom(data);

        boolean cliVoted = false;
        String validateMode = base.getAttribute("vmode", VMode.IP.toString());
        if( validateMode.equals(VMode.IP.toString()) ) {
            cliVoted = validateIPAddress(request);
        }else {
            cliVoted = validateCookie(request);
            //Pone cookie
            MngCookie = new SWBCookieMgr();
            String value = (String) MngCookie.SearchCookie("VotosEncuesta"+ base.getId(), request);
            MngCookie.AddCookie("VotosEncuesta"+ base.getId(), "SI",  true, true, request, response);
        }

        boolean voteOnce = Boolean.parseBoolean(base.getAttribute("oncevote", "true"));
        if( !voteOnce || !cliVoted) { // Es un usuario que paso la prueba de las IPs
            int valor = 0;
            try {
                valor = Integer.parseInt(request.getParameter("radiobutton").substring(9));
            }catch(Exception e) {
                valor=0;
                log.error("Respuesta de encuesta en cero. ", e);
            }

            if(valor > 0) {
                String varia = "enc_votos";
                if (data == null) {
                    try {
                        dom = SWBUtils.XML.getNewDocument();
                        Element root = dom.createElement("resource");
                        dom.appendChild(root);
                        Element option = dom.createElement(varia + valor);
                        option.appendChild(dom.createTextNode("1"));
                        root.appendChild(option);
                        base.setData(SWBUtils.XML.domToXml(dom));
                    }catch (Exception e) {
                        log.error(paramRequest.getLocaleString("msgDoView_setData") +" "+ restype +" " + paramRequest.getLocaleString("msgDoView_id") +" "+ base.getId() +" - "+ base.getTitle(), e);
                    }
                }else {
                    try {
                        NodeList nlist = dom.getElementsByTagName(varia + valor);
                        boolean exist = false;
                        for (int i = 0; i < nlist.getLength(); i++) {
                            exist = true;
                            try {
                                int votosOption = Integer.parseInt(nlist.item(i).getChildNodes().item(0).getNodeValue());
                                votosOption = votosOption + 1;
                                nlist.item(i).getChildNodes().item(0).setNodeValue(String.valueOf(votosOption));
                            }catch(NumberFormatException nfe) {
                                log.error("La opción está guardando un valor no numérico.\n" + nfe);
                            }
                        }
                        if (!exist) {
                            Node nres = dom.getFirstChild();
                            Element option = dom.createElement(varia + valor);
                            option.appendChild(dom.createTextNode("1"));
                            nres.appendChild(option);
                        }
                        base.setData(SWBUtils.XML.domToXml(dom));
                        base.addHit(request, paramRequest.getUser(), paramRequest.getWebPage());
                    }
                    catch (Exception e) {
                        log.error(paramRequest.getLocaleString("msgDoView_setData") +" "+ restype +" " + paramRequest.getLocaleString("msgDoView_id") +" "+ base.getId() +" - "+ base.getTitle(), e);
                    }
                }
            }
        }
    }

    /**
     * Muestra los resultados de la encuesta.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @param data the data
     * @return the poll results
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    private String getPollResults(HttpServletRequest request, SWBParamRequest paramRequest, Document data) throws SWBResourceException, IOException {
        StringBuilder ret = new StringBuilder();
        Resource base=getResourceBase();
        boolean display = Boolean.valueOf(base.getAttribute("display","true")).booleanValue();

        Document dom = SWBUtils.XML.xmlToDom(base.getXml());
        if(dom==null) {
            return ret.toString();
        }
        if( display ) {
            ret.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
            ret.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            ret.append("<head>");
            ret.append("<title>" + paramRequest.getLocaleString("msgResults_title") + "</title>");
            ret.append("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\""+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/swbadmin/css/swb_portal.css"+"\" />");
            ret.append("</head>");
            ret.append("<body>");
        }

        NodeList node = dom.getElementsByTagName("option");
        ret.append("<div class=\"swb-poll-res\">");
        ret.append("<p class=\"swb-poll-res-title\">"+paramRequest.getLocaleString("msgResults_title")+"</p>");
        ret.append("<table class=\"swb-poll-data\" \n>");

        if( !"".equals(base.getAttribute("question", "").trim()) && node.getLength()>1 )
        {
            ret.append("<caption>" + base.getAttribute("question").trim() + "</caption> \n");
            ret.append("<tbody> \n");
            if (data != null)
            {
                //Suma el total de votos para calcular el porcentaje
                long intTotalVotos = 0;
                long intAjuste = 0;
                Node nodoFC = data.getFirstChild();
                NodeList nlOption = nodoFC.getChildNodes();
                for (int j = 0; j < nlOption.getLength(); j++)
                {
                    if (nlOption.item(j).getChildNodes().getLength() > 0)
                    {
                        intTotalVotos = intTotalVotos + Integer.parseInt(nlOption.item(j).getChildNodes().item(0).getNodeValue());
                        Integer votos = new Integer(nlOption.item(j).getChildNodes().item(0).getNodeValue());
                        intAjuste = intAjuste + ((votos.longValue() * 100) / intTotalVotos);
                    }
                }
                if (intAjuste > 0) {
                    intAjuste = 100 - intAjuste;
                }

                long intVotos = 0;
                float intPorcentaje = 0;
                float largo = 0;

                boolean porcent = Boolean.valueOf(base.getAttribute("porcent","true")).booleanValue();
                boolean totvote = Boolean.valueOf(base.getAttribute("totvote","true")).booleanValue();
                for (int i = 0; i < node.getLength(); i++) {
                    int num = i + 1;
                    ret.append("<tr> \n");
                    ret.append("  <td class=\"swb-res-opciones-h\">"+node.item(i).getChildNodes().item(0).getNodeValue()+"</td> \n");
                    String varia = "enc_votos";
                    NodeList nlist = data.getElementsByTagName(varia + num);
                    for (int j = 0; j < nlist.getLength(); j++)
                    {
                        String key = nlist.item(j).getNodeName();
                        String nume = key.substring(9);
                        Integer votos = new Integer(nlist.item(j).getChildNodes().item(0).getNodeValue());
                        intVotos = votos.intValue();
                        intPorcentaje = ((float) votos.intValue() * 100) / (float) intTotalVotos;
                        intPorcentaje = (intPorcentaje * 10);
                        intPorcentaje += .5;
                        intPorcentaje = (int) intPorcentaje;
                        intPorcentaje = intPorcentaje / 10;

                        if (Integer.parseInt(nume) == num) {
                            largo = intPorcentaje;
                            ret.append("  <td class=\"swb-res-porciento-h\"><div class=\"swb-res-porciento-no-h\"><div class=\"swb-res-porciento-si-h\" style=\"width:"+largo+"%\"></div></div></td> \n");
                            ret.append("  <td class=\"swb-res-votos-h\"> \n");

                            if (porcent) {
                                ret.append("<span>"+largo+"%</span> \n");
                            }
                            if (porcent && totvote) {
                                ret.append(" : ");
                            }
                            if (totvote) {
                                ret.append("<span>"+intVotos+"&nbsp;"+base.getAttribute("msg_vote",paramRequest.getLocaleString("msg_vote"))+"(s)</span> \n");
                            }
                            ret.append("</td> \n");
                            ret.append("</tr> \n");
                        }
                    }
                }
                ret.append("</tbody> \n");
                intAjuste = 0;

                if (totvote) {
                    ret.append("<tfoot> \n");
                    ret.append("<tr><td align=\"right\" colspan=\"3\">"+ base.getAttribute("msg_totvotes",paramRequest.getLocaleString("msg_totvotes")) + ": " + intTotalVotos + "</td></tr> \n");
                    ret.append("</tfoot> \n");
                }
            }else {
                ret.append("<tr><td>" + paramRequest.getLocaleString("usrmsg_Encuesta_getResultEnc_noVotes") +"</td></tr> \n");
            }
            ret.append("</table> \n");
            ret.append("</div> \n");

            ret.append("<div class=\"swb-poll-resume\"> \n");
            if( LocLnks.INRESULTS.toString().equals(base.getAttribute("wherelinks")) || LocLnks.INBOTH.toString().equals(base.getAttribute("wherelinks")) )
                ret.append(getLinks(dom.getElementsByTagName("link"), paramRequest.getLocaleString("usrmsg_Encuesta_doView_relatedLink"))+" \n");

            if( display )
                ret.append("<p class=\"swb-poll-close\"><a href=\"javascript:window.close();\">" + base.getAttribute("msg_closewin",paramRequest.getLocaleString("msg_closewin")) + "</a></p> \n");
            else {
                if( Display.SIMPLE.toString().equals(base.getAttribute("display")) )
                    ret.append("<p class=\"swb-poll-close\"><a href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\">" + base.getAttribute("msg_closewin",paramRequest.getLocaleString("msg_closewin")) + "</a></p> \n");
                else
                    ret.append("<p class=\"swb-poll-close\"><a href=\"javascript:;\" onmousedown=\"collapse();\">" + base.getAttribute("msg_closewin",paramRequest.getLocaleString("msg_closewin")) + "</a></p> \n");
            }
            ret.append("</div> \n");
        }
        if(display){
            ret.append("</body> \n");
            ret.append("</html> \n");
        }
        return ret.toString();
    }

    /**
     * Gets the links.
     * 
     * @param links the links
     * @param genDesc the gen desc
     * @return the links
     */
    private String getLinks(NodeList links, final String genDesc) {
        StringBuffer ret = new StringBuffer("");
        if( links==null )
            return ret.toString();

        String ownDesc = genDesc;
        for (int i = 0; i < links.getLength(); i++) {
            String value = links.item(i).getChildNodes().item(0).getNodeValue().trim();
            if( !"".equals(value) ) {
                //int idx = value.indexOf("/wblink/");
                int idx = value.indexOf(",");
                if( idx>-1 ) {
                    ownDesc = value.substring(idx + 1);
                    value = value.substring(0, idx);
                }
                ret.append("<p class=\"swb-poll-more\"><a href=\""+value+"\" title=\""+ownDesc+"\">"+ ownDesc + "</p> ");
                ownDesc=genDesc;
            }
        }
        return ret.toString();
    }

    /**
     * Metodo que despliega la administraci?n del recurso.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();
        String msg=paramRequest.getLocaleString("lblDoAdmin_undefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();

        if(action.equals("add") || action.equals("edit")) {
            out.println(getForm(request, paramRequest));
        }else if(action.equals("update")) {
            FileUpload fup = new FileUpload();
            try
            {
                fup.getFiles(request, response);
                String value = null != fup.getValue("question") && !"".equals(fup.getValue("question").trim()) ? fup.getValue("question").trim() : null;
                String option = null != fup.getValue("option") && !"".equals(fup.getValue("option").trim()) ? fup.getValue("option").trim() : null;
                if (value!=null && option!=null)
                {
                    base.setAttribute("question", value);
                    value = null != fup.getValue("noimgencuesta") && !"".equals(fup.getValue("noimgencuesta").trim()) ? fup.getValue("noimgencuesta").trim() : "0";
                    if ("1".equals(value) && !"".equals(base.getAttribute("imgencuesta", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(workPath+base.getAttribute("imgencuesta").trim());
                        base.removeAttribute("imgencuesta");
                    }
                    else
                    {
                        value = null != fup.getFileName("imgencuesta") && !"".equals(fup.getFileName("imgencuesta").trim()) ? fup.getFileName("imgencuesta").trim() : null;
                        if (value!=null)
                        {
                            String file = admResUtils.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(file, "jpg|jpeg|gif|png")){
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>jpg, jpeg, gif, png</i>: " + file;
                                }
                                else
                                {
                                    if (admResUtils.uploadFile(base, fup, "imgencuesta")){
                                        base.setAttribute("imgencuesta", file);
                                    }
                                    else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }
                            else {
                                msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                            }
                        }
                    }

                    value = null != fup.getValue("jspfile") && !"".equals(fup.getValue("jspfile").trim()) ? fup.getValue("jspfile").trim() : "";
                    base.setAttribute("jspfile",value);
                    value = null != fup.getFileName("template") && !"".equals(fup.getFileName("template").trim()) ? fup.getFileName("template").trim() : null;
                    String f = admResUtils.getFileName(base, value);
                    String applet = admResUtils.uploadFileParsed(base, fup, "template");
                    base.setAttribute("template", f);

                    value = null != fup.getValue("nobutton") && !"".equals(fup.getValue("nobutton").trim()) ? fup.getValue("nobutton").trim() : "0";
                    if ("1".equals(value) && !"".equals(base.getAttribute("button", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(workPath+base.getAttribute("button").trim());
                        base.removeAttribute("button");
                    }
                    else
                    {
                        value = null != fup.getFileName("button") && !"".equals(fup.getFileName("button").trim()) ? fup.getFileName("button").trim() : null;
                        if (value!=null)
                        {
                            String file = admResUtils.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(file, "jpg|jpeg|gif|png")) {
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>jpg, jpeg, gif, png</i>: " + file;
                                }
                                else
                                {
                                    if (admResUtils.uploadFile(base, fup, "button")) {
                                        base.setAttribute("button", file);
                                    }
                                    else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }
                            else {
                                msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                            }
                        }
                    }

                    value = null != fup.getValue("nobackimgres") && !"".equals(fup.getValue("nobackimgres").trim()) ? fup.getValue("nobackimgres").trim() : "0";
                    if ("1".equals(value) && !"".equals(base.getAttribute("backimgres", "").trim()))
                    {
                        SWBUtils.IO.removeDirectory(workPath+base.getAttribute("backimgres").trim());
                        base.removeAttribute("backimgres");
                    }
                    else
                    {
                        value = null != fup.getFileName("backimgres") && !"".equals(fup.getFileName("backimgres").trim()) ? fup.getFileName("backimgres").trim() : null;
                        if (value!=null)
                        {
                            String file = admResUtils.getFileName(base, value);
                            if (file != null && !file.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(file, "jpg|jpeg|gif|png")) {
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>jpg, jpeg, gif, png</i>: " + file;
                                }
                                else
                                {
                                    if (admResUtils.uploadFile(base, fup, "backimgres")){
                                        base.setAttribute("backimgres", file);
                                    }
                                    else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }
                            else msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                        }
                    }

//                    value = null!=fup.getValue("branches") && !"".equals(fup.getValue("branches")) ? fup.getValue("branches").trim() : "0";
//                    base.setAttribute("branches", value);
                    value = null != fup.getValue("time") && !"".equals(fup.getValue("time").trim()) ? fup.getValue("time").trim() : "20";
                    base.setAttribute("time", value);
                    setAttribute(base, fup, "wherelinks");
//                    setAttribute(base, fup, "textcolor");
                    setAttribute(base, fup, "oncevote");
                    setAttribute(base, fup, "vmode");
                    setAttribute(base, fup, "display");
                    setAttribute(base, fup, "porcent");
                    setAttribute(base, fup, "totvote");
//                    setAttribute(base, fup, "textcolorres");
                    setAttribute(base, fup, "menubar", "yes");
                    setAttribute(base, fup, "toolbar", "yes");
                    setAttribute(base, fup, "status", "yes");
                    setAttribute(base, fup, "location", "yes");
                    setAttribute(base, fup, "directories", "yes");
                    setAttribute(base, fup, "scrollbars", "yes");
                    setAttribute(base, fup, "resizable", "yes");
                    setAttribute(base, fup, "width");
                    setAttribute(base, fup, "height");
                    setAttribute(base, fup, "top");
                    setAttribute(base, fup, "left");
                    setAttribute(base, fup, "msg_viewresults");
                    setAttribute(base, fup, "msg_vote");
                    setAttribute(base, fup, "msg_closewin");
                    setAttribute(base, fup, "msg_totvotes");
                    setAttribute(base, fup, "msg_tovote");

                    setAttribute(base, fup, "cssClass");
                    setAttribute(base, fup, "header");
//                    setAttribute(base, fup, "headerStyle");

                    base.updateAttributesToDB();
                    //Document dom=base.getDom();
                    Document dom=SWBUtils.XML.xmlToDom(base.getXml());
                    if(dom!=null) {
                        removeAllNodes(dom, Node.ELEMENT_NODE, "option");
                    }else {
                        dom = SWBUtils.XML.getNewDocument();
                        Element root = dom.createElement("resource");
                        dom.appendChild(root);
                    }
                    value = null != fup.getValue("option") && !"".equals(fup.getValue("option").trim()) ? fup.getValue("option").trim() : null;
                    if(value!=null)
                    {
                        StringTokenizer stk = new StringTokenizer(value, "|");
                        while (stk.hasMoreTokens())
                        {
                            value = stk.nextToken();
                            Element emn = dom.createElement("option");
                            emn.appendChild(dom.createTextNode(value));
                            dom.getFirstChild().appendChild(emn);
                        }
                    }
                    removeAllNodes(dom, Node.ELEMENT_NODE, "link");
                    value = null != fup.getValue("link") && !"".equals(fup.getValue("link").trim()) ? fup.getValue("link").trim() : null;
                    if(value!=null)
                    {
                        StringTokenizer stk = new StringTokenizer(value, "|");
                        while (stk.hasMoreTokens())
                        {
                            value = stk.nextToken();
                            Element emn = dom.createElement("link");
                            emn.appendChild(dom.createTextNode(value));
                            dom.getFirstChild().appendChild(emn);
                        }
                    }
                    base.setXml(SWBUtils.XML.domToXml(dom));
                    //base.getRecResource().update(paramsRequest.getUser().getId(), "Resource with identifier "+base.getId()+" was updated successfully ");

                    msg=paramRequest.getLocaleString("msgOkUpdateResource") +" "+ base.getId();
                    out.println("<script type=\"text/javascript\">");
                    out.println("<!--");
                    out.println("  alert('"+msg+"');");
                    out.println("-->");
                    out.println("</script>");
                    if( applet!=null && !applet.trim().equals("") ) {
                        out.println(applet);
                    }else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("<!--");
                        out.println("  location='"+paramRequest.getRenderUrl().setAction("edit").toString()+"';");
                        out.println("-->");
                        out.println("</script>");
                    }

                }
                else msg=paramRequest.getLocaleString("msgDataRequired");
            }
            catch(Exception e) { log.error(e); msg=paramRequest.getLocaleString("msgErrUpdateResource") +" "+ base.getId(); }
        }
        else if(action.equals("remove"))
        {
            msg=admResUtils.removeResource(base);
            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("  alert('"+msg+"');");
            out.println("-->");
            out.println("</script>");
        }
        out.flush();
    }

    /**
     * Sets the attribute.
     * 
     * @param base the base
     * @param fup the fup
     * @param att the att
     */
    protected void setAttribute(Resource base, FileUpload fup, String att) {
        try
        {
            if(null != fup.getValue(att) && !"".equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            }
            else {
                base.removeAttribute(att);
            }
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }

    /**
     * Sets the attribute.
     * 
     * @param base the base
     * @param fup the fup
     * @param att the att
     * @param value the value
     */
    protected void setAttribute(Resource base, FileUpload fup, String att, String value) {
        try
        {
            if(null != fup.getValue(att) && value.equals(fup.getValue(att).trim())) {
                base.setAttribute(att, fup.getValue(att).trim());
            }
            else {
                base.removeAttribute(att);
            }
        }
        catch(Exception e) {  log.error("Error while setting resource attribute: "+att + ", "+base.getId() +"-"+ base.getTitle(), e); }
    }

    /**
     * Removes the all nodes.
     * 
     * @param dom the dom
     * @param nodeType the node type
     * @param name the name
     */
    private void removeAllNodes(Document dom, short nodeType, String name) {
        NodeList list = dom.getElementsByTagName(name);
        for (int i = 0; i < list.getLength(); i++)
        {
            Node node=list.item(i);
            if (node.getNodeType() == nodeType)
            {
                node.getParentNode().removeChild(node);
                if(node.hasChildNodes()) {
                    removeAllNodes(dom, nodeType, name);
                }
            }
        }
    }

    /**
     * Metodo que muestra la forma de la encuesta de opini?n en html.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @return the form
     */
    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) {
        StringBuilder ret=new StringBuilder();
        Resource base=getResourceBase();
        try
        {
            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN);
            url.setAction("update");

            ret.append("<div class=\"swbform\">");
            ret.append("<form id=\"frmAdmRes\" name=\"frmAdmRes\" method=\"post\" dojoType=\"dijit.form.Form\" enctype=\"multipart/form-data\" action=\""+ url.toString()+"\"> ");

            ret.append("<fieldset> ");
            ret.append("<legend>"+paramRequest.getLocaleString("lblDoAdmin_PollData")+"</legend>");
            ret.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");
            ret.append("<tr><td width=\"250\"></td><td></td></tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_question")+"<span class=\"requerido\">*</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"question\" value=\""+base.getAttribute("question","").replaceAll("\"", "&#34;")+"\" maxlength=\"50\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" required=\"true\" />");
            ret.append("</td> ");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_option")+"<span class=\"requerido\">*</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"txtOption\" maxlength=\"50\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("<input type=\"hidden\" name=\"option\" value=\""+base.getAttribute("option","").trim().replaceAll("\"", "&#34;")+"\" />");
            ret.append("<input type=\"button\" value=\""+paramRequest.getLocaleString("lblDoAdmin_btnAdd")+"\" onClick=\"addOption(this.form.selOption, this.form.txtOption)\" />");
            ret.append("<input type=\"button\" value=\""+paramRequest.getLocaleString("lblDoAdmin_btnEdit")+"\" onClick=\"updateOption(this.form.selOption, this.form.txtOption)\" />");
            ret.append("</td> ");
            ret.append("</tr> ");

            ret.append("<tr> ");
            ret.append("<td class=\"datos\">&nbsp;</td> ");
            ret.append("<td class=\"valores\">");
            ret.append("<select name=\"selOption\" size=\"5\" multiple=\"multiple\" onChange=\"editOption(this.form.selOption, this.form.txtOption)\">");

            String value="";
            Document dom = SWBUtils.XML.xmlToDom(base.getXml());
            if( dom!=null ) {
                NodeList node = dom.getElementsByTagName("option");
                for(int i=0; i<node.getLength(); i++) {
                    value = node.item(i).getChildNodes().item(0).getNodeValue().trim();
                    if(!"".equals(value.trim())) {
                        ret.append("<option value=\"" + value.trim().replaceAll("\"", "&#34;") + "\">"+value.trim()+"</option>");
                    }
                }
            }
            ret.append("</select>");
            ret.append("<input type=\"button\" name=\"btnDel\" value=\""+paramRequest.getLocaleString("lblDoAdmin_btnDelete")+"\" onClick=\"deleteOption(this.form.selOption, this.form.txtOption)\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_link")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"txtLink\" maxlength=\"60\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("<input type=\"hidden\" name=\"link\" value=\""+base.getAttribute("link","").trim().replaceAll("\"", "&#34;")+"\" />");
            ret.append("<input type=\"button\" value=\""+paramRequest.getLocaleString("lblDoAdmin_btnAdd")+"\" onClick=\"addOption(this.form.selLink, this.form.txtLink)\" />");
            ret.append("<input type=\"button\" value=\""+paramRequest.getLocaleString("lblDoAdmin_btnEdit")+"\" onClick=\"updateOption(this.form.selLink, this.form.txtLink)\" />");
            ret.append("</td> ");
            ret.append("</tr> ");

            ret.append("<tr> ");
            ret.append("<td class=\"datos\">&nbsp;</td> ");
            ret.append("<td class=\"valores\">");
            ret.append("<select name=\"selLink\" size=\"5\" multiple=\"multiple\" onChange=\"editOption(this.form.selLink, this.form.txtLink)\">");
            if(dom!=null) {
                NodeList node = dom.getElementsByTagName("link");
                for(int i = 0; i < node.getLength(); i++) {
                    value = node.item(i).getChildNodes().item(0).getNodeValue().trim();
                    if(!"".equals(value.trim())) {
                        ret.append("<option value=\"" + value.trim().replaceAll("\"","&#34;")+"\">"+value.trim()+"</option>");
                    }
                }
            }
            ret.append("</select>");
            ret.append("<input type=\"button\" value=\""+paramRequest.getLocaleString("lblDoAdmin_btnDelete")+"\" onClick=\"deleteOption(this.form.selLink, this.form.txtLink)\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_displayLinks")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            value = base.getAttribute("wherelinks", LocLnks.INPOLL.toString());
            ret.append("<label for=\"wherelinks1\">");
            ret.append("<input type=\"radio\" name=\"wherelinks\" id=\"wherelinks1\" value=\""+LocLnks.INPOLL.toString()+"\" ");
            if(LocLnks.INPOLL.toString().equalsIgnoreCase(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_onPoll")+"</label>&nbsp;&nbsp;&nbsp;");

            ret.append("<label for=\"wherelinks2\">");
            ret.append("<input type=\"radio\" name=\"wherelinks\" id=\"wherelinks2\" value=\""+LocLnks.INRESULTS.toString()+"\" ");
            if(LocLnks.INRESULTS.toString().equalsIgnoreCase(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_onPollResults")+"</label>&nbsp;&nbsp;&nbsp;");

            ret.append("<label for=\"wherelinks3\">");
            ret.append("<input type=radio name=\"wherelinks\" id=\"wherelinks3\" value=\""+LocLnks.INBOTH.toString()+"\" ");
            if(LocLnks.INBOTH.toString().equalsIgnoreCase(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_onBoth")+"</label>");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_voteOnce")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            value = base.getAttribute("oncevote", Boolean.TRUE.toString());
            ret.append("<label for=\"oncevoteyes\">");
            ret.append("<input type=\"radio\" name=\"oncevote\" id=\"oncevoteyes\" value=\""+Boolean.TRUE.toString()+"\" ");
            if(Boolean.TRUE.toString().equals(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_yes")+"</label>&nbsp;&nbsp;&nbsp;");
            ret.append("<label for=\"oncevoteno\">");
            ret.append("<input type=\"radio\" name=\"oncevote\" id=\"oncevoteno\" value=\""+Boolean.FALSE.toString()+"\" ");
            if(Boolean.FALSE.toString().equals(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_no")+"</label>");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_vmode")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            value = base.getAttribute("vmode", VMode.IP.toString());
            ret.append("<label for=\"\">");
            ret.append("<input type=\"radio\" name=\"vmode\" id=\"vmodeip\" value=\""+VMode.IP.toString()+"\" ");
            if( VMode.IP.toString().equalsIgnoreCase(value) )
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_ipMode")+"</label>&nbsp;&nbsp;&nbsp;");
            ret.append("<label for=\"vmodecki\">");
            ret.append("<input type=\"radio\" name=\"vmode\" id=\"vmodecki\" value=\""+VMode.COOKIE.toString()+"\" ");
            if( VMode.COOKIE.toString().equalsIgnoreCase(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_cookieMode")+"</label>");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_time")+" <span class=\"enfasis\">(" + paramRequest.getLocaleString("lblDoAdmin_minutes") + ")</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"time\" dir=\"rtl\" maxlength=\"6\" value=\""+base.getAttribute("time", "20")+"\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\""+paramRequest.getLocaleString("lblDoAdmin_time")+"\" invalidMessage=\""+paramRequest.getLocaleString("lblDoAdmin_timeInvalid")+"\" regExp=\"\\d{1,6}\" />");
            ret.append("</td>");
            ret.append("</tr>");
            ret.append("</table> ");
            ret.append("</fieldset> ");



            ret.append("<fieldset> ");
            ret.append("<legend>"+paramRequest.getLocaleString("lblDoAdmin_lookfeelPoll")+"</legend>");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");
            ret.append("<tr><td width=\"250\"></td><td></td></tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_cssClass")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"cssClass\" value=\""+base.getAttribute("cssClass","")+"\" maxlength=\"30\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" invalidMessage=\"to do\" regExp=\".+\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_header")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"header\" value=\""+base.getAttribute("header","")+"\" maxlength=\"50\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" invalidMessage=\"to do\" regExp=\".+\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">" + paramRequest.getLocaleString("lblDoAdmin_imgTitle")+"&nbsp; <span class=\"enfasis\">(jpg, jpeg, gif, png)</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"file\" size=\"40\" name=\"imgencuesta\" onChange=\"isFileType(this, 'jpg|jpeg|gif|png');\" maxlength=\"80\" />");
            ret.append("</td>");
            ret.append("</tr>");
            if( base.getAttribute("imgencuesta")!=null ) {
                ret.append("<tr>");
                ret.append("<td class=\"datos\">&nbsp;</td>");
                ret.append("<td class=\"valores\">");
                ret.append(admResUtils.displayImage(base, base.getAttribute("imgencuesta"), "imgencuesta") +"<br /><input type=\"checkbox\" name=\"noimgencuesta\" value=\"1\" /> <span class=\"enfasis\">"+paramRequest.getLocaleString("lblDoAdmin_removeImage")+" <i>"+base.getAttribute("imgencuesta")+"</i></span>");
                ret.append("</td>");
                ret.append("</tr>");
            }

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_label")+" "+paramRequest.getLocaleString("lblDoAdmin_vote")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"msg_tovote\" value=\""+base.getAttribute("msg_tovote","")+"\" maxlength=\"25\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+ paramRequest.getLocaleString("lblDoAdmin_imgVote") + "<span class=\"enfasis\">(jpg, jpeg, gif, png):</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"file\" size=\"40\" name=\"button\" onChange=\"isFileType(this, 'jpg|jpeg|gif|png');\" maxlength=\"80\"/>");
            ret.append("</td>");
            ret.append("</tr>");
            if( base.getAttribute("button")!=null ) {
                ret.append("<tr>");
                ret.append("<td class=\"datos\">&nbsp;</td>");
                ret.append("<td class=\"valores\">");
                ret.append(admResUtils.displayImage(base, base.getAttribute("button"), "button") +"<br /><input type=\"checkbox\" name=\"noimgencuesta\" value=\"1\" /> <span class=\"enfasis\">"+paramRequest.getLocaleString("lblDoAdmin_removeImage")+" <i>"+base.getAttribute("button")+"</i></span>");
                ret.append("</td>");
                ret.append("</tr>");
            }
            ret.append("</table>");
            ret.append("</fieldset>");


            ret.append("<fieldset> ");
            ret.append("<legend>"+paramRequest.getLocaleString("lblDoAdmin_ResultsData")+"</legend>");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");
            ret.append("<tr><td width=\"250\"></td><td></td></tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_percentage")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            value = base.getAttribute("porcent", Boolean.TRUE.toString());
            ret.append("<label for=\"porcenttrue\"><input type=\"radio\" name=\"porcent\" id=\"porcenttrue\" value=\""+Boolean.TRUE.toString()+"\" ");
            if( Boolean.TRUE.toString().equals(value) )
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_yes")+"</label>&nbsp;&nbsp;&nbsp;");
            ret.append("<label for=\"porcentfalse\"><input type=\"radio\" name=\"porcent\" id=\"porcentfalse\" value=\""+Boolean.FALSE.toString()+"\" ");
            if(Boolean.FALSE.toString().equals(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_no")+"</label>");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_total")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            value = base.getAttribute("totvote", Boolean.TRUE.toString());
            ret.append("<label for=\"totvotetrue\"><input type=\"radio\" name=\"totvote\" id=\"totvotetrue\" value=\""+Boolean.TRUE.toString()+"\" ");
            if(Boolean.TRUE.toString().equals(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_yes")+"</label>");
            ret.append("&nbsp;&nbsp;&nbsp;");
            ret.append("<label for=\"totvotefalse\"><input type=\"radio\" name=\"totvote\" id=\"totvotefalse\" value=\""+Boolean.FALSE.toString()+"\" ");
            if(Boolean.FALSE.toString().equals(value))
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_no")+"</label>");
            ret.append("</td>");
            ret.append("</tr>");
            ret.append("</table> ");
            ret.append("</fieldset> ");



            ret.append("<fieldset> ");
            ret.append("<legend>"+paramRequest.getLocaleString("lblDoAdmin_lookfeelResults")+"</legend>");
            ret.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");
            ret.append("<tr><td width=\"250\"></td><td></td></tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+ paramRequest.getLocaleString("lblDoAdmin_display_results")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            value = base.getAttribute("display", Display.SLIDE.toString());
            ret.append("<label for=\"popup\"><input type=\"radio\" name=\"display\" id=\"popup\" value=\""+Display.POPUP.toString()+"\" ");
            if( Display.POPUP.toString().equals(value) )
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_window")+"</label>");

            ret.append("&nbsp;&nbsp;&nbsp;");
            ret.append("<label for=\"slide\"><input type=\"radio\" name=\"display\" id=\"slide\" value=\""+Display.SLIDE.toString()+"\" ");
            if( Display.SLIDE.toString().equals(value) )
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_slide")+"</label>");

            ret.append("&nbsp;&nbsp;&nbsp;");
            ret.append("<label for=\"simple\"><input type=\"radio\" name=\"display\" id=\"simple\" value=\""+Display.SIMPLE.toString()+"\" ");
            if( Display.SIMPLE.toString().equals(value) )
                ret.append(" checked=\"checked\"");
            ret.append("/> "+paramRequest.getLocaleString("lblDoAdmin_accessible")+"</label>");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_message")+" "+paramRequest.getLocaleString("lblDoAdmin_msgResults")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"msg_viewresults\" value=\""+base.getAttribute("msg_viewresults","")+"\" maxlength=\"25\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td align=\"right\">"+paramRequest.getLocaleString("lblDoAdmin_message")+" "+paramRequest.getLocaleString("lblDoAdmin_numVotes")+":&nbsp;</td>");
            ret.append("<td>");
            ret.append("<input type=\"text\" name=\"msg_vote\" value=\""+base.getAttribute("msg_vote","")+"\" maxlength=\"55\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_message")+" "+paramRequest.getLocaleString("lblDoAdmin_windowClose")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"msg_closewin\" value=\""+base.getAttribute("msg_closewin","")+"\" maxlength=\"25\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_message")+" "+paramRequest.getLocaleString("lblDoAdmin_totalVotes")+":&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"text\" name=\"msg_totvotes\" value=\""+base.getAttribute("msg_totvotes","")+"\" maxlength=\"25\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" />");
            ret.append("</td>");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+ paramRequest.getLocaleString("lblDoAdmin_imgBackground") + "&nbsp, <span class=\"enfasis\">(jpg, jpeg, gif, png)</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\">");
            ret.append("<input type=\"file\" size=\"40\" name=\"backimgres\" onChange=\"isFileType(this,'jpg|jpeg|gif|png');\" maxlength=\"80\" />");
            ret.append("</td>");
            ret.append("</tr>");
            if( base.getAttribute("backimgres")!=null ) {
                ret.append("<tr>");
                ret.append("<td class=\"datos\">&nbsp;</td>");
                ret.append("<td class=\"valores\">");
                ret.append(admResUtils.displayImage(base, base.getAttribute("backimgres"), "backimgres") +"<br /><input type=\"checkbox\" name=\"nobackimgres\" value=\"1\" /> <span class=\"enfasis\">"+paramRequest.getLocaleString("lblDoAdmin_removeImage")+" <i>"+base.getAttribute("backimgres")+"</i></span>");
                ret.append("</td>");
                ret.append("</tr>");
            }
            ret.append("</table>");
            ret.append("</fieldset>");



            ret.append("<fieldset> ");
            ret.append("<legend>"+paramRequest.getLocaleString("usrmsg_SettingsNewWindow")+"</legend>");
            ret.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");
            ret.append("<tr><td width=\"250\"></td><td></td></tr>");
            ret.append(admResUtils.loadWindowConfiguration(base, paramRequest));
            ret.append("</table> ");
            ret.append("</fieldset>");



            ret.append("<fieldset> ");
            ret.append("<legend>"+paramRequest.getLocaleString("lblDoAdmin_Advanced")+"</legend>");
            ret.append("<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\"> ");
            ret.append("<tr><td width=\"250\"></td><td></td></tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_jsp")+":&nbsp;</td>");
            ret.append("<td class=\"valores\"><input type=\"text\" name=\"jspfile\" value=\""+base.getAttribute("jspfile", "")+"\" maxlength=\"20\" dojoType=\"dijit.form.ValidationTextBox\" promptMessage=\"to do\" invalidMessage=\"to do\" regExp=\".+\" /></td> ");
            ret.append("</tr>");

            ret.append("<tr>");
            ret.append("<td class=\"datos\">"+paramRequest.getLocaleString("lblDoAdmin_Template")+" <span class=\"enfasis\">(xsl, xslt)</span>:&nbsp;</td>");
            ret.append("<td class=\"valores\"><input type=\"file\" size=\"40\" name=\"template\" onChange=\"isFileType(this, 'xsl|xslt');\" maxlength=\"80\" /></td>");
            ret.append("</tr>");
            ret.append("<tr>");
            ret.append("<td class=\"datos\">&nbsp;</td>");
            if(!"".equals(base.getAttribute("template", "").trim())) {
                ret.append("<td class=\"valores\">"+paramRequest.getLocaleString("lblDoAdmin_curTemplate")+":&nbsp;");
                ret.append("<a href=\"\">"+base.getAttribute("template")+"</a>");
                ret.append("</td>");
            }else {
                ret.append("<td class=\"valores\">"+paramRequest.getLocaleString("lblDoAdmin_defTemplate")+":&nbsp;Poll.xsl</td>");
            }
            ret.append("</tr>");

            ret.append("</table> ");
            ret.append("</fieldset> ");


            ret.append("<fieldset>");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
            ret.append(" <tr><td>");
            ret.append(" <button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submitImgGal\" value=\"Submit\" onclick=\"if(jsValida(dojo.byId('frmAdmRes'))) return true; else return false;\">"+paramRequest.getLocaleString("lblDoAdmin_submit")+"</button>&nbsp;");
            ret.append(" <button dojoType=\"dijit.form.Button\" type=\"reset\">"+paramRequest.getLocaleString("lblDoAdmin_reset")+"</button>");
            ret.append(" </td></tr>");
            ret.append("</table> ");
            ret.append("</fieldset>");
            ret.append("</form> ");
            ret.append("<span class=\"requerido\">*</span> " + paramRequest.getLocaleString("lblDoAdmin_required"));
            ret.append("</div>");
            ret.append(getAdminScript(paramRequest));
        }
        catch(Exception e) {
            log.error(e);
        }
        return ret.toString();
    }

    /**
     * Metodo de validaci?n en javascript para la encuesta.
     * 
     * @param paramRequest the param request
     * @return the admin script
     * @throws SWBResourceException the sWB resource exception
     */
    private String getAdminScript(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder script = new StringBuilder();

        script.append("\n<script type=\"text/javascript\">\n");
        script.append("<!--\n");
        script.append("var swOk=0, optionObj;");
        script.append("function jsValida(pForm)");
        script.append("{");
        script.append("   if(pForm.question.value==null || pForm.question.value=='' || pForm.question.value==' ')");
        script.append("   {");
        script.append("       alert('" + paramRequest.getLocaleString("lblDoAdmin_msgQuestion") + "');");
        script.append("       pForm.question.focus();");
        script.append("       return false;");
        script.append("   }");
        script.append("   if(pForm.selOption.length < 2)");
        script.append("   {");
        script.append("       alert('" + paramRequest.getLocaleString("lblDoAdmin_msgOption") + "');");
        script.append("       pForm.txtOption.focus();");
        script.append("       return false;");
        script.append("   }");
        script.append("   if (!setPrefix(pForm.selLink, 'http://')) return false;");
        script.append("   if(!isFileType(pForm.imgencuesta, 'jpg|jpeg|gif|png')) return false;");
        script.append("   if(!isFileType(pForm.button, 'jpg|jpeg|gif|png')) return false;");
        script.append("   if(!isFileType(pForm.backimgres, 'jpg|jpeg|gif|png')) return false;");
        script.append("   if(!isFileType(pForm.template, 'xsl|xslt')) return false;");
//            ret.append("   if(pForm.textcolor.value==null || pForm.textcolor.value=='' || pForm.textcolor.value==' ')");
//            ret.append("       pForm.textcolor.value='#'+ document.selColor.getColor();");
//            ret.append("   if(!isHexadecimal(pForm.textcolor)) return false;");
//            ret.append("   if(pForm.textcolorres.value==null || pForm.textcolorres.value=='' || pForm.textcolorres.value==' ')");
//            ret.append("       pForm.textcolorres.value='#'+ document.selColorBack.getColor();");
//            ret.append("   if(!isHexadecimal(pForm.textcolorres)) return false;");
        script.append("   if(!isNumber(pForm.time)) return false;");
//            ret.append("   if(!isNumber(pForm.branches)) return false;");
        script.append("   if(!isNumber(pForm.width)) return false;");
        script.append("   if(!isNumber(pForm.height)) return false;");
        script.append("   if(!isNumber(pForm.top)) return false;");
        script.append("   if(!isNumber(pForm.left)) return false;");
        script.append("   pForm.option.value='';");
        script.append("   for(var i=0; i<pForm.selOption.length; i++)");
        script.append("   {");
        script.append("       if(i>0) pForm.option.value+=\"|\";");
        script.append("       pForm.option.value+=pForm.selOption.options[i].value;");
        script.append("   }");
        script.append("   pForm.link.value='';");
        script.append("   for(var i=0; i<pForm.selLink.length; i++)");
        script.append("   {");
        script.append("       if(i>0) pForm.link.value+=\"|\";");
        script.append("       pForm.link.value+=pForm.selLink.options[i].value;");
        script.append("   }");
        script.append("   return true;");
        script.append("}");
        script.append(admResUtils.loadAddOption());
        script.append(admResUtils.loadEditOption());
        script.append(admResUtils.loadUpdateOption());
        script.append(admResUtils.loadDeleteOption());
        script.append(admResUtils.loadDuplicateOption());
        script.append(admResUtils.loadIsFileType());
        script.append(admResUtils.loadIsNumber());
        script.append(admResUtils.loadSetPrefix());
        script.append(admResUtils.loadIsHexadecimal());
        script.append("\n-->");
        script.append("\n</script>");

        return script.toString();
    }

    /**
     * Gets the render script.
     * 
     * @param paramRequest the param request
     * @return the render script
     * @throws SWBResourceException the sWB resource exception
     */
    private String getRenderScript(SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base = paramRequest.getResourceBase();
        StringBuilder script = new StringBuilder();

        StringBuilder win = new StringBuilder();
        win.append("menubar=" + base.getAttribute("menubar", "no"));
        win.append(",toolbar=" + base.getAttribute("toolbar", "no"));
        win.append(",status=" + base.getAttribute("status", "no"));
        win.append(",location=" + base.getAttribute("location", "no"));
        win.append(",directories=" + base.getAttribute("directories", "no"));
        win.append(",scrollbars=" + base.getAttribute("scrollbars", "no"));
        win.append(",resizable=" + base.getAttribute("resizable", "no"));
        win.append(",width=" + base.getAttribute("width", "360"));
        win.append(",height=" + base.getAttribute("height", "260"));
        win.append(",top=" + base.getAttribute("top", "125"));
        win.append(",left=" + base.getAttribute("left", "220"));

        script.append("\n<script type=\"text/javascript\">\n");
        script.append("<!--\n");
        script.append("dojo.require(\"dojo.fx\");");

        script.append("function buscaCookie(cocacola, rgName, isCLIValidable, url) {");
        script.append("  var numcom = getCookie(cocacola);");
        script.append("  if(numcom=='SI' && isCLIValidable) {");
        script.append("    alert('"+paramRequest.getLocaleString("msgDoView_msgVote")+"');");
        script.append("    return;");
        script.append("  }");
        script.append("  grabaEncuesta(rgName, url);");
        script.append("}");

        script.append("function grabaEncuesta(rgName, url) {");
        script.append("    var optValue;");
        script.append("    var rg = document.getElementsByName(rgName);");
        script.append("    for(var i=0; i<rg.length; i++) {");
        script.append("        var e = rg.item(i);");
        script.append("        if(e.type=='radio')");
        script.append("            if(e.checked) {");
        script.append("                optValue = e.value;");
        script.append("                break;");
        script.append("            }");
        script.append("    }");
        script.append("    if( optValue!=null ) {");
        boolean isPopup = Boolean.valueOf(base.getAttribute("display", "true")).booleanValue();
        if( isPopup )
            script.append("   window.open(url+'&radiobutton='+optValue, '_newenc', '"+win+"');");
        else
            script.append("   getHtml(url+'&radiobutton='+optValue,'"+PREF+base.getId()+"'); expande();");
        script.append("   }else {");
        script.append("      alert('"+paramRequest.getLocaleString("msgDoView_msgAnswer")+"');");
        script.append("   }");
        script.append("}");

        script.append("function abreResultados(ruta) {");
        script.append("   window.open(ruta,'_blank','"+win+"');");
        script.append("}");

        script.append("function expande() {");
        script.append("   var anim1 = dojo.fx.wipeIn( {node:'"+PREF+base.getId()+"', duration:500 });");
        script.append("   var anim2 = dojo.fadeIn({node:'"+PREF+base.getId()+"', duration:650});");
        script.append("   dojo.fx.combine([anim1,anim2]).play();");
        script.append("}");

        script.append("function collapse() {");
        script.append("   var anim1 = dojo.fx.wipeOut( {node:'"+PREF+base.getId()+"', duration:500 });");
        script.append("   var anim2 = dojo.fadeOut({node:'"+PREF+base.getId()+"', duration:650});");
        script.append("   dojo.fx.combine([anim1, anim2]).play();");
        script.append(" }");
        script.append("\n-->");
        script.append("\n</script>");

        return script.toString();
    }
}