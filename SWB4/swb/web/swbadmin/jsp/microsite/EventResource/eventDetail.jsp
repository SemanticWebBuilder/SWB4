<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);

    String uri = request.getParameter("uri");
    EventElement event = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();  

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    if(event!=null && event.canView(member)) {
        event.incViews();
%>
<div class="columnaIzquierda">

<%
    SWBResourceURL back = paramRequest.getRenderUrl().setParameter("act", "view");
    back = paramRequest.getRenderUrl().setParameter("act", "edit");
    back.setParameter("year", request.getParameter("year"));
    back.setParameter("month", request.getParameter("month"));
    back.setParameter("day", request.getParameter("day"));
    back.setParameter("uri", event.getURI());
%>    
<%
    if (event.canModify(member)) {
%>
        <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "attend").setParameter("uri", event.getURI())%>">Asistir al evento</a></p></div>
<%  } %>
<div class="editarInfo"><p><a href="<%= paramRequest.getRenderUrl()%>">Ver todos</a></p></div>


<%
    if(event!=null && event.canView(member)) {
        event.incViews();
%>
<div id="detalleFoto">
    <div class="clear">&nbsp;</div>
    <h2 class="tituloGrande"><%= event.getTitle()%></h2>
    <p class="tituloNaranja"><%= event.getAudienceType()%></p>
    <div id="imagenDetalle">
        <a href="<%= SWBPortal.getWebWorkPath()+event.getEventImage()%>" target="_self">
            <img id="img_<%=event.getId()%>" src="<%= SWBPortal.getWebWorkPath()+event.getEventImage()%>" alt="<%=event.getTitle()%>" border="0" width="380" height="100%" />
        </a>
    </div>
    <p class="fotoInfo">Fecha: <%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%> | <span class="linkNaranja"><%= event.getViews()%> Vistas</span></p>
    <p class="descripcion"><%= event.getDescription()%></p>
    <p class="descripcion">Inicia:<strong><%= (event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></strong></p>
    <p class="descripcion">Termina:<strong><%= (event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></strong></p>
    <p class="descripcion">Lugar: <strong><%= event.getPlace()%></strong></p>
    <p>Asistentes:
<%
    Iterator<User> users = event.listAttendants();
    while (users.hasNext()) {
        User m = users.next();
%>
        <%= m.getFullName()%>
        <%
        if(users.hasNext()) {
        %>,&nbsp;<%
        }
    }
%>
    </p>
<%
    if (event.canModify(member)) {
%>
        <div class="editarInfo"><p><a href="<%= back%>">Editar información</a></p></div>
<%
    }
    if (event.canModify(member)) {
%>
        <div class="editarInfo"><p><a href="<%= paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", event.getURI())%>">Eliminar</a></p></div>
<%
    } %>
</div>
<br/><br/>
<%
    }
    SWBResponse res=new SWBResponse(response);
event.renderGenericElements(request, res, paramRequest);
out.write(res.toString());
    }
%>

</div>
<div class="columnaCentro">

</div>