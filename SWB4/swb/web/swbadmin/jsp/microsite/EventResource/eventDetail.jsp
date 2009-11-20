<%@page contentType="text/html"%>
<%@page import="java.util.*,org.semanticwb.portal.lib.*,java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);

            String uri = request.getParameter("uri");
            EventElement event = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String rank = df.format(event.getRank());
            if (event != null && event.canView(member))
            {
                //event.incViews();

%>
<div class="columnaIzquierda">
    <%
                if (event != null && event.canView(member))
                {
                    event.incViews();

    %>
    <h2><%= event.getTitle()%></h2>
    <p><%= event.getDescription()%></p>
    <p align="center">
        <a href="<%= SWBPortal.getWebWorkPath() + event.getEventImage()%>" target="_self">
            <img id="img_<%=event.getId()%>" src="<%= SWBPortal.getWebWorkPath() + event.getEventThumbnail()%>" alt="<%=event.getTitle()%>" border="0" width="50%" height="50%" />
        </a>
    </p>
    <p><span class="itemTitle">Comienza:</span> <%= (event.getStartDate() == null ? "" : dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime() == null ? "" : timeFormat.format(event.getStartTime()))%><br>
            <span class="itemTitle">Termina:</span> <%= (event.getEndDate() == null ? "" : dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime() == null ? "" : timeFormat.format(event.getEndTime()))%></p>

    <br/>
    <%
                }
                SWBResponse res = new SWBResponse(response);
                event.renderGenericElements(request, res, paramRequest);
                out.write(res.toString());
            }
    %>

</div>
<div class="columnaCentro">        


    <%
            GregorianCalendar cal = new GregorianCalendar(new Locale("es"));
            SimpleDateFormat sf = new SimpleDateFormat("MMMM", new Locale("es"));
            String smonth = sf.format(event.getStartDate());
            smonth = smonth.substring(0, 1).toUpperCase() + smonth.substring(1);
            cal.setTime(event.getStartDate());
            int year = cal.get(cal.YEAR);
    %>
    <div id="calendario">
        <h2><%=smonth%> de <%=year%></h2>
        <ul class="dias semana">
            <li>D</li><li>L</li><li>M</li><li>M</li><li>J</li><li>V</li><li>S</li>
        </ul>
        <ul class="dias">
            <%
            int inicio = 1;
            int fin = cal.getActualMaximum(cal.DAY_OF_MONTH);
            cal.set(cal.DAY_OF_MONTH, 1);
            int offset = cal.get(cal.DAY_OF_WEEK) - cal.SUNDAY;
            for (int i = 1; i <= offset; i++)
            {
            %>
            <li>&nbsp;</li>
            <%            }
            cal.setTime(event.getStartDate());

            java.util.Calendar cend = java.util.Calendar.getInstance();
            cend.setTime(event.getEndDate());
            java.util.Calendar cinit = java.util.Calendar.getInstance();
            cinit.setTime(event.getStartDate());

            for (int i = inicio; i <= fin; i++)
            {
                boolean active = false;
                cal.set(cal.DAY_OF_MONTH, i);
                if (cal.compareTo(cinit) >= 0 && cal.compareTo(cend) <= 0)
                {
                    active = true;
                }
                if (active)
                {
            %>
            <li><a href="#"><%=i%></a></li>
            <%
                }
                else
                {
            %>
            <li><%=i%></li>
            <%

                }
            }
            %>            
        </ul>
        <div class="clear">&nbsp;</div>
    </div>
    <p>Audiencia: <%= event.getAudienceType()%></p>
    <p>Lugar: <strong><%= event.getPlace()%></strong></p>
    <p>Asistentes:
        <%
            Iterator<User> users = event.listAttendants();
            while (users.hasNext())
            {
                User m = users.next();
        %>
        <%= m.getFullName()%>
        <%
                if (users.hasNext())
                {
        %>,&nbsp;<%            }
            }
        %>
    </p>
    <p>Creado el: <%=dateFormat.format(event.getCreated())%></p>
    <p><%=event.getViews()%> vistas</p>
    <p>Calificación: <%=rank%></p>
    <%
            SWBResourceURL back = paramRequest.getRenderUrl().setParameter("act", "view");
            back = paramRequest.getRenderUrl().setParameter("act", "edit");
            back.setParameter("year", request.getParameter("year"));
            back.setParameter("month", request.getParameter("month"));
            back.setParameter("day", request.getParameter("day"));
            back.setParameter("uri", event.getURI());
    %>
    <p><a href="<%= paramRequest.getRenderUrl()%>">[Ver todos los eventos]</a></p>
    <%
            boolean issuscribed = false;
            Iterator<EventElement> it = EventElement.ClassMgr.listEventElementByAttendant(user, paramRequest.getWebPage().getWebSite());
            while (it.hasNext())
            {
                EventElement element = it.next();
                if (element.getURI().equals(event.getURI()))
                {
                    issuscribed = true;
                    break;
                }
            }
            if (event.canView(member) && !issuscribed)
            {


    %>
    <p><a href="<%=paramRequest.getActionUrl().setParameter("act", "attend").setParameter("uri", event.getURI())%>">[Asistir al evento]</a></p>
    <%  }
    %>
    <%
            if (event.canModify(member))
            {
    %>
    <p><a href="<%= back%>">[Editar información]</a></p>
    <%
            }
            if (event.canModify(member))
            {
    %>
    <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", event.getURI())%>">[Eliminar]</a></p>
    <%
            }%>

    <ul class="miContenido">
        <%
            SWBResourceURL urla = paramRequest.getActionUrl();
            if (user.isRegistered())
            {
                if (member == null)
                {
                    urla.setParameter("act", "subscribe");
        %>
        <li><a href="<%=urla%>">Suscribirse a esta comunidad a comunidad</a></li>
        <%
                        }
                        else
                        {
                            urla.setParameter("act", "unsubscribe");
        %>
        <li><a href="<%=urla%>">Cancelar suscripción a comunidad</a></li>
        <%
                }
            }
            String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?event=" + java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de eventos de la comunidad</a></li>
    </ul>
</div>