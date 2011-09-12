<%@page import="java.text.DateFormatSymbols"%><%@page import="org.semanticwb.portal.api.SWBResourceURL"%><%@page import="java.text.SimpleDateFormat"%><%@page import="org.semanticwb.*"%>
<%@page import="java.util.Comparator"%><%@page import="org.semanticwb.portal.api.SWBParamRequest"%><%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.*"%><%@page import="org.semanticwb.model.User"%><%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.SWBPortal"%><%@page import="org.semanticwb.portal.util.SWBPriorityComparator"%><%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="java.util.*"%><%@page import="org.semanticwb.model.Resource"%><%@page import="org.semanticwb.model.ResourceSubType"%>
<%@page import="org.semanticwb.model.WebSite"%><%@page import="mx.gob.stps.portalempleo.swb.*"%><%!
    public Set<Integer> getDays(Set<Event> eventos, int year, int month)
    {

        HashSet<Integer> getYears = new HashSet<Integer>();
        for (Event event : eventos)
        {
            Date date = event.getExpiration();
            if (date != null && date.getYear() == year && date.getMonth() == month && event.isActive())
            {
                getYears.add(date.getDate());
            }
        }
        return getYears;

    }

    public Set<Event> getEvents(Set<Event> eventos, int year, int month, int day)
    {

        HashSet<Event> getYears = new HashSet<Event>();
        for (Event event : eventos)
        {
            Date date = event.getExpiration();
            if (date != null && date.getYear() == year && date.getDate() == day && date.getMonth() == month && event.isActive())
            {
                getYears.add(event);
            }
        }
        return getYears;

    }

    public Set<Integer> getMonths(List<Event> eventos, int year)
    {

        HashSet<Integer> getYears = new HashSet<Integer>();
        for (Event event : eventos)
        {
            Date date = event.getExpiration();
            if (date != null && date.getYear() == year && event.isActive())
            {
                getYears.add(date.getMonth());
            }
        }
        return getYears;

    }

    public Set<Event> getEvents(List<Event> eventos, int year, int month)
    {

        HashSet<Event> getYears = new HashSet<Event>();
        for (Event event : eventos)
        {
            Date date = event.getExpiration();
            if (date != null && date.getYear() == year && date.getMonth() == month && event.isActive())
            {
                getYears.add(event);
            }
        }
        return getYears;

    }

    enum MonthOfYear
    {

        enero("Enero"),
        febrero("Febrero"),
        marzo("Marzo"),
        abril("Abril"),
        mayo("Mayo"),
        junio("Junio"),
        julio("Julio"),
        agosto("Agosto"),
        septiembre("Septiembre"),
        octubre("Octubre"),
        noviembre("Noviembre"),
        diciembre("Diciembre");
        private String description;

        MonthOfYear(String description)
        {
            this.description = description;
        }

        public String getDescription()
        {
            return this.description;
        }

        public MonthOfYear previus()
        {
            switch (this)
            {
                case enero:
                    return enero;
                case febrero:
                    return enero;
                case marzo:
                    return febrero;
                case abril:
                    return marzo;
                case mayo:
                    return abril;
                case junio:
                    return mayo;
                case julio:
                    return junio;
                case agosto:
                    return julio;
                case septiembre:
                    return agosto;
                case octubre:
                    return septiembre;
                case noviembre:
                    return octubre;
                case diciembre:
                    return noviembre;
                default:
                    return null;
            }
        }

        public MonthOfYear next()
        {
            switch (this)
            {
                case enero:
                    return febrero;
                case febrero:
                    return marzo;
                case marzo:
                    return abril;
                case abril:
                    return mayo;
                case mayo:
                    return junio;
                case junio:
                    return julio;
                case julio:
                    return agosto;
                case agosto:
                    return septiembre;
                case septiembre:
                    return octubre;
                case octubre:
                    return noviembre;
                case noviembre:
                    return diciembre;
                case diciembre:
                    return diciembre;
                default:
                    return null;
            }
        }

        public boolean hasNext()
        {
            switch (this)
            {
                case diciembre:
                    return false;
                default:
                    return true;
            }
        }

        public static MonthOfYear valueOf(int value) throws IllegalArgumentException
        {
            switch (value)
            {
                case 0:
                    return enero;
                case 1:
                    return febrero;
                case 2:
                    return marzo;
                case 3:
                    return abril;
                case 4:
                    return mayo;
                case 5:
                    return junio;
                case 6:
                    return julio;
                case 7:
                    return agosto;
                case 8:
                    return septiembre;
                case 9:
                    return octubre;
                case 10:
                    return noviembre;
                case 11:
                    return diciembre;
                default:
                    return null;
            }
        }
    }
