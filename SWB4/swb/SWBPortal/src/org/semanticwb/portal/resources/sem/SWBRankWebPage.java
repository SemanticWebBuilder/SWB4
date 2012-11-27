/*
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
 */
package org.semanticwb.portal.resources.sem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBRankWebPage.
 */
public class SWBRankWebPage extends org.semanticwb.portal.resources.sem.base.SWBRankWebPageBase
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBRankWebPage.class);

    /** The Constant PREFIX. */
    private static final String PREFIX = "_voted";

    /** The full star path. */
    private String fullStarPath;

    /** The half star path. */
    private String halfStarPath;

    /** The empty star path. */
    private String emptyStarPath;

    /** The Constant sp_rank. */
    private static final org.semanticwb.platform.SemanticProperty sp_rank = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");

    /** The Constant sp_reviews. */
    private static final org.semanticwb.platform.SemanticProperty sp_reviews = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");

    /**
     * Instantiates a new sWB rank web page.
     */
    public SWBRankWebPage()
    {
    }

    /**
     * Instantiates a new sWB rank web page.
     *
     * @param base the base
     */
    public SWBRankWebPage(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        fullStarPath =  getFullStar();
        halfStarPath = getHalfStar();
        emptyStarPath = getEmptyStar();
        if (null == fullStarPath)
        {
            fullStarPath = "/swbadmin/resources/ranking/fullstar.png";
        }
        if (null == halfStarPath)
        {
            halfStarPath = "/swbadmin/resources/ranking/halfstar.png";
        }
        if (null == emptyStarPath)
        {
            emptyStarPath = "/swbadmin/resources/ranking/emptystar.png";
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if( paramRequest.getMode().equals("vote") )
            doVote(request, response, paramRequest);
        else if( paramRequest.getMode().equals("on") )
            doOn(request, response, paramRequest);
        else if( paramRequest.getMode().equals("off") )
            doOff(request, response, paramRequest);
        else
            super.processRequest(request, response, paramRequest);
    }

    /**
     * Do on.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doOn(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        PrintWriter out = response.getWriter();

        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);
        WebPage wp = null;
        double rank;
        if( obj!=null ) {
            rank = obj.getDoubleProperty(sp_rank);
        }else {
            wp = paramRequest.getWebPage();
            rank = wp.getRank();
        }

        int idx;
        try {
            idx = Integer.parseInt(request.getParameter("idx"));
        }catch(NumberFormatException nfe) {
            idx = 1;
        }

        out.println("<ul>");
        for(int i=1; i<=idx; i++) {
            out.println("<li><a href=\"#\" onclick=\"vote("+i+")\" onmouseout=\"lightoff("+i+")\" title=\""+paramRequest.getLocaleString("msg_give")+" "+i+" "+paramRequest.getLocaleString("lbl_stars")+"\">");
            out.println("<img src=\""+SWBPlatform.getContextPath()+fullStarPath+"\" alt=\""+paramRequest.getLocaleString("msg_has")+" "+((0.0f + rank)/10.0f)+" "+paramRequest.getLocaleString("lbl_stars")+"\"/>");
            out.println("</a></li>");
        }
        idx++;
        for(int i=idx; i<=5; i++) {
            out.println("<li><a href=\"#\" onclick=\"vote("+i+")\" onmouseout=\"lightoff()\" title=\""+paramRequest.getLocaleString("msg_give")+" "+i+" "+paramRequest.getLocaleString("lbl_stars")+"\">");
            out.println("<img src=\""+SWBPlatform.getContextPath()+emptyStarPath+"\" alt=\""+paramRequest.getLocaleString("msg_has")+" "+((0.0f + rank)/10.0f)+" "+paramRequest.getLocaleString("lbl_stars")+"\"/>");
            out.println("</a></li>");
        }
        out.println("</ul>");
        out.flush();
        out.close();
    }

    /**
     * Do off.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doOff(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        PrintWriter out = response.getWriter();

        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);
        WebPage wp = null;
        double rank;
        if( obj!=null ) {
            rank = obj.getDoubleProperty(sp_rank);
        }else {
            wp = paramRequest.getWebPage();
            rank = wp.getRank();
        }

        String cookieName = obj==null?paramRequest.getWebPage().getSemanticObject().getId()+PREFIX:obj.getId()+PREFIX;
        Cookie[] cookies = request.getCookies();
        if( cookies!=null )
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals(cookieName)) {
                   doVote(request, response, paramRequest);
                }
            }

        out.println("<ul>");
        for(int i=1; i<=5; i++) {
            out.println("<li><a href=\"#\" onclick=\"vote("+i+")\" onmouseover=\"lighton("+i+")\" title=\""+paramRequest.getLocaleString("msg_give")+" "+i+" "+paramRequest.getLocaleString("lbl_stars")+"\">");
            out.println("<img src=\""+SWBPlatform.getContextPath()+emptyStarPath+"\" alt=\""+paramRequest.getLocaleString("msg_has")+" "+((0.0f + rank)/10.0f)+" "+paramRequest.getLocaleString("lbl_stars")+"\"/>");
            out.println("</a></li>");
        }
        out.println("</ul>");
        out.flush();
        out.close();
    }


    /**
     * Do vote.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doVote(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        PrintWriter out = response.getWriter();

        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);

        WebPage wp = null;
        double rank;
        if( obj!=null ) {
            rank = obj.getDoubleProperty(sp_rank);
        }else {
            wp = paramRequest.getWebPage();
            rank = wp.getRank();
        }

        out.println("<ul>");
        for(int i=1; i<=5; i++) {
            out.println("<li><a href=\"#\" title=\""+paramRequest.getLocaleString("rate")+"="+Math.round(Math.floor(rank))+"\">");
            out.println("<img src=\""+SWBPlatform.getContextPath()+getStar(i,rank*10)+"\" alt=\""+paramRequest.getLocaleString("msg_has")+" "+((0.0f + rank)/10.0f)+" "+paramRequest.getLocaleString("lbl_stars")+"\"/>");
            out.println("</a></li>");
        }
        out.println("</ul>");
        if( obj!=null )
            out.println("<p>"+paramRequest.getLocaleString("msgDoViewVotes")+": "+obj.getLongProperty(sp_reviews)+"</p>");
        else
            out.println("<p>"+paramRequest.getLocaleString("msgDoViewVotes")+": "+wp.getReviews()+"</p>");

//        SWBResourceURL url = paramRequest.getActionUrl();
//        url.setCallMethod(SWBResourceURL.Call_DIRECT);
//        url.setMode("vote");
//        out.println("<script type=\"text/javascript\">");
//        out.println("<!--");
//        out.println("function vote(val) {");
//        if( obj!=null ) {
//            out.println("  var uri='"+URI+"';");
//            out.println("  uri=escape(uri);");
//            out.println("  var url = '"+url+"?rating='+escape(val)+'&uri='+uri;");
//            out.println("  postHtml(url,'rate_"+obj.getId()+"');");
//        }else {
//            out.println("  var url = '"+url+"?rating='+escape(val);");
//            out.println("  postHtml(url,'rate_"+wp.getId()+"');");
//        }
//        out.println("  alert('"+paramRequest.getLocaleString("msg_voteAcepted")+"');");
//        out.println("}");
//        out.println("-->");
//        out.println("</script>");

        out.flush();
        out.close();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        PrintWriter out = response.getWriter();

        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);

        WebPage wp = null;
        double rank;
        if( obj!=null ) {
            rank = obj.getDoubleProperty(sp_rank);
        }else {
            wp = paramRequest.getWebPage();
            rank = wp.getRank();
        }

        boolean isVoted = false;
        String cookieName = obj==null?paramRequest.getWebPage().getSemanticObject().getId()+PREFIX:obj.getId()+PREFIX;
        Cookie[] cookies = request.getCookies();
        if( cookies!=null )
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals(cookieName)) {
                   isVoted = true;
                   break;
                }
            }

        if( obj!=null )
            out.println("<div class=\"swb-rate\" id=\"rate_"+obj.getId()+"\">");
        else
            out.println("<div class=\"swb-rate\" id=\"rate_"+wp.getId()+"\">");
        out.println("<ul>");
        for(int i=1; i<=5; i++) {
            if(isVoted) {
                out.println("<li>");
                out.println("<a href=\"#\" title=\""+paramRequest.getLocaleString("rate")+"="+Math.round(Math.floor(rank))+"\">");
                out.println("<img src=\""+SWBPlatform.getContextPath()+getStar(i,rank*10)+"\" alt=\""+paramRequest.getLocaleString("msg_has")+" "+((0.0f + rank)/10.0f)+" "+paramRequest.getLocaleString("lbl_stars")+"\"/>");
                out.println("</a></li>");
            }else {
                out.println("<li><a href=\"#\" onclick=\"vote("+i+")\" onmouseover=\"lighton("+i+")\" onmouseout=\"lightoff()\" title=\""+paramRequest.getLocaleString("msg_give")+" "+i+" "+paramRequest.getLocaleString("lbl_stars")+"\">");
                out.println("<img src=\""+SWBPlatform.getContextPath()+emptyStarPath+"\" alt=\""+paramRequest.getLocaleString("msg_has")+" "+((0.0f + rank)/10.0f)+" "+paramRequest.getLocaleString("lbl_stars")+"\"/>");
                out.println("</a></li>");
            }
        }
        out.println("</ul>");

        if( isVoted ) {
            if( obj!=null )
                out.println("<p>"+paramRequest.getLocaleString("msgDoViewVotes")+": "+obj.getLongProperty(sp_reviews)+"</p>");
            else
                out.println("<p>"+paramRequest.getLocaleString("msgDoViewVotes")+": "+wp.getReviews()+"</p>");
        }

        out.println("</div>");
        if( !isVoted ) {
            SWBResourceURL url;
            url = paramRequest.getRenderUrl();
            url.setCallMethod(SWBResourceURL.Call_DIRECT);

            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("dojo.require(\"dijit.dijit\");");
            out.println("function lighton(idx) {");
            String tmpUrl = url.setMode("on").toString(); tmpUrl=tmpUrl.replace('\'', '-');
            if( obj!=null ) {
                out.println("  var uri='"+URI+"';");
                out.println("  uri=escape(uri);");
                out.println("  var url = '"+tmpUrl+"?idx='+idx+'&uri='+uri;");
                out.println("  postHtml(url,'rate_"+obj.getId()+"');");
            }else {
                out.println("  var url = '"+tmpUrl+"?idx='+idx;");
                out.println("  postHtml(url,'rate_"+wp.getId()+"');");
            }
            out.println("}");

            out.println("function lightoff() {");
            tmpUrl = url.setMode("off").toString(); tmpUrl=tmpUrl.replace('\'', '-');
            if( obj!=null ) {
                out.println("  var uri='"+URI+"';");
                out.println("  uri=escape(uri);");
                out.println("  var url = '"+tmpUrl+"?uri='+uri;");
                out.println("  postHtml(url,'rate_"+obj.getId()+"');");
            }else {
                out.println("  var url = '"+tmpUrl+"';");
                out.println("  postHtml(url,'rate_"+wp.getId()+"');");
            }
            out.println("}");


            url = paramRequest.getActionUrl();
            url.setCallMethod(SWBResourceURL.Call_DIRECT);
            out.println("function vote(val) {");
            tmpUrl = url.setMode("vote").toString(); tmpUrl=tmpUrl.replace('\'', '-');
            if( obj!=null ) {
                out.println("  var uri='"+URI+"';");
                out.println("  uri=escape(uri);");
                out.println("  var url = '"+tmpUrl+"?rating='+val+'&uri='+uri;");
                out.println("  postHtml(url,'rate_"+obj.getId()+"');");
            }else {
                out.println("  var url = '"+tmpUrl+"?rating='+val;");
                out.println("  postHtml(url,'rate_"+wp.getId()+"');");
            }
            out.println("  alert('"+paramRequest.getLocaleString("msg_voteAcepted")+"');");
            out.println("}");
            out.println("-->");
            out.println("</script>");
        }
        out.flush();
        out.close();
    }

    /**
     * Prints the star.
     * 
     * @param current the current
     * @param rank the rank
     * @return the star
     * @throws SWBResourceException the sWB resource exception
     */
    private String getStar(int current, double rank)
    {
        String imgRank = emptyStarPath;
        int midl = (current*10)-7;
        int midt = (current*10)-2;
        if (rank>=midl&&rank<=midt)
            imgRank = halfStarPath;
        if (rank>midt)
            imgRank = fullStarPath;
        return imgRank;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);

        String cookieName = obj==null?response.getWebPage().getSemanticObject().getId()+PREFIX:obj.getId()+PREFIX;

        Cookie[] cookies = request.getCookies();
        if( cookies!=null )
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals(cookieName)) {
                    if( URI!=null )
                        response.setRenderParameter("uri", URI);
                    response.setMode("vote");
                    return;
                }
            }

        int vote;
        try {
            vote = Integer.parseInt(request.getParameter("rating"));
        }catch(NumberFormatException nfe) {
            vote = 0;
        }

        if( vote>0 ) {
            WebPage ws = response.getWebPage();
            double rank;
            long rev;

            if( obj==null ) {
                rank = ws.getRank();
                rev = ws.getReviews();
            }else {
                rank = obj.getDoubleProperty(sp_rank);
                rev = obj.getLongProperty(sp_reviews);
                response.setRenderParameter("uri", URI);
            }
            rank = rank * rev;
            rev++;
            rank = rank + vote;
            rank = rank / rev;

            if( obj==null ) {
                ws.setRank(rank);
                ws.setReviews(rev);
            }else {
                obj.setDoubleProperty(sp_rank, rank);
                obj.setLongProperty(sp_reviews, rev);
            }
            response.setMode(response.Mode_HELP);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doHelp(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String URI = request.getParameter("uri");
        SemanticObject obj = SemanticObject.createSemanticObject(URI);
        if( obj==null )
             obj = paramRequest.getWebPage().getSemanticObject();
        Cookie cookie = new Cookie(obj.getId()+PREFIX, "true");
        cookie.setPath("/");
        response.addCookie(cookie);

        doVote(request, response, paramRequest);
    }















//    @Override
//    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
//    {
//            PrintWriter out = response.getWriter();
//
//            out.println("            <script language=\"javascript\" type=\"text/javascript\" src=\"" + SWBPlatform.getContextPath() + "/swbadmin/js/upload.js\"></script>");
//            out.println("	<style type=\"text/css\">\n @import \"" + SWBPlatform.getContextPath() + "/swbadmin/css/upload.css\";\n </style>");
//            out.println("    <script type=\"text/javascript\">");
//           // out.println("  dojo.require(\"dojo.parser\");");
//            out.println("    var uploads_in_progress = 0;");
//            out.println();
//            out.println("    function beginAsyncUpload(ul,sid) {");
//            out.println("      ul.form.submit();");
//            out.println("    	uploads_in_progress = uploads_in_progress + 1;");
//            out.println("    	var pb = document.getElementById(ul.name + \"_progress\");");
//            out.println("    	pb.parentNode.style.display='block';");
//            out.println("    	new ProgressTracker(sid,{");
//            out.println("    		progressBar: pb,");
//            out.println("    		onComplete: function() {");
//            out.println("    			var inp_id = pb.id.replace(\"_progress\",\"\");");
//            out.println("    			uploads_in_progress = uploads_in_progress - 1;");
//            out.println("    			var inp = document.getElementById(inp_id);");
//            out.println("    			if(inp) {");
//            out.println("    				inp.value = sid;");
//            out.println("    			}");
//            out.println("    			pb.parentNode.style.display='none';");
//            out.println("    		},");
//            out.println("    		onFailure: function(msg) {");
//            out.println("    			pb.parentNode.style.display='none';");
//            out.println("    			alert(msg);");
//            out.println("    			uploads_in_progress = uploads_in_progress - 1;");
//            out.println("    		},");
//            out.println("            url: '"+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
//                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_EDIT)+"'");
//            out.println("    	});");
//            out.println("    }");
//            out.println();
//            out.println("	</script>");
//            out.println("<div class=\"swbform\">");
//            out.println("    <fieldset>");
//            out.println("    <table>");
//            out.println("                <tr><td width=\"200px\" align=\"right\">");
//            out.println("                    <label>Identificador &nbsp;</label>");
//            out.println("                </td><td>");
//            out.println("                    <span>"+getResourceBase().getId()+"</span>");
//            out.println("                </td></tr>");
//            out.println("            </table>");
//            out.println("            </fieldset>");
//            out.println("            <fieldset>");
//            out.println("                <legend>Datos Generales</legend>");
//            out.println("                <table>");
//            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"fullStar\">Imagen Estrella Completa &nbsp;</label></td>");
//            out.println("                    <td><iframe id=\"fullStarTransferFrame\" name=\"fullStarTransferFrame\" src=\"\" style=\"display:none\" ></iframe>");
//            out.println("                        <form enctype=\"multipart/form-data\"");
//            out.println("                        action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
//                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD)+"\"");
//            out.println("                        method=\"post\" target=\"fullStarTransferFrame\" >");
//            out.println("                        <input type=\"file\" name=\"fullStar\"");
//            out.println("                        onchange=\"beginAsyncUpload(this,'fullStar');\" />");
//            out.println("                        <div class=\"progresscontainer\" style=\"display: none;\"><div class=\"progressbar\" id=\"fullStar_progress\"></div></div>");
//            out.println("                        </form>");
//            out.println("                    </td></tr>");
//            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"halfStar\">Imagen de Media Estrella &nbsp;</label></td>");
//            out.println("                    <td><iframe id=\"halfStarTransferFrame\" name=\"halfStarTransferFrame\" src=\"\" style=\"display:none\" ></iframe>");
//            out.println("                        <form enctype=\"multipart/form-data\"");
//            out.println("                        action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
//                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD)+"\"");
//            out.println("                        method=\"post\" target=\"halfStarTransferFrame\" >");
//            out.println("                        <input type=\"file\" name=\"halfStar\"");
//            out.println("                        onchange=\"beginAsyncUpload(this,'halfStar');\" />");
//            out.println("                        <div class=\"progresscontainer\" style=\"display: none;\"><div class=\"progressbar\" id=\"halfStar_progress\"></div></div>");
//            out.println("                        </form>");
//            out.println("                    </td></tr>");
//            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"emptyStar\">Imagen de Estrella vacía &nbsp;</label></td>");
//            out.println("                    <td><iframe id=\"emptyStarTransferFrame\" name=\"emptyStarTransferFrame\" src=\"\" style=\"display:none\" ></iframe>");
//            out.println("                        <form enctype=\"multipart/form-data\"");
//            out.println("                        action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
//                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD)+"\"");
//            out.println("                        method=\"post\" target=\"emptyStarTransferFrame\" >");
//            out.println("                        <input type=\"file\" name=\"emptyStar\"");
//            out.println("                        onchange=\"beginAsyncUpload(this,'emptyStar');\" />");
//            out.println("                        <div class=\"progresscontainer\" style=\"display: none;\"><div class=\"progressbar\" id=\"emptyStar_progress\"></div></div>");
//            out.println("                        </form>");
//            out.println("                    </td></tr>");
//            out.println("                    <tr><td width=\"200px\" align=\"right\"><label for=\"cleanStars\">Limpiar lbl_stars &nbsp;</label></td>");
//            out.println("                    <td><form action=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP)
//                    .setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_REMOVE)+"\">");
//            out.println("                    <button type=\"submit\">Limpiar</button></form></td></tr>");
//            out.println("                </table>");
//            out.println("            </fieldset>");
//            out.println("</div>");
//
//
//    }

    /* (non-Javadoc)
 * @see org.semanticwb.portal.api.GenericResource#doAdminHlp(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
 */
@Override
    public void doAdminHlp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        final Percentage per = new Percentage();
        if (SWBResourceURL.Action_ADD.equals(paramRequest.getAction()))
        {
            try
            {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                HashMap<String,String> params = new HashMap<String,String>();
                // Create a factory for disk-based file items
                File tmpwrk = new File(SWBPortal.getWorkPath()+"/tmp");
                if (!tmpwrk.exists()) tmpwrk.mkdirs();
                FileItemFactory factory = new DiskFileItemFactory(1*1024*1024,tmpwrk);
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                //Create a progress listener
                ProgressListener progressListener = new ProgressListener(){
                    private long kBytes = -1;
                public void update(long pBytesRead, long pContentLength, int pItems) {
                    long mBytes = pBytesRead / 10000;
                    if (kBytes == mBytes) {
                    return;
                    }
                    kBytes = mBytes;
                    int percent = (int)(pBytesRead * 100 / pContentLength);
                    per.setPercentage(percent);
                }
                };
                upload.setProgressListener(progressListener);
                // Parse the request
                List items = upload.parseRequest(request); /* FileItem */
                FileItem currentFile = null;
                // Process the uploaded items
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString();
                        params.put(name, value);
                    } else {
                        currentFile = item;
//                        String fieldName = item.getFieldName();
//                        String fileName = item.getName();
//                        String contentType = item.getContentType();
//                        boolean isInMemory = item.isInMemory();
//                        long sizeInBytes = item.getSize();
//                        File uploadedFile = new File();
//                        item.write(uploadedFile);
                    }
                }
                request.getSession(true).setAttribute(currentFile.getFieldName(), per);
                String path = SWBPortal.getWorkPath()+getResourceBase().getWorkPath();
                File file = new File(path);
                if (!file.exists()) file.mkdirs();
                String name = currentFile.getFieldName()+currentFile.getName().substring(currentFile.getName().lastIndexOf("."));
                currentFile.write(new File(path+"/"+name));
                path = SWBPortal.getWebWorkPath()+getResourceBase().getWorkPath();
                if (currentFile.getFieldName().equals("fullStar"))
                {
                    setFullStar(path+"/"+name);
                    fullStarPath=getFullStar();
                }
                if (currentFile.getFieldName().equals("halfStar"))
                {
                    setHalfStar(path+"/"+name);
                    halfStarPath=getHalfStar();
                }
                if (currentFile.getFieldName().equals("emptyStar"))
                {
                    setEmptyStar(path+"/"+name);
                    emptyStarPath=getEmptyStar();
                }
                per.setPercentage(100);

            } catch (Exception ex)
            {
                log.error(ex);
            }

        }else if (SWBResourceURL.Action_EDIT.equals(paramRequest.getAction()))
        {
            Percentage pers = (Percentage) request.getSession(true).getAttribute(request.getParameter("sid"));
            PrintWriter out = response.getWriter();
            if (null!=pers)
            {
                out.println(pers.getPercentage());
            }
            else
            {
                out.println(0);
            }
        }else if (SWBResourceURL.Action_REMOVE.equals(paramRequest.getAction()))
        {
            getSemanticObject().removeProperty(SWBRankWebPage.rankwebpage_fullStar);
            getSemanticObject().removeProperty(SWBRankWebPage.rankwebpage_halfStar);
            getSemanticObject().removeProperty(SWBRankWebPage.rankwebpage_emptyStar);
            fullStarPath = "/swbadmin/resources/ranking/fullstar.png";
            halfStarPath = "/swbadmin/resources/ranking/halfstar.png";
            emptyStarPath = "/swbadmin/resources/ranking/emptystar.png";
            //doAdmin(request, response, paramRequest);

            SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMIN).setCallMethod(SWBResourceURL.Call_CONTENT);
            PrintWriter out = response.getWriter();
            out.println("<html><head><meta http-equiv=\"Refresh\" CONTENT=\"0; URL=" + url + "\" /><script>window.location='" + url + "';</script></head></html>");
            out.flush();
        }
    }


    /**
     * The Class Percentage.
     */
    private class Percentage
    {

        /** The per. */
        int per = 0;

        /**
         * Sets the percentage.
         *
         * @param per the new percentage
         */
        public void setPercentage(int per)
        {
            this.per = per;
        }

        /**
         * Gets the percentage.
         *
         * @return the percentage
         */
        public int getPercentage()
        {
            return per;
        }
    }
}