/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.resources.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.Twitter;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 *
 * @author carlos.ramos
 */
public class PostSummary extends GenericAdmResource {
    /** The log. */
    private static Logger log = SWBUtils.getLogger(PostSummary.class);
    
    public static final int PAGE_SIZE = 1000; //Líneas por página
    public static final String Mode_JSON = "json";
    public static final String Mode_FILLGRD = "fg";
    public static int xxx=1000;
    
    /** The tpl. */
    javax.xml.transform.Templates tpl;
    
    /** The web work path. */
    String webWorkPath = "/work";
    
    /** The path. */
    final String path = SWBPlatform.getContextPath() +"/swbadmin/jsp/social/reports/";
    
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        
//System.out.println("\n\n\nSWBPortal.getWebWorkPath()="+SWBPortal.getWebWorkPath());
//System.out.println("SWBPortal.getWorkPath()="+SWBPortal.getWorkPath());
//System.out.println("base.getWorkPath()="+base.getWorkPath());
//System.out.println("path="+path);
//System.out.println("\n\n");

//        webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath();
//        if(!base.getAttribute("template","").isEmpty()) {
//            try {
//                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
//                path=webWorkPath + "/";
//            }catch(Exception e) {
//                log.error("Error while loading resource template: "+base.getId(), e);
//            }
//        }
//        if(tpl==null) {
//            try {
//                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(SWBPortal.getWebWorkPath()+"/xsl/reports/psummary.xsl"));
//            }catch(Exception e) {
//                log.error("Error while loading default resource template: "+base.getId(), e);
//            }
//        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
        if(Mode_JSON.equals(mode))
            doJson(request, response, paramRequest);
        /*else if(Mode_FILLGRD.equals(mode))
            doFillReport(request, response, paramRequest);*/
        else
            super.processRequest(request, response, paramRequest);
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        final String myPath = path+"postSummary.jsp";
        if (request != null) {
            RequestDispatcher dis = request.getRequestDispatcher(myPath);
            if(dis != null) {
                try {
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                    e.printStackTrace(System.out);
                }
            }
        }
    }
    
    public void _doJson(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        PrintWriter out = response.getWriter();
        
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        
        
//////////////////////
int ipage;
try {
    ipage = Integer.parseInt(request.getParameter("ipage"));
}catch (NumberFormatException nfe) {
    ipage = 1;
}
Iterator<PostIn> itposts = PostIn.ClassMgr.listPostIns(wsite);
long el = SWBUtils.Collections.sizeOf(itposts);
long paginas = el / PAGE_SIZE;
if(el % PAGE_SIZE != 0) {
    paginas++;
}
long inicio = 0;
long fin = PAGE_SIZE;

// Mantiene la consistencia al eliminar elementos
if(ipage>paginas)
    ipage--;

inicio = (ipage * PAGE_SIZE) - PAGE_SIZE;
fin = (ipage * PAGE_SIZE);

if(ipage < 1 || ipage > paginas) {
    ipage = 1;
}
if(inicio < 0) {
    inicio = 0;
}
if(fin < 0) {
    fin = PAGE_SIZE;
}
if(fin > el) {
    fin = el;
}
if(inicio > fin) {
    inicio = 0;
    fin = PAGE_SIZE;
}
if(fin - inicio > PAGE_SIZE) {
    inicio = 0;
    fin = PAGE_SIZE;
}
inicio++;
//////////////////////


        SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
        
        out.println("<script type=\"text/javascript\">");
        out.println(" dojo.require('dijit.layout.ContentPane');");
        out.println(" dojo.require('dijit.dijit');");
        out.println(" dojo.require('dojox.grid.DataGrid');");
        out.println(" dojo.require('dojo.data.ItemFileReadStore');");
        out.println(" dojo.require('dojo.parser');");
        
        
        out.println(" function fillGrid(grid, uri) {");
        out.println("   grid.store = new dojo.data.ItemFileReadStore({url: uri+'?ipage="+ipage+"'});");
        out.println("   grid._refresh();");
        out.println(" }");
        
out.println("function x_(value) {");
out.println(" if(value==2)");
out.println("  return '/work/positivo.jpg';");
out.println(" else if(value==1)");
out.println("  return '/work/negativo.jpg';");
out.println(" else");
out.println("  return '/work/neutro.jpg';");
out.println("}");
        
        out.println(" function apply() {");
        out.println("   var grid = dijit.byId('gridMaster');");
        out.println("   fillGrid(grid, '"+url.setMode(Mode_FILLGRD)+"');");
        out.println("  ");
        out.println(" }");
        
        out.println(" var layout= null;");
        out.println(" var jStrMaster = null;");
        out.println(" var gridMaster = null;");
        out.println(" var gridResources = null;");

        out.println(" dojo.addOnLoad(function() {");
        out.println("   layout= [");
        out.println("      { field:'fl',  width:'50px', name:'Num' },");
        out.println("      { field:'cta', width:'50px', name:'Cuenta' },");
        out.println("      { field:'sn',  width:'100px',name:'Red social' },");
        out.println("      { field:'date',width:'150px',name:'Fecha' },");
        out.println("      { field:'msg', width:'300px',name:'Mensaje' },");
        out.println("      { field:'feel',width:'80px', name:'Sentimiento', formatter:function(value){var src=x_(value);return '<img src=\"'+src+'\" />';} },");
        out.println("      { field:'int', width:'50px', name:'Intensidad' },");
        out.println("      { field:'user',width:'100px',name:'Usuario' },");
        out.println("      { field:'fllwrs', width:'80px', name:'Seguidores' },");
        out.println("      { field:'frds',width: '50px',name:'Amigos' }");
        out.println("   ];");

        out.println("   gridMaster = new dojox.grid.DataGrid({");
        out.println("      escapeHTMLInData: 'true',");     
        out.println("      preload: 'true',");     
        out.println("      id: 'gridMaster',");
        out.println("      structure: layout,");
        out.println("      rowSelector: '10px',");
        out.println("      rowsPerPage: '25'");
        out.println("   }, 'gridMaster');");
        out.println("   gridMaster.startup();");
        
        out.println("   fillGrid(gridMaster, '"+url.setMode(Mode_FILLGRD)+"');");
        out.println(" });");
        out.println("</script>");
        out.println("<div id=\"ctnergrid\" style=\"height:600px; width:99%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">");
        out.println("   <div id=\"gridMaster\"></div>");
        out.println("</div>");
        
// paginación
if(paginas > 1) {
    SWBResourceURL pagURL = paramRequest.getRenderUrl().setMode(Mode_JSON);
    StringBuilder html = new StringBuilder();
    html.append("<div class=\"\"  style=\"text-align:center\">");
    String nextURL = "#";
    String previusURL = "#";
    if (ipage < paginas) {
        nextURL = paramRequest.getRenderUrl().setParameter("ipage", Integer.toString(ipage+1)).toString();
    }
    if (ipage > 1) {
        previusURL = paramRequest.getRenderUrl().setParameter("ipage", Integer.toString(ipage-1)).toString();
    }
    if (ipage > 1) {
        html.append("<a class=\"amfr-pagi\" href=\""+previusURL+"\">Anterior</a>&nbsp;");
    }
    for (int i=ipage-2<=0?1:ipage-2; i<=ipage+2; i++)
    {
        if(i==ipage)
            html.append("<strong>");
        else
            html.append("<a class=\"amfr-pagi\" href=\""+pagURL.setParameter("ipage", Integer.toString(i))+"\">");
        html.append(i);
        if(i==ipage)
            html.append("</strong>&nbsp;");
        else
            html.append("</a>&nbsp;");
    }
    if (ipage != paginas)
    {
        html.append("<a class=\"amfr-pagi\" href=\""+nextURL+"\">Siguiente</a>");
    }
    html.append("</div>");
    out.println(html);
}        
    }
    
    private void doJson(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        
        String lang = paramsRequest.getUser().getLanguage();
        Locale locale = new Locale("es");
        NumberFormat nf = NumberFormat.getIntegerInstance(locale);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy HH:mm", locale);
        DecimalFormat df = new DecimalFormat("#.#");
        
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("identifier", "fl");
            jobj.put("label", "fl");
            jobj.put("items", jarr);
        }catch (JSONException jse) {
            jse.printStackTrace(System.out);
        }
        
        Resource base = getResourceBase();
        WebSite wsite = base.getWebSite();
        Iterator<PostIn> itposts = PostIn.ClassMgr.listPostIns(wsite);
        itposts = SWBComparator.sortByCreated(itposts, false);
        List<PostIn> posts = SWBUtils.Collections.copyIterator(itposts);
        int size = posts.size();
        
        
//////////////////////
int ipage;
try {
    ipage = Integer.parseInt(request.getParameter("ipage"));
}catch (NumberFormatException nfe) {
    ipage = 1;
}
int el = size;
int paginas = el / PAGE_SIZE;
if(el % PAGE_SIZE != 0) {
    paginas++;
}
int inicio = 0;
int fin = PAGE_SIZE;

// Mantiene la consistencia al eliminar elementos
if(ipage>paginas)
    ipage--;

inicio = (ipage * PAGE_SIZE) - PAGE_SIZE;
fin = (ipage * PAGE_SIZE);

if(ipage < 1 || ipage > paginas) {
    ipage = 1;
}
if(inicio < 0) {
    inicio = 0;
}
if(fin < 0) {
    fin = PAGE_SIZE;
}
if(fin > el) {
    fin = el;
}
if(inicio > fin) {
    inicio = 0;
    fin = PAGE_SIZE;
}
if(fin - inicio > PAGE_SIZE) {
    inicio = 0;
    fin = PAGE_SIZE;
}
inicio++;
//////////////////////        
System.out.println("\n\nipage="+ipage);
System.out.println("inicio="+inicio);
System.out.println("fin="+fin);
         
        
        int count = 0;
        PostIn post;
//        for(int i=0; i<xxx && i<size; i++ ) {
        for(int i=inicio; i<fin && i<size; i++ ) {
            post = posts.get(i);
            if(post instanceof MessageIn) {
//                if( count>fin )
//                    break;
//                if( count>=inicio && count<=fin) {
                MessageIn msg = (MessageIn)post;
                JSONObject obj = new JSONObject();
                try {
                    obj.put("fl",i);
                    obj.put("cta",msg.getPostInSocialNetwork().getTitle());
                    obj.put("sn",msg.getPostInSocialNetwork().getClass().getSimpleName());
                    obj.put("date",sdf.format(msg.getCreated()));
                    obj.put("msg",msg.getMsg_Text());
                    obj.put("feel",msg.getPostSentimentalType());
//if(msg.getPostSentimentalType()!=0)
//System.out.println("sentimiento"+msg.getPostSentimentalType());
                    obj.put("int",df.format(msg.getPostIntensityValue()));
                    obj.put("user",msg.getPostInSocialNetworkUser().getSnu_name());
                    obj.put("fllwrs",nf.format(msg.getPostInSocialNetworkUser().getFollowers()));
                    obj.put("frds",nf.format(msg.getPostInSocialNetworkUser().getFriends()));
                    jarr.put(obj);
                }catch (JSONException jse) {
                    jse.printStackTrace(System.out);
                    continue;
                }
//                }
            }
        }
        //response.getOutputStream().println(jobj.toString());
        response.getWriter().print(jobj.toString());
    }
    
}
