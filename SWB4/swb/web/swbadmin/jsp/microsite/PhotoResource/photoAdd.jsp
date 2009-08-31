<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
%>

<script type="text/javascript">
function validaForma()
{
    var foto = document.frmaddfoto.title.value;
    if(!foto)
    {
        alert('¡Debe ingresar el archivo de la foto!');
        document.frmaddfoto.foto.focus();
        return;
    }
    var title = document.frmaddfoto.title.value;
    if(!title)
    {
        alert('¡Debe ingresar el título de la foto!');
        document.frmaddfoto.description.focus();
        return;
    }
    var description = document.frmaddfoto.description.value;
    if(!description)
    {
        alert('¡Debe ingresar la description de la foto!');
        document.frmaddfoto.description.focus();
        return;
    }
    document.frmaddfoto.submit();
}
</script>
<br />
<div id="panorama">
<form name="frmaddfoto" id="frmaddfoto" enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
            <legend>Agrega foto</legend>
            <div>
                <p>
                    <label for="foto">Archivo:&nbsp;</label><br />
                    <input id="foto" type="file" size="22" name="foto" />
                </p>
                <p>
                    <label for="title">Título:&nbsp;</label><br />
                    <input id="title" type="text" size="25" name="title" maxlength="200" />
                </p>
                <p>
                    <label for="description">Descripción</label><br />
                    <textarea id="description" cols="30" rows="5" name="description"></textarea>
                 </p>
                 <p>
                    <label for="tags">Etiquetas:&nbsp;</label><br />
                    <input id="tags" type="text" size="22" name="tags" maxlength="2000" />
                </p>
            </div>
        </fieldset>
        <fieldset>
            <legend>¿Quién puede ver este video?</legend>
            <div>
                <p>
                    <label for="level"><input type="radio" name="level" value="0" checked="checked" />&nbsp;Cualquiera</label>
                </p>
                <p>
                    <label for="level"><input type="radio" name="level" value="1" />&nbsp;Sólo los miembros</label>
                </p>
                <p>
                    <label for="level"><input type="radio" name="level" value="3" />&nbsp;Sólo yo</label>
                </p>
            </div>
        </fieldset>
        <fieldset>
            <legend></legend>
            <div>
            <p>
                <div class="editarInfo"><p><a onclick="validaForma()" href="#">Enviar</a></p></div>
                <div class="editarInfo"><p><a href="<%=paramRequest.getRenderUrl()%>">Cancelar</a></p></div>
            </p>
            </div>
        </fieldset>
    </div>
    <input type="hidden" name="act" value="add"/>
</form>
</div>