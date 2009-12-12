<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            if (!member.canAdd())
            {
                response.sendError(404);
                return;
            }
%>
<script type="text/javascript">
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dojo.parser");
</script>
<script type="text/javascript">
    function validaForma()
    {
        var foto = document.frmeditvideo.video_code.value;
        if(!foto)
        {
            alert('¡Debe ingresar el código de youtube!');
            document.frmaddfoto.foto.focus();
            return;
        }
        document.frmeditvideo.submit();
    }
</script>
<%
            String cancelurl = paramRequest.getRenderUrl().toString();
%>
<div class="columnaIzquierda">
    <div class="adminTools">
        <a class="adminTool" onclick="validaForma()" href="#">Guardar</a>
        <a class="adminTool" href="<%=cancelurl%>">Cancelar</a>
    </div>
    <form class="swbform" name="frmeditvideo" method="post" action="<%=paramRequest.getActionUrl()%>">
        <div>
            <fieldset>
                <table>
                    <tr>
                        <td align="right" valign="center"><label for="video_code">Código youTube&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea id="video_code" style_="width: 98%" rows="2" cols="23" name="video_code"></textarea>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <input type="hidden" name="act" value="add"/>
    </form>
</div>