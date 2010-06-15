

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.scian.*"%>
<%@page import="org.semanticwb.sieps.*"%>
<%@page import="org.semanticwb.sieps.search.SearchResource"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%!
    public String getLabel(SemanticObject obj,SemanticClass clazz,User user)
    {
        String getLabel="";
        org.semanticwb.platform.SemanticProperty prop=obj.getModel().getSemanticProperty(org.semanticwb.platform.SemanticVocabulary.RDFS_SUBCLASSOF);
        org.semanticwb.platform.SemanticObject parent=null;
        Iterator<org.semanticwb.platform.SemanticObject> parents=obj.listObjectProperties(prop);
        while(parents.hasNext())
        {
            org.semanticwb.platform.SemanticObject temp=parents.next();
            if(clazz.equals(temp.getSemanticClass()))
            {
                parent=temp;
                break;
            }
        }

        if(parent!=null)
        {
            getLabel=parent.getLabel(user.getLanguage());
        }
        return getLabel;
    }
    public String getCode(SemanticObject obj,SemanticClass clazz)
    {
        String getCode="";
        org.semanticwb.platform.SemanticProperty prop=obj.getModel().getSemanticProperty(org.semanticwb.platform.SemanticVocabulary.RDFS_SUBCLASSOF);
        org.semanticwb.platform.SemanticObject parent=null;
        Iterator<org.semanticwb.platform.SemanticObject> parents=obj.listObjectProperties(prop);
        while(parents.hasNext())
        {
            org.semanticwb.platform.SemanticObject temp=parents.next();
            if(clazz.equals(temp.getSemanticClass()))
            {
                parent=temp;
                break;
            }
        }

        if(parent!=null)
        {
            Sector sector=new Sector(parent);
            getCode=sector.getCode();
        }
        return getCode;
    }
%>
<%
	Empresa e 					= 	(Empresa)request.getAttribute("obj");
	String empresaURI			=	"";
	SWBResourceURL url 			= 	paramRequest.getRenderUrl().setParameter("act", "results"),
				   urlCatalogo	=	paramRequest.getRenderUrl().setParameter("act", "cat"),
				   urlSimilares	=	paramRequest.getRenderUrl().setParameter("act", "empresassimilares");
	
	SWBResourceURL urlGuardaEmpresas 	= 	paramRequest.getActionUrl().setAction("guardaEmpresasFicha");
	
	if (e != null) {
		urlCatalogo.setParameter("uri", e.getURI());
		urlSimilares.setParameter("uri", e.getURI());
		empresaURI	=	e.getURI();
	}
	
	Iterator<Empresa> iterEmpresasSimi	=	Empresa.ClassMgr.listEmpresaByScian(e.getScian());
	String urllog=SWBPortal.getWebWorkPath()+e.getWorkPath()+"/"+e.getLogo();

        String numclase=e.getScian().getCode();
        String numsubrama=getCode(e.getScian().getSemanticObject(), org.semanticwb.scian.SubRama.sclass);
        String numrama=getCode(e.getScian().getSemanticObject(), org.semanticwb.scian.Rama.sclass);
        String numsubsector=getCode(e.getScian().getSemanticObject(), org.semanticwb.scian.SubSector.sclass);
        String numsector=getCode(e.getScian().getSemanticObject(), org.semanticwb.scian.Sector.sclass);

        String clase=e.getScian().getSemanticObject().getLabel(paramRequest.getUser().getLanguage());
        String subrama=getLabel(e.getScian().getSemanticObject(), org.semanticwb.scian.SubRama.sclass,paramRequest.getUser());
        String rama=getLabel(e.getScian().getSemanticObject(), org.semanticwb.scian.Rama.sclass,paramRequest.getUser());
        String subsector=getLabel(e.getScian().getSemanticObject(), org.semanticwb.scian.SubSector.sclass,paramRequest.getUser());
        String sector=getLabel(e.getScian().getSemanticObject(), org.semanticwb.scian.Sector.sclass,paramRequest.getUser());
        
   //Determina si la empresa ya es de interés para el usuario...
   User user					=	paramRequest.getUser();
   SWBModel webSite				=	paramRequest.getWebPage().getWebSite();
   boolean 	isEmpresaInteres	=	SearchResource.isEmpresasInteres(user,webSite, empresaURI),
   			isUser				=	(user != null && user.isSigned());
      
   String mensaje	= 	request.getParameter("mensaje")!= null ? request.getParameter("mensaje") : "";
      
%>
<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAolpeBAG69pwV4I7Q2UbUGBS9-76TLlD3CjpiqwLbfvCHGabL2hTlSKx5Z3KGx9kvlzrwQIkIKQ28uA"></script>
<script type="text/javascript" src="/swbadmin/jsp/sieps/googleMaps.js"></script>
<script type="text/javascript" src="/swbadmin/jsp/sieps/sieps.js"></script>

