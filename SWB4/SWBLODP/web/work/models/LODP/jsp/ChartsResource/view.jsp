<%-- 
    Document   : view
    Created on : 29/05/2013, 12:32:29 PM
    Author     : Sabino Pariente
--%>
<%@page import="com.infotec.lodp.swb.resources.ChartData"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.WebPage"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
    WebPage wpage = paramRequest.getWebPage();
    WebSite wsite = wpage.getWebSite();
    String urlSite = wpage.getUrl();
    String urlTypeData = urlSite + "?dataType=";
    String chartType = (String) (request.getAttribute("chartType")== null? "Barra" : request.getAttribute("chartType"));    
    SWBResourceURL urlAction = paramRequest.getRenderUrl();            
    String rango = (String) (request.getAttribute("rango")== null? "" : request.getAttribute("rango"));
    String fechaInicial = ""; //request.getParameter("startDate")==null? "":request.getParameter("startDate");
    String fechaFinal = ""; // request.getParameter("finishDate")==null? "":request.getParameter("finishDate");
    String datasetSelected = request.getParameter("dataset")==null? "":request.getParameter("dataset");    
    if(fechaInicial.isEmpty()){        
        fechaInicial = (String)(request.getAttribute("startDate")==null? "":request.getAttribute("startDate"));
    }
    if(fechaFinal.isEmpty()){        
        fechaFinal = (String)(request.getAttribute("finishDate")==null? "":request.getAttribute("finishDate"));        
    }
    String DataType = (String)(request.getAttribute("dataType")== null? "views" : request.getAttribute("dataType"));
    //List<ChartData> datos = getDatos(DataType, dFinal, dStart, dias, rango, datasetObj);
%>
<script type="text/javascript">    
    dojo.require("dojo.parser");
    dojo.require("dijit.form.DateTextBox");    
    chartData = [        
        { x: "1", y: "19021" },
        { x: "2", y: "12837" },
        { x: "3", y: "12378" },
        { x: "4", y: "21882" },
        { x: "5", y: "17654" },
        { x: "6", y: "15833" },
        { x: "7", y: "16122" }
    ];  
    window.onload = function(){ 
        var chartType = '<%=chartType%>'; 
        var date = new Date();
        document.getElementById("startDate").setAttribute("constraints","{max:"+date+"}");
        document.getElementById("finishDate").setAttribute("constraints","{max:"+date+"}");
        document.getElementById("startDate").setAttribute("value","<%=fechaInicial%>");
        document.getElementById("finishDate").setAttribute("value","<%=fechaFinal%>");
        document.getElementById("txtGraph1").innerHTML = "";
        document.getElementById("txtGraph2").innerHTML = "";
        document.getElementById("txtGraph3").innerHTML = "";
        document.getElementById("txtGraph4").innerHTML = "";        
        if(chartType == 'Barra') {
            document.getElementById("txtGraph1").innerHTML = "(selected)"; 
            document.getElementById("Barra").setAttribute("checked",true);
            document.getElementById("grafica").setAttribute("style","width:600px;height:400px");            
        }
        if(chartType == 'Lineal') { 
            document.getElementById("txtGraph2").innerHTML = "(selected)";    
            document.getElementById("Lineal").setAttribute("checked",true);
            document.getElementById("grafica").setAttribute("style","width:600px;height:400px");
            
        }
        if(chartType == 'Dispersion') { 
            document.getElementById("txtGraph3").innerHTML = "(selected)";  
            document.getElementById("Dispersion").setAttribute("checked",true);
            document.getElementById("grafica").setAttribute("style","width:600px;height:400px");            
        }
        if(chartType == 'Pastel') { 
            document.getElementById("txtGraph4").innerHTML = "(selected)"; 
            document.getElementById("Pastel").setAttribute("checked",true);
            document.getElementById("grafica").setAttribute("style","width:400px;height:400px");                       
        }       	
    }     
    function validaFecha(){
        var fechaIni = document.getElementById("startDate").value;
        var fechaFin = document.getElementById("finishDate").value;
        var dIni = new Date(fechaIni);
        var dFin = new Date(fechaFin);
        //alert(fechaIni+":::"+fechaFin);
        if(dIni > dFin){
            alert("La fecha inicial debe ser menor a la fecha final");
        }        
    }
