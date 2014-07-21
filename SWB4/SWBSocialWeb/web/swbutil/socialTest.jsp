<%-- 
    Document   : deletePostOutbyTopic
    Created on : 03-sep-2013, 13:10:02
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

Inicia...<br>
<%
    int cont=0;
    //WebSite wsite=WebSite.ClassMgr.getWebSite("Marca1");
    Iterator<PostIn> itPostIns=PostIn.ClassMgr.listPostIns();
    while(itPostIns.hasNext())
    {
        cont++; 
        PostIn postIn=itPostIns.next();
        out.println("postIn :"+postIn);
        if(postIn.getPostInSocialNetworkUser()==null || postIn.getPostInSocialNetwork()==null) 
        {
           out.println("postIn SIN USER:"+postIn+",NET:"+postIn.getPostInSocialNetwork()+"<br>");
           //postIn.remove();
        }
    }    
%>
<br>
Termina...<%=cont%>    
    