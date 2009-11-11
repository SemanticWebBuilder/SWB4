<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            if (!member.canAdd())
            {
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
<div class="columnaIzquierda">
    <h3>Agregar video</h3>
    <form class="swbform" method="post" action="<%=paramRequest.getActionUrl()%>">        
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
            <p>
                <strong><input class="button" dojoType="dijit.form.Button" type="submit" value="Guardar" label="Guardar"/></strong>
                <input class="button" dojoType="dijit.form.Button" type="button" label="Cancelar" value="Cancelar" onclick="window.location='<%=paramRequest.getRenderUrl()%>';"/>
            </p>
        </div>
        <input type="hidden" name="act" value="add"/>
    </form>
</div>