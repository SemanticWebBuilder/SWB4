
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%>
<%@page import="org.semanticwb.sieps.Empresa"%>

<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.sieps.search.SearchResource"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.hp.hpl.jena.rdf.model.*"%>
<script type="text/javascript">
	function desplieguaDescripcion(idAnchor, idCell) {
		var cell = document.getElementById(idCell);
		if (cell != null) {			
			var modDispl  = cell.style.display;
			if (modDispl == "block") {
				cell.style.display = "none";
				cambiaEstadoDisplayAllDescrip('formTableRes');
				var objAnchor = document.getElementById(idAnchor);
				objAnchor.innerHTML = '<img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" /> Abrir detalle';
								
			} else {
				cell.style.display = "block";
				var objAnchor = document.getElementById(idAnchor);
				objAnchor.innerHTML = '<img src="/work/models/sieps/Template/2/1/images/bulletGris_tabla.jpg" alt=" " width="10" height="10" /> Cerrar detalle';
			}
		}
		return;
	}
	function desplieguaTodasDescripcion(objChk) {
		var displayType = (objChk.checked) ? "block" : "none";
		var table = document.getElementById("tablaResultados");
		var rows  = table.rows;
		for (var i = 0; i<rows.length; i++) {
			var row = rows[i];
			//rowBullets
            //cellBulletDescrip
			if (row != null && row.id.indexOf("rowBullets")!= -1 ) {
				var cells = row.cells;
				
				cells[0].children[0].innerHTML = (displayType == 'block')
															? '<img src="/work/models/sieps/Template/2/1/images/bulletGris_tabla.jpg" alt=" " width="10" height="10" /> Cerrar detalle'
															: '<img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" /> Abrir detalle';		
			}
            			
			if (row != null && row.id.indexOf("rowDescrip")!= -1 ) {
				var cells = row.cells;
				cells[0].style.display = displayType;		
			}
		}
		return;
	}

	function cambiaEstadoDisplayAllDescrip(idForm) {		
		var forma 	= document.forms[idForm]
		var objChkAll = forma.elements["checkAllDescrip"];		
		objChkAll.checked  =false;
				
		return;
	}	
		
</script>

<script type="text/javascript" src="/swbadmin/jsp/sieps/sieps.js"></script>
<%
	String query 						= 	request.getParameter("query");
	String queryAttr 					= 	(String)request.getAttribute("query");
	SWBResourceURL 	urlDetail 			= 	paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	SWBResourceURL 	urlGuardaBusqueda 	= 	paramRequest.getActionUrl().setAction("guardaConsulta"),
					urlGuardaProductos 	= 	paramRequest.getActionUrl().setAction("guardaProductos");
	
	List<Producto> productos 			= 	(List<Producto>)request.getAttribute("results");
	Boolean isAllProdInt 				= 	(Boolean)request.getAttribute("isAllProdInt");
	
	boolean isResultados   				= 	(productos != null &&  !productos.isEmpty());
	String mensaje		   				= 	request.getParameter("mensaje")!= null ? request.getParameter("mensaje") : "";
	
	/** XXX:Implementación para fines del demo. Replantear funcionalidad para un ambiente productivo...**/
	boolean isBusquedaCarpeta 	= 	mensaje.contains("búsqueda");	
			mensaje 			= 	isBusquedaCarpeta ? mensaje : "";

	SWBModel webSite			=	paramRequest.getWebPage().getWebSite();
	User user					= 	paramRequest.getUser();
	boolean isUser				=	(user != null && user.isSigned());
	
	String urlImages			=	"/swbadmin/jsp/"+paramRequest.getWebPage().getWebSiteId()+"/images/";
