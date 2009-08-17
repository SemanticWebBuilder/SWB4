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
    <form class="swbform" enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
        <div>
            <h3>Agregar noticia nueva</h3>
        </div>
        <div>
            <fieldset><legend></legend>
                <table>
                    <tr>
                        <td align="right" valign="center"><label for="new_image">Imagen de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input id="foto" type="file" style="width: 90%;" class="textfield tags" size="22" name="foto" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_title">Título de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_title" name="new_title"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td align="right" valign="center"><label for="new_author">Autor de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_author" name="new_author"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_abstract">Resumen de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea style="width:244px" dojoType="dijit.form.Textarea" id="new_abstract" name="new_abstract"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_fulltext">Texto completo&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea style="width:244px" dojoType="dijit.form.Textarea" id="new_fulltext" name="new_fulltext"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_citation">Fuente&nbsp;<em>*</em></label></td>
                        <td>
                            <input type="text" dojoType="dijit.form.TexBox" id="new_citation" name="new_citation"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_tags">Etiquetas&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_tags" name="new_tags"/>
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