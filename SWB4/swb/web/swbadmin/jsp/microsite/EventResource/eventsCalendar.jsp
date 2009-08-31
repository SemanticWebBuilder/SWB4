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
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dojo.parser");

    function openTooltip(oid, ttid, tip) {
        var o = document.getElementById(oid);
        var coords = dojo.coords(o);

        var tt = document.createElement('div');
        tt.setAttribute('id', ttid);
	tt.setAttribute("style", "position: absolute;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;line-height: 3px;color: #000000;background-color: #FFFF99;padding-right: 5px;padding-left: 5px;position: absolute;border: 1px solid #AAAAAA;");
	tt.style.top = (coords.y)+'px';
	tt.style.left = (coords.x+coords.w+2)+'px';
        tt.innerHTML = tip;
        document.body.appendChild(tt);
    }

    function closeTooltip(ttid) {
        var tt = document.getElementById(ttid);
        document.body.removeChild(tt);
    }


</script>

<br />
<div id="panorama">
<%  if (member.canAdd()) { %>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add")%>">Agregar evento</a></p></div>
<%  }
    if (wputil != null && member.canView()) {
%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "view")%>">Ver todos</a></p></div>
<%      if (!wputil.isSubscribed(member)) { %>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "subcribe")%>">Suscribirse</a></p></div>
<%
        }else {
%>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubcribe")%>">Cancelar suscripci�n</a></p></div>
<%      }
   } %>
</div>
<br/>
<div id="panorama">
    <form action="<%=paramRequest.getRenderUrl().setParameter("act", "calendar")%>" method="post">
        <fieldset><legend>Elige una fecha</legend>
            <label for="year">A�o:</label>
            <input type="text" style="width:70px;" regExp="\d{4}" dojoType="dijit.form.ValidationTextBox" name="year" id="year" value="<%=current.getYear() + 1900%>"/>
            <label for="month">Mes:</label>
            <select dojoType="dijit.form.FilteringSelect" style="width:150px;" name ="month" id="month" value="<%=current.getMonth()%>">
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
            
    <%=renderCalendar(current, wpage, paramRequest)%>
</div>
<%!
    private String renderCalendar(Date current, WebPage wpage, SWBParamRequest paramRequest) {
        String resId = paramRequest.getResourceBase().getId();
        StringBuilder sbf = new StringBuilder();

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
        SWBResourceURL nml = paramRequest.getRenderUrl().setParameter("act", "calendar");
        nml.setParameter("year", String.valueOf(year + 1900));
        nml.setParameter("month", String.valueOf(month + 1));
        nml.setParameter("day", "1");

        SWBResourceURL pml = paramRequest.getRenderUrl().setParameter("act", "calendar");
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

            Iterator<EventElement> evsNow = EventElement.listEventElementsByDate(null, new Date(year, month, i), wpage, wpage.getWebSite());

            String eventTitles = "";
            while (evsNow.hasNext()) {
                EventElement eve = evsNow.next();
                eventTitles = eventTitles + "<p>" + eve.getTitle().trim() + "</p>";
            }

            //Today?
            if (day == i - 1) {
                //Are there events today?
                if (!eventTitles.equals("")) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year + 1900));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(i));

                    sbf.append("<td id=\"td_"+i+resId+"\" class=\"dated\">\n");
                    sbf.append("  <div class=\"daylabel\"><a href=\""+viewUrl+"\" onmouseover=\"openTooltip('td_"+i+resId+"','tt_"+i+resId+"','"+eventTitles+"')\" onmouseout=\"closeTooltip('tt_"+i+resId+"')\">" + i + "</a></div>\n");
                    sbf.append("</td>\n");
                }  else {
                    //There aren't events today
                    sbf.append("<td class=\"day\">\n");
                    sbf.append("  <div class=\"daylabel\">" + i + "</div>\n");
                    sbf.append("</td>\n");
                }
            } else {
                //Not today
                evsNow = EventElement.listEventElementsByDate(null, new Date(year, month, i), wpage, wpage.getWebSite());
                if (!eventTitles.equals("")) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year + 1900));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(i));

                    sbf.append("<td id=\"td_"+i+resId+"\" class=\"dated\">\n");
                    sbf.append("  <div class=\"daylabel\"><a href=\""+viewUrl+"\"  onmouseover=\"openTooltip('td_"+i+resId+"','tt_"+i+resId+"','"+eventTitles+"')\" onmouseout=\"closeTooltip('tt_"+i+resId+"')\">" + i + "</a></div>\n");
                    sbf.append("</td>\n");
                } else {
                    sbf.append("<td class=\"day\" >\n");
                    sbf.append("  <div class=\"daylabel\">" + i + "</div>\n");
                    sbf.append("</td>\n");
                }
            }
            weekDay++;
        }
        for(int i = 0; i < (7-weekDay); i++) {
            sbf.append("<td class=\"empty\" ></td>\n");
        }
        sbf.append("</tr>\n");
        sbf.append("</table>\n");
        sbf.append("</div>\n");

        return sbf.toString();
    }
%>