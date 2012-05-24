<%-- 
    Document   : removePost
    Created on : 24-may-2012, 15:38:11
    Author     : jorge.jimenez
--%>

<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

Inicia...
<%
    int cont=0;
    WebSite wsite=paramRequest.getWebPage().getWebSite();
    //Lista todos los post que existan
    Iterator <Post> itPost=Post.ClassMgr.listPosts(wsite);
    while(itPost.hasNext())
    {
        cont++;
        Post post=itPost.next();
        System.out.println("Post Existente:"+post.getId());
    }
    System.out.println("Post Totales:"+cont);
    
    //Elimina todos los objetos(instancias) de Post
    itPost=Post.ClassMgr.listPosts(wsite);
    while(itPost.hasNext())
    {
        Post post=itPost.next();
        post.remove();
    }
 
%>
Termina...