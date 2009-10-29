<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.model.*,java.util.*"%>
<%!
    public static Iterator sortByViews(Iterator it, boolean ascendente)
    {
        TreeSet set = null;
        if (ascendente)
        {
            set = new TreeSet(new Comparator()
            {

                public int compare(Object o1, Object o2)
                {
                    MicroSite ob1 = (MicroSite) (o1);
                    MicroSite ob2 = (MicroSite) (o2);
                    Long v1 = ob1.getViews();
                    Long v2 = ob2.getViews();
                    int ret = v1.compareTo(v2);
                    return ret;
                }
            });
        }
        else
        {
            set = new TreeSet(new Comparator()
            {

                public int compare(Object o1, Object o2)
                {
                    MicroSite ob1 = (MicroSite) (o1);
                    MicroSite ob2 = (MicroSite) (o2);
                    Long v1 = ob1.getViews();
                    Long v2 = ob2.getViews();
                    int ret = v2.compareTo(v1);
                    return ret;
                }
            });
        }
        while (it.hasNext())
        {
            set.add(it.next());
        }
        return set.iterator();
    }
%>

<h3>Las m&aacute;s activas</h3>
    <ul class="comunidades">
        <%
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebSite site = webpage.getWebSite();
            Iterator communities = sortByViews(MicroSite.ClassMgr.listMicroSites(site), false);
            int i = 0;
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
