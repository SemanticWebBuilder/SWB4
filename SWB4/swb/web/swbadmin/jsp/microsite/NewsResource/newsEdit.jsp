<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
%>
<%
            String uri = request.getParameter("uri");
            NewsElement rec = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            if (rec == null) {
%>
Error: Elemento no encontrado...
<%
                return;
            }
%>
<script type="text/javascript">
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dijit.form.Button");
    dojo.require("dojo.parser");
</script>
<div class="soria">
    <form class="swbform" method="post" action="<%=paramRequest.getActionUrl()%>">
        <div>
            <h3>Editar noticia</h3>
            <fieldset>
                <table>
                    <tr>
                        <td align="right" valign="center"><label for="new_title">Título de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_title" name="new_title" value="<%=(rec.getTitle()==null?"":rec.getTitle())%>"/><br>
                        </td>
                    <tr>
                        <td align="right" valign="center"><label for="new_image">Imagen de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input type="text" dojoType="dijit.form.TextBox" id="new_image" name="new_image" value="<%=(rec.getNewsPicture()==null?"":rec.getNewsPicture())%>"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_author">Autor de la noticia&nbsp;<em>*</em></label></td>
                        <td>
                            <input type="text" dojoType="dijit.form.TextBox" id="new_author" name="new_author" value="<%=(rec.getAuthor()==null?"":rec.getAuthor())%>"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_abstract">Resumen&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea dojoType="dijit.form.Textarea" style="width:244px" id="new_abstract" name="new_abstract"><%=(rec.getAbstr()==null?"":rec.getAbstr())%></textarea><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_fulltext">Texto completo&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea dojoType="dijit.form.Textarea" style="width:244px" id="new_fulltext" name="new_fulltext"><%=(rec.getFullText()==null?"":rec.getFullText())%></textarea><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_citation">Fuente&nbsp;<em>*</em></label></td>
                        <td>
                            <input type="text" dojoType="dijit.form.TexBox" id="new_citation" name="new_citation" value="<%=(rec.getCitation()==null?"":rec.getCitation())%>"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="new_tags">Etiquetas&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="new_tags" name="new_tags" value="<%=(rec.getTags()==null?"":rec.getTags())%>"/><br>
                        </td>
                    </tr>
                </table>
            </fieldset>
                        <fieldset>
                        <legend><strong>¿Quién puede ver esta noticia?</strong></legend>
                        <ul class="options">
                            <%String chk="checked=\"checked\"";%>
                            <li><label><input dojoType="dijit.form.RadioButton" type="radio" class="radio" name="level" value="0" <%if(rec.getVisibility()==0)out.println(chk);%>/> Cualquiera</label></li>
                            <li><label><input dojoType="dijit.form.RadioButton" type="radio" class="radio" name="level" value="1" <%if(rec.getVisibility()==1)out.println(chk);%>/> Sólo los miembros</label></li>
                            <li><label><input dojoType="dijit.form.RadioButton" type="radio" class="radio" name="level" value="3" <%if(rec.getVisibility()==3)out.println(chk);%>/> Sólo yo</label></li>
                        </ul>
                    </fieldset>
            <p class="pad5 last-child clear right">
                <strong><input class="button" value="Guardar" label="Guardar" dojoType="dijit.form.Button" type="submit"/></strong>
                <input type="button" class="button" value="Cancelar" label="Cancelar" dojoType="dijit.form.Button" onclick="window.location='<%=paramRequest.getRenderUrl()%>';"/>
            </p>
            <input type="hidden" name="uri" value="<%=rec.getURI()%>"/>
            <input type="hidden" name="act" value="edit"/>
        </div>
    </form>
</div>