<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
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
<%=renderCalendar(new Date(System.currentTimeMillis()), paramRequest)%>
<table>
    <tbody>
        <%
            String day = request.getParameter("day");
            String month = request.getParameter("month");
            String year = request.getParameter("year");

            Iterator<EventElement> eit = EventElement.listEventElementsByDate(new Date(2009, 7, 17));
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
                Puntuación:&nbsp;<%=event.getRank()%><BR>
                <%=event.getViews()%> vistas.
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
    private String renderCalendar(Date current, SWBParamRequest paramRequest) {
        StringBuffer sbf = new StringBuffer();

        //If no date specified, get current date
        if (current == null) current = new Date(System.currentTimeMillis());

        int day = current.getDate();
        int month = current.getMonth();
        int year = current.getYear();

        String [] months = {"Enero", "Febrero", "Marzo", "Abril",
                            "Mayo", "Junio", "Julio", "Agosto",
                            "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String [] days = {"D", "L", "M", "M", "J", "V", "S"};

        Date thisMonth = new Date(year, month, 1);
        Date nextMonth = new Date(year, month + 1, 1);

        //Find out when this mont starts and ends
        int firstWeekDay = thisMonth.getDay();
        long daysInMonth = Math.round((nextMonth.getTime() - thisMonth.getTime()) / (1000 * 60 * 60 * 24));

        sbf.append("<div class=\"calendar\">\n" +
                "  <table>\n" +
                "    <tr>\n" +
                "      <td class=\"head\" colspan=\"7\">" +
                months[month] + " " + (year + 1900) +
                "</td>\n" +
                "    </tr>\n" +
                "    <tr>\n");

        //Render day labels
        for (int i = 0; i < 7; i++) {
            sbf.append("      <td class=\"head\">" + days[i] + "</td>\n");
        }

        sbf.append("    </tr>\n" +
                "    <tr>\n");

        //Fill the first week in the month with the appropiate day offset
        for (int i = 0; i < firstWeekDay; i++) {
            sbf.append("      <td class=\"empty\"> </td>\n");
        }

        int weekDay = firstWeekDay;
        for (int i = 1; i <= daysInMonth; i++) {
            weekDay %= 7;
            if (weekDay == 0) {
                sbf.append("    </tr>\n" +
                        "    <tr>\n");
            }

            //Today?
            if (day == i - 1) {
                //Are there events today?
                if (EventElement.listEventElementsByDate(new Date(year, month, i)).hasNext()) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(day));

                    sbf.append("      <td class=\"dated\" onclick=\"window.location='" + viewUrl + "';\">\n" +
                            "        <div class=\"daylabel\">" + i + "</div>\n" +
                            "      </td>\n");
                }  else {
                    //There aren't events today
                    sbf.append("      <td class=\"today\">\n" +
                            "<div class=\"daylabel\">" + i + "</div>\n" +
                            "      </td>\n");
                }
            } else {
                //Not today
                if (EventElement.listEventElementsByDate(new Date(year, month, i)).hasNext()) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(day));

                    sbf.append("      <td class=\"dated\" onclick=\"window.location='" + viewUrl + "';\">\n" +
                            "        <div class=\"daylabel\">" + i + "</div>\n" +
                            "      </td>\n");
                } else {
                    sbf.append("      <td class=\"day\" >\n" +
                            "        <div class=\"daylabel\">" + i + "</div>\n" +
                            "      </td>\n");
                }
            }
            weekDay++;
        }
        sbf.append("    </tr>\n" +
                "  </table>\n" +
                "</div>\n");

        return sbf.toString();
    }
%>