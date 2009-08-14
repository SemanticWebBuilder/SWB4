<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    Member member=Member.getMember(user,wpage);
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
<form class="soria" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>Agregar evento nuevo</fieldset>
        <fieldset>
                    <label for="event_title">Título del evento:</label>
                    <input dojoType="dijit.form.TextBox" type="text" id="event_title" name="event_title"/><br>
                    <label for="event_description">Descripción del evento:</label>
                    <textarea dojoType="dijit.form.Textarea" id="event_description" name="event_description" style="width:200px;"></textarea><br>
                    <label for="event_startDate">Fecha de inicio:</label>
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_startDate" name="event_startDate"/><br>
                    <label for="event_startTime">Hora de inicio:</label>
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_startTime" name="event_startTime"/><br>
                    <label for="event_place">Lugar del evento:</label>
                    <input dojoType="dijit.form.TextBox" type="text" id="event_place" name="event_place"/><br>
        </fieldset>
        <p class="pad5 last-child clear right">
            <strong><button dojoType="dijit.form.Button" type="submit">Guardar</button></strong>
            <a class="button" href="<%=paramRequest.getRenderUrl()%>">Cancelar</a>
        </p>
    </div>
    <input type="hidden" name="act" value="add"/>
</form>
</div>