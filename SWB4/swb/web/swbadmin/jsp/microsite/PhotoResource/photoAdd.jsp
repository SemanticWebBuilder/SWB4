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
        <h3>Agrega una foto</h3>
    </div>
    <div>
        <fieldset><legend></legend>
            <div>
                <div>
                    <p>
                        <label for="foto">Archivo: </label>
                        <input id="foto" type="file" style="width: 90%;" class="textfield tags" size="22" name="foto" />
                    </p>
                    <p>
                        <label for="title">Título:</label><br />
                        <input id="title" style="width: 90%;" type="text" class="textfield" size="25" name="title" maxlength="200" />
                    </p>
                    <p>
                        <label for="description">Descripción</label><br/>
                        <textarea id="description" style="width: 90%" rows="5" cols="23" name="description"></textarea>
                     </p>
                     <p>
                        <label for="tags">Etiquetas:</label><br />
                        <input id="tags" type="text" style="width: 90%;" class="textfield tags" size="22" name="tags" maxlength="2000" />
                    </p>
                </div>
            </div>
            <div>
                <div>
                    <fieldset>
                        <legend><strong>¿Quién puede ver este video?</strong></legend>
                        <ul class="options">
                            <%String chk="checked=\"checked\"";%>
                            <li><label><input type="radio" class="radio" name="level" value="0" checked="checked" /> Cualquiera</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="1" /> Sólo los miembros</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="3" /> Sólo yo</label></li>
                        </ul>
                    </fieldset>
                </div>
            </div>
        </fieldset>
        <p>
            <strong><input type="submit" value="Enviar" class="button"/></strong>
            <a class="button" href="<%=paramRequest.getRenderUrl()%>">Cancelar</a>
        </p>
    </div>
</form>
