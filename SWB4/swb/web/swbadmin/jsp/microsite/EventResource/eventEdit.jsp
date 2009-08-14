<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
<script type="text/javascript">
    dojo.require("dijit.form.TextBox");
    dojo.require("dijit.form.Textarea");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dojo.parser");
</script>
<div class="soria">
    <form class="swbform" method="post" action="<%=paramRequest.getActionUrl()%>">
        <div>
            <h3>Editar evento</h3>
            <fieldset>
                <table>
                    <tr>
                        <td align="right" valign="center"><label for="event_title">Título del evento:</label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="event_title" name="event_title" value="<%=(rec.getTitle()==null?"":rec.getTitle())%>"/><br>
                        </td>
                    <tr>
                        <td align="right" valign="center"><label for="event_description">Descripción del evento:</label></td>
                        <td>
                            <textarea dojoType="dijit.form.Textarea" id="event_description" name="event_description" style="width:200px;"><%=(rec.getDescription()==null?"":rec.getDescription())%></textarea><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_audience">Dirigido a:</label></td>
                        <td>
                            <input type="text" dojoType="dijit.form.TextBox" id="event_audience" name="event_audience" value="<%=(rec.getAudienceType()==null?"":rec.getAudienceType())%>"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_startDate">Fecha de inicio:</label></td>
                        <td>
                            <input dojoType="dijit.form.DateTextBox" type="text" id="event_startDate" name="event_startDate" value="<%=(rec.getStartDate()==null?"":dateFormat.format(rec.getStartDate()))%>" onchange="dijit.byId('event_endDate').constraints.min = arguments[0];"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_endDate">Fecha de término:</label></td>
                        <td>
                            <input class="tundra" dojoType="dijit.form.DateTextBox" type="text" id="event_endDate" name="event_endDate" value="<%=(rec.getEndDate()==null?"":dateFormat.format(rec.getEndDate()))%>" onchange="dijit.byId('event_startDate').constraints.max = arguments[0];"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_startTime">Hora de inicio:</label></td>
                        <td>
                            <input dojoType="dijit.form.TimeTextBox" type="text" id="event_startTime" name="event_startTime" value="<%=(rec.getStartTime()==null?"":"T" + rec.getStartTime())%>" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" /><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_endTime">Hora de término:</label></td>
                        <td>
                            <input dojoType="dijit.form.TimeTextBox" type="text" id="event_endTime" name="event_endTime" value="<%=(rec.getEndTime()==null?"":"T" + rec.getEndTime())%>" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" /><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_place">Lugar del evento:</label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="event_place" name="event_place" value="<%=(rec.getPlace()==null?"":rec.getPlace())%>"/><br>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <p class="pad5 last-child clear right">
                <strong><button dojoType="dijit.form.Button" type="submit">Guardar</button></strong>
                <button dojoType="dijit.form.Button" onclick="window.location='<%=paramRequest.getRenderUrl()%>';">Cancelar</button>
            </p>
            <input type="hidden" name="act" value="add"/>
        </div>        
    </form>
</div>