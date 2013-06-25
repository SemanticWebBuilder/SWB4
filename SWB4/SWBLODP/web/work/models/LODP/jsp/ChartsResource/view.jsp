<%-- 
    Document   : view
    Created on : 29/05/2013, 12:32:29 PM
    Author     : Sabino Pariente
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.infotec.lodp.swb.resources.ChartsResource"%>
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
    SWBResourceURL urlAction = paramRequest.getRenderUrl(); 
    WebSite wsite = wpage.getWebSite();
    String urlSite = wpage.getUrl();
    String urlTypeData = urlSite + "?dataType=";
    String fechaInicial = request.getParameter("startDate")== null? "":request.getParameter("startDate");
    String fechaFinal = request.getParameter("finishDate")== null? "":request.getParameter("finishDate");
    String dataset = request.getParameter("dataset");
    String chartType = request.getParameter("chartType")==null?"Barra":request.getParameter("chartType");
    String DataType = request.getParameter("dataType")==null?"views":request.getParameter("dataType");
    Date dStart = null;
    Date dFinal = null;        
    if(fechaInicial.equals("") && fechaFinal.equals("")){
        dFinal = new Date();                   
        dStart = new Date(dFinal.getTime()-(7*ChartsResource.MILLSECS_PER_DAY));            
    }else {
        try {
            DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            dStart = fechaInicial.equals("")?new Date():sdf1.parse(fechaInicial);                
            dFinal = fechaFinal.equals("")?new Date():sdf1.parse(fechaFinal);                
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }    
    long dias = 1+(dFinal.getTime()-dStart.getTime())/ChartsResource.MILLSECS_PER_DAY;
    String rango = ChartsResource.getRango(dias,dStart,dFinal); 
    Dataset datasetObj = null;
    String idDSSelected = "";
    List<ChartData> datos= new ArrayList();
    String titleGraph = ""; 
    String paramIdDataset="";
    String paramSDate = "";
    String paramFDate = "";
    if(dataset!=null){
        datasetObj = Dataset.ClassMgr.getDataset(dataset, wsite);   
        idDSSelected = datasetObj.getId();
        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        fechaInicial = sdf1.format(dStart);
        fechaFinal =  sdf1.format(dFinal);
        datos = ChartsResource.getDatos(DataType, dFinal, dStart, dias, rango, datasetObj,wsite);
        titleGraph = paramRequest.getLocaleString("lbl_period");
        if(rango==ChartsResource.RANGO_DIAS){ 
            SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
            titleGraph += " "+sdf.format(dStart).toUpperCase();
        }
        if(rango==ChartsResource.RANGO_MESES){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            titleGraph += " "+ sdf.format(dStart).toUpperCase();
        }
        if(rango==ChartsResource.RANGO_YEARS){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            titleGraph += " "+sdf.format(dStart).toUpperCase()+" "+paramRequest.getLocaleString("lbl_to")+" "+sdf.format(dFinal).toUpperCase();
        }
        paramIdDataset="&dataset="+datasetObj.getId();
        paramSDate = "&startDate="+fechaInicial;
        paramFDate = "&finishDate="+fechaFinal;
    }    
%>
<meta charset="utf-8">
<link href="/work/models/LODP/jsp/ChartsResource/barChart/nv.d3.css" rel="stylesheet" type="text/css">
<style>

body {
  overflow-y:scroll;
}

text {
  font: 10px sans-serif;
}

svg {
  display: block;
}

#grafica svg{ 
  min-width: 100px;
  min-height: 100px;
}
.mypiechart {
  /*width: 400px;*/
  border: 2px;
}

</style>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/d3.v2.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/nv.d3.js"></script>
<!-- including all the components so I don't have to minify every time I test in development -->
<script src="/work/models/LODP/jsp/ChartsResource/barChart/tooltip.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/utils.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/axis.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/discreteBar.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/discreteBarChart.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/pie.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/pieChart.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/line.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/cumulativeLineChart.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/distribution.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/scatter.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/scatterChart.js"></script>
<script type="text/javascript">    
    dojo.require("dojo.parser");
    dojo.require("dijit.form.DateTextBox"); 
    var chartType = '<%=chartType%>'; 
