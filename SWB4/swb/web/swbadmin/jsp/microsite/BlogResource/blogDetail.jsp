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
    PostElement post=(PostElement)request.getAttribute("post");
    Member member=Member.getMember(user,wpage);
    if(!post.canView(member) || post==null)
    {
        return;
    }
    post.incViews();
    String defaultFormat = "dd/MM/yyyy HH:mm";
    SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
    String updated = iso8601dateFormat.format(post.getUpdated());
    String postAuthor = post.getCreator().getFirstName();
    postAuthor=post.getCreator().getFullName();
    String email=post.getCreator().getEmail();
    String content="Sin contenido";
    post.incViews();  //Incrementar apariciones
    if(post.getContent()!=null)
    {
        content=post.getContent();
    }
%>
<div id="blog">
<table width="100%" cellpadding="2" cellspacing="2" border="1">
    <tr>
        <td>
            <h1><%=post.getTitle()%></h1>
        </td>
    </tr>
    
    <tr>
        <td>            
            <%

if(email!=null)
    {
    %>
    <a href="mailto:<%=email%>"><%=postAuthor%></a>
    <%
    }
    else
        {
        %>
        <p id="author"><%=postAuthor%></p>
        <%
        }
%>

        </td>
    </tr>
    <tr>
        <td>
            <p id="date"><%=updated%></p>
            <hr>
        </td>
    </tr>    
    <tr>
        <td>
            <p id="description"><%=post.getDescription()%></p>
        </td>
    </tr>
    <tr>
        <td>            
            <p id="content"><%=content%></p>
        </td>
    </tr>

</table>
</div>

<br>
<br>
<center>
    <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
    <%if(post.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",post.getURI()).setParameter("mode","editpost")%>">Editar Información</a><%}%>
    <%if(post.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",post.getURI())%>">Eliminar Entrada</a><%}%>
</center>

<%
post.renderGenericElements(request, out, paramRequest);
%>

