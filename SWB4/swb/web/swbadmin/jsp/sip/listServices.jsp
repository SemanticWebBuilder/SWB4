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
String parentWp = (String)request.getAttribute("parentSection");
String defcss = (String)request.getAttribute("defCssIcon");
String maxViews = (String)request.getAttribute("maxServices");
String lang = "es";
int callMethod = paramRequest.getCallMethod();
int maxServices = 6;

WebPage servWp = paramRequest.getWebPage().getWebSite().getWebPage(parentWp);

if (maxViews != null) {
    maxServices = Integer.parseInt(maxViews);
}


if (paramRequest.getUser() != null) {
    lang = paramRequest.getUser().getLanguage();
}

%>
<div class="bloqueNotas">
    <h2 class="tituloBloque">Consulta<span class="titulo_seccion_b">&nbsp;Tr&aacute;mites y servicios</span></h2>
    <%
    if (servWp != null) {
        Iterator<WebPage> childs = servWp.listChilds(lang, true, false, false, false, true);
        if (callMethod == paramRequest.Call_STRATEGY) {
            %>
            <ul class="listaTramites">
                <%
                int c = 0;
                while(childs.hasNext() && c < maxServices) {
                    c++;
                    WebPage child = childs.next();
                    String iconClass = child.getIconClass();
                    if (iconClass == null || iconClass.trim().equals("") || iconClass.equals("null")) {
                        iconClass = defcss;
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