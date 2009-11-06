<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<%
            WebPage parent = (WebPage) request.getAttribute("webpage");
            WebPage currentpage = (WebPage) request.getAttribute("webpage");
            String classActiveTwitter = "", classActiveBlog = "", classActiveWiki = "", classActiveVideo = "", classActivePrincipal = "", classActiveChat = "", classActiveEvents = "", classActiveFotos = "", classActiveMiembros = "", classActiveNoticias = "";
            String urlTwitter = "", urlBlog = "", urlWiki = "", urlVideo = "", urlPrincipal = "", urlChat = "", urlEvents = "", urlFotos = "", urlMiembros = "", urlNoticias = "";
            String active = "class=\"active\"";
            if (parent instanceof MicroSite)
            {
                classActivePrincipal = active;

            }
            else
            {
                parent = parent.getParent();
                if (parent != null && parent instanceof MicroSite)
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
                        classActiveTwitter = active;
                    }
                }
            }
            if (parent != null)
            {
                urlPrincipal = parent.getUrl();
                urlBlog=parent.getWebSite().getWebPage(parent.getId()+"_Blog").getUrl();
                urlChat=parent.getWebSite().getWebPage(parent.getId()+"_Chat").getUrl();
                urlEvents=parent.getWebSite().getWebPage(parent.getId()+"_Events").getUrl();
                urlFotos=parent.getWebSite().getWebPage(parent.getId()+"_Photos").getUrl();
                urlMiembros=parent.getWebSite().getWebPage(parent.getId()+"_Members").getUrl();
                urlNoticias=parent.getWebSite().getWebPage(parent.getId()+"_News").getUrl();
                urlVideo=parent.getWebSite().getWebPage(parent.getId()+"_Videos").getUrl();
                urlTwitter=parent.getWebSite().getWebPage(parent.getId()+"_Twitter").getUrl();
            }

%>
<ul id="menuInterna">
    <li><a <%=classActivePrincipal%> href="<%=urlPrincipal%>">Principal</a></li>
    <li><a <%=classActiveBlog%> href="<%=urlBlog%>">Blog</a></li>
    <li><a <%=classActiveChat%> href="<%=urlChat%>">Chat</a></li>
    <li><a <%=classActiveEvents%> href="<%=urlEvents%>">Eventos</a></li>
    <li><a <%=classActiveFotos%> href="<%=urlFotos%>">Fotos</a></li>
    <li><a <%=classActiveMiembros%> href="<%=urlMiembros%>">Miembros</a></li>
    <li><a <%=classActiveNoticias%> href="<%=urlNoticias%>">Noticias</a></li>
    <li><a <%=classActiveTwitter%> href="<%=urlTwitter%>">Twitter</a></li>
    <li><a <%=classActiveVideo%> href="<%=urlVideo%>">Videos</a></li>
    <%-- <li><a <%=classActiveWiki%> href="#">Wiki</a></li> --%>
</ul>