<%-- 
    Document   : twitterTabs1
    Created on : 15/01/2014, 01:01:31 PM
    Author     : francisco.jimenez
--%>

<%@page contentType="text/html"%><%@page pageEncoding="UTF-8"%><%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*,org.semanticwb.portal.api.*"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>

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

    //out.println("<div dojoType=\"dijit.layout.TabContainer\" region=\"center\" id=\""+replaceId(objUri)+"_tabs_twitter\">");
    out.println("<div class=\"swbform\" style=\" width : 2400px; overflow-y: hidden; height:500px;\">");
    //TODO:Modificar este codigo para recarga de clases, posible cambio por onLoad
    out.println("    <script type=\"dojo/connect\">");
    out.println("       this.watch(\"selectedChildWidget\", function(name, oval, nval){");
    out.println("           onClickTab(nval);");
    out.println("       });    ");
    out.println("    </script>");    
    
   
    out.println("<div class=\"pub-redes\" style=\"width: 400px; height:500px;\" id=\""+ objUri + HOME_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("timelineLabel")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + HOME_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");

    out.println("<div class=\"pub-redes\" style=\"width: 400px; height:500px;\" id=\""+ objUri + MENTIONS_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("mentionsLabel")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + MENTIONS_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div class=\"pub-redes\" style=\"width: 400px; height:500px;\" id=\""+ objUri + FAVORITES_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("favoritesLabel")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + FAVORITES_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div class=\"pub-redes\" style=\"width: 400px; height:500px;\" id=\""+ objUri + DIRECT_MESSAGES_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("directMLabel")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + DIRECT_MESSAGES_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div class=\"pub-redes\" style=\"width: 400px; height:500px;\" id=\""+ objUri + FRIENDS_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("connectionsLabel")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + FRIENDS_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");
    
    out.println("<div class=\"pub-redes\" style=\"width: 400px; height:500px;\" id=\""+ objUri + FOLLOWERS_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+paramRequest.getLocaleString("connectionsLabel")+"\" refreshOnShow=\""+"false"+"\" href=\"" + resourcePath + param + FOLLOWERS_TAB +"\" _loadingMessage=\""+loading+"\" style=\"overflow:auto;\" style_=\"border:0px; width:100%; height:100%\" onLoad_=\"onLoadTab(this);\">");
    out.println("</div>");


    out.println("</div><!-- end Bottom TabContainer -->");   
%>