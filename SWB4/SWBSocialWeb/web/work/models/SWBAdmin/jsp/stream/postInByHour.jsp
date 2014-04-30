<%-- 
    Document   : postInByHour
    Created on : 25/04/2014, 12:09:44 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.SWBPlatform"%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
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

<%!
    JSONArray getObject(SemanticObject semObj, String lang, HttpServletRequest request) throws Exception {
        String fullDate = "";
        ArrayList aListFilter = new ArrayList();
        String selectedAnio = request.getParameter("selectedAnio") == null ? "" : request.getParameter("selectedAnio");
        String selectedMes = request.getParameter("selectedMes") == null ? "" : request.getParameter("selectedMes");
        String selectedDia = request.getParameter("selectedDia") == null ? "" : request.getParameter("selectedDia");
        
        if(selectedAnio.trim().isEmpty() || selectedMes.trim().isEmpty() || selectedDia.trim().isEmpty()){
            JSONArray node = new JSONArray();
            return node;
        }
        fullDate += selectedAnio + "-" + (selectedMes.length() == 1 ? "0" + selectedMes : selectedMes) +
                 "-" + (selectedDia.length() == 1 ? "0" + selectedDia : selectedDia);
        
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            aListFilter = getPostInByStreamAndDay(stream, fullDate);            
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            aListFilter = getPostInBySocialTopicAndDay(socialTopic, fullDate);
        }

        
        if (aListFilter.size() == 0) {
            JSONArray node = new JSONArray();
            return node;
        }
        
        java.util.Date date = null;
        Calendar calendario = GregorianCalendar.getInstance();

        JSONArray node = new JSONArray();

        
        int days = 24;// Get the number of hours in a day
        int neutrals_ = 0, positives_ = 0, negatives_ = 0;
        int[][] dias = new int[days][4];
            
        for(int i = 0; i < aListFilter.size(); i++){
            SemanticObject sobj =(SemanticObject) aListFilter.get(i);
            PostIn postIn = (PostIn)sobj.createGenericInstance();
            
            if (postIn.getPostSentimentalType() == 0) {
                neutrals_++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positives_++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negatives_++;
            }
            if (postIn.getPi_createdInSocialNet() != null) {
                date = postIn.getPi_createdInSocialNet();
            }

            calendario.setTime(date);

            //int year = calendario.get(Calendar.YEAR);
            //int comMonth = calendario.get(Calendar.MONTH);
            int hourIndex = calendario.get(Calendar.HOUR_OF_DAY);

            dias[hourIndex][0] += 1;
            dias[hourIndex][1] += neutrals_;
            dias[hourIndex][2] += negatives_;
            dias[hourIndex][3] += positives_;
            //dias[hourIndex][4] = comMonth;
            //dias[hourIndex][5] = year;
            //dias[hourIndex][6] = calendario.get(Calendar.DAY_OF_MONTH);

            neutrals_ = 0;
            negatives_ = 0;
            positives_ = 0;
        }
        int max = 0;
        for (int i = 0; i < dias.length; i++) {//Obtiene la hora con el mayor numero de posts
            int suma = dias[i][1] + dias[i][2] + dias[i][3];
            if(suma > max){
                max = suma;
            }
        }        
        int d = 1;
        for (int idx = 0; idx < dias.length; idx++) {
            if (d <= days) {
                int totalPosts = dias[idx][0];
                int neutrals_s = dias[idx][1];
                int negatives_s = dias[idx][2];
                int positives_s = dias[idx][3];
                int mes = Integer.parseInt(selectedMes);
                int year = Integer.parseInt(selectedAnio);
                int dia = Integer.parseInt(selectedDia);

                JSONObject node1 = new JSONObject();
                node1.put("day", dia);
                node1.put("hourOfDay", idx);
                node1.put("month", mes);
                node1.put("year", year);
                node1.put("neutrals", neutrals_s);
                node1.put("positives", positives_s);
                node1.put("negatives", negatives_s);
                node1.put("post", totalPosts);
                node1.put("chartclass", "possClass");
                node1.put("x", days);
                node1.put("totalPost", max + (max*.3));//Dar un margen para mostrar el tooltip
                node1.put("typeX", "Horas");
                node.put(node1);
                d++;
            }
        }        
        return node;

    }


%>

