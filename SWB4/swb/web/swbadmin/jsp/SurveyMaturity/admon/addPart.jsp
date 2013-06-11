<script type="text/javascript">
    function closeDialogAddPart()
    {
        dijit.byId("dialogAdmonParte").hide();
    }
    function saveDialogAddPart(forma)
    {
        
        dojo.ready(enviaforma(forma.id));
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
        /*var parametros={};
        parametros['tituloparteditor']=tituloparteditor;
        parametros['descriptionparteditor']=descriptionparteditor;
        post(parametros);*/
        forma.submit();
        reloadAdmonParte();
        
        dijit.byId("dialogAdmonParte").hide();
    }
    function saveAndContinueDialogAddPart()
    {
        var htmltitulo=dojo.byId('tituloparteditor');
        forma.submit();
    }
   
</script>
<h1 align="center">Parte</h1>

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
            <input type="button" value="Cancelar" onclick="closeDialogAddPart(this.form);">&nbsp;<input type="button" value="Guardar" onclick="saveDialogAddPart()">&nbsp;<input type="button" value="Guardar y Agregar" onclick="saveAndContinueDialogAddPart();">
        </td>

    </tr>
</table>
