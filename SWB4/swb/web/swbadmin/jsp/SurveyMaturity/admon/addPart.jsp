<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    urlAction.setCallMethod(SWBResourceURL.Call_DIRECT);
    urlAction.setAction("addPart");

    SWBResourceURL render = paramRequest.getRenderUrl();
    
%>
<script type="text/javascript">
    function closeDialogAddPart()
    {
        
        dijit.byId("dialogAdmonParte").hide();
    }
    function saveDialogAddPart(forma)
    {
        
        
        try
        {
            
            
            
            
            var tituloparteditor=getValueEditor('tituloparteditor');
            if(tituloparteditor.isEmpty())
            {
                alert('Indique el título de la parte del cuestionario');
                dijit.byId('tituloparteditor').focus();
                return;
            }
            var descriptionparteditor=getValueEditor('descriptionparteditor');
            if(descriptionparteditor.isEmpty())
            {
                alert('Indique la descripción de la parte del cuestionario');
                dijit.byId('descriptionparteditor').focus();
                return;
            }
            forma.tituloparte.value=tituloparteditor;
            forma.descriptionparte.value=descriptionparteditor;
            sendform(forma.id,reloadAdmonParte);
            
            
            dijit.byId("dialogAdmonParte").hide();
        }
        catch(err)
        {
            alert(err.message);
        }
    }
    function saveAndContinueDialogAddPart(forma)
    {
        var htmltitulo=dojo.byId('tituloparteditor');
        forma.submit();
    }
   
</script>
<h1 align="center">Parte</h1>
<form id="frmAddPart" action="<%=urlAction%>">
    <input type="hidden" name="tituloparte">
    <input type="hidden" name="descriptionparte">
    <table>
        <tr>
            <td>
                T&iacute;tulo:
            </td>
            <td>
                <div dojoType="dijit.Editor" id="tituloparteditor"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>

        </tr>
        <tr>
            <td>
                Descripci&oacute;n:
            </td>
            <td>
                <div dojoType="dijit.Editor" id="descriptionparteditor"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="button" value="Cancelar" onclick="closeDialogAddPart();">&nbsp;<input type="button" value="Guardar" onclick="saveDialogAddPart(this.form)">&nbsp;<input type="button" value="Guardar y Agregar" onclick="saveAndContinueDialogAddPart(this.form);">
            </td>

        </tr>
    </table>
</form>
