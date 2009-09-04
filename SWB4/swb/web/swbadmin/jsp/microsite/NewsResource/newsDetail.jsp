<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    String uri = request.getParameter("uri");
    NewsElement anew = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

    String path = SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/";
%>

<br />
<%if (anew.canModify(member)) {%>
<div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI())%>">Editar Información</a></p></div>
<%}%>
<%if (anew.canModify(member)) {%>
<div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", anew.getURI())%>">Eliminar</a></p></div>
<%}%>
<div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Ver todas</a></p></div>


<%
    if(anew!=null && anew.canView(member)) {
        anew.incViews();
%>
<div id="detalleFoto">
    <div class="clear">&nbsp;</div>
    <h2 class="tituloGrande"><%= anew.getTitle()%></h2>
    <p class="tituloNaranja">Por: <%= anew.getAuthor()%>. <%= anew.getCitation()%></p>
    <div id="imagenDetalle">
        <img id="img_<%=anew.getId()%>" src="<%= path+anew.getNewsImage() %>" alt="<%= anew.getTitle() %>" border="0" width="380" height="100%" />
    </div>
    <p class="fotoInfo">
        Fecha: <%=dateFormat.format(anew.getCreated())%> | <span class="linkNaranja"><%= anew.getViews()%> Vistas</span>
    </p>
    <p class="descripcion" style="text-align:justify;"><%=anew.getFullText()%></p>
</div>
<%  }
    anew.renderGenericElements(request, out, paramRequest);
%>