<%
    if (request.getParameter("objUri") != null && request.getParameter("getGraphData") != null) {        
        SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        out.println(getObject(semObj, lang, request));
        return;
    }else{//Prints the html to allocate the graph and calls itself to load the data of the graph
%>

<%
    String suri = request.getParameter("suri");
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    //System.out.println(semObj);
    if (semObj == null) {
        return;
    }

    String args = "?objUri=" + semObj.getEncodedURI();

    String selectedAnio = request.getParameter("selectedAnio");
    String selectedMes = request.getParameter("selectedMes");
    String selectedDia = request.getParameter("selectedDia");
    args += "&selectedAnio=" + selectedAnio;
    args += "&selectedMes=" + selectedMes;
    args += "&selectedDia=" + selectedDia;
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");    
    SWBResourceURL urlRender = paramRequest.getRenderUrl();
%>


<html>
    <head>
        <script src="http://d3js.org/d3.v3.min.js"></script>
        <script src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.min.js"></script>
        <script language="javascript" type="text/javascript">
            function resizeIframe() {
                var iframe =  window.parent.document.getElementById('inneriframe1');              
                var container = document.getElementById('chart');     
                iframe.style.height = container.offsetHeight + 'px';             
            }
        </script>
        <meta charset="utf-8">
        <style type="text/css">
            body {
                font: 10px sans-serif;
            }

            .axis path, .axis line {
                fill: none;
                stroke: #FF8000;
                shape-rendering: crispEdges;
            }
            .bar {
                fill: orange;
            }

            .bar:hover {
                fill: orange ;
            }

            .bar_neutrals {
                fill: #FFD700;
            }

            .bar_neutrals:hover {
                fill: #FFD700 ;
            }

            .bar_negatives {
                fill: #FF0000;
            }

            .bar_negatives:hover {
                fill: #FF0000;
            }


            .bar_positives {
                fill: #008000;
            }

            .bar_positives:hover{
                fill: #008000 ;
            }            
            .x.axis path {
                display: none;
            }

            .d3-tip {
                line-height: 1;
                font-weight: bold;
                padding: 12px;
                background: rgba(0, 0, 0, 0.8);
                color: #fff;
                border-radius: 2px;
            }

            /* Creates a small triangle extender for the tooltip */
            .d3-tip:after {
                box-sizing: border-box;
                display: inline;
                font-size: 10px;
                width: 100%;
                line-height: 1;
                color: rgba(0, 0, 0, 0.8);
                content: "\25BC";
                position: absolute;
                text-align: center;
            }

            /* Style northward tooltips differently */
            .d3-tip.n:after {
                margin: -1px 0 0 0;
                top: 100%;
                left: 0;
            }


        </style>
    </head>
    <body onload="resizeIframe();javascript:valid('1');">        

        <div id="chart"></div>
        <script type="text/javascript" >

            var margin = {top: 20, right: 20, bottom: 30, left: 40},
            width = 960 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom;

            var x = d3.scale.ordinal().rangeRoundBands([1, width]);
            var y = d3.scale.linear().range([height, 0]);
            
            var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom");
            //.tickSize(0);

            var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");
                          
            var tip = d3.tip()
            .attr('class', 'd3-tip')
            .offset([-10, 0])
            .html(function(d) {
                return "<strong>Numero de post:</strong> <span style='color:#FFFFFF'>" + d.post + "</span><br>\n\
                            <strong>Neutros:</strong>  <span style='color:#D8D8D8'>" + d.neutrals + "</span><br>  \n\
                            <strong>Positivos:</strong>  <span style='color:#04B431'>" + d.positives + "</span><br> \n\
                            <strong>Negativos:</strong>  <span style='color:red'>" + d.negatives + "</span><br>           ";
            })
            //});        //var data = [[1,100],[6,20],[20, 50]];

            var xArray = new Array();

            //d3.json("<%//=SWBPlatform.getContextPath()%>/work/models/<%//=SWBContext.getAdminWebSite().getId()%>/jsp/stream/InfoGraphBar.jsp<%//=args%>", function(error, data) {
            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/postInByHour.jsp<%=args%>&getGraphData=true", function(error, data) {
                //function(d) { alert('entro');return x(data.day);}
                // alert(data[1].x);
                var typeX ;
                var neutrals ;
                var positives;
                var negatives; 
                
                for (i = 0; i < data[1].x; i++) {               
                    xArray.push(data[i].hourOfDay)                    
                    y.domain([0,data[i].totalPost]);               
                    typeX =  data[i].typeX;
                   
                }  
                x.domain(xArray);                                      

                var svg = d3.select("#chart").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
         
                svg.call(tip);
                svg.append("g")
                .attr("class", "axis")
                .call(yAxis)
                .append("text")
                .attr("transform", "rotate(-90)")
                .attr("y", 6)
                .attr("dy", ".71em")
                .style("text-anchor", "end")
                .text("Numero de Post");           

        
                svg.append("g")
                .attr("class", "axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
                .append("text")
                .attr("x", 910)
                .attr("dx", ".20em")
                .style('text-anchor','end')
                .text(typeX);
             
             

               
                svg.selectAll("bar")
                .data(data)
                .enter().append("rect")
                .attr("class",function(d) { 
                    var neutrals = d.neutrals;
                    var positives = d.positives;
                    var negatives = d.negatives;
                    if(positives>negatives && positives>neutrals){
                        return "bar_positives"; 
                    }else if(negatives>neutrals){                        
                        return "bar_negatives";                        
                    }else {
                        return "bar_neutrals";                        
                    }        
                    
                })
                .attr("x", function(d) { return x(d.hourOfDay);})
                .attr("width", x.rangeBand() -5 )
                .attr("y", function(d) {return y(d.post);})
                .attr("height", function(d) {return height - y(d.post);}) 
                .on('mouseover', tip.show)
                .on('mouseout', tip.hide)
                .on("click", function(d) {                   
                    var url = "<%=urlRender.setMode("exportExcel").setParameter("type", "graphBarByHour").setCallMethod(SWBParamRequest.Call_DIRECT).setParameter("suri", suri)%>&selectedYear="+d.year+"&selectedMonth="+d.month+"&selectedDay="+d.day+"&selectedHour="+d.hourOfDay;
                    document.location.href = url;
                }) 
   
            });

        </script>

</html>



<%    
    }
%>