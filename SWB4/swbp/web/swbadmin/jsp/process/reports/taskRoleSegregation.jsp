<%-- 
    Document   : taskRoleSegregation
    Created on : 14/11/2011, 12:57:27 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
String dp = (String)request.getAttribute("downPath");

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