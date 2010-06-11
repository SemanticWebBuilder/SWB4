
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%>
<%@page import="org.semanticwb.sieps.Empresa"%>

<%@page import="org.semanticwb.model.User"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
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
	function desplieguaTodasEmpresas(objChk) {
		var checkedType = (objChk.checked) ? true : false;
		var forma 	= objChk.form

		for (var i = 0; i<forma.elements.length; i++) {
			var e = forma.elements[i];
			if ((e.id.indexOf("checkEmp") != -1) && (e.type=='checkbox')) {
				e.checked = checkedType;
			}								
		}
		
		return;
	}
	function cambiaEstadoSelectAllEmpresas(objChk) {
		var checkedType = (objChk.checked) ? true : false;
		var forma 	= objChk.form
		var objChkAll = forma.elements["checkAllEmpresas"];
		if (objChkAll.checked && !checkedType) {
			objChkAll.checked  =false;
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
	SWBResourceURL 	urlGuardaBusqueda 	= 	paramRequest.getActionUrl().setAction("guardaConsulta");
	
	List<Producto> productos 			= 	(List<Producto>)request.getAttribute("results");
	boolean isResultados   				= 	(productos != null &&  !productos.isEmpty());
	String mensaje		   				= 	request.getParameter("mensaje")!= null ? request.getParameter("mensaje") : "";
	
	/** XXX:Implementación para fines del demo. Replantear funcionalidad para un ambiente productivo...**/
	boolean isBusquedaCarpeta 	= 	mensaje.contains("búsqueda");	
			mensaje 			= 	isBusquedaCarpeta ? mensaje : "";

	User user					= 	paramRequest.getUser();
	boolean isUser				=	(user != null && user.isSigned());
%>
<% if (queryAttr != null && queryAttr.length() > 0) { %>
	<p>Usted buscó:<%=queryAttr%></p>
<% } %>
<% if (isResultados) {%>
      <h2 class="tableH2">Resultados de búsqueda</h2>
      <form id="formTableRes" method="post" action="">
      <input type="hidden" id="currentQuery" name="currentQuery" value="<%=query%>"/>
        <p>
          <input type="checkbox" name="checkAllDescrip" id="checkAllDescrip"  onclick="javascript:desplieguaTodasDescripcion(this);"/>
          <label for="check1">Vista breve de todos los resultados</label>
        </p>
      
	  <table id="tablaResultados">
          <tr>
            <th>Código</th>
            <th>Producto</th>
            <th>Subcategoría</th>
            <th>Empresa</th>
            <th>Ubicación</th>
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
	<% if (isUser && !isBusquedaCarpeta){ %>    	
    	<input type="button" name="btnSendConsulta" id="btnSendConsulta" value="Enviar Consulta a mi Carpeta" class="btn-bigger" onclick="javascript:enviarBusquedas('<%=urlGuardaBusqueda%>', this); "/>
    <% } %>	
	</p>  			
	</form>  	        	 
<%} else {%>
	<h2 class="tableH2">No se encontraron coincidencias</h2>	
<%}%>
<script type="text/javascript">muestraMensaje('<%=mensaje%>');</script>