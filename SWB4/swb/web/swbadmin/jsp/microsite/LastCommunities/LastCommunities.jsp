<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.model.*,java.util.*"%>
<h2 class="titulo2">Comunidades más recientes</h2>
<div class="cajas">
    <ul>
        <%
            //User user=(User)request.getAttribute("user");
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebSite site = webpage.getWebSite();
            Iterator communities = SWBComparator.sortByCreated(MicroSite.listMicroSites(site), true);
            int i = 0;
            while (communities.hasNext())
            {
                i++;
                MicroSite comm = (MicroSite) communities.next();

        %>
        <li><a href="<%=comm.getUrl()%>"><%=comm.getTitle()%></a></li>
        <%
                if (i == 5)
                {
                    break;
                }

            }
        %>
    </ul>
</div>