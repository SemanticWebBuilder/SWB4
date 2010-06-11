<%@page import="java.util.List"%><%@page import="java.util.*"%><%@page import="org.semanticwb.SWBPortal"%><%@page import="org.semanticwb.platform.*"%><%@page import="org.semanticwb.model.*"%><%@page import="org.semanticwb.scian.*"%><%@page import="org.semanticwb.unspsc.*"%><%@page import="org.semanticwb.sieps.*"%><%
    String urlBusqueda = SWBPortal.getContextPath() + "/swbadmin/jsp/sieps/busqueda_icono.png";
    if(request.getParameter("mode")!=null && request.getParameter("value")!=null && !request.getParameter("mode").equals("") && !request.getParameter("value").equals(""))
        {
        Hashtable<String,String> options=new Hashtable<String,String>();
        options.put("all", "Todos");
        
        if(!request.getParameter("value").equals("all"))
        {
            if(request.getParameter("mode").equals("subsector"))
            {
                Iterator<Sector> sectores=Sector.ClassMgr.listSectors();
                while(sectores.hasNext())
                {
                    Sector sector=sectores.next();
                    if(sector.getCode().equals(request.getParameter("value")))
                    {
                        Iterator<SubSector> subsectores=sector.getSubSectores();
                        while(subsectores.hasNext())
                        {
                            SubSector subsector=subsectores.next();
                            options.put(subsector.getCode(),subsector.getSemanticObject().getLabel("es"));
                        }
                        break;
                    }
                }
            }
            if(request.getParameter("mode").equals("rama"))
            {
                Iterator<SubSector> sectores=SubSector.ClassMgr.listSubSectors();
                while(sectores.hasNext())
                {
                    SubSector subsector=sectores.next();
                    if(subsector.getCode().equals(request.getParameter("value")))
                    {
                        Iterator<Rama> ramas=subsector.getRamas();
                        while(ramas.hasNext())
                        {
                            Rama rama=ramas.next();
                            options.put(rama.getCode(),rama.getSemanticObject().getLabel("es"));
                        }
                        break;
                    }
                }
            }
            if(request.getParameter("mode").equals("actividad"))
            {
                Iterator<SubRama> sectores=SubRama.ClassMgr.listSubRamas();
                while(sectores.hasNext())
                {
                    SubRama subsector=sectores.next();
                    if(subsector.getCode().equals(request.getParameter("value")))
                    {
                        Iterator<Clase> ramas=subsector.getClasses();
                        while(ramas.hasNext())
                        {
                            SubRama rama=ramas.next();
                            options.put(rama.getCode(),rama.getSemanticObject().getLabel("es"));
                        }
                        break;
                    }
                }
            }
            if(request.getParameter("mode").equals("subrama"))
            {
                Iterator<Rama> sectores=Rama.ClassMgr.listRamas();
                while(sectores.hasNext())
                {
                    Rama subsector=sectores.next();
                    if(subsector.getCode().equals(request.getParameter("value")))
                    {
                        Iterator<SubRama> ramas=subsector.getSubRamas();
                        while(ramas.hasNext())
                        {
                            SubRama rama=ramas.next();
                            options.put(rama.getCode(),rama.getSemanticObject().getLabel("es"));
                        }
                        break;
                    }
                }
            }
        }

        %>values=[<%
        for(String id : options.keySet())
        {
            String data=new String(options.get(id).getBytes("utf-8"));
            %>
            {"data":"<%=data%>","id":"<%=id%>"},
            <%
        }
        %>]<%
        return;
        }
%>

<script type="text/javascript">
    function activaproducto()
    {
        document.getElementById('busquedaProductos').style.display ='block';
        document.getElementById('busquedaEmpresas').style.display ='none';
        document.frmadvancedsearch.act.value='busquedaproductos';
    }
    function activaempresa()
    {
        document.getElementById('busquedaProductos').style.display ='none';
        document.getElementById('busquedaEmpresas').style.display ='block';
        document.frmadvancedsearch.act.value='busquedaempresas';
    }
    function LlenarCombo(json,combo)
    {        
        var the_object = eval('(' + json + ')');             
        for(var i=0;i<the_object.length;i++)
        {
            if(the_object[i] && the_object[i].data && the_object[i].id)
            {
                combo.options[combo.length]= new Option(the_object[i].data, the_object[i].id);
            }
        }
    }

    function loadCombo(source,target,url)
    {
        var xmlHttpReq = false;
        var self = this;
        // Mozilla/Safari
        if (window.XMLHttpRequest) {
            self.xmlHttpReq = new XMLHttpRequest();
        }
        // IE
        else if (window.ActiveXObject) {
            self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
        }
        var value=document.frmadvancedsearch[source].value;        
        var query=url+'?mode='+target+'&value='+value;        
        self.xmlHttpReq.open('GET', query, true);
        self.xmlHttpReq.onreadystatechange = function() {
            if (self.xmlHttpReq.readyState == 4) {
                updatepage(self.xmlHttpReq.responseText,target);
            }
        }
        self.xmlHttpReq.send();
    }
    function updatepage(str,target){        
        LimpiarCombo(document.frmadvancedsearch[target]);
        LlenarCombo(str,document.frmadvancedsearch[target]);        
    }
    function LimpiarCombo(combo){
        while(combo.length>0){
        combo.remove(combo.length-1);
        }
    }




