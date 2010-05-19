<%-- 
    Document   : listServices
    Created on : 18/05/2010, 11:04:23 AM
    Author     : Hasdai Pacheco {haxdai@gmail.com}
--%>
<%@page import="org.semanticwb.SWBPortal" %>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.Iterator"%>

<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
WebPage servWp = paramRequest.getWebPage().getWebSite().getWebPage("Tramites_y_Servicios");
int callMethod = paramRequest.getCallMethod();
%>
<div class="bloqueNotas">
    <h2 class="tituloBloque">Consulta<span class="titulo_seccion_b">&nbsp;Tr&aacute;mites y servicios</span></h2>
    <%
    if (servWp != null) {
        Iterator<WebPage> childs = servWp.listChilds();
        if (callMethod == paramRequest.Call_STRATEGY) {
            %>
            <ul class="listaTramites">
                <%
                while(childs.hasNext()) {
                    WebPage child = childs.next();
                    String iconClass = child.getIconClass();
                    if (iconClass == null || iconClass.trim().equals("") || iconClass.equals("null")) {
                        iconClass="icono_8";
                    }
                    %>
                    <li><a href="<%=child.getUrl()%>"><span class="<%=iconClass%>">&nbsp;</span><%=child.getTitle()%></a></li>
                    <%
                }
                %>
            </ul>
            <p class="vermas"><a href="<%=servWp.getUrl()%>">Ver m&aacute;s tr&aacute;mites y servicios</a></p>
            <%
        }
    }
    %>
</div>