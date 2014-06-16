<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.process.model.ItemAware"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.process.model.UserTask"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.process.resources.ProcessForm"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest)request.getAttribute(ProcessForm.ATT_PARAMREQUEST);
Resource base = (Resource) request.getAttribute(ProcessForm.ATT_RBASE);
ArrayList<String> baseProps = (ArrayList<String>) request.getAttribute(ProcessForm.ATT_PROPMAP);
HashMap<String, SemanticClass> hmclass = (HashMap<String, SemanticClass>) request.getAttribute(ProcessForm.ATT_CLASSMAP);
HashMap<String, ItemAware> userDefinedVars = (HashMap<String, ItemAware>) request.getAttribute(ProcessForm.ATT_USERVARS);
UserTask task = (UserTask) request.getAttribute(ProcessForm.ATT_TASK);

WebSite site = paramRequest.getWebPage().getWebSite();
User user = paramRequest.getUser();
String lang = "es";
boolean showHeader = base.getAttribute("showHeader") != null;
boolean btnAccept = base.getAttribute("btnAccept") != null;
boolean btnReject = base.getAttribute("btnReject") != null;
boolean btnCancel = base.getAttribute("btnCancel") != null;
boolean btnSave = base.getAttribute("btnSave") != null;
boolean useSign = base.getAttribute("useSign") != null;

if (user != null && user.getLanguage() != null) lang = user.getLanguage();

String admMode = base.getAttribute(ProcessForm.PARAM_ADMMODE, ProcessForm.ADM_MODESIMPLE);
%>
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link type="text/css" rel="stylesheet" href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/fontawesome/font-awesome.css" />
    <link type="text/css" rel="stylesheet" href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/bootstrap/bootstrap.css" />
    <link rel="stylesheet" type="text/css" media="all" href="/swbadmin/js/dojo/dijit/themes/soria/soria.css" />
    <script type="text/javascript">
        var djConfig = {
            parseOnLoad: true,
            isDebug: false
        };
    </script>
    <script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/dojo/dojo/dojo.js" ></script>
    <script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/swb_admin.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
           //Initialize bootstrap tooltips
            if ($("[data-tooltip=tooltip]").length) {
              $("[data-tooltip=tooltip]").tooltip();
            }
            
            //Destroy modals content
            $('#modalDialog').on('hidden.bs.modal', function () {
                $(this).removeData('bs.modal');
            });
        });
        
        function showModal(url) {
            var emsg = "Sorry, there was an error processing the request...";

            var jqxhr = $.get(url, function(data) {
                $("#modalDialog").html(data);
            })
            .fail(function(xhr, status, et) {
                $("#modalDialog").find("#modal-content").html(" "+emsg);
            });
            $("#modalDialog").modal('show');
        }
    </script>
    <style type="text/css">
        .toolbar {
            background-color: #FFF;
            padding-top: 2px;
            padding-bottom: 5px;
            border-bottom: 2px solid lightgray;
            margin-bottom: 15px;
        }

        .workspace {
            background-color: #FFF;
            border: 1px solid lightgray;
            min-height: 600px;
            height: 100%;
            margin-bottom: 10px;
            -webkit-box-shadow: 0px 0px 5px 0px rgba(36,36,36,1);
            -moz-box-shadow: 0px 0px 5px 0px rgba(36,36,36,1);
            box-shadow: 0px 0px 5px 0px rgba(36,36,36,1);
            font-size: 12px;
        }

        .row.no-margins {
            margin: 0px;
        }

        body {
            background-color: #F3F3F3;
        }
    </style>
