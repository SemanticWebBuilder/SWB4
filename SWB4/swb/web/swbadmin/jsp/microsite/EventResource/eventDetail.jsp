<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
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

<br/>
<div id="panorama">
<%
    SWBResourceURL back = paramRequest.getRenderUrl().setParameter("act", "view");
    back = paramRequest.getRenderUrl().setParameter("act", "edit");
    back.setParameter("year", request.getParameter("year"));
    back.setParameter("month", request.getParameter("month"));
    back.setParameter("day", request.getParameter("day"));
    back.setParameter("uri", event.getURI());
%>    
<%if (event.canModify(member)) {%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "attend").setParameter("uri", event.getURI())%>">Asistir al evento</a></p></div>
<%}%>

    <div class="editarInfo"><p><a href="<%= paramRequest.getRenderUrl()%>">Ver todos</a></p></div>
</div>

<br/><br/>
<div id="entriesWrapper">
    <div class="entry">
        <a href="<%= SWBPlatform.getWebWorkPath()+event.getEventImage()%>" target="_self">
            <img id="img_<%=event.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+event.getEventImage()%>" alt="<%=event.getTitle()%>" border="0" width="380" height="100%" />
        </a>
        <div class="entryInfo">
            <p><%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
            <p class="tituloNaranja"><%= event.getTitle()%></p>
            <p><%= event.getDescription()%></p>
            <p>Dirigido a: <strong><%= event.getAudienceType()%></strong></p>
            <p class="eventoInicio">Inicia:<strong><%= (event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></strong></p>
            <p class="eventoFinal">Termina:<strong><%= (event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></strong></p>
            <p>Lugar: <strong><%= event.getPlace()%></strong></p>
            <p>Asistentes:
            <%
            Iterator<User> users = event.listAttendants();
            while (users.hasNext()) {
                User m = users.next();
                %>
                <%=m.getFullName()%>
                <%if(users.hasNext()) {
                %>,&nbsp;<%
                }
            }
            %></p>
            <p style="line-height:13px;"><%=event.getViews()%> vistas</p>
            <%if (event.canModify(member)) { %>
                <div class="editarInfo"><p><a href="<%= back%>">Editar información</a></p></div>
            <%
            }
            if (event.canModify(member)) { %>
                <div class="editarInfo"><p><a href="<%= paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", event.getURI())%>">Eliminar</a></p></div>
            <%
            } %>
            <div class="clear">&nbsp;</div>
        </div>
    </div>
</div>
<%
            }
%>
<%
event.renderGenericElements(request, out, paramRequest);
%>

