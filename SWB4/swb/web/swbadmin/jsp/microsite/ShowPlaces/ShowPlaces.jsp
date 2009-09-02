<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<div class="tabsTemas">
<ul>
<%
    WebPage webpage = (WebPage) request.getAttribute("webpage");
    WebPage sitios_de_Interes = webpage.getWebSite().getWebPage("Sitios_de_Interes");
    Iterator<WebPage> pages=sitios_de_Interes.listVisibleChilds("es"); // ordenados por nombre
    int count=0;
    while(pages.hasNext())
    {
        WebPage child=pages.next();
        %>
        <li><img src="/work/models/Ciudad_Digital/css/iconos/hotel.png" alt="<%=child.getTitle()%>"><a href="<%=child.getUrl()%>"><%=child.getTitle()%></a></li>
        <%
        count++;
        if(count==8)
        {
            break;
        }

    }

%>
    </ul>
    <div class="clear">&nbsp;</div>
</div>
<div class="ademas">
    <div class="ademasHeader">
        <%
            if(pages.hasNext())
            {
                %>
                <p>Adem�s...</p>
                <%
            }
        %>
        
        <a href="<%=sitios_de_Interes.getUrl()%>">ver listado completo</a></div>
        <ul class="ademasContent">
        <%
            count=0;
            while(pages.hasNext())
            {
                WebPage child=pages.next();
                %>
                    <li><a href="<%=child.getUrl()%>"><%=child.getTitle()%></a></li>
                <%
                count++;
                if(count==15)
                {
                    break;
                }
            }
        %>        
    </ul>
    <div class="clear">&nbsp;</div>
</div>
