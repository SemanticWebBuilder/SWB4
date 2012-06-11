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
    dojo.require("dijit.form.RadioButton");
    dojo.require("dijit.form.CheckBox");

    function enviar() {
        var objd=dijit.byId('form1ud');
        if(objd.validate())
        {
            return true;
        }else {
            alert("Datos incompletos o erroneos");
        }
        return false;
    }
    -->
    </script>    
  </head>
  <body class="soria">
<div>
 <div>
  <form id="rv" dojoType="dijit.form.Form" class="" action="<%=(paramRequest.getActionUrl())%>" method="post">
   <p class="icv-3col">
    <label for="fw"><b>*</b>Palabras</label>
    <input type="text" name="fw" id="fw" dojoType="dijit.form.ValidationTextBox" value="" required="true" promptMessage="Lista de palabras separadas por punto y coma" invalidMessage="Palabra incorrecta" trim="true" />
   </p>
   
   <p>
    <input type="reset" value="limpiar"/>
    <input type="submit" value="enviar" onclick="return enviar()"/>
   </p>
  </form>
 </div>
</div>
  </body>
</html>
