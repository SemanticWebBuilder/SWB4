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
ArrayList<UserRolesSegregationBean> beans = null;
SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
String dp = (String)request.getAttribute("downPath");
String sortType = request.getParameter("sort");
String pNum = request.getParameter("page");
String itemsPerPage = (String) String.valueOf(request.getAttribute("itemsPerPage"));
int maxPages = (Integer) request.getAttribute("maxPages");
int count = 0;

int pageNum = 1;

if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
}
if (sortType != null && !sortType.trim().equals("")) {
    sortType = sortType.trim();
} else {
    sortType = "user";
}
if (itemsPerPage == null || itemsPerPage.trim().equals("")) {
    itemsPerPage = "5";
}
SWBResourceURL optsUrl = paramRequest.getRenderUrl().setMode("URSReport").setParameter("page", pNum);
%>
<script type="text/javascript">
    function loadPageUrl(url, paramName, paramValue) {
        var dest = url;
        if (paramName != null && paramValue != null) {
            dest+="&"+paramName+"="+paramValue;
        }
        window.location = dest;
    }
</script>

<h2>Reporte de Segregación de Roles por Usuario</h2>
<div class="bandeja-combo">
    <ul>
        <li>
            <input type="button" value="Descargar reporte" onclick="window.location='<%=dp%>'"/>
        </li>
        <li>
            <input type="button" value="Regresar" onclick="window.location='<%=viewUrl%>'"/>
        </li>
    </ul>
</div>
<%
if (beans != null) {
%>
<table class="tabla-bandeja">
    <thead>
        <tr>
            <th>Usuario</th>
            <th>Proceso</th>
            <th>Actividad</th>
            <th>Rol</th>
        </tr>
    </thead>
    <tbody>
        <%
        Iterator<UserRolesSegregationBean> itbeans = beans.iterator();
        while(itbeans.hasNext()) {
            UserRolesSegregationBean itbean = itbeans.next();
            count++;
            %>
            <tr>
                <td><%=itbean.getUserName()%></td>
                <td><%=itbean.getProcessName()%></td>
                <td><%=itbean.getTaskName()%></td>
                <td><%=itbean.getRoleName()%></td>
            </tr>
            <%
        }
        %>
    </tbody>
</table>
<div class="paginado">
    P&aacute;gina:
    <%
    for (int i = 1; i <= maxPages; i++) {
        if (pageNum == i) {
            %><strong><%=i%></strong> <%
        } else {
            SWBResourceURL pUrl = paramRequest.getRenderUrl();
            pUrl.setParameter("sort", sortType);
            pUrl.setParameter("page", String.valueOf(i));
            %><a href="<%=pUrl%>"><%=i%></a> <%
        }
    }
    %>
</div>
<%
}
%>