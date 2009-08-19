<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<div id="contactos">
  <h2>Mis Subscripciones</h2>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user=paramRequest.getUser();
String lang="es";
WebPage wpage = paramRequest.getWebPage();
if(user.getLanguage()!=null) lang=user.getLanguage();
%><%=renderCalendar(user, new Date(System.currentTimeMillis()), wpage, paramRequest)%>
<a href="<%=paramRequest.getRenderUrl().setParameter("act", "view")%>">Regresar</a>
</div>
<%!
    private String renderCalendar(User user, Date current, WebPage wpage, SWBParamRequest paramRequest) {
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
        SWBResourceURL nml = paramRequest.getRenderUrl();
        nml.setParameter("year", String.valueOf(year + 1900));
        nml.setParameter("month", String.valueOf(month + 1));
        nml.setParameter("day", "1");

        SWBResourceURL pml = paramRequest.getRenderUrl();
        pml.setParameter("year", String.valueOf(year + 1900));
        pml.setParameter("month", String.valueOf(month - 1));
        pml.setParameter("day", "1");

        sbf.append("<div class=\"calendar\">\n" +
                "  <table>\n" +
                "    <tr>\n" +
                "      <td class=\"month-head\" colspan=\"7\">" +
                "<a href=\"" + pml + "\">&lt;</a>    " +
                months[month] + " " + (year + 1900) +
                "    <a href=\"" + nml + "\">&gt;</a>" +
                "</td>\n" +
                "    </tr>\n" +
                "    <tr>\n");

        //Render day labels
        for (int i = 0; i < 7; i++) {
            sbf.append("      <td class=\"day-head\">" + days[i] + "</td>\n");
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

            Iterator<EventElement> evsNow = EventElement.listEventElementsByDate(user, new Date(year, month, i), wpage, wpage.getWebSite());
            String eventTitles = "";

            while (evsNow.hasNext()) {
                EventElement eve = evsNow.next();
                eventTitles = eventTitles + "* " + eve.getTitle().trim() + "&#10;";
            }

            //Today?
            if (day == i - 1) {
                //Are there events today?
                if (!eventTitles.equals("")) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year + 1900));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(i));

                    sbf.append("      <td class=\"dated\">\n" +
                            "        <div class=\"daylabel\"><a href=\"" + viewUrl + "\">" + i + "</a></div>\n" +
                            "      </td>\n");
                }  else {
                    //There aren't events today
                    sbf.append("      <td class=\"day\">\n" +
                            "<div class=\"daylabel\">" + i + "</div>\n" +
                            "      </td>\n");
                }
            } else {
                //Not today
                evsNow = EventElement.listEventElementsByDate(user, new Date(year, month, i), wpage, wpage.getWebSite());
                if (!eventTitles.equals("")) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year + 1900));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(i));

                    sbf.append("      <td class=\"dated\">\n" +
                            "        <div class=\"daylabel\"><a href=\"" + viewUrl + "\">" + i + "</a></div>\n" +
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