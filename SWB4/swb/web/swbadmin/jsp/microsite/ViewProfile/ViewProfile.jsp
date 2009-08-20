<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    if(user.isSigned() && paramRequest.getWebPage().getWebSite().getWebPage("perfil")!=null)
    {
        String path=paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
        %>
        <li><%=user.getFullName()%></li>
        <li><a href="<%=path%>">Ver perfil</a></li>

        <%
    }
%>
