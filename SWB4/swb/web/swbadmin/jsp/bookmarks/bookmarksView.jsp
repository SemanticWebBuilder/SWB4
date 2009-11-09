<%@page import="java.util.*,java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Iterator<BookmarkEntry> entries = (Iterator<BookmarkEntry>) request.getAttribute("entries");
            SWBResourceURL aUrl = paramRequest.getActionUrl().setAction("ADDNEW");
            //boolean showList = (Boolean) request.getAttribute("l");
            int maxBookmarks = 10;
            boolean existe=false;
            entries = (Iterator<BookmarkEntry>) request.getAttribute("entries");

            ArrayList<BookmarkEntry> arrayEntries=new ArrayList<BookmarkEntry>();
            if (entries != null && entries.hasNext()) {
                while (entries.hasNext())
                {
                    BookmarkEntry entry=entries.next();
                    arrayEntries.add(entry);
                    if(paramRequest.getWebPage().getUrl().equalsIgnoreCase(entry.getBookmarkURL()))
                    {
                        existe=true;
                    }
                }

                entries=arrayEntries.iterator();
            }
%>

    <h2>Mis Favoritos</h2>
    <%
    String sitepath=paramRequest.getWebPage().getWebSite().getId();
    String pathImg="/work/models/"+sitepath+"/css/images/blockListBack.png";
    if (paramRequest.getUser().isSigned())
    {
            if(!existe && paramRequest.getCallMethod()!=paramRequest.Call_CONTENT)
            {
                %>
                <div class="editarInfo">
                    <p>
                        <a href="<%=aUrl%>">Agregar esta p&aacute;gina </a>
                    </p>
                </div>
                <div class="clear">&nbsp;</div>
                <%
            }
     }
            int bkCount = 0;
            if (entries != null && entries.hasNext() /*&& showList*/) {
    %>
    <ul>
        <%
            while (entries.hasNext() && bkCount < maxBookmarks) {
                BookmarkEntry entry = entries.next();

        %>
        <li>
            <a href="<%=entry.getBookmarkURL()%>">
                <img alt="<%=entry.getTitle()%>"  src="<%=pathImg%>"/>
            </a>
                <a class="contactos_nombre" href="<%=entry.getBookmarkURL()%>">
                <%=entry.getTitle()%>
            </a>
            <%
                if(paramRequest.getCallMethod()==paramRequest.Call_CONTENT)
                {
            %>
            
                <p>
                    <a href="<%=paramRequest.getActionUrl().setAction("DELETE").setParameter("id", entry.getId())%>">Eliminar</a>
                </p>
            
            <%
                }
            %>
        </li>
        <%
                bkCount++;
            }
        %>
    </ul>
<%
    }
            %>
