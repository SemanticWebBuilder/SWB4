<%@page contentType="text/html"%>
<%@page import="org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
%>
<%
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            if (rec == null) {
%>
Error: Elemento no encontrado...
<%
                return;
            }
%>
<form method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <h3>Editar evento</h3>
        <!--
                <p class="last-child"><strong><a href="http://webbuilder.ning.com/video/video/show?id=2034909%3AVideo%3A102">&#171; Cancelar y volver al video</a></strong></p>
        -->
    </div>
    <div>
        <fieldset><legend></legend>
            <div>
                <div>
                    <p>
                        <label for="event_title">Título del evento:</label><br />
                        <input dojoType="dijit.form.TextBox" id="event_title" type="text" name="event_title" value="<%=rec.getTitle()%>" />
                    </p>
                    <p>
                        <label for="event_description">Descripción del evento:</label><br/>
                        <textarea dojoType="dijit.form.TextArea" id="event_description" name="event_description"><%=rec.getDescription()%></textarea>
                    </p>
                    <p>
                        <label for="event_startDate">Fecha de inicio:</label><br />
                        <input  dojoType="dijit.form.DateTextBox" id="event_startDate" type="text" name="event_startDate" value="<%=rec.getStartDate()%>" maxlength="2000" />                        
                    </p>
                    <p>
                        <label for="event_startTime">Hora de inicio:</label><br />
                        <input  dojoType="dijit.form.TimeTextBox" id="event_startTime" type="text" name="event_startTime" value="<%=rec.getStartTime()%>" maxlength="2000" />
                    </p>
                    <p>
                        <label for="event_place">Lugar del evento:</label><br />
                        <input  dojoType="dijit.form.TextBox" id="event_place" type="text" name="event_place" value="<%=rec.getPlace()%>" maxlength="2000" />
                    </p>
                </div>
            </div>
            <div>
                <div>
                    <fieldset>
                        <legend><strong>¿Quién puede ver este video?</strong></legend>
                        <ul class="options">
                            <%String chk = "checked=\"checked\"";%>
                            <li><label><input type="radio" class="radio" name="level" value="0" <%if (rec.getVisibility() == 0) {
                out.println(chk);
            }%>/> Cualquiera</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="1" <%if (rec.getVisibility() == 1) {
                out.println(chk);
            }%>/> Sólo los miembros</label></li>
                            <li><label><input type="radio" class="radio" name="level" value="3" <%if (rec.getVisibility() == 3) {
                out.println(chk);
            }%>/> Sólo yo</label></li>
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