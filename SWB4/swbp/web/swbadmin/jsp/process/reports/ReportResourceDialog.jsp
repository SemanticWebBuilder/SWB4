<%-- 
    Document   : ReportResourceExport
    Created on : 4/09/2013, 12:53:03 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.process.resources.reports.Report"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    Report report = Report.ClassMgr.getReport(request.getAttribute("idReport").toString(), paramRequest.getWebPage().getWebSite());
    boolean isSaveOnSystem = Boolean.parseBoolean(request.getAttribute("isSaveOnSystem").toString());
    SWBResourceURL urlReport = paramRequest.getRenderUrl().setMode("generate").setCallMethod(SWBParamRequest.Call_DIRECT);
    String action = request.getParameter("action");
    String title = "";
    if (isSaveOnSystem) {
        title = paramRequest.getLocaleString("save");
    } else {
        title = paramRequest.getLocaleString("generate");
    }
    title += " " + paramRequest.getLocaleString("report") + " " + report.getTitle();
if (action.equals("export")) {%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                    data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("close")%>">&times;</button>
            <h4><%=title%></h4>
        </div>
        <form method="post" <%if (!isSaveOnSystem) {%>action="<%=urlReport%>"<%}%>>
            <div class="modal-body">
                <input type="hidden" id="idReport" name="idReport" value="<%=report.getId()%>"/>
                <table style="width: 100%;"><tr>
                        <td style="width: 200px; text-align: right;"><strong><%=paramRequest.getLocaleString("name") + " " + paramRequest.getLocaleString("of") + " " + paramRequest.getLocaleString("file") + ": "%></strong> </td>
                        <td><input class="form-control input-sm" placeholder="<%=paramRequest.getLocaleString("name") + " " + paramRequest.getLocaleString("of") + " " + paramRequest.getLocaleString("file")%>" type="text" id="reportName" name="reportName"/></td>
                    </tr><tr>
                        <td style="width: 200px; text-align: right;"><strong><%=paramRequest.getLocaleString("format") + ": "%></strong> </td>
                        <td><select class="form-control input-sm" id="extension" name="extension">
                                <option value="xls" selected="true">Excel</option>
                                <option value="pdf">PDF</option>
                            </select></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal"
                        data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("back")%>">
                    <li class="fa fa-mail-reply"></li> Regresar</button>
                <button type="submit" class="btn btn-success" <%if (isSaveOnSystem) {%>onclick="submitUrl('<%=urlReport%>', this);
        return false;" data-dismiss="modal" data-original-title="<%=paramRequest.getLocaleString("save")%>"<%}%> 
                        data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("generate")%>">
                    <%if (isSaveOnSystem) {%>
                    <li class="fa fa-save"></li> Guardar
                        <%} else {%>
                    <li class="fa fa-file"></li> Generar<%}%>
                </button>
            </div>
        </form>
    </div>
</div>
<%}
    if (action.equals("delete")) {%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                    data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("close")%>">&times;</button>
            <h4>Eliminar archivo</h4>
        </div>
        <div class="modal-body">
            Body
        </div>
        <div class="modal-footer">
            footer
        </div>
    </div>
</div>
<%}%>

