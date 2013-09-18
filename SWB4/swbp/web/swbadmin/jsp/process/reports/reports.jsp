<%-- 
    Document   : reports
    Created on : 11/11/2011, 01:42:52 PM
    Author     : hasdai
--%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
SWBResourceURL URSUrl = paramRequest.getRenderUrl().setMode("URSReport");
SWBResourceURL TRSUrl = paramRequest.getRenderUrl().setMode("TRSReport");

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