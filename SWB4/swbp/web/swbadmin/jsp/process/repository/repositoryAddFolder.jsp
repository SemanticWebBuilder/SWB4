<%-- 
    Document   : repositoryAddFolder.jsp
    Created on : 3/09/2013, 11:41:28 AM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();

if (!user.isSigned()) {
    if (paramRequest.getCallMethod() == SWBParamRequest.Call_CONTENT) {
        %>
        <div class="alert alert-block alert-danger fade in">
            <h4><span class="fa fa-ban"></span> <%=paramRequest.getLocaleString("msgNoAccessTitle")%></h4>
            <p><%=paramRequest.getLocaleString("msgNoAccess")%></p>
            <p>
                <a class="btn btn-default" href="/login/<%=site.getId()%>/<%=paramRequest.getWebPage().getId()%>"><%=paramRequest.getLocaleString("btnLogin")%></a>
            </p>
        </div>
        <%
    }
} else {
    SWBResourceURL viewURL = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW);
    SWBResourceURL createURL = paramRequest.getActionUrl().setAction(ProcessFileRepository.ACT_NEWFOLDER);
    %>
    <div class="col-lg-offset-3 col-lg-6">
    <div class="panel panel-default swbp-panel">
        <div class="panel-heading swbp-panel-title">
            <h1 class="panel-title"><strong><%=paramRequest.getLocaleString("msgCreateDirectory")%></strong></h1>
        </div>
        <form id="folderAddForm" class="form-horizontal" role="form" action="<%=createURL%>" method="post">
            <div class="panel-body">
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgTitle")%> *</label>
                    <div class="col-lg-6">
                        <input id="ftitle" type="text" name="ftitle" class="form-control"/>
                    </div>
                </div>
                    <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgID")%> *</label>
                    <div class="col-lg-6">
                        <input id="fid" type="text" name="fid" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=paramRequest.getLocaleString("msgDescription")%></label>
                    <div class="col-lg-6">
                        <textarea name="fdescription" id="fdescription" class="form-control"></textarea>
                    </div>
                </div>
                </div>
                <div class="panel-footer text-right">
                    <button type="button" onclick="window.location='<%=viewURL%>';" class="btn btn-default"><%=paramRequest.getLocaleString("msgBTNCancel")%></button>
                    <button type="submit" class="btn btn-success"><%=paramRequest.getLocaleString("msgAdd")%></button>
                </div>
            </form>
        </div>
    </div>
    <%
}
%>
<script type="text/javascript">
    $('#ftitle').on('keyup', function(){
        var idField = $('#fid');
        var val = replaceChars4Id($(this).val());
        idField.val(val);
        
        if (!canCreateSemanticObject('<%=site.getSemanticModel().getName()%>','<%=RepositoryDirectory.sclass.getClassId()%>', val)) {
            idField.parents('.form-group').addClass("has-error");
        } else {
            idField.parents('.form-group').removeClass("has-error");
        }
    });
    
    $('#fid').on('keyup blur', function() {
        var val = $(this).val();
        if (canCreateSemanticObject('<%=site.getSemanticModel().getName()%>','<%=RepositoryDirectory.sclass.getClassId()%>', val) === false) {
            $(this).parents('.form-group').addClass("has-error");
        } else {
            $(this).parents('.form-group').removeClass("has-error");
        }
    });
    
    $('form#folderAddForm').on('submit', function() {
        var idFieldVal = $('#fid').val();
        var titleField = $('#ftitle').val();
        var isValid = canCreateSemanticObject('<%=site.getSemanticModel().getName()%>','<%=RepositoryDirectory.sclass.getClassId()%>', idFieldVal);
        
        if (isValid) {
            if (titleField === null || titleField.length === 0) isValid = false;
        }
        
        if (!isValid) {
            alert('<%=paramRequest.getLocaleString("msgCreateEditError")%>');
        }
        return isValid;
    });
</script>