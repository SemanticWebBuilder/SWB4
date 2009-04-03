<%@page import="org.semanticwb.portal.resources.cp.*" %>
<%@page import="org.semanticwb.portal.*" %>
<%@page import="org.semanticwb.model.*" %>
<%-- 
    Document   : testControl
    Created on : 31/03/2009, 07:39:45 PM
    Author     : javier.solis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            @import "/swb/swbadmin/js/dojo/dijit/themes/soria/soria.css";
            @import "/swb/swbadmin/css/swb.css";
        </style>

        <script type="text/javascript" src="/swb/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: true" ></script>
        <script type="text/javascript" charset="utf-8" src="/swb/swbadmin/js/swb.js" ></script>
    </head>
    <body class="soria">
        <h1>Hello World!</h1>
<%
        WebSite site=SWBContext.getGlobalWebSite();
        Image img=Image.getImage("2", site);
        //img.setImageAlt("Imagen del 2");

        SWBFormMgr fm=new SWBFormMgr(img.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
        fm.setLang("es");
        fm.processForm(request);
        String str=fm.renderForm(request);
        out.println(str);
%>
    </body>
</html>
