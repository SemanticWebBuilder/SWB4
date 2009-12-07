<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%!
private static final int ELEMENETS_BY_PAGE = 5;
%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);
    String act = "calendar";
    if (request.getParameter("act") != null) act = request.getParameter("act");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
    String year = request.getParameter("y");
    String month = request.getParameter("m");
    String day = request.getParameter("d");

    Date current = new Date(System.currentTimeMillis());
    int imonth = 0;

    if(day != null && month != null && year != null) {
        current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));
        imonth = Integer.parseInt(month);
    }

    String cssPath = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";

    //System.out.println("====act:" + request.getParameter("act") +" " + dateFormat.format(current));

    String [] months = {"Enero", "Febrero", "Marzo", "Abril",
                            "Mayo", "Junio", "Julio", "Agosto",
                            "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    String [] days = {"D", "L", "M", "M", "J", "V", "S"};

    int ilmonth = current.getMonth();
    int ilyear = current.getYear();

    Date thisMonth = new Date(ilyear, ilmonth, 1);
    Date nextMonth = new Date(ilyear, ilmonth + 1, 1);

    //Find out when this mont starts and ends
    int firstWeekDay = thisMonth.getDay();
    long daysInMonth = Math.round((nextMonth.getTime() - thisMonth.getTime()) / (1000 * 60 * 60 * 24));
    Calendar cal = Calendar.getInstance();

    /*cal.get(Calendar.DAY_OF_MONTH);
    System.out.println("===Primer dia de la semana: " + firstWeekDay);
    System.out.println("===Primer dia de la semana2: " + (cal.getActualMaximum(cal.DAY_OF_WEEK) - cal.DAY_OF_WEEK_IN_MONTH));
    System.out.println("===Dia del mes: " + cal.get(Calendar.DAY_OF_MONTH));
    System.out.println("===Dia del mes2: " + current.getDate());
    System.out.println("===Mes: " + cal.get(Calendar.MONTH));
    System.out.println("===Mes2: " + current.getMonth());*/

    Date startOfMonth = new Date(current.getYear(), current.getMonth(), 1);
    Date endOfMonth = new Date(current.getYear(), current.getMonth(), (int)daysInMonth);

    if (act.equals("calendar") && paramRequest.getCallMethod() != paramRequest.Call_CONTENT) {
        //Get reserved days of the month
        Set<Integer> reserved = new TreeSet<Integer>();
        Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
        while(itev.hasNext()) {
            EventElement ee = itev.next();
            boolean isNow = false;
            int sDay = ee.getStartDate().getDate();
            int eDay = ee.getEndDate().getDate();

            if(ee.canView(member)) {
                //If the event is active this month
                if(isThisMonth(ee, startOfMonth, endOfMonth)) {
                    sDay = ee.getStartDate().getDate();
                    eDay = ee.getEndDate().getDate();
                    isNow = true;

                    //If the event started before this month, the month is reserved from its first day
                    if (ee.getStartDate().before(startOfMonth) && ee.getEndDate().after(startOfMonth)) {
                        sDay = 1;
                    }

                    //If the event ends after this month, the month is reserved to its last day
                    if (ee.getEndDate().after(endOfMonth)) {
                        eDay = (int)daysInMonth;
                    }
                }
            }

            //Add reserved days to the set
            for (int i = sDay; i <= eDay; i++) {
                if (isNow) {
                    reserved.add(i);
                }
            }
        }

        //Build URL for next month
        SWBResourceURL nm = paramRequest.getRenderUrl();
        nm.setParameter("d", "1");
        if (ilmonth == 11) {
            nm.setParameter("m", "0");
            nm.setParameter("y", String.valueOf((ilyear + 1) + 1900));
        } else {
            nm.setParameter("m", String.valueOf(ilmonth + 1));
            nm.setParameter("y", String.valueOf(ilyear + 1900));
        }

        //Build URL for previous month
        SWBResourceURL pm = paramRequest.getRenderUrl();
        pm.setParameter("d", "1");
        if (ilmonth == 0) {
            pm.setParameter("m", "11");
            pm.setParameter("y", String.valueOf((ilyear - 1) + 1900));
        } else {
            pm.setParameter("m", String.valueOf(ilmonth - 1));
            pm.setParameter("y", String.valueOf(ilyear + 1900));
        }

        %>
        <h2>Eventos del mes</h2>
        <div id ="calendario" style="margin:10px; height:220px;">
            <h2>
                <a href="<%=pm.toString()+"#anchorDays"%>">&lt;</a>&nbsp;<%=months[ilmonth]%>&nbsp;&nbsp;<%=ilyear+1900%>&nbsp;<a href="<%=nm.toString()+"#anchorDays"%>">&gt;</a>
            </h2>
            <ul id="anchorDays" class="dias semana">
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
                for (int i = 1; i <= daysInMonth; i++) {
                    if (reserved.contains(i)) {
                        String dayUrl = paramRequest.getWebPage().getWebSite().getWebPage("Eventos_del_dia").getUrl().toString();
                        dayUrl += "?act=daily&y=" + (ilyear + 1900) + "&m=" + ilmonth + "&d=" + i;
                        %><li><a href="<%=dayUrl%>"><%=i%></a></li><%
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
  <%} else if (act.equals("daily")) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
        ArrayList<EventElement> events = new ArrayList<EventElement>();
        Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
        while(itev.hasNext()) {
            EventElement ee = itev.next();
            if (ee.canView(member)) {
                //El evento inicia o termina en este día?
                if (same(ee.getStartDate(), current) || same(ee.getEndDate(), current)) {
                    events.add(ee);
                //El evento inició antes de este día y termina después de este día?
                } else if (ee.getStartDate().before(current) && ee.getEndDate().after(current)) {
                    events.add(ee);
                }
            }
        }

        int elementos = events.size();
        int paginas = elementos / ELEMENETS_BY_PAGE;
        if (elementos % ELEMENETS_BY_PAGE != 0) paginas++;
        int inicio = 0;
        int fin = ELEMENETS_BY_PAGE;
        int ipage = 1;
        if (request.getParameter("ipage") != null) {
            try {
                ipage = Integer.parseInt(request.getParameter("ipage"));
                inicio = (ipage * ELEMENETS_BY_PAGE) - ELEMENETS_BY_PAGE;
                fin = (ipage * ELEMENETS_BY_PAGE);
            } catch (NumberFormatException nfe) {
                    ipage = 1;
            }
        }
        if (ipage < 1 || ipage > paginas) ipage = 1;
        if (inicio < 0) inicio = 0;
        if (fin < 0) fin = ELEMENETS_BY_PAGE;
        if (fin > elementos) fin = elementos;
        if (inicio > fin)  {
            inicio = 0;
            fin = ELEMENETS_BY_PAGE;
        }
        if (fin - inicio > ELEMENETS_BY_PAGE) {
            inicio = 0;
            fin = ELEMENETS_BY_PAGE;
        }
        inicio++;

        if (events.size() > 0) {
            %>
            <div class="columnaIzquierda">
            <%
            if (paginas > 1) {
                %><div id="paginacion"><%
                String nextURL = "#";
                String previusURL = "#";
                if (ipage < paginas) {
                    nextURL = paramRequest.getWebPage().getUrl() + "?act=daily&y=" + (current.getYear() + 1900) + "&m=" +
                            current.getMonth() + "&d=" + current.getDate() + "&ipage=" + (ipage + 1);
                }
                if (ipage > 1) {
                    previusURL = paramRequest.getWebPage().getUrl() + "?act=daily&y=" + (current.getYear() + 1900) + "&m=" +
                            current.getMonth() + "&d=" + current.getDate() + "&ipage=" + (ipage - 1);
                }
                if (ipage > 1) {
                    %><a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"></a><%
                }
                for (int i = 1; i <= paginas; i++) {
                    %><a href="<%=wpage.getUrl()%>?act=daily&y=<%=current.getYear() + 1900%>&m=<%=current.getMonth()%>&d=<%=current.getDate()%>&ipage=<%=i%>"><%
                    if (i == ipage) {
                        %><strong><%
                    }%>
                    <%=i%>
                    <%
                    if (i == ipage) {
                        %></strong><%
                    }%>
                    </a>
                    <%
                }

                if (ipage != paginas) {
                    %><a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"></a><%
                }
                %></div><%
            }%>
            <h1>Eventos del <%=dateFormat.format(current)%></h1>
            <%
            int iElement = 0;
            for (EventElement ev : events) {
                if(ev.canView(member)) {
                    iElement++;
                    if (iElement > fin) break;
                        if (iElement >= inicio && iElement <= fin) {
                            String fechaEvento = "Sin determinar";
                            try {
                                fechaEvento = dateFormat.format(ev.getStartDate());
                            } catch (Exception e) { }
                            
                            String hfechaEvento = "Sin determinar";
                            try {
                                hfechaEvento = timeFormat.format(ev.getStartTime());
                            } catch (Exception e) { }

                            String rank = df.format(ev.getRank());
                            String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/microsite/EventResource/noevent.jpg";
                            String path = ev.getWorkPath();
                            if (ev.getEventThumbnail() != null) {
                                int pos = ev.getEventThumbnail().lastIndexOf("/");
                                if (pos != -1) {
                                    String sphoto = ev.getEventThumbnail().substring(pos + 1);
                                    ev.setEventThumbnail(sphoto);
                                }
                                pathPhoto = SWBPortal.getWebWorkPath() + path + "/" + ev.getEventThumbnail();
                            }

                            String postAuthor = "Usuario dado de baja";
                            if (ev.getCreator() != null) {
                                postAuthor = ev.getCreator().getFirstName();
                            }

                            String viewUrl = ev.getURL();
                            %>
                            <div class="noticia">
                                <img src="<%=pathPhoto%>" alt="<%= ev.getTitle()%>">
                                <div class="noticiaTexto">
                                    <h2><%=ev.getTitle()%></h2>
                                    <p>&nbsp;<br>Por: <%=postAuthor%><br><%=dateFormat.format(ev.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(ev.getCreated(), user.getLanguage())%></p>
                                    <p>
                                        <%=ev.getDescription()%> | <a href="<%=viewUrl%>">Ver m&aacute;s</a>
                                    </p>
                                    <p class="stats">
                                        Puntuación: <%=rank%><br>
                                        <%=ev.getViews()%> vistas
                                    </p>
                                </div>
                            </div>
                            <%
                        }
                }
            }
            %>
                <!-- paginacion -->
            <%
            if (paginas > 1) {
                %><div id="paginacion"><%
                String nextURL = "#";
                String previusURL = "#";
                if (ipage < paginas) {
                    nextURL = paramRequest.getWebPage().getUrl() + "?act=daily&y=" + (current.getYear() + 1900) + "&m=" +
                            current.getMonth() + "&d=" + current.getDate() + "&ipage=" + (ipage + 1);
                }
                if (ipage > 1) {
                    previusURL = paramRequest.getWebPage().getUrl() + "?act=daily&y=" + (current.getYear() + 1900) + "&m=" +
                            current.getMonth() + "&d=" + current.getDate() + "&ipage=" + (ipage - 1);
                }
                if (ipage > 1) {
                    %><a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"></a><%
                }

                for (int i = 1; i <= paginas; i++) {
                    %><a href="<%=wpage.getUrl()%>?act=daily&y=<%=current.getYear()+1900%>&m=<%=current.getMonth()%>&d=<%=current.getDate()%>&ipage=<%=i%>"><%
                    if (i == ipage) {
                        %><strong><%
                    }
                    %>
                    <%=i%>
                    <%
                    if (i == ipage) {
                        %></strong><%
                    }
                    %></a><%
                }
                if (ipage != paginas) {
                    %><a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"></a><%
                }
                %></div><%
            }
            %>
            <!-- fin paginacion -->
            </div>
            <%
        } else {
            %><h2>No existen eventos para este d&iacute;a</h2><%
        }
    }
%>

<%!
private boolean same(Date d1, Date d2) {
    boolean ret = false;

    if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate()) {
        ret = true;
    }

    return ret;
}
%>

<%!
private  boolean isThisMonth(EventElement event, Date startOfM, Date endOfM) {
    boolean ret = false;

    //El evento inició algun día de este mes?
    if ((same(event.getStartDate(), startOfM) || event.getStartDate().after(startOfM)) &&
            (same(event.getStartDate(), endOfM) || event.getStartDate().before(endOfM))) {
        ret = true;
    }

    //El evento inició algun día de este mes?
    if ((same(event.getEndDate(), startOfM) || event.getEndDate().after(startOfM)) &&
            (same(event.getEndDate(), endOfM) || event.getEndDate().before(endOfM))) {
        ret = true;
    }

    //El evento inició antes del mes y termina despues del mes?
    if (event.getStartDate().before(startOfM) && event.getEndDate().after(endOfM)) {
        ret = true;
    }

    return ret;
}
%>