</script>
<form method="post"  action="<%=urlAction.setMode("GRAPHIC").setParameter("dataType", DataType)%>">
    <div id="gral">
        <div id="cuerpo">
            <h3>Graficas de actividad</h3>
            <div id="graficador">
                <div id="categoria">
                    <span>
                        <%if(!DataType.equals("views")){%>
                            <a href="<%=urlTypeData+"views"%>">
                        <%}%>    
                        Visitas
                        <%if(!DataType.equals("views")){%>
                            </a>
                        <%}%>
                    </span>
                    <span>
                         <%if(!DataType.equals("ranking")){%>
                            <a href="<%=urlTypeData+"ranking"%>" />
                         <%}%>
                         Calificación
                         <%if(!DataType.equals("ranking")){%>
                            </a>
                        <%}%>
                    </span>
                    <span>
                         <%if(!DataType.equals("hits")){%>   
                            <a href="<%=urlTypeData+"hits"%>" />
                         <%}%>
                         Descargas
                         <%if(!DataType.equals("hits")){%>
                            </a>
                         <%}%>
                    </span>
                    <span>
                         <%if(!DataType.equals("comments")){%> 
                            <a href="<%=urlTypeData+"comments"%>" />
                         <%}%>
                         Comentarios
                         <%if(!DataType.equals("comments")){%>
                            </a>
                        <%}%>
                    </span>
                    <span>
                        <%if(!DataType.equals("appl")){%> 
                            <a href="<%=urlTypeData+"appl"%>" />
                        <%}%>
                        Contribuciones
                        <%if(!DataType.equals("appl")){%>
                            </a>
                        <%}%>
                    </span>
                </div>
                <div id="izq-graf">
                    <div id="grafica">
                        Aqui va la gráfica
                    </div>        
                    <div  id="graficatipo">
                        <label for="gbarras">
                            <input type="radio" name="chartType" value="Barra" id="Barra">
                            Barras <span id="txtGraph1"></span>
                        </label>
                        <label for="glineal">
                            <input type="radio" name="chartType" value="Lineal" id="Lineal"> 
                            Lineal <span id="txtGraph2"></span>
                        </label>
                        <label for="gdisper">  
                            <input type="radio" name="chartType" value="Dispersion" id="Dispersion"> 
                            Dispersi&oacute;n <span id="txtGraph3"></span>
                        </label>
                        <label for="gpastel">
                            <input type="radio" name="chartType" value="Pastel" id="Pastel"> 
                            Pastel <span id="txtGraph4"></span>
                        </label>                            
                    </div>
                    <div id="grafpie">
                        <div id="graficafecha">
                            <p>Periodo del 
                              <input name="startDate" id="startDate" type="text" 
                              class="calendario" dojoType="dijit.form.DateTextBox" required="true" 
                              promptMessage="dd/MM/yyyy" trim="true" valid="false"
                              invalidMessage="Fecha invalida. Use el formato mm/dd/yyyy."/> 
                              al 
                              <input name="finishDate" id="finishDate" type="text" 
                              class="calendario" dojoType="dijit.form.DateTextBox" required="true"  
                              promptMessage="dd/MM/yyyy" trim="true" valid="false"  onchange="validaFecha();" 
                              invalidMessage="Fecha invalida. Use el formato mm/dd/yyyy."/> 
                            </p>
                            <div id="graficaunidad">
                                Unidad: <%=rango%>
                            </div>
                             <input type="Submit" value="Gráficar" class="boton-simple" /> 
                        </div>
                    </div>
                    <div id="der-graf">
                        <div id="grafdatasets">
                            <p>Datasets</p>
                            <select size="10" name="dataset" id="dataset">
                            <%
                            Iterator<Dataset> listDS = Dataset.ClassMgr.listDatasets(wsite);
                            while(listDS.hasNext()){
                            Dataset ds = listDS.next(); 
                            String selected = "";
                            if(ds.isDatasetActive() && ds.isApproved()){%>
                            
                            <%if(ds.getId().equalsIgnoreCase(datasetSelected)){
                                    selected = "selected";
                                }                
                            %>
                                <option <%=selected%> value="<%=ds.getId()%>" ><%=ds.getDatasetTitle()%></option>
                            <%}
                            }%>
                            </select>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
    </div>   
</form> 
  
       
