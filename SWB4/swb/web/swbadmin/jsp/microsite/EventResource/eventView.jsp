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
%>

<%
    if (member.canAdd()) {
%>
<br/>
<div id="panorama">
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "calendar")%>">Ver calendario</a></p></div>
    <div class="editarInfo">
        <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar evento</a></p>
    </div>

<%
    if( member.canView() ) {
        if(!wputil.isSubscribed(member)) {
%>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse</a></p>
    </div>
<%
        }else {
%>
    <div class="editarInfo">
        <p><a href="<%= paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a></p>
    </div>
<%
        }
    }
%>
</div>
<%  }%>

<br/><br/>
<div id="entriesWrapper">
<%
    Iterator<EventElement> it = EventElement.listEventElementByEventWebPage(wpage, wpage.getWebSite());
    while (it.hasNext()) {
        EventElement event = it.next();
        SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
        if (event.canView(member)) {
%>
    <div class="entry">
        <a href="<%=viewurl%>">
            <img src="<%=SWBPortal.getWebWorkPath()+event.getEventThumbnail()%>" alt="<%= event.getTitle()%>" border="0" />
        </a>
        <div class="entryInfo">            
            <p><%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
            <p class="tituloNaranja"><%=event.getTitle()%></p>
            <p class="eventoInicio">Inicia: <strong><%= (event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></strong></p>
            <p class="eventoFinal">Termina: <strong><%= (event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></strong></p>
            <p>Valoraci&oacute;n:<%=event.getRank()%></p>
            <p><%=event.getViews()%> vistas</p>
            <div class="clear">&nbsp;</div>
        </div>
    </div>
<%      }
    }
%>
</div>