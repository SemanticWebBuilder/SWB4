<script type="text/javascript">
    function closeDialogAddSubQuestion()
    {
        dijit.byId("dialogaddSubquestions").hide();
    }
    function saveDialogAddSubQuestion()
    {
        dijit.byId("dialogaddSubquestions").hide();
    }
</script>
<h1 align="center">SubPregunta</h1><br>
<table>
    <tr>
        <td>
            Texto de subpregunta:
        </td>
        <td>
            <textarea cols="80" rows="50" name="titulo" ></textarea>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <input type="button" value="Selecionar de banco de preguntas">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <input type="button" value="Cancelar" onclick="closeDialogAddSubQuestion();">
            <input type="button" value="Guardar" onclick="saveDialogAddSubQuestion();">
        </td>
    </tr>
</table>