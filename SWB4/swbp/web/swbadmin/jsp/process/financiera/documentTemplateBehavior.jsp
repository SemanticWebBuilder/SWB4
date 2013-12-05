<%-- 
    Document   : documentTemplateBehavior
    Created on : 20/11/2013, 11:55:59 AM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.financiera.model.Definition"%>
<%@page import="org.semanticwb.financiera.model.Reference"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.portal.api.SWBResourceModes"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.financiera.model.Format"%>
<%@page import="org.semanticwb.process.model.ProcessElement"%>
<%@page import="org.semanticwb.process.model.ProcessSite"%>
<%@page import="org.semanticwb.financiera.model.FreeText"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.financiera.model.DocumentSectionInstance"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.model.GenericObject"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.financiera.model.DocumentationInstance"%>
<%@page import="org.semanticwb.financiera.model.SectionElement"%>
<%@page import="org.semanticwb.financiera.resources.DocumentTemplateBehavior"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.financiera.model.DocumentSection"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.financiera.model.DocumentTemplate"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/fontawesome/font-awesome.css" rel="stylesheet">
<!--link href="<%//SWBPlatform.getContextPath()%>/work/config/images/estilo.css" rel="stylesheet"-->
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/commons/css/swbp.css" rel="stylesheet">
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/jquery/jquery.js"></script>
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/bootstrap/bootstrap.js"></script>

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
    //WebSite model = paramRequest.getWebPage().getWebSite();
    //model.getHomePage().listChilds() - instanceof repositorydirectory
    //ServiceTask - process.model.storerepositoryfile
    String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : URLDecoder.decode(request.getParameter("suri"));
    org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
    ProcessElement processElement = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
    ProcessSite model = processElement.getProcessSite();
    //System.out.println("enter on jsp: " + process);


    DocumentationInstance di = null;
    DocumentTemplate dt = null;
    Iterator<DocumentationInstance> itdi = null;
    SWBResourceURL urlEditor = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateBehavior.SAVE_FREE_TEXT).setParameter("suri", suri);
    //System.out.println("the dt on process: " + DocumentTemplate.ClassMgr.listDocumentTemplateByProcess(process));
    if (DocumentTemplate.ClassMgr.listDocumentTemplateByProcess(process).hasNext()) {
        dt = DocumentTemplate.ClassMgr.listDocumentTemplateByProcess(process).next();
        itdi = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(process);
        //System.out.println("get di: " + itdi);
        /*itdi = DocumentationInstance.ClassMgr.listDocumentationInstances();
         while(itdi.hasNext()){
         di = itdi.next();
         di.removeAllDocumnetSectionInstance();;
         di.removeDocTypeDefinition();
         di.removeProcessRef();
         di.remove();
         }*/
        if (itdi.hasNext()) {
            di = itdi.next();
        } else {
            di = DocumentationInstance.ClassMgr.createDocumentationInstance(model);
            di.setDocTypeDefinition(dt);
            di.setProcessRef(process);
            Iterator<DocumentSection> itdsi = di.getDocTypeDefinition().listDocumentSections();
            while (itdsi.hasNext()) {
                DocumentSection ds = itdsi.next();
                DocumentSectionInstance dsi = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                dsi.setSecTypeDefinition(ds);
                di.addDocumnetSectionInstance(dsi);
                SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
                if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
                    FreeText ft = FreeText.ClassMgr.createFreeText(model);
                    ft.setText("");
                    SectionElement se = (SectionElement) ft.getSemanticObject().createGenericInstance();
                    dsi.addDocuSectionElementInstance(se);
                }
            }
        }
        SWBResourceURL urlDialog = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("suri", suri);
        String titleDt = dt.getTitle();
        SWBResourceURL urlAction = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateBehavior.REMOVE_SECTION_INSTANCE).setParameter("suri", suri);

%>

