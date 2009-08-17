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
        if(uri==null) {
            uri = (String)request.getSession(true).getAttribute("uri");
            request.getSession(true).setAttribute("uri", null);
            request.getSession(true).removeAttribute("uri");
        }
        PhotoElement rec= (PhotoElement)SemanticObject.createSemanticObject(uri).createGenericInstance();
        if(rec==null)
        {
%>
            Error: Elemento no encontrado...
<%
            return;
        }
%>
<form method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <h3>Editar</h3>
    </div>
    <div>
        <fieldset><legend></legend>
            <div>
                <div>
                    <p>
                        <label for="title">Título:</label><br />
                        <input id="title" style="width: 90%;" type="text" class="textfield" size="25" name="title" maxlength="200" value="<%=rec.getTitle()%>" />
                    </p>
                    <p>
                        <label for="description">Descripción</label><br/>
                        <textarea id="description" style="width: 90%" rows="5" cols="23" name="description"><%=rec.getDescription()%></textarea>
                     </p>
                     <p>
                        <label for="tags">Etiquetas:</label><br />
                        <input id="tags" type="text" style="width: 90%;" class="textfield tags" size="22" name="tags" value="<%=rec.getTags()%>" maxlength="2000" />
                    </p>
                </div>
            </div>
            <div>
                <div>
                    <fieldset>
                        <legend><strong>¿Quién puede ver este video?</strong></legend>
                        <ul class="options">
                            <%String chk="checked=\"checked\"";%>
                            <li><label><input type="radio" class="radio" name="level" value="0" <%if(rec.getVisibility()==0)out.println(chk);%>/> Cualquiera</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="1" <%if(rec.getVisibility()==1)out.println(chk);%>/> Sólo los miembros</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="3" <%if(rec.getVisibility()==3)out.println(chk);%>/> Sólo yo</label></li>
                        </ul>
                    </fieldset>
                </div>
            </div>
        </fieldset>
        <p>
            <strong><input type="submit" value="Guardar cambios" class="button"/></strong>
            <a class="button" href="<%=paramRequest.getRenderUrl()%>">Cancelar</a>
        </p>
    </div>
    <input type="hidden" name="uri" value="<%=rec.getURI()%>"/>
    <input type="hidden" name="act" value="edit"/>
</form>