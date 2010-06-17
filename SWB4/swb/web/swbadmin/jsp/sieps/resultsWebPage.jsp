
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.sieps.Empresa"%>
<%
	String query 				= 	request.getParameter("query");
	String queryAttr 			= 	(String)request.getAttribute("query");
	SWBResourceURL urlDetail 	= 	paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	List<SemanticObject> webpages 		= 	(List<SemanticObject>)request.getAttribute("results");
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
	        	<% for (SemanticObject so : webpages) {
                                if (so.instanceOf(WebPage.sclass)) {
                                    WebPage wp = (WebPage)so.createGenericInstance();
                                %>
                                    <h3><%=wp.getDisplayTitle(userLang)%></h3>
                                    <p><strong>Pagina Web:</strong><br/>
                                        <a href="<%=wp.getUrl()%>"><%=wp.getTitle()%></a>
                                    </p>
                                    <% } else if (so.instanceOf(Empresa.sclass)) {
                                        Empresa e = (Empresa)so.createGenericInstance();
                                        String reswp = paramRequest.getWebPage().getWebSite().getWebPage("Resultados_Empresas").getUrl();
                                        reswp += "?query=empresas&act=detail&uri=" + e.getEncodedURI();
                                        %>
                                    <h3><%=e.getName()%></h3>
                                    <p><strong>Empresa:</strong><br/>
                                        <a href="<%=reswp%>"><%=e.getDescripcion()%></a>
                                    </p>
                                    <%
                                       } else if (so.instanceOf(Producto.sclass)) {
                                        Producto p = (Producto)so.createGenericInstance();
                                        String reswp = paramRequest.getWebPage().getWebSite().getWebPage("Resultados_Empresas").getUrl();
                                        reswp += "?query=productos&act=detail&uri=" + p.getEncodedURI();
                                        %>
                                    <h3><%=p.getTitle()%></h3>
                                    <p><strong>Producto:</strong><br/>
                                        <a href="<%=reswp%>"><%=p.getDescription()%></a>
                                    </p>
                                    <%
                                       }
                        }%>
	        </div>
        </div>
	</div>        
<%} else {%>
	<h2 class="tableH2">No se encontraron coincidencias</h2>
<%}%>