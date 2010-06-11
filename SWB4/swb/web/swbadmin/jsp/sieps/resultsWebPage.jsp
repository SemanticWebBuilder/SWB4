
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<%
	String query 				= 	request.getParameter("query");
	String queryAttr 			= 	(String)request.getAttribute("query");
	SWBResourceURL urlDetail 	= 	paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	List<WebPage> webpages 		= 	(List<WebPage>)request.getAttribute("results");
	boolean isResultados   		= 	(webpages != null &&  !webpages.isEmpty());
	String userLang				=	paramRequest.getUser().getLanguage();
%>
<% if (queryAttr != null && queryAttr.length() > 0) { %>
	<p>Usted buscó:<%=queryAttr%></p>
<% } %>
<% if (isResultados) {%>
	<div id="resultadosBusqueda">
	    <h2 class="tableH2">Resultados de la busqueda</h2>
        <div id="resultadosHead">
	        <p>No se encontraron resultados conicidentes a su b&uacute;squeda en nuestro sistema.</p>
	        <p><strong>Le ofrecemos el siguiente listado de sitios similares para facilitar su b&uacute;squeda.</strong></p>
        </div>
        <div id="resultadosBody">
	        <div class="resultado">
	        	<% for (WebPage webPage : webpages) { %>
		            <h3><%=webPage.getDisplayTitle(userLang)%></h3>
		            <p><strong>Pagina Web:</strong><br/>
		            <a href="<%=webPage.getUrl()%>"><%=webPage.getTitle()%></a>
		            </p>
		        <% } %>    
	        </div>
        </div>
	</div>        
<%} else {%>
	<h2 class="tableH2">No se encontraron coincidencias</h2>
<%}%>