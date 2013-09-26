<%-- 
    Document   : ReportResource
    Created on : 11/03/2013, 05:23:28 PM
    Author     : carlos.alvarez
--%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.portal.SWBServiceMgr"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.process.resources.reports.FileReport"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL url = paramRequest.getRenderUrl();
    SWBResourceURL urlViewReport = paramRequest.getRenderUrl();
    boolean isSaveOnSystem = Boolean.parseBoolean(request.getAttribute("isSaveOnSystem").toString());
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    Integer pageElements = Integer.parseInt(request.getAttribute("pageElements").toString());
    Integer modeExport = Integer.parseInt(request.getAttribute("modeExport").toString());
    Iterator<Report> report = Report.ClassMgr.listReports(paramRequest.getWebPage().getWebSite());
    SWBResourceURL urlDialog = paramRequest.getRenderUrl().setMode("dialog").setCallMethod(SWBResourceURL.Call_DIRECT);
    WebPage wpage = paramRequest.getWebPage();
    SWBResourceURL URSUrl = paramRequest.getRenderUrl().setMode("URSReport").setCallMethod(SWBResourceURL.Call_DIRECT);
    SWBResourceURL TRSUrl = paramRequest.getRenderUrl().setMode("TRSReport").setCallMethod(SWBResourceURL.Call_DIRECT);
%>
<div id="out" class="alert alert-dismissable alert-warning" style="display: none; width: 100%; alignment-adjust: central;">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    <a id="a" href="<%=url%>"
       data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%>">
        <%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("saved")%> <span id="count" class="badge"></span></a>
</div>
<ul class="list-unstyled list-inline"><li>
        <a class="btn btn-default btn-sm" href="<%=url.setMode("add")%>" data-placement="bottom" data-toggle="tooltip" data-original-title="<%out.println(paramRequest.getLocaleString("add") + " " + paramRequest.getLocaleString("report"));%>">
            <i class="icon-plus"></i> <%=paramRequest.getLocaleString("add")%>
        </a>
    </li>
    <li>
        <div class="btn-group">
            <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">
                <%=paramRequest.getLocaleString("reports") + " " + paramRequest.getLocaleString("of") + " " + paramRequest.getLocaleString("roles")%>
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <!-- dropdown menu links -->
                <li><a  href="#" onclick="window.location = '<%=URSUrl%>'"><i class="icon-download-alt"></i> <%=paramRequest.getLocaleString("userRoles")%></a></li>
                <li><a href="#" onclick="window.location = '<%=TRSUrl%>'"><i class="icon-download-alt"></i> <%=paramRequest.getLocaleString("activityRoles")%></a></li>
            </ul>
        </div>
    </li>
</ul>
<%
    if(report.hasNext()){
%>
<div class="table-responsive">
    <table class="table table-hover swbp-table">
        <thead>
        <th><%=paramRequest.getLocaleString("report")%></th>
        <th><%=paramRequest.getLocaleString("files")%></th>
        <th><%=paramRequest.getLocaleString("actions")%></th></thead>
            <%
                while (report.hasNext()) {
                    Report rp = (Report) report.next();
                    urlDialog = paramRequest.getRenderUrl().setMode("dialog").setCallMethod(SWBResourceURL.Call_DIRECT);
                    urlDialog.setParameter("idReport", rp.getId());
                    urlDialog.setParameter("action", "export");
            %>
        <tr>
            <td><a 
                    <%if (modeExport == 2) {%>
                    href="<%=urlViewReport.setMode("viewReport").setParameter("idReport", rp.getId())%>" 
                    data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("view") + " " + paramRequest.getLocaleString("report") + " " + rp.getTitle()%>"
                    <%} else {%> href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog"
                    data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("generate") + " " + paramRequest.getLocaleString("report") + " " + rp.getTitle()%>"
                    <%}%>><%=rp.getTitle()%></a>
            </td>
            <td><%
                Iterator<FileReport> itfr = FileReport.ClassMgr.listFileReportByFileNameReport(rp);
                while (itfr.hasNext()) {
                    FileReport fr = itfr.next();
                    String path = SWBPlatform.getContextPath() + "/work" + rp.getWorkPath() + "/" + rp.getTitle() + "/" + fr.getTitle();
                %>
                <%=fr.getTitle()%>
                <a class="btn btn-default" <%if (fr.isActive()) {%> href="<%=path%>"<%}%> 
                   data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("download") + " " + paramRequest.getLocaleString("file") + " " + fr.getTitle()%>"
                   > <li class="icon-download-alt"></li></a>
                        <%if (fr.isActive()) {%>
                <a  class="btn btn-default" 
                    onclick="if (!confirm('<%=paramRequest.getLocaleString("remove") + " " + paramRequest.getLocaleString("file")%>?'))
                                return false;" href="<%=urlAction.setAction("removeFileReport").setParameter("idFileReport", fr.getId())%>" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("remove") + " " + paramRequest.getLocaleString("file") + " " + fr.getTitle()%>">
                    <li class="icon-trash"></li>            
                </a><%}%>
                <br/>
                <%}
                %></td>
            <td style="text-align: center; vertical-align: middle;" class="tban-tarea">
                <a class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("configureReport") + " " + rp.getTitle()%>" href="<%=url.setMode(SWBResourceURL.Mode_EDIT).setParameter("idReport", rp.getId())%>">
                    <li class="icon-wrench icon-large"></li>
                </a>  
                <%if (modeExport == 2) {
                        urlViewReport.setParameter("modeExport", modeExport.toString());%>
                <a class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("view") + " " + paramRequest.getLocaleString("report") + " " + rp.getTitle()%>"
                   href="<%=urlViewReport.setMode("viewReport").setParameter("idReport", rp.getId())%>">
                    <li class="icon-eye-open icon-large"></li>
                </a>
                <%} else {%>
                <a href="<%=urlDialog%>" data-toggle="modal" data-target="#modalDialog"
                   class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("save") + " " + paramRequest.getLocaleString("report") + " " + rp.getTitle()%>">
                    <%if (isSaveOnSystem) {%>
                    <li class="icon-save icon-large"></li><%} else {%>
                    <li class="icon-file icon-large"></li>
                        <%}%>
                </a>
                <%}%>
                <a class="btn btn-default btn-sm" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("remove") + " " + paramRequest.getLocaleString("report") + " " + rp.getTitle()%>"
                   onclick="if (!confirm('<%=paramRequest.getLocaleString("remove") + " " + paramRequest.getLocaleString("report")%>?'))
                                return false;" href="<%=urlAction.setAction(SWBResourceURL.Action_REMOVE).setParameter("idReport", rp.getId())%>">
                    <li class="icon-trash icon-large"></li>
                </a>
            </td>
        </tr>
        <%}%>
    </table>
</div>
<%} else {%>
<div class="alert alert-block alert-warning fade in">
    <p><%=paramRequest.getLocaleString("msgNoReports")%></p>
</div>
<%}%>
<script type="text/javascript">
                        var count = 0;
                        function submitUrl(url, reference) {
                        var extension = document.getElementById("extension");
                                url = url + '?idReport=' + document.getElementById('idReport').value + '&extension=' + extension.options[extension.selectedIndex].value + '&reportName=' + document.getElementById('reportName').value;
                            dojo.xhrGet({
                                url: url,
                                load: function(response, ioArgs)
                                {
                                count++;
                                document.getElementById('out').style.display = 'block';
                            //alert(count);
                                    document.getElementById('count').innerHTML = count;                                     //setInterval(function(){myTimer()},2000);
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