%>
<div id="resultadosBusqueda">
<% if (queryAttr != null && queryAttr.length() > 0) { %>
	<p id="resultadosBusquedaTop"><strong>Usted buscó:</strong>Usted buscó:<%=queryAttr%></p>
<% } %>
<% if (isResultados) {%>
      <h2 class="tableH2">Resultados de búsqueda</h2>
      <form id="formTableRes" method="post" action="">
      <input type="hidden" id="currentQuery" name="currentQuery" value="<%=queryAttr%>"/>
        <p>
          <input type="checkbox" name="checkAllDescrip" id="checkAllDescrip"  onclick="javascript:desplieguaTodasDescripcion(this);"/>
          <label for="check1">Vista breve de todos los resultados</label>
          <% if (isUser && !isAllProdInt) { %>	
	          <input type="checkbox" name="checkAllProductos" id="checkAllProductos"  onclick="javascript:desplieguaTodosProductos(this);"/>
	          <label for="check4">Selecciona el producto</label>
          <% } %>
        </p>
      
	  <table id="tablaResultados">
          <tr>
            <th>Código</th>
            <th>Producto</th>
            <th>Categoría</th>
            <th>Empresa</th>
            <th colspan="2">Ubicación</th>
          </tr>
          <%          	
          	for (int i = 0; i< productos.size(); i++) {
        	  Producto p = productos.get(i);
        	  Empresa f = p.getFabrica();
        	  if (f == null){
        		  continue;
        	  }
        	  String estiloRow = (i % 2 == 0) ? "odd" : "even";
              String codigo="",subcategoria="";
                  
                  if(p.getUnspsc()!=null && p.getUnspsc().getUnspsc()!=null)
                      {
                        codigo=p.getUnspsc().getUnspsc();
                      }
                  if(p.getUnspsc()!=null)
                  {
                        subcategoria=p.getUnspsc().getSemanticObject().getLabel(paramRequest.getUser().getLanguage());
                    }
          %>  
          	<tr>          		
	            <td onclick='javascript: document.location ="<%=urlDetail.setParameter("uri", p.getEncodedURI())%>"' style="cursor: hand;" class="<%=estiloRow%> bold"><%=codigo%></td>
	            <td onclick='javascript: document.location ="<%=urlDetail.setParameter("uri", p.getEncodedURI())%>"' style="cursor: hand;" class="<%=estiloRow%> bold"><%=p.getTitle()%></td>
	            <td class="<%=estiloRow%> bold"><%=subcategoria%></td>
	            <td onclick='javascript: document.location ="<%=urlDetail.setParameter("uri", f.getEncodedURI())%>"' style="cursor: hand;" class="<%=estiloRow%> bold"><%=(f != null)? f.getName(): "No disponible"%></td>
	            <td class="<%=estiloRow%> bold"><%=(f != null)? f.getEstado(): "No disponible"%></td>
	            <td class="<%=estiloRow%>">            
            	<% if (isUser && !SearchResource.isProductosInteres(user, webSite, p.getURI())) {%>
              		<input type="checkbox" name="uriProductos" id="uriProductos" value="<%=p.getURI()%>" onclick="javascript:cambiaEstadoSelectAllProductos(this);"/>
            	<% } else if (isUser){%>
            		<img src="<%=urlImages%>favorites.png" width="16" height="16" alt="" />
            	<% } else { %>
            		&nbsp;
            	<% } %>
	           </td> 	            	          
           </tr>
           <tr id="rowBullets<%=i%>">
	            <td id="cellBulletDescrip<%=i%>" class="<%=estiloRow%>"><a id="anchor<%=i%>" href="javascript:desplieguaDescripcion('anchor<%=i%>', 'cellDescrip<%=i%>');");"><img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" />Abrir detalle</a></td>	            
	            <td colspan="4" class="<%=estiloRow%>">&nbsp;</td>
          </tr>
          <tr id="rowDescrip<%=i%>">
            <td id="cellDescrip<%=i%>" class="<%=estiloRow%> tableHide" colspan="6"><%=p.getDescription()%></td>
          </tr>
		  <%}%>
	</table>
	<p class="centrar">
	<% if (isUser && !isAllProdInt){ %>
    	<input type="button" name="btnSendProducto" id="btnSendProducto" value="Enviar Producto(s) a mi Carpeta" class="btn-bigger" onclick="javascript:enviarProductosInteres('<%=urlGuardaProductos%>', this); "/>
    <% } %>		
	<% if (isUser && !isBusquedaCarpeta){ %>    	
    	<input type="button" name="btnSendConsulta" id="btnSendConsulta" value="Enviar Consulta a mi Carpeta" class="btn-bigger" onclick="javascript:enviarBusquedas('<%=urlGuardaBusqueda%>', this); "/>
    <% } %>	
	</p>  			
	</form>  	        	 
<%} else {%>
	<h2 class="tableH2">No se encontraron coincidencias</h2>	
<%}%>
<script type="text/javascript">muestraMensaje('<%=mensaje%>');</script>
</div>