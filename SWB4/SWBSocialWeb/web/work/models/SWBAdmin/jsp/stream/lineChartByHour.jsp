<%-- 
    Document   : lineChartByHour
    Created on : 2/05/2014, 07:01:56 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.Calendar"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.social.Stream"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%
    String suri = request.getParameter("suri");    
    if(suri == null)return;
    SemanticObject semObj = SemanticObject.createSemanticObject(suri);
    if(semObj == null)return;
    String title = "";
    Iterator<PostIn> itObjPostIns = null;    
    if (semObj.getGenericInstance() instanceof Stream) {
        Stream stream = (Stream) semObj.getGenericInstance();
        itObjPostIns = stream.listPostInStreamInvs();
        title = stream.getTitle();
    } else if (semObj.getGenericInstance() instanceof SocialTopic) {
        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        title = socialTopic.getTitle();
    }    
    if (itObjPostIns == null || !itObjPostIns.hasNext()) {
%>
<script>
    var ifHour = parent.document.getElementById('<%=suri + "byHour"%>');
    if(ifHour){
        ifHour.style.height = '0px';
    }
    </script>
<%
        return;
    }    
    java.util.Date date = null;
    Calendar calendario = Calendar.getInstance();
    int dataArray[][] = new int[24][3];//positive, negative, neutrals
    int totalPosts = 0;
    while(itObjPostIns.hasNext()){
        PostIn postIn = itObjPostIns.next();
        if (postIn.getPi_createdInSocialNet() != null) {
            date = postIn.getPi_createdInSocialNet();
        }
        
        if(date != null){
            calendario.setTime(date);
        }else{
            continue;
        }

        int hourOfDay = calendario.get(Calendar.HOUR_OF_DAY);
        if (postIn.getPostSentimentalType() == 0) {//neutrals
            dataArray[hourOfDay][2]++;
        } else if (postIn.getPostSentimentalType() == 1) {//positives
            dataArray[hourOfDay][0]++;
        } else if (postIn.getPostSentimentalType() == 2) {//negatives
            dataArray[hourOfDay][1]++;
        }
        totalPosts++;        
    }    
%>

<meta charset="utf-8">
<link href="/swbadmin/css/nv.d3.css" rel="stylesheet" type="text/css">

<style>

body {
  overflow-y:scroll;
}

text {
  font: 12px sans-serif;
}

svg {
  display: block;
}

#chart1 svg {
  height: 500px;
  min-width: 200px;
  min-height: 100px;
/*
  margin: 50px;
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/

}
#chart1 {
  margin-top: 10px;
  margin-left: 100px;
}
</style>
<body class='with-3d-shadow with-transitions'>

<div align="center" style="margin-left: 100px; width: 700px">N&Uacute;MERO DE MENSAJES POR HORA DEL D&Iacute;A</div>
<div id="chart1" >  
  <svg style="height: 500px;"></svg>
</div>

<script src="../../js/d3.v3.js"></script>
<script src="../../js/nv.d3.js"></script>

<script>
// Wrapping in nv.addGraph allows for '0 timeout render', stores rendered charts in nv.graphs, and may do more in the future... it's NOT required
var chart;

nv.addGraph(function() {
  chart = nv.models.lineChart()
  .options({
    margin: {left: 80, bottom: 8},
    x: function(d,i) { return i},
    showXAxis: true,
    showYAxis: true,
    transitionDuration: 250
  })
  ;

  // chart sub-models (ie. xAxis, yAxis, etc) when accessed directly, return themselves, not the parent chart, so need to chain separately
  chart.xAxis
    .axisLabel("Hora del dia")
    .tickFormat(d3.format(',.1d'));

  chart.yAxis
    .axisLabel('Numero de posts')
    .tickFormat(d3.format("d"))
    ;

  d3.select('#chart1 svg')
    .datum(getChartData())
    .call(chart);

  //TODO: Figure out a good way to do this automatically
  nv.utils.windowResize(chart.update);
  //nv.utils.windowResize(function() { d3.select('#chart1 svg').call(chart) });

  chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

  return chart;
});

function getChartData() {
  var total = [];
  var positives =[];
  var negatives =[];
  var neutrals =[];
  
       <% for (int i=0; i < dataArray.length; i++) { %>
        positives.push({x:<%=i%>, y: <%= dataArray[i][0] %>});
        negatives.push({x:<%=i%>, y: <%= dataArray[i][1] %>});
        neutrals.push({x:<%=i%>, y: <%= dataArray[i][2] %>});
        total.push({x:<%=i%>, y: <%= dataArray[i][0] + dataArray[i][1] +dataArray[i][2] %>});        
        <% } %>
	
  

  return [
    {
      values: total,
      positivos: positives,
      negativos: negatives,
      neutros: neutrals,
      key: "Datos de <%=title%>",
      color: "#FF6600"
    }
  ];
}

</script>