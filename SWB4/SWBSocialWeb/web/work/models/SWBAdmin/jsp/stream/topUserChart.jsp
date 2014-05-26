<%-- 
    Document   : topUserChart
    Created on : 20/05/2014, 11:43:14 AM
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
    String title = "";
    LinkedHashMap usersByStream = null;//<SocialNetUser,post number>
    LinkedHashMap<SocialNetworkUser, Integer[]> userCount= new LinkedHashMap<SocialNetworkUser,Integer[]>();//SocialNetUser, [neutrals][positives][negatives]
    
    if (semObj.getGenericInstance() instanceof Stream) {
        Stream stream = (Stream) semObj.getGenericInstance();
        title = stream.getTitle();
        usersByStream = SWBSocialUtil.sparql.getSocialUsersInStream(stream);             
    } else if (semObj.getGenericInstance() instanceof SocialTopic) {
        SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
        title = socialTopic.getTitle();
        usersByStream = SWBSocialUtil.sparql.getSocialUsersInSocialTopic(socialTopic);
        //itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
    }
    
    Iterator usersToCount =  usersByStream.entrySet().iterator();
    int maxUsers = 0;
    while(usersToCount.hasNext()){
        if(++maxUsers > 10 )break;
        Map.Entry pair = (Map.Entry)usersToCount.next();
        SocialNetworkUser snetu= (SocialNetworkUser)((SemanticObject)pair.getKey()).createGenericInstance();
        
        Iterator posts = snetu.listPostInInvs();//Lists user posts
        Integer[] sentimentCounter = {0,0,0};//array of posts number [neutrals][positive][neagtive]
        while(posts.hasNext()){
            PostIn postIn = (PostIn)posts.next();
            //adds 1 depending what is the post sentiment
            if(postIn.getPostSentimentalType() >= 0 &&postIn.getPostSentimentalType() <=2 ){
                sentimentCounter[postIn.getPostSentimentalType()]++;
            }
        }
        userCount.put(snetu, sentimentCounter);
    }
    
    if(usersByStream == null || usersByStream.size() <= 0){
%>
<script>
    var ifHour = parent.document.getElementById('<%=suri + "byUser"%>');
    if(ifHour){
        ifHour.style.height = '0px';
    }
    </script>
<%
        return;
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

#chart1 {
  margin: 10px;
  min-width: 100px;
  min-height: 100px;
/*
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/
}

#chart1 svg {
  height: 500px;
}

</style>
<body class='with-3d-shadow with-transitions'>

<div id="chart1" >
  <div align="center" style="margin-left: 100px; width: 700px">USUARIOS CON M&Aacute;S INTERACCI&Oacute;N</div>
  <svg style="height: 430px;"></svg>
</div>

<script src="../../js/d3.v3.js"></script>
<script src="../../js/nv.d3.js"></script>

<script>

var chart;
nv.addGraph(function() {
  chart = nv.models.multiBarHorizontalChart()
      .x(function(d) { return d.label })
      .y(function(d) { return d.value })
      .margin({top: 30, right: 20, bottom: 50, left: 175})
      //.showValues(true)
      //.tooltips(false)
      //.barColor(d3.scale.category20().range())
      .transitionDuration(250)
      .stacked(true)
      .showControls(true);

  chart.yAxis
      .axisLabel("No. de mensajes")
      .tickFormat(d3.format(',.1d'));

  d3.select('#chart1 svg')
      .datum(getChartData())
      .call(chart);

  nv.utils.windowResize(chart.update);

  chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

  return chart;
});

function getChartData() {      
    return[
        <%
            String labels[] = {"Neutros","Positivos","Negativos"};
            String colors[] = {"#FFD700","#008000","#FF0000"};
            for(int i = 0; i < 3 ; i++){//for
        %>
        {
            key:'<%=labels[i]%>',
            color:'<%=colors[i]%>',
            values:[
        <%
                maxUsers = 0;
                Iterator entries =  userCount.entrySet().iterator();
                while(entries.hasNext()){//while
                    if(++maxUsers > 10 )break;
                    Map.Entry entry = (Map.Entry)entries.next();
                    SocialNetworkUser userEntry = (SocialNetworkUser) entry.getKey();
                    Integer entrySentiments[] = (Integer[]) entry.getValue();
        %>
            {
                "label": "<%=userEntry.getSnu_name()%>",
                "value": <%=entrySentiments[i]%>
            }<%=entries.hasNext() && maxUsers < 10 ? ",":""%>
        <%
                }//while
        %>
            ]
        }<%=i<2 ?",":""%>
        <%
            }//for
        %>                                                
    ];
}

</script>