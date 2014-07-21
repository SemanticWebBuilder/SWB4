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
    WebSite wsite=WebSite.ClassMgr.getWebSite("Jordi");
    //Iterator<Stream> itStreams=Stream.ClassMgr.listStreams(wsite);
    //while(itStreams.hasNext())
    {
        //Stream stream=itStreams.next();
        Iterator<PostIn> itPostIns=PostIn.ClassMgr.listPostIns(wsite);
        while(itPostIns.hasNext())
        {
            cont++; 
            PostIn postIn=itPostIns.next();
            out.println("postIn :"+postIn);
            postIn.remove();
        }    
    }
    
    /*
 *  /*
 * Iterator<Stream> itStreams=Stream.ClassMgr.listStreams(wsite);
    while(itStreams.hasNext())
    {
        Stream stream=itStreams.next();
        String sQuery=getAllPostInStream_Query(stream);    
        Iterator<PostIn> itListFilter=SWBSocial.executeQueryArray(sQuery, wsite).iterator();
        System.out.println("itListFilterJJorge:"+itListFilter);
        while(itListFilter.hasNext())
        {
            PostIn postIn=itListFilter.next();
            System.out.println("postIn2Remove:"+postIn);
            postIn.remove();
            cont++;
        }
    }*/
%>
<br>
Termina...<%=cont%>    
    