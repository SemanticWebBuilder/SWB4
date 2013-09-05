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
<h2>Generador de Reportes</h2>
<div class="bandeja-combo">
    <ul>
        <li>
            Reporte:
            <select id="reportId">
                <option value="<%=URSUrl%>">Segregaci&oacute;n de Roles por Usuario</option>
                <option value="<%=TRSUrl%>">Segregaci&oacute;n de Roles por Actividad</option>
            </select>
        </li>
        <li>
            <input type="button" value="Generar" onclick="window.location=document.getElementById('reportId').value"/>
        </li>
    </ul>
</div>