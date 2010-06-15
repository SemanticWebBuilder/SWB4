<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBPortal"%>

<%@page import="org.semanticwb.sieps.Empresa"%>
<%@page import="org.semanticwb.sieps.Producto"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.sieps.search.SearchResource"%>
<%@page import="org.semanticwb.model.SWBModel"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
	//SWBResourceURL url = paramRequest.getRenderUrl().setParameter("act", "results");
	Producto producto = (Producto)request.getAttribute("obj");
    Empresa e = producto.getFabrica();
    String urllog=SWBPortal.getWebWorkPath()+e.getWorkPath()+"/"+e.getLogo();
    String urlphotoprod=SWBPortal.getWebWorkPath()+producto.getWorkPath()+"/"+producto.getFoto();
    SWBResourceURL 	urlGuardaProducto 	= 	paramRequest.getActionUrl().setAction("guardaProductosFicha");
    String mensaje		   	= 	request.getParameter("mensaje")!= null ? request.getParameter("mensaje") : "";
    String query 			= 	(String)request.getAttribute("query");

    SWBResourceURL 	urlResultados		= 	paramRequest.getRenderUrl().setParameter("act", "results").setParameter("query", query);
	/** XXX:Implementación para fines del demo. Replantear funcionalidad para un ambiente productivo...**/
	User user					= 	paramRequest.getUser();
    SWBModel model				=	paramRequest.getWebPage().getWebSite();
	boolean isProductoCarpeta 	= 	SearchResource.isProductosInteres(user, model, producto.getURI());//mensaje.contains("producto");
	boolean isUser				=	(user != null && user.isSigned());

%>
<script type="text/javascript" src="/swbadmin/jsp/sieps/sieps.js"></script>
<div id="columnaDerecha">
    <div id="datos_empresa">
        <img src="<%=urllog%>" width="104" height="89" alt="<%=e.getName()%>"/>
        <div id="descripcion">
            <h2><%=e.getName()%></h2>
            <span id="clave">Clave SIEM: <%=e.getClavesiem()%></span>
            <p><strong>Calle: </strong> <%=e.getAddress()%></p>
            <p><strong>Colonia: </strong> <%=e.getColonia()%></p>
            <p><strong>Municipio: </strong> <%=e.getMunicipio()%></p>
            <p><strong>Estado: </strong> <%=e.getEstado()%></p>
            <p><strong>C.P.: </strong> <%=e.getCp()%></p>
            <p><strong>R.F.C.: </strong> <%=e.getRfc()%></p>
            <p><strong>Correo electrónico:</strong> <%=e.getEmail()%></p>
            <p><strong>Teléfono: </strong> <%=e.getTelefono()%></p>
        </div>
    </div>
    <div class="ficha">
        <div class="detalle">
            <img src="<%=urlphotoprod%>" width="110" height="83" alt="<%=producto.getPname()%>"/>
            <h2>Detalle del producto</h2>
            <dl>
                <dt>Producto:</dt>
                <dd><%=producto.getDisplayTitle(paramRequest.getUser().getLanguage()) %></dd>
                <dt>Proveedor:</dt>
                <dd><%=producto.getFabrica().getName() %></dd>
                <dt>Contacto:</dt>
                <dd>No disponible</dd>
                <dt>Clave:</dt>
                <dd><%=(producto.getUnspsc() != null)? producto.getUnspsc().getUnspsc() : "No disponible"%></dd>
                <dt>Precio unitario:</dt>
                <dd><%=producto.getPrecioprom()%> <%=producto.getUnidad()%></dd>
                <dt>Descripci&oacute;n:</dt>
                <dd><%=producto.getDisplayDescription(paramRequest.getUser().getLanguage())%></dd>
            </dl>
            <form action="" method="post" id="frmProductos" name="frmProductos">
           		<input type="hidden" id="currentQuery" name="currentQuery" value="<%=query%>"/>
            	<input type="hidden" id="uriProductos" name="uriProductos" value="<%=producto.getURI()%>">
                <p id="EnvioFicha">
                	<% if (isUser && !isProductoCarpeta){ %>
                    	<input type="submit" name="search4" id="search4" class="btn-bigger" value="Enviar a mi carpeta" onclick="javascript:enviarProductosInteresFicha('<%=urlGuardaProducto%>', this);"/>
                    <%} %>
                   <!--  
                   <input type="submit" name="search5" id="search5" class="btn-bigger" value="Enviar un mensaje" />
                   --> 
                </p>
            </form>
        </div>
    </div>
    <p id="regresar">
        <a class="btn-large" href="#" onclick="javascript:document.location ='<%=urlResultados%>'">Regresar</a>
    </p>
</div>
<script type="text/javascript">muestraMensaje('<%=mensaje%>');</script>