</script>
<script>
    if(chartType == 'Barra') {
        dataBarChart = [ 
                {
                key: "Dataset",
                values: [
                <%
                    int iB=0;
                    for(ChartData cd : datos){
                %>
                { "label" : "<%=cd.getTitle()%>", "value" : <%=cd.getCount()%> }
                <%      iB++;
                        if(iB<datos.size()){ %>,<%}%>
                <%
                    }
                %> 
                 ]
                 }
                 ];
                 
            nv.addGraph(function() {                 
                var chart = nv.models.discreteBarChart()
                    .x(function(d) { return d.label })
                    .y(function(d) { return d.value })
                    .staggerLabels(true)
                    //.staggerLabels(historicalBarChart[0].values.length > 8)
                    .tooltips(true)
                    .valueFormat(d3.format('d'))
                    .showValues(true);
                chart.yAxis
                        .tickFormat(d3.format('d'));
                d3.select('#grafica svg')
                    .datum(dataBarChart)
                  .transition().duration(500)
                    .call(chart);
                nv.utils.windowResize(chart.update);
                return chart;
              });                                 
        }
    if(chartType == 'Lineal') {
        <%
         String datasetTitle = "";
         if(dataset!=null){
             datasetTitle = datasetObj.getDatasetTitle();
         }%>
        var rango = '<%=rango%>';        
        dataLineChart= [
            {
            "key": "<%=datasetTitle%>",
            "values": [ 
                <%
                    int iL=0;
                    for(ChartData cd : datos){
                %>
                    [ <%=cd.getStartDate().getTime()%> , <%=cd.getCount()%>] 
                <%      iL++;
                        if(iL<datos.size()){ %>,<%}
                    }
                %> 
            ]
            }
        ]; 
        var chart;
        nv.addGraph(function() {  
           chart = nv.models.cumulativeLineChart()
                     .x(function(d) { return d[0] })
                     .y(function(d) { return d[1]})
                     .color(d3.scale.category10().range())
                     .average(function(d) { return d.mean; })
                     .clipVoronoi(true)
                     .showControls(false);

          if(rango=="Días"){
            chart.xAxis
              .tickFormat(function(d) {
                  return d3.time.format('%d')(new Date(d))
                });
          }
          if(rango=="Meses"){
            chart.xAxis
              .tickFormat(function(d) {
                  return d3.time.format('%B')(new Date(d))
                });
          }
          if(rango=="Años"){
            chart.xAxis
              .tickFormat(function(d) {
                  return d3.time.format('%Y')(new Date(d))
                });
          }
          
          chart.yAxis
              .tickFormat(d3.format('d'));

          d3.select('#grafica svg')              
              .datum(dataLineChart)
              .transition().duration(500)
              .call(chart);

          //TODO: Figure out a good way to do this automatically
          nv.utils.windowResize(chart.update);
          //nv.utils.windowResize(function() { d3.select('#chart1 svg').call(chart) });


          chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

          return chart;
        });
    }
    
    if(chartType == 'Dispersion') { 
        <%
         datasetTitle = "";
         if(dataset!=null){
             datasetTitle = datasetObj.getDatasetTitle();
         }%>
        var rango = '<%=rango%>';
        dataScatterChart = [ 
                {
                key: "<%=datasetTitle%>",
                values: [
                <%
                    int iD=0;
                    for(ChartData cd : datos){
                         if(rango.equals(ChartsResource.RANGO_MESES)){
                %>                                   
                { x : <%=cd.getStartDate().getMonth()%>, y : <%=cd.getCount()%>, size: Math.random() }
                <%       }else{%>
                         
                { x : <%=cd.getTitle()%>, y : <%=cd.getCount()%>, size: 2 }         <%
                         }                       
                         iD++;
                         if(iD<datos.size()){ %>,<%}%>
                <%
                    }
                %> 
                 ]
                 }
                 ];
        var chart;
        nv.addGraph(function() {
          chart = nv.models.scatterChart()
                        .showDistX(true)
                        .showDistY(true)                        
                        .useVoronoi(true)
                        .color(d3.scale.category10().range());
          chart.xAxis
                  .tickFormat(d3.format('d'));;
          chart.yAxis
                  .tickFormat(d3.format('d'));
          d3.select('#grafica svg')
              .datum(dataScatterChart)
            .transition().duration(500)
              .call(chart);
          nv.utils.windowResize(chart.update);
          chart.dispatch.on('stateChange', function(e) { ('New State:', JSON.stringify(e)); });
          return chart;
        });
    }
    if(chartType == 'Pastel') { 
        var dataPieChart = [
            <%
                int iP=0;
                for(ChartData cd : datos){
            %>
            { "key" : "<%=cd.getTitle()%>", "y" : <%=cd.getCount()%> }
            <%      iP++;
                    if(iP<datos.size()){ %>,<%}%>
            <%
                }
            %> 
        ];
        nv.addGraph(function() {
            var width = 600,
            height = 380;
            var chart = nv.models.pieChart()
                .x(function(d) { return d.key })
                .y(function(d) { return d.y })
                .showLabels(false)
                .values(function(d) { return d })
                .color(d3.scale.category10().range())
                .width(width)
                .height(height);
            d3.select("#grafica svg")
                .datum([dataPieChart])
                .transition().duration(1200)
                .attr('width', width)
                .attr('height', height)
                .call(chart);
            chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
            return chart;
        });
    }
