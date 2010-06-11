
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<script type="text/javascript" src="/swbadmin/jsp/sieps/sieps.js"></script>
<%
	SWBResourceURL urlBuscaEmpresa 	= 	paramRequest.getRenderUrl().setCallMethod(SWBParamRequest.Call_DIRECT).setMode("EMPRESA"),
				   urlDesasocia		=	paramRequest.getRenderUrl().setMode("DESASOCIA");
	
	String mensajeUsr  = (String)request.getAttribute("mensaje");
	boolean isMensaje  = (mensajeUsr != null && mensajeUsr.length() > 0);
%>  
  	
  	<% if (!isMensaje) {%>
	    <form id="frmAltaCert" name="frmAltaCert" method="post" action="">
	    	<div id="cupon">
	        	<div id="cuponHead">
	                <label for="idCertificado">N&uacute;mero certificado:</label>
	                <input type="text" name="idCertificado" id="idCertificado" value="" />
	                <input type="button" value="Confirmar" name="confirmar" id="confirmar" class="btn-small" onclick="javascript:buscaEmpresa('<%=urlBuscaEmpresa%>', this);"/>
	                <input type="button" value="Borrar" name="borrar" class="btn-small" onclick="javascript:this.form.reset(); this.form.elements['confirmar'].disabled = false;"/>
	            </div>
	          <div id="cuponBody"></div>
	          <div id="cuponConfirmacion" style="display: none;"></div>
	        </div>
	        </form>
	<% } else {%>
		<h2 class="tableH2"><%=mensajeUsr%></h2>
	<% } %>