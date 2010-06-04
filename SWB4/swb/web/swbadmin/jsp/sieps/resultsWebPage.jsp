
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<%
	String query = request.getParameter("query");
	SWBResourceURL urlDetail = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	List<WebPage> webpages = (List<WebPage>)request.getAttribute("results");
	boolean isResultados   = (empresas != null &&  !empresas.isEmpty());
%>
<% if (isResultados) {%>
      <h2 class="tableH2">Resultados de búsqueda</h2>
      <form id="formTableRes" method="post" action="">
        <p>
          <input type="checkbox" name="checkAllDescrip" id="checkAllDescrip"  onclick="javascript:desplieguaTodasDescripcion(this);"/>
          <label for="check1">Vista breve de todos los resultados</label>
          <input type="checkbox" name="checkAllEmpresas" id="checkAllEmpresas"  onclick="javascript:desplieguaTodasEmpresas(this);"/>
          <label for="check4">Selecciona la empresa</label>
        </p>

	 
          <%
          	for (int i = 0; i< webpages.size(); i++) {
        	  WebPage p = webpages.get(i);
        	 String url=p.getUrl();
                 String title=p.getTitle();
          %>
          <p><a href="<%=url%>"><%=title%></a></p>
		  <%}%>
	
	</form>
<%} else {%>
	<p>No se encontraron coincidencias</p>
<%}%>