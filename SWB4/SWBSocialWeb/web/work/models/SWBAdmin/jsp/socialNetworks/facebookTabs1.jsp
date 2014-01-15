<%-- 
    Document   : twitterTabs1
    Created on : 15/01/2014, 01:01:31 PM
    Author     : francisco.jimenez
--%>

<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%><%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*,org.semanticwb.portal.api.*"%>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%!
    String replaceId(String id)
    {
        id=id.substring(id.lastIndexOf('/')+1);
        id=id.replace('#', ':');        
        return id;
    }
%>

<%
    User user=SWBContext.getAdminUser();
    if(user==null)
    {
        response.sendError(403);
        return;
    }
%>
<%    
    String lang="es";
    if(user!=null)lang=user.getLanguage();
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 

    String objUri=request.getParameter("suri");
    
    
    String model = SWBContext.WEBSITE_ADMIN;
    String webPageId = paramRequest.getWebPage().getId();

    String resourcePath = "/" + lang + "/" + model + "/" + webPageId + "?";
    String param = "suri=" + URLEncoder.encode(request.getParameter("suri")) + "&contentTabId=";
    
    String loading="<BR/><center><img src='"+SWBPlatform.getContextPath()+"/swbadmin/images/loading.gif'/><center>";

    //Div dummy para detectar evento de carga y modificar titulo
    out.println("<div dojoType=\"dijit.layout.ContentPane\"/>");

    out.println("<div dojoType=\"dijit.layout.TabContainer\" region=\"center\" id=\""+replaceId(objUri)+"_tabs_facebook\">");

    //TODO:Modificar este codigo para recarga de clases, posible cambio por onLoad
    out.println("    <script type=\"dojo/connect\">");
    out.println("       this.watch(\"selectedChildWidget\", function(name, oval, nval){");
    out.println("           onClickTab(nval);");
    out.println("       });    ");
    out.println("    </script>");    
    
   
    out.println("<div id=\""+ objUri + NEWS_FEED_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("newsFeed")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + NEWS_FEED_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");

    out.println("<div id=\""+ objUri + WALL_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("myWall")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + WALL_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div id=\""+ objUri + PICTURES_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("myImages")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + PICTURES_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div id=\""+ objUri + VIDEOS_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("myVideos")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + VIDEOS_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div id=\""+ objUri + CONEXION +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("myConnections")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + CONEXION +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");


    out.println("</div><!-- end Bottom TabContainer -->");   
%>