<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
    String path = SWBPlatform.getWebWorkPath()+base.getWorkPath()+"/";
    String uri = request.getParameter("uri");

    EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
    if (rec != null) {
        rec.incViews();                             //Incrementar apariciones
%>
<table border="0" cellspacing="10">
    <tr>
        <td valign="top">
            <img src="<%=path+rec.getEventImage()%>" alt="<%=rec.getDescription()%>" width="110" height="150" />
        </td>
        <td valign="top">
                <%=rec.getTitle()%> (<%=SWBUtils.TEXT.getTimeAgo(rec.getCreated(), user.getLanguage())%>)<BR>
                <hr>
                <%=rec.getDescription()%> <BR>
                Inicia: <%=dateFormat.format(rec.getStartDate())%> - <%=timeFormat.format(rec.getStartTime())%><BR>
                Termina: <%=dateFormat.format(rec.getEndDate())%> - <%=timeFormat.format(rec.getEndTime())%><BR>
                Lugar: <%=rec.getPlace()%> <BR>
                Asistentes:
                <%
                Iterator<User> users = rec.listAttendants();
                while (users.hasNext()) {
                    User m = users.next();
                    %>
                    <%=m.getFullName()%>
                    <%if(users.hasNext()) {
                    %>,&nbsp;<%
                    }                
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
SWBResourceURL back = paramRequest.getRenderUrl().setParameter("act", "daily");
back.setParameter("year", request.getParameter("year"));
back.setParameter("month", request.getParameter("month"));
back.setParameter("day", request.getParameter("day"));
%>

<center>
    <a href="<%=back%>">Regresar</a>
    <%
            back = paramRequest.getRenderUrl().setParameter("act", "edit");
            back.setParameter("year", request.getParameter("year"));
            back.setParameter("month", request.getParameter("month"));
            back.setParameter("day", request.getParameter("day"));
            back.setParameter("uri", rec.getURI());
    %>
    <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "attend").setParameter("uri", rec.getURI())%>">Asistir al evento</a><%}%>
    <%if (rec.canModify(member)) {%><a href="<%=back%>">Editar Información</a><%}%>
    <%if (rec.canModify(member)) {%><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", rec.getURI())%>">Eliminar Evento</a><%}%>
</center>