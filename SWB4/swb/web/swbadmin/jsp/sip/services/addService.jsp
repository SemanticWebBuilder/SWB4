<%-- 
    Document   : addService
    Created on : 18/05/2010, 04:26:58 PM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>
<%@page import="org.semanticwb.SWBPortal" %>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.User"%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
User user = paramRequest.getUser();
SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
SWBResourceURL addUrl = paramRequest.getActionUrl().setAction("ADD");
%>
<div class="bloqueNotas">
    <h2 class="tituloBloque">Consulta<span class="span_tituloBloque"> Tr&aacute;mites y servicios</span></h2>
<%
    if (true/*user.isRegistered() && user.isSigned()*/) {
    %>
    <p id="addService">
        <b>Agregar nuevo tr&aacute;mite o servicio</b>
    </p>
        <form action="<%=addUrl%>" method="post">
            <p>
            <label for="title">T&iacute;tulo:&nbsp;</label>
            <input type="text" name="title">
            </p>
            <p>
            <label for="link">URL:&nbsp;</label>
            <input type="text" name="link">
            </p>
            <p>
            <label for="cssIcon">Icono:&nbsp;</label>
            <input type="text" name="cssIcon">
            </p>
            <input type="submit" value="Guardar">
            <input type="button" value="Cancelar" onclick="window.location='<%=viewUrl%>';">
        </form>
    <%
    } else {
        %>
        <p id="addService">
            <font color="Red">Debe estar registrado para agregar nuevos servicios</font>
        </p>
        <p>
            <a href="<%=viewUrl%>">Regresar</a>
        </p>
        <%
    }
%>
</div>