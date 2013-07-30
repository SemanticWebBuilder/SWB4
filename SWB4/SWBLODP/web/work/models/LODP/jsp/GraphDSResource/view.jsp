<%-- 
    Document   : view
    Created on : 26/07/2013, 02:19:28 PM
    Author     : sabino.pariente
--%>
<%@page import="com.infotec.lodp.swb.resources.ChartData"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.infotec.lodp.swb.Dataset"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%  
    List<ChartData>  data = new ArrayList();
    List<SemanticProperty> listProp = new ArrayList();
    SemanticProperty xProperty = null;
    SemanticProperty yProperty = null;
    SemanticClass sc = null;
    String xPropertyUri = "";
    String yPropertyUri = "";
    String lang=paramRequest.getUser().getLanguage();
    WebPage wpage = paramRequest.getWebPage();
    SWBResourceURL urlAction = paramRequest.getRenderUrl();
    String graphType = request.getParameter("graphType")==null?"Bars":request.getParameter("graphType");    
    String classDatasetUri = request.getParameter("datasetUri")==null?Dataset.sclass.getURI().toString():request.getParameter("datasetUri");
    if(!classDatasetUri.isEmpty()){                
        sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(classDatasetUri);
        Iterator<SemanticProperty> itProp = sc.listProperties();        
        while(itProp.hasNext()){
            SemanticProperty sp = itProp.next();        
            listProp.add(sp);
        }
        xPropertyUri = request.getParameter("axisX");        
        yPropertyUri = request.getParameter("axisY");        
        if(xPropertyUri != null && yPropertyUri != null){            
            xProperty = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(xPropertyUri);
            yProperty = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(yPropertyUri);
            Iterator <SemanticObject> it=wpage.getSemanticObject().getModel().listInstancesOfClass(sc);                      
            while (it.hasNext()) {
                SemanticObject obj = it.next();
                ChartData cd = new ChartData();
                cd.setTitle(obj.getProperty(xProperty));
                cd.setCount(obj.getLongProperty(yProperty)); 
                data.add(cd);                
            }
        }
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
<script src="/work/models/LODP/jsp/ChartsResource/barChart/multiBarHorizontal.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/multiBarHorizontalChart.js"></script>
<script src="/work/models/LODP/jsp/ChartsResource/barChart/legend.js"></script>
<script>
    var chartType = '<%=graphType%>'; 
    <%
    String datasetTitle = "";
    if(sc!=null){
         datasetTitle = sc.getDisplayName(lang);
    }
    %>
    if(chartType == 'Bars') { 
        dataBarCols = [
          {
          key: "<%=datasetTitle%>",
          values: [
          <%
          int iB=0;
          for(ChartData cd : data){
          %>
            { "label" : "<%=cd.getTitle()%>", "value" : <%=cd.getCount()%> }
          <%iB++;
            if(iB<data.size()){ %>,<%}
          }%> 
          ]    
          }
        ];
        nv.addGraph(function() {
        var chart = nv.models.multiBarHorizontalChart()
            .x(function(d) { return d.label })
            .y(function(d) { return d.value })
            .margin({top: 30, right: 20, bottom: 50, left: 175})
            .showValues(true)
            .tooltips(true)
            .showControls(false);

        chart.yAxis
            .axisLabel('Users')
            .tickFormat(d3.format('d'));

        d3.select('#grafica svg')
            .datum(dataBarCols)
          .transition().duration(500)
            .call(chart);

        nv.utils.windowResize(chart.update);

        return chart;
      });
    }  
    if(chartType == 'Cols') {          
        dataBarChart = [ 
            {
            key: "<%=datasetTitle%>",
            values: [
            <%
                iB=0;
                for(ChartData cd : data){
            %>
            { "label" : "<%=cd.getTitle()%>", "value" : <%=cd.getCount()%> }
            <%      iB++;
                    if(iB<data.size()){ %>,<%}
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
    if(chartType == 'Line') {                   
        dataLineChart= [
            {
            "key": "<%=datasetTitle%>",
            "values": [ 
                <%
                int iL=0;
                for(ChartData cd : data){
                %>
                [ "<%=cd.getTitle()%>" , <%=cd.getCount()%>] 
                <%
                    iL++;
                    if(iL<data.size()){ %>,<%}
                }
                %> 
            ]
            }
        ];         
        nv.addGraph(function() {  
            var chart = nv.models.cumulativeLineChart()
                .x(function(d) { return d[0] })
                .y(function(d) { return d[1]})
                .color(d3.scale.category10().range())
                .average(function(d) { return d.mean; })
                .clipVoronoi(true)
                .showControls(false)
                .tooltips(true);                
            chart.xAxis
                .tickFormat(d3.format('d'));         
            chart.yAxis
                .tickFormat(d3.format('d'));
            d3.select('#grafica svg')              
                .datum(dataLineChart)
                .transition().duration(500)
                .call(chart);
            nv.utils.windowResize(chart.update);
            chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
            return chart;
        });
    }
    if(chartType == 'Scatter') {         
        dataScatterChart = [ 
            {
            key: "<%=datasetTitle%>",
            values: [
            <%
            int iD=0;
            for(ChartData cd : data){
            %>                               
                { x : "<%=cd.getTitle()%>", y : <%=cd.getCount()%>, size: 2 }         
            <%  iD++;
                if(iD<data.size()){ %>,<%}
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
                .color(d3.scale.category10().range())
                .tooltips(true);
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
    if(chartType == 'Pie') { 
        var dataPieChart = [
            <%
                int iP=0;
                for(ChartData cd : data){
            %>
            { "key" : "<%=cd.getTitle()%>", "y" : <%=cd.getCount()%> }
            <%      iP++;
                    if(iP<data.size()){ %>,<%}%>
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
                .showLabels(true)
                .values(function(d) { return d })
                .color(d3.scale.category20c().range())
                .width(width)
                .height(height)
                .tooltips(true);
            d3.select("#grafica svg")
                .datum([dataPieChart])
                .transition().duration(30000)
                .attr('width', width)
                .attr('height', height)
                .call(chart);
            chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });
            return chart;
        });
    }
</script>
<form method="post"  action="<%=urlAction.setMode("GRAPHICS").setParameter("datasetUri", classDatasetUri) %>">
    <div id="izq-graf">    
        <div id="grafica">                
            <svg></svg>
        </div>
    </div>
    <div id="der-graf">
        <p><label for="graphType"><%=paramRequest.getLocaleString("lbl_graph_type")%></label></p>
        <p>
            <select id="graphType" name="graphType">                
                <option value="Bars" <%=(graphType.equals("Bars")?"Selected":"")%>>
                    <%=paramRequest.getLocaleString("lbl_bars")%>
                </option>
                <option value="Cols" <%=(graphType.equals("Columns")?"Selected":"")%>>
                    <%=paramRequest.getLocaleString("lbl_cols")%>
                </option>
                <option value="Line" <%=(graphType.equals("Line")?"Selected":"")%>>
                    <%=paramRequest.getLocaleString("lbl_line")%>
                </option>
                <option value="Scatter" <%=(graphType.equals("Scatter")?"Selected":"")%>>
                    <%=paramRequest.getLocaleString("lbl_scatter")%>
                </option>
                <option value="Pie" <%=(graphType.equals("Pie")?"Selected":"")%>>
                    <%=paramRequest.getLocaleString("lbl_pie")%>
                </option>
            </select>
        </p>
        <p><label for="axisX"><%=paramRequest.getLocaleString("lbl_x_axis")%></label></p>
        <p>
            <select id="axisX" name="axisX">
                <%for(SemanticProperty sp:listProp ){  
                    if(sp.isString()||sp.isDate()||sp.isInt() || sp.isFloat() || sp.isDouble() || sp.isLong()){
                %>
                    <option value="<%=sp.getURI()%>" <%=(sp.getURI().equals(xPropertyUri)?"Selected":"")%>>
                        <%=sp.getDisplayName()%></option>
                <%  }
                 }%>
            </select>
        </p>
        <p><label for="axisY"><%=paramRequest.getLocaleString("lbl_y_axis")%></label></p>
        <p><select id="axisY" name="axisY">
            <%for(SemanticProperty sp:listProp){
                if(sp.isInt() || sp.isFloat() || sp.isDouble() || sp.isLong()){
            %>
            <option value="<%=sp.getURI()%>" <%=(sp.getURI().equals(yPropertyUri)?"Selected":"")%>>
                <%=sp.getDisplayName(lang)%></option>
            <% }
            }%>
            </select>
        </p>
        <p><input type="Submit" value="<%=paramRequest.getLocaleString("lbl_graphing")%>" class="boton-simple" /></p>
    </div>
</form>

    