<script type="text/javascript">
    function showAddQuestion()
    {
        dijit.byId("dialogaddCuestion").show();
    }
</script>
<p>* Seleccione el cuestionario y la secci&oacute;n a la que pertenece la pregunta que desea agregar</p>
<form name="addQuestion" action="">
    <table width="100%">
        <tr>
            <td>
                <p>Cuestionario</p>
            </td>
            <td>
                <select name="cuestionario" onchange="cargaCuestionarios()">
                    <!-- llena listado de cuestionarios -->
                </select>
            </td>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td>
                <p>Parte</p>
            </td>
            <td>
                <select name="parte" onchange="cargaSeccion()">
                    <!-- llena listado de partes -->
                </select>
            </td>
            <td>
                <p>Secci&oacute;n</p>
            </td>
            <td>
                <select name="seccion">
                    <!-- llena listado de seccion -->
                </select>
            </td>
        </tr>
    </table>
</form>
<br>
<!-- listado de preguntas -->
<table width="100%">
    <th>Preguntas</th>
    <th>Acci&oacute;n</th>

    <tr>
        <td>

        </td>
    </tr>
</table>
<br>
<br>


<input type="button" value="Agregar Pregunta" onclick="showAddQuestion()">