<%-- 
    Document   : editSectionDialog
    Created on : 19/11/2013, 01:28:47 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.financiera.model.SectionElement"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.financiera.model.DocumentSection"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.financiera.resources.DocumentTemplateResource"%>
<%@page import="org.semanticwb.financiera.model.DocumentTemplate"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite model = paramRequest.getWebPage().getWebSite();
    String iddt = request.getAttribute("iddt") != null ? request.getAttribute("iddt").toString() : "";
    String idds = request.getAttribute("idds") != null ? request.getAttribute("idds").toString() : "";
    DocumentSection ds = DocumentSection.ClassMgr.getDocumentSection(idds, model);
    String lang = paramRequest.getUser().getLanguage();
    SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
    SWBResourceURL urlSave = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.ADD_DOCUMENT_SECTION);
    String viewlabel = "Add section";
    String title = "";
    String uricls = "";
    if (ds != null) {
        title = ds.getTitle();
        viewlabel = "Edit section: " + title;
        urlSave = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateResource.EDIT_DOCUMENT_SECTION);
        uricls = ds.getSectionType().getURI();
    }
    url.setParameter("idds", idds);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4><%=viewlabel%></h4>
        </div>
        <form method="post" id="formesd" action="<%=urlSave%>" onsubmit="submitFormP('formesd');
                return false;" class="form-horizontal" role="form">
            <div class="modal-body">
                <div class="form-group">
                    <label for="" class="col-lg-3 control-label">Title:</label>
                    <div class="col-lg-9">
                        <input type="text" name="titleSection" class="form-control" value="<%=title%>"/>
                    </div>
                </div>
                <br/>
                <div class="form-group">
                    <label for="" class="col-lg-3 control-label">Type * :</label>
                    <div class="col-lg-9">
                        <select name="dstype" class="form-control" onchange="" <% if (ds != null) {%> disabled="true" <%}%>>
                            <%if (ds == null) {%>
                            <option value="">-- Select one option --</option>  
                            <%}
                                Iterator<SemanticClass> itse = SWBComparator.sortByDisplayName(SectionElement.sclass.listSubClasses(), lang);
                                while (itse.hasNext()) {
                                    SemanticClass sc = itse.next();
                                    String dsc = sc.getDisplayName(lang);
                                    String usc = sc.getEncodedURI();
                                    String classuri = sc.getURI();
                            %>
                                    <option onclick="postHtml('<%=url.setMode(DocumentTemplateResource.VIEW_PROPERTIES).setParameter("classuri", classuri)%>', 'containersp');" value="<%=usc%>" <%if (ds != null) {
                                    if (ds.getSectionType().getURI().equals(sc.getURI())) {%> selected="true" <%}%>
                                    <%}%>><%=dsc%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div id="containersp">
                </div>
            </div>
            <div class="modal-footer text-center">
                <a class="btn btn-default fa fa-mail-reply" data-dismiss="modal"> Cancel</a>
                <button type="submit" class="btn btn-success fa fa-save"> Save</button>
            </div>
            <input type="hidden" name="iddt" value="<%=iddt%>"/>
            <input type="hidden" name="idds" value="<%=idds%>"/>
        </form>
    </div>
</div>
<%
    if (ds != null) {%>
<script>
            postHtml('<%=url.setMode(DocumentTemplateResource.VIEW_PROPERTIES).setParameter("classuri", uricls)%>', 'containersp');
</script> 
<%}%>
