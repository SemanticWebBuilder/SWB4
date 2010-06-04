
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.sieps.Producto"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.hp.hpl.jena.rdf.model.*"%>
<style>
	div#qTip {
	 padding: 3px;
	 border: 1px solid #666;
	 border-right-width: 2px;
	 border-bottom-width: 2px;
	 display: none;
	 background: #999;
	 color: #FFF;
	 font: bold 9px Verdana, Arial, sans-serif;
	 text-align: left;
	 position: absolute;
 	 z-index: 1000;
	 
	}
</style>
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

<script type="text/javascript" src="/swbadmin/jsp/sieps/qtips.js"></script>
<%
	String query = request.getParameter("query");
	SWBResourceURL urlDetail = paramRequest.getRenderUrl().setParameter("act", "detail").setParameter("query", query);
	List<Producto> productos = (List<Producto>)request.getAttribute("results");
	boolean isResultados   = (productos != null &&  !productos.isEmpty());
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
      
	  <table id="tablaResultados">
          <tr>
            <th>Código</th>
            <th>Producto</th>
            <th>Subcategoría</th>
            <th>Empresa</th>
            <th colspan="2">Ubicación</th>
          </tr>
          <%          	
          	for (int i = 0; i< productos.size(); i++) {
        	  Producto p = productos.get(i);
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
          	<tr onclick='javascript: document.location ="<%=urlDetail.setParameter("uri", p.getEncodedURI())%>"' style="cursor: hand;">          		
	            <td class="<%=estiloRow%> bold"><%=codigo%></td>
	            <td class="<%=estiloRow%> bold"><%=p.getTitle()%></td>
	            <td class="<%=estiloRow%> bold"><%=subcategoria%></td>
	            <td class="<%=estiloRow%> bold"><%=p.getFabrica().getName()%></td>
	            <td class="<%=estiloRow%> bold"><%=p.getFabrica().getEstado()%>
	            	<span class="<%=estiloRow%>">
	              		<input type="checkbox" name="checkEmp<%=i%>" id="checkEmp<%=i%>" onclick="javascript:cambiaEstadoSelectAllEmpresas(this);"/>
	            	</span>
	            </td>            
	            <td class="<%=estiloRow%>">&nbsp;</td>	          
           </tr>
           <tr id="rowBullets<%=i%>">
	            <td id="cellBulletDescrip<%=i%>" class="<%=estiloRow%>"><a id="anchor<%=i%>" href="javascript:desplieguaDescripcion('anchor<%=i%>', 'cellDescrip<%=i%>');");"><img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" />Abrir detalle</a></td>
	            <td class="<%=estiloRow%>"><a href="#" title='  <iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=es&amp;geocode=&amp;q=Toriello+Guerra,+Tlalpan,+M%C3%A9xico&amp;sll=37.0625,-95.677068&amp;sspn=47.885545,79.013672&amp;ie=UTF8&amp;hq=&amp;z=14&amp;ll=19.29476,-99.166373&amp;output=embed"></iframe>'><img src="/work/models/sieps/Template/2/1/images/bulletVerde_tabla.jpg" alt=" " width="10" height="10" /> Ver mapa</a>
	            </td>
	            <td colspan="4" class="<%=estiloRow%>">&nbsp;</td>
          </tr>
          <tr id="rowDescrip<%=i%>">
            <td id="cellDescrip<%=i%>" class="<%=estiloRow%> tableHide" colspan="6"><%=p.getDescription()%></td>
          </tr>
		  <%}%>
	</table>	
	</form>  	        	 
<%} else {%>
	<p>No se encontraron coincidencias</p>
<%}%>