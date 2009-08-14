<%@page contentType="text/html"%>
<%@page import="java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
%>
<h1>Eventos</h1>
<%=getDaysInMonth(2000, 1)%>-<%=getFirstDay(2000, 1)%>
<table>
    <tbody>
        <%
            Iterator<EventElement> eit = EventElement.listEventElementByEventWebPage(wpage, wpage.getWebSite());
            while (eit.hasNext()) {
                EventElement event = eit.next();
                SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
                if (event.canView(member)) {
        %>
        <tr>
            <td>
                <a href="<%=viewUrl%>"><%=event.getTitle()%></a><BR>
                Inicio:&nbsp;<b><%=(event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></b> a las <b><%=(event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></b><BR>
                Fin:&nbsp;<b><%=(event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></b> a las <b><%=(event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></b><BR>
                Lugar:&nbsp;<%=event.getPlace()%><BR>
            </td>
        </tr>
        <%
                }
            }
        %>
    </tbody>
</table>
<%
            if (member.canAdd()) {
%>
<center>
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

<%!
private long getDaysInMonth(int year, int month) {
    long aDay = 1000 * 60 * 60 * 24;
    Calendar thisMonth = new GregorianCalendar(year, month, 1);
    Calendar nextMonth = new GregorianCalendar(year, month + 1, 1);
    double len = Math.ceil((nextMonth.getTimeInMillis() - thisMonth.getTimeInMillis()) / aDay);

    return (long)len;
}
%>

<%!
private int getFirstDay(int year, int month) {
    Calendar thisMonth = new GregorianCalendar(year, month, 1);
    return thisMonth.DAY_OF_MONTH;
}
%>