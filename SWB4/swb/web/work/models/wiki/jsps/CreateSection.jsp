<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<script type="text/javascript">
  dojo.require("dijit.Dialog");
  dojo.require("dijit.form.TextBox");  
  dojo.require("dijit.form.Button");
<%
   if("true".equals(request.getParameter("atpe")))
   {
%>
  alert("Seccion creada previamente");
<%
   }
   String tpname=request.getParameter("atpe");
   if(tpname==null)tpname="";
%>
</script>
<li><a href="<%=paramRequest.getTopic().getUrl()%>/_rid/5/_mod/edit">Editar Contenido</a></li>
<li><a href="#" onclick="dijit.byId('tooltipDlg').show()">Agregar Sección</a></li>
<div dojoType="dijit.Dialog" style="display:none;" id="tooltipDlg" title="Agregar Sección">
<!--    execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">-->
  <form id="addtpf" action="<%=paramRequest.getActionUrl()%>">
    <table>
      <tr>
        <td><label for="user">Nombre:</label></td>
        <td><input dojoType=dijit.form.TextBox type="text" name="tpname" id="tpname" value="<%%>"></td>
      </tr>
<!--
      <tr>
        <td><label for="pwd">Id:</label></td>
        <td><input dojoType=dijit.form.TextBox type="text" name="tpid" id="tpid"></td>
      </tr>
-->
      <tr>
        <td colspan="2" align="center">
          <button dojoType=dijit.form.Button type="submit" name="submit">Agregar</button>
        </td>
      </tr>
    </table>
  </form>
</div>