<div class="col-lg-12 swbp-content" role="main">
    <div class="panel panel-default swbp-panel">
        <div class="panel-heading swbp-panel-title">
            <div class="panel-title">
                <h1 class="panel-title"><strong><%=titleDt%></strong></h1>
            </div>
        </div>
        <div class="panel-body">
            <%
                Iterator<DocumentSectionInstance> itdsi = di.listDocumnetSectionInstances();
                Map map = new HashMap();
                while (itdsi.hasNext()) {
                    DocumentSectionInstance dsit = itdsi.next();
                    map.put(dsit.getSecTypeDefinition().getURI(), dsit.getURI());
                }
                Iterator<DocumentSection> itdst = SWBComparator.sortSemanticObjects(dt.listDocumentSections());
                ArrayList arr = new ArrayList();
                int i = 0;
                while (itdst.hasNext()) {
                    DocumentSection dst = itdst.next();
                    if (!map.containsKey(dst.getURI())) {
                        DocumentSectionInstance dsin = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                        dsin.setSecTypeDefinition(dst);
                        di.addDocumnetSectionInstance(dsin);
                        SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsin.getSecTypeDefinition().getSectionType().getURI());
                        if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
                            FreeText ft = FreeText.ClassMgr.createFreeText(model);
                            ft.setText("");
                            SectionElement se = (SectionElement) ft.getSemanticObject().createGenericInstance();
                            dsin.addDocuSectionElementInstance(se);
                        }
                        arr.add(i, dsin.getURI());
                    } else {
                        arr.add(i, map.get(dst.getURI()));
                    }
                    i++;
                }
                for (int j = 0; j < arr.size(); j++) {
                    DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(arr.get(j).toString());
                    String titleDs = dsi.getSecTypeDefinition().getTitle();
                    String uridsi = dsi.getURI();
                    SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
            %>
            <div class="panel panel-default swbp-panel">
                <div class="panel-heading">
                    <div class="panel-title">
                        <%=titleDs%>
                        <%if (!FreeText.sclass.getClassId().equals(cls.getClassId())) {%>
                        <a href="<%=urlDialog.setMode(DocumentTemplateBehavior.ADD_SECTION).setParameter("uridsi", uridsi)%>" class="btn btn-default fa fa-plus pull-right" 
                           data-toggle="modal" data-target="#modalDialog"> Add</a>
                        <%}%>
                    </div>
                </div>
                <%
                    if (!FreeText.sclass.getClassId().equals(cls.getClassId())) {%>
                <div class="panel-body">
                    <%
                        Map mapsp = new HashMap();
                        if (dsi.getSecTypeDefinition().getVisibleProperties().length() > 0) {
                            String[] values = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                            for (int k = 0; k < values.length; k++) {
                                String value = values[k];
                                String title = value.substring(0, value.indexOf(";"));
                                String propid = value.substring((value.indexOf(";") + 1), value.length());
                                mapsp.put(propid, title);
                            }
                        }
                        Iterator<SectionElement> itse = SWBComparator.sortByCreated(dsi.listDocuSectionElementInstances());
                        if (itse.hasNext()) {
                            String[] propid = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                            String[] newpropid = new String[propid.length];
                            String[] newproptitle = new String[propid.length];
                            for (int n = 0; n < propid.length; n++) {
                                String value = propid[n];
                                newproptitle[n] = value.substring(0, value.indexOf(";"));
                                newpropid[n] = value.substring((value.indexOf(";") + 1), value.length());
                            }
                            if (newpropid.length > 0) {%>
                    <div class="table-responsive">
                        <table class="table table-hover swbp-table">
                            <thead>
                                <%for (int k = 0; k < newpropid.length; k++) {
                                        //SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(newpropid[k]);
                                        String dn = newproptitle[k];
                                %>
                            <th><%=dn%></th>
                                <%}%>
                            <th style="width: 100px;">Actions</th>
                            </thead>
                            <%
                                while (itse.hasNext()) {
                            %>
                            <tr>
                                <%
                                    SectionElement se = itse.next();
                                    String urisei = se.getURI();
                                    String titlese = se.getTitle() != null ? se.getTitle() : "--";
                                    for (int k = 0; k < newpropid.length; k++) {
                                        SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(newpropid[k]);
                                        Resource resource = null;
                                        String filename = "";
                                        Format format = null;
                                        Reference reference = null;
                                        Definition definition = null;
                                        if (prop.getPropId().equals("finrural:file")) {
                                            if (se.getSemanticObject().getSemanticClass().getClassId().equals(Format.sclass.getClassId())) {
                                                format = (Format) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                                            }
                                            if (se.getSemanticObject().getSemanticClass().getClassId().equals(Reference.sclass.getClassId())) {
                                                reference = (Reference) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                                            }
                                            if (se.getSemanticObject().getSemanticClass().getClassId().equals(Definition.sclass.getClassId())) {
                                                definition = (Definition) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                                            }
                                %>
                                <td>
                                    <%
                                        RepositoryDirectory rd = null;
                                        String fid = "";
                                        String verNum = "";
                                        if (format != null && format.getRefRepository() != null) {
                                            rd = format.getRefRepository().getRepositoryDirectory();
                                            fid = format.getRefRepository().getId();
                                            verNum = format.getRefRepository().getLastVersion().getVersionNumber() + "";
                                            filename = format.getRefRepository().getTitle();
                                        }
                                        if (reference != null && reference.getRefRepository() != null) {
                                            rd = reference.getRefRepository().getRepositoryDirectory();
                                            fid = reference.getRefRepository().getId();
                                            verNum = reference.getRefRepository().getLastVersion().getVersionNumber() + "";
                                            filename = reference.getRefRepository().getTitle();
                                        }
                                        if (definition != null && definition.getRefRepository() != null) {
                                            rd = definition.getRefRepository().getRepositoryDirectory();
                                            fid = definition.getRefRepository().getId();
                                            verNum = definition.getRefRepository().getLastVersion().getVersionNumber() + "";
                                            filename = definition.getRefRepository().getTitle();
                                        }

                                        //System.out.println("repositoryelement: " + rd);

                                        if (rd != null) {
                                            Iterator<Resource> ire = rd.listResources();
                                            while (ire.hasNext()) {
                                                resource = ire.next();
                                                if (resource.getResourceType().getResourceClassName().equals("org.semanticwb.process.resources.ProcessFileRepository")) {
                                                    break;
                                                }
                                            }
                                            if (resource != null) {
                                                SWBResourceURL urlDownload = new SWBResourceURLImp(request, resource, (WebPage) rd, SWBResourceModes.UrlType_RENDER);
                                                urlDownload.setMode(ProcessFileRepository.MODE_GETFILE);
                                                urlDownload.setCallMethod(SWBResourceURL.Call_DIRECT);
                                                urlDownload.setParameter("fid", fid);
                                                urlDownload.setParameter("verNum", verNum);
                                    %>

                                    <a href="<%=urlDownload%>"><%=filename%> <i class="fa fa-download"></i></a>

                                    <%
                                        }
                                    } else {%>
                                    <span>No file</span>   
                                    <%}%>
                                </td>    
                                <%//}

                                } else {
                                    SWBFormMgr forMgr = new SWBFormMgr(se.getSemanticObject(), null, SWBFormMgr.MODE_VIEW);
                                %>
                                <td><%
                                    String outprop = forMgr.renderElement(request, prop, SWBFormMgr.MODE_VIEW);
                                    outprop = outprop.substring((outprop.indexOf(">") + 1), outprop.lastIndexOf("<"));
                                    //System.out.println("outprop: " + outprop);
                                    out.print(outprop.length() > 40 ? SWBUtils.TEXT.cropText(outprop, 40) : outprop);%></td>
                                    <%}
                                        }%>
                                <td>
                                    <a href="<%=urlDialog.setMode(DocumentTemplateBehavior.EDIT_SECTION_INSTANCE).setParameter("urisei", urisei + "|" + uridsi)%>" class="btn btn-default fa fa-pencil"
                                       data-toggle="modal" data-target="#modalDialog"></a> 
                                    <a class="btn btn-default fa fa-trash-o"
                                       onclick="if (!confirm('Remove: <%=titlese%>?'))
            return false;
        deleteSEI('<%=urlAction.setParameter("urisei", urisei + "|" + uridsi)%>', this);
        return false;"></a></td> 
                            </tr>  
                            <%}%>
                        </table>  
                    </div>
                    <%}%>
                    <%} else {
                    %>
                    <div class="alert alert-block alert-warning fade in">
                        No instances for <%=titleDs%>
                    </div>
                    <%}%>
                </div>
                <%} else {
                    FreeText ft = (FreeText) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(dsi.getDocuSectionElementInstance().getURI());
                %>
                <form method="post">
                    <div class="editable" id="editable<%=ft.getURI()%>">
                        <%out.println(ft.getText());%>
                    </div>
                </form>
                <%}%>
            </div>
            <%}%>
        </div>
    </div>
