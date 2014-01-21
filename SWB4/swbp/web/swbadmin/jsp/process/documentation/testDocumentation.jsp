<%-- 
    Document   : TestDocumentation
    Created on : 24/10/2013, 12:36:03 PM
    Author     : carlos.alvarez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript" src="/swbadmin/jsp/process/utils/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
    tinymce.init({
        selector: "textarea",
        theme: "modern",
        plugins: [
            "advlist autolink lists link image charmap print preview hr anchor pagebreak",
            "searchreplace wordcount visualblocks visualchars code fullscreen",
            "insertdatetime media nonbreaking save table contextmenu directionality",
            "emoticons template paste textcolor"
        ],
        toolbar1: "fullpage save insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
        toolbar2: "print preview media | forecolor backcolor emoticons",
        image_advtab: true,
        templates: [
            {title: 'Test template 1', content: 'Test 1'},
            {title: 'Test template 2', content: 'Test 2'}
        ]
    });
</script>

<form method="post">
    <textarea name="ss" style="width:100%;"></textarea>
</form>