</script>
<%!
    public SemanticObject getSuper(SemanticObject obj,SemanticClass clazz)
    {
        org.semanticwb.platform.SemanticProperty prop=obj.getModel().getSemanticProperty(org.semanticwb.platform.SemanticVocabulary.RDFS_SUBCLASSOF);        
        Iterator<org.semanticwb.platform.SemanticObject> parents=obj.listObjectProperties(prop);
        while(parents.hasNext())
        {
            org.semanticwb.platform.SemanticObject temp=parents.next();
            if(clazz.equals(temp.getSemanticClass()))
            {
                return temp;                
            }
        }
        return null;
    }

    
%>
<%
    String url=SWBPortal.getContextPath()+"/swbadmin/jsp/sieps/advancedsearch.jsp";
    String action=SWBPortal.getContextPath()+"/es/sieps/Resultados_Empresas";
%>
<div id="busquedaAvanzada">
    <form name="frmadvancedsearch" id="frmadvancedsearch" method="post" action="<%=action%>">
                    <input type="hidden" name="act" value="busquedaempresas">
            	<div id="busquedaHead">
                <img src="<%=urlBusqueda%>" width="49" height="50" alt="Busqueda avanzada" />
            	<h2>B&uacute;squedaAvanzada</h2>
                <p>Le sugerimos utilizar los siguientes flitros para optimizar su b&uacute;squeda</p>                
                </div>
                <div id="busquedaOpciones">
                	<label for="empresas">Buscar por Empresas:</label>
                	<input  type="radio" onclick="activaempresa()" name="opc" id="empresas" />
                	<label for="productos">Buscar por Productos:</label>
                        <input  type="radio" onclick="activaproducto()" name="opc" id="productos" />
                </div>
                <div id="busquedaEmpresas">
                <label for="palabraClave">Palabra clave:</label>
                <input type="text" name="palabraClave" id="palabraClave" />
                <label for="estado">Estado:</label>
                <select name="estado">
                  <option selected value="all">Todos</option>
                  <option value="Guanajuato">Guanajuato</option>
                  <option value="Michoacán" >Michoacán</option>
                  <option value="Veracuz">Veracuz</option>                  
                </select>
                <label for="sector">Sector:</label>
                <select name="sector" onChange="javascript:loadCombo('sector','subsector','<%=url%>')">
                    <option selected value="all">Todos</option>
                    <%
                        Sector sector1=null;
                        {
                            Iterator<Sector> sectores=Sector.ClassMgr.listSectors();
                            while(sectores.hasNext())
                            {
                                Sector sector=sectores.next();
                                if(sector1==null)
                                {
                                    sector1=sector;
                                }
                                String name=sector.getSemanticObject().getLabel("es");
                                String value=sector.getCode();
                                %>
                                <option value="<%=value%>"><%=name%></option>
                                <%
                            }
                        }
                    %>
                  
                  </select>
                <label for="subsector">Subsector:</label>
                <select id="subsector" name="subsector" onChange="javascript:loadCombo('subsector','rama','<%=url%>')">
                    <option selected value="all">Todos</option>                    
                </select>
                <label for="rama">Rama:</label>
                <select id="rama" name="rama" onChange="javascript:loadCombo('rama','subrama','<%=url%>')">
                    <option selected value="all">Todos</option>                 
                </select>
                <label for="subrama">Subrama:</label>
                    
                <select id="subrama" name="subrama" onChange="javascript:loadCombo('subrama','actividad','<%=url%>')">
                    <option selected value="all">Todos</option>
                </select>
                <label for="actividad">Actividad:</label>
                <select id="actividad" name="actividad">
                    <option selected value="all">Todos</option>
                </select>
                <p>
                <input type="submit" value="Buscar" name="wbseach" id="buscarEmpresas" class="btn-small" />
                </p>
            </div>
            	<div id="busquedaProductos">
                <label for="palabraClave">Palabra clave:</label>
                <input type="text" id="palabraClave" name="palabraClave" id="palabraClave" />
                <label for="segmento">Segmento:</label>
                <select id="segmento" name="segmento" onChange="javascript:loadCombo('segmento','familia','<%=url%>')">
                    <option selected value="all">Todos</option>
                    <%
                        Segment segmento1=null;
                        Iterator<Segment> segmentos=Segment.ClassMgr.listSegments();
                        while(segmentos.hasNext())
                        {
                            Segment segmento=segmentos.next();
                            if(segmento1==null)
                            {
                                segmento1=segmento;
                            }
                            if(segmento!=null && segmento.getSemanticObject()!=null)
                            {
                                String name=segmento.getSemanticObject().getLabel("es");
                                String value=segmento.getUnspsc();
                                %>
                                    <option value="<%=value%>"><%=name%></option>
                                <%
                           }
                        }
                    %>
                </select>
                <label for="familia">Familia:</label>
                <select id="familia" name="familia" onChange="javascript:loadCombo('familia','clase','<%=url%>')">
                  <option selected value="all">Todos</option>
                </select>
                <label for="clase">Clase:</label>
                <select id="clase" name="clase" onChange="javascript:loadCombo('clase','categoria','<%=url%>')">
                  <option selected value="all">Todos</option>
                </select>
                <label for="categoria">Categor&iacute;a:</label>
                <select id="categoria" name="categoria">
                  <option selected value="all">Todos</option>
                </select>
                <p>
                <input type="submit" value="Buscar" name="wbseach" id="buscarProductos" class="btn-small" />
                </p>
            </div>
            </form>
        </div>