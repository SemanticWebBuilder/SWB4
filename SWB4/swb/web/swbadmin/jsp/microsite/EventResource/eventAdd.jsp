<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            if (!member.canAdd())
            {
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

    function validaForma()
    {
        var title = document.frmaddevent.event_title.value;
        if(!title)
        {
            alert('¡Debe ingresar el título del evento!');
            document.frmaddevent.event_title.focus();
            return;
        }
        var description = document.frmaddevent.event_description.value;
        if(!description)
        {
            alert('¡Debe ingresar la description del evento!');
            document.frmaddevent.event_description.focus();
            return;
        }
        var event_audience = document.frmaddevent.event_audience.value;
        if(!event_audience)
        {
            alert('¡Debe ingresar la audiencia a la que está dirigido el evento!');
            document.frmaddevent.event_audience.focus();
            return;
        }
        document.frmaddevent.submit();
    }
</script>


<div class="columnaIzquierda">
    <div class="adminTools">

        <a class="adminTool" onclick="validaForma()" href="#">Enviar</a>
        <a class="adminTool" href="<%=paramRequest.getRenderUrl()%>">Cancelar</a>

    </div>
<form name="frmaddevent" id="frmaddevent" class="swbform" enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
            <legend>Agregar evento</legend>
            <div>
                <p>
                    <label for="foto">Imagen del evento:&nbsp;</label><br />
                    <input type="file" id="foto" name="foto" size="45" />
                </p>
                <p>
                    <label for="event_title">Título del evento:&nbsp;</label><br />
                    <input type="text" id="event_title" name="event_title" maxlength="50" size="45" />
                </p>
                <p>
                    <label for="event_description">Descripción del evento:&nbsp;</label><br />
                    <textarea id="event_description" name="event_description" cols="45" rows="2"></textarea>
                </p>
                <p>
                    <label for="event_audience">Dirigido a:&nbsp;</label><br />
                    <input type="text" id="event_audience" name="event_audience" value="Todos" maxlength="50" size="45" />
                </p>
                <p>
                    <label for="event_startDate">Fecha de inicio:&nbsp;</label><br />
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_startDate" name="event_startDate" constraints="{datePattern:'dd/MM/yyyy'}" onchange="dijit.byId('event_endDate').constraints.min = arguments[0];"/>
                </p>
                <p>
                    <label for="event_endDate">Fecha de término:&nbsp;</label><br />
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_endDate" name="event_endDate" constraints="{datePattern:'dd/MM/yyyy'}" onchange="dijit.byId('event_startDate').constraints.max = arguments[0];"/>
                </p>
                <p>
                    <label for="event_startTime">Hora de inicio:&nbsp;</label><br />
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_startTime" name="event_startTime" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" />
                </p>
                <p>
                    <label for="event_endTime">Hora de término:&nbsp;</label><br />
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_endTime" name="event_endTime" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" />
                </p>
                <p>
                    <label for="event_place">Lugar del evento:&nbsp;</label><br />
                    <input type="text" id="event_place" name="event_place" maxlength="120" size="60" />
                </p>
                <p>
                    <label for="event_tags">Etiquetas:&nbsp;</label><br />
                    <input type="text" id="event_tags" name="event_tags" maxlength="50" size="60" />
                </p>
            </div>
        </fieldset>        
    </div>
    <input type="hidden" name="act" value="add"/>
</form>
</div>
                <div class="columnaCentro">

</div>