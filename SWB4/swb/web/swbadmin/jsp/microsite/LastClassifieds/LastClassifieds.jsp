<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<h2 class="tituloEspacio">Últimos clasificados</h2>
<div class="cajas">
    <ul>
        <%
            String defaultFormat = "dd/MM/yy HH:mm";
            SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(defaultFormat);
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebPage clasificados = webpage.getWebSite().getWebPage("Clasificados");
            GenericIterator<Clasified> itClass=new GenericIterator(webpage.getWebSite().getSemanticObject().getModel().listInstancesOfClass(Clasified.sclass,true));
            Iterator objects = SWBComparator.sortByCreated(itClass, true);
            int count = 0;
            while (objects.hasNext())
            {
                Clasified obj = (Clasified) objects.next();
                String created = "Sin fecha";
                if (obj.getCreated() != null)
                {
                    created = iso8601dateFormat.format(obj.getCreated());
                }
        %>
        <li><%=obj.getCreator().getFullName()%>, <%=obj.getTitle()%>
            (<%=created%>)</li>
            <%
                count++;
                if (count == 10)
                {
                    break;
                }
            }

            %>
    </ul>
    <p class="vermas"><a href="<%=clasificados.getUrl()%>" >Ver m&aacute;s</a></p>
</div>