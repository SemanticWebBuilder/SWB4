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
<div class="soria">
    <form class="swbform" method="post" action="<%=paramRequest.getActionUrl()%>">
        <div>
            <h3>Agregar evento nuevo</h3>
            <fieldset>
                <table>
                    <tr>
                        <td align="right" valign="center"><label for="event_title">Título del evento&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" required="true" id="event_title" name="event_title"/><br>
                        </td>
                    <tr>
                        <td align="right" valign="center"><label for="event_description">Descripción del evento&nbsp;<em>*</em></label></td>
                        <td>
                            <textarea dojoType="dijit.form.Textarea" id="event_description" name="event_description" style="width:200px;"></textarea><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_audience">Dirigido a&nbsp;<em>*</em></label></td>
                        <td>
                            <input type="text" dojoType="dijit.form.TextBox" id="event_audience" name="event_audience"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_startDate">Fecha de inicio&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.DateTextBox" type="text" id="event_startDate" name="event_startDate" constraints="{datePattern:'yyyy/MM/dd'}" onchange="dijit.byId('event_endDate').constraints.min = arguments[0];"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_endDate">Fecha de término&nbsp;<em>*</em></label></td>
                        <td>
                            <input class="tundra" dojoType="dijit.form.DateTextBox" type="text" id="event_endDate" name="event_endDate" constraints="{datePattern:'yyyy/MM/dd'}" onchange="dijit.byId('event_startDate').constraints.max = arguments[0];"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_startTime">Hora de inicio&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TimeTextBox" type="text" id="event_startTime" name="event_startTime" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" /><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_endTime">Hora de término&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TimeTextBox" type="text" id="event_endTime" name="event_endTime" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" /><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_place">Lugar del evento&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="event_place" name="event_place"/><br>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="center"><label for="event_tags">Etiquetas&nbsp;<em>*</em></label></td>
                        <td>
                            <input dojoType="dijit.form.TextBox" type="text" id="event_tags" name="event_tags"/><br>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <p class="pad5 last-child clear right">
                <strong><input class="button" dojoType="dijit.form.Button" type="submit" value="Guardar" label="Guardar"/></strong>
                <input class="button" dojoType="dijit.form.Button" type="button" label="Cancelar" value="Cancelar" onclick="window.location='<%=paramRequest.getRenderUrl()%>';"/>
            </p>
        </div>
        <input type="hidden" name="act" value="add"/>
    </form>
</div>