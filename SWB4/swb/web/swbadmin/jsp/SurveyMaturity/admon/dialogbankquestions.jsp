<script type="text/javascript">
    function closeBankQuestions()
    {
        dijit.byId("dialogaddbankQuestions").hide();
    }
    function saveBankQuestions(forma)
    {
        dijit.byId("dialogaddbankQuestions").hide();
    }
</script>
<h1>Preguntas disponibles</h1><br>
<table width="100">
    <th>
        Preguntas disponibles
    </th>
    <th>
        Acci&oacute;n
    </th>
    <tr>
        <td colspan="2" align="center">
            <a href=""><<</a> &nbsp; <input type="text" size="3" maxlength="3"> &nbsp; <a href="">>></a>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <input type="button" value="Cancelar" onclick="closeBankQuestions();">&nbsp;<input type="button" value="Agregar" onclick="saveBankQuestions(this.form);">
        </td>
    </tr>
</table>