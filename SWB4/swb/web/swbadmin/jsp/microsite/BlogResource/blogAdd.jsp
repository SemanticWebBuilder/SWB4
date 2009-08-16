<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
    
%>

<form method="post" action="<%=paramRequest.getActionUrl()%>">
    <input type="hidden" name="act" value="<%=request.getParameter("act")%>">
    <div>
        <h3>Agregar una nueva entrada al Blog</h3>
    </div>
    <div>
        <fieldset><legend></legend>
            <div>
                <p>
                    <label for="title">Título</label><input id="title" name="title" value=""><br>
                    <label for="description">Contenido de entrada</label>                    
                    <textarea id="description" style="width: 98%" rows="5" cols="23" name="description"></textarea>
                </p>
            </div>
        </fieldset>
                    <fieldset>
                        <legend><strong>¿Quién puede ver este video?</strong></legend>
                        <ul class="options">
                            <%String chk="checked=\"checked\"";%>
                            <li><label><input type="radio" class="radio" name="level" value="0" /> Cualquiera</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="1" /> Sólo los miembros</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="3" /> Sólo yo</label></li>
                        </ul>
                    </fieldset>
        <p class="pad5 last-child clear right">
            <strong><input type="submit" value="Guardar cambios" class="button"/></strong>
            <a class="button" href="<%=paramRequest.getRenderUrl()%>">Cancelar</a>
        </p>
    </div>
    <input type="hidden" name="act" value="add"/>
</form>
        