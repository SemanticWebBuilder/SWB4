<%-- 
    Document   : showPostOutLinksHitsData
    Created on : 25-abr-2014, 11:59:21
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    String postOutLinksHitsUri=request.getParameter("uri");
    if(postOutLinksHitsUri!=null)
    {
        %>
        <div class="swbform">
        <fieldset>
        <table class="tabla1">
            <thead>
            <tr>
                <th class="accion">
                    <span>IP</span>
                </th>
                <th class="accion">
                    <span>Fecha</span>
                </th>
            </tr>
            </thead>
            <body>
        <%
        try{
            PostOutLinksHits postOutLinksHits=(PostOutLinksHits)SemanticObject.createSemanticObject(postOutLinksHitsUri).createGenericInstance(); 
            Iterator <PostOutLinksHitsIp> itIps=postOutLinksHits.listUserIps();
            while(itIps.hasNext())
            {
                PostOutLinksHitsIp postOutLinksHitsIp=itIps.next();
                %>
                    <tr>
                        <td>
                            <%=postOutLinksHitsIp.getUserIP()%>
                        </td>
                        <td>
                            <%=postOutLinksHitsIp.getCreated()%>
                        </td>
                    </tr>
                <%
            }
        }catch(Exception e){e.printStackTrace();}
        %>
        </fieldset>
        </div>
        <%
    }
%>