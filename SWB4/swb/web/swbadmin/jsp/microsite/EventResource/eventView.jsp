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
<a href="<%=paramRequest.getRenderUrl().setParameter("act", "calendar")%>">Ver calendario</a>
<table border="0" width="100%">
    <%
                Iterator<EventElement> it = EventElement.listEventElementByEventWebPage(wpage, wpage.getWebSite());
                int i = 0;
                while (it.hasNext()) {
                    EventElement event = it.next();
                    SWBResourceURL viewurl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", event.getURI());
                    if (event.canView(member)) {
                        if (i % 2 == 0) {
                            %><tr><%
                        }
    %>
    <td width="50%" valign="top">
        <table border="0" width="100%" cellspacing="10">
            <tr>
                <td valign="top" width="130">
                    <a href="<%=viewurl%>"><img src="<%=event.getEventThumbnail()%>" width="125"></a>
                </td>
                <td valign="top" align="left">
                    <small>
                        <b><%=event.getTitle()%></b>(<%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%>) <BR>
                        Inicia:<b><%=dateFormat.format(event.getStartDate())%></b> a las <b><%=timeFormat.format(event.getStartTime())%></b>
                        Termina:<b><%=dateFormat.format(event.getEndDate())%></b> a las <b><%=timeFormat.format(event.getEndTime())%></b>
                        Valoraci&oacute;n:<%=event.getRank()%> <BR>
                        <%=event.getViews()%> vistas<BR>
                    </small>
                </td>
            </tr>
        </table>
    </td>
    <%
                        if (i % 2 == 1) {
                            %></tr><%
                        }
                        i++;
                    }
                }
                if (i % 2 == 1) {
                    %><td></td></tr><%
                }
    %>
</table>
<%
            if (member.canAdd()) {
%>
<center>
    <a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar Evento</a>
    <%
            if (wputil != null && member.canView()) {
                if (!wputil.isSubscribed(member)) {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse a este elemento</a>
    <%
            } else {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a>
    <%
                }
            }
    %>
</center>
<%  }%>