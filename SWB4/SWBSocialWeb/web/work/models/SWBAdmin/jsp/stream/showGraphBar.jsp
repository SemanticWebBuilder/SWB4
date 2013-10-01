<%@page import="java.net.URLEncoder"%>
<%@page import="com.sun.mail.handlers.image_gif"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    System.out.println("entro a show");
    String suri = request.getParameter("suri");
    System.out.println("suri en showgraphbar" + suri);
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.getSemanticObject(suri);
    if (semObj == null) {
        return;
    }
    System.out.println("semObj" + semObj);
    String args = "?objUri=" + semObj.getEncodedURI();
    String selectedAnio = request.getParameter("selectedAnio");
    String selectAnio = request.getParameter("selectAnio");
    String selectMes = request.getParameter("selectMes");
    args += "&selectedAnio=" + selectedAnio;
    args += "&selectAnio=" + selectAnio;
    args += "&selectMes=" + selectMes;


    System.out.println("jspselectedAnio " + selectedAnio);
    System.out.println("jsp selectAnio2 " + selectAnio);
    System.out.println(" jsp selectMes" + selectMes);


    String anioSelected = "";
    ArrayList listMeses = new ArrayList();
    listMeses.add("Enero");
    listMeses.add("Febrero");
    listMeses.add("Marzo");
    listMeses.add("Abril");
    listMeses.add("Mayo");
    listMeses.add("Junio");
    listMeses.add("Julio");
    Iterator iMeses = listMeses.iterator();

    ArrayList listAnio = new ArrayList();
    listAnio.add("2013");
    listAnio.add("2012");

    Iterator iAnio = listAnio.iterator();
    SWBResourceURL url = paramRequest.getRenderUrl();
    System.out.println("url" + url);

%>


<html>
    <head>
        <script src="http://d3js.org/d3.v3.min.js"></script>
        <script src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.min.js"></script>
        <script language="javascript" type="text/javascript">
            function resizeIframe() {

                var iframe =  window.parent.document.getElementById('inneriframe');              
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
                fill: orangered ;
            }

            .bar_neutrals {
                fill: orange;
            }

            .bar_neutrals:hover {
                fill: #D8D8D8 ;
            }
            
            .bar_negatives {
                fill: orange;
            }

            .bar_negatives:hover {
                fill: orangered ;
            }
            
            
            .bar_positives {
                fill: orange;
            }

            .bar_positives {
                fill: #04B431 ;
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
    <body onload="resizeIframe();">

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

            d3.json("<%=SWBPlatform.getContextPath()%>/work/models/<%=SWBContext.getAdminWebSite().getId()%>/jsp/stream/InfoGraphBar.jsp<%=args%>", function(error, data) {
         
                //function(d) { alert('entro');return x(data.day);}
                // alert(data[1].x);
                var typeX ;
                var neutrals ;
                var positives;
                var negatives; 
                
                for (i = 0; i < data[1].x; i++) {               
                    xArray.push(data[i].month)                    
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
                    if(neutrals>positives>negatives){
                        return "bar_neutrals"; 
                    }else if(neutrals<positives<negatives){                        
                        return "bar_negatives";                        
                    }else if(neutrals<positives>negatives){
                         return "bar_positives";                        
                    }
                    
                })
                .attr("x", function(d) { return x(d.month);})
                .attr("width", x.rangeBand() )
                .attr("y", function(d) {return y(d.post);})
                .attr("height", function(d) {return height - y(d.post);}) 
                .on('mouseover', tip.show)
                .on('mouseout', tip.hide)
   
            });

        </script>

</html>