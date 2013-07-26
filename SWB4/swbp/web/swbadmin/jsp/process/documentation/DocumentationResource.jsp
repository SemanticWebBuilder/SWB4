<%-- 
    Document   : ProcessDocumentationResource
    Created on : 24/07/2013, 06:41:50 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.process.model.Documentation"%>
<%@page import="org.semanticwb.process.resources.DocumentationResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
    WebSite wsite = (WebSite) paramRequest.getWebPage().getWebSite();
    String suri = request.getAttribute("suri") != null ? request.getAttribute("suri").toString() : "";
    String idDocumentation = request.getAttribute("idDocumentation") != null ? request.getAttribute("idDocumentation").toString() : "";
    Documentation documentation = Documentation.ClassMgr.getDocumentation(idDocumentation, wsite);
    String text = documentation.getText() != null ? documentation.getText() : "";
    SWBResourceURL urlAction = paramRequest.getActionUrl().setAction(DocumentationResource.ACT_UPDATETEXT);
%>
<script type="text/javascript" src="/swbadmin/jsp/process/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
    tinymce.init({
        selector: "textarea",
        language : 'es',
        theme: "modern",
        plugins: [
            "save advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "table contextmenu directionality emoticons template textcolor paste fullpage textcolor"
        ],
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
        ],
        templates: [
            {title: 'Test template 1', content: 'Test 1'},
            {title: 'Test template 2', content: 'Test 2'}
        ]
    });</script>
<form method="post" action="<%=urlAction%>">
    <textarea name="<%=DocumentationResource.PARAM_TEXT%>" id="<%=DocumentationResource.PARAM_TEXT%>" style="width:100%; height: 365px;">
        <%out.print(text);%></textarea>
    <input type="hidden" name="suri" value="<%=suri%>"/>
    <input type="hidden" name="idDocumentation" value="<%=idDocumentation%>"/>
</form>