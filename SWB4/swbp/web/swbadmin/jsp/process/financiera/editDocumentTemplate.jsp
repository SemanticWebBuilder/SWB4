<%-- 
    Document   : editDocumentTemplate
    Created on : 14/11/2013, 12:57:13 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.financiera.model.DocumentSection"%>
<%@page import="org.semanticwb.financiera.model.DocumentTemplate"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.financiera.resources.DocumentTemplateResource"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL action = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.ADD_TEMPLATE);
    String iddt = request.getAttribute("iddt") != null ? request.getAttribute("iddt").toString() : "";
    String lang = paramRequest.getUser().getLanguage();
    SWBResourceURL urlrds = null;
    SWBResourceURL urleds = null;
    SWBResourceURL urluds = null;
    String actionlabel = "Add template";
    String title = "";
    WebSite model = paramRequest.getWebPage().getWebSite();
    DocumentTemplate dt = DocumentTemplate.ClassMgr.getDocumentTemplate(iddt, model);
    if (dt != null) {
        action = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.EDIT_TEMPLATE);
        actionlabel = "Edit: " + dt.getTitle();
        title = dt.getTitle();
        urlrds = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.REMOVE_DOCUMENT_SECTION).setParameter("iddt", iddt);
        urleds = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(DocumentTemplateResource.EDIT_DOCUMENT_SECTION).setParameter("iddt", iddt);
        urluds = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.UPDATE_DOCUMENT_SECTION).setParameter("iddt", iddt);
    }
%>
<div class="panel panel-default">
    <div class="panel-heading">
        <div class="panel-title"> 
            <span class=""><%=actionlabel%></span>
            <%
                if (dt != null) {
            %>
            <a class="btn btn-default fa fa-trash-o pull-right"
               data-placement="bottom" data-toggle="tooltip" data-original-title="Eliminar"
               onclick="if (!confirm('Eliminar: <%=title%>?'))
                           return false;
                       deleteDT('<%=paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.REMOVE_TEMPLATE).setParameter("iddt", iddt)%>');">
            </a>
            <a class="btn btn-default fa fa-plus pull-right"
               href="<%=paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(DocumentTemplateResource.ADD_DOCUMENT_SECTION).setParameter("iddt", dt.getId())%>"
               data-toggle="modal" data-target="#modalDialog">
            </a>
            <%}%>
        </div>
    </div>
    <form method="post" id="formdt" action="<%=action%>" onsubmit="submitFormP('formdt');
                       return false;" class="form-horizontal" role="form">
        <div class="panel-body">
            <div class="form-group">
                <label for="" class="col-lg-3 control-label">Title:</label>
                <div class="col-lg-8">
                    <input type="text" name="titleTemplate" id="titleTemplate" value="<%=title%>" class="form-control"/>
                </div>
            </div>
            <br/>
            <div class="form-group">
                <label for="" class="col-lg-3 control-label">Apply to process:</label>
                <div class="col-lg-8">
                <select name="applytop" multiple="true" class="form-control">
                    <option> -- None -- </option>
                    <%
                        int sizep = 0;
                        Iterator<org.semanticwb.process.model.Process> itp = org.semanticwb.process.model.Process.ClassMgr.listProcesses();
                        while (itp.hasNext()) {
                            org.semanticwb.process.model.Process process = itp.next();
                            String titlep = process.getTitle();
                            String idp = process.getURI();
                            String urip = process.getURI();
                    %>
                    <option <% if (dt != null && dt.hasProcess(process)) {%> selected="true" <% sizep++;
                        }%> value="<%=idp%>"><%=titlep%></option>
                    <%}
                    %>
                </select>
                </div>
                <% if (dt != null) {%>
                <label for="" class="col-lg-1 control-label"><li class="badge"><%=sizep%></li></label>
                <%}%>
            </div>
            <br/>
            <%
                if (dt != null) {
                    Iterator<DocumentSection> itds = SWBComparator.sortSortableObject(dt.listDocumentSections());
                    if (itds.hasNext()) {
            %>
            <div class="table-responsive">
                <table class="table table-hover swbp-table">
                    <thead>
                    <th>Active</th>
                    <th>Section</th>
                    <th>Type</th>
                    <th>Created</th>
                    <th>Update</th>
                    <th style="width: 70px;">Order</th>
                    <th style="width: 100px;">Actions</th>
                    </thead>
                    <%
                        while (itds.hasNext()) {
                            DocumentSection ds = itds.next();
                            String titleds = ds.getTitle();
                            String typeds = ds.getSectionType().getDisplayName(lang);
                            String idds = ds.getId();
                    %>
                    <tr>
                        <td><input <%if (ds.isActive()) {%>checked="true"<%}%> id="<%=idds%>" type="checkbox" 
                                                           onclick="updateElement('<%=urluds%>', '<%=idds%>', this);
                       return true;"></td>
                        <td><%=titleds%></td>
                        <td><%=typeds%></td>
                        <td><%=ds.getCreated()%></td>
                        <td><%=ds.getUpdated()%></td>
                        <td>
                            <input type="int" value="<%=ds.getIndex()%>" name="ind<%=ds.getId()%>" class="form-control"/>
                        </td>
                        <td>
                            <a class="btn btn-default fa fa-pencil" 
                               href="<%=urleds.setParameter("idds", idds)%>"
                               data-toggle="modal" data-target="#modalDialog"></a>
                            <a class="btn btn-default fa fa-trash-o" 
                               onclick="if (!confirm('Eliminar: <%=titleds%>?'))
                           return false;
                       deleteDT('<%=urlrds.setParameter("idds", ds.getId())%>', this);
                       return false;">
                            </a>
                        </td>
                    </tr>
                    <%}
                    %>
                </table>
            </div>
            <%} else {%>
            <div class="alert alert-block alert-warning fade in">
                No document section on template: <%=title%>
            </div>
            <%}
                }%>
        </div>
        <div class="panel-footer text-center">
            <button type="submit" class="btn btn-success fa fa-save"> Save</button>
        </div>
        <input type="hidden" name="iddt" value="<%=iddt%>"/>
    </form>
</div>



