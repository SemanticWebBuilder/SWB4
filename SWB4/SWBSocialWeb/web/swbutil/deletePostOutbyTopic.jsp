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

<%
    WebSite wsite=WebSite.ClassMgr.getWebSite("Jore");
    
    if(wsite==null) return; 
    SocialTopic socialTopic=SocialTopic.ClassMgr.getSocialTopic("Tem_1", wsite);
    
    if(socialTopic!=null)
    {
        Iterator<PostOut> itPostOuts=PostOut.ClassMgr.listPostOutBySocialTopic(socialTopic, wsite);
         System.out.println("socialTopic-Jorge25-itPostOuts:"+itPostOuts.hasNext());
        while(itPostOuts.hasNext())
        {
            PostOut postOut=itPostOuts.next();
            System.out.println("postOut:"+postOut);
        }
    }
%>

