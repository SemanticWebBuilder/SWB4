<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.model.*,java.util.*,org.semanticwb.platform.*"%>
<h2 class="titulo2">Comunidades</h2>
<h3>Las m&aacute;s recientes</h3>
<ul class="comunidades">
    <%
            //User user=(User)request.getAttribute("user");
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebSite site = webpage.getWebSite();
            TreeSet<SemanticObject> setVals = new TreeSet<SemanticObject>(new Comparator()
        {

            public int compare(Object arg0, Object arg1)
            {
                SemanticObject obj0 = (SemanticObject) arg0;
                SemanticObject obj1 = (SemanticObject) arg1;
                return obj1.getProperty(org.semanticwb.model.comm.MicroSite.swb_created
                        ).compareTo(obj0.getProperty(org.semanticwb.model.comm.MicroSite.swb_created));
            }
        });
        Iterator<SemanticObject> lista = site.getSemanticObject().getModel().listInstancesOfClass(MicroSite.sclass);
        while (lista.hasNext()){
            setVals.add(lista.next());
        }
        Iterator<SemanticObject> communities = setVals.iterator();
            //Iterator communities = SWBComparator.sortByCreated(MicroSite.ClassMgr.listMicroSites(site), false);
            if (!communities.hasNext())
            {
    %>
    <li>&nbsp;</li>
    <%            }
            int i = 0;
            //communities = SWBComparator.sortByCreated(MicroSite.ClassMgr.listMicroSites(site), false);
            while (communities.hasNext())
            {

                MicroSite comm = (MicroSite) communities.next().createGenericInstance();
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