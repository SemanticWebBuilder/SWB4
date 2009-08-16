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
<table width="100%">
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
        <p><%=postAuthor%></p>
        <%
        }
%>


        </td>
    </tr>
    <tr>
        <td>
            <p><%=updated%></p>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;
        </td>

    </tr>
    <tr>
        <td>
            <%=post.getDescription()%>
        </td>

    </tr>

</table>


<br>
<br>
<center>
    <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
    <%if(post.canModify(member)){%><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",post.getURI()).setParameter("mode","editpost")%>">Editar Información</a><%}%>
    <%if(post.canModify(member)){%><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",post.getURI())%>">Eliminar Entrada</a><%}%>
</center>


