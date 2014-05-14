<%-- 
    Document   : lineChartByHourByNet
    Created on : 6/05/2014, 05:38:53 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.json.*"%>
<%@page import="java.util.*"%> 
<%@page import="java.util.Calendar"%> 
<%@page import="static org.semanticwb.social.admin.resources.PieChart.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%
    String suri = request.getParameter("suri");    
    if(suri == null)return;
    SemanticObject semObj = SemanticObject.createSemanticObject(suri);
    if(semObj == null)return;
    
    Iterator<PostIn> itObjPostIns = null;
    HashMap<SocialNetwork,Integer > networks = new HashMap<SocialNetwork,Integer>();
    if (semObj.getGenericInstance() instanceof Stream) {
        Stream stream = (Stream) semObj.getGenericInstance();
        itObjPostIns = stream.listPostInStreamInvs();
        ArrayList nets = SWBSocialUtil.sparql.getStreamSocialNetworks(stream);
        for(int i = 0; i < nets.size(); i++){
            SocialNetwork snet= (SocialNetwork)((SemanticObject)nets.get(i)).createGenericInstance();
            networks.put(snet, i);
            //System.out.println("RED SOCIAL:" + snet);
        }
        //if(1==1)return;
    } else if (semObj.getGenericInstance() instanceof SocialTopic) {
        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        ArrayList nets = SWBSocialUtil.sparql.getSocialTopicSocialNetworks(socialTopic);
        for(int i = 0; i < nets.size(); i++){
            SocialNetwork snet= (SocialNetwork)((SemanticObject)nets.get(i)).createGenericInstance();
            networks.put(snet, i);
        }
        itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
    }
    
    
    if (itObjPostIns == null) {
        JSONArray node = new JSONArray();
        //return node;
    }    
    java.util.Date date = null;
    Calendar calendario = Calendar.getInstance();
    int netsNumber = networks.size();
    if(netsNumber == 0)return;
    String title[] = new String[netsNumber];
    int dataArray[][][] = new int[netsNumber][24][3];//positive, negative, neutrals
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
        int socialNet = networks.get(postIn.getPostInSocialNetwork());
        //System.out.println("---------:"  + socialNet);
        int hourOfDay = calendario.get(Calendar.HOUR_OF_DAY);
        if (postIn.getPostSentimentalType() == 0) {//neutrals
            dataArray[socialNet][hourOfDay][2]++;
        } else if (postIn.getPostSentimentalType() == 1) {//positives
            dataArray[socialNet][hourOfDay][0]++;
        } else if (postIn.getPostSentimentalType() == 2) {//negatives
            dataArray[socialNet][hourOfDay][1]++;
        }
        title[socialNet] = postIn.getPostInSocialNetwork().getTitle();
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
  height: 430px;
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

<div id="chart1" >
  <svg style="height: 430px;"></svg>
</div>

<script src="../../js/d3.v3.js"></script>
<script src="../../js/nv.d3.js"></script>

<script>
function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
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
  <%
  for (int i=0; i < dataArray.length; i++){%>
    var total<%=i%> = [];
    var positives<%=i%> =[];
    var negatives<%=i%> =[];
    var neutrals<%=i%> =[];
  <%}%>  
  
       <% for (int i=0; i < dataArray.length; i++) { 
           for(int j=0; j < dataArray[i].length; j++){%>
        positives<%=i%>.push({x:<%=i%>, y: <%= dataArray[i][j][0] %>});
        negatives<%=i%>.push({x:<%=i%>, y: <%= dataArray[i][j][1] %>});
        neutrals<%=i%>.push({x:<%=i%>, y: <%= dataArray[i][j][2] %>});
        total<%=i%>.push({x:<%=i%>, y: <%= dataArray[i][j][0] + dataArray[i][j][1] +dataArray[i][j][2] %>});
        <% }} %>
	
  

  return [
    <%for (int i = 0; i < dataArray.length; i++){%>
    {
      values: total<%=i%>,
      positivos: positives<%=i%>,
      negativos: negatives<%=i%>,
      neutros: neutrals<%=i%>,
      key: "<%=title[i]%>",
      color: getRandomColor()
    }
    <%if(i < dataArray.length-1)
        out.println(",");
    }%>
  ];
}

</script>