</div>
<%
} else {%>
<div class="alert alert-block alert-warning fade in">
    No document template assigned
</div>
<%}%>
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-hidden="true"></div>
<script type="text/javascript">
    function submitFormPB(formid) {
        var obj = dojo.byId(formid);
        var fid = formid;
        dojo.xhrPost({
            url: obj.action,
            form: fid,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            load: function(response, ioArgs) {
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?suri=' + encodeURIComponent(response) + '';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }
    dojo.require("dojo.io.iframe");

    function sendForm(formid) {
        var obj = dojo.byId(formid);
        dojo.io.iframe.send({
            url: obj.action,
            method: "post",
            handleAs: "text",
            form: obj,
            load: function(response, ioArgs) {
                console.log("Upload OK", response, ioArgs);
                return response;
            },
            error: function(response, ioArgs) {

                console.log("Upload FAILED!!!", response, ioArgs);
                return response;
            }
        });
    }


    function deleteSEI(url, reference) {
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            load: function(response, ioArgs) {
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?suri=' + encodeURIComponent(response);
                +'';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }
    function saveFreeText(url, data, urift) {
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            content: {data: data.toString().trim(), urift: urift},
            load: function(response, ioArgs)
            {
                //window.location = '<%//paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?suri=' + encodeURIComponent(response) +'';
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }

    function sendFormJq() {
        $("#upload").submit(function(e) {
            var formObj = $(this);
            console.log('out: ' + $(this));
            var formURL = formObj.attr("action");
            var formData = new FormData(this);
            $.ajax({
                url: formURL,
                type: 'POST',
                data: formData,
                mimeType: "multipart/form-data",
                contentType: false,
                cache: false,
                processData: false,
                success: function(data, textStatus, jqXHR) {
                    window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?suri=' + encodeURIComponent(data) + '';
                },
                error: function(jqXHR, textStatus, errorThrown) {
                }
            });
            e.preventDefault(); //Prevent Default action.
        });
        $("#upload").submit(); //Submit the form
    }

    function sendFile(url) {
        dojo.io.iframe.send({
            url: url,
            method: "post",
            handleAs: "text",
            contentType: "multipart/form-data",
            form: dojo.byId("upload"),
            load: function(response, ioArgs) {
                console.log("Upload OK", response, ioArgs);
                //window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?suri=' + encodeURIComponent(response) + '';
                return response;
            },
            error: function(response, ioArgs) {
                console.log('error response: ' + response);
                window.location = '<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_VIEW)%>' + '?suri=' + encodeURIComponent(response) + '';
                //console.log("Upload FAILED!!!", response, ioArgs);
                return response;
            }
        });
    }
</script>
<script>
    $(window).load(function() {
        tinymce.init({
            selector: "div.editable",
            language: '<%=paramRequest.getUser().getLanguage()%>',
            theme: "modern",
            force_br_newlines: false,
            force_p_newlines: false,
            forced_root_block: '',
            toolbar: "save",
            save_enablewhendirty: true,
            height: 300,
            plugins: [
                "save advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
                "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                "directionality emoticons template textcolor paste fullpage textcolor table contextmenu"
            ],
            save_onsavecallback: function(ed) {
                var urift = ed.id.replace("editable", "");
                saveFreeText('<%=urlEditor%>', ed.getContent(), urift);
            },
            toolbar1: "save bold italic underline strikethrough | \n\
 alignleft aligncenter alignright alignjustify bullist numlist | cut copy paste |\n\
 searchreplace | outdent indent blockquote | undo redo | \n\
 thr removeformat | subscript superscript | charmap | print fullscreen | \n\
 link unlink anchor code | inserttime preview",
            toolbar2: "| forecolor backcolor styleselect formatselect fontselect fontsizeselect|",
            menubar: false,
            toolbar_items_size: 'small',
            style_formats: [
                {title: 'Bold text', inline: 'b'},
                {title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
                {title: 'Red header', block: 'h1', styles: {color: '#ff0000'}}, {title: 'Example 1', inline: 'span', classes: 'example1'}, {title: 'Example 2', inline: 'span', classes: 'example2'},
                {title: 'Table styles'},
                {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
            ]
        });
    });//]]> 
</script>