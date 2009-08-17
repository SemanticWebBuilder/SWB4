<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
%>
<%
    String path = SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/";
    String uri = request.getParameter("uri");
    EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
    if (rec != null) {
        rec.incViews();                             //Incrementar apariciones
%>
<table border="0" cellspacing="10">
    <tr>
        <td valign="top">
            <img src="<%=path+rec.getEventImage()%>" alt="<%=rec.getDescription()%>" />
        </td>
        <td valign="top">
                <%=rec.getTitle()%> <BR>
                <hr>
                <%=rec.getDescription()%> <BR>
                Inicia: <%=dateFormat.format(rec.getStartDate())%> - <%=timeFormat.format(rec.getStartTime())%><BR>
                Termina: <%=dateFormat.format(rec.getEndDate())%> - <%=timeFormat.format(rec.getEndTime())%><BR>
                Lugar: <%=rec.getPlace()%> <BR>
                Asistentes:
                <%
                Iterator<Member> members = rec.listAttendants();
                while (members.hasNext()) {
                    Member m = members.next();
                    %>
                    <%=m.getUser().getFullName()%>
                <%
                }
                %>
                <br><%=rec.getViews()%> vistas<BR>
        </td>
    </tr>
</table>
<%
            }
%>
<%
rec.renderGenericElements(request, out, paramRequest);
%>

<center>
    <a href="<%=paramRequest.getRenderUrl()%>">Regresar</a>
    <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getRenderUrl().setParameter("act", "attend").setParameter("uri", rec.getURI())%>">Asistir al evento</a><%}%>
    <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", rec.getURI())%>">Editar Información</a><%}%>
    <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", rec.getURI())%>">Eliminar Evento</a><%}%>
</center>