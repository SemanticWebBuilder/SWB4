<%-- 
    Document   : addReport
    Created on : 11/03/2013, 05:23:28 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBFormMgr tipo = new SWBFormMgr(Report.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), SWBFormMgr.MODE_CREATE);
    String lang = "";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    tipo.setType(SWBFormMgr.TYPE_DOJO);
    tipo.setLang(lang);
%>
<div class="bandeja-combo">
    <h2>Datos generales</h2>
    <form dojoType="dijit.form.Form" action="<%=paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)%>" method="post">
        <%out.println(tipo.getFormHiddens());%>
        <table class="tabla-bandeja">
            <tr><td align="right" width="200px">
                    <%out.println(tipo.renderLabel(request, Report.swb_title, SWBFormMgr.MODE_CREATE));%></td>
                <td><%out.println(tipo.renderElement(request, Report.swb_title, SWBFormMgr.MODE_CREATE));%></td></tr>
            <tr><td align="right" width="200px">
                    <%out.println(tipo.renderLabel(request, Report.rep_processName, SWBFormMgr.MODE_CREATE));%></td>
                <td><%out.println(tipo.renderElement(request, Report.rep_processName, SWBFormMgr.MODE_CREATE));%></td></tr>
            <tr><td align="right" width="200px">
                    <%out.println(tipo.renderLabel(request, Report.rep_pagingSize, SWBFormMgr.MODE_CREATE));%></td>
                <td><%out.println(tipo.renderElement(request, Report.rep_pagingSize, SWBFormMgr.MODE_CREATE));%></td></tr>
            <tr><td align="right" width="200px"></td>
                <td>
                    <input type="submit" value="Guardar" title="Guardar"/>
                    <input type="button" value="Regresar" title="Regresar" onclick="javascript:document.back.submit()"/>
                </td></tr>
        </table>
    </form>
</div>
<form method="post" action="<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>" name="back"></form>