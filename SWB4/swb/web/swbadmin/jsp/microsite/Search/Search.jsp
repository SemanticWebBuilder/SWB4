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
            <input id="busqueda_comercios" type="checkbox" checked="checked" name="comercios"/>
            <label for="busqueda_comercios">Comercios</label>
            <input id="busqueda_organizaciones" type="checkbox" name="organizaciones"/>
            <label for="busqueda_organizaciones">Organizaciones</label>
            <input id="busqueda_persons" type="checkbox" name="personas"/>
            <label for="busqueda_persons">Personas</label>
            <input id="busqueda_clasificados" type="checkbox" name="clasificados"/>
            <label for="busqueda_clasificados">Clasificados</label>
        </div>
    </form>
</div>
<%
}
            if(results != null && results.hasNext()){
                System.out.println("Hay resultados");
            }
%>