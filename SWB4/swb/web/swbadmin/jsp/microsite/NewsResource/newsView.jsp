<%@page contentType="text/html"%>
<%@page import="java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Resource base = paramRequest.getResourceBase();
    User user = paramRequest.getUser();
    WebPage wpage = paramRequest.getWebPage();
    MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
    Member member = Member.getMember(user, wpage);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
%>

<%  if (member.canAdd()) {
%>
<div id="panorama">
    <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar noticia</a></p></div>
    <%  if(wputil != null && member.canView()) {
            if(!wputil.isSubscribed(member)) {
    %>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "subcribe").toString()%>">Suscribirse</a></p></div>
    <%
            }else {
    %>
    <div class="editarInfo"><p><a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString()%>">Cancelar suscripción</a></p></div>
    <%
            }
        }
    %>
</div>
<%  } %>

<div id="panorama">
<h1>Noticias</h1>
<table>
<%
    Iterator<NewsElement> eit = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
    while (eit.hasNext()) {
        NewsElement anew = eit.next();
        if(anew.canView(member)) {
            SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
%>
        <tr>
            <td valign="top">
                <a href="<%= viewUrl%>">
                    <img src="<%= SWBPlatform.getWebWorkPath()+anew.getNewsThumbnail() %>" alt="<%= anew.getTitle()%>" border="0" />
                </a>
            </td>
            <td valign="top">
                <p><%= anew.getTitle()%>&nbsp;(<%= anew.getCitation()%>)</p>
                <p>Por:&nbsp;<%= anew.getAuthor()%> - <%=SWBUtils.TEXT.getTimeAgo(anew.getCreated(), user.getLanguage())%></p>
                <p>
                    <strong><%= dateFormat.format(anew.getCreated()) %></strong>&nbsp;
                    <%= anew.getDescription()%>&nbsp;|&nbsp;
                    <a href="<%=viewUrl%>">Ver m&aacute;s</a>
                </p>                
                <p>Puntuación:&nbsp;<%= anew.getRank()%></p>
                <p><%= anew.getViews()%> vistas.</p>
            </td>
        </tr>
<%
        }
    }
%>
</table>
</div>