<%-- 
    Document   : ReportResource
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
    SWBResourceURL urlViewReport = paramRequest.getRenderUrl();

    SWBResourceURL urlAction = paramRequest.getActionUrl();
    Integer pageElements = Integer.parseInt(request.getAttribute("pageElements").toString());
    Integer modeExport = Integer.parseInt(request.getAttribute("modeExport").toString());
    boolean isSaveOnSystem = Boolean.parseBoolean(request.getAttribute("isSaveOnSystem").toString());
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
        <a href="<%=url.setMode("add")%>" title="Agregar reporte"><img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/reports/images/add.png"%>"></a>
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
                //rp.remove();
        %>
        <tr>
            <td class="tban-tarea"><a
                    <%if (modeExport == 2) {%>
                    href="<%=urlViewReport.setMode("viewReport").setParameter("idReport", rp.getId())%>" 
                    title="Visualizar reporte"
                    <%} else {%> href="#" onclick="showDialog('<%=rp.getId()%>');
                            return false;" title="Generar reporte"
                    <%}%>
                    title="<%=rp.getTitle()%>"><%=rp.getTitle()%></a>
            </td>
            <td><%
                Iterator<FileReport> itfr = FileReport.ClassMgr.listFileReportByFileNameReport(rp);
                while (itfr.hasNext()) {
                    FileReport fr = itfr.next();
                    //fr.remove();
                    String path = "/work" + rp.getWorkPath() + "/" + rp.getTitle() + "/" + fr.getTitle();
                %>
                <a <%if (fr.isActive()) {%> href="<%=path%>"<%}%> title="<%=fr.getTitle()%>"><%=fr.getTitle()%></a>
                <%if (fr.isActive()) {%><a onclick="if (!confirm('Eliminar archivo?'))
                                return false;" href="<%=urlAction.setAction("removeFileReport").setParameter("idFileReport", fr.getId())%>" title="Eliminar"><img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/reports/images/delete.png"%>"></a><%}%>
                <br/>
                <%}
                %></td>
            <td style="text-align: center;" class="tban-tarea">
                <a href="<%=url.setMode(SWBResourceURL.Mode_EDIT).setParameter("idReport", rp.getId())%>" title="Editar"><img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/reports/images/edit.png"%>"></a>  
                --
                <%if (modeExport == 2) {%>
                <a href="<%=urlViewReport.setMode("viewReport").setParameter("idReport", rp.getId())%>" 
                   title="Visualizar reporte">
                    <img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/reports/images/detail.png"%>"></a>
                    <%} else {%>
                <a href="#" onclick="showDialog('<%=rp.getId()%>');
                            return false;" title="Generar reporte"><img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/reports/images/generate.png"%>"></a>
                    <%}%>
                -- 
                <a onclick="if (!confirm('Eliminar reporte?'))
                                return false;" href="<%=urlAction.setAction(SWBResourceURL.Action_REMOVE).setParameter("idReport", rp.getId())%>" title="Eliminar"><img src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/reports/images/delete.png"%>"></a></td>
        </tr>
        <%}%>
    </table>
</div>
<div dojoType="dijit.Dialog" style="display:none;" id="configDialog" title="Generar reporte">
    <div dojoType="dijit.layout.ContentPane" id="configDialogImp" executeScripts="true">
        <form method="post" <%if (!isSaveOnSystem) {%>action="<%=urlReport%>"<%}%>>
            <input type="hidden" id="idReport" name="idReport"/>
            Nombre de archivo:
            <input type="text" id="reportName" name="reportName" dojoType="dijit.form.ValidationTextBox" 
                   required="true" trim="true" promptMessage="Nombre de archivo"
                   regExp="[a-zA-Z0-9_\ %-]+[a-zA-Z0-9_\ %-]" />
            <br/>
            Formato:
            <select id="extension" name="extension" style="width: 200px;">
                <option value="xls" selected="true">Excel</option>
                <option value="pdf">PDF</option>
            </select>
            <br/>
            <input type="submit" dojoType="dijit.form.Button" label="Generar" <%if (isSaveOnSystem) {%>onclick="submitUrl('<%=urlReport%>', this);
                            return false;"<%} else {%> onclick="close();"<%}%> title="Generar"/>
        </form>
    </div>
</div>
<script type="text/javascript">
                        var count = 0;
                        function showDialog(idReport) {
                            document.getElementById('idReport').setAttribute("value", "" + idReport);
                            dijit.byId('configDialog').show();
                        }
                        function close() {
                            dijit.byId('configDialog').hide();
                        }
                        function submitUrl(url, reference) {
                            var extension = document.getElementById("extension");
                            dijit.byId('configDialog').hide();
                            url = url + '?idReport=' + document.getElementById('idReport').value + '&extension=' + extension.options[extension.selectedIndex].value + '&reportName=' + document.getElementById('reportName').value;
                            dojo.xhrGet({
                                url: url,
                                load: function(response, ioArgs)
                                {
                                    count++;
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
</script>
<script type="text/javascript">
    dojo.require("dijit.Dialog");
    dojo.require("dojo.parser");
    dojo.require("dijit._Calendar");
    dojo.require("dijit.ProgressBar");
    dojo.require("dijit.Editor");
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.NumberSpinner");
    dojo.require("dijit.form.Slider");
    dojo.require("dojox.form.BusyButton");
    dojo.require("dojox.form.TimeSpinner");
</script>