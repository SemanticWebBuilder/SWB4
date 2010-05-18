<%-- 
    Document   : listServices
    Created on : 18/05/2010, 11:04:23 AM
    Author     : hasdai
--%>
<%@page import="org.semanticwb.SWBPortal" %>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.Iterator"%>


<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
WebPage servWp = paramRequest.getWebPage().getWebSite().getWebPage("Tramites_y_Servicios");
int callMethod = paramRequest.getCallMethod();

if (servWp != null) {
    Iterator<WebPage> childs = servWp.listChilds();
    if (callMethod == paramRequest.Call_STRATEGY) {
        %>
        <div class="titulo_seccion">
            Consulta
            <span class="titulo_seccion_b">Tr&aacute;mites y servicios</span>
        </div>
        <div class="lista_con_iconos_A">
            <ul>
                <%
                while(childs.hasNext()) {
                    WebPage child = childs.next();
                    %>
                    <li>
                        <a href="<%=child.getUrl()%>">
                            <span class="icono_6">&nbsp;</span>
                            <%=child.getTitle()%>
                        </a>
                    </li>
                    <%
                }
                %>
                <li class="derecha">
                    <a href="<%=servWp.getUrl()%>">
                        <img border="0" src="/work/models/sip/archivos/bullet.gif">
                        ver m&aacute;s tr&aacute;mites y servicios
                        <span class="nota_8">&nbsp;</span>
                    </a>
                </li>
            </ul>
        </div>
        <%
    }
}
%>
