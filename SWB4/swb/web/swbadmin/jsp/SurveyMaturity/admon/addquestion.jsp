<script type="text/javascript">
    function closeAddQuestion()
    {
        dijit.byId("dialogaddCuestion").hide();
    }
    function saveAndContinueQuestion(forma)
    {
        
    }
    function saveAddQuestion(forma)
    {
        // validaciones
        reloadAdmonQuestion();
        dijit.byId("dialogaddCuestion").hide();
        
    }
    function addSubPreguntas()
    {
        dijit.byId("dialogaddSubquestions").show();
    }
    function addRespuestas()
    {
        dijit.byId("dialogaddAnswer").show();
    }
    
    function showBankQuestions()
    {
        dijit.byId("dialogaddbankQuestions").show();
    }
</script>
<title>Agregar Pregunta</title>
<h1 align="center">Pregunta</h1>
<table width="100%">
    <tr>
        <td>
            Cuestionario
        </td>
        <td>
            &nbsp;
        </td>
        <td>
            Parte
        </td>
        <td>
            &nbsp;
        </td>
        <td>
            Secci&oacute;n
        </td>
        <td>
            &nbsp;
        </td>
    </tr>
</table>
<br>
<table>
    <tr>
        <td>
            Texto de la pregunta:
        </td>
        <td>
            <div dojoType="dijit.Editor" id="texteditor">
        </div>
            <!--<textarea cols="80" rows="10" name="textquestion"></textarea>-->
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <input type="button" name="bank" value="Seleccionar del banco de preguntas" onclick="showBankQuestions();">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td>
            Subpreguntas:
        </td>
        <td>
            <table width="100%">
                <th>Subpregunta</th>
                <th>Acci&oacute;n</th>
                <tr>
                    <td>

                    </td>
                    <td>

                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <input type="button" value="Agregar SubPreguntas" onclick="addSubPreguntas();">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td>
            Respuestas:
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <input type="button" value="Agregar Respuesta" onclick="addRespuestas();">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td>Descripci&oacute;n:</td>
        <td>
            <div dojoType="dijit.Editor" id="descriptioneditor"></div>
            <!--<textarea rows="10"  name="description" cols="80"></textarea>-->
        </td>
    </tr>
    <tr>
        <td>Nota:</td>
        <td>
            <div dojoType="dijit.Editor" id="notaeditor"></div>
            <!--<textarea rows="10"  name="description" cols="80"></textarea>-->
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <input type="button" value="Cancelar" onclick="closeAddQuestion();">&nbsp;<input type="button" value="Guardar" onclick="saveAddQuestion(this.form);">&nbsp;<input type="button" value="Guardar y Agregar" onclick="saveAndContinueQuestion(this.form);">
        </td>
    </tr>
</table>
<div id="dialogaddSubquestions" dojoType="dijit.Dialog" title="Agregar SubPreguntas">
    <jsp:include flush="true" page="addSubquestions.jsp" />
</div>
<div id="dialogaddAnswer" dojoType="dijit.Dialog" title="Agregar Respuestas">
    <jsp:include flush="true" page="addAnswer.jsp" />
</div>

<div id="dialogaddbankQuestions" dojoType="dijit.Dialog" title="Banco de preguntas">
    <jsp:include flush="true" page="dialogbankquestions.jsp" />
</div>
