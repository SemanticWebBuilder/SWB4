<script type="text/javascript">
    function closeDialogAddSection()
    {
        dijit.byId("dialogAdmonSection").hide();
    }
    function saveDialogAddSection(forma)
    {
        reloadAdmonSection();
        dijit.byId("dialogAdmonSection").hide();
    }
    function saveAndContinueDialogAddSection(forma)
    {
        
    }
    
</script>
<h1 align="center">Secci&oacute;n</h1>
<form action="" >
    <table>
        <tr>
            <td>
                T&iacute;tulo:
            </td>
            <td>
                <div dojoType="dijit.Editor" id="titulosectioneditor"></div>
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
                <div dojoType="dijit.Editor" id="descriptionsectioneditor"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="button" value="Cancelar" onclick="closeDialogAddSection();">&nbsp;<input type="button" value="Guardar" onclick="saveDialogAddSection(this.form)">&nbsp;<input type="button" value="Guardar y Agregar" onclick="saveAndContinueDialogAddSection(this.form);">
            </td>

        </tr>
    </table>
</form>