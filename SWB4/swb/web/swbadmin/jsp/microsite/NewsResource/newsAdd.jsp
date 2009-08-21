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
<form class="swbform" enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
            <legend>Agregar noticia nueva</legend>
            <div>
                <p>
                    <label for="new_image">Imagen de la noticia&nbsp;</label>
                    <input type="file" id="foto" name="foto" />
                </p>
                <p>
                    <label for="new_title">Título de la noticia&nbsp;</label>
                    <input type="text" id="new_title" name="new_title"/>
                </p>
                <p>
                    <label for="new_author">Autor de la noticia&nbsp;</label>
                    <input type="text" id="new_author" name="new_author"/>
                </p>
                <p>
                    <label for="new_abstract">Resumen de la noticia&nbsp;</label>
                    <textarea id="new_abstract" name="new_abstract" cols="30" rows="5"></textarea>
                </p>
                <p>
                    <label for="new_fulltext">Texto completo&nbsp;</label>
                    <textarea id="new_fulltext" name="new_fulltext" cols="30" rows="5"></textarea>
                </p>
                <p>
                    <label for="new_citation">Fuente&nbsp;</label>
                    <input type="text" id="new_citation" name="new_citation"/>
                </p>
                <p>
                    <label for="new_tags">Etiquetas&nbsp;</label>
                    <input type="text" id="new_tags" name="new_tags"/>
                </p>
            </div>
        </fieldset>
        <fieldset>
            <legend></legend>
            <div>
            <p>
                <input type="submit" value="Guardar" />
                <input type="button" value="Cancelar" onclick="window.location='<%=paramRequest.getRenderUrl()%>';"/>
            </p>
            </div>
        </fieldset>
    </div>
    <input type="hidden" name="act" value="add"/>
</form>