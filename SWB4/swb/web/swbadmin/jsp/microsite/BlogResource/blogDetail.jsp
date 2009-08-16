<%-- 
    Document   : blogDetail
    Created on : 13/08/2009, 06:01:12 PM
    Author     : victor.lorenzana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
    PostElement post=(PostElement)request.getAttribute("post");
    String defaultFormat = "dd 'de' MMMM 'del' yyyy";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    String updated = iso8601dateFormat.format(post.getUpdated());
    String postAuthor = post.getCreator().getFirstName();
    String email=post.getCreator().getEmail();
%>

<%=postAuthor%>&nbsp;&nbsp;&nbsp;
<%
if(email!=null)
    {
    %>
    <%=email%>
    <%
    }
%>
<br>
<%=post.getTitle()%> &nbsp;&nbsp;&nbsp;<%=updated%><br><br>
<%=post.getDescription()%>
<br>
<br>
<center>
    <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
    <%if(post.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",post.getURI()).setParameter("mode","editpost")%>">Editar Información</a><%}%>
    <%if(post.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",post.getURI())%>">Eliminar Entrada</a><%}%>
</center>


