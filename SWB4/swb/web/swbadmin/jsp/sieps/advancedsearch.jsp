<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.scian.*"%>
<%@page import="org.semanticwb.unspsc.*"%>
<%@page import="org.semanticwb.sieps.*"%>

<%
    String urlBusqueda = SWBPortal.getContextPath() + "/swbadmin/jsp/sieps/busqueda_icono.png";
%>
<script type="text/javascript">
    function activaproducto()
    {
        document.getElementById('busquedaProductos').style.display ='block';
        document.getElementById('busquedaEmpresas').style.display ='none';
        document.frmadvancedsearch.mode.value='producto';
    }
    function activaempresa()
    {
        document.getElementById('busquedaProductos').style.display ='none';
        document.getElementById('busquedaEmpresas').style.display ='block';
        document.frmadvancedsearch.mode.value='empresa';
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
<div id="busquedaAvanzada">
        	<form name="frmadvancedsearch" id="frmadvancedsearch" action="./Resultados_Empresas">
                    <input type="hidden" name="mode" value="empresas">
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
                  <option value="Michoacan" >Michoacan</option>
                  <option value="Veracuz">Veracuz</option>                  
                </select>
                <label for="sector">Sector:</label>
                <select name="sector">
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
                <select name="subsector">
                    <option selected value="all">Todos</option>                    
                </select>
                <label for="rama">Rama:</label>
                <select name="rama">
                    <option selected value="all">Todos</option>                 
                </select>
                <label for="subrama">Subrama:</label>
                    
                <select name="subrama">
                    <option selected value="all">Todos</option>
                </select>
                <label for="actividad">Actividad:</label>
                <select name="actividad">
                    <option selected value="all">Todos</option>
                </select>
                <p>
                <input type="submit" value="Buscar" id="buscarEmpresas" class="btn-small" />
                </p>
            </div>
            	<div id="busquedaProductos">
                <label for="palabraClave">Palabra clave:</label>
                <input type="text" name="palabraClave" id="palabraClave" />
                <label for="segmento">Segmento:</label>
                <select name="segmento">
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
                <select name="familia">
                  <option selected value="all">Todos</option>
                </select>
                <label for="clase">Clase:</label>
                <select name="clase">
                  <option selected value="all">Todos</option>
                </select>
                <label for="categoria">Categor&iacute;a:</label>
                <select name="categoria">
                  <option selected value="all">Todos</option>
                </select>
                <p>
                <input type="submit" value="Buscar" id="buscarProductos" class="btn-small" />
                </p>
            </div>
            </form>
        </div>