<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);

            String path = SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/";
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
<form class="swbform" enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
        <legend>Editar noticia</legend>
        <div>
            <p>
                <label for="new_image">Imagen de la noticia:&nbsp;</label>
                <a href="<%= SWBPlatform.getWebWorkPath()+rec.getNewsImage()%>" target="_self">
                    <img src="<%= SWBPlatform.getWebWorkPath()+rec.getNewsImage() %>" alt="<%= rec.getTitle() %>" border="0" />
                </a><br />
                <input type="file" id="foto" name="foto" />
            </p>
            <p>
                <label for="new_title">Título de la noticia:&nbsp;</label>
                <input type="text" id="new_title" name="new_title"/>
            </p>
            <p>
                <label for="new_author">Autor de la noticia:&nbsp;</label>
                <input type="text" id="new_author" name="new_author"/>
            </p>
            <p>
                <label for="new_abstract">Resumen de la noticia:&nbsp;</label>
                <textarea id="new_abstract" name="new_abstract" cols="30" rows="5"></textarea>
            </p>
            <p>
                <label for="new_fulltext">Texto completo:&nbsp;</label>
                <textarea id="new_fulltext" name="new_fulltext" cols="30" rows="5"></textarea>
            </p>
            <p>
                <label for="new_citation">Fuente:&nbsp;</label>
                <input type="text" id="new_citation" name="new_citation"/>
            </p>
            <p>
                <label for="new_tags">Etiquetas:&nbsp;</label>
                <input type="text" id="new_tags" name="new_tags"/>
            </p>
        </div>
        </fieldset>
        <fieldset>
            <legend>¿Quién puede ver este evento?</legend>
            <%String chk = "checked=\"checked\"";%>
            <div>
                <p>
                    <label for="level"><input type="radio" name="level" value="0" <%if(rec.getVisibility()==0)out.println(chk);%> />&nbsp;Cualquiera</label>
                </p>
                <p>
                    <label for="level"><input type="radio" name="level" value="1" <%if(rec.getVisibility()==1)out.println(chk);%> />&nbsp;Sólo los miembros</label>
                </p>
                <p>
                    <label for="level"><input type="radio" name="level" value="3" <%if(rec.getVisibility()==3)out.println(chk);%> />&nbsp;Sólo yo</label>
                </p>
            </div>
        </fieldset>
        <fieldset>
            <legend></legend>
            <div>
            <p>
                <input type="submit" value="Enviar" />
                <input type="button" value="Cancelar" onclick="window.location='<%= paramRequest.getRenderUrl()%>'"/>
            </p>
            </div>
        </fieldset>
    </div>
    <input type="hidden" name="uri" value="<%=rec.getURI()%>"/>
    <input type="hidden" name="act" value="edit"/>
</form>