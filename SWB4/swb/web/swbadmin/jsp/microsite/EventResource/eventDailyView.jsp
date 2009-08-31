<%@page contentType="text/html"%>
<%@page import="java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
    Member member = Member.getMember(user, wpage);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
%>

<br />
<div id="panorama">
<%  if (member.canAdd()) { %>
    <div class="editarInfo"><p><a href="<%= paramRequest.getRenderUrl().setParameter("act", "add")%>">Agregar evento</a></p></div>
<%  }
    if (wputil != null && member.canView()) {
%>
<%      if (!wputil.isSubscribed(member)) { %>
    <div class="editarInfo"><p><a href="<%= paramRequest.getActionUrl().setParameter("act", "subcribe")%>">Suscribirse</a></p></div>
<%
        }else {
%>
    <div class="editarInfo"><p><a href="<%= paramRequest.getActionUrl().setParameter("act", "unsubcribe")%>">Cancelar suscripción</a></p></div>
<%      }
   } %>
    <div class="editarInfo"><p><a href="<%= paramRequest.getRenderUrl().setParameter("act", "calendar")%>">Regresar</a></p></div>
</div>

<div id="panorama">
<table border="0" cellspacing="3">
<%
    String day = request.getParameter("day");
    String month = request.getParameter("month");
    String year = request.getParameter("year");

    Date dNow = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month), Integer.valueOf(day));

    Iterator<EventElement> eit = EventElement.listEventElementsByDate(null, dNow, wpage, wpage.getWebSite());
    while(eit.hasNext()) {
        EventElement event = eit.next();
        if(event.canView(member)) {
            SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
            viewUrl.setParameter("year", year);
            viewUrl.setParameter("month", month);
            viewUrl.setParameter("day", day);
%>
        <tr>
            <td valign="top">
                <a href="<%=viewUrl%>">
                    <img src="<%= SWBPlatform.getWebWorkPath()+event.getEventThumbnail()%>" alt="<%=event.getDescription()%>" border="0" />
                </a>
            </td>
            <td valign="top">
                <div class="entryEvent">
                    <p style="line-height:1px;"><%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
                    <p style="line-height:1px;">&nbsp;</p>
                    <p class="tituloNaranja"><%= event.getTitle()%></p>
                    <p style="line-height:13px;"><%= event.getDescription()%></p>
                    <p style="line-height:1px;">&nbsp;</p>
                    <p style="line-height:13px;">Dirigido a: <strong><%= event.getAudienceType()%></strong></p>
                    <p style="line-height:13px;">Inicia:<strong><%= (event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></strong></p>
                    <p style="line-height:13px;">Termina:<strong><%= (event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></strong></p>
                    <p style="line-height:13px;">Lugar: <strong><%= event.getPlace()%></strong></p>
                    <p style="line-height:13px;"><%=event.getViews()%> vistas</p>
                </div>
            </td>
        </tr>
<%
        }
    }
    SWBResourceURL back = paramRequest.getRenderUrl();
    back.setParameter("year", year);
    back.setParameter("month", month);
    back.setParameter("day", day);
%>
</table>
</div>