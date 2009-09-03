<%@page import="java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Iterator<BookmarkEntry> entries = (Iterator<BookmarkEntry>) request.getAttribute("entries");
            SWBResourceURL aUrl = paramRequest.getActionUrl().setAction("ADDNEW");
            boolean showList = (Boolean) request.getAttribute("l");
            int maxBookmarks = 10;
%>
<div class="contacto">
    <h2>Mis Favoritos</h2>        
    <%
            int bkCount = 0;
            if (entries != null && entries.hasNext() /*&& showList*/) {
    %>
    <p>
    <ul>
        <%
            while (entries.hasNext() && bkCount < maxBookmarks) {
                BookmarkEntry entry = entries.next();
        %>
        <li style="list-style-type:none;">
            <a class="contactos_nombre" href="<%=entry.getBookmarkURL()%>">
                <img src="/work/models/Ciudad_Digital/css/boton_contacto.png"/><%=entry.getTitle()%>
            </a>&nbsp;<!--a class="editarInfo" href="%=paramRequest.getActionUrl().setAction("DELETE").setParameter("id", entry.getId())%>">Eliminar</a-->
        </li>
        <%
                bkCount++;
            }
        %>
    </ul>
</p>
<%
    }
    if (paramRequest.getUser().isSigned()) {
%>
<div class="editarInfo" style="float:right;">
    <p>
        <a href="<%=aUrl%>">Marcar p&aacute;gina</a>
    </p>
</div>
<%
        }
%>
</div>