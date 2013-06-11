<script type="text/javascript">
    function closeAddSurvey()
    {
        dijit.byId("dialogaddSurvey").hide();
    }
    function saveAddSurvey(forma)
    {
        reloadAdmonSurvey();
        dijit.byId("dialogaddSurvey").hide();
    }
    function saveAndContinueAddSurvey(forma)
    {
        
    }
    
</script>
<%
            String pathLogo = "";
%>
<h1 align="center">Cuestionario</h1>
<form action="" >
    <table>
        <tr>
            <td>
                <img alt="logo"  src="<%=pathLogo%>">
            </td>
            <td>
                <input type="file" name="logo">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>

        </tr>
        <tr>
            <td>
                T&iacute;tulo:
            </td>
            <td>
                <div dojoType="dijit.Editor" id="titleeditor"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>

        </tr>
        <tr>
            <td>
                Presentaci&oacute;n:
            </td>
            <td>
                <div dojoType="dijit.Editor" id="presentacioneditor"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>

        </tr>
        <tr>
            <td>
                Instrucciones de llenado:
            </td>
            <td>
                <div dojoType="dijit.Editor" id="instructionseditor"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="button" value="Cancelar" onclick="closeAddSurvey();">&nbsp;<input type="button" value="Guardar" onclick="saveAddSurvey(this.form)">&nbsp;<input type="button" value="Guardar y Agregar" onclick="saveAndContinueAddSurvey(this.form);">
            </td>
        </tr>
        
    </table>
</form>