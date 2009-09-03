<%@page import="java.text.SimpleDateFormat, org.semanticwb.portal.resources.sem.BookmarkEntry, org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Iterator<String> results = (Iterator<String>) request.getAttribute("results");
            SWBResourceURL searchUrl = paramRequest.getActionUrl().setAction("search");
%>

<%
if (paramRequest.getCallMethod() != paramRequest.Call_STRATEGY) {
%>
<div id="busqueda">
    <form id="busqueda_form" action="<%=searchUrl%>" method="post">
        <p>
            <input id="busqueda_input" type="text" name="q"/>
            <input id="busqueda_enviar" type="submit" value="Submit" name="button"/>
        </p>
        <div>
            <label for="busqueda_personas">Personas</label>
            <input id="busqueda_personas" type="checkbox" checked="checked" name="personas"/>
            <label for="busqueda_empresas">Empresas</label>
            <input id="busqueda_empresas" type="checkbox" name="empresas"/>
        </div>
    </form>
</div>
<%
}
            if(results != null && results.hasNext()){
                System.out.println("Hay resultados");
            }
%>

