<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);

    String lang = user.getLanguage();
%>
<%
        String uri=request.getParameter("uri");
        PhotoElement photo=(PhotoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(member.canView() && photo!=null)
        {
            photo.incViews();  //Incrementar apariciones
%>
<br/>
<div id="panorama">
    <%if(photo.canModify(member)){%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",photo.getURI())%>">Editar información</a></p></div>
    <%}%>
    <%if(photo.canModify(member)){%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",photo.getURI())%>">Eliminar foto</a></p></div>
    <%}%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Regresar</a></p></div>
</div>
<div id="panorama">
    <table border="0">
    <tr>
        <td valign="top">
            <a href="<%= SWBPlatform.getWebWorkPath()+photo.getImageURL()%>" target="_self">
            <img id="img_<%=photo.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+photo.getImageURL() %>" alt="<%= photo.getTitle() %>" border="0" />
            </a>
        </td>
        
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
        <td valign="top" align="left">
            <small>
                <p style="line-height:3px;"><%= photo.getTitle() %> </p>                                
                <p style="line-height:3px;"><%= photo.getDescription() %> </p>
                <p style="line-height:3px;">Autor: <%= photo.getCreator().getFirstName() %> </p>
                <p style="line-height:3px;">Fecha: <%= SWBUtils.TEXT.getStrDate(photo.getCreated(), lang, "dd/mm/yy") %> </p>
                <p style="line-height:3px;"><%= photo.getRank() %> </p>
                <p style="line-height:3px;"><%= photo.getViews() %> vistas </p>
            </small>
        </td>
    </tr>
  </table>
</div>
<script type="text/javascript">
    var img = document.getElementById('img_<%=photo.getId()%>');
    if( img.width>img.height && img.width>450) {
        img.width = 450;
        img.height = 370;
    }else {
        if(img.height>370) {
            img.width = 370;
            img.height = 450;
        }
    }
</script>
<%
        }
%>
<%
    photo.renderGenericElements(request, out, paramRequest);
%>