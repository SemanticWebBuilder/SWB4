<%-- 
    Document   : addReport
    Created on : 11/03/2013, 05:23:28 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.process.resources.reports.FileReport"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL url = paramRequest.getRenderUrl();
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    Integer pageElements = Integer.parseInt(request.getAttribute("pageElements").toString());
    Iterator<Report> report = Report.ClassMgr.listReports(paramRequest.getWebPage().getWebSite());
    SWBResourceURL urlReport = paramRequest.getRenderUrl().setMode("generate").setCallMethod(SWBParamRequest.Call_DIRECT);
%>
<div class="bandeja-combo" dojoType="dijit.layout.ContentPane" region="center" id="workspace">

    <div id="out" style="display: none; width: 100%; alignment-adjust: central;">
        <%url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_VIEW);%>
        <a id="a" href="<%=url%>" title="Reportes nuevos">Reportes nuevos</a>
    </div>
    <div id="out2" style="display: none;background: #008000">
        <h2 style="color: red;">No creó reporte</h2>
    </div>
    <div style="text-align: right;">
        <a href="<%=url.setMode("add")%>" title="Agregar reporte"><img src="/swbadmin/jsp/process/reports/add.png"></a>
    </div>
    <table class="tabla-bandeja">
        <thead>
        <th>
            Reporte
        </th>
        <th>
            Reportes generados
        </th>
        <th>
            Acciones
        </th>
        </thead>
        <%
            while (report.hasNext()) {
                Report rp = (Report) report.next();
        %>
        <tr>
            <td class="tban-tarea"><a href="#" onclick="showDialog('<%=rp.getId()%>');
                    return false;" title="<%=rp.getTitle()%>"><%=rp.getTitle()%></a></td>
            <td><%
                Iterator<FileReport> itfr = FileReport.ClassMgr.listFileReportByFileNameReport(rp);
                while (itfr.hasNext()) {
                    FileReport fr = itfr.next();
                    //fr.remove();
                    String path = "/work" + rp.getWorkPath() + "/" + rp.getTitle() + "/" + fr.getTitle();
                %>
                <a <%if (fr.isActive()) {%> href="<%=path%>"<%}%> title="<%=fr.getTitle()%>"><%=fr.getTitle()%></a>
                <%if (fr.isActive()) {%><a onclick="if (!confirm('Eliminar archivo?'))
                        return true;" href="<%=urlAction.setAction("removeFileReport").setParameter("idFileReport", fr.getId())%>" title="Eliminar"><img src="/swbadmin/jsp/process/reports/remove.png"></a><%}%>

                <br/>
                <%}
                %></td>
            <td style="text-align: center;" class="tban-tarea">
                <a href="<%=url.setMode(SWBResourceURL.Mode_EDIT).setParameter("idReport", rp.getId())%>" title="Editar"><img src="/swbadmin/jsp/process/reports/edit.png"></a>  
                --
                <a href="#" onclick="showDialog('<%=rp.getId()%>');
                    return false;" title="Generar reporte"><img src="/swbadmin/jsp/process/reports/generate.png"></a>
                -- 
                <a onclick="if (!confirm('Eliminar reporte?'))
                        return false;" href="<%=urlAction.setAction(SWBResourceURL.Action_REMOVE).setParameter("idReport", rp.getId())%>" title="Eliminar"><img src="/swbadmin/jsp/process/reports/delete.png"></a></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<div dojoType="dijit.Dialog" style="display:none;" id="configDialog" title="Generar reporte">
    <div dojoType="dojox.layout.ContentPane" id="configDialogImp" executeScripts="true">
        <form method="post">
            <h3>Selecciona el formato</h3>
            <input type="hidden" id="idReport"/>
            <select id="extension" style="width: 200px;">
                <option value="xls" selected="true">Excel</option>
                <option value="pdf">PDF</option>
            </select>
            <input onclick="submitUrl('<%=urlReport%>', this);
                    return false;" style="cursor: pointer;" type="submit" value="Generar" title="Generar reporte"/>
        </form>
    </div>
</div>
<script type="text/javascript">
    var count = 0;
                dojo.require("dijit.Dialog");
                function showDialog(idReport) {
                    document.getElementById('idReport').setAttribute("value", "" + idReport);
                    dijit.byId('configDialog').show();
                }
                function submitUrl(url, reference) {
                    var panel = getContentPanel(reference);
                    var extension = document.getElementById("extension");
                    dijit.byId('configDialog').hide();
                    url = url + '?idReport=' + document.getElementById('idReport').value + '&extension=' + extension.options[extension.selectedIndex].value;
                    dojo.xhrGet({
                        url: url,
                        load: function(response, ioArgs)
                        {
                            count ++;
                            document.getElementById('out').style.display = 'block';
                            //alert(count);
                            document.getElementById('a').innerHTML = "Reportes nuevos: " + count; 
                            //setInterval(function(){myTimer()},2000);
                            return response;
                        },
                        error: function(response, ioArgs) {
                            setInterval(function() {
                                myTimer2()
                            }, 2000);
                            return response;
                        },
                        handleAs: "text"
                    });
                }

                function myTimer() {
                    document.getElementById('out').style.display = 'none';
                }
                function myTimer2() {
                    document.getElementById('out2').style.display = 'none';
                }
</script>