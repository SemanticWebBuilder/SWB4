<%-- 
    Document   : engagementRateChart
    Created on : 2/10/2014, 01:29:13 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="java.io.PrintWriter"%>
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
    
    SimpleDateFormat formatSince = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    LinkedHashMap<Timestamp, Integer[]> chartData = new LinkedHashMap<Timestamp, Integer[]>();
    
    Date dateSince = null;
    Date dateTo = null;
    
    if(SemanticObject.getSemanticObject(suri) != null){
        SocialNetwork socialNet = (SocialNetwork)SemanticObject.getSemanticObject(suri).createGenericInstance();
        Connection con = SWBUtils.DB.getDefaultConnection();
        String sinceDate = request.getParameter("engagement_inidate");
        String toDate = request.getParameter("engagement_enddate");
        
        try{
                if(sinceDate != null && !sinceDate.trim().isEmpty() &&
                        toDate != null && !toDate.trim().isEmpty()){

                toDate += " 23:59:59";

                dateSince = formatSince.parse(sinceDate);
                dateTo = formatTo.parse(toDate);
                }

        }catch(ParseException pe){
            /*ignored*/
        }

        String sql = "select * from socialnets_stats where socialNet=?";
        if(dateSince != null && dateTo != null){
            sql = "select * from socialnets_stats WHERE socialNet=? AND date BETWEEN ? AND ?";
        }
        try{
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, socialNet.getURI());
            if(dateSince != null && dateTo != null){
                st.setTimestamp(2, new Timestamp(dateSince.getTime()));
                st.setTimestamp(3, new Timestamp(dateTo.getTime()));
                //System.out.println("running sql:" + sql);
            }
            ResultSet rs = st.executeQuery();


            while(rs.next()){
                Integer entry [] = new Integer[2];
                entry[0] = rs.getInt("followers");
                entry[1] = rs.getInt("ptat");
                
                chartData.put(rs.getTimestamp("date"), entry);
                
                /*out.println("<p>");
                out.println("socialsite: " + rs.getString("socialsite"));
                out.println("socialNet: " + rs.getString("socialNet"));
                out.println("friends: " + rs.getInt("friends"));
                out.println("followers: " + rs.getInt("followers"));
                out.println("ptat: " + rs.getInt("ptat"));
                out.println("date " + rs.getTimestamp("date"));
                out.println("</p>");*/
            }
          
            
        }catch(SQLException sqle){
//            System.out.println("error....." +  sqle.getMessage());
        }       
    }else{
        return;
    }
    //System.out.println("growth in jsp");
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
  min-width: 100px;
  min-height: 100px;
/*
  margin: 50px;
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/
}

</style>
<body class='with-3d-shadow with-transitions'>
    <div align="center"><h3>% DE ENGAGEMENT DEL <%=formatSince.format(dateSince)%> AL <%=formatSince.format(dateTo)%></h3></div>
<div id="chart1" >  
  <svg style="height: 500px;"></svg>
</div>

<script src="/work/models/SWBAdmin/js/d3.v3.js"></script>
<script src="/work/models/SWBAdmin/js/nv.d3.js"></script>

<script type="text/javascript"> 
    function getChartData() {
        var data = [];
        var engagement = [];
        
<%
            for (Map.Entry<Timestamp, Integer[]> entry : chartData.entrySet()) {
                try{
                long timestamp = entry.getKey().getTime();
                Integer tmp[] = entry.getValue();
                int followers = tmp[0];
                int ptat = tmp[1];
%>
                engagement.push({x:<%=timestamp%>, y: <%=((ptat)/(float)followers) * 100.0%>});
<%
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
%>      
        data.push({key:"Engagement", values: engagement});
        return data;
    }
    
    
    nv.addGraph(function() {
    var chart = nv.models.lineWithFocusChart();

    //Format x-axis labels with custom function.
    chart.xAxis
        .tickFormat(function(d) { 
         return d3.time.format('%d/%m/%y')(new Date(d)) 
    });
    chart.x2Axis.tickFormat(function(d) { 
         return d3.time.format('%d/%m/%y')(new Date(d)) 
    });

    chart.yAxis
    .tickFormat(d3.format(',.2f'));

    chart.y2Axis
    .tickFormat(d3.format(',.2f'));
    //console.log('data::' + testData().toSource());
    d3.select('#chart1 svg')
        .datum(getChartData())
        .call(chart);

    nv.utils.windowResize(chart.update);

    return chart;
  });
</script>
