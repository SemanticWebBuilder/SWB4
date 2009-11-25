<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.model.*,java.util.*"%>
<h2 class="titulo2">Comunidades</h2>
<h3>Las m&aacute;s recientes</h3>
<ul class="comunidades">
    <%
            //User user=(User)request.getAttribute("user");
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebSite site = webpage.getWebSite();
            Iterator communities = SWBComparator.sortByCreated(MicroSite.ClassMgr.listMicroSites(site), false);
            if (!communities.hasNext())
            {
    %>
    <li>&nbsp;</li>
    <%            }
            int i = 0;
            communities = SWBComparator.sortByCreated(MicroSite.ClassMgr.listMicroSites(site), false);
            while (communities.hasNext())
            {

                MicroSite comm = (MicroSite) communities.next();
                if (comm.isActive())
                {
                    i++;
    %>
    <li><a href="<%=comm.getUrl()%>"><%=comm.getTitle()%></a></li>
    <%
                    if (i == 5)
                    {
                        break;
                    }
                }

            }
    %>
</ul>