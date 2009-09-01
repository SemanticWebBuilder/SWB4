<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<div class="clasificadosIzquierda">

    <%
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebPage clasificados = webpage.getWebSite().getWebPage("Clasificados");
            Iterator<WebPage> nivel1 = clasificados.listVisibleChilds("es");
            int elements = 0;
            while (nivel1.hasNext())
            {
                nivel1.next();
                elements++;
            }
            int rows = elements / 2;
            nivel1 = clasificados.listVisibleChilds("es");
            int irow = 0;
            while (nivel1.hasNext())
            {
                WebPage child = nivel1.next();
    %>
    <ul>
        <li>
            <h3><%=child.getTitle()%></h3>
        </li>

        <%
                Iterator<WebPage> subpages = child.listVisibleChilds("es");
                int i=0;
                while (subpages.hasNext())
                {
                    WebPage sbpage = subpages.next();
        %>
        <li><a href="#"><%=sbpage.getTitle()%></a></li>
        <%
                i++;
                if(i>=5)
                {
                    break;
                }

                }
        %>
    </ul>
    <%

                irow++;
                if (rows == rows)
                {
    %>
</div>
<div class="clasificadosDerecha">
    <%                }
            }

    %>
</div>
<div class="clear">&nbsp;</div>
