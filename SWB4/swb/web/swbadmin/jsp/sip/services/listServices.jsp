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
WebPage srvWp = paramRequest.getWebPage().getWebSite().createWebPage("Tramites_y_Servicios");
Iterator<Service> services = (Iterator<Service>) request.getAttribute("servList");
SWBResourceURL addUrl = paramRequest.getRenderUrl().setMode("ADD");
%>
<div class="bloqueNotas">
    <h2 class="tituloBloque">Consulta<span class="span_tituloBloque"> Tr&aacute;mites y servicios</span></h2>
    <p>
        <a href="<%=addUrl%>#addService"><font size="2pt">Agregar tr&aacute;mite o servicio</font></a>
    </p>
    <ul class="listaTramites">
        <%
            while(services.hasNext()) {
                Service service = services.next();
                SWBResourceURL delUrl = paramRequest.getActionUrl().setAction("DEL").setParameter("uri", service.getEncodedURI());
                %>
                <li>
                    <a href="<%=service.getUrl()%>"><span class="<%=service.getCssIcon()%>">&nbsp;</span><%=service.getTitle()%></a>
                    [<a href="<%=delUrl%>"><font size="2pt">Eliminar</font></a>]
                </li>
                <%
            }
        %>
    </ul>
    <p class="vermas"><a href="<%=srvWp.getUrl()%>">Ver m&aacute;s tr&aacute;mites y servicios</a></p>
</div>