</script>
    
<script>   
    function setConstrain() {
        dijit.byId('startDate').constraints.max = new Date();
        dijit.byId('finishDate').constraints.max = new Date();
    }
</script>
<form method="post"  action="<%=urlAction.setMode("GRAPHIC").setParameter("dataType", DataType)%>">
    <div id="graficador">
        <div id="categoria">            
            <%if(!DataType.equals("views")){%>
                <a href="<%=urlTypeData+"views"+paramIdDataset+paramSDate+paramFDate%>">            
                <%=paramRequest.getLocaleString("lbl_Views")%>            
                </a>
            <%}%>
            <%if(DataType.equals("views")){%>
                <span><%=paramRequest.getLocaleString("lbl_Views")%></span>
            <%}%>   
            <%if(!DataType.equals("hits")){%>   
               <a href="<%=urlTypeData+"hits"+paramIdDataset+paramSDate+paramFDate%>" />
               <%=paramRequest.getLocaleString("lbl_Downloads")%>
               </a>
            <%}%>
            <%if(DataType.equals("hits")){%>
                <span><%=paramRequest.getLocaleString("lbl_Downloads")%></span>            
            <%}%>
            <%if(!DataType.equals("comments")){%> 
                <a href="<%=urlTypeData+"comments"+paramIdDataset+paramSDate+paramFDate%>" />
                <%=paramRequest.getLocaleString("lbl_Comments")%>
                </a>
            <%}%>            
            <%if(DataType.equals("comments")){%>                
                <span><%=paramRequest.getLocaleString("lbl_Comments")%></span>
            <%}%>            
            <%if(!DataType.equals("appl")){%> 
                <a href="<%=urlTypeData+"appl"+paramIdDataset+paramSDate+paramFDate%>" />
                <%=paramRequest.getLocaleString("lbl_Applications")%>
                </a>
            <%}%>            
            <%if(DataType.equals("appl")){%>
                <span><%=paramRequest.getLocaleString("lbl_Applications")%></span>
            <%}%>            
        </div>
        <div id="izq-graf">
            <p> <%=titleGraph%> </p>
            <div id="grafica">                
                <svg></svg>
            </div>        
            <div  id="graficatipo">
                <label for="gbarras">
                    <input type="radio" name="chartType" value="Barra" id="Barra"
                    <%if(chartType.equals("Barra")){%>checked="true"<%}%>>
                    <%=paramRequest.getLocaleString("lbl_Bars")%> 
                    <%if(chartType.equals("Barra")){%><%=paramRequest.getLocaleString("lbl_Select")%><%}%>
                </label>
                <label for="glineal">
                    <input type="radio" name="chartType" value="Lineal" id="Lineal"
                    <%if(chartType.equals("Lineal")){%>checked="true"<%}%> > 
                    <%=paramRequest.getLocaleString("lbl_lineal")%> 
                    <%if(chartType.equals("Lineal")){%><%=paramRequest.getLocaleString("lbl_Select")%><%}%>
                </label>
                <label for="gdisper">  
                    <input type="radio" name="chartType" value="Dispersion" id="Dispersion"
                    <%if(chartType.equals("Dispersion")){%>checked="true"<%}%> > 
                    <%=paramRequest.getLocaleString("lbl_Scatter")%> 
                    <%if(chartType.equals("Dispersion")){%><%=paramRequest.getLocaleString("lbl_Select")%><%}%>
                </label>
                <label for="gpastel">
                    <input type="radio" name="chartType" value="Pastel" id="Pastel"
                    <%if(chartType.equals("Pastel")){%>checked="true"<%}%> > 
                    <%=paramRequest.getLocaleString("lbl_Pie")%> 
                    <%if(chartType.equals("Pastel")){%><%=paramRequest.getLocaleString("lbl_Select")%><%}%>
                </label>                            
            </div>
            <div id="grafpie">
                <div id="graficafecha">                   
                    <p><%=paramRequest.getLocaleString("lbl_Period_of")%>
                      <input name="startDate" id="startDate" type="text"
                      constraints="{datePattern:'dd/MM/yyyy'}"        
                      dojoType="dijit.form.DateTextBox" required="true" 
                      promptMessage="dd/MM/yyyy" valid="true"
                      invalidMessage="Fecha invalida. Use el formato dd/MM/yyyy."
                      value="<%=fechaInicial%>" trim="true"
                      onchange="dijit.byId('finishDate').constraints.min = arguments[0];" 
                      onclick ="setConstrain();" /> 
                      <%=paramRequest.getLocaleString("lbl_to")%> 
                      <input name="finishDate" id="finishDate" type="text" 
                      constraints="{datePattern:'dd/MM/yyyy'}"       
                      dojoType="dijit.form.DateTextBox" required="true"  
                      promptMessage="dd/MM/yyyy" valid="true"  
                      invalidMessage="Fecha invalida. Use el formato dd/MM/yyyy."
                      value="<%=fechaFinal%>" trim="true"
                      onchange="dijit.byId('startDate').constraints.max = arguments[0];" 
                      onclick="setConstrain();" /> 
                    </p>
                </div>
                <div id="graficaunidad">
                    <%=paramRequest.getLocaleString("lbl_Unit")%> <%=rango%>
                </div>                                                
            </div>
        </div>
        <div id="der-graf">
            <div id="grafdatasets">
                <p><%=paramRequest.getLocaleString("lbl_Dataset")%></p>
                <select size="10" name="dataset" id="dataset" 
                required="true" >
                <%
                Iterator<Dataset> listDS = Dataset.ClassMgr.listDatasets(wsite);
                while(listDS.hasNext()){
                Dataset ds = listDS.next(); 
                String selected = "";
                if(ds.isDatasetActive() && ds.isApproved()){
                    if(ds.getId().equalsIgnoreCase(idDSSelected)){
                        selected = "selected";
                    }                
                %>
                    <option <%=selected%> value="<%=ds.getId()%>" ><%=ds.getDatasetTitle()%></option>
                <%}
                }%>
                </select>
            </div>
        </div>
        <input type="Submit" value="Gráficar" class="boton-simple" />         
    </div>          
</form> 
  
       
