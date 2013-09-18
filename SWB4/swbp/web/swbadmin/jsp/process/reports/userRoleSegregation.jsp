<%-- 
    Document   : userRoleSegregation
    Created on : 11/11/2011, 03:22:21 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<%@page import="org.semanticwb.process.resources.reports.UserRolesSegregationBean" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
//ArrayList<UserRolesSegregationBean> beans = (ArrayList<UserRolesSegregationBean>) request.getAttribute("beans");
SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
String dp = (String)request.getAttribute("downPath");

%>
<script type="text/javascript">
    function loadPageUrl(url, paramName, paramValue) {
        var dest = url;
        if (paramName !== null && paramValue !== null) {
            dest+="&"+paramName+"="+paramValue;
        }
        window.location = dest;
    }
</script>

<h2><%=paramRequest.getLocaleString("titleUserRoles")%></h2>
<ul class="list-unstyled list-inline">
    <li>
        <button class="btn btn-default" onclick="window.location='<%=viewUrl%>';"><i class="icon-reply"></i> <%=paramRequest.getLocaleString("actBack")%></button>
    </li>
    <li>
        <button class="btn btn-success" onclick="window.location='<%=dp%>';"><i class="icon-download-alt"></i> <%=paramRequest.getLocaleString("actDownload")%></button>
    </li>
</ul>