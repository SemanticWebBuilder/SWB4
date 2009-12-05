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

        //System.out.println("====act:" + act +" " + dateFormat.format(current));
        
    if (!act.equals("daily")) {
        //System.out.println("====Mostrando eventos en " + dateFormat.format(current));

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
            //System.out.println("====>Verificando " + ee.getTitle() + "[" + ee.getStartDate().getDate() + " - " + ee.getEndDate().getDate() + "]");
            if (ee.getStartDate().getMonth() == current.getMonth()) {
                for (int i = sDay; i <= eDay; i++) {
                    reserved.add(i);
                }
            }
        }

        /*Iterator<Integer> it = reserved.iterator();
        while (it.hasNext()) {
            System.out.println("===Hay evento el " + it.next());
        }*/

        //Build URL for next month
        SWBResourceURL nm = paramRequest.getRenderUrl();
        nm.setParameter("d", "1");
        nm.setParameter("m", String.valueOf(ilmonth + 1));
        nm.setParameter("y", String.valueOf(ilyear + 1900));

        //Build URL for previous month
        SWBResourceURL pm = paramRequest.getRenderUrl();
        pm.setParameter("d", "1");
        pm.setParameter("m", String.valueOf(ilmonth - 1));
        pm.setParameter("y", String.valueOf(ilyear + 1900));



        %>
        <h2>Eventos del mes</h2>
        <div id ="calendario" style="margin:10px">
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
            System.out.println("===En el else");
            ArrayList<EventElement> events = new ArrayList<EventElement>();
            Iterator<EventElement> itev = EventElement.ClassMgr.listEventElements();
            while(itev.hasNext()) {
                EventElement ee = itev.next();
                //El evento inicia o termina en esta fecha
                if (ee.getStartDate().equals(current) || ee.getEndDate().equals(current)) {
                    System.out.println("===El evento " + ee.getTitle() + " inicia o termina en esta fecha");
                    events.add(ee);
                } else if (current.after(ee.getStartDate()) && current.before(ee.getEndDate())) { //El evento se lleva a cabo en esta fecha
                    System.out.println("===El evento " + ee.getTitle() + " se lleva a cabo en esta fecha");
                    events.add(ee);
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