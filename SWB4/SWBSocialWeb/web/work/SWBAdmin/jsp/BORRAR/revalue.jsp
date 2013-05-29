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
<%@page import="static org.semanticwb.social.admin.resources.reports.PostSummary.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title></title>
<style type="text/css">
            @import "/swbadmin/js/dojo/dojo/resources/dojo.css";
            @import "/swbadmin/js/dojo/dijit/themes/soria/soria.css";
            @import "/swbadmin/css/swb.css";
            @import "/swbadmin/js/dojo/dojox/grid/resources/soriaGrid.css";
            @import "/swbadmin/js/dojo/dojox/grid/resources/Grid.css";


            html, body, #main{
                overflow: auto;
            }
</style>    
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

    dojo.provide("ValidationTextarea");
    dojo.require("dijit.form.SimpleTextarea");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require('dojo.data.ItemFileReadStore');
    dojo.declare(
        "ValidationTextarea",
        [dijit.form.ValidationTextBox, dijit.form.SimpleTextarea],
        {
            invalidMessage: "Este dato es requerido",
            promptMessage: "Ingresa",
            postCreate: function() {
                this.inherited(arguments);
            },
            validate: function() {
                if(arguments.length==0)
                    return this.validate(false);
                return this.inherited(arguments);
            },
            onFocus: function() {
                if(!this.isValid()) {
                    this.displayMessage(this.getErrorMessage());
                }
            },
            onBlur: function() {
                this.validate(false);
            }
         }
    );

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
    <div style="padding: 15px;">
     <form id="rv" dojoType="dijit.form.Form" class="" action="<%=(paramRequest.getActionUrl().setAction(SWBResourceURL.Action_EDIT))%>" method="post">
<table border="0" cellspacing="5">
<tr align="center"><th colspan="3" align="center">Reclasificar frases</th></tr>
<tr>
<td>
    <label for="fw"><em>*</em>Frases</label>
</td>
<td colspan="2">
    <!--input type="text" name="fw" id="fw" dojoType="dijit.form.ValidationTextBox" value="" required="true" promptMessage="Lista de frases separadas por punto y coma" invalidMessage="Palabra incorrecta" trim="true" /-->
    <textarea name="fw" id="fw" rows="2" cols="28" dojoType="ValidationTextarea" required="true" promptMessage="Lista de frases separadas por punto y coma" invalidMessage="Frase;Frase" trim="true"></textarea>
</td>
</tr>
<tr>
<td>
       <label for="nv"><em>*</em>Tipo de sentimiento</label>
</td>
<td colspan="2">
       <select name="nv" id="nv" dojoType="dijit.form.FilteringSelect" value="0" required="true" promptMessage="cambiar sentimiento">
           <option value="0" selected="selected">Neutro</option>
           <option value="1">Positivo</option>
           <option value="2">Negativo</option>
       </select>
</td>
</tr>
<tr>
<td>
       <label for="dpth"><em>*</em>Intensidad</label>
</td>
<td colspan="2">
       <select name="dpth" id="dpth" dojoType="dijit.form.FilteringSelect" value="0" required="true" promptMessage="cambiar intensidad">
           <option value="0" selected="selected">Media</option>
           <option value="1">Alta</option>
           <option value="2">Baja</option>
       </select>
</td>
</tr>
<tr><td colspan="3">&nbsp;</td></tr>
<tr align="center">
<td>
    <input type="reset" value="limpiar"/>
</td>
<td>
    <input type="button" value="salir" onclick="window.close()"/>
</td>
<td>
    <input type="submit" value="enviar" onclick="return enviar()"/>
</td>
</tr>
</table>   
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
