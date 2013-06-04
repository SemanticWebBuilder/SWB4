<%-- 
    Document   : showPostIn
    Created on : 03-jun-2013, 13:01:48
    Author     : jorge.jimenez
--%>

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
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    if(request.getAttribute("postIn")==null) return;
    
    SemanticObject semObj=(SemanticObject)request.getAttribute("postIn");
    if(semObj==null) return; 
    
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if(wsite==null) return;
    
    out.println("<table>");
    out.println("<tr>");
    if(semObj.getGenericInstance() instanceof MessageIn) 
    {
        MessageIn message=(MessageIn)semObj.getGenericInstance();
        out.println("<td>");
        out.println(message.getMsg_Text());
        out.println("</td>");
    }else if(semObj.getGenericInstance() instanceof PhotoIn)
    {
        PhotoIn photo=(PhotoIn)semObj.getGenericInstance(); 
        out.println("<td>");
        //System.out.println("Name:"+Photo.social_Photo.getName()); 
        //System.out.println("ClassID:"+Photo.social_Photo.getClassId()); 
        //System.out.println("Canonical:"+Photo.social_Photo.getCanonicalName());
         //Puse ese tolowercase porque el nombre de la propiedad lo pone en mayuscula, quien sabe porque, si esta en minuscula
        out.println("<img src=\""+SWBPortal.getWebWorkPath()+photo.getWorkPath()+"/"+Photo.social_Photo.getName().toLowerCase()+"_"+photo.getId()+"_"+photo.getPhoto()+"\"/>");
        out.println("<br><br><br>"+photo.getMsg_Text());
        out.println("</td>");
    }else if(semObj.getGenericInstance() instanceof VideoIn)
    {
        VideoIn video=(VideoIn)semObj.getGenericInstance(); 
        out.println("<td>");
        out.println(video.getVideo());
        out.println("<br><br><br>"+video.getMsg_Text());
        out.println("</td>");
    }
    out.println("</tr>");
    out.println("</table>");

%>

