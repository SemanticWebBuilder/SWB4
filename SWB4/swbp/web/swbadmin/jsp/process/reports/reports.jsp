<%-- 
    Document   : reports
    Created on : 11/11/2011, 01:42:52 PM
    Author     : hasdai
--%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
SWBResourceURL URSUrl = paramRequest.getRenderUrl().setMode("URSReport");
SWBResourceURL TRSUrl = paramRequest.getRenderUrl().setMode("TRSReport");
User user = paramRequest.getUser();
WebSite site = paramRequest.getWebPage().getWebSite();

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
    <h2><%=paramRequest.getLocaleString("pageTitle")%></h2>
    <ul class="list-unstyled list-inline">
        <li>
            <select id="reportId" class="form-control form-inline">
                <option value="<%=URSUrl%>"><%=paramRequest.getLocaleString("optUserRoles")%></option>
                <option value="<%=TRSUrl%>"><%=paramRequest.getLocaleString("optActRoles")%></option>
            </select>
        </li>
        <li>
            <button class="btn btn-success" onclick="window.location=document.getElementById('reportId').value;"><%=paramRequest.getLocaleString("actGenerate")%></button>
        </li>
    </ul>
    <%
}
%>