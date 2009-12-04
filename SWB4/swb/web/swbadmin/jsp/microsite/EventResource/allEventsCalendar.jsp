<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
    String year = request.getParameter("year");
    String month = request.getParameter("month");
    int imonth = 0;
    String day = request.getParameter("day");
    Date current = new Date(System.currentTimeMillis());

    if(day != null && month != null && day != null) {
        current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));
        imonth = Integer.parseInt(month);
    }
%>

<%=renderCalendar(current, paramRequest)%>
<%!
    private String renderCalendar(Date current, SWBParamRequest paramRequest) {
        Set reserved = new TreeSet();
        Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
        while(itev.hasNext()) {
            reserved.add(itev.next().getCreated());
        }

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

        sbf.append("\n" +
                "  <div id=\"calendario\">\n" +
                "      <h2>" + months[month] + " " + (year + 1900) +
                "</h2>\n");

        //Render day labels
        sbf.append("<ul class=\"dias semana\">\n");
        for (int i = 0; i < 7; i++) {
            sbf.append("      <li>" + days[i] + "</li>\n");
        }
        sbf.append("    </ul>\n");

        sbf.append("<ul class=\"dias\">\n");
        //Fill the first week in the month with the appropiate day offset
        for (int i = 0; i < firstWeekDay; i++) {
            sbf.append("      <li>&nbsp;</li>\n");
        }

        int weekDay = firstWeekDay;
        for (int i = 1; i <= daysInMonth; i++) {
            //Not today
            
                sbf.append("<li>\n" + i + "</li>\n");
            
            weekDay++;
        }
        for(int i = 0; i < (7-weekDay); i++) {
            sbf.append("<li>&nbsp</li>\n");
        }
        sbf.append("</ul>\n");
        sbf.append("</div>\n");
        sbf.append("<div class=\"clear\">&nbsp;</div>");
        return sbf.toString();
    }
%>