</head>
<body class="soria">
    <%
    SWBResourceURL addPropsUrl = paramRequest.getRenderUrl().setMode(ProcessForm.MODE_ADDPROPS).setCallMethod(SWBParamRequest.Call_DIRECT);
    SWBResourceURL updMode = paramRequest.getActionUrl().setAction(ProcessForm.ACT_UPDADMINMODE);
    String spanClass = "fa-file-code-o";
    String label = paramRequest.getLocaleString("toolAdvanced");
    if (ProcessForm.ADM_MODESIMPLE.equals(admMode)) {
        updMode.setParameter(ProcessForm.PARAM_ADMMODE, ProcessForm.ADM_MODEADVANCED);
    } else if (ProcessForm.ADM_MODEADVANCED.equals(admMode)) {
        updMode.setParameter(ProcessForm.PARAM_ADMMODE, ProcessForm.ADM_MODESIMPLE);
        spanClass = "fa-list-ul";
        label = paramRequest.getLocaleString("toolSimple");
    }
    SWBResourceURL toggle = paramRequest.getActionUrl().setAction(ProcessForm.ACT_TOGGLEBUTTON);
    %>
    <div class="row no-margins">
        <div class=" toolbar text-center">
            <%
            if (ProcessForm.ADM_MODESIMPLE.equals(admMode)) {
                %>
                <div class="btn-group">
                    <a href="#" onclick="showModal('<%=addPropsUrl%>'); return false;" class="btn btn-sm btn-default" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolAddProp")%>"><span class="fa fa-plus"></span></a>
                    <a href="#" onclick="window.location='<%=toggle%>?btns=showHeader'; return false;" class="btn btn-sm btn-default <%=showHeader?"active":""%>" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolShowHead")%>"><span class="fa fa-header"></span></a>
                </div>
                <div class="btn-group">
                    <a href="#" onclick="window.location='<%=toggle%>?btns=save'; return false;" class="btn btn-sm btn-default <%=btnSave?"active":""%>" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolSaveButton")%>"><span class="fa fa-floppy-o"></span></a>
                    <a href="#" onclick="window.location='<%=toggle%>?btns=accept'; return false;" class="btn btn-sm btn-default <%=btnAccept?"active":""%>" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolCloseButton")%>"><span class="fa fa-check"></span></a>
                    <a href="#" onclick="window.location='<%=toggle%>?btns=reject'; return false;" class="btn btn-sm btn-default <%=btnReject?"active":""%>" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolRejectButton")%>"><span class="fa fa-times"></span></a>
                    <a href="#" onclick="window.location='<%=toggle%>?btns=cancel'; return false;" class="btn btn-sm btn-default <%=btnCancel?"active":""%>" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolBackButton")%>"><span class="fa fa-reply"></span></a>
                </div>
                <div class="btn-group">
                    <a href="#" onclick="window.location='<%=toggle%>?btns=sign'; return false;" class="btn btn-sm btn-default <%=useSign?"active":""%>" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("toolUseSign")%>"><span class="fa fa-lock"></span></a>
                    <a href="#" onclick="window.location='<%=updMode%>';" class="btn btn-sm btn-default" data-tooltip="tooltip" data-placement="bottom" title="<%=label%>"><span class="fa <%=spanClass%>"></span></a>
                </div>
                <%
            } else if (ProcessForm.ADM_MODEADVANCED.equals(admMode)) {
                SWBResourceURL urladd = paramRequest.getActionUrl().setAction(ProcessForm.ACT_UPDATEXML);
                %>
                <div class="btn-group">
                    <a href="#" onclick="if (confirm('<%=paramRequest.getLocaleString("msgXMLReload")%>')) {window.location='<%=urladd%>';} else {return false;}" class="btn btn-sm btn-default" data-tooltip="tooltip" data-placement="bottom" title="<%=paramRequest.getLocaleString("lblGenerateXML")%>"><span class="fa fa-file-code-o fa-fw"></span></a>
                    <a href="#" onclick="window.location='<%=updMode%>';" class="btn btn-sm btn-default" data-tooltip="tooltip" data-placement="bottom" title="<%=label%>"><span class="fa <%=spanClass%>"></span></a>
                </div>
                <%
            }
            %>
        </div>
    </div>
    <div class="row no-margins">
        <div class="col-lg-12">
            <%
            if (ProcessForm.ADM_MODESIMPLE.equals(admMode)) {
            %>
                <div class="row no-margins workspace">
                    <div class="col-lg-12 text-center">
                        <%
                        if (showHeader) {
                            %>
                            <h2><%=task.getTitle()%></h2>
                            <hr>
                            <%
                        } else {
                            %><br><%
                        }
                        if (baseProps != null && !baseProps.isEmpty()) {
                            Iterator<String> props = baseProps.iterator();
                            %>
                            <div class="panel panel-default">
                                <form class="swbform" dojoType="dijit.form.Form">
                                    <div class="panel-body">
                                        <table>
                                            <tbody>
                                                <%
                                                int max = 1;
                                                while (props.hasNext()) {
                                                    String val = props.next();
                                                    HashMap<String, String> map = ProcessForm.getPropertiesMap(val);
                                                    String varName = map.get("varName");
                                                    String fe = map.get("fe");
                                                    String modo = map.get("mode");
                                                    label = map.get("label");
                                                    String propKey = varName + "|" + map.get("propId");

                                                    SemanticProperty sprop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(map.get("propId"));
                                                    if (sprop != null) {
                                                        SemanticClass sclass = hmclass.get(map.get("varName"));
                                                        if (sclass != null) {
                                                            SemanticObject virtual = new SemanticObject(site.getSemanticModel(), sclass);
                                                            SWBFormMgr mgr = new SWBFormMgr(virtual, null, SWBFormMgr.MODE_CREATE);
                                                            mgr.setType(SWBFormMgr.TYPE_DOJO);
                                                            mgr.setLang("es");
                                                            if (map.get("label") == null) {
                                                                if (userDefinedVars.containsKey(propKey)) {
                                                                    map.put("label", userDefinedVars.get(propKey).getDisplayTitle(lang));
                                                                } else {
                                                                    map.put("label", sprop.getDisplayName(lang));
                                                                }
                                                            }
                                                            FormElement ele = mgr.getFormElement(sprop);
                                                            SWBResourceURL urlmove = paramRequest.getActionUrl();
                                                            urlmove.setAction(ProcessForm.ACT_SWAP);
                                                            urlmove.setParameter(ProcessForm.PARAM_PROPIDX, "" + max);
                                                            urlmove.setParameter(ProcessForm.PARAM_DIR, "up");
                                                            %>
                                                            <tr>
                                                                <td>
                                                                    <%
                                                                    if (max != 1) {
                                                                        %>
                                                                        <a href="#" class="btn btn-xs btn-default" onclick="window.location='<%=urlmove%>'; return false;" title="<%=paramRequest.getLocaleString("lblUp")%>"><span class="fa fa-chevron-up"></span></a>
                                                                        <%
                                                                    }
                                                                    if (max < baseProps.size()) {
                                                                        urlmove.setParameter(ProcessForm.PARAM_DIR, "down");
                                                                        %>
                                                                        <a href="#" class="btn btn-xs btn-default" onclick="window.location='<%=urlmove%>'; return false;" title="<%=paramRequest.getLocaleString("lblDown")%>"><span class="fa fa-chevron-down"></span></a>
                                                                        <%
                                                                    }
                                                                    %>
                                                                </td>
                                                                <td width="200px" align="right">
                                                                    <%=ele.renderLabel(request, null, sprop, varName + "." + sprop.getName(), SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, "es", map.get("label"))%>
                                                                </td>
                                                                <%
                                                                SemanticObject sofe = SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(fe);
                                                                if (sofe != null) {
                                                                    FormElement frme = (FormElement) sofe.createGenericInstance();
                                                                    frme.setModel(virtual.getModel());
                                                                    if (modo.equals("view")) {
                                                                        if (sprop.isBoolean()) {
                                                                            virtual.setBooleanProperty(sprop, false);
                                                                        } else if (sprop.isDate() || sprop.isDateTime()) {
                                                                            virtual.setDateProperty(sprop, new Date(System.currentTimeMillis()));
                                                                        } else if (sprop.isDouble() || sprop.isFloat()) {
                                                                            virtual.setDoubleProperty(sprop, 0.0);
                                                                        } else if (sprop.isInt() || sprop.isLong() || sprop.isShort()) {
                                                                            virtual.setIntProperty(sprop, 0);
                                                                        } else if (sprop.isString()) {
                                                                            virtual.setProperty(sprop, "Texto de " + label);
                                                                        } else if (sprop.isObjectProperty()) {
                                                                            SemanticClass range = null;
                                                                            range = sprop.getRangeClass();
                                                                            if (range != null) {
                                                                                Iterator<SemanticObject> instance = SWBPlatform.getSemanticMgr().getOntology().listInstancesOfClass(range);
                                                                                if (instance.hasNext()) {
                                                                                    SemanticObject inst = instance.next();
                                                                                    virtual.setObjectProperty(sprop, inst);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    try {
                                                                        %>
                                                                        <td align="left"><%=frme.renderElement(request, virtual, sprop, SWBFormMgr.TYPE_DOJO, modo, "es")%></td>
                                                                        <%
                                                                    } catch (Exception ex) {
                                                                        ex.printStackTrace();
                                                                    }
                                                                } else {
                                                                    %>
                                                                    <td align="left"><%=mgr.renderElement(request, sprop, modo)%></td>
                                                                    <%
                                                                }

                                                                SWBResourceURL delUrl = paramRequest.getActionUrl().setAction(ProcessForm.ACT_REMOVEPROP);
                                                                SWBResourceURL editUrl = paramRequest.getRenderUrl().setMode(ProcessForm.MODE_EDITPROP).setCallMethod(SWBParamRequest.Call_DIRECT);
                                                                delUrl.setParameter(ProcessForm.PARAM_PROPIDX, String.valueOf(max));
                                                                editUrl.setParameter(ProcessForm.PARAM_PROPIDX, String.valueOf(max));
                                                                editUrl.setAction(ProcessForm.ACT_UPDPROP);
                                                                %>
                                                                <td width="80" align="right">
                                                                    <a href="#" class="btn btn-xs btn-default" title="<%=paramRequest.getLocaleString("lblDelete")%>" onclick="window.location='<%=delUrl%>';return false;"><span class="fa fa-trash-o"></span></a>
                                                                    <a href="#" class="btn btn-xs btn-default" title="<%=paramRequest.getLocaleString("lblEdit")%>" onclick="showDialog('<%=editUrl%>','<%=paramRequest.getLocaleString("lblEdit")%>'); return false;"><span class="fa fa-pencil"></span></a>
                                                                </td>
                                                            </tr>
                                                            <%
                                                        }
                                                        max++;
                                                    }
                                                }
                                                %>
                                            </tbody>
                                        </table>
                                    </div>
                                </form>
                                <%
                                if (btnAccept || btnCancel || btnReject || btnSave) {
                                    SWBResourceURL editUrl = paramRequest.getRenderUrl().setMode(ProcessForm.MODE_EDITPROP).setCallMethod(SWBParamRequest.Call_DIRECT);
                                    editUrl.setAction(ProcessForm.ACT_UPDBTNLABEL);
                                    %>
                                    <div class="panel-footer text-left">
                                        <%
                                        if (btnSave) {
                                            editUrl.setParameter(ProcessForm.PARAM_BTNID , "btnSave");
                                            %>
                                            <button dojoType="dijit.form.Button"><%=base.getAttribute("btnSaveLabel",paramRequest.getLocaleString("btnSaveTask"))%></button><a href="#" class="btn btn-xs btn-default" onclick="showDialog('<%=editUrl%>','<%=paramRequest.getLocaleString("lblEdit")%>'); return false;" title="<%=paramRequest.getLocaleString("lblEdit")%>"><span class="fa fa-pencil"></span></a>
                                            <%
                                        }
                                        if (btnAccept) {
                                            editUrl.setParameter(ProcessForm.PARAM_BTNID , "btnAccept");
                                            %>
                                            <button dojoType="dijit.form.Button"><%=base.getAttribute("btnAcceptLabel",paramRequest.getLocaleString("btnCloseTask"))%></button><a href="#" class="btn btn-xs btn-default" onclick="showDialog('<%=editUrl%>','<%=paramRequest.getLocaleString("lblEdit")%>'); return false;" title="<%=paramRequest.getLocaleString("lblEdit")%>"><span class="fa fa-pencil"></span></a>
                                            <%
                                        }
                                        if (btnReject) {
                                            editUrl.setParameter(ProcessForm.PARAM_BTNID , "btnReject");
                                            %>
                                            <button dojoType="dijit.form.Button"><%=base.getAttribute("btnRejectLabel",paramRequest.getLocaleString("btnRejectTask"))%></button><a href="#" class="btn btn-xs btn-default" onclick="showDialog('<%=editUrl%>','<%=paramRequest.getLocaleString("lblEdit")%>'); return false;" title="<%=paramRequest.getLocaleString("lblEdit")%>"><span class="fa fa-pencil"></span></a>
                                            <%
                                        }
                                        if (btnCancel) {
                                            editUrl.setParameter(ProcessForm.PARAM_BTNID , "btnCancel");
                                            %>
                                            <button dojoType="dijit.form.Button"><%=base.getAttribute("btnCancelLabel",paramRequest.getLocaleString("btnBack"))%></button><a href="#" class="btn btn-xs btn-default" onclick="showDialog('<%=editUrl%>','<%=paramRequest.getLocaleString("lblEdit")%>'); return false;" title="<%=paramRequest.getLocaleString("lblEdit")%>"><span class="fa fa-pencil"></span></a>
                                            <%
                                        }
                                        %>
                                    </div>
                                    <%
                                }
                                %>
                            </div>
                            <%
                        }
                        %>
                    </div>
                </div>
            <%
            } else if (ProcessForm.ADM_MODEADVANCED.equals(admMode)) {
                String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
                String value = SWBUtils.IO.getFileFromPath(basepath + "code.xml");
                %>
                <div class="text-center">
                    <%
                    if (null != value && value.trim().length() > 0) {
                        SWBResourceURL url = paramRequest.getActionUrl().setAction(ProcessForm.ACT_SAVEXML);
                        %>
                        <script type="text/javascript" src="<%=SWBPlatform.getContextPath() + "/swbadmin/js/editarea/edit_area/edit_area_full.js" %>" ></script>
                        <script type="text/javascript" charset="UTF-8">
                            editAreaLoader.init({
                                id: "resource<%=base.getId()%>",
                                language: "<%=lang%>",
                                syntax: "xml",
                                start_highlight: true,
                                toolbar: "save, |, search, go_to_line, |, undo, redo, |, select_font,|, change_smooth_selection, highlight, reset_highlight, |, help",
                                is_multi_files: false,
                                save_callback: "my_save",
                                allow_toggle: false
                            });

                            function my_save(id, content) {
                                var elemento = document.getElementById("hiddencode");
                                elemento.value = content;
                                document.xmledition.submit();
                            }
                        </script>
                        <form name="xmledition" action="<%=url%>" method="post">
                            <input type="hidden" id="hiddencode" name="hiddencode" value=""/>
                            <textarea id="resource<%=base.getId()%>" name="resource<%=base.getId()%>" rows="25" cols="120"><%=value%></textarea>
                        </form>
                        <%
                    }
                %>
                </div>
                <%
            }
            %>
        </div>
    </div>
    <div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-hidden="true"></div>
    <script type="text/javascript" src="/swbadmin/js/bootstrap/bootstrap.js"></script>
</body>