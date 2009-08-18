<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
    Member member = Member.getMember(user, wpage);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
%>
<h1>Eventos Diario</h1>
<table>
    <tbody>
<%
    String day = request.getParameter("day");
    String month = request.getParameter("month");
    String year = request.getParameter("year");

    Date dNow = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));

    Iterator<EventElement> eit = EventElement.listEventElementsByDate(null, dNow, wpage, wpage.getWebSite());
    while(eit.hasNext()) {
        EventElement event = eit.next();
        if(event.canView(member)) {
            SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
            viewUrl.setParameter("year", year);
            viewUrl.setParameter("month", month);
            viewUrl.setParameter("day", day);
%>
        <tr>
            <td valign="top">
                <a href="<%=viewUrl%>">
                    <img src="<%=event.getEventThumbnail()%>" alt="<%=event.getDescription()%>" border="0" />
                </a>
            </td>
            <td valign="top">
                <p><a href="<%=viewUrl%>"><%=event.getTitle()%></a></p>
                <p>Inicio:&nbsp;<b><%=(event.getStartDate() == null ? "" : dateFormat.format(event.getStartDate()))%></b> a las <b><%=(event.getStartTime() == null ? "" : timeFormat.format(event.getStartTime()))%></b></p>
                <p>Fin:&nbsp;<b><%=(event.getEndDate() == null ? "" : dateFormat.format(event.getEndDate()))%></b> a las <b><%=(event.getEndTime() == null ? "" : timeFormat.format(event.getEndTime()))%></b></p>
                <p>Lugar:&nbsp;<%=event.getPlace()%></p>
                <p>Puntuación:&nbsp;<%=event.getRank()%></p>
                <p>(<%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%>)</p>
                <p><%=event.getViews()%> vistas.</p>
            </td>
        </tr>
<%
        }
    }
    SWBResourceURL back = paramRequest.getRenderUrl();
    back.setParameter("year", year);
    back.setParameter("month", month);
    back.setParameter("day", day);
%>
    </tbody>
</table>
<center>
    <a href="<%=back%>">Regresar</a>
    <%

                if (member.canAdd()) {
    %>
    <a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar Evento</a>
    <%
                if (wputil != null && member.canView()) {
                    if (!wputil.isSubscribed(member)) {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "subcribe").toString()%>">Suscribirse a este elemento</a>
    <%
            } else {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString()%>">Cancelar suscripción</a>
    <%
                    }

                }
    %>
</center>
<%  }%>