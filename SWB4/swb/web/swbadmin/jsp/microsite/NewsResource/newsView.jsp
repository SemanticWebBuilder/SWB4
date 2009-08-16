<%@page contentType="text/html"%>
<%@page import="java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
%>
<h1>Noticias</h1>
<table>
    <tbody>
        <%
            Iterator<NewsElement> eit = NewsElement.listNewsElementByNewsWebPage(wpage, wpage.getWebSite());
            while (eit.hasNext()) {
                NewsElement anew = eit.next();
                SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("uri", anew.getURI());
                if (anew.canView(member)) {
        %>
        <tr>
            <td>
                <a href="<%=viewUrl%>"><%=anew.getTitle()%></a>(<%=anew.getCreated()%>)<BR>
                Por:&nbsp;<b><%=(anew.getAuthor()==null?"":anew.getAuthor())%></b><BR>
                <%=(anew.getAbstr()==null?"":anew.getAbstr())%><BR>
                En:&nbsp;<%=(anew.getCitation()==null?"":anew.getCitation())%><BR>
                Enlace:&nbsp;<%=(anew.getFullText()==null?"":anew.getFullText())%><BR>
                Puntuación:&nbsp;<%=anew.getRank()%><BR>
                <%=anew.getViews()%> vistas.
            </td>
        </tr>
        <%
                }
            }
        %>
    </tbody>
</table>
<%
            if (member.canAdd()) {
%>
<center>
    <a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar Noticia</a>
    <%
        if (wputil != null && member.canView()) {
            if (!wputil.isSubscribed(member)) {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "subcribe").toString()%>">Suscribirse a este elemento</a>
    <%
                } else {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubcribe").toString()%>">Cancelar suscripción</a>
    <%
            }

        }
    %>
</center>
<%  }%>