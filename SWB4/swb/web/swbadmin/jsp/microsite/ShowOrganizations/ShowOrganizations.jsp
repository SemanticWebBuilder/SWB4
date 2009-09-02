<%@page import="java.io.*,java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<div class="tabsTemas">
<ul>
<%
    User user=(User)request.getAttribute("user");
    String lang="es";
    if(user.getLanguage()!=null)
    {
        lang=user.getLanguage();
    }
    WebPage webpage = (WebPage) request.getAttribute("webpage");
    WebPage organizaciones = webpage.getWebSite().getWebPage("Organizaciones");
    Iterator<WebPage> pages=organizaciones.listVisibleChilds(lang); // ordenados por nombre
    int count=0;
    while(pages.hasNext())
    {
        WebPage child=pages.next();
        String path="/models/"+ webpage.getWebSiteId() +"/css/iconos/"+child.getId()+".png";
        path="/work"+path;
        /*try
        {
            InputStream in=SWBPlatform.getFileFromWorkPath(path);
            if(in==null)
            {
                path="/work/models/"+ webpage.getWebSiteId() +"/css/iconos/default.png";;
            }
            else
            {
                path="/work"+path;

            }

        }
        catch(Exception e)
        {
            path="/work/models/"+ webpage.getWebSiteId() +"/css/iconos/default.png";;
        }*/
        %>
        <li><img src="<%=path%>" width="60" height="60" alt="<%=child.getTitle()%>"><a href="<%=child.getUrl()%>"><%=child.getTitle()%></a></li>
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
                <p>Además...</p>
                <%
            }
        %>
        <a href="<%=organizaciones.getUrl()%>">ver listado completo</a></div>
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

