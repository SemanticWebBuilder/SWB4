<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="static org.semanticwb.social.admin.resources.Timeline.*"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
        String lang=paramRequest.getUser().getLanguage();
        String model = paramRequest.getWebPage().getWebSiteId();
        //String resource = paramRequest.
        Resource base = paramRequest.getResourceBase();
        //System.out.println(lang + "/" + model + "/" + base.get;
        
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
        
        out.println("<div id=\""+ objUri + HOME_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Home"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Timeline"+"?" + param + "&contentTabId=homeTimeline" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + MENTIONS_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Mentions"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Timeline"+"?" + param + "&contentTabId=mentions" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + FAVORITES_TAB + "\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Favorites"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Timeline"+"?" + param + "&contentTabId=favorites" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + DIRECT_MESSAGES_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Direct Messages"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Timeline"+"?" + param + "&contentTabId=directMessages" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");

        out.println("<div id=\""+ objUri + USER_TWEETS_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"My Tweets"+"\" refreshOnShow=\""+"false"+"\" href=\""+"/es/SWBAdmin/Timeline"+"?" + param + "&contentTabId=userTweets" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");
        
        out.println("<div id=\""+ objUri + DISCOVER_TAB +"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Discover"+"\" refreshOnShow=\""+"true"+"\" href=\""+"/es/SWBAdmin/Timeline"+"?" + param + "&contentTabId=discover" +"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("</div>");        
                
        //out.println("<div id=\""+ objUri +"/profile"+"\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Profile"+"\" refreshOnShow=\""+"true"+"\" href=\""+"/es/SWBAdmin/T_Timeline"+"?" + param + "&contentTabId=showUserProfile&targetUser=dendral"+"\" _loadingMessage=\""+loading+"\" style_=\"border:0px; width:100%; height:100%\">");
        //out.println("</div>");
        
        //out.println("<div id=\"pacone\" dojoType=\"dijit.layout.ContentPane\" title=\"Other Closable\" closable=\"true\" onClose=\"return confirm('really?');\" onLoad=\"onLoadTab(this);\">");
        //    out.println("... I have an in-line onClose");
        //out.println("</div>");
        
        out.println("</div><!-- end Bottom TabContainer -->");
        %>
