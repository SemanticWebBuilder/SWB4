

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBPortal"%>

<%@page import="org.semanticwb.sieps.Empresa"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%
	SWBResourceURL url = paramRequest.getRenderUrl().setParameter("act", "results");
	Empresa e = (Empresa)request.getAttribute("obj");
	Iterator<Empresa> iterEmpresasSimi	=	Empresa.ClassMgr.listEmpresaByScian(e.getScian());
	String urllog=SWBPortal.getWebWorkPath()+e.getWorkPath()+e.getLogo();
	System.out.println("---> urllog = " + urllog);
%>
<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key="></script>
<script type="text/javascript" src="/swbadmin/jsp/sieps/googleMaps.js"></script>

<div id="datos_empresa">
     	<img src="<%=urllog%>" width="104" height="89" alt="Grupo Plus"/>
        <div id="descripcion">
	    	<h2><%=e.getName()%></h2>
            <span id="clave">Clave: <%=e.getClavesiem()%></span>
            <p><strong>Calle</strong><%="No disponible"%></p>
            <p><strong>Colonia</strong><%=e.getColonia()%></p>
            <p><strong>Municipio</strong>"No disponible"</p>
            <p><strong>Estado</strong><%=e.getEstado()%></p>
            <p><strong>C.P.</strong><%=e.getCp()%></p>
            <p><strong>R.F.C.</strong><%=e.getRfc()%></p>
            <p><strong>Correo electrónico</strong><%=e.getEmail()%></p>
            <p><strong>Teléfono</strong><%=e.getTelefono()%></p>
        </div>
</div>
<div id="columnaCentro">
  <!-- Inicio Mapa -->
<div id="map_canvas" style="margin-top: 20px; margin-left: 20px;width:395px;height:266px"></div>						
			<script type="text/javascript">initialize(19.2931282, -99.1701111, 14);</script>
<!-- Fin Mapa -->
  
  <h3 class="first">Descripción de la empresa</h3>
  <p><%= (e.getDescripcion() != null )? e.getDescripcion() : "No disponible"  %></p>
</div>
<div class="panelDerechoA">
	<h4>Conocer más de esta empresa</h4>
    <ul>
        <li><a href="#">Ver su galería fotográfica</a></li>
        <li><a href="#">Visitar su página web</a></li>
        <li><a href="#">Ver su video corporativo</a></li>
    </ul>
</div>
<div class="panelDerechoB">
	<h4>Empresas similares de la industria</h4>
	<ul>
		<% while (iterEmpresasSimi.hasNext()) {
		Empresa emp = iterEmpresasSimi.next();
		%>
			<li><a href="#"><%=emp.getName()%></a><span><%=emp.getEstado()%></span></li>
		<%} %>
		</ul>     
         <form action="#" >
       		<input type="submit" name="buscar2" id="buscar" class="panel_btn" value="Buscar" />
         </form>
</div>
 <div class="panelDerechoB">
 	<h4>Ofertas relacionadas a tu empresa</h4>
     <ul>
         <li><a href="#">Botellas de vidrio</a></li>
         <li><a href="#">Botellas de plástico</a></li>
         <li><a href="#">Tarimas</a></li>
         <li><a href="#">Cajas de cartón</a></li>
         <li><a href="#">Papel</a></li>
         <li><a href="#">Impresiones publicitarias</a></li>
         <li><a href="#">Transporte de materia prima</a></li>
     </ul>
     <form action="#">
   <input type="submit" name="buscar2" id="buscar" class="panel_btn" value="Buscar" />
     </form>
 </div>
<div id="industria">
       <h3>Industria a la que pertenece esta empresa</h3>
       <ul>
           <li class="first"><span class="codigo">Código SCIAN</span><span class="descripcion">DESCRIPCIÓN</span></li>
           <li class="a"><span class="codigo">312</span><a href="#"><span class="descripcion">Industria de las bebidas y el tabaco</span></a>
             <ul>
               <li class="b"><span class="codigo">3121</span><a href="#"><span class="descripcion">Industria de las bebidas y el tabaco</span></a>
                 <ul>
                   <li class="a"><span class="codigo">31211</span><a href="#"><span class="descripcion">Industria de las bebidas y el tabaco</span></a>
                     <ul>
                       <li class="b"><span class="codigo">312111</span><a href="#"><span class="descripcion">Industria de las bebidas y el tabaco</span></a></li>
                       <li class="a"><span class="codigo">312112</span><a href="#"><span class="descripcion">Industria de las bebidas y el tabaco</span></a></li>
                       <li class="b"><span class="codigo">312113</span><a href="#"><span class="descripcion">Industria de las bebidas y el tabaco</span></a></li>
                     </ul>
                   </li>
                 </ul>
               </li>
             </ul>
           </li>
       </ul>
</div>	
    	<form action="#" enctype="multipart/form-data">
          <p id="Envio">
            <input type="submit" name="search" id="search" value="Enviar a mi carpeta" />
            <input type="submit" name="search" id="search" value="Enviar un mensaje" />
          </p>
        </form>


