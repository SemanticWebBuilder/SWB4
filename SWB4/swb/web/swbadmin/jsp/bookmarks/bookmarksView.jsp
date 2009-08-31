<%@page import="java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Iterator<BookmarkEntry> entries = (Iterator<BookmarkEntry>) request.getAttribute("entries");
            SWBResourceURL aUrl = paramRequest.getActionUrl().setAction("ADDNEW");
            boolean showList = (Boolean) request.getAttribute("l");
            int maxBookmarks = 10;
%>

<div>
    <%
    if (paramRequest.getUser().isSigned()) {
    %>
        <p>
            <a href="<%=aUrl%>">Marcar p&aacute;gina</a>
        </p>
    <%
    }
    %>
    <p>
    <%
        int bkCount = 0;
        if (entries != null && entries.hasNext() /*&& showList*/) {
    %>
        <ul>
        <%
            while (entries.hasNext() && bkCount < maxBookmarks) {
                BookmarkEntry entry = entries.next();
        %>
                <li><a href="<%=entry.getBookmarkURL()%>"><%=entry.getTitle()%></a>&nbsp;[<a href="<%=paramRequest.getActionUrl().setAction("DELETE").setParameter("id", entry.getId())%>">-</a>]</li>
        <%
                    //System.out.println(entry.getTitle() + "--" + entry.getBookmarkURL() + "--" + entry.getId());
                bkCount++;
            }
        %>
        </ul>
        <%
        }
        %>
    </p>
</div>