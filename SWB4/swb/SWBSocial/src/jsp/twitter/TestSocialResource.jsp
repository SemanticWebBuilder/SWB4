<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
 <%
    SWBResourceURL postTweet = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);

%>
<script type="text/javascript">
    function limiteCaracteres(cadena, contador, limite){
        if(cadena.value.length>limite){
            cadena.value=cadena.value.substring(0,limite);
        }else{
            contador.value=limite-cadena.value.length;
        }
    }
    
    function imagen(){
        var imagen = document.getElementById("image"); 
        imagen.style.display="block";
    }
</script>
<br><br>

<form name="post" action="<%=postTweet%>">
    <table>
        <tr>
            <td>¿Que estas haciendo ahora?</td>
        </tr>
        <tr>
            <td>
                <textarea name="estado" cols="40" rows="3" 
                          onkeydown="limiteCaracteres(this.form.estado, this.form.remLen, 140);" 
                          onkeyup="limiteCaracteres(this.form.estado, this.form.remLen, 140);">
                </textarea>
                
                <a href="javascript:imagen();">Subir imagen</a>
                <div id="image" style="display: none">
                    <input type="file" name="img" label="Imagen (jpg, jpeg, gif, png, swf)" showfile="true" filetype="jpg|jpeg|gif|png|swf" isshowfiletype="true" isremovefile="true" removemsg="Quitar imagen" size="40" />
                    
                </div>
                <input readonly type="text" name="remLen" size="3" maxlength="3" value="140"> caracteres
                <span></span>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="enviar"></td>
        </tr>
    </table>
</form>