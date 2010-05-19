<%-- 
    Document   : listServices
    Created on : 18/05/2010, 11:04:23 AM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>
<%@page import="org.semanticwb.SWBPortal" %>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.sip.tservices.*"%>


<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
Iterator<Service> services = (Iterator<Service>) request.getAttribute("servList");
SWBResourceURL addUrl = paramRequest.getRenderUrl().setMode("ADD");
%>
<div class="bloqueNotas">
    <h2 class="tituloBloque">Consulta<span class="span_tituloBloque"> Tr&aacute;mites y servicios</span></h2>
    <p>
        <a href="<%=addUrl%>">Agregar tr&aacute;mite o servicio</a>
    </p>
    <ul class="listaTramites">
        <%
            while(services.hasNext()) {
                Service service = services.next();
                %>
                <li>
                    <a href="<%=service.getUrl()%>"><span class="<%=service.getCssIcon()%>">&nbsp;</span><%=service.getTitle()%></a>
                </li>
                <%
            }
        %>
    </ul>
    <p class="vermas"><a href="#">Ver m&aacute;s tr&aacute;mites y servicios</a></p>
</div>