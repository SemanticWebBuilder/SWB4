<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.scian.*"%>
<%@page import="org.semanticwb.unspsc.*"%>
<%@page import="org.semanticwb.sieps.*"%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%!
    public ArrayList<Set<Producto>> getLineas(Set<Producto>productos,int prodByLine)
    {
        ArrayList<Set<Producto>> getLineas=new ArrayList<Set<Producto>>();
        int lineas=productos.size()/prodByLine;
        if(productos.size()%prodByLine>0)
        {
            lineas++;
        }
        for(int i=1;i<=lineas;i++)
        {
            getLineas.add(new HashSet<Producto>());            
        }
        int iLinea=0;
        int iProducto=1;
        for(Producto producto : productos)
        {
            if(iProducto==prodByLine+1)
            {
                iLinea++;
                iProducto=1;
            }
            Set<Producto> temp=getLineas.get(iLinea);
            temp.add(producto);
            iProducto++;
        }
        return getLineas;
    }
    public Hashtable<Commodity,Set<Producto>> ordenaProductos(GenericIterator<Producto> productos)
    {
        Hashtable<Commodity,Set<Producto>> ordenaProductos=new Hashtable<Commodity,Set<Producto>>();
        while(productos.hasNext())
        {
            Producto producto=productos.next();
            if(producto.getUnspsc()!=null)
            {
                if(!ordenaProductos.containsKey(producto.getUnspsc()))
                {
                    ordenaProductos.put(producto.getUnspsc(), new HashSet<Producto>());
                }
                Set<Producto> sproductos=ordenaProductos.get(producto.getUnspsc());
                sproductos.add(producto);
            }
        }
        return ordenaProductos;
    }
%>
<%	
	Empresa e = (Empresa)request.getAttribute("obj");
        String urllog=SWBPortal.getWebWorkPath()+e.getWorkPath()+"/"+e.getLogo();
        String urlGaleria = SWBPortal.getContextPath() + "/swbadmin/jsp/sieps/galeria_icono.png";
        %>

        <div id="columnaDerecha">
      <p class="fecha">Bienvenido, hoy es 25 de septiembre de 2010</p>
      <p class="noEmpresas">Ya somos: 18,367 empresas</p>
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
        <%
        GenericIterator<Producto> lproductos=e.listProductos();
        Hashtable<Commodity,Set<Producto>> productos_por_categoria=ordenaProductos(lproductos);
        if(productos_por_categoria.keySet().size()==0)
            {
            %>
            <div id="columnaCentro">
                    <h2 class="first"><img src="<%=urlGaleria%>" width="50" height="50" alt="" />Galer&iacute;a fotogr&aacute;fica</h2>
                </div>
                <div class="panelDerechoA">
                    <h4>Conocer más de esta empresa</h4>
                    <ul>
                        <li><a href="#">Ver detalles de &eacute;sta empresa</a></li>
                        <li><a href="#">Visitar su página web</a></li>
                        <li><a href="#">Ver su video corporativo</a></li>
                    </ul>
                </div>            
            <%
            }
        int iCategoria=1;
        for(Commodity categoria : productos_por_categoria.keySet())
        {
            String label=categoria.getSemanticObject().getLabel(paramRequest.getUser().getLanguage());
            String UNSPSC=categoria.getUnspsc();
            if(iCategoria==1)
            {
                
                %>
                <div id="columnaCentro">
                    <h2 class="first"><img src="<%=urlGaleria%>" width="50" height="50" alt="" />Galer&iacute;a fotogr&aacute;fica</h2>
                    <h3 class="short"><%=label%> - <span>C&oacute;digo UNSPSC <%=UNSPSC%></span></h3>
                </div>
                <div class="panelDerechoA">
                    <h4>Conocer más de esta empresa</h4>
                    <ul>
                        <li><a href="#">Ver detalles de &eacute;sta empresa</a></li>
                        <li><a href="#">Visitar su página web</a></li>
                        <li><a href="#">Ver su video corporativo</a></li>
                    </ul>
                </div>
                <%
            }
            Set<Producto>productos=productos_por_categoria.get(categoria);
            ArrayList<Set<Producto>> lineas=getLineas(productos, 4);
            int iLinea=1;
            if(lineas.size()==0)
                {
                %>
                <p>Esta empresa no tiene productos registrados</p>
                <%
                }
            for(Set<Producto> linea : lineas)
            {
                if(iLinea==1 && iCategoria==1)
                {
                    %>
                            <div class="product-first">
                    <%
                    for(Producto producto : linea)
                    {
                        String name=producto.getPname();
                        String description=producto.getDescription();
                        String urlfoto=SWBPortal.getWebWorkPath()+producto.getWorkPath()+"/"+producto.getFoto();
                        SWBResourceURL url=paramRequest.getRenderUrl();
                        url.setParameter("act", "detail");
                        url.setParameter("uri", producto.getURI());
                        %>
                            <div class="product">
                                <img src="<%=urlfoto%>" width="110" height="83" alt="<%=name%>">
                                <p><strong><%=name%></strong><br/><%=description%></p>
                                <a href="<%=url%>" class="boton_detalle">Ver detalle</a>
                                <a href="#" class="boton_carrito">Agregar carrito</a>
                            </div>
                        <%
                    }
                    %>
                    </div>
                    <%
                }
                else
                {
                    String sClass="product-non";
                    if(iLinea%2==0)
                    {
                        sClass="product-pair";
                    }                    
                    %>
                    <div class="<%=sClass%>">
                    <h3><%=label%> - <span>C&oacute;digo UNSPSC <%=UNSPSC%></span></h3>

                    <%
                    for(Producto producto : linea)
                    {
                        String name=producto.getPname();
                        String description=producto.getDescription();
                        String urlfoto=SWBPortal.getWebWorkPath()+producto.getWorkPath()+"/"+producto.getFoto();
                        SWBResourceURL url=paramRequest.getRenderUrl();
                        url.setParameter("act", "detail");
                        url.setParameter("uri", producto.getURI());
                        %>
                            <div class="product">
                                <img src="<%=urlfoto%>" width="110" height="83" alt="<%=name%>">
                                <p><strong><%=name%></strong><br/><%=description%></p>
                                <a href="<%=url%>" class="boton_detalle">Ver detalle</a>
                                <a href="#" class="boton_carrito">Agregar carrito</a>
                            </div>
                        <%
                    }
                    %>

            </div>
                    <%
                }
                iLinea++;
            }
        }
        
%>
      
	
        
<form action="#" enctype="multipart/form-data">
          <p id="Envio">
            <input type="submit" name="search2" id="search2" value="Enviar a mi carpeta" />
            <input type="submit" name="search3" id="search3" value="Enviar un mensaje" />
          </p>
        </form>
    </div>