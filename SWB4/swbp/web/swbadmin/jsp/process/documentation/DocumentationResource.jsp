<%-- 
    Document   : ProcessDocumentationResource
    Created on : 24/07/2013, 06:41:50 PM
    Author     : carlos.alvarez
--%>

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
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite wsite = (WebSite) paramRequest.getWebPage().getWebSite();
    String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : "";
    String idDocumentation = request.getAttribute("idDocumentation") != null ? request.getAttribute("idDocumentation").toString() : "";
    Documentation documentation = Documentation.ClassMgr.getDocumentation(idDocumentation, wsite);
    ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
    SWBResourceURL urlUpdate = paramRequest.getRenderUrl().setMode(DocumentationResource.MOD_UPDATETEXT).setCallMethod(SWBParamRequest.Call_DIRECT);
%>
<script type="text/javascript" src="/swbadmin/jsp/process/tinymce/tinymce.min.js"></script>
<%
    if (pe instanceof org.semanticwb.process.model.Process) {
        out.println("<h2>Documentación de Proceso " + pe.getTitle() + "</h2>");
    } else {
        out.println("<h2>Documentación de Elemento " + pe.getTitle() + "</h2>");
    }
%>
<form method="post" style="width: 100%;">
    <div class="editable" style="width:100%; height:100%;" id="idDocumentation/<%=idDocumentation%>">
        <%
            if (documentation != null) {
                out.println(documentation.getText() != null ? documentation.getText() : "");
            }%>
    </div>
</form>


<script type="text/javascript">
    tinymce.init({
        selector: "div.editable",
        inline: true,
        language: '<%=paramRequest.getUser().getLanguage()%>',
        theme: "modern",
        force_br_newlines: false,
        force_p_newlines: false,
        forced_root_block: '',
        plugins: [
            "save advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "table contextmenu directionality emoticons template textcolor paste fullpage textcolor"
        ],
        save_onsavecallback: function(ed) {
            //alert(ed.getContent());
            submitUrl('<%=urlUpdate%>', ed.getContent(), ed.id);
        },
        toolbar: "save",
        save_enablewhendirty: true,
        toolbar1: "save bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
        toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor code | inserttime preview | forecolor backcolor",
        toolbar3: "table | hr removeformat | subscript superscript | charmap | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",
        menubar: false,
        toolbar_items_size: 'big',
        style_formats: [
            {title: 'Bold text', inline: 'b'},
            {title: 'Red text', inline: 'span', styles: {color: '#ff0000'}},
            {title: 'Red header', block: 'h1', styles: {color: '#ff0000'}},
            {title: 'Example 1', inline: 'span', classes: 'example1'},
            {title: 'Example 2', inline: 'span', classes: 'example2'},
            {title: 'Table styles'},
            {title: 'Table row 1', selector: 'tr', classes: 'tablerow1'}
        ]
    });
    function submitUrl(url, data, id) {
        dojo.xhrPost({
            url: url,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            content: {txt: data.toString().trim(), idDocumentation: id.replace("idDocumentation/", ""), suri: '<%=suri%>'},
            load: function(response, ioArgs)
            {
                alert('Se guardaron datos');
                return response;
            },
            error: function(response, ioArgs) {
                //console.log('error');
                return response;
            },
            handleAs: "text"
        });
    }
</script>
<%
    if (pe instanceof org.semanticwb.process.model.Process) {
        SemanticObject semObj = SemanticObject.createSemanticObject(suri);
        org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) semObj.createGenericInstance();
        if (process != null) {
            Iterator<GraphicalElement> iterator = process.listAllContaineds();
            while (iterator.hasNext()) {
                GraphicalElement ge = iterator.next();
                out.println("<h3>Elemento ::: " + ge.getTitle() + " :::</h3>");
                Documentation doc = null;
                if (ge.listDocumentations().hasNext()) {
                    doc = ge.listDocumentations().next();
                } else {
                    doc = Documentation.ClassMgr.createDocumentation(paramRequest.getWebPage().getWebSite());
                    doc.setText("Aquí la documentación");
                    doc.setTextFormat("text/html");
                    ge.addDocumentation(doc);
                }
%>
<form method="post">
    <div class="editable" style="width:100%; " id="idDocumentation/<%=doc.getId()%>">
        <%
            if (doc != null) {
                out.println(doc.getText() != null ? doc.getText() : "");
            }%>
    </div>
</form>
<%
            }
        }
    }
%>