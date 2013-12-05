<%-- 
    Document   : documentTemplate
    Created on : 14/11/2013, 11:36:43 AM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.financiera.model.DocumentSection"%>
<%@page import="org.semanticwb.financiera.model.DocumentTemplate"%>
<%@page import="org.semanticwb.financiera.resources.DocumentTemplateResource"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
<script type="text/javascript">
    var djConfig = {
        parseOnLoad: true,
        isDebug: false
    };
</script>
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/fontawesome/font-awesome.css" rel="stylesheet">
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/commons/css/swbp.css" rel="stylesheet">
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/jquery/jquery.js"></script>
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/bootstrap/bootstrap.js"></script>
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/commons/css/swbp.css" rel="stylesheet">
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/swb.js"></script>
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/swb_admin.js"></script>
<script type="text/javascript" src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/utils/tinymce/tinymce.min.js"%>"></script>
<script type="text/javascript">
    $(document).ready(function() {
        //Destroy modals content
        $('#modalDialog').on('hidden.bs.modal', function() {
            $(this).removeData('bs.modal');
        });
    });
</script>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT);
    WebSite model = paramRequest.getWebPage().getWebSite();
    String iddt = request.getParameter("iddt") != null ? request.getParameter("iddt").toString() : "";
    /*Iterator<WebPage> itwp = model.getHomePage().listChilds();
     while (itwp.hasNext()) {
     WebPage wp = itwp.next();
     out.println("<br/>" + wp.getTitle() +" is rd: " +(wp instanceof RepositoryDirectory));
     }*/

%>
<!--link href="<%//SWBPlatform.getContextPath()%>/swbadmin/jsp/process/financiera/css/style.css" rel="stylesheet"-->
<div class="row">
    <div class="col-lg-4">
        
    </div>
</div>
<br/>
<div class="row">
    <div class="col-lg-4">
        <div class="panel panel-default swbp-panel">
            <div class="panel-heading swbp-panel-title">
            <div class="panel-title">
                <strong>Templates</strong>
                <a onclick="postHtml('<%=url.setMode(DocumentTemplateResource.ADD_TEMPLATE)%>', 'container');" class="btn btn-default fa fa-plus pull-right"> Add</a>
            </div>
        </div>
            <div class="panel-body">
                <div class="list-group">
            <%
                Iterator<DocumentTemplate> iterator = DocumentTemplate.ClassMgr.listDocumentTemplates(model);
                while (iterator.hasNext()) {
                    DocumentTemplate dt = iterator.next();
                    String title = dt.getTitle();
            %>
            <a class="list-group-item" onclick="postHtml('<%=url.setMode(DocumentTemplateResource.EDIT_TEMPLATE).setParameter("iddt", dt.getId())%>', 'container');" style="cursor: pointer;"><i class="fa fa-file"></i> <%=title%></a>
            <%}
            %>
        </div>
            </div>
        </div>
    </div>
    <div class="col-lg-8">
        <div id="container">
        </div>
    </div>
</div>
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-hidden="true"></div>
<script>
    var iddt = '<%=iddt%>';
    var winVar;
    var returl;
    if (iddt != "") {
        returl = '<%=paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode(DocumentTemplateResource.EDIT_TEMPLATE)%>' + '?iddt=' + iddt + '';
        redirect(returl);
    } else {
        redirectRDT();
    }
    function saveDocumentTemplate(url, reference) {
        var titleTemplate = document.getElementById("titleTemplate");
        var iddt = document.getElementById("iddt");
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            content: {titleTemplate: titleTemplate.value, iddt: iddt.value},
            load: function(response, ioArgs)
            {
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?iddt=' + response.toString().trim() + '';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }

    function redirect(url) {
        window.clearTimeout(winVar);
        winVar = window.setTimeout(function() {
            postHtml('' + url + '', 'container');
            uncollapse($('#ttdt'), this);
        }, 100);
    }
    function deleteDT(url, reference) {
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            load: function(response, ioArgs)
            {
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?iddt=' + response.toString().trim() + '';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }
    function redirectRDT() {
        window.clearTimeout(winVar);
        winVar = window.setTimeout(function() {
            uncollapse($('#ttdt'), this);
        }, 200);
    }
    /*
     function saveDocumentSection(url, reference) {
     var titleSection = document.getElementById("titleSection");
     var dstype = document.getElementById("dstype");
     var iddt = document.getElementById("iddt");
     var idds = document.getElementById("idds");
     dojo.xhrPost({
     url: url,
     contentType: "application/x-www-form-urlencoded; charset=utf-8",
     content: {titleSection: titleSection.value, iddt: iddt.value, idds: idds.value, dstype: dstype.options[dstype.selectedIndex].value},
     load: function(response, ioArgs)
     {
     window.location = '<%//paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?iddt=' + response.toString().trim() + '';
     return response;
     },
     error: function(response, ioArgs) {
     return response;
     },
     handleAs: "text"
     });
     }*/
    function updateElement(url, id, reference) {
        var idds = document.getElementById(id);
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            content: {active: idds.checked, idds: id},
            load: function(response, ioArgs)
            {
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?iddt=' + response.toString().trim() + '';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }
    function submitFormP(formid) {
        var obj = dojo.byId(formid);
        var fid = formid;
        dojo.xhrPost({
            url: obj.action,
            form: fid,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            load: function(response, ioArgs)
            {
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?iddt=' + response.toString().trim() + '';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }
    function getform() {
        var formpp = document.getElementById('formpp');
        alert(formpp);
        //formpp.submit();
    }

</script>
<script>


    /**
     * Collapses a tree element.
     * 
     * @param element Toggling anchor (a.tree-toggler) object.
     */
    function collapse(element) {
        var childs = element.parent().children('ul.tree');
        if (childs.length > 0) {
            element.data('collapsed', 'collapsed');
            element.removeClass('fa fa-folder-open-o');
            element.addClass('fa fa-folder-o');
            childs.hide(200);
        }
    }

    /**
     * Uncollapses a tree element and all it's parents if needed.
     * 
     * @param element Toggling anchor (a.tree-toggler) object.
     * @param parents Indicates wheter parents should be uncollapsed.
     */
    function uncollapse(element, parents) {
        var childs = element.parent().children('ul.tree');
        if (childs.length > 0) {
            element.data('collapsed', 'uncollapsed');
            element.removeClass('fa fa-folder-o');
            element.addClass('fa fa-folder-open-o');
            childs.show(200);
        }

        if (parents && parents !== 'undefined' && parents === true) {
            var parents = element.parent().parents('li.list-group-item');
            parents.each(function() {
                var childs = $(this).children('a.tree-toggler');
                childs.each(function() {
                    uncollapse($(this), false);
                });
            });
        }
    }
</script>
