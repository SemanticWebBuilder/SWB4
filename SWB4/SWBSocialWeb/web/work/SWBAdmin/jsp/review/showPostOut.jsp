<%-- 
    Document   : showPostOut
    Created on : 17-may-2013, 13:54:20
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="static org.semanticwb.social.admin.resources.SocialTopicMsgIn.*"%> 
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    if(request.getAttribute("wsite")==null || request.getAttribute("postOut")==null) return;
    
    //WebSite wsite=(WebSite)request.getAttribute("wsite");
    SemanticObject semObj=(SemanticObject)request.getAttribute("postOut");
    
    
    out.println("<table>");
    out.println("<tr>");
    if(semObj.getGenericInstance() instanceof Message) 
    {
        Message message=(Message)semObj.getGenericInstance();
        out.println("<td>");
        out.println(message.getMsg_Text());
        out.println("</td>");
    }else if(semObj.getGenericInstance() instanceof Photo)
    {
        Photo photo=(Photo)semObj.getGenericInstance(); 
        out.println("<td>");
        out.println(photo.getPhoto());
        out.println("<br><br><br>"+photo.getMsg_Text());
        out.println("</td>");
    }else if(semObj.getGenericInstance() instanceof Video)
    {
        Video video=(Video)semObj.getGenericInstance(); 
        out.println("<td>");
        out.println(video.getVideo());
        out.println("<br><br><br>"+video.getMsg_Text());
        out.println("</td>");
    }
    out.println("</tr>");
    out.println("</table>");

%>

