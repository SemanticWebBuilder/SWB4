<%-- 
    Document   : postSummary
    Created on : 12-mar-2012, 20:41:26
    Author     : carlos.ramos
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="static org.semanticwb.social.resources.reports.PostSummary.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    Locale locale = new Locale("es");
    NumberFormat nf = NumberFormat.getIntegerInstance(locale);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy HH:mm", locale);

    final WebPage wpage = paramRequest.getWebPage(); 
    final WebSite wsite = wpage.getWebSite();
    Iterator<PostIn> itposts = PostIn.ClassMgr.listPostIns(wsite);
    itposts = SWBComparator.sortByCreated(itposts, false);
    List<PostIn> posts = SWBUtils.Collections.copyIterator(itposts);
    int size = posts.size();
%>
    <a href="<%=(paramRequest.getRenderUrl().setMode(Mode_JSON))%>" title="ver grid">ver grid</a>
    <hr/>
    <p>Total: <%=nf.format(size)%></p>
    <hr/>
    <table>
     <tr>
      <th>Cuenta</th>
      <th>Red social</th>
      <th><a href="#" title="order descendente">Fecha</a></th>
      <th>Mensaje</th>
      <th>Sentimiento</th>
      <th>Intensidad</th>
      <th>Usuario</th>
      <th><a href="#" title="order descendente">Seguidores</a></th>
      <th><a href="#" title="order descendente">Amigos</a></th>
     </tr>
<%
    //PostIn post;
    //while(itposts.hasNext()) {
    for(PostIn post:posts) {
        //post = itposts.next();
        if(post instanceof MessageIn) {
            MessageIn msg = (MessageIn)post;
            out.println("<tr>");
            out.println("<td>"+msg.getPostInSocialNetwork().getTitle()+"</td>");
            out.println("<td>"+msg.getPostInSocialNetwork().getClass().getSimpleName()+"</td>");
            out.println("<td>"+sdf.format(msg.getCreated())+"</td>");
            out.println("<td>"+msg.getMsg_Text()+"</td>");
            out.println("<td>"+msg.getPostSentimentalType()+"</td>");
            out.println("<td>"+msg.getPostIntensityValue()+"</td>");
            out.println("<td>"+msg.getPostInSocialNetworkUser().getSnu_name()+"</td>");
            out.println("<td>"+nf.format(msg.getPostInSocialNetworkUser().getFollowers())+"</td>");
            out.println("<td>"+nf.format(msg.getPostInSocialNetworkUser().getFriends())+"</td>");
            out.println("</tr>");
        }
    }
    out.println("</table>");
    out.println("<hr/>");
    out.println("<p>Total: "+nf.format(size)+"</p>");
    out.println("<hr/>");
%>