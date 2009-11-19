<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
            String lang = "es";
            String year = request.getParameter("y");
            String month = request.getParameter("m");
            String day = request.getParameter("d");
            Date current = null;

            if (day != null && month != null && year != null)
            {
                current = new Date(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
            }
            if (user.getLanguage() != null)
            {
                lang = user.getLanguage();
            }
%>

<div class="columnaIzquierda">

    
    <%
            Iterator<EventElement> it;

            if (current == null)
            {
                it = EventElement.ClassMgr.listEventElementByAttendant(user, paramRequest.getWebPage().getWebSite());
            }
            else
            {
                it = EventElement.listEventElementsByDate(user, current, wpage, wpage.getWebSite());
            }
            boolean hasEvents=false;
            while (it.hasNext())
            {

                EventElement event = it.next();
                String viewUrl = event.getURL(); 
                String rank = df.format(event.getRank());
                if (event.canView(member))
                {
                    hasEvents=true;
    %>
    <div class="noticia">
        <img src="<%=SWBPortal.getWebWorkPath() + event.getEventThumbnail()%>" alt="<%= event.getTitle()%>">
        <div class="noticiaTexto">
            <h2><%=event.getTitle()%></h2>
            <p>&nbsp;<br>Por: <%=event.getCreator().getFullName()%><br><%=dateFormat.format(event.getCreated())%> - <%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
            <p>
                <%=event.getDescription()%> | <a href="<%=viewUrl%>">Ver más</a>
            </p>
            <p class="stats">
            	Puntuación: <%=rank%><br>
                <%=event.getViews()%> vistas
            </p>
        </div>
    </div>
    <%-- <div class="entry">
        
        <a href="<%=viewurl%>">
            <img src="<%=SWBPortal.getWebWorkPath() + event.getEventThumbnail()%>" alt="<%= event.getTitle()%>" >
        </a>
        <div class="entryInfo">
            <p>Creado: <%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
            <p class="tituloNaranja"><%=event.getTitle()%></p>
            <p class="eventoInicio">Inicia: <strong><%= (event.getStartDate() == null ? "" : dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime() == null ? "" : timeFormat.format(event.getStartTime()))%></strong></p>
            <p class="eventoFinal">Termina: <strong><%= (event.getEndDate() == null ? "" : dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime() == null ? "" : timeFormat.format(event.getEndTime()))%></strong></p>
            <p>Valoraci&oacute;n:<%=event.getRank()%></p>
            <p><%=event.getViews()%> vistas</p>
            <div class="clear">&nbsp;</div>
        </div>
               </div> --%>

    <%      }
            }
    %>
    <%
        if(!hasEvents)
            {
            %>
            <p>No hay eventos registrados.</p>
            <%
            }
    %>
</div>
<div class="columnaCentro"></div>