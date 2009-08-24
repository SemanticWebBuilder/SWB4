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
            <legend>Agregar evento</legend>
            <div>
                <p>
                    <label for="foto">Imagen del evento:&nbsp;</label>
                    <input type="file" id="foto" name="foto" />
                </p>
                <p>
                    <label for="event_title">Título del evento:&nbsp;</label>
                    <input type="text" id="event_title" name="event_title"/>
                </p>
                <p>
                    <label for="event_description">Descripción del evento:&nbsp;</label>
                    <textarea id="event_description" name="event_description" cols="30" rows="5"></textarea>
                </p>
                <p>
                    <label for="event_audience">Dirigido a:&nbsp;</label>
                    <input type="text" id="event_audience" name="event_audience" value="Todos"/>
                </p>
                <p>
                    <label for="event_startDate">Fecha de inicio:&nbsp;</label>
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_startDate" name="event_startDate" constraints="{datePattern:'yyyy/MM/dd'}" onchange="dijit.byId('event_endDate').constraints.min = arguments[0];"/>
                </p>
                <p>
                    <label for="event_endDate">Fecha de término:&nbsp;</label>
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_endDate" name="event_endDate" constraints="{datePattern:'yyyy/MM/dd'}" onchange="dijit.byId('event_startDate').constraints.max = arguments[0];"/>
                </p>
                <p>
                    <label for="event_startTime">Hora de inicio:&nbsp;</label>
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_startTime" name="event_startTime" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" />
                </p>
                <p>
                    <label for="event_endTime">Hora de término:&nbsp;</label>
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_endTime" name="event_endTime" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" />
                </p>
                <p>
                    <label for="event_place">Lugar del evento:&nbsp;</label>
                    <input type="text" id="event_place" name="event_place"/>
                </p>
                <p>
                    <label for="event_tags">Etiquetas:&nbsp;</label>
                    <input type="text" id="event_tags" name="event_tags"/>
                </p>
            </div>
        </fieldset>
        <fieldset>
            <legend></legend>
            <div>
            <p>
                <input type="submit" value="Enviar" />
                <input type="button" value="Cancelar" onclick="window.location='<%=paramRequest.getRenderUrl()%>';"/>
            </p>
            </div>
        </fieldset>
    </div>
    <input type="hidden" name="act" value="add"/>
</form>