%><%

            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            response.setContentType("text/html; charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            int currentYear = Calendar.getInstance().getTime().getYear();


            Iterator<Event> itevents = Event.ClassMgr.listEvents(paramRequest.getWebPage().getWebSite());
            List<Event> events = SWBUtils.Collections.copyIterator(itevents);
            List<Event> temp = new ArrayList<Event>();
            for (Event event : events)
            {
                if (event.getExpiration() != null && event.isValid() && currentYear == event.getExpiration().getYear())
                    temp.add(event);

            }
            events.clear();
            events.addAll(temp);
            Collections.sort(events, new Event.EventSortByExpiration());
            Locale locale = new Locale("es", "MX");
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", locale);
            GregorianCalendar gc = new GregorianCalendar(locale);
            boolean hasAhead = false, hasBehind = false;

            MonthOfYear moy;
            if (request.getParameter("m") == null)
            {
                try
                {
                    moy = MonthOfYear.valueOf(sdf.format(gc.getTime()).toLowerCase());
                }
                catch (IllegalArgumentException e)
                {
                    moy = MonthOfYear.valueOf(gc.getTime().getMonth());
                }
            }
            else
            {
                try
                {
                    moy = MonthOfYear.valueOf(Integer.parseInt(request.getParameter("m")));
                    if (moy.ordinal() < gc.get(Calendar.MONTH))
                        hasBehind = false;
                    else
                        hasBehind = true;
                }
                catch (NumberFormatException e)
                {
                    moy = MonthOfYear.valueOf(gc.getTime().getMonth());
                }
            }
            int m = moy.ordinal();

            gc = new GregorianCalendar(gc.get(Calendar.YEAR), m, 1, 0, 0, 0);
            Date di = gc.getTime();
            Set<Event> allEventsOnMonth = getEvents(events, currentYear, moy.ordinal());
            Set<Event> eventsOnMonth = new HashSet<Event>();
            for (Event event : allEventsOnMonth)
            {
                if (event.getExpiration().after(di))
                {
                    eventsOnMonth.add(event);
                }
            }
            if (eventsOnMonth.isEmpty())
            {
                int nextmonth = moy.next().ordinal();
                for (int imonth = nextmonth; imonth <= 11; imonth++)
                {
                    Set<Event> tempEvents = getEvents(events, currentYear, imonth);
                    if (!tempEvents.isEmpty())
                    {
                        moy = MonthOfYear.valueOf(imonth);
                        eventsOnMonth.clear();
                        eventsOnMonth.addAll(tempEvents);
                        break;
                    }
                }
            }

            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println(" dojo.require('dijit.dijit');");
            out.println("-->");
            out.println("</script>");
            SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(paramRequest.Call_DIRECT).setMode("roll");

            Set<Integer> months = getMonths(events, currentYear);
            if (months.size() > 0)
            {


                int nextmonth = moy.next().ordinal();
                int previousmonth = moy.previus().ordinal();


                for (int imonth = nextmonth; imonth <= 11; imonth++)
                {
                    Set<Event> tempEvents = getEvents(events, currentYear, imonth);
                    if (!tempEvents.isEmpty())
                    {
                        nextmonth = imonth;
                        hasAhead = true;
                        break;
                    }
                }




                for (int imonth = previousmonth; imonth >= 0; imonth--)
                {
                    Set<Event> tempEvents = getEvents(events, currentYear, imonth);
                    if (!tempEvents.isEmpty())
                    {
                        previousmonth = imonth;
                        hasBehind = true;
                        break;
                    }
                }
                if (hasAhead && nextmonth == 11 && moy == MonthOfYear.diciembre)
                {
                    hasAhead = false;
                }
                if (hasBehind && previousmonth == (Calendar.getInstance().getTime().getMonth() - 1))
                {
                    hasBehind = false;
                }
                if (hasBehind && Calendar.getInstance().getTime().getMonth() == 0)
                {
                    hasBehind = false;
                }
                java.text.DecimalFormat df = new java.text.DecimalFormat("00");
                out.println(" <div id=\"mes\">");
                if (hasBehind)
                {
                    url.setParameter("m", Integer.toString(previousmonth));
                    out.println(" <a id=\"mes_anterior\" href=\"javascript:postHtml('" + url + "','calendario_eventos')\" >mes anterior</a>");
                    //out.println(" <a id=\"mes_anterior\" href=\"#\" onclick=\"postHtml('" + url + "','calendario_eventos')\">mes anterior</a>");
                }
                out.println("  <h3>" + moy.getDescription() + "</h3>");
                if (hasAhead)
                {
                    url.setParameter("m", Integer.toString(nextmonth));
                    out.println(" <a id=\"mes_siguiente\" href=\"javascript:postHtml('" + url + "','calendario_eventos')\">mes siguiente</a>");
                }
                out.println(" </div>");
                
                events.clear();
                events.addAll(eventsOnMonth);
                Collections.sort(events, new Event.EventSortByExpiration());
                for (Event event : events)
                {
                    out.println("<div>");
                    out.println("<span class=\"dia_calendario\">" + df.format(event.getExpiration().getDate()) + "</span>");
                    out.println("<p><a href=\"" + event.getRealUrl() + "\">" + event.getTitle() + "</a></p>");
                    out.println(" </div>");
                }

            }

//        out.println("</div>");
//        url.setMode("vall").setCallMethod(paramRequest.Call_CONTENT);
//        out.println("<div class=\"bottom_calendario\">");
//        out.println(" <a style=\"float: right; margin: 10px 20px 0pt 0pt;\" href=\""+url+"\" class=\"links\">ver todos los eventos</a>");
//        out.println("</div>");
%>
