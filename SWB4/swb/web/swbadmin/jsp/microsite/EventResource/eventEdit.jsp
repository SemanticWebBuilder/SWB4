<%@page contentType="text/html"%>
<%@page import="java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
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
    dojo.require("dijit.form.RadioButton");
    dojo.require("dijit.form.Button");
    dojo.require("dojo.parser");
</script>
<form class="swbform" enctype="multipart/form-data" method="post" action="<%=paramRequest.getActionUrl()%>">
    <div>
        <fieldset>
            <legend>Editar evento</legend>
            <div>
                <p>
                    <label for="foto">Imagen del evento:&nbsp;</label>
                    <a href="<%= SWBPlatform.getWebWorkPath()+rec.getEventImage()%>" target="_self">
                        <img id="img_<%=rec.getId()%>" src="<%= SWBPlatform.getWebWorkPath()+rec.getEventImage() %>" alt="<%= rec.getTitle() %>" border="0" />
                    </a><br />
                    <input type="file" id="foto" name="foto" />
                </p>
                <p>
                    <label for="event_title">Título del evento:&nbsp;</label>
                    <input type="text" id="event_title" name="event_title" value="<%=(rec.getTitle()==null?"":rec.getTitle())%>"/>
                </p>
                <p>
                    <label for="event_description">Descripción del evento:&nbsp;</label>
                    <textarea id="event_description" name="event_description" cols="30" rows="5"><%=(rec.getDescription()==null?"":rec.getDescription())%></textarea>
                </p>
                <p>
                    <label for="event_audience">Dirigido a:&nbsp;</label>
                    <input type="text" id="event_audience" name="event_audience" value="<%=(rec.getAudienceType()==null?"":rec.getAudienceType())%>"/>
                </p>
                <p>
                    <label for="event_startDate">Fecha de inicio:&nbsp;</label>
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_startDate" name="event_startDate" value="<%=(rec.getStartDate()==null?"":dateFormat.format(rec.getStartDate()))%>" constraints="{datePattern:'yyyy/MM/dd'}" onchange="dijit.byId('event_endDate').constraints.min = arguments[0];"/>
                </p>
                <p>
                    <label for="event_endDate">Fecha de término:&nbsp;</label>
                    <input dojoType="dijit.form.DateTextBox" type="text" id="event_endDate" name="event_endDate" value="<%=(rec.getEndDate()==null?"":dateFormat.format(rec.getEndDate()))%>" constraints="{datePattern:'yyyy/MM/dd'}" onchange="dijit.byId('event_startDate').constraints.max = arguments[0];"/>
                </p>
                <p>
                    <label for="event_startTime">Hora de inicio:&nbsp;</label>
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_startTime" name="event_startTime" value="<%=(rec.getStartTime()==null?"":"T" + timeFormat.format(rec.getStartTime()))%>" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" />
                </p>
                <p>
                    <label for="event_endTime">Hora de término:&nbsp;</label>
                    <input dojoType="dijit.form.TimeTextBox" type="text" id="event_endTime" name="event_endTime" value="<%=(rec.getEndTime()==null?"":"T" + timeFormat.format(rec.getEndTime()))%>" constraints="{timePattern:'hh:mm a', visibleRange:'T01:30:00'}" />
                </p>
                <p>
                    <label for="event_place">Lugar del evento:&nbsp;</label>
                    <input type="text" id="event_place" name="event_place" value="<%=(rec.getPlace()==null?"":rec.getPlace())%>"/>
                </p>
                <p>
                    <label for="event_tags">Etiquetas:&nbsp;</label>
                    <input type="text" id="event_tags" name="event_tags" value="<%=(rec.getTags()==null?"":rec.getTags())%>"/>
                </p>
            </div>
        </fieldset>
        <fieldset>
            <legend>¿Quién puede ver este evento?</legend>
            <%String chk = "checked=\"checked\"";%>
            <div>
                <p>
                    <label for="level"><input type="radio" name="level" value="0" <%if(rec.getVisibility()==0)out.println(chk);%> />&nbsp;Cualquiera</label>
                </p>
                <p>
                    <label for="level"><input type="radio" name="level" value="1" <%if(rec.getVisibility()==1)out.println(chk);%> />&nbsp;Sólo los miembros</label>
                </p>
                <p>
                    <label for="level"><input type="radio" name="level" value="3" <%if(rec.getVisibility()==3)out.println(chk);%> />&nbsp;Sólo yo</label>
                </p>
            </div>
        </fieldset>

<%
    SWBResourceURL back = paramRequest.getRenderUrl().setParameter("act", "detail");
    back.setParameter("uri", uri);
    back.setParameter("day", request.getParameter("day"));
    back.setParameter("month", request.getParameter("month"));
    back.setParameter("year", request.getParameter("year"));
%>
        <fieldset>
            <legend></legend>
            <div>
            <p>
                <input type="submit" value="Enviar" />
                <input type="button" value="Cancelar" onclick="window.location='<%= back%>'"/>
            </p>
            </div>
        </fieldset>
    </div>
    <input type="hidden" name="uri" value="<%=rec.getURI()%>"/>
    <input type="hidden" name="act" value="edit"/>
</form>

<script type="text/javascript">
    var img = document.getElementById('img_<%=rec.getId()%>');
    if( img.width>img.height && img.width>350) {
        img.width = 350;
        img.height = 270;
    }else {
        if(img.height>270) {
            img.width = 270;
            img.height = 350;
        }
    }
</script>