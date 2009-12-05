<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
    String year = request.getParameter("year");
    String month = request.getParameter("month");
    String day = request.getParameter("day");
    Date current = new Date(System.currentTimeMillis());
    int imonth = 0;

    if(day != null && month != null && day != null) {
        current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));
        imonth = Integer.parseInt(month);
    }
    
    System.out.println("====Mostrando eventos en " + dateFormat.format(current));
    
    String [] months = {"Enero", "Febrero", "Marzo", "Abril",
                            "Mayo", "Junio", "Julio", "Agosto",
                            "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    String [] days = {"D", "L", "M", "M", "J", "V", "S"};    

    int ilday = current.getDay();
    int ilmonth = current.getMonth();
    int ilyear = current.getYear();

    Date thisMonth = new Date(ilyear, ilmonth, 1);
    Date nextMonth = new Date(ilyear, ilmonth + 1, 1);

    //Find out when this mont starts and ends
    int firstWeekDay = thisMonth.getDay();
    long daysInMonth = Math.round((nextMonth.getTime() - thisMonth.getTime()) / (1000 * 60 * 60 * 24));

    Set<Integer> reserved = new TreeSet<Integer>();
    Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
    while(itev.hasNext()) {
        EventElement ee = itev.next();
        int sDay = ee.getStartDate().getDate();
        int eDay = ee.getEndDate().getDate();
        //Termina despues de este mes?
        if (ee.getEndDate().getMonth() > ee.getStartDate().getDate()) {
            eDay = Integer.parseInt(String.valueOf(daysInMonth));
        }
        System.out.println("====>Verificando " + ee.getTitle() + "[" + ee.getStartDate().getDate() + " - " + ee.getEndDate().getDate() + "]");
        if (ee.getStartDate().getMonth() == current.getMonth()) {
            for (int i = sDay; i <= eDay; i++) {
                reserved.add(i);
            }
        }
    }

    Iterator<Integer> it = reserved.iterator();
    while (it.hasNext()) {
        System.out.println("===Hay evento el " + it.next());
    }

    %>
    <h2>Eventos del mes</h2>
    <div id ="calendario">
        <h2><%=months[ilmonth]%>&nbsp;&nbsp;<%=ilyear+1900%></h2>
        <ul class="dias semana">
            <%
            for(int i = 0; i < 7; i++) {
                %><li><%=days[i]%></li><%
            }
            %>
        </ul>
        <ul class="dias">
            <%for(int i = 0; i < firstWeekDay; i++) {
                %><li>&nbsp;</li><%
            }
    
            int weekDay = firstWeekDay;
            for (int i = 1; i < daysInMonth; i++) {
                if (reserved.contains(i)) {
                    %><li><a href="#"><%=i%></a></li><%
                } else {
                    %><li><%=i%></li><%
                }
            }

            for(int i = 0; i < (7-weekDay); i++) {
                %><li>&nbsp</li><%
            }
            %>
        </ul>
    </div>
    <div class="clear">&nbsp;</div>