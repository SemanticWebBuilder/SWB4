<%-- 
    Document   : ProcessDocumentationResource
    Created on : 24/07/2013, 06:41:50 PM
    Author     : carlos.alvarez
--%>

<%@page import="java.lang.reflect.Field"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.semanticwb.util.JarFile"%>
<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.zip.ZipFile"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.process.model.DataObject"%>
<%@page import="org.semanticwb.process.model.SequenceFlow"%>
<%@page import="org.semanticwb.process.model.ConnectionObject"%>
<%@page import="org.semanticwb.process.model.Containerable"%>
<%@page import="org.semanticwb.model.SWBComparator"%>
<%@page import="org.semanticwb.process.model.Event"%>
<%@page import="org.semanticwb.process.model.Activity"%>
<%@page import="org.semanticwb.process.model.Lane"%>
<%@page import="org.semanticwb.process.model.Gateway"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.process.model.GraphicalElement"%>
<%@page import="org.semanticwb.process.model.FlowNode"%>
<%@page import="org.semanticwb.process.model.ProcessElement"%>
<%@page import="org.semanticwb.process.model.FlowNodeInstance"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.Documentation"%>
<%@page import="org.semanticwb.process.resources.DocumentationResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
<script type="text/javascript">
    var djConfig = {
        parseOnLoad: true,
        isDebug: false
    };
</script>
<link rel="stylesheet" href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/formsBuilder/css/formsBuilder.css" type="text/css"/>
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/commons/css/swbp.css" rel="stylesheet">
<!--IMPORT BOOTSTRAP-->
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="<%=SWBPlatform.getContextPath()%>/swbadmin/css/fontawesome/font-awesome.css" rel="stylesheet">
<!--link href="<%//=SWBPlatform.getContextPath()%>/swbadmin/jsp/process/utils/bootstrap/css/swbp2.css" rel="stylesheet"-->
<script type="text/javascript" src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/jquery/jquery.js"></script>
<script src="<%=SWBPlatform.getContextPath()%>/swbadmin/js/bootstrap/bootstrap.js"></script>
<script type='text/javascript'> //Activate tooltips
    $(document).ready(function() {
        if ($("[data-toggle=tooltip]").length) {
            $("[data-toggle=tooltip]").tooltip();
        }
        $('body').off('.data-api');
    });
