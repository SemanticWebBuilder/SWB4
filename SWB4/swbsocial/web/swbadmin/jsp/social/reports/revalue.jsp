<%-- 
    Document   : revalue
    Created on : 11-jun-2012, 17:31:06
    Author     : carlos.ramos
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="static org.semanticwb.social.resources.reports.PostSummary.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title></title>
    
<link rel="stylesheet" type="text/css" media="all" href="/swbadmin/js/dojo/dijit/themes/soria/soria.css" />
<link rel="stylesheet" type="text/css" media="all" href="/swbadmin/css/swb_portal.css" />
<script type="text/javascript" >
    var djConfig = {
        parseOnLoad: true,
        isDebug: false
    };
</script>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" ></script>
<script type="text/javascript" src="/swbadmin/js/swb.js" ></script>    
    
    <script type="text/javascript">
    <!--
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dijit.form.FilteringSelect");
    /*dojo.require("dijit.form.RadioButton");
    dojo.require("dijit.form.CheckBox");*/

    function enviar() {
        var objd=dijit.byId('rv');
        if(objd.validate()) {
            return true;
        }else {
            alert("Datos incompletos o incorrectos");
        }
        return false;
    }
    -->
    </script>    
  </head>
  <body class="soria">
<div>
 <div>
     <form id="rv" dojoType="dijit.form.Form" class="" action="<%=(paramRequest.getActionUrl().setAction(SWBResourceURL.Action_EDIT))%>" method="post">
   <p class="">
    <label for="fw"><em>*</em>Frases</label>
    <input type="text" name="fw" id="fw" dojoType="dijit.form.ValidationTextBox" value="" required="true" promptMessage="Lista de frases separadas por punto y coma" invalidMessage="Palabra incorrecta" trim="true" />
   </p>
   <p>
       <label for="nv"><em>*</em>Tipo de sentimiento</label>
       <select name="nv" id="nv" dojoType="dijit.form.FilteringSelect" value="0" required="true" promptMessage="cambiar sentimiento">
           <option value="0" selected="selected">Neutro</option>
           <option value="1">Positivo</option>
           <option value="2">Negativo</option>
       </select>
   </p>
   <p>
       <label for="dpth"><em>*</em>Intensidad</label>
       <select name="dpth" id="dpth" dojoType="dijit.form.FilteringSelect" value="0" required="true" promptMessage="cambiar intensidad">
           <option value="0" selected="selected">Media</option>
           <option value="1">Alta</option>
           <option value="2">Baja</option>
       </select>
   </p>
   <p>
    <input type="reset" value="limpiar"/>
    <input type="button" value="salir" onclick="window.close()"/>
    <input type="submit" value="enviar" onclick="return enviar()"/>
   </p>
  </form>
 </div>
</div>
<%
    if(request.getParameter("alertmsg")!=null) {
%>
<script type="text/javascript">
<!--
 dojo.addOnLoad(function(){
   alert('<%=request.getParameter("alertmsg")%>');
 });
 -->
</script>
<%
    }
%>
  </body>
</html>
