<%-- 
    Document   : growthCharts
    Created on : 5/09/2014, 12:52:43 PM
    Author     : francisco.jimenez
--%>

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
    
    String title = "";
    Iterator<PostIn> itObjPostIns = null;    
    if (1==2) {
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
    
    LinkedHashMap<Timestamp, Integer[]> chartData = new LinkedHashMap<Timestamp, Integer[]>();
    
    
    System.out.println("SocialNetGrowth....");
    if(SemanticObject.getSemanticObject(suri) != null){
        System.out.println("Social Net 1");
        SocialNetwork socialNet = (SocialNetwork)SemanticObject.getSemanticObject(suri).createGenericInstance();
        Connection con = SWBUtils.DB.getDefaultConnection();

        String sql = "select * from socialnets_stats where socialNet=?";

        try{
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, socialNet.getURI());                
            ResultSet rs = st.executeQuery();


            while(rs.next()){
                Integer entry [] = new Integer[3];
                entry[0] = rs.getInt("friends");
                entry[1] = rs.getInt("followers");
                entry[2] = rs.getInt("ptat");
                
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
            /*String sql1 = "insert into socialnets_stats(socialsite,socialNet,friends,followers,ptat,date) values(?,?,?,?,?,?)";
            st = con.prepareStatement(sql1);
            st.setString(1, "http://www.Pacone.swb#Pacone");
            st.setString(2, "http://www.Pacone.swb#social_Facebook:8");
            st.setInt(3, 63);
            st.setInt(4, 46);
            st.setInt(5, 1);
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateAsString = "21/9/2014";
            Date date = sourceFormat.parse(dateAsString);
            st.setTimestamp(6, new Timestamp(date.getTime()));
            st.execute();*/
            
        }catch(SQLException sqle){
            System.out.println("error....." +  sqle.getMessage());
        }       
    }else{
        return;
    }
    System.out.println("growth in jsp");
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

<div align="center">CRECIMIENTO DE CONTACTOS Y PTAT</div>
<div id="chart1" >  
  <svg style="height: 500px;"></svg>
</div>

<script src="/work/models/SWBAdmin/js/d3.v3.js"></script>
<script src="/work/models/SWBAdmin/js/nv.d3.js"></script>

<script type="text/javascript"> 
    function getChartData() {
        var data = [];
        var friends = [];
        var followers = [];
        var ptat = [];
        
<%
            for (Map.Entry<Timestamp, Integer[]> entry : chartData.entrySet()) {
                try{
                long timestamp = entry.getKey().getTime();
                Integer tmp[] = entry.getValue();
                int friends = tmp[0];
                int followers = tmp[1];
                int ptat = tmp[2];
%>
                friends.push({x:<%=timestamp%>, y: <%=friends%>});
                followers.push({x:<%=timestamp%>, y: <%=followers%>});
                ptat.push({x:<%=timestamp%>, y: <%=ptat%>});
<%
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("eeeeeeeeeroror");
                }
            }
%>      
        data.push({key:"Amigos", values: friends});
        data.push({key:"Seguidores", values: followers});
        data.push({key:"PTAT", values: ptat});
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
        .tickFormat(d3.format('d'));
    chart.y2Axis
        .tickFormat(d3.format('d'));

    //console.log('data::' + testData().toSource());
    d3.select('#chart1 svg')
        .datum(getChartData())
        .call(chart);

    nv.utils.windowResize(chart.update);

    return chart;
  });
</script>
