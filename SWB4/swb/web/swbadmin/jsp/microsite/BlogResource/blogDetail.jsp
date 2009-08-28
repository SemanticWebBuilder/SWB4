<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
 
<script language="Javascript" type="text/javascript">
        function validateremove(url, title,uri)
        {
            if(confirm('¿Esta seguro de borrar la entrada '+title+'?'))
            {
                var url=url+'&uri='+escape(uri);
                window.location.href=url;
            }
        }
    </script>

<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    PostElement post=(PostElement)request.getAttribute("post");
    Member member=Member.getMember(user,wpage);
    if(!post.canView(member) || post==null)
    {
        %>
        <p>No tiene permisos para ver esta entrada, o la entrada ya no existe</p>
        <%
        return;
    }
    String srcLine=SWBPlatform.getWebWorkPath()+"/models/"+ paramRequest.getWebPage().getWebSiteId()  +"/css/solidLine.jpg";

    String updated = SWBUtils.TEXT.getTimeAgo(post.getUpdated(), user.getLanguage());
    String postAuthor = post.getCreator().getFirstName();
    postAuthor=post.getCreator().getFullName();
    String email=post.getCreator().getEmail();
    String content="Sin contenido";
    post.incViews();  //Incrementar apariciones
    if(post.getContent()!=null)
    {
        content=post.getContent();
    }
    DecimalFormat df=new DecimalFormat("#0.0#");
    String rank=df.format(post.getRank());
    SWBResourceURL removeUrl=paramRequest.getActionUrl();
    removeUrl.setParameter("act", "remove");
%>
<br>
<div id="blog">
<table width="100%" cellpadding="2" cellspacing="2" border="0">
    <tr>
        <td>
            <h2 class="tituloGrande"><%=post.getTitle()%></h2>
        </td>
    </tr>
    <tr>
        <td>
            <div class="blogcontent"><%=post.getDescription()%></div>
        </td>
    </tr>
    
    <tr>
        <td>            
            <%

if(email!=null)
    {
    %>
    <p>Escrito por: <a href="mailto:<%=email%>"><%=postAuthor%></a> , <%=updated%>, visitas: <%=post.getViews()%> , calificación: <%=rank%></p>
    <p><img src="<%=srcLine%>" alt="" width="680" height="1" ></p>
    <%
    }
    else
        {
        %>
        <p id="author">Escrito por: <%=postAuthor%></p> , <%=updated%>, visitas: <%=post.getViews()%> , calificación: <%=post.getRank()%></p>
        <p><img src="<%=srcLine%>" alt="" width="680" height="1" ></p>
        <%
        }
%>

        </td>
    </tr>       
    <tr>
        <td>
            <br>
             <div class="blogcontent"><%=content%></div>
        </td>
    </tr>

</table>
</div>

<br>
<p><img src="<%=srcLine%>" alt="" width="680" height="1" ></p>
<center>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></p></div>
    <%if(post.canModify(member)){%><div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",post.getURI()).setParameter("mode","editpost")%>">Editar Información</a></p></div><%}%>
    <%if(post.canModify(member)){%><div class="editarInfo"><p><a href="javascript:validateremove('<%=removeUrl%>','<%=post.getTitle()%>','<%=post.getURI()%>')">Eliminar Entrada</a></p></div><%}%>
</center>
    <br>    
    <br>
    <br>

    
<%
post.renderGenericElements(request, out, paramRequest);
%>

