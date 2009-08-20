<%@page import="org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.User,org.semanticwb.model.WebPage,java.util.*"%>
<%

    User user=(User)request.getAttribute("user");
    WebPage webpage=(WebPage)request.getAttribute("webpage");
    if(webpage!=null && user!=null && user.isSigned())
    {
        String path=webpage.getUrl()+"/../perfil";
        %>
        <li><%=user.getFullName()%></li>
        <li><a href="<%=path%>">Ver perfil</a></li>
        <%
    }
    /*if(user.isSigned() && webpage.getWebPage().getWebSite().getWebPage("perfil")!=null)
    {
        String path=webpage.getWebPage().getWebSite().getWebPage("perfil").getUrl();
        %>
        <li><%=user.getFullName()%></li>
        <li><a href="<%=path%>">Ver perfil</a></li>
        <%
    }*/
%>
