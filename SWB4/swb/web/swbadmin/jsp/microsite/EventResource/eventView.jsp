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

<div id="panorama">    
    <table border="0" >
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
        <td  valign="top">
            <table border="0"  cellspacing="3">
                <tr>
                    <td >
                        <a href="<%=viewurl%>"><img src="<%=SWBPlatform.getWebWorkPath()+event.getEventThumbnail()%>" alt="<%= event.getTitle()%>" border="0" /></a>
                    </td>
                    <td valign="top" align="left">
                        <div class="entryEvent">
                            <p style="line-height:5px;"><%=SWBUtils.TEXT.getTimeAgo(event.getCreated(), user.getLanguage())%></p>
                            <p class="tituloNaranja"><a href="<%=viewurl%>"><%=event.getTitle()%></a></p>
                            <p style="line-height:1px;">&nbsp;</p>
                            <p style="line-height:10px;">Inicia:<strong><%= (event.getStartDate()==null?"":dateFormat.format(event.getStartDate()))%></strong> a las <strong><%= (event.getStartTime()==null?"":timeFormat.format(event.getStartTime()))%></strong></p>
                            <p style="line-height:10px;">Termina:<strong><%= (event.getEndDate()==null?"":dateFormat.format(event.getEndDate()))%></strong> a las <strong><%= (event.getEndTime()==null?"":timeFormat.format(event.getEndTime()))%></strong></p>
                            <p style="line-height:5px;">&nbsp;</p>
                            <p style="line-height:10px;">Valoraci&oacute;n:<%=event.getRank()%></p>
                            <p style="line-height:10px;"><%=event.getViews()%> vistas</p>
                        </div>
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
</div>