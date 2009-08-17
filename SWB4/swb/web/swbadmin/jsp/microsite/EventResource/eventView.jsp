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
            String year = request.getParameter("year");
            String month = request.getParameter("month");
            String day = request.getParameter("day");
            Date current = new Date(System.currentTimeMillis());

            if(day != null && month != null && day != null) {
                current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));
            }
%>
<script type="text/javascript">
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dojo.parser");
</script>
<h1>Eventos</h1>
<div>
    <div>
    <form action="<%=paramRequest.getRenderUrl()%>" method="post">
        <fieldset><legend>Elige una fecha</legend>
            <label for="year">Año:</label>
            <input type="text" dojoType="dijit.form.TextBox" name="year" id="year" value="<%=current.getYear() + 1900%>"/>
            <label for="month">Mes:</label>
            <select dojoType="dijit.form.FilteringSelect" name ="month" id="month" value="<%=current.getMonth()%>">
                <option value="0">Enero</option>
                <option value="1">Febrero</option>
                <option value="2">Marzo</option>
                <option value="3">Abril</option>
                <option value="4">Mayo</option>
                <option value="5">Junio</option>
                <option value="6">Julio</option>
                <option value="7">Agosto</option>
                <option value="8">Septiembre</option>
                <option value="9">Octubre</option>
                <option value="10">Noviembre</option>
                <option value="11">Diciembre</option>
            </select>
            <input type="hidden" name="day" id="day" value="1"/>
            <input type="submit" dojoType="dijit.form.Button" label="Ir" value="Ir"/>
        </fieldset>
    </form>
    </div>
    <%=renderCalendar(current, paramRequest)%>
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
</div>
<%!
    private String renderCalendar(Date current, SWBParamRequest paramRequest) {
        StringBuffer sbf = new StringBuffer();

        System.out.println("Current: " + current);
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
                "      <td class=\"head\" colspan=\"7\">" +
                "<a href=\"" + pml + "\">&lt;</a>    " +
                months[month] + " " + (year + 1900) +
                "    <a href=\"" + nml + "\">&gt;</a>" +
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
                    viewUrl.setParameter("year", String.valueOf(year + 1900));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(i));

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
                    viewUrl.setParameter("year", String.valueOf(year + 1900));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(i));

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