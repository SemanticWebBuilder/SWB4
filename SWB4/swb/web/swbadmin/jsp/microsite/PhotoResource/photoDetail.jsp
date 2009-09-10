<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
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
<div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Ver todos</a></p></div>

<br/><br/>
<div id="detalleFoto">
    <h2 class="tituloGrande"><%= photo.getTitle()%></h2>
    <div id="imagenDetalle">
        <a href="<%= SWBPlatform.getWebWorkPath()+photo.getImageURL()%>" target="_self">
            <img id="img_<%=photo.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+photo.getImageURL() %>" alt="<%= photo.getTitle() %>" border="0" width="300" height="100%" />
        </a>
    </div>
    
    <div class="detalleFotoInfo">
        <p class="tituloNaranja">Autor: <%= photo.getCreator().getFirstName()%></p>
        <p class="fotoInfo">Fecha: <%= SWBUtils.TEXT.getStrDate(photo.getCreated(), lang, "dd/mm/yy")%>   | <span class="linkNaranja"><%= photo.getViews()%> Vistas</span></p>
        <p class="descripcion"><%= photo.getDescription()%></p>  
        <p class="descripcion"><%= photo.getRank()%> </p>
        <%if(photo.canModify(member)){%>
        <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",photo.getURI())%>">Editar información</a></p></div>
        <%}%>
        <%if(photo.canModify(member)){%>
        <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act","remove").setParameter("uri",photo.getURI())%>">Eliminar</a></p></div>
        <%}%>
        <div class="clear">&nbsp;</div>
    </div>
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
    photo.renderGenericElements(request, response, paramRequest);
%>