</script>
<!-- END IMPORT BOOTSTRAP -->
<div id="workspace">
    <div class="col-lg-12 swbp-content" role="main">
        <%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : "";

            String uridocpro = request.getAttribute("uridocpro") != null ? request.getAttribute("uridocpro").toString() : "";
            Documentation documentation = (Documentation) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridocpro);
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            SWBResourceURL urlAction = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_ADD);
            SWBResourceURL urlGenerate = paramRequest.getRenderUrl().setMode("viewDocumentation");
            urlGenerate.setParameter("suri", suri);
            String text = paramRequest.getLocaleString("hereDoc");
            String aux = text;
            String lang = "es";
            String suriProcess = "";
            ConnectionObject conObj = null;
            GraphicalElement graEle = null;
            Containerable container = null;
            if (paramRequest.getUser() != null && paramRequest.getUser().isSigned()) {
        %>
        <script type="text/javascript" src="<%=SWBPlatform.getContextPath() + "/swbadmin/jsp/process/utils/tinymce/tinymce.min.js"%>"></script>
        <%
            if (pe instanceof org.semanticwb.process.model.Process
                    || pe instanceof Lane
                    || pe instanceof Activity
                    || pe instanceof Gateway
                    || pe instanceof ConnectionObject
                    || pe instanceof DataObject) {
        %><div class="panel panel-default"><%
            if (pe instanceof org.semanticwb.process.model.Process) {
            %>
            <div class="panel-heading">
                <table style="width: 100%;"><tr><td>
                            <div class="panel-title"><strong><%=paramRequest.getLocaleString("docFromPro") + " " + pe.getTitle()%></strong></div>
                        </td><td style="text-align: right;">
                            <a class="btn btn-default btn-sm" onclick="window.location = '<%=urlGenerate%>'" data-placement="bottom" data-toggle="tooltip" data-original-title="<%=paramRequest.getLocaleString("view")%>">
                                <i class="fa fa-file-text-o"></i>
                            </a>
                        </td></tr></table>
            </div>
            <%                } else {
                    out.print("<div class=\"panel-heading\"><div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("docFromSub") + " " + pe.getTitle() + "</strong></div> </div>");
                    if (pe instanceof GraphicalElement) {
                        graEle = (GraphicalElement) pe;
                    }
                    if (pe instanceof ConnectionObject) {
                        conObj = (ConnectionObject) pe;
                        graEle = (GraphicalElement) conObj.getSource();
                    }
                    container = graEle.getContainer();
                    while (container != null) {
                        if (container instanceof GraphicalElement) {
                            container = ((GraphicalElement) container).getContainer();
                        } else {
                            suriProcess = ((org.semanticwb.process.model.Process) container).getURI();
                            container = null;
                        }
                    }
                }
            %><div class="panel-body">
                <form method="post" style="width: 100%;">
                    <!--div class="editable" style="width:100%; height:100%;" id="idDocumentation/<%//idDocumentation%>"-->
                    <div class="editable" style="width:100%; height:100%;" id="<%=documentation.getURI()%>">
                        <%
                            if (documentation != null) {
                                if (documentation.getText().trim().length() > 0) {
                                    text = documentation.getText();
                                } else {
                                    text = aux;
                                }
                                out.println(text);
                                
                            }%>
                    </div>
                    <input type="hidden" id="doc<%=documentation.getURI()%>" value="<%=pe.getTitle()%>"/>
                </form>
            </div>
            <%
                if (pe instanceof org.semanticwb.process.model.Process) {
                    SemanticObject semObj = SemanticObject.createSemanticObject(suri);
                    org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) semObj.createGenericInstance();
                    if (process != null) {
                        Iterator<GraphicalElement> iterator = process.listAllContaineds();
                        ArrayList lane = new ArrayList();
                        ArrayList activity = new ArrayList();
                        ArrayList gateway = new ArrayList();
                        ArrayList event = new ArrayList();
                        ArrayList data = new ArrayList();
                        GraphicalElement ge = null;
                        Documentation doc = null;
                        while (iterator.hasNext()) {
                            ge = iterator.next();
                            ge.removeAllDocumentation();
                            if (ge instanceof Lane) {
                                lane.add(ge);
                            }
                            if (ge instanceof Activity) {
                                activity.add(ge);
                            }
                            if (ge instanceof Gateway) {
                                gateway.add(ge);
                            }
                            if (ge instanceof Event) {
                                event.add(ge);
                            }
                            if (ge instanceof DataObject) {
                                data.add(ge);
                            }
                            if (!ge.listDocumentations().hasNext()) {
                                doc = Documentation.ClassMgr.createDocumentation(paramRequest.getWebPage().getWebSite());
                                doc.setText(text);
                                doc.setTextFormat("text/html");
                                ge.addDocumentation(doc);
                            }
                        }
            %></div><%
                //Lane
                iterator = SWBComparator.sortByDisplayName(lane.iterator(), paramRequest.getUser().getLanguage());
                if (lane.size() > 0) {
            %><div class="panel panel-default"><%
                out.print("<div class=\"panel-heading\"><div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("lane") + "</strong></div></div>");
            %><div class="panel-body"><%
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    doc = ge.listDocumentations().next();
                    out.println("<h4> " + ge.getTitle() + " </h4>");
                    out.println("<form method=\"post\">");
                    out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"" + doc.getURI() + "\">");
                    text = doc.getText();
                    out.println(text);
                    text = "";
                    out.println("</div>");
                    out.println("<input type=\"hidden\" id=\"doc" + doc.getURI() + "\" value=\"" + ge.getTitle() + "\"/>");
                    out.println("</form>");
                }%></div></div><%}
                    //Activity
                    if (activity.size() > 0) {
                %><div class="panel panel-default"><%
                    out.print("<div class=\"panel-heading\"><div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("activity") + "</strong></div></div>");
            %><div class="panel-body"><%
                iterator = SWBComparator.sortByDisplayName(activity.iterator(), paramRequest.getUser().getLanguage());
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    doc = ge.listDocumentations().next();
                    out.println("<h4> " +ge.getTitle() + " </h4>");
                    out.println("<form method=\"post\">");
                    out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"" + doc.getURI() + "\">");
                    if (doc.getText().trim().length() > 0) {
                        text = doc.getText();
                    } else {
                        text = aux;
                    }
                    out.println(text);
                    out.println("</div>");
                    out.println("<input type=\"hidden\" id=\"doc" + doc.getURI() + "\" value=\"" + ge.getTitle() + "\"/>");
                    out.println("</form>");
                }%></div></div><%
                    }
                    //Gateway
                    if (gateway.size() > 0) {
                %><div class="panel panel-default"><%
                    out.print("<div class=\"panel-heading\"><div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("gateway") + "</strong></div></div>");
            %><div class="panel-body"><%
                iterator = SWBComparator.sortByDisplayName(gateway.iterator(), paramRequest.getUser().getLanguage());
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    //Connections
                    Iterator<ConnectionObject> itConObj = SWBComparator.sortByDisplayName(((Gateway) ge).listOutputConnectionObjects(), paramRequest.getUser().getLanguage());
                    while (itConObj.hasNext()) {
                        ConnectionObject connectionObj = itConObj.next();
                        if (connectionObj instanceof SequenceFlow) {
                            if (connectionObj.getDocumentation() == null) {
                                doc = Documentation.ClassMgr.createDocumentation(paramRequest.getWebPage().getWebSite());
                                doc.setText(text);
                                doc.setTextFormat("text/html");
                                connectionObj.addDocumentation(doc);
                            } else {
                                doc = connectionObj.getDocumentation();
                            }
                            out.println("<h4> " + connectionObj.getTitle() + " </h4>");
                            out.println("<form method=\"post\">");
                            out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"" + doc.getURI() + "\">");
                            if (doc.getText().trim().length() > 0) {
                                text = doc.getText();
                            } else {
                                text = aux;
                            }
                            out.println(text);
                            out.println("</div>");
                            out.println("<input type=\"hidden\" id=\"doc" + doc.getURI() + "\" value=\"" + connectionObj.getTitle() + "\"/>");
                            out.println("</form>");
                        }
                    }
                    doc = ge.listDocumentations().next();
                    out.println("<h4> " + ge.getTitle() + " </h4>");
                    out.println("<form method=\"post\">");
                    out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"" + doc.getURI() + "\">");
                    if (doc.getText().trim().length() > 0) {
                        text = doc.getText();
                    } else {
                        text = aux;
                    }
                    out.println(text);
                    out.println("</div>");
                    out.println("<input type=\"hidden\" id=\"doc" + doc.getURI() + "\" name=\"pe/" + doc.getId() + "\" value=\"" + ge.getTitle() + "\"/>");
                    out.println("</form>");
                }
                %></div></div><%
                    }
                    //Event
                    if (event.size() > 0) {
                %><div class="panel panel-default"><%
                    out.print("<div class=\"panel-heading\"><div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("event") + "</strong></div></div>");
            %><div class="panel-body"><%
                iterator = SWBComparator.sortByDisplayName(event.iterator(), paramRequest.getUser().getLanguage());
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    doc = ge.listDocumentations().next();
                    out.println("<h4> " + ge.getTitle() + " </h4>");
                    out.println("<form method=\"post\">");
                    out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"" + doc.getURI() + "\">");
                    if (doc.getText().trim().length() > 0) {
                        text = doc.getText();
                    } else {
                        text = aux;
                    }
                    out.println(text);
                    out.println("</div>");
                    out.println("<input type=\"hidden\" id=\"doc" + doc.getURI() + "\" value=\"" + ge.getTitle() + "\"/>");
                    out.println("</form>");
                }%></div></div><%
                    }
                    //Data
                    if (data.size() > 0) {
                %><div class="panel panel-default"><%
                    out.print("<div class=\"panel-heading\"><div class=\"panel-title\"><strong>" + paramRequest.getLocaleString("data") + "</strong></div></div>");
            %><div class="panel-body"><%
                iterator = SWBComparator.sortByDisplayName(data.iterator(), paramRequest.getUser().getLanguage());
                while (iterator.hasNext()) {
                    ge = iterator.next();
                    doc = ge.listDocumentations().next();
                    out.println("<h4> " + ge.getTitle() + " </h4>");
                    out.println("<form method=\"post\">");
                    //out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"idDocumentation/" + doc.getId() + "\">");
                    out.println("<div class=\"editable\" style=\"width:100%; border: 2px;\" id=\"" + doc.getURI() + "\">");
                    if (doc.getText().trim().length() > 0) {
                        text = doc.getText();
                    } else {
                        text = aux;
                    }
                    out.println(text);
                    out.println("</div>");
                    out.println("<input type=\"hidden\" id=\"doc" + doc.getURI() + "\" value=\"" + ge.getTitle() + "\"/>");
                    out.println("</form>");
                                }%></div></div><%}
                                                }
                                            }
                %>
        <script type="text/javascript">
    var suriProcess = "<%=suriProcess%>";
    tinymce.init({
        selector: "div.editable",
        inline: true,
        language: '<%=paramRequest.getUser().getLanguage()%>',
        theme_advanced_toolbar_location: "bottom",
        theme: "modern",
        force_br_newlines: false,
        force_p_newlines: false,
        forced_root_block: '',
        plugins: [
            "fullpage save advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "directionality emoticons template textcolor paste fullpage textcolor table contextmenu"
        ],
        save_onsavecallback: function(ed) {
            var title = document.getElementById("doc"+ed.id).value;
            submitUrl('<%=urlAction%>', ed.getContent(), ed.id, title);
        },
        toolbar: "save",
        save_enablewhendirty: true,
        toolbar1: "save bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
        toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor code | inserttime preview | forecolor backcolor",
        toolbar3: "thr removeformat | subscript superscript | charmap | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",
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

    function submitUrl(url, data, uridoc, title) {
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            content: {txt: data.toString().trim(), uridoc: uridoc, suri: '<%=suri%>'},
            load: function(response, ioArgs)
            {
                parent.showStatus('<%=paramRequest.getLocaleString("save")%>: ' + title, '#12345');
                parent.reloadTab('<%=suri%>');
                return response;
            },
            error: function(response, ioArgs) {
                parent.showError("<%=paramRequest.getLocaleString("error")%>");
                return response;
            },
            handleAs: "text"
        });
    }
    function generateHtml(url) {
        dojo.xhrPost({url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            content: {suri: '<%=suri%>'},
            load: function(response, ioArgs)
            {
                return response;
            },
            error: function(response, ioArgs) {
                return response;
            },
            handleAs: "text"
        });
    }
        </script>
        <%} else {
                    out.println("<h1>" + paramRequest.getLocaleLogString("noDocumentation") + "</h1>");
                }
            }%>
    </div>
</div>
