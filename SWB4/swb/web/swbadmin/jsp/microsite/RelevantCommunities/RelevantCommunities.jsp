<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.model.*,java.util.*"%>
<%!
    public static Iterator sortByViews(Iterator<MicroSite> it, boolean ascendente)
    {
        TreeSet<MicroSite> set = null;
        if (ascendente)
        {
            set = new TreeSet<MicroSite>(new Comparator()
            {

                public int compare(Object o1, Object o2)
                {
                    if (o1 != null && o2 != null)
                    {
                        MicroSite ob1 = (MicroSite) (o1);
                        MicroSite ob2 = (MicroSite) (o2);
                        Long v1 = ob1.getViews();
                        Long v2 = ob2.getViews();
                        int ret = v1.compareTo(v2);
                        return ret;
                    }
                    else
                    {
                        return -1;
                    }
                }
            });
        }
        else
        {
            set = new TreeSet<MicroSite>(new Comparator()
            {

                public int compare(Object o1, Object o2)
                {
                    if (o1 != null && o2 != null)
                    {
                        MicroSite ob1 = (MicroSite) (o1);
                        MicroSite ob2 = (MicroSite) (o2);
                        Long v1 = ob1.getViews();
                        Long v2 = ob2.getViews();
                        int ret = v2.compareTo(v1);
                        return ret;
                    }
                    else
                    {
                        return -1;
                    }
                    
                }
            });
        }
        while (it.hasNext())
        {
           
            MicroSite obj = it.next();
            set.add(obj);
        }
        return set.iterator();
    }
%>

<h3>Las m&aacute;s activas</h3>
<ul class="comunidades">
    <%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            //WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebPage webpage = paramRequest.getWebPage();
            WebSite site = webpage.getWebSite();
            Iterator communities = sortByViews(MicroSite.ClassMgr.listMicroSites(site), false);
            if (!communities.hasNext())
            {
    %>
    <li>&nbsp;</li>
    <%      }
            communities = sortByViews(MicroSite.ClassMgr.listMicroSites(site), false);
            int i = 0;
            while (communities.hasNext())
            {

                Object obj = communities.next();
                if (obj != null && obj instanceof MicroSite)
                {
                    MicroSite comm = (MicroSite) communities.next();
                    if (comm != null && comm.getUrl() != null && comm.getTitle() != null && comm.isActive())
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
            }

    %>
</ul>
