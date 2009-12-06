<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    Member member = Member.getMember(user, wpage);
    String act = "calendar";
    if (request.getParameter("act") != null)
        act = request.getParameter("act");

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

        //System.out.println("====act:" + request.getParameter("act") +" " + dateFormat.format(current));

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

        Date startOfMonth = new Date(current.getYear(), current.getMonth(), 1);
        Date endOfMonth = new Date(current.getYear(), current.getMonth(), (int)daysInMonth);

    if (act.equals("calendar")) {
        //System.out.println("====Mostrando eventos en " + dateFormat.format(current));

        

        Set<Integer> reserved = new TreeSet<Integer>();
        Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
        while(itev.hasNext()) {
            EventElement ee = itev.next();
            boolean isNow = false;
            int sDay = ee.getStartDate().getDate();
            int eDay = ee.getEndDate().getDate();

            //if(ee.getStartDate().getMonth() == startOfMonth.getMonth() && ee.getEndDate().getMonth() == startOfMonth.getMonth()) {
                //if(ee.getStartDate().getYear() == startOfMonth.getYear() && ee.getEndDate().getYear() == startOfMonth.getYear())
            if(ee.canView(member)) {
                if(isThisMonth(ee, startOfMonth, endOfMonth)) {
                    /*System.out.println("====El evento " + ee.getTitle() + " que inicia el "
                            + dateFormat.format(ee.getStartDate()) + " y termina el " +
                            dateFormat.format(ee.getEndDate()) + " se está llevando a cabo");*/
                    sDay = ee.getStartDate().getDate();
                    eDay = ee.getEndDate().getDate();
                    isNow = true;

                    if (ee.getStartDate().before(startOfMonth) && ee.getEndDate().after(startOfMonth)) {
                        sDay = 1;
                    }

                    if (ee.getEndDate().after(endOfMonth)) {
                        eDay = (int)daysInMonth;
                    }
                  }
            }

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
            <h2><a href="<%=pm.toString()+"#anchorDays"%>">&lt;</a>&nbsp;<%=months[ilmonth]%>&nbsp;&nbsp;<%=ilyear+1900%>&nbsp;<a href="<%=nm.toString()+"#anchorDays"%>">&gt;</a></h2>
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
    <%} else {
        //System.out.println("======Mostrando eventos del " + dateFormat.format(current));
            ArrayList<EventElement> events = new ArrayList<EventElement>();
            Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
            while(itev.hasNext()) {
                EventElement ee = itev.next();
                //El evento inicia o termina en esta fecha
                /*if (same(ee.getStartDate(), current) || same(ee.getEndDate(), current)) {
                    events.add(ee);
                } else if (current.after(ee.getStartDate()) && current.before(ee.getEndDate())) { //El evento se lleva a cabo en esta fecha
                    events.add(ee);
                }*/
                if (ee.canView(member)) {
                    if (isThisMonth(ee, startOfMonth, endOfMonth)) {
                        events.add(ee);
                    }
                }
            }

            Iterator<EventElement> eit = events.iterator();
            if (eit.hasNext()) {
                %>
                <div class="columnaIzquierda">
                    <h1>Eventos del <%=dateFormat.format(current)%></h1>
                    <%
                    while(eit.hasNext()) {
                        EventElement ev = eit.next();
                        if(ev.canView(member)) {
                            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
                            String rank = df.format(ev.getRank());

                            String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/microsite/EventResource/noevent.jpg";
                            String path = ev.getWorkPath();
                            if (ev.getEventThumbnail() != null)
                            {
                                int pos = ev.getEventThumbnail().lastIndexOf("/");
                                if (pos != -1)
                                {
                                    String sphoto = ev.getEventThumbnail().substring(pos + 1);
                                    ev.setEventThumbnail(sphoto);
                                }
                                pathPhoto = SWBPortal.getWebWorkPath() + path + "/" + ev.getEventThumbnail();
                            }
                            String postAuthor = "Usuario dado de baja";
                            if (ev.getCreator() != null)
                            {
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
                %>
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

    //El evento se lleva a cabo este mes?

    //La fecha de inicio del evento es igual o menor al inicio de mes
       //La fecha de fin del evento es mayor o igual al fin de mes
          //Se lleva a cabo

    if(same(event.getStartDate(), startOfM) || event.getStartDate().before(startOfM)) {
        //System.out.println("====>>El evento " + event.getTitle() + " inicio este mes o antes");
        if(same(event.getEndDate(), endOfM) || event.getEndDate().after(endOfM)) {
            //System.out.println("====>>El evento " + event.getTitle() + " termina este mes o despues");
            ret = true;
       } else {
            if(same(event.getEndDate(), endOfM) || (event.getEndDate().before(endOfM) && event.getEndDate().after(startOfM))) {
                //System.out.println("====>>El evento " + event.getTitle() + " termina en este mes");
                ret = true;
            }
        }
    }

    //La fecha de inicio del evento es mayor o igual a la fecha de inicio de mes
      //La fecha de fin del evento es menor o igual a la fecha de fin de mes

    if(same(event.getStartDate(), startOfM) || event.getStartDate().after(startOfM)) {
        if(same(event.getEndDate(), endOfM) || event.getEndDate().before(endOfM)) {
            ret = true;
        }
    }

    return ret;
}
%>