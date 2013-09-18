<%-- 
    Document   : taskRoleSegregation
    Created on : 14/11/2011, 12:57:27 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();
String dp = (String)request.getAttribute("downPath");


if (!user.isSigned()) {
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
        %>
        <div class="alert alert-block alert-danger fade in">
            <h4><i class="icon-ban-circle"></i> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
            <p><%=paramRequest.getLocaleString("msgNoAccess")%></p>
            <p>
                <a class="btn btn-default" href="/login/<%=site.getId()%>/<%=paramRequest.getWebPage().getId()%>"><%=paramRequest.getLocaleString("btnLogin")%></a>
            </p>
        </div>
        <%
    }
} else {
    %>
    <h2><%=paramRequest.getLocaleString("titleActRoles")%></h2>
    <ul class="list-unstyled list-inline">
        <li>
            <button class="btn btn-default" onclick="window.location='<%=viewUrl%>'"><i class="icon-reply"></i> <%=paramRequest.getLocaleString("actBack")%></button>
        </li>
        <li>
            <button class="btn btn-success" onclick="window.location='<%=dp%>'"><i class="icon-download-alt"></i> <%=paramRequest.getLocaleString("actDownload")%></button>
        </li>
    </ul>
    <%
}
%>