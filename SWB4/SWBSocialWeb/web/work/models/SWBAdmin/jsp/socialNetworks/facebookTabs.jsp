<%-- 
    Document   : facebookTabs
    Created on : 7/06/2013, 05:21:31 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.net.URLEncoder"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%
        String lang=paramRequest.getUser().getLanguage();
        String model = paramRequest.getWebPage().getWebSiteId();
        
        if(paramRequest.getUser()!=null)lang=paramRequest.getUser().getLanguage();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");        
        String objUri=request.getParameter("suri");
        String param = "suri=" + URLEncoder.encode(request.getParameter("suri"));
        //System.out.println("parametro en tabs.jsp:" + param);
        String loading="<BR/><center><img src='"+SWBPlatform.getContextPath()+"/swbadmin/images/loading.gif'/><center>";
        
        //All tabs are inside the same resource
        //Setting refreshOnShow=false does not reload tab when double-clicked.
        //Div dummy para detectar evento de carga y modificar titulo
        out.println("<div dojoType=\"dijit.layout.ContentPane\" postCreate=\"setTabTitle('"+objUri+"','"+ "Titulo " +"','"+loading+"');\" />");

        out.println("<div dojoType=\"dijit.layout.TabContainer\" region=\"center\" style_=\"border:0px; width:100%; height:100%\" id=\""+objUri+"/tabs\" _tabPosition=\"bottom\" nested_=\"true\" _selectedChild=\"btab1\" onButtonClick_=\"alert('click');\" onLoad_=\"alert('Hola');\">");        
        
        out.println("<div id=\""+ objUri + "/wall" +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Wall"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Facebook_Wall"+"?" + param + "&contentTabId=wall" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + "/images" +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Pictures"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Facebook_Wall"+"?" + param + "&contentTabId=pictures" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + "/wallPosts" +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Wall posts"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Facebook_Wall"+"?" + param + "&contentTabId=pictures" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + "/videos" +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Videos"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Facebook_Wall"+"?" + param + "&contentTabId=videos" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
                
        
        out.println("</div><!-- end Bottom TabContainer -->");
        %>