<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
%>
<%
        String uri=request.getParameter("uri");        
        PhotoElement rec= (PhotoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(rec==null)
        {
%>
            Error: Elemento no encontrado...
<%
            return;
        }
%>
<br />
<div id="panorama">
<form method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
            <legend>Editar foto</legend>
            <div>
                <p>
                    <label for="foto">Archivo:&nbsp;</label><br />
                    <input id="foto" type="file" size="22" name="foto" />
                </p>
                <p>
                    <label for="title">Título:&nbsp;</label><br />
                    <input id="title" type="text" size="25" name="title" maxlength="200" value="<%= rec.getTitle()%>" />
                </p>
                <p>
                    <label for="description">Descripción</label><br />
                    <textarea id="description" cols="30" rows="5" name="description"><%= rec.getDescription()%></textarea>
                 </p>
                 <p>
                    <label for="tags">Etiquetas:&nbsp;</label><br />
                    <input id="tags" type="text" size="22" name="tags" maxlength="2000" value="<%= rec.getTags()%>" />
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
</div>