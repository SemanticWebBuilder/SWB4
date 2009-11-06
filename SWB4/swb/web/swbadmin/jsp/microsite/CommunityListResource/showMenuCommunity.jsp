<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<%
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebPage currentpage = (WebPage) request.getAttribute("webpage");
            String classActiveTwitter = "",classActiveBlog = "", classActiveWiki = "", classActiveVideo = "", classActivePrincipal = "", classActiveChat = "", classActiveEvents = "", classActiveFotos = "", classActiveMiembros = "", classActiveNoticias = "";
            String active = "class=\"active\"";
            if (webpage instanceof MicroSite)
            {
                classActivePrincipal = active;
            }
            else
            {
                webpage = webpage.getParent();
                if (webpage != null && webpage instanceof MicroSite)
                {
                    if (currentpage.getId().endsWith("_Blog"))
                    {
                        classActiveBlog = active;
                    }
                    else if (currentpage.getId().endsWith("_Chat"))
                    {
                        classActiveChat = active;
                    }
                    else if (currentpage.getId().endsWith("_Events"))
                    {
                        classActiveEvents = active;
                    }
                    else if (currentpage.getId().endsWith("_Photos"))
                    {
                        classActiveFotos = active;
                    }
                    else if (currentpage.getId().endsWith("_Members"))
                    {
                        classActiveMiembros = active;
                    }
                    else if (currentpage.getId().endsWith("_News"))
                    {
                        classActiveNoticias = active;
                    }
                    else if (currentpage.getId().endsWith("_Videos"))
                    {
                        classActiveVideo = active;
                    }
                    else if (currentpage.getId().endsWith("_Twitter"))
                    {
                        classActiveVideo = active;
                    }

                }
            }
%>
<ul id="menuInterna">
    <li><a <%=classActivePrincipal%> href="#">Principal</a></li>
    <li><a <%=classActiveBlog%> href="#">Blog</a></li>
    <li><a <%=classActiveChat%> href="#">Chat</a></li>
    <li><a <%=classActiveEvents%> href="#">Eventos</a></li>
    <li><a <%=classActiveFotos%> href="#">Fotos</a></li>
    <li><a <%=classActiveMiembros%> href="#">Miembros</a></li>
    <li><a <%=classActiveNoticias%> href="#">Noticias</a></li>
    <li><a <%=classActiveTwitter%> href="#">Twitter</a></li>
    <li><a <%=classActiveVideo%> href="#">Videos</a></li>
    <%-- <li><a <%=classActiveWiki%> href="#">Wiki</a></li> --%>
</ul>