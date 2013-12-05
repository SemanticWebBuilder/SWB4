<%-- 
    Document   : editSectionTemplateBehavior
    Created on : 20/11/2013, 01:13:04 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.financiera.model.Definition"%>
<%@page import="org.semanticwb.financiera.model.Reference"%>
<%@page import="org.semanticwb.process.resources.ProcessFileRepository"%>
<%@page import="org.semanticwb.portal.api.SWBResourceModes"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.RepositoryDirectory"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURLImp"%>
<%@page import="org.semanticwb.financiera.model.Format"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.financiera.model.FreeText"%>
<%@page import="org.semanticwb.financiera.model.DocumentSectionInstance"%>
<%@page import="org.semanticwb.financiera.model.SectionElement"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.model.DisplayProperty"%>
<%@page import="org.semanticwb.financiera.model.DocumentTemplate"%>
<%@page import="org.semanticwb.financiera.resources.DocumentTemplateBehavior"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.financiera.model.DocumentSection"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="multipart/form-data" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite model = paramRequest.getWebPage().getWebSite();
    String lang = paramRequest.getUser().getLanguage();
    String uridsi = request.getAttribute("uridsi") != null ? request.getAttribute("uridsi").toString() : "";
    String urisei = request.getAttribute("urisei") != null ? request.getAttribute("urisei").toString() : "";
    String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : "";
    DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);
    if (dsi == null) {
        urisei = urisei.substring(0, urisei.indexOf("|"));
        uridsi = request.getParameter("urisei") != null ? request.getParameter("urisei") : "";
        uridsi = uridsi.substring(uridsi.lastIndexOf("|") + 1, uridsi.length());
        dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);
    }
    String action = "";
    SWBResourceURL urlAction = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateBehavior.ADD_SECTION).setParameter("suri", suri);
    SWBResourceURL urlFile = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateBehavior.ADD_SECTION_WITH_FILE);
    SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
    SWBFormMgr forMgr = null;
    SectionElement sei = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urisei);
    if (sei != null) {
        action = "Edit: " + (sei.getTitle() != null ? sei.getTitle() : "--");
        urlAction = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(DocumentTemplateBehavior.EDIT_SECTION_INSTANCE).setParameter("suri", suri);
        forMgr = new SWBFormMgr(sei.getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        urlFile.setAction(DocumentTemplateBehavior.EDIT_SECTION_WITH_FILE);
    } else {
        forMgr = new SWBFormMgr(cls, model.getSemanticObject(), SWBFormMgr.MODE_CREATE);
        action = "Add: " + dsi.getSecTypeDefinition().getTitle();
    }
    forMgr.clearProperties();
    Iterator<SemanticProperty> itsp = cls.listProperties();
    while (itsp.hasNext()) {
        SemanticProperty sp = itsp.next();
        if (sp.getDisplayProperty() != null) {
            forMgr.addProperty(sp);
        }
    }
    String forfocus = "";
    forMgr.setType(SWBFormMgr.TYPE_XHTML);
    out.print(SWBFormMgr.DOJO_REQUIRED);
    //SWBResourceURL urlIf = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction("iframe");
%>
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/repository/fileRepositoryUtils.js" charset="utf-8"></script>
<div class="modal-dialog">
    <div class="modal-content swbp-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <%=action%>
        </div>
        <form <% if (!cls.getClassId().equals(Format.sclass.getClassId())
                    && !cls.getClassId().equals(Reference.sclass.getClassId())
                    && !cls.getClassId().equals(Definition.sclass.getClassId())) {%> action="<%=urlAction%>" <%} else {%> action="<%=urlFile%>"<%}%> 
            class="form-horizontal"  role="form" id="upload" name="upload" method="post" enctype="multipart/form-data">
            <%out.print(forMgr.getFormHiddens());%>
            <div class="modal-body">
                <%
                    Map map = new HashMap();
                    if (dsi.getSecTypeDefinition().getVisibleProperties().length() > 0) {
                        String[] values = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                        for (int i = 0; i < values.length; i++) {
                            String value = values[i];
                            String title = value.substring(0, value.indexOf(";"));
                            String propid = value.substring((value.indexOf(";") + 1), value.length());
                            map.put(propid, title);
                        }
                    }
                    itsp = forMgr.getProperties().iterator();
                    int cont = 0;
                    while (itsp.hasNext()) {
                        SemanticProperty sp = itsp.next();
                        //System.out.println("sp: " + sp.getPropId());
                        String idinput = "";
                        String titlesp = map.containsKey(sp.getPropId()) ? map.get(sp.getPropId()).toString() : sp.getDisplayName(lang);
                        if (!sp.getPropId().equals("finrural:file")) {
                            if (dsi.getSecTypeDefinition().getVisibleProperties().contains(sp.getPropId())) {
                                idinput = sp.getName();
                                //Boolean required = sp.isRequired();
                                if (cont == 0) {
                                    forfocus = sp.getName();
                                }
                                //System.out.println("sp " + sp.getName() + "\trequired: " + required);
                                cont++;
                %>                
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=titlesp%></label>
                    <div class="col-lg-6">
                        <%
                            String inputfm = forMgr.renderElement(request, sp, SWBFormMgr.MODE_CREATE);
                            inputfm = inputfm.replaceFirst(">", "required=\"true\" id=\"" + idinput + "\" class=\"form-control\">");
                            out.println(inputfm);
                        %>
                    </div>
                </div>
                <%}
                } else {
                    if (sei != null) {
                        Format format = null;
                        Reference reference = null;
                        Definition definition = null;
                        RepositoryDirectory rd = null;
                        String filename = "";
                        String fid = "";
                        String verNum = "";
                        System.out.println("cls.getClassId(): " + cls.getClassId());
                        if (cls.getClassId().equals(Format.sclass.getClassId())) {
                            format = (Format) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(sei.getURI());
                            if (format.getRefRepository() != null) {
                                rd = format.getRefRepository().getRepositoryDirectory();
                                filename = format.getRefRepository().getTitle();
                                fid = format.getRefRepository().getId();
                                verNum = format.getRefRepository().getLastVersion().getVersionNumber() + "";
                            }
                        }
                        if (cls.getClassId().equals(Reference.sclass.getClassId())) {
                            reference = (Reference) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(sei.getURI());
                            if (format.getRefRepository() != null) {
                                rd = reference.getRefRepository().getRepositoryDirectory();
                                filename = reference.getRefRepository().getTitle();
                                fid = reference.getRefRepository().getId();
                                verNum = reference.getRefRepository().getLastVersion().getVersionNumber() + "";
                            }
                        }
                        if (cls.getClassId().equals(Definition.sclass.getClassId())) {
                            definition = (Definition) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(sei.getURI());
                            if (format.getRefRepository() != null) {
                                rd = definition.getRefRepository().getRepositoryDirectory();
                                filename = definition.getRefRepository().getTitle();
                                fid = definition.getRefRepository().getId();
                                verNum = definition.getRefRepository().getLastVersion().getVersionNumber() + "";
                            }
                        }
                        System.out.println("rd on jsp view: " + rd);
                        if (rd != null) {
                            Resource res = null;
                            Iterator<Resource> ire = rd.listResources();
                            while (ire.hasNext()) {
                                res = ire.next();
                                if (res.getResourceType().getResourceClassName().equals("org.semanticwb.process.resources.ProcessFileRepository")) {
                                    break;
                                }
                            }
                            if (res != null) {
                                SWBResourceURL urlDownload = new SWBResourceURLImp(request, res, (WebPage) rd, SWBResourceModes.UrlType_RENDER);
                                urlDownload.setMode(ProcessFileRepository.MODE_GETFILE);
                                urlDownload.setCallMethod(SWBResourceURL.Call_DIRECT);
                                urlDownload.setParameter("fid", fid);
                                urlDownload.setParameter("verNum", verNum);
                %>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=titlesp%></label>
                    <div class="col-lg-7">
                        <a href="<%=urlDownload%>" class="form-control"><%=filename%> <i class="fa fa-download pull-right"></i></a>
                    </div>
                </div>
                <%
                    }
                } else {%>
                <div class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=titlesp%></label>
                    <div class="col-lg-7">
                        <input class="form-control" value="No file" disabled="true"> 
                    </div>
                </div>  
                <%}
                } else {
                %>
                <div id="fileSelect" class="form-group">
                    <label for="" class="col-lg-4 control-label"><%=titlesp%></label>
                    <div class="col-lg-7">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <span class="btn btn-success btn-file">
                                    Seleccionar <input type="file" name="ffile" class="form-control">
                                </span>
                            </span>
                            <input type="text" class="form-control" disabled="true"/>
                        </div>
                    </div>
                </div>
                <%}
                    }%>
                <br/>
                <%}%>
            </div>

            <input type="hidden" value="<%=uridsi%>" name="uridsi">
            <input type="hidden" value="<%=suri%>" name="surio">
            <input type="hidden" value="<%=urisei%>" name="urisei">
            <div class="modal-footer">
                <a class="btn btn-default fa fa-mail-reply" data-dismiss="modal"> Cancel</a>
                <button id="saveForm" dojoType="dijit.form.Button" class="btn btn-success fa fa-save" type="submit" 
                        data-dismiss="modal" 
                        <% if (!cls.getClassId().equals(Format.sclass.getClassId())
                                    && !cls.getClassId().equals(Reference.sclass.getClassId())
                                    && !cls.getClassId().equals(Definition.sclass.getClassId())) {%>onclick="submitFormPB('upload');
                                            return false;"<%} else {%> onclick="sendFormJq();
                                            return false;"<%}%> > Save</button>
            </div>
        </form>


        <form>
            <input id="titletest" type="text" class="form-control">

            <div class="form-group has-success">
                <label class="control-label" for="inputSuccess">Input with success</label>
                <input type="text" class="form-control" id="inputSuccess">
            </div>
            <div class="form-group has-warning">
                <label class="control-label" for="inputWarning">Input with warning</label>
                <input type="text" class="form-control" id="inputWarning">
            </div>
            <div class="form-group has-error">
                <label class="control-label" for="inputError">Input with error</label>
                <input type="text" class="form-control" id="inputError">
            </div>
            <button id="saveButton" class="btn btn-default">Save</button>
        </form>
        <!--form method="post" enctype="multipart/form-data" onsubmit="return false;">
        <input type="file" id="filef" name="filef">
        <input type="text" id="txtf" name="txtf">
        <button onclick="subm(this.form, null);">Enviar</button>
    </form>
        <iframe id="if" name="if" style="display: none;" src="">
        </iframe>
        <script>
            function subm(formid, element){
                var encoding = formid.encoding;
                formid.encoding = 'multipart/form-data';
                var method = formid.method;
                formid.method = 'post';
                var action = formid.action;
                formid.action = '<%//urlIf%>';
                var target = formid.target;
                formid.target = 'if';
                formid.submit();
                
                
                formid.encoding = encoding;
                formid.method = method;
                formid.action = action;
                formid.target = target;
                return false;
                
            }
        </script-->
    </div>
</div>

<script>
                                        var winVar;
                                        window.clearTimeout(winVar);
                                        winVar = window.setTimeout(function() {
                                            document.getElementById('<%=forfocus%>').focus();
                                        }, 500);
                                        $('#saveButton').click(function() {
                                            //alert('enter jquey');
                                            var value = ($('#titletest').val());
                                            alert('value: ' + value);

                                            if ($('#titletest').val() === "") {
                                                // invalid
                                                $('#titletest').addClass('has-warning');
                                                return false;
                                            }
                                            else {
                                                // submit the form here
                                                console.log('send form');
                                                //$('#InfroText').submit();
                                                return false;
                                            }
                                        });

</script>