<div id="datos_empresa">
     	<img src="<%=urllog%>" width="104" height="89" alt="Grupo Plus"/>
        <div id="descripcion">
	    	<h2><%=e.getName()%></h2>
            <span id="clave">Clave SIEM: <%=e.getClavesiem()%></span>
            <p><strong>Calle: </strong><%=e.getAddress()%></p>
            <p><strong>Colonia: </strong><%=e.getColonia()%></p>
            <p><strong>Municipio: </strong><%=e.getMunicipio()%></p>
            <p><strong>Estado: </strong><%=e.getEstado()%></p>
            <p><strong>C.P.: </strong><%=e.getCp()%></p>
            <p><strong>R.F.C.: </strong><%=e.getRfc()%></p>
            <p><strong>Correo electrónico: </strong><%=e.getEmail()%></p>
            <p><strong>Teléfono: </strong><%=e.getTelefono()%></p>
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
		<%
                int i=1;
                while (iterEmpresasSimi.hasNext()) {
			Empresa emp = iterEmpresasSimi.next();
			SWBResourceURL urldetail=paramRequest.getRenderUrl();
			urldetail.setParameter("act","detail");
			urldetail.setParameter("uri",emp.getEncodedURI());
                        i++;
			if (!e.getURI().equals(emp.getURI())) {
		%>
			<li><a href="<%=urldetail%>"><%=emp.getName()%></a><span><%=emp.getEstado()%></span></li>
			<%}
                        if(i==5)
                            {
                            break;
                            }
                        %>
		<%} %>
		</ul>
         <form action="#" >
       		<input type="button" name="verMas" id="verMas" class="panel_btn" value="Ver más" onclick="javascript:document.location='<%=urlSimilares%>'"/>
         </form>
</div>
                <div class="panelDerechoB">
 	<h4>Ofertas relacionadas a la empresa</h4>
     <ul>
                <%
                int iProducto=1;
                GenericIterator<Producto> productos= e.listProductos();
                while(productos.hasNext())
                    {
                    Producto producto=productos.next();
                    SWBResourceURL urlProducto=paramRequest.getRenderUrl();
                    urlProducto.setParameter("act","detail");
                    urlProducto.setParameter("uri",producto.getURI());
                    String name=producto.getPname();
                    %>
                    <li><a href="<%=urlProducto%>"><%=name%></a></li>
                    <%
                    if(iProducto>=3)
                        {
                        break;
                        }
                    iProducto++;
                    }

            %>
     </ul>
     <form action="#">
   	  <input type="button" name="verMas" id="verMas" class="panel_btn" value="Ver más" onclick="javascript:document.location='<%=urlCatalogo%>'"/>
     </form>
 </div>
<div id="industria">
       <h3>Industria a la que pertenece esta empresa</h3>
       <ul>
           <li class="first"><span class="codigo">Código SCIAN</span><span class="descripcion">DESCRIPCIÓN</span></li>
           <li class="a"><span class="codigo"><%=numsector%></span><a href="#"><span class="descripcion"><%=sector%></span></a>
             <ul>
               <li class="b"><span class="codigo"><%=numsubsector%></span><a href="#"><span class="descripcion"><%=subsector%></span></a>
                 <ul>
                   <li class="a"><span class="codigo"><%=numrama%></span><a href="#"><span class="descripcion"><%=rama%></span></a>
                     <ul>
                       <li class="a"><span class="codigo"><%=numsubrama%></span><a href="#"><span class="descripcion"><%=subrama%></span></a></li>
                       <ul>
                            <li class="b"><span class="codigo"><%=numclase%></span><a href="#"><span class="descripcion"><%=clase%></span></a></li>
                       </ul>
                     </ul>
                   </li>
                 </ul>
               </li>
             </ul>
           </li>
       </ul>
</div>
    	<form name="frmFichaEmp" id="frmFichaEmp" method="post" action="">
    	  <input type="hidden" id="chkEmpresas" name="chkEmpresas" value="<%=empresaURI%>"/>
          <p id="Envio">
          	<% if (isUser && !isEmpresaInteres){ %>
    			<input type="button" name="btnSendEmpresa" id="btnSendEmpresa" value="Enviar Empresa(s) a mi Carpeta" class="btn-bigger" onclick="javascript:enviarEmpresasInteresFicha('<%=urlGuardaEmpresas%>', this); "/>
    		<% } %>	
          
          <!-- 
          	<input type="submit" name="search" id="search" value="Enviar un mensaje" /> 
          -->  
          </p>
        </form>
<script type="text/javascript">muestraMensaje('<%=mensaje%>');</script>

