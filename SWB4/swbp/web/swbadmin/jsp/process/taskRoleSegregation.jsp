<%-- 
    Document   : taskRoleSegregation
    Created on : 14/11/2011, 12:57:27 PM
    Author     : hasdai
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL" %>
<%@page import="org.semanticwb.portal.api.SWBParamRequest" %>
<h2>Reporte de Segregación de Roles por Actividad</h2>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
String dp = (String)request.getAttribute("downPath");
String pNum = request.getParameter("page");
String itemsPerPage = (String) String.valueOf(request.getAttribute("itemsPerPage"));
int maxPages = (Integer) request.getAttribute("maxPages");
int count = 0;

int pageNum = 1;

if (pNum != null && !pNum.trim().equals("")) {
    pageNum = Integer.valueOf(request.getParameter("page"));
}
if (itemsPerPage == null || itemsPerPage.trim().equals("")) {
    itemsPerPage = "5";
}
SWBResourceURL optsUrl = paramRequest.getRenderUrl().setMode("URSReport").setParameter("page", pNum);
%>
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