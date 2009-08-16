<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
%>
<script type="text/javascript">
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dojo.parser");
</script>
<div class="soria">
    <form class="swbform" method="post" action="<%=paramRequest.getActionUrl()%>">
        <div>
            <h3>Agregar noticia nueva</h3>
            <fieldset>
                <table>
                    <tr>
                        <td align="right" valign="center"><label for="new_title">Título de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_title" name="new_title"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_image">Im&aacute;gen de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_image" name="new_image"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_author">Autor de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_author" name="new_author"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_abstract">Resumen de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea style="width:244px" dojoType="dijit.form.Textarea" id="new_abstract" name="new_abstract"></textarea><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_fulltext">Texto completo&nbsp;<em>*</em></label></td>
                        <td>
                            <input style="width:244px" dojoType="dijit.form.Textarea" id="new_fulltext" name="new_fulltext" /><br>
                        </td>
                    </tr>                    
                    <tr>
                        <td align="right" valign="center"><label for="event_tags">Etiquetas&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="event_tags" name="event_tags"/><br>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <p class="pad5 last-child clear right">
                <strong><input class="button" dojoType="dijit.form.Button" type="submit" value="Guardar" label="Guardar"/></strong>
                <input class="button" dojoType="dijit.form.Button" type="button" label="Cancelar" value="Cancelar" onclick="window.location='<%=paramRequest.getRenderUrl()%>';"/>
            </p>
        </div>
        <input type="hidden" name="act" value="add"/>
    </form>
</div>