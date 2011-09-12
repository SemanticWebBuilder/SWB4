<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.portal.util.SWBPriorityComparator"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.ResourceSubType"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.Resourceable"%>
<%@page import="mx.gob.stps.portalempleo.swb.*"%>
<%!
    public Set<Integer> getYears(List<Event> eventos)
    {

        HashSet<Integer> getYears = new HashSet<Integer>();
        for (Event event : eventos)
        {
            if(event.isValid() &&  event.getStart()!=null && event.isActive())
            {
                getYears.add(event.getStart().getYear());
            }
        }
        return getYears;

    }

    public Set<Integer> getMonths(List<Event> eventos, int year)
    {

        HashSet<Integer> getYears = new HashSet<Integer>();
        Date date;
        for (Event event : eventos)
        {
            date = event.getStart();
            if (date != null && date.getYear() == year && event.isActive() && event.isValid())
            {
                getYears.add(date.getMonth());
            }
        }
        return getYears;

    }

    public Set<Event> getEvents(List<Event> eventos, int year, int month)
    {

        HashSet<Event> getYears = new HashSet<Event>();
        Date date;
        for (Event event : eventos)
        {
            date = event.getStart();
            if (date != null && date.getYear() == year && date.getMonth() == month && event.isActive() && event.isValid())
            {
                getYears.add(event);
            }
        }
        return getYears;

    }

    /*class EventSortByExpiration implements Comparator<Event>
    {

        public int compare(Event event1, Event event2)
        {
            return event1.getExpiration().compareTo(event2.getExpiration());
        }
    }*/
%>
<%
            String defaultFormat = "dd 'de' MMMM 'del' yyyy";
            Locale es = new Locale("es");
            SimpleDateFormat df = new SimpleDateFormat(defaultFormat, es);
            DateFormatSymbols symbols = df.getDateFormatSymbols();
            String[] months =
            {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
            };
            symbols.setMonths(months);
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Iterator<Event> itevents = Event.ClassMgr.listEvents(paramRequest.getWebPage().getWebSite());
            List<Event> events = SWBUtils.Collections.copyIterator(itevents);
            HashSet<Event> finalEvents = new HashSet<Event>();
            Collections.sort(events, new Event.EventSortByStartDate());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            Date now = cal.getTime();
            if ("hist".equals(paramRequest.getAction()))
            {

                for (Event event : events)
                {
                    Date date = event.getStart();
                    if (date != null && date.before(now))
                    {
                        finalEvents.add(event);
                    }
                }
                events.clear();
                events.addAll(finalEvents);


%>


<h3 class="tituloSeccion">Eventos Anteriores</h3>
<%
                Set<Integer> years = getYears(events);
                if (years.size() == 0)
                {
%>
<p>No hay eventos anteriores</p>
<%                    }
%>

<div id="Accordion1" class="Accordion">
    <%
                    List<Integer> yearsSort = new ArrayList<Integer>();
                    yearsSort.addAll(years);
                    Collections.sort(yearsSort, new Comparator<Integer>()
                    {

                        public int compare(Integer i1, Integer i2)
                        {
                            return i2.compareTo(i1);
                        }
                    });
                    for (Integer year : yearsSort)
                    {
                        String syear = "" + (year + 1900);
    %>
    <div class="AccordionPanel">
        <div class="AccordionPanelTab"><%=syear%></div>
        <div class="AccordionPanelContent">
            <%
                                    List<Integer> monthSort = new ArrayList<Integer>();
                                    monthSort.addAll(getMonths(events, year));
                                    Collections.sort(monthSort, new Comparator<Integer>()
                                    {

                                        public int compare(Integer i1, Integer i2)
                                        {
                                            return i2.compareTo(i1);
                                        }
                                    });

                                    for (Integer month : monthSort)
                                    {
                                        String smonth = months[month];


            %>
            <div class="mesNoticias">
                <h3><%=smonth%></h3>
                <%

                                                        List<Event> eventSort = new ArrayList<Event>();
                                                        eventSort.addAll(getEvents(events, year, month));
                                                        Collections.sort(eventSort, new Comparator<Event>()
                                                        {

                                                            public int compare(Event i1, Event i2)
                                                            {
                                                                return i2.getStart().compareTo(i1.getStart());
                                                            }
                                                        });
                                                        for (Event event : eventSort)
                                                        {
                                                            if (event.getStart() != null)
                                                            {
                                                                String text = "Sin título";
                                                                if (event.getTitle() != null)
                                                                {
                                                                    text = event.getTitle();
                                                                }
                                                                String description = "Sin descripción";
                                                                if (event.getDescription() != null)
                                                                {
                                                                    description = event.getDescription();
                                                                }
                                                                Date date = event.getStart();

                                                                String dateToShow = df.format(date);
                                                                String url = event.getUrl();
                %>
                <div class="noticia">
                    <img src="/work/models/empleo/foro/ico_conversacion.gif" alt="Noticia INFOTEC" >
                    <h4><a href="<%=url%>"><%=text%></a></h4>
                    <p>
                        <span class="resaltado"><%=dateToShow%>.&nbsp;</span> <%=description%>
                    </p>
                </div>
                <%
                                                            }
                                                        }
                %>

            </div>
            <%                                                }
            %>


        </div>
    </div>
    <%
                    }
    %>




</div>
    <script type="text/javascript">
    <!--
    var Accordion1 = new Spry.Widget.Accordion("Accordion1");
    //-->
</script>
<%
                SWBResourceURL urlHist = paramRequest.getRenderUrl();
                urlHist.setAction("act");
%>

<br/><a href="<%=urlHist%>" id="noticiasAnteriores">Ver Eventos Recientes</a>

<%                            }
            else
            {

                for (Event event : events)
                {
                    Date date = event.getStart();
                    if (date != null && event.isValid() && date.after(now))
                    {
                        finalEvents.add(event);
                    }
                }
%>
<div id="noticiasRecientes">
    <h3 class="tituloSeccion">Eventos Recientes</h3>
    <%
                    if (finalEvents.size() == 0)
                    {
    %>
    <p>No hay eventos recientes</p>
    <%                                        }
                    events.clear();
                    events.addAll(finalEvents);
                    Collections.sort(events, new Event.EventSortByStartDate());
                    for (Event event : events)
                    {
                        if (event.getStart() != null)
                        {
                            String text = "Sin título";
                            if (event.getTitle() != null)
                            {
                                text = event.getTitle();
                            }
                            String description = "Sin descripción";
                            if (event.getDescription() != null)
                            {
                                description = event.getDescription();
                            }
                            Date date = event.getStart();
                            String dateToShow = df.format(date);
                            String url = event.getUrl();
    %>
    <div class="noticia">
        <img src="/work/models/empleo/foro/ico_conversacion.gif" alt="Noticia de TICs" >
        <h4><%=text%></h4>
        <p><%=dateToShow%>.&nbsp;<%=description%>
        </p>
        <a href="<%=url%>" class="verNotaCompleta">ver evento</a>
    </div>
    <%
                        }
                    }
                    SWBResourceURL urlHist = paramRequest.getRenderUrl();
                    urlHist.setAction("hist");
    %>
    <br/><a href="<%=urlHist%>" id="noticiasAnteriores">Ver Eventos Anteriores</a>
</div>
<%


            }

%>



