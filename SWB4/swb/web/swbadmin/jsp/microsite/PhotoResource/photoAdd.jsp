<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
%>
<%--<form enctype="multipart/form-data" method="post" action="<%=paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_ADMHLP).setCallMethod(SWBResourceURL.Call_DIRECT)%>">--%>
<form enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
            <legend>Agrega una foto</legend>
            <div>
                <p>
                    <label for="foto">Archivo:&nbsp;</label>
                    <input id="foto" type="file" size="22" name="foto" />
                </p>
                <p>
                    <label for="title">Título:&nbsp;</label>
                    <input id="title" type="text" size="25" name="title" maxlength="200" />
                </p>
                <p>
                    <label for="description">Descripción</label>
                    <textarea id="description" cols="30" rows="5" name="description"></textarea>
                 </p>
                 <p>
                    <label for="tags">Etiquetas:&nbsp;</label>
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
                <input type="submit" value="Enviar" />
                <input type="button" value="Cancelar" onclick="window.location='<%= paramRequest.getRenderUrl()%>'"/>
            </p>
            </div>
        </fieldset>
    </div>
    <input type="hidden" name="act" value="